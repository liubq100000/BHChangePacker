package cn.bh.jc.common;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.bh.jc.vo.VSVO;

/**
 * 工具类
 * 
 * @author liubq
 * @since 2017年12月20日
 */
public class PathUtil {

	// 排除的文件目录
	private static List<String> exclusiveList = new ArrayList<String>();
	// 初始化排除文件列表
	static {
		exclusiveList.add("src/main/resources");
		exclusiveList.add("target");
	}

	// 目录对应文件
	private static List<VSVO> resPathList = new ArrayList<VSVO>();
	// 初始化目录对应关系
	static {
		resPathList.add(new VSVO("/src/main/java/", "/WEB-INF/classes/"));
		resPathList.add(new VSVO("/src/main/webapp/", "/"));
		resPathList.add(new VSVO("/src/java/", "/WEB-INF/classes/"));
		resPathList.add(new VSVO("/src/webapp/", "/"));
		resPathList.add(new VSVO("/src/", "/WEB-INF/classes/"));
		resPathList.add(new VSVO("/WebRoot/", "/"));
		resPathList.add(new VSVO("/WebContent/", "/"));
	}

	/**
	 * 目录对应文件
	 * 
	 * @return
	 */
	public static String replaceToTomcatDir(String path) {
		String inPath = replace(path);
		inPath = "/" + inPath + "/";
		for (VSVO entry : resPathList) {
			inPath = inPath.replace(entry.getSrcPath(), entry.getTargetPath());
		}
		return replace(inPath);
	}

	/**
	 * 取得排除的文件列表
	 * 
	 * @return
	 */
	public static List<String> getExclusiveList() {
		return exclusiveList;
	}

	/**
	 * 替换
	 * 
	 * @param path
	 * @return
	 */
	public static String replace(String path) {
		if (path == null || path.trim().length() == 0) {
			return "";
		}
		String res = path;
		while (res.indexOf("\\") >= 0) {
			res = res.replace("\\", "/");
		}
		while (res.indexOf("//") >= 0) {
			res = res.replace("//", "/");
		}
		while (res.endsWith("/")) {
			res = res.substring(0, res.length() - 1);
		}
		while (res.startsWith("/")) {
			res = res.substring(1);
		}
		return res;
	}

	/**
	 * svn方式取得名称
	 * 
	 * @param svnFile
	 * @param projectName
	 * @return
	 */
	public static String trimName(String svnFile, String projectName) {
		if (svnFile.length() <= projectName.length()) {
			return svnFile;
		}
		String tSvnFile = svnFile.substring(svnFile.indexOf(projectName));
		return PathUtil.replace(tSvnFile);
	}

	/**
	 * 剪切出具体名称
	 * 
	 * @param file
	 * @param baseFile
	 * @return
	 */
	public static String trimName(File file, File baseFile) {
		String baseFilePath = PathUtil.replace(baseFile.getParentFile().getAbsolutePath());
		String nowFilePath = PathUtil.replace(file.getAbsolutePath());
		return nowFilePath.substring(baseFilePath.length() + 1);
	}

	/**
	 * java 和 class 需要忽略扩展名
	 * 
	 * @param file
	 * @return
	 */
	public static boolean ignoreEx(File file) {
		return ignoreEx(file.getName());
	}

	/**
	 * java 和 class 需要忽略扩展名
	 * 
	 * @param file
	 * @return
	 */
	public static boolean ignoreEx(String fileName) {
		String name = fileName;
		if (name.lastIndexOf(".") > 0) {
			String type = name.substring(name.lastIndexOf(".") + 1);
			if ("java".equalsIgnoreCase(type)) {
				return true;
			}
			if ("class".equalsIgnoreCase(type)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 取得去掉扩展名后的名称
	 * 
	 * @param file
	 * @return
	 */
	public static String trimEx(String fileName) {
		String name = fileName;
		if (name.lastIndexOf(".") > 0) {
			name = name.substring(0, name.lastIndexOf("."));
		}
		return name;
	}

}
