package cn.bh.jc.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

/**
 * 文件复制
 * 
 * @author liubq
 */
public class FileCopy {
	/**
	 * 复制单个文件
	 * 
	 * @param oldPath String 原文件路径 如：c:/fqf.txt
	 * @param newPath String 复制后路径 如：f:/fqf.txt
	 * @return boolean
	 * @throws Exception
	 */
	public static void copyFile(String oldPath, String newPath) throws Exception {
		File oldFile = new File(oldPath);
		File newFile = new File(newPath);
		copyFile(oldFile, newFile);
	}

	/**
	 * 复制单个文件
	 * 
	 * @param oldFile 原文件
	 * @param newFile 复制后文件
	 * @return boolean
	 * @throws Exception
	 */
	public static void copyFile(File oldFile, File newFile) throws Exception {
		InputStream inStream = null;
		FileOutputStream fs = null;
		if (!oldFile.exists()) {
			return;
		}
		try {
			if (newFile.exists()) {
				if (!newFile.delete()) {
					System.out.println(newFile.getAbsolutePath() + " 删除失败");
				}
			}
			@SuppressWarnings("unused")
			int byteSum = 0;
			int byteRead = 0;
			// 读入原文件
			inStream = new FileInputStream(oldFile);
			fs = new FileOutputStream(newFile);
			byte[] buffer = new byte[1444];
			while ((byteRead = inStream.read(buffer)) != -1) {
				// 字节数 文件大小
				byteSum += byteRead;
				fs.write(buffer, 0, byteRead);
			}
			fs.flush();
			fs.close();
			System.out.println("复制文件： " + newFile.getAbsolutePath());
		} finally {
			if (inStream != null) {
				inStream.close();
			}
			if (fs != null) {
				fs.close();
			}
		}
	}

}
