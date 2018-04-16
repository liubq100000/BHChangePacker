package cn.bh.jc.domain;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 变化集合
 * 
 * @author liubq
 * @since 2018年4月13日
 */
public class ChangeInfo {
	// 变化文件列表
	private List<String> changeFiles;

	// 删除文件目录
	private Set<String> delSet = new HashSet<String>();

	/**
	 * 变化文件列表
	 * 
	 * @return 变化文件列表
	 */
	public List<String> getChangeFiles() {
		return changeFiles;
	}

	/**
	 * 变化文件列表
	 * 
	 * @param changeFiles 变化文件列表
	 */
	public void setChangeFiles(List<String> changeFiles) {
		this.changeFiles = changeFiles;
	}

	/**
	 * 删除文件目录
	 * 
	 * @return 删除文件目录
	 */
	public Set<String> getDelSet() {
		return delSet;
	}

	/**
	 * 删除文件目录
	 * 
	 * @param delSet 删除文件目录
	 */
	public void setDelSet(Set<String> delSet) {
		this.delSet = delSet;
	}

}
