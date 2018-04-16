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
		for (String msg : msgs) {
			System.out.println(msg);
		}
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
	}

}
