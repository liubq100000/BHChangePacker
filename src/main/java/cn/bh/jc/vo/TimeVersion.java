package cn.bh.jc.vo;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.List;

import cn.bh.jc.common.PathUtil;
import cn.bh.jc.common.TimeWorker;

/**
 * 时间变化版本
 * 
 * @author liubq
 * @since 2018年1月16日
 */
public class TimeVersion extends StoreVersion {
	// 项目目录
	private String projectPath;
	// 输入时间
	private String time;
	// 开始时间
	private long beginTime;
	// 时间版本
	private TimeWorker worker;

	/**
	 * 时间变化版本
	 * 
	 * @param target 可运行程序（编译后程序）保存地址
	 * @param projectPath 项目工程地址
	 * @param time 开始时间
	 * @throws Exception
	 */
	public TimeVersion(String target, String projectPath, String time) throws Exception {
		super();
		this.projectPath = PathUtil.replace(projectPath);
		this.time = time;
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		this.beginTime = f.parse(this.time).getTime();
		worker = new TimeWorker();
	}

	/**
	 * 列出所有变化文件
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<File> listAllChangeFileName() throws Exception {
		return worker.listAllChangeFile(this);
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

}
