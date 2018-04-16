package cn.bh;

import java.util.ArrayList;
import java.util.List;

import cn.bh.jc.DiffFilePacker;
import cn.bh.jc.ListDiffOper;
import cn.bh.jc.common.SysLog;
import cn.bh.jc.domain.ChangeVO;
import cn.bh.jc.domain.SVNVersion;

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
			String exeHome = "D:\\tomcat\\webapps\\wsb_forum";
			List<SVNVersion> chageList = new ArrayList<SVNVersion>();
			String url = "svn://172.16.0.155:9999/jcsvn/JC2018-003%E5%90%89%E6%9E%97%E7%9C%81%E5%A4%96%E4%BA%8B%E5%8A%9E%E6%8A%A5%E5%90%8D%E5%8F%8A%E7%BB%9F%E8%AE%A1%E9%A1%B9%E7%9B%AE/01%E5%BC%80%E5%8F%91%E5%9F%9F/03%E7%BC%96%E7%A0%81%E5%AE%9E%E7%8E%B0/wsb_forum";
			String username = "liubq";
			String password = "lbq123456";
			chageList.add(new SVNVersion(exeHome, url, username, password, 193000L));
			SysLog.log("开始执行请等待。。。。。。  ");
			// 根据版本取得差异文件
			ListDiffOper<SVNVersion> oper = new ListDiffOper<SVNVersion>(chageList);
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
