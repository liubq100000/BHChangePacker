package cn.bh.jc.vo;

import cn.bh.jc.common.PathUtil;

/**
 * 变化版本信息
 * 
 * @author liubq
 * @since 2018年3月7日
 */
public class StoreVersion {

	// 可运行程序执行保存地址
	private String targetPath;

	/**
	 * 
	 * 取得可运行程序保存地址
	 * 
	 * @return地址
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

}
