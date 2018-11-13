package cn.bh.jc.version;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import cn.bh.jc.common.PathUtil;
import cn.bh.jc.domain.ChangeInfo;
import cn.bh.jc.domain.ChangeVO;
import cn.bh.jc.domain.Config;

/**
 * 时间变化方式收集变化
 * 
 * @author liubq
 * @since 2018年1月16日
 */
public class TimeVersion extends StoreVersion {
	// 项目目录
	private final String projectPath;
	// 输入时间
	private final String time;
	// 开始时间
	private final long beginTime;

	/**
	 * 时间变化版本
	 * 
	 * @param inConf 配置信息
	 * @param target 可运行程序（编译后程序）保存地址
	 * @param inProjectPath 项目工程地址
	 * @param time 开始时间
	 * @throws Exception
	 */
	public TimeVersion(Config inConf, String target, String inProjectPath, String time) throws Exception {
		this(inConf, target, inProjectPath, time, null);
	}

	/**
	 * 时间变化版本
	 * 
	 * @param inConf 配置信息
	 * @param target 可运行程序（编译后程序）保存地址
	 * @param inProjectPath 项目工程地址
	 * @param time 开始时间
	 * @param inExportProjectName 导出工程名称
	 * @throws Exception
	 */
	public TimeVersion(Config inConf, String target, String inProjectPath, String time, String inExportProjectName) throws Exception {
		super();
		this.setTargetPath(target);
		this.projectPath = PathUtil.replace(inProjectPath);
		this.setProjectName(projectPath.substring(projectPath.lastIndexOf("/") + 1));
		if (inExportProjectName == null || inExportProjectName.trim().length() == 0) {
			this.setExportProjectName(this.getProjectName());
		} else {
			this.setExportProjectName(inExportProjectName.trim());
		}
		this.time = time;
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		this.beginTime = f.parse(this.time).getTime();
	}

	/**
	 * 列出所有变化文件
	 * 
	 * @return
	 * @throws Exception
	 */
	public ChangeVO get() throws Exception {
		ChangeVO resVO = new ChangeVO();
		resVO.setVersion(this);
		// 取得变化文件列表
		List<File> changeFileList = listTimeChangeFile(new File(projectPath));
		// 变成标准地址
		String tempFile;
		List<String> changeList = new ArrayList<String>();
		for (File cf : changeFileList) {
			tempFile = PathUtil.trimName(cf.getAbsolutePath(), this.getProjectName());
			changeList.add(tempFile);
		}
		ChangeInfo resInfo = new ChangeInfo();
		resInfo.setChangeFiles(changeList);
		resVO.setInfo(resInfo);
		return resVO;
	}

	/**
	 * 列出所有变化文件
	 * 
	 * @param file
	 * @return
	 */
	private List<File> listTimeChangeFile(File file) {

		List<File> resList = new ArrayList<File>();
		if (file.exists()) {
			if (file.isDirectory() && !isExclusiveOnTime(file)) {
				for (File subFile : file.listFiles()) {
					resList.addAll(listTimeChangeFile(subFile));
				}
			} else if (file.isFile() && isFileChangeOnTime(file)) {
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
	private boolean isFileChangeOnTime(File file) {
		// 特殊文件排除
		if (isExclusiveOnTime(file)) {
			return false;
		}
		// 时间不符合排除
		long lastMoified = file.lastModified();
		if (lastMoified > beginTime) {
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
	private boolean isExclusiveOnTime(File file) {
		if (!file.exists()) {
			return true;
		}
		// 指定目录排除
		String path = file.getAbsolutePath();
		return PathUtil.isExclusive(projectPath, path, getConf());
	}

}
