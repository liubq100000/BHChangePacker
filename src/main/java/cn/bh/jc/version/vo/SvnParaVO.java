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
	// 工程所用的tomcat地址（主要是为了Copy class等文件）
	private String target;
	// 开始版本
	private Long startVersion;
	// 导出工程名
	private String expName;

	/**
	 * @param svnUrl svn地址
	 * @param user 用户名称
	 * @param pwd 用户密码
	 * @param startVersion 版本本号
	 * @param target class目录地址
	 */
	public SvnParaVO(String svnUrl, String user, String pwd, Long startVersion, String target) {
		super();
		this.svnUrl = svnUrl;
		this.user = user;
		this.pwd = pwd;
		this.startVersion = startVersion;
		this.target = target;
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

	/**
	 * 导出工程名称
	 * 
	 * @return 导出工程名称
	 */
	public String getExpName() {
		return expName;
	}

	/**
	 * 导出工程名称
	 * 
	 * @param expName 导出工程名称
	 */
	public SvnParaVO setExpName(String expName) {
		this.expName = expName;
		return this;
	}

	/**
	 * class 文件目录
	 * 
	 * @return class 文件目录
	 */
	public String getTarget() {
		return target;
	}

	/**
	 * 开始版本
	 * 
	 * @return 开始版本
	 */
	public Long getStartVersion() {
		return startVersion;
	}

}
