package cn.bh.jc.common;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 日志文件
 * 
 * @author liubq
 * @since 2018年1月25日
 */
public class SysLog {
	// 日志
	private static File logFile;

	// 初始化
	static {
		try {
			logFile = new File(PathUtil.SAVE_PATH + "/log/log_" + System.currentTimeMillis() + ".log");
			logFile.getParentFile().mkdirs();
			logFile.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 日志
	 * 
	 * @param msgs
	 */
	public static void log(String... msgs) {
		if (msgs == null) {
			return;
		}
		List<String> list = new ArrayList<String>();
		for (String msg : msgs) {
			System.out.println(msg);
			list.add(msg + "\r\n");
		}
		appendLog(list);
	}

	/**
	 * 日志
	 * 
	 * @param msgs
	 */
	public static void log(String msg, Throwable e) {
		if (msg != null) {
			System.out.println(msg);
		}
		if (e != null) {
			e.printStackTrace();
		}
		List<String> list = new ArrayList<String>();
		list.add(msg + "\r\n");
		list.add(e.getMessage() + "\r\n");
		for (StackTraceElement el : e.getStackTrace()) {
			list.add(el.toString() + "\r\n");
		}
		appendLog(list);
	}

	/**
	 * 添加日志
	 * 
	 * @param list
	 */
	private static void appendLog(List<String> list) {
		if (list == null) {
			return;
		}
		try {
			SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
			String time = f.format(new Date()) + " ==> ";
			// 记录日志
			FileWriter writer = new FileWriter(logFile, true);
			for (String msg : list) {
				writer.write(time + msg);
			}
			writer.flush();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
