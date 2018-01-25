package cn.bh.jc.common;

/**
 * 日志文件
 * 
 * @author liubq
 * @since 2018年1月25日
 */
public class SysLog {
	/**
	 * 日志
	 * 
	 * @param msgs
	 */
	public static void log(String... msgs) {
		if (msgs == null) {
			return;
		}
		// List<String> list = new ArrayList<String>();
		for (String msg : msgs) {
			System.out.println(msg);
			// list.add(msg);
		}
		// appendLog(list);
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
		// List<String> list = new ArrayList<String>();
		// list.add(msg + "\r\n");
		// list.add(e.getMessage() + "\r\n");
		// for (StackTraceElement el : e.getStackTrace()) {
		// list.add(el.toString() + "\r\n");
		// }
		// appendLog(list);
	}

	// /**
	// * 添加日志
	// *
	// * @param list
	// */
	// private static void appendLog(List<String> list) {
	// if (list == null) {
	// return;
	// }
	// for (String el : list) {
	// System.out.println(el);
	// }
	//
	// }
}
