package cn.bh.view.ui;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileUtil {
	public static final String BASE_DIR = "c:\\jcCode\\config";
	public static final String SPLIT_CHAR = "##";
	public static final String NULL_STR = "@@";

	public static String tempName;

	public static String save(String fileName, ConfigInfoVO data) throws Exception {
		FileUtil.tempName = fileName;
		String dataStr = FileUtil.buildStr(data);
		File file = new File(BASE_DIR + "\\" + fileName + ".bh");
		if (file.exists()) {
			file.delete();
		} else {
			file.getParentFile().mkdirs();
		}
		file.createNewFile();
		Files.write(Paths.get(file.getAbsolutePath()), dataStr.getBytes());
		return file.getAbsolutePath();
	}

	public static ConfigInfoVO read(File saveFile) throws Exception {
		if (saveFile == null) {
			return new ConfigInfoVO();
		}
		FileUtil.tempName = saveFile.getName();
		int pos = FileUtil.tempName.indexOf(".");
		if (pos > 0) {
			FileUtil.tempName = FileUtil.tempName.substring(0, pos);
		}
		byte[] datas = Files.readAllBytes(Paths.get(saveFile.getAbsolutePath()));
		String dataStr = new String(datas);
		return convert(dataStr);
	}

	public static ConfigInfoVO convert(String dataStr) {
		if (dataStr == null) {
			return new ConfigInfoVO();
		}
		String[] dataArr = dataStr.split(SPLIT_CHAR);
		ConfigInfoVO data = new ConfigInfoVO();
		data.setOutPosition(fillNvl(dataArr[0]));
		data.setSrcPosition(fillNvl(dataArr[1]));
		data.setSvnURL1(fillNvl(dataArr[2]));
		data.setSvnUserName1(fillNvl(dataArr[3]));
		data.setSvnPassword1(fillNvl(dataArr[4]));
		data.setSvnStartVersion1(fillNvl(dataArr[5]));
		data.setSvnDefaultName1(fillNvl(dataArr[6]));
		data.setSvnURL2(fillNvl(dataArr[7]));
		data.setSvnUserName2(fillNvl(dataArr[8]));
		data.setSvnPassword2(fillNvl(dataArr[9]));
		data.setSvnStartVersion2(fillNvl(dataArr[10]));
		data.setSvnDefaultName2(fillNvl(dataArr[11]));
		return data;
	}

	public static String buildStr(ConfigInfoVO data) {
		String splitChar = SPLIT_CHAR;
		StringBuilder s = new StringBuilder();
		s.append(buildNvl(data.getOutPosition())).append(splitChar);
		s.append(buildNvl(data.getSrcPosition())).append(splitChar);
		s.append(buildNvl(data.getSvnURL1())).append(splitChar);
		s.append(buildNvl(data.getSvnUserName1())).append(splitChar);
		s.append(buildNvl(data.getSvnPassword1())).append(splitChar);
		s.append(buildNvl(data.getSvnStartVersion1())).append(splitChar);
		s.append(buildNvl(data.getSvnDefaultName1())).append(splitChar);
		s.append(buildNvl(data.getSvnURL2())).append(splitChar);
		s.append(buildNvl(data.getSvnUserName2())).append(splitChar);
		s.append(buildNvl(data.getSvnPassword2())).append(splitChar);
		s.append(buildNvl(data.getSvnStartVersion2())).append(splitChar);
		s.append(buildNvl(data.getSvnDefaultName2()));
		return s.toString();
	}

	private static String fillNvl(String key) {
		if (NULL_STR.equals(key)) {
			return "";
		}
		return key.trim();
	}

	private static String buildNvl(String key) {
		if (key == null || key.trim().length() <= 0) {
			return NULL_STR;
		}
		return key.trim();
	}
}
