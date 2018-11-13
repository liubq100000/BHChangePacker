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
		super(inConf, target, inProjectPath, inExportProjectName);
		this.projectPath = PathUtil.replace(inProjectPath);
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
			// 排除的目录是直接返回
			if (PathUtil.isExclusive(this.getProjectName(), file.getAbsolutePath(), getConf())) {
				return resList;
			}
			if (file.isDirectory()) {
				for (File subFile : file.listFiles()) {
					resList.addAll(listTimeChangeFile(subFile));
				}
			} else if (file.isFile()) {
				// 时间不符合排除
				long lastMoified = file.lastModified();
				if (lastMoified > beginTime) {
					resList.add(file);
				}
			}
		}
		return resList;
	}
}
