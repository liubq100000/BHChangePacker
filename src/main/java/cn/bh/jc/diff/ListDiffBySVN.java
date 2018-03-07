package cn.bh.jc.diff;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.bh.jc.IListDiffOper;
import cn.bh.jc.common.PathUtil;
import cn.bh.jc.vo.SVNVersion;

/**
 * 变更文件打包工具
 * 时间对比方式
 * 
 * @author liubq
 * @since 2017年12月19日
 */
public class ListDiffBySVN implements IListDiffOper<SVNVersion> {

	// 项目目录
	private List<SVNVersion> changeList;

	/**
	 * 构造方法
	 * 
	 * @param list
	 * @throws Exception
	 */
	public ListDiffBySVN(List<SVNVersion> list) throws Exception {
		if (list == null || list.size() <= 0) {
			throw new Exception("参数不正确，异常结束");
		}
		changeList = new ArrayList<SVNVersion>();
		changeList.addAll(list);
	}

	/**
	 * 取得差异文件
	 */
	public Map<SVNVersion, List<String>> listChangeFile() throws Exception {
		Map<SVNVersion, List<String>> mapList = new HashMap<SVNVersion, List<String>>();
		for (SVNVersion svnVersion : changeList) {
			List<String> fileList = new ArrayList<String>();
			List<String> files = svnVersion.listAllChangeFileName();
			if (files == null || files.size() == 0) {
				continue;
			}
			for (String svnFileName : files) {
				fileList.add(PathUtil.trimName(svnFileName, svnVersion.getProjectName()));
			}
			mapList.put(svnVersion, fileList);
		}
		return mapList;
	}
}
