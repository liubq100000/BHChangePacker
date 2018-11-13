package cn.bh.jc.common;

import java.io.File;

import cn.bh.jc.domain.Config;
import cn.bh.jc.domain.PathVO;

/**
 * 工具类
 * 
 * @author liubq
 * @since 2017年12月20日
 */
public class PathUtil {

	/**
	 * 替换成目录
	 * 
	 * @param path 源目录
	 * @param conf 配置
	 * @return
	 */
	public static String replaceToTargetDir(String path, Config conf) {
		String inPath = replace(path);
		inPath = "/" + inPath + "/";
		for (PathVO entry : conf.relList()) {
			inPath = inPath.replace(entry.getSrcPath(), entry.getTargetPath());
		}
		return replace(inPath);
	}

	/**
	 * 是否需要排除
	 * 
	 * 这样仍然排除不了所有目录 例如：abc/ddd/asd.d这个类型目录
	 * 
	 * @param file
	 * @return
	 */
	public static boolean isExclusive(String projectName, String file, Config conf) {
		// 空对象
		if (file == null) {
			return true;
		}
		// 标准文件路径
		String name = PathUtil.replace(file);
		// 特殊文件排除
		if (name.indexOf(projectName + "/.") >= 0) {
			return true;
		}
		// 指定目录排除
		for (String subPath : conf.exclusiveDirList()) {
			if (name.indexOf(projectName + "/" + subPath) > 5) {
				return true;
			}
		}
		// 指定文件扩展名排除
		for (String fileExt : conf.exclusiveFileExtList()) {
			if (name.endsWith(fileExt)) {
				return true;
			}
		}
		return false;
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
	 * @return 不包含工程名称的相对地址
	 */
	public static String trimName(String svnFile, String projectName) {
		if (svnFile.length() <= projectName.length()) {
			return svnFile;
		}
		String tSvnFile = svnFile.substring(svnFile.indexOf(projectName) + projectName.length());
		return PathUtil.replace(tSvnFile);
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
