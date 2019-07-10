package cn.bh.jc.version.vo;

/**
 * Svn 相关参数
 * 
 * @author liubq
 */
public class SvnParaVO {
	// svn地址
	private String svnUrl;
	// 用户名称
	private String user;
	// 用户密码
	private String pwd;

	/**
	 * @param svnUrl svn地址
	 * @param user 用户名称
	 * @param pwd 用户密码
	 */
	public SvnParaVO(String svnUrl, String user, String pwd) {
		super();
		this.svnUrl = svnUrl;
		this.user = user;
		this.pwd = pwd;
	}

	/**
	 * svn地址
	 * 
	 * @return svn地址
	 */
	public String getUser() {
		return user;
	}

	/**
	 * 用户名称
	 * 
	 * @return 用户名称
	 */
	public String getSvnUrl() {
		return svnUrl;
	}

	/**
	 * 用户密码
	 * 
	 * @return 用户密码
	 */
	public String getPwd() {
		return pwd;
	}

}
