package cn.bh.view.simple;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.bh.jc.DiffFileLister;
import cn.bh.jc.DiffFilePacker;
import cn.bh.jc.common.SysLog;
import cn.bh.jc.domain.ChangeVO;
import cn.bh.jc.domain.Config;
import cn.bh.jc.version.GitVersion;
import cn.bh.jc.version.vo.GitParaVO;

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
			// 配置
			Config conf = new Config();
			// 排除配置文件
			// conf.addExclusiveFileExt(".properties");
			List<GitVersion> chageList = new ArrayList<GitVersion>();
			chageList.add(new GitVersion(conf, buildStaticVO()));
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

	public static GitParaVO buildStaticVO() {
		String url = "http://liubq@172.16.4.195:10101/r/gsite-basic.git";
		String username = "test";
		String password = "test123456";
		String gitBranch = "develop";
		File dir = new File("C:\\Users\\Administrator\\Desktop\\dir");
		String exeHome = "F:\\wk_zzb\\other\\apache-tomcat-7-site\\webapps\\gsite";
		String startVerison = "98dc9dfb981ca72854d804eba7263ce485039302";
		return new GitParaVO(url, username, password, gitBranch, startVerison, exeHome, dir).setExpName("gsite");
	}

	public static GitParaVO buildStaticVO1() {
		String url = "http://liubq@git.jcinfo.com:7990/scm/jthy/jttsp.git";
		String username = "liubq";
		String password = "Lbq007624";
		String gitBranch = "develop";
		File dir = new File("C:\\Users\\Administrator\\Desktop\\dir");
		String exeHome = "D:\\tomcat-8.5.35.8200\\webapps\\jttsp";
		String startVerison = "5f50b8ae9d8997bbef9453bb8dc40ecb3718c27e";
		return new GitParaVO(url, username, password, gitBranch, startVerison, exeHome, dir);
	}

}
