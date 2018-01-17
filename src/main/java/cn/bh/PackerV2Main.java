package cn.bh;

import java.util.ArrayList;
import java.util.List;

import cn.bh.jc.DiffFilePacker;
import cn.bh.jc.IListDiffOper;
import cn.bh.jc.diff.ListDiffBySVN;
import cn.bh.jc.vo.SVNVersion;

/**
 * SVN方式打包
 * 注意，必须保持本地最新代码，因为要取本地tomcat下编译好的class,js等文件，本项目不能自动编译
 * resource 会自动跳过，配置文件自己拷贝
 * 
 * @author liubq
 * @since 2017年12月21日
 */
public class PackerV2Main {

	public static void main(String[] args) {
		try {
			// 导出文件保存目录
			String exportSavePath = "C:\\Users\\Administrator\\Desktop\\test";
			// 工程所用的tomcat地址（主要是为了Copy class等文件）
			String projectTomcat = "D:\\tomcat\\webapps\\tscls";
			List<SVNVersion> chageList = new ArrayList<SVNVersion>();
			String url = "svn://172.16.0.155:9999/jcsvn/%E8%A1%8C%E6%94%BF%E5%AE%A1%E6%89%B9%E5%B9%B3%E5%8F%B0/%E4%BA%A4%E9%80%9A%E5%8E%85%E8%A1%8C%E6%94%BF%E5%AE%A1%E6%89%B9%E6%96%B0%E7%89%88/tscls";
			String username = "liubq";
			String password = "lbq123456";
			chageList.add(new SVNVersion(url, username, password, 183697L));
			System.out.println(" 开始执行请等待。。。。。。  ");
			// 根据版本取得差异文件
			IListDiffOper oper = new ListDiffBySVN(chageList);
			List<String> list = oper.listChangeFile();
			int size = list == null ? 0 : list.size();
			if (size == 0) {
				System.out.println("取得变化文件个数：" + size);
			}
			// System.out.println("************************************************************");
			// for (String line : list) {
			// System.out.println(line);
			// }
			System.out.println("取得变化文件 size=" + size);
			// 打包
			DiffFilePacker p = new DiffFilePacker(projectTomcat, exportSavePath);
			p.pack(list);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
