package cn.bh.jc.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * 默认配置
 * 
 * @author liubq
 * @since 2018年11月13日
 */
public class Config {
	/**
	 * 初始化化
	 */
	public Config() {
		// 默认排除目录
		addExclusiveDir("target");
		addExclusiveDir("classes");
		// 默认对应关系
		addRelPath("/src/main/java/", "/WEB-INF/classes/");
		addRelPath("/src/main/resources/", "/WEB-INF/classes/");
		addRelPath("/src/main/webapp/", "/");
		addRelPath("/src/java/", "/WEB-INF/classes/");
		addRelPath("/src/webapp/", "/");
		addRelPath("/src/WebRoot/", "/");
		addRelPath("/src/webroot/", "/");
		addRelPath("/src/WebContent/", "/");
		addRelPath("/src/webcontent/", "/");
		addRelPath("/overlays/com.jc.jcap.jcap-static-1.2/", "/");
		addRelPath("/WebRoot/", "/");
		addRelPath("/WebContent/", "/");
		addRelPath("/webroot/", "/");
		addRelPath("/webcontent/", "/");
	}

	// 排除的文件目录
	private List<String> exclusiveDirList = new ArrayList<String>();

	// 排除的文件类型
	private List<String> exclusiveFileExtList = new ArrayList<String>();

	// 目录对应文件
	private List<PathVO> resPathList = new ArrayList<PathVO>();

	/**
	 * 添加目录对应关系
	 * 
	 * @param src 代码目录
	 * @param dest 编译后目录
	 */
	public void addRelPath(String src, String dest) {
		if (src == null || src.trim().length() == 0) {
			return;
		}
		if (dest == null || dest.trim().length() == 0) {
			return;
		}
		resPathList.add(new PathVO(src, dest));
	}

	/**
	 * 移除目录对应关系
	 * 
	 * @param src 代码目录
	 */
	public void removeRelPath(String src) {
		if (src == null || src.trim().length() == 0) {
			return;
		}
		resPathList.remove(new PathVO(src, ""));
	}

	/**
	 * 取得目录对应关系
	 * 
	 * @return 目录对应关系
	 */
	public List<PathVO> relList() {
		return resPathList;
	}

	/**
	 * 添加需要排除的文件目录
	 * 
	 * @param dir 文件目录
	 */
	public void addExclusiveDir(String dir) {
		if (dir == null || dir.trim().length() == 0) {
			return;
		}
		if (!exclusiveDirList.contains(dir)) {
			exclusiveDirList.add(dir);
		}
	}

	/**
	 * 移除需要排除的文件目录
	 * 
	 * @param dir 文件目录
	 */
	public void removeExclusiveDir(String dir) {
		if (dir == null || dir.trim().length() == 0) {
			return;
		}
		exclusiveDirList.remove(dir);
	}

	/**
	 * 取得要排除的文件目录列表
	 * 
	 * @return 文件目录列表
	 */
	public List<String> exclusiveDirList() {
		return exclusiveDirList;
	}

	/**
	 * 添加需要排除的文件扩展名称
	 * 
	 * @param fileExt 文件扩展名称 例如 property
	 */
	public void addExclusiveFileExt(String fileExt) {
		if (fileExt == null || fileExt.trim().length() == 0) {
			return;
		}
		exclusiveFileExtList.add(fileExt);
	}

	/**
	 * 移除需要排除的文件扩展名称
	 * 
	 * @param dir 文件扩展名称
	 */
	public void removeExclusiveFileExt(String fileExt) {
		if (fileExt == null || fileExt.trim().length() == 0) {
			return;
		}
		exclusiveFileExtList.remove(fileExt);
	}

	/**
	 * 取得要排除的文件扩展名称列表
	 * 
	 * @return 文件扩展名称列表
	 */
	public List<String> exclusiveFileExtList() {
		return exclusiveFileExtList;
	}

}
