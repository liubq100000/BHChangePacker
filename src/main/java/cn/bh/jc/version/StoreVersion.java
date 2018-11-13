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
	 * 构造函数
	 * 
	 * @param inConf 配置
	 * @param target 目标
	 * @param path 工程路径
	 * @param inExportProjectName 导出的工程名称
	 */
	public StoreVersion(Config inConf, String target, String path, String inExportProjectName) {
		this.setTargetPath(target);
		this.setConf(inConf);
		// 工程名称计算
		String tempPath = PathUtil.replace(path);
		if (tempPath.lastIndexOf("/") > 0) {
			this.setProjectName(tempPath.substring(tempPath.lastIndexOf("/") + 1));
		} else {
			this.setProjectName(tempPath);
		}
		if (inExportProjectName == null || inExportProjectName.trim().length() == 0) {
			this.setExportProjectName(this.getProjectName());
		} else {
			this.setExportProjectName(inExportProjectName.trim());
		}
	}

	// 项目名称
	private String projectName;

	// 导出工程名称
	private String exportProjectName;

	// 可运行程序执行保存地址
	private String targetPath;

	// 配置
	private Config defaultConf;

	/**
	 * 列出所有变化的对象
	 * 
	 * @return
	 * @throws Exception
	 */
	public abstract ChangeVO get() throws Exception;

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
