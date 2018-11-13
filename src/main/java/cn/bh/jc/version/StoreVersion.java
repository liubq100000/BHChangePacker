package cn.bh.jc.version;

import cn.bh.jc.common.PathUtil;
import cn.bh.jc.domain.ChangeVO;
import cn.bh.jc.domain.Config;

/**
 * 变化版本信息
 * 
 * @author liubq
 * @since 2018年3月7日
 */
public abstract class StoreVersion {

	/**
	 * 列出所有变化的对象
	 * 
	 * @return
	 * @throws Exception
	 */
	public abstract ChangeVO get() throws Exception;

	// 项目名称
	private String projectName;

	// 导出工程名称
	private String exportProjectName;

	/**
	 * 取得项目名称
	 * 
	 * @return 项目名称
	 */
	public String getProjectName() {
		return projectName;
	}

	/**
	 * 取得导出工程名称
	 * 
	 * @return 导出工程名称
	 */
	public String getExportProjectName() {
		return exportProjectName;
	}

	/**
	 * 设定取得项目名称
	 * 
	 * @param projectName 取得项目名称
	 */
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	/**
	 * 设定导出工程名称
	 * 
	 * @param exportProjectName 导出工程名称
	 */
	public void setExportProjectName(String exportProjectName) {
		this.exportProjectName = exportProjectName;
	}

	// 可运行程序执行保存地址
	private String targetPath;

	/**
	 * 
	 * 取得可运行程序保存地址
	 * 
	 * @return 地址
	 */
	public String getTargetPath() {
		return targetPath;
	}

	/**
	 * 设定可运行程序保存地址
	 * 
	 * @param targetPath
	 */
	public void setTargetPath(String targetPath) {
		this.targetPath = PathUtil.replace(targetPath);
	}

	// 配置
	private Config defaultConf;

	/**
	 * 
	 * 取得配置
	 * 
	 * @return 配置
	 */
	public Config getConf() {
		return defaultConf;
	}

	/**
	 * 设定配置
	 * 
	 * @param inConf
	 */
	public void setConf(Config inConf) {
		this.defaultConf = inConf;
	}

}
