package cn.bh.jc;

import java.util.ArrayList;
import java.util.List;

import cn.bh.jc.domain.ChangeVO;
import cn.bh.jc.domain.StoreVersion;

/**
 * 变更文件打包工具
 * 
 * @author liubq
 * @since 2017年12月19日
 */
public class ListDiffOper<E extends StoreVersion> {

	// 项目目录
	private List<E> changeList;

	/**
	 * 构造方法
	 * 
	 * @param list
	 * @throws Exception
	 */
	public ListDiffOper(List<E> list) throws Exception {
		if (list == null || list.size() <= 0) {
			throw new Exception("参数不正确，异常结束");
		}
		changeList = new ArrayList<E>();
		changeList.addAll(list);
	}

	/**
	 * 取得差异文件
	 */
	public List<ChangeVO> listChange() throws Exception {
		List<ChangeVO> cList = new ArrayList<ChangeVO>();
		ChangeVO vo;
		for (E version : changeList) {
			vo = version.get();
			if (vo != null && vo.getInfo() != null) {
				cList.add(version.get());
			}
		}
		return cList;
	}
}
