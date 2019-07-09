package cn.bh.view.simple;

import java.util.ArrayList;
import java.util.List;

import cn.bh.jc.DiffFileLister;
import cn.bh.jc.DiffFilePacker;
import cn.bh.jc.common.SysLog;
import cn.bh.jc.domain.ChangeVO;
import cn.bh.jc.domain.Config;
import cn.bh.jc.version.GitVersion;

/**
 * SVN方式打包 注意，必须保持本地最新代码，因为要取本地tomcat下编译好的class,js等文件，本项目不能自动编译 resource
 * 会自动跳过，配置文件自己拷贝
 * 
 * @author liubq
 * @since 2017年12月21日
 */
public class PackerV3Main {

	public static void main(String[] args) {
		try {
			// 工程所用的tomcat地址（主要是为了Copy class等文件）
			String exeHome = "F:\\wk_zzb\\other\\apache-tomcat-7-site\\webapps\\gsite";
			// 配置
			Config conf = new Config();
			// 排除配置文件
			// conf.addExclusiveFileExt(".properties");

			List<GitVersion> chageList = new ArrayList<GitVersion>();
			String url = "http://liubq@172.16.4.195:10101/r/gsite-basic.git";
			String username = "test";
			String password = "test123456";
			String gitBranch = "develop";
			chageList.add(new GitVersion(conf, exeHome, url, username, password, gitBranch, "d28db2fa955fa368c900f34880ffdc107f1ec7c2", "gsite-basic"));
			SysLog.log("开始执行请等待。。。。。。  ");
			// 根据版本取得差异文件
			DiffFileLister<GitVersion> oper = new DiffFileLister<GitVersion>(chageList);
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
