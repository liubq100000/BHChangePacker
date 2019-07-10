package cn.bh.jc.version.vo;

import java.io.File;
import java.io.IOException;

/**
 * Git 相关参数
 * 
 * @author liubq
 */
public class GitParaVO {
	// 本地资源目录（.git目录上层）
	private String localRepositoryDir;
	// 地址
	private String gitUrl;
	// 用户名称
	private String gitName;
	// 用户密码
	private String gitPass;
	// 分支
	private String gitBranch;

	/**
	 * 构造
	 * 
	 * @param gitUrl 地址
	 * @param gitName 用户名称
	 * @param gitPass 用户密码
	 * @param gitBranch 分支
	 */
	public GitParaVO(String gitUrl, String gitName, String gitPass, String gitBranch) {
		super();
		this.gitUrl = gitUrl;
		this.gitName = gitName;
		this.gitPass = gitPass;
		this.gitBranch = gitBranch;
		try {
			localRepositoryDir = new File("./repository").getCanonicalPath();
		} catch (IOException e) {
		}
		if (localRepositoryDir == null) {
			throw new RuntimeException("创建Git本地资源目录失败");
		}
	}

	/**
	 * 构造
	 * 
	 * @param gitUrl 地址
	 * @param gitName 用户名称
	 * @param gitPass 用户密码
	 * @param gitBranch 分支
	 * @param repositoryDir 本地资源目录（.git目录上层）
	 */
	public GitParaVO(String gitUrl, String gitName, String gitPass, String gitBranch, File repositoryDir) {
		super();
		this.gitUrl = gitUrl;
		this.gitName = gitName;
		this.gitPass = gitPass;
		this.gitBranch = gitBranch;

		if (!repositoryDir.isDirectory()) {
			throw new RuntimeException("参数不正确，必须是git资源的目录");
		}
		if (!repositoryDir.exists()) {
			repositoryDir.mkdirs();
		}
		try {
			localRepositoryDir = repositoryDir.getCanonicalPath();
		} catch (IOException e) {
		}
	}

	/**
	 * 地址
	 * 
	 * @return 地址
	 */
	public String getGitUrl() {
		return gitUrl;
	}

	/**
	 * 用户名称
	 * 
	 * @return 用户名称
	 */
	public String getGitName() {
		return gitName;
	}

	/**
	 * 用户密码
	 * 
	 * @return 用户密码
	 */
	public String getGitPass() {
		return gitPass;
	}

	/**
	 * 分支
	 * 
	 * @return 分支
	 */
	public String getGitBranch() {
		return gitBranch;
	}

	/**
	 * 本地资源目录（.git目录上层）
	 * 
	 * @return 本地资源目录（.git目录上层）
	 */
	public String getLocalRepositoryDir() {
		return localRepositoryDir;
	}

}
