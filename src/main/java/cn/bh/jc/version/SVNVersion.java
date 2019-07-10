package cn.bh.jc.version;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.tmatesoft.svn.core.SVNLogEntry;
import org.tmatesoft.svn.core.SVNLogEntryPath;
import org.tmatesoft.svn.core.SVNNodeKind;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.auth.ISVNAuthenticationManager;
import org.tmatesoft.svn.core.internal.io.dav.DAVRepositoryFactory;
import org.tmatesoft.svn.core.io.SVNRepository;
import org.tmatesoft.svn.core.io.SVNRepositoryFactory;
import org.tmatesoft.svn.core.wc.SVNWCUtil;

import cn.bh.jc.common.PathUtil;
import cn.bh.jc.common.SysLog;
import cn.bh.jc.domain.ChangeInfo;
import cn.bh.jc.domain.ChangeVO;
import cn.bh.jc.domain.Config;
import cn.bh.jc.version.vo.SvnParaVO;

/**
 * SVN版本方式收集变化
 * 
 * @author liubq
 * @since 2018年1月16日
 */
public class SVNVersion extends StoreVersion {
	// 参数
	private final SvnParaVO para;
	// 结束版本
	private Long endVersion;

	/**
	 * SVN变化版本
	 * 
	 * @param inConf 配置信息
	 * @param target 可运行程序（编译后程序）保存地址
	 * @param inPara 参数
	 * @param startVersion 开始版本号
	 * @param expName 导出工程名称
	 * @throws Exception
	 */
	public SVNVersion(Config inConf, SvnParaVO inPara) throws Exception {
		super(inConf, inPara.getTarget(), inPara.getSvnUrl(), inPara.getExpName());
		this.para = inPara;
		this.endVersion = -1L;
		// SVN初始化
		DAVRepositoryFactory.setup();
	}

	/**
	 * 列出所有变化文件
	 * 
	 * @return
	 * @throws Exception
	 */
	public ChangeVO get() throws Exception {
		// 变化的文件列表
		ChangeInfo svnInfo = listAllSvnChange();
		if (svnInfo == null) {
			return null;
		}
		ChangeVO resVO = new ChangeVO();
		resVO.setVersion(this);
		resVO.setInfo(new ChangeInfo());
		// SVN，地址转换为标准地址
		// 变化文件转换
		List<String> changeFiles = new ArrayList<String>();
		String tempFile;
		for (String svnFileName : svnInfo.getChangeFiles()) {
			tempFile = PathUtil.trimName(svnFileName, this.getProjectName());
			changeFiles.add(tempFile);
		}
		resVO.getInfo().setChangeFiles(changeFiles);
		// 删除文件转换
		Set<String> delSet = new HashSet<String>();
		for (String svnFileName : svnInfo.getDelSet()) {
			tempFile = PathUtil.trimName(svnFileName, this.getProjectName());
			delSet.add(tempFile);
		}
		resVO.getInfo().setDelSet(delSet);
		return resVO;
	}

	/**
	 * 根据版本号查询变化文件名列表
	 * 
	 * @return
	 * @throws Exception
	 */
	private ChangeInfo listAllSvnChange() throws Exception {
		// 定义svn版本库的URL。
		SVNURL repositoryURL = null;
		// 定义版本库。
		SVNRepository repository = null;
		try {
			// 获取SVN的URL。
			repositoryURL = SVNURL.parseURIEncoded(para.getSvnUrl());
			// 根据URL实例化SVN版本库。
			repository = SVNRepositoryFactory.create(repositoryURL);
		} catch (Exception e) {
			SysLog.log("创建版本库实例时失败，版本库的URL是 '" + para.getSvnUrl() + "'", e);
			throw e;
		}
		// 对版本库设置认证信息。
		ISVNAuthenticationManager authManager = SVNWCUtil.createDefaultAuthenticationManager(para.getUser(), para.getPwd().toCharArray());
		repository.setAuthenticationManager(authManager);
		SVNURL svnRootUrl = repository.getRepositoryRoot(true);
		if (svnRootUrl == null) {
			SysLog.log("创建版本库根路径异常");
			throw new Exception("getRepositoryRoot 异常");
		}
		String preSvnUrl = PathUtil.replace(repository.getLocation().toString());
		preSvnUrl = preSvnUrl.substring(svnRootUrl.toString().length());
		preSvnUrl = "/" + PathUtil.replace(preSvnUrl);
		preSvnUrl = URLDecoder.decode(preSvnUrl, "UTF-8");
		// 下载这个期间内所有变更文件
		try {
			if (endVersion < 0) {
				endVersion = repository.getLatestRevision();
			}
			@SuppressWarnings("unchecked")
			Collection<SVNLogEntry> logEntries = repository.log(new String[] { "" }, null, para.getStartVersion(), endVersion, true, true);
			List<String> fileList = new ArrayList<String>();
			Set<String> delSet = new HashSet<String>();
			// 我测试这个是有顺序
			for (SVNLogEntry log : logEntries) {
				String key;
				for (Map.Entry<String, SVNLogEntryPath> entry : log.getChangedPaths().entrySet()) {
					key = entry.getKey();
					if (!entry.getValue().getKind().equals(SVNNodeKind.DIR)) {
						if (!key.startsWith(preSvnUrl)) {
							continue;
						}
						// 排除文件跳过
						if (PathUtil.isExclusive(this.getProjectName(), key, this.getConf())) {
							continue;
						}
						if ("D".equalsIgnoreCase(String.valueOf(entry.getValue().getType()))) {
							fileList.remove(key);
							// 删除了
							delSet.add(key);

						} else {
							if (!fileList.contains(key)) {
								fileList.add(key);
							}
							// 删除了又加回来了
							if (delSet.contains(key)) {
								SysLog.log(key + "删除后又回复了这个文件，请判断是否合理 ");
								delSet.remove(key);
							}
						}
					}
				}
			}
			ChangeInfo resVO = new ChangeInfo();
			resVO.setChangeFiles(fileList);
			resVO.setDelSet(delSet);
			return resVO;
		} catch (Exception e) {
			SysLog.log("下载这个期间内所有变更文件错误: ", e);
			throw e;
		}
	}
}
