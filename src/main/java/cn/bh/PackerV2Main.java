package cn.bh;

import java.util.ArrayList;
import java.util.List;

import cn.bh.jc.DiffFileLister;
import cn.bh.jc.DiffFilePacker;
import cn.bh.jc.common.SysLog;
import cn.bh.jc.domain.ChangeVO;
import cn.bh.jc.version.SVNVersion;

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
			// 工程所用的tomcat地址（主要是为了Copy class等文件）
			String exeHome = "D:\\tomcat\\webapps\\jttsp";
			List<SVNVersion> chageList = new ArrayList<SVNVersion>();
			String url = "svn://172.16.0.155:9999/jcsvn/%E8%A1%8C%E6%94%BF%E5%AE%A1%E6%89%B9%E5%B9%B3%E5%8F%B0/%E4%BA%A4%E9%80%9A%E5%8E%85%E8%A1%8C%E6%94%BF%E5%AE%A1%E6%89%B9%E6%96%B0%E7%89%88/jttsp";
			String username = "liubq";
			String password = "lbq123456";
			chageList.add(new SVNVersion(exeHome, url, username, password, 197633L));
			String url1 = "svn://172.16.0.155:9999/jcsvn/%E8%A1%8C%E6%94%BF%E5%AE%A1%E6%89%B9%E5%B9%B3%E5%8F%B0/%E4%BA%A4%E9%80%9A%E5%8E%85%E8%A1%8C%E6%94%BF%E5%AE%A1%E6%89%B9%E6%96%B0%E7%89%88/xzsp-base";
			String username1 = "liubq";
			String password1 = "lbq123456";
			chageList.add(new SVNVersion(exeHome, url1, username1, password1, 197633L));

			SysLog.log("开始执行请等待。。。。。。  ");
			// 根据版本取得差异文件
			DiffFileLister<SVNVersion> oper = new DiffFileLister<SVNVersion>(chageList);
			List<ChangeVO> list = oper.listChange();
			// 打包
			DiffFilePacker p = new DiffFilePacker();
			p.pack(list);

			SysLog.log("\r\n 处理完成 。。。。。。    ");
		} catch (Exception e) {
			SysLog.log("异常", e);
		}
	}
}
