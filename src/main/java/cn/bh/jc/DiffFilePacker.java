package cn.bh.jc;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import cn.bh.jc.common.FileCopy;
import cn.bh.jc.common.PathUtil;
import cn.bh.jc.common.SysLog;
import cn.bh.jc.domain.ChangeVO;
import cn.bh.jc.domain.Config;

/**
 * 变更文件打包工具
 * 
 * @author liubq
 * @since 2017年12月19日
 */
public class DiffFilePacker {

	// 保存目录
	private String exportSavePath;
	// 删除文件列表
	private String deleteFile;
	// 默认配置
	private Config conf;

	/**
	 * 变更文件打包工具
	 * 
	 * @param savePath 打包文件保存路径 例如：C:\\Users\\Administrator\\Desktop\\test
	 * @param inConf 默认配置
	 */
	public DiffFilePacker(String savePath, Config inConf) {
		if (savePath == null || savePath.trim().length() == 0) {
			SysLog.log("请输入保存路径");
			throw new RuntimeException("请输入保存路径");
		}
		File saveDir = new File(savePath);
		try {
			saveDir.mkdirs();
			if (!saveDir.isDirectory()) {
				SysLog.log("保存路径必须是目录");
				throw new RuntimeException("保存路径必须是目录");
			}
		} catch (Exception ex) {
			SysLog.log("保存路径创建不成功");
			throw new RuntimeException(ex);
		}
		this.exportSavePath = saveDir.getAbsolutePath() + "/upgrade_" + System.currentTimeMillis();
		File file = new File(exportSavePath);
		file.mkdirs();
		// 配置
		conf = inConf;
	}

	/**
	 * 打包运行
	 * 
	 * @param mapList 文件列表
	 * @return 被打包的文件列表
	 * @throws Exception
	 */
	public List<String> pack(List<ChangeVO> cList) throws Exception {
		// 实际打包的文件
		List<String> actFileList = new ArrayList<String>();
		if (cList == null || cList.size() == 0) {
			return actFileList;
		}
		for (ChangeVO entry : cList) {
			// 取得变更文件对应可执行文件
			File targetFile = new File(entry.getVersion().getTargetPath());
			if (!targetFile.exists()) {
				throw new Exception("文件路径:" + entry.getVersion().getTargetPath() + "不存在");
			}
			// 查询变化文件
			List<File> exeChangeFileList = findChangeFile(targetFile, entry.getInfo().getChangeFiles());
			// 排序
			Collections.sort(exeChangeFileList, new Comparator<File>() {
				public int compare(File o1, File o2) {
					return o1.getAbsolutePath().compareTo(o2.getAbsolutePath());
				}

			});
			// 拷贝文件
			SysLog.log("++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
			int len = targetFile.getAbsolutePath().length() + 1;
			File newFile;
			// 保存的文件前缀
			String saveFilePre = exportSavePath + "/" + entry.getVersion().getExportProjectName() + "/";
			for (File f : exeChangeFileList) {
				// 目录不用拷贝
				if (f.isDirectory()) {
					continue;
				}
				newFile = new File(saveFilePre + f.getAbsolutePath().substring(len));
				if (!newFile.getParentFile().exists()) {
					newFile.mkdirs();
				}
				try {
					FileCopy.copyFile(f, newFile);
					actFileList.add(newFile.getAbsolutePath());
				} catch (Exception e) {
					SysLog.log("文件复制异常", e);
				}
			}
			Set<String> delList = entry.getInfo().getDelSet();
			if (delList != null && delList.size() > 0) {
				SysLog.log("\r\n删除文件列表---------------------------------");
				String delBasePath = "tomcat\\webapp\\";
				File delFile = new File(exportSavePath + "/" + entry.getVersion().getProjectName() + "_删除文件列表.txt");
				deleteFile = delFile.getAbsolutePath();
				StringBuilder delTxt = new StringBuilder();
				String newLine;
				for (String line : delList) {
					line = PathUtil.replace(line);
					newLine = delBasePath + PathUtil.replaceToTargetDir(line, conf);
					newLine = PathUtil.replace(newLine);
					delTxt.append(newLine + "\r\n");
					SysLog.log(newLine);
				}
				Files.write(Paths.get(deleteFile), delTxt.toString().getBytes());
				SysLog.log("删除文件列表---------------------------------");
			}
			SysLog.log("\r\n 项目：" + entry.getVersion().getProjectName() + " 打包完成，打包到地址：" + saveFilePre);
			SysLog.log("++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
		}

		SysLog.log("\r\n打包完成 共打包" + actFileList.size() + "个文件");
		SysLog.log("************************************************************");

		return actFileList;
	}

	/**
	 * 取得变化文件
	 * 
	 * @param targetDir
	 * @param fileNameList
	 * @return
	 */
	private List<File> findChangeFile(File targetDir, List<String> fileNameList) throws Exception {
		String basePath = targetDir.getAbsolutePath();
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
				SysLog.log("文件(" + fileName + ")在目标目录下(" + dir + ")没有找到对应文件，可能该文件已经被删除，跳过，请确认是否正确！");
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
		if (fileName.lastIndexOf("/") >= 0) {
			String dir = fileName.substring(0, fileName.lastIndexOf("/"));
			String allDir = PathUtil.replaceToTargetDir(basePath + "/" + dir, conf);
			return new File(allDir);
		} else {
			String allDir = PathUtil.replaceToTargetDir(basePath + "/" + fileName, conf);
			return new File(allDir);
		}

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
		// 去掉发布源码
		String name = file.getName();
		if (name.endsWith(".java")) {
			return false;
		}
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
