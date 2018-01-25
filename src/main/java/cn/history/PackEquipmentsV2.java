package cn.history;

import java.util.ArrayList;
import java.util.List;

import cn.bh.jc.DiffFilePacker;
import cn.bh.jc.IListDiffOper;
import cn.bh.jc.common.SysLog;
import cn.bh.jc.diff.ListDiffBySVN;
import cn.bh.jc.vo.SVNVersion;

public class PackEquipmentsV2 {

	public static void main(String[] args) {
		try {
			// 导出文件保存目录
			String exportSavePath = "C:\\Users\\Administrator\\Desktop";
			// 工程所用的tomcat地址（主要是为了Copy class等文件）
			String projectTomcat = "D:\\tomcat7575\\webapps\\equipmentsys";
			List<SVNVersion> chageList = new ArrayList<SVNVersion>();
			String url = "svn://172.16.0.155:9999/jcsvn/%E5%90%89%E6%9E%97%E7%9C%81%E6%9C%BA%E8%A6%81%E5%B1%80%E9%A1%B9%E7%9B%AE/%E8%A1%8C%E6%94%BF%E5%8A%9E%E5%85%AC%E3%80%81%E5%A4%84%E5%AE%A4%E5%B9%B3%E5%8F%B0/code/equipmentsys";
			String username = "liubq";
			String password = "lbq123456";
			chageList.add(new SVNVersion(url, username, password, 171118L));
			SysLog.log(" 开始执行请等待。。。。。。  ");
			// 根据版本取得差异文件
			IListDiffOper oper = new ListDiffBySVN(chageList);
			List<String> list = oper.listChangeFile();
			int size = list == null ? 0 : list.size();
			if (size == 0) {
				SysLog.log("取得变化文件个数：" + size);
			}
			SysLog.log("******************************************************");
			// 打包
			DiffFilePacker p = new DiffFilePacker(projectTomcat, exportSavePath);
			p.pack(list);
		} catch (Exception e) {
			SysLog.log("异常", e);
		}
	}
}
