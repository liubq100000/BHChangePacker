package cn.bh.jc.domain;

import cn.bh.jc.version.StoreVersion;

/**
 * 变化目录
 * 
 * @author liubq
 * @since 2018年4月13日
 */
public class ChangeVO {
	// 变化版本信息
	private StoreVersion version;

	// 变化文件信息
	private ChangeInfo info;

	/**
	 * 变化版本信息
	 * 
	 * @return 版本信息
	 */
	public StoreVersion getVersion() {
		return version;
	}

	/**
	 * 变化版本信息
	 * 
	 * @param version 变化版本信息
	 */
	public void setVersion(StoreVersion version) {
		this.version = version;
	}

	/**
	 * 变化文件列表
	 * 
	 * @return
	 */
	public ChangeInfo getInfo() {
		return info;
	}

	/**
	 * 变化文件列表
	 * 
	 * @param info
	 */
	public void setInfo(ChangeInfo info) {
		this.info = info;
	}

}
