package cn.bh.jc.version;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.diff.RenameDetector;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.eclipse.jgit.treewalk.TreeWalk;

import cn.bh.jc.common.PathUtil;
import cn.bh.jc.common.SysLog;
import cn.bh.jc.domain.ChangeInfo;
import cn.bh.jc.domain.ChangeVO;
import cn.bh.jc.domain.Config;
import cn.bh.jc.version.vo.GitParaVO;

/**
 * GIT版本方式收集变化
 * 
 * @author liubq
 * @since 2018年1月16日
 */
public class GitVersion extends StoreVersion {
	// 参数
	private final GitParaVO para;

	/**
	 * GIT变化版本
	 * 
	 * @param inConf 配置信息
	 * @param target 可运行程序（编译后程序）保存地址
	 * @param inPara 参数
	 * @param startVersion 开始版本号
	 * @param expName 导出工程名称
	 * @throws Exception
	 */
	public GitVersion(Config inConf, GitParaVO inPara) throws Exception {
		super(inConf, inPara.getTarget(), inPara.getGitUrl(), inPara.getExpName());
		this.para = inPara;
	}

	/**
	 * 列出所有变化文件
	 * 
	 * @return
	 * @throws Exception
	 */
	public ChangeVO get() throws Exception {
		// 变化的文件列表
		ChangeInfo gitInfo = listAllSvnChange();
		if (gitInfo == null) {
			return null;
		}
		ChangeVO resVO = new ChangeVO();
		resVO.setVersion(this);
		resVO.setInfo(new ChangeInfo());
		// SVN，地址转换为标准地址
		// 变化文件转换
		List<String> changeFiles = new ArrayList<String>();
		String tempFile;
		for (String fileName : gitInfo.getChangeFiles()) {
			tempFile = PathUtil.replace(fileName);
			changeFiles.add(tempFile);
		}
		resVO.getInfo().setChangeFiles(changeFiles);
		// 删除文件转换
		Set<String> delSet = new HashSet<String>();
		for (String fileName : gitInfo.getDelSet()) {
			tempFile = PathUtil.replace(fileName);
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
		Git git = null;
		try {
			StringBuilder localRepoPath = new StringBuilder(para.getLocalRepositoryDir());
			localRepoPath.append("/").append(para.getGitName()).append("/").append(para.getGitBranch());
			localRepoPath.append("/").append(this.getProjectName());
			// 下载地址
			File dir = new File(localRepoPath.toString());
			if (!dir.exists()) {
				dir.mkdirs();
			}
			Long startTime = System.currentTimeMillis();
			SysLog.log("本地库位置，dir" + para.getLocalRepositoryDir());
			SysLog.log("建立git库连接开始");
			// 判断是否下载过
			File gitFile = new File(dir.getAbsolutePath() + "/.git");
			UsernamePasswordCredentialsProvider provider = new UsernamePasswordCredentialsProvider(para.getGitName(), para.getGitPass());
			if (gitFile.exists()) {
				git = Git.open(gitFile);
				git.pull().setRemoteBranchName(para.getGitBranch()).setCredentialsProvider(provider) // 设置权限验证
						.call();
			}
			// 第一次下载
			else {
				git = Git.cloneRepository().setURI(para.getGitUrl()).setBranch(para.getGitBranch()) // 设置clone下来的分支,默认master
						.setDirectory(dir) // 设置下载存放路径
						.setCredentialsProvider(provider) // 设置权限验证
						.call();

			}
			if (git == null) {
				SysLog.log("建立git库连接失败");
				return null;
			}
			SysLog.log("建立git库连接完成，耗时：" + (System.currentTimeMillis() - startTime));
			startTime = System.currentTimeMillis();
			// 取得所有日志
			Iterable<RevCommit> allCommitsLater = git.log().call();
			// 过滤出需要的提交
			LinkedList<RevCommit> list = new LinkedList<RevCommit>();
			boolean breakFlag = false;
			for (RevCommit com : allCommitsLater) {
				list.push(com);
				// 把startVersion开始前的一个节点也找到
				if (breakFlag) {
					break;
				}
				if (com.getId().toString().indexOf(para.getStartVersion()) >= 0) {
					breakFlag = true;
				}
			}
			SysLog.log("取得提交历史：" + list.size());
			List<DiffEntry> allDiffEntry = new ArrayList<DiffEntry>();
			Repository repository = git.getRepository();
			RevCommit now;
			RevCommit pre;
			for (int index = 1; index < list.size(); index++) {
				pre = list.get(index - 1);
				now = list.get(index);
				try (TreeWalk tw = new TreeWalk(repository);) {
					tw.addTree(pre.getTree());
					tw.addTree(now.getTree());
					tw.setRecursive(true);
					RenameDetector rd = new RenameDetector(repository);
					rd.addAll(DiffEntry.scan(tw));
					allDiffEntry.addAll(rd.compute());
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
			SysLog.log("分析变更历史，耗时：" + (System.currentTimeMillis() - startTime));
			startTime = System.currentTimeMillis();
			List<String> fileList = new ArrayList<String>();
			Set<String> delSet = new HashSet<String>();
			// 我测试这个是有顺序
			for (DiffEntry log : allDiffEntry) {
				String key = log.getNewPath();
				// 排除文件跳过
				if (PathUtil.isExclusive(this.getProjectName(), key, this.getConf())) {
					continue;
				}
				if (DiffEntry.ChangeType.DELETE == log.getChangeType()) {
					fileList.remove(log.getNewPath());
					delSet.add(log.getNewPath());
				} else {
					// 删除了又加回来了
					if (delSet.contains(key)) {
						SysLog.log(key + "删除后又回复了这个文件，请判断是否合理 ");
						delSet.remove(key);
					}
					if (!fileList.contains(key)) {
						fileList.add(key);
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
		} finally {
			if (git != null) {
				try {
					git.close();
				} catch (Exception e) {
				}
			}
		}
	}
}
