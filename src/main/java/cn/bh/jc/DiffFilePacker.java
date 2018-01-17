package cn.bh.jc;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import cn.bh.jc.common.FileCopy;
import cn.bh.jc.common.PathUtil;

/**
 * 变更文件打包工具
 * 
 * @author liubq
 * @since 2017年12月19日
 */
public class DiffFilePacker {

	// 保存目录
	private String exportSavePath;

	// 配置
	private String projectTomcat;

	/**
	 * 变更文件打包工具
	 * 
	 * @param inProjectTomcat tomcat下工程路径 例如：D:\tomcat7575\webapps\equipmentsys
	 * @param path 打包文件保存路径 例如：C:\\Users\\Administrator\\Desktop\\test
	 */
	public DiffFilePacker(String inProjectTomcat, String inPath) {
		this.projectTomcat = PathUtil.replace(inProjectTomcat);
		this.exportSavePath = PathUtil.replace(inPath) + "/" + "upgrade_" + System.currentTimeMillis();
		File file = new File(exportSavePath);
		file.mkdirs();
	}

	/**
	 * 运行
	 * 
	 * @param fileNameList 打包文件列表
	 */
	public void pack(List<String> fileNameList) throws Exception {
		if (fileNameList == null || fileNameList.size() == 0) {
			System.out.println("打包失败，没有找到变更文件");
			return;
		}
		// 取得变更文件对应可执行文件
		File tomcatProjectDir = new File(projectTomcat);
		List<File> exeChangeFileList = findChangeFile(tomcatProjectDir, fileNameList);
		// 排序
		Collections.sort(exeChangeFileList, new Comparator<File>() {
			public int compare(File o1, File o2) {
				return o1.getAbsolutePath().compareTo(o2.getAbsolutePath());
			}

		});
		// 拷贝文件
		System.out.println("\r\n************************************************************");
		System.out.println("开始复制");
		int len = tomcatProjectDir.getParentFile().getAbsolutePath().length() + 1;
		File newFile;
		int count = 0;
		for (File f : exeChangeFileList) {
			newFile = new File(exportSavePath + "/" + f.getAbsolutePath().substring(len));
			if (!newFile.getParentFile().exists()) {
				newFile.mkdirs();
			}
			try {
				FileCopy.copyFile(f, newFile);
				count++;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		System.out.println("打包完成 共打包" + count + "个文件");
		System.out.println("************************************************************");
	}

	/**
	 * 取得变化文件
	 * 
	 * @param tomcatProjectDir
	 * @param fileNameList
	 * @return
	 */
	private List<File> findChangeFile(File tomcatProjectDir, List<String> fileNameList) throws Exception {
		String basePath = tomcatProjectDir.getAbsolutePath();
		File dir;
		List<File> exeChangeFileList = new ArrayList<File>();
		boolean find = false;
		for (String fileName : fileNameList) {
			find = false;
			dir = getChangeFileDir(basePath, fileName);
			if (dir.exists()) {
				for (File tFile : dir.listFiles()) {
					if (isEquals(tFile, fileName)) {
						find = true;
						exeChangeFileList.add(tFile);
					}
				}
			}
			if (!find) {
				System.out.print("文件(" + fileName + ")该在tomcat下(" + dir + ")没有找到对应文件，可能该文件已经被删除，跳过，请确认是否正确！");
			}
		}
		return exeChangeFileList;
	}

	/**
	 * 取得变化文件所在目录
	 * 
	 * @param basePath
	 * @param fileName
	 * @return
	 */
	private File getChangeFileDir(String basePath, String fileName) {
		fileName = PathUtil.replace(fileName);
		String dir = fileName.substring(fileName.indexOf("/"), fileName.lastIndexOf("/"));
		String allDir = PathUtil.replaceToTomcatDir(basePath + "/" + dir);
		return new File(allDir);
	}

	/**
	 * 判断是否相等
	 * 
	 * @param file 待对比文件名
	 * @param inFileName 变化文件名
	 * @return
	 */
	private boolean isEquals(File file, String inPathName) {
		if (!file.exists()) {
			return false;
		}
		String inFileName = inPathName.substring(inPathName.lastIndexOf("/") + 1);
		String inName = inFileName;
		boolean equals = true;
		// java类变化
		if (PathUtil.ignoreEx(inFileName)) {
			inName = PathUtil.trimEx(inFileName);
			equals = false;
		}
		String name = file.getName();
		if (!equals) {
			if (name.startsWith(inName)) {
				return true;
			}
		} else {
			if (name.equalsIgnoreCase(inName)) {
				return true;
			}
		}
		return false;
	}

}
