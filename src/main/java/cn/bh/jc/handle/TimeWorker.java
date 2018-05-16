package cn.bh.jc.handle;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.bh.jc.common.PathUtil;
import cn.bh.jc.version.TimeVersion;

/**
 * 时间比较工具类
 * 
 * @author liubq
 * @since 2017年12月20日
 */
public class TimeWorker {

	// 时间比较工具类
	private TimeVersion version;

	/**
	 * 时间比较工具类
	 * 
	 * @param version 版本
	 * @param name 用户名称
	 * @param pwd 名称
	 */
	public TimeWorker(TimeVersion version) {
		this.version = version;
	}

	/**
	 * 列出所有变化文件
	 * 
	 * @param file
	 * @return
	 */
	public List<File> listAllChangeFile() {
		File file = new File(version.getProjectPath());
		return listChangeFile(file, version);
	}

	/**
	 * 列出所有变化文件
	 * 
	 * @param file
	 * @return
	 */
	private List<File> listChangeFile(File file, TimeVersion timeVersion) {

		List<File> resList = new ArrayList<File>();
		if (file.exists()) {
			if (file.isDirectory() && !isExclusive(file, timeVersion)) {
				for (File subFile : file.listFiles()) {
					resList.addAll(listChangeFile(subFile, timeVersion));
				}
			} else if (file.isFile() && isChange(file, timeVersion)) {
				resList.add(file);
			}
		}
		return resList;
	}

	/**
	 * 是否变化过
	 * 
	 * @param file
	 * @return
	 */
	protected boolean isChange(File file, TimeVersion timeVersion) {
		// 特殊文件排除
		if (isExclusive(file, timeVersion)) {
			return false;
		}
		// 时间不符合排除
		long lastMoified = file.lastModified();
		if (lastMoified > timeVersion.getBeginTime()) {
			return true;
		}
		return false;
	}

	/**
	 * 是否需要排除
	 * 
	 * @param file
	 * @return
	 */
	protected boolean isExclusive(File file, TimeVersion timeVersion) {
		if (!file.exists()) {
			return true;
		}
		// 特殊文件排除
		String name = file.getName();
		if (name.startsWith(".")) {
			return true;
		}
		// 指定目录排除
		String path = file.getAbsolutePath();
		path = PathUtil.replace(path);
		for (String subPath : PathUtil.getExclusiveList()) {
			if (path.indexOf(timeVersion.getProjectPath() + "/" + subPath) >= 0) {
				return true;
			}
		}
		return false;
	}
}
