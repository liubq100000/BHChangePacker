package cn.bh.jc.diff;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.bh.jc.IListDiffOper;
import cn.bh.jc.common.PathUtil;
import cn.bh.jc.vo.TimeVersion;

/**
 * 变更文件打包工具
 * 时间对比方式
 * 
 * @author liubq
 * @since 2017年12月19日
 */
public class ListDiffByLastModified implements IListDiffOper {

	// 项目目录
	private List<TimeVersion> changeList;

	/**
	 * 构造方法
	 * 
	 * @param projectPath 工程位置
	 * @throws Exception
	 */
	public ListDiffByLastModified(List<TimeVersion> list) throws Exception {
		if (list == null || list.size() <= 0) {
			throw new Exception("参数不正确，异常结束");
		}
		changeList = new ArrayList<TimeVersion>();
		changeList.addAll(list);
	}

	/**
	 * 取得差异文件
	 */
	public List<String> listChangeFile() throws Exception {
		List<String> fileNameList = new ArrayList<String>();
		for (TimeVersion timeVersion : changeList) {
			// 取得变化文件列表
			List<File> changeFileList = timeVersion.listAllChangeFileName();
			File projectFile = new File(timeVersion.getProjectPath());
			// 取得所有文件名称，采用大致匹配方式，不是绝对相对，可能出现多的情况，问题不大
			for (File cf : changeFileList) {
				fileNameList.add(PathUtil.trimName(cf, projectFile));
			}
		}
		return fileNameList;
	}

}
