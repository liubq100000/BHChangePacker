package cn.bh.jc.common;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.tmatesoft.svn.core.SVNLogEntry;
import org.tmatesoft.svn.core.SVNLogEntryPath;
import org.tmatesoft.svn.core.SVNNodeKind;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.auth.ISVNAuthenticationManager;
import org.tmatesoft.svn.core.internal.io.dav.DAVRepositoryFactory;
import org.tmatesoft.svn.core.io.SVNRepository;
import org.tmatesoft.svn.core.io.SVNRepositoryFactory;
import org.tmatesoft.svn.core.wc.SVNWCUtil;

/**
 * SVN工具类
 * 
 * @author liubq
 * @since 2017年12月20日
 */
public class SVNWorker {
	// svn地址
	private String url;
	// 用户名称
	private String username;
	// 名称
	private String password;
	// 工程名称
	private String projectName;

	/**
	 * SVN操作者
	 * 
	 * @param url svn地址
	 * @param name 用户名称
	 * @param pwd 名称
	 */
	public SVNWorker(String url, String name, String pwd) {
		this.url = url;
		this.username = name;
		this.password = pwd;
		String tempSvnUrl = url;
		tempSvnUrl = PathUtil.replace(tempSvnUrl);
		projectName = tempSvnUrl.substring(tempSvnUrl.lastIndexOf("/") + 1);
		DAVRepositoryFactory.setup();
	}

	/**
	 * 根据版本号查询变化文件名列表
	 * 
	 * @param startRevision
	 * @param endRevision
	 * @return
	 * @throws Exception
	 */
	public List<String> listAllChangeFileName(long startRevision, long endRevision) throws Exception {
		// 定义svn版本库的URL。
		SVNURL repositoryURL = null;
		// 定义版本库。
		SVNRepository repository = null;
		try {
			// 获取SVN的URL。
			repositoryURL = SVNURL.parseURIEncoded(url);
			// 根据URL实例化SVN版本库。
			repository = SVNRepositoryFactory.create(repositoryURL);
		} catch (Exception e) {
			SysLog.log("创建版本库实例时失败，版本库的URL是 '" + url + "'", e);
			throw e;
		}
		// 对版本库设置认证信息。
		ISVNAuthenticationManager authManager = SVNWCUtil.createDefaultAuthenticationManager(username, password.toCharArray());
		repository.setAuthenticationManager(authManager);

		// 下载这个期间内所有变更文件
		try {
			if (startRevision < 0) {
				startRevision = 1;
			}
			if (endRevision < 0) {
				endRevision = repository.getLatestRevision();
			}
			@SuppressWarnings("unchecked")
			Collection<SVNLogEntry> logEntries = repository.log(new String[] { "" }, null, startRevision, endRevision, true, true);
			List<String> fileList = new ArrayList<String>();
			// 我测试这个是有顺序
			for (SVNLogEntry log : logEntries) {
				String key;
				for (Map.Entry<String, SVNLogEntryPath> entry : log.getChangedPaths().entrySet()) {
					key = entry.getKey();
					if (!entry.getValue().getKind().equals(SVNNodeKind.DIR)) {
						// 排除文件跳过
						if (isExclusive(key)) {
							continue;
						}
						if ("D".equalsIgnoreCase(String.valueOf(entry.getValue().getType()))) {
							fileList.remove(key);
						} else {
							if (!fileList.contains(key)) {
								fileList.add(key);
							}
						}
					}
				}
			}
			return fileList;
		} catch (Exception e) {
			SysLog.log("下载这个期间内所有变更文件错误: ", e);
			throw e;
		}
	}

	/**
	 * 是否需要排除
	 * 
	 * 这样仍然排除不了所有目录 例如：abc/ddd/asd.d这个类型目录
	 * 
	 * @param file
	 * @return
	 */
	protected boolean isExclusive(String file) {
		// 空对象
		if (file == null) {
			return true;
		}
		// 标准文件路径
		String name = PathUtil.replace(file);
		// 特殊文件排除
		if (name.indexOf(projectName + "/.") > 5) {
			return true;
		}
		// 指定目录排除
		for (String subPath : PathUtil.getExclusiveList()) {
			if (name.indexOf(projectName + "/" + subPath) > 5) {
				return true;
			}
		}
		return false;
	}
}
