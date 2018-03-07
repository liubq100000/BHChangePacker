package cn.bh.jc;

import java.util.List;
import java.util.Map;

import cn.bh.jc.vo.StoreVersion;

/**
 * 差异文件接口
 * 
 * @author liubq
 * @since 2018年1月4日
 */
public interface IListDiffOper<T extends StoreVersion> {

	/**
	 * 列出所有变化文件
	 * 
	 * @return <变化版本，变化文件列表>
	 * @throws Exception
	 */
	public Map<T, List<String>> listChangeFile() throws Exception;
}
