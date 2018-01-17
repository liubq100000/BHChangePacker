package cn.bh.jc.vo;

import java.util.List;

import cn.bh.jc.common.PathUtil;
import cn.bh.jc.common.SVNWorker;

/**
 * SVN变化版本
 * 
 * @author liubq
 * @since 2018年1月16日
 */
public class SVNVersion {
	// svn地址
	private String svnUrl;
	// 用户名称
	private String username;
	// 用户密码
	private String password;
	// 开始版本
	private Long startVersion;
	// 结束版本
	private Long endVersion;
	// 项目名称
	private String projectName;
	// 工作者
	private SVNWorker worker = null;

	/**
	 * SVN变化版本
	 * 
	 * @param inSvnUrl svn 地址
	 * @param name 用户名称
	 * @param pwd 用户密码
	 * @param startVersion 开始版本号
	 * @throws Exception
	 */
	public SVNVersion(String inSvnUrl, String name, String pwd, Long startVersion) throws Exception {
		super();
		this.svnUrl = inSvnUrl;
		String tempSvnUrl = svnUrl;
		tempSvnUrl = PathUtil.replace(tempSvnUrl);
		projectName = tempSvnUrl.substring(tempSvnUrl.lastIndexOf("/") + 1);
		this.username = name;
		this.password = pwd;
		this.startVersion = startVersion;
		this.endVersion = -1L;
		worker = new SVNWorker(svnUrl, username, password);
	}

	/**
	 * 列出所有变化文件
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<String> listAllChangeFileName() throws Exception {
		return worker.listAllChangeFileName(startVersion, endVersion);
	}

	public String getSvnUrl() {
		return svnUrl;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public Long getStartVersion() {
		return startVersion;
	}

	public Long getEndVersion() {
		return endVersion;
	}

	public String getProjectName() {
		return projectName;
	}

	public SVNWorker getWorker() {
		return worker;
	}

}
