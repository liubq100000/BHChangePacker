package cn.bh.jc.domain;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import cn.bh.jc.common.PathUtil;
import cn.bh.jc.handle.TimeWorker;

/**
 * 时间变化版本
 * 
 * @author liubq
 * @since 2018年1月16日
 */
public class TimeVersion extends StoreVersion {
	// 项目目录
	private final String projectPath;
	// 项目名称
	private final String projectName;
	// 导出工程名称
	private final String exportProjectName;
	// 输入时间
	private final String time;
	// 开始时间
	private final long beginTime;
	// 时间版本
	private final TimeWorker worker;

	/**
	 * 时间变化版本
	 * 
	 * @param target 可运行程序（编译后程序）保存地址
	 * @param inProjectPath 项目工程地址
	 * @param time 开始时间
	 * @throws Exception
	 */
	public TimeVersion(String target, String inProjectPath, String time) throws Exception {
		this(target, inProjectPath, time, null);
	}

	/**
	 * 时间变化版本
	 * 
	 * @param target 可运行程序（编译后程序）保存地址
	 * @param inProjectPath 项目工程地址
	 * @param time 开始时间
	 * @param inExportProjectName 导出工程名称
	 * @throws Exception
	 */
	public TimeVersion(String target, String inProjectPath, String time, String inExportProjectName) throws Exception {
		super();
		this.setTargetPath(target);
		this.projectPath = PathUtil.replace(inProjectPath);
		this.projectName = projectPath.substring(projectPath.lastIndexOf("/") + 1);
		if (inExportProjectName == null || inExportProjectName.trim().length() == 0) {
			// 检查
			if (!this.getTargetPath().endsWith(projectName)) {
				throw new Exception("可执行工程(" + target + ") 和目标工程(" + inProjectPath + ")的工程名称不统一，目前该系统处理不了 ");
			}
			exportProjectName = projectName;
		} else {
			exportProjectName = inExportProjectName.trim();
		}
		this.time = time;
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		this.beginTime = f.parse(this.time).getTime();
		worker = new TimeWorker(this);
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
		List<File> changeFileList = worker.listAllChangeFile();
		// 变成标准地址
		String tempFile;
		List<String> changeList = new ArrayList<String>();
		for (File cf : changeFileList) {
			tempFile = PathUtil.trimName(cf.getAbsolutePath(), projectName);
			changeList.add(tempFile);
		}
		ChangeInfo resInfo = new ChangeInfo();
		resInfo.setChangeFiles(changeList);
		resVO.setInfo(resInfo);
		return resVO;
	}

	public String getProjectPath() {
		return projectPath;
	}

	public String getTime() {
		return time;
	}

	public long getBeginTime() {
		return beginTime;
	}

	public String getExportProjectName() {
		return exportProjectName;
	}

	@Override
	public String getProjectName() {
		return projectName;
	}

}
