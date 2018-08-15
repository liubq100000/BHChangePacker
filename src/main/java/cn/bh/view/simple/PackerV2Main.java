package cn.bh.view.simple;

import java.util.ArrayList;
import java.util.List;

import cn.bh.jc.DiffFileLister;
import cn.bh.jc.DiffFilePacker;
import cn.bh.jc.common.SysLog;
import cn.bh.jc.domain.ChangeVO;
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
			String exeHome = "D:\\tomcat\\webapps\\jtt";
			List<SVNVersion> chageList = new ArrayList<SVNVersion>();
			String url = "svn://######:9999/jcsvn/####/jtt1";
			String username = "useraaa";
			String password = "pwdbbbb";
			chageList.add(new SVNVersion(exeHome, url, username, password, 207640L, "ROOT"));
			String url1 = "svn://######:9999/jcsvn/####/jtt2";
			String username1 = "useraaa";
			String password1 = "pwdbbbb";
			chageList.add(new SVNVersion(exeHome, url1, username1, password1, 207640L, "ROOT"));
			SysLog.log("开始执行请等待。。。。。。  ");
			// 根据版本取得差异文件
			DiffFileLister<SVNVersion> oper = new DiffFileLister<SVNVersion>(chageList);
			List<ChangeVO> list = oper.listChange();
			// 打包
			DiffFilePacker p = new DiffFilePacker("C:/Users/Administrator/Desktop");
			p.pack(list);
			SysLog.log("\r\n 处理完成 。。。。。。    ");
		} catch (Exception e) {
			SysLog.log("异常", e);
		}
	}
}
