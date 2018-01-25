package cn.history;

import java.util.ArrayList;
import java.util.List;

import cn.bh.jc.DiffFilePacker;
import cn.bh.jc.IListDiffOper;
import cn.bh.jc.common.SysLog;
import cn.bh.jc.diff.ListDiffBySVN;
import cn.bh.jc.vo.SVNVersion;

public class PackConAssetsV2 {

	public static void main(String[] args) {
		try {
			// 工程所用的tomcat地址（主要是为了Copy class等文件）
			String projectTomcat = "D:\\tomcat\\webapps\\con_assets";
			List<SVNVersion> chageList = new ArrayList<SVNVersion>();
			String url = "svn://172.16.0.155:9999/jcsvn/%E5%90%89%E6%9E%97%E7%9C%81%E9%A3%9F%E5%93%81%E8%8D%AF%E5%93%81%E7%9B%91%E7%9D%A3%E7%AE%A1%E7%90%86%E5%B1%80%E9%A1%B9%E7%9B%AE/%E8%8D%AF%E6%A3%80%E6%89%80%E9%A1%B9%E7%9B%AE/%E5%BC%80%E5%8F%91%E7%9B%B8%E5%85%B3/%E8%B5%84%E4%BA%A7%E7%AE%A1%E7%90%86/%E6%BA%90%E4%BB%A3%E7%A0%81/con_assets";
			String username = "liubq";
			String password = "lbq123456";
			chageList.add(new SVNVersion(url, username, password, 185460L));
			SysLog.log(" 开始执行请等待。。。。。。  ");
			// 根据版本取得差异文件
			IListDiffOper oper = new ListDiffBySVN(chageList);
			List<String> list = oper.listChangeFile();
			int size = list == null ? 0 : list.size();
			if (size == 0) {
				SysLog.log("取得变化文件个数：" + size);
			}
			SysLog.log("取得变化文件 size=" + size);
			// 打包
			DiffFilePacker p = new DiffFilePacker(projectTomcat);
			p.pack(list);
		} catch (Exception e) {
			SysLog.log("异常", e);
		}
	}
}
