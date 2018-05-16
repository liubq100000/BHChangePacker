package cn.bh.jc.version;

import cn.bh.jc.common.PathUtil;
import cn.bh.jc.domain.ChangeVO;

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

	/**
	 * 取得工程名称
	 * 
	 * @return 工程名称
	 */
	public abstract String getProjectName();

	/**
	 * 取得导出工程名称
	 * 
	 * @return 导出工程名称
	 */
	public abstract String getExportProjectName();

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
