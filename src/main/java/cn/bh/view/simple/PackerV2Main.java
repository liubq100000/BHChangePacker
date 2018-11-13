package cn.bh.view.simple;

import java.util.ArrayList;
import java.util.List;

import cn.bh.jc.DiffFileLister;
import cn.bh.jc.DiffFilePacker;
import cn.bh.jc.common.SysLog;
import cn.bh.jc.domain.ChangeVO;
import cn.bh.jc.domain.Config;
import cn.bh.jc.version.SVNVersion;

/**
 * SVN方式打包 注意，必须保持本地最新代码，因为要取本地tomcat下编译好的class,js等文件，本项目不能自动编译 resource
 * 会自动跳过，配置文件自己拷贝
 * 
 * @author liubq
 * @since 2017年12月21日
 */
public class PackerV2Main {

	public static void main(String[] args) {
		try {
			// 工程所用的tomcat地址（主要是为了Copy class等文件）
			String exeHome = "D:\\tomcat\\webapps\\ROOT";
			// 配置
			Config conf = new Config();
			// 排除配置文件
			// conf.addExclusiveFileExt(".properties");

			List<SVNVersion> chageList = new ArrayList<SVNVersion>();
			String url = "svn://172.16.0.155:9999/jcsvn/JC2018-045-ZW%E7%8E%AF%E4%BF%9D%E7%9D%A3%E6%9F%A5%E4%BF%A1%E8%AE%BF%E6%A1%88%E4%BB%B6%E7%BB%9F%E8%AE%A1%E5%B9%B3%E5%8F%B0/00SRC/xf_statis";
			String username = "liubq";
			String password = "lbq123456";
			chageList.add(new SVNVersion(conf, exeHome, url, username, password, 224421L, "ROOT"));
			SysLog.log("开始执行请等待。。。。。。  ");
			// 根据版本取得差异文件
			DiffFileLister<SVNVersion> oper = new DiffFileLister<SVNVersion>(chageList);
			List<ChangeVO> list = oper.listChange();
			// 打包
			DiffFilePacker p = new DiffFilePacker("C:/Users/Administrator/Desktop", conf);
			p.pack(list);
			SysLog.log("\r\n 处理完成 。。。。。。    ");
		} catch (Exception e) {
			SysLog.log("异常", e);
		}
	}
}
