package cn.bh.jc.common;

/**
 * 日志文件
 * 
 * @author liubq
 * @since 2018年1月25日
 */
public class SysLog {

	public static ILoglisten logListen = null;

	/**
	 * 日志
	 * 
	 * @param msgs
	 */
	public static void log(String... msgs) {
		if (msgs == null) {
			return;
		}
		for (String msg : msgs) {
			if (logListen != null) {
				logListen.log(msg);
			} else {
				System.out.println(msg);
			}
		}
	}

	/**
	 * 日志
	 * 
	 * @param msgs
	 */
	public static void log(String msg, Throwable e) {
		if (msg != null) {
			if (logListen != null) {
				logListen.log(msg);
			} else {
				System.out.println(msg);
			}
		}
		if (e != null) {
			if (logListen != null) {
				logListen.log(e.getMessage());
				for (StackTraceElement item : e.getStackTrace()) {
					logListen.log(item.toString());
				}
			} else {
				e.printStackTrace();
			}
		}
	}

}
