package cn.bh;

import java.util.ArrayList;
import java.util.List;

import cn.bh.jc.DiffFilePacker;
import cn.bh.jc.IListDiffOper;
import cn.bh.jc.common.SysLog;
import cn.bh.jc.diff.ListDiffByLastModified;
import cn.bh.jc.vo.TimeVersion;

/**
 * 最后修改时间比较方式打包
 * 注意，必须保持本地最新代码，因为要取本地tomcat下编译好的class,js等文件，本项目不能自动编译
 * resource 会自动跳过，配置文件自己拷贝
 * 
 * @author liubq
 * @since 2017年12月21日
 */
public class PackerV1Main {

	public static void main(String[] args) {
		try {
			// 工程所用的tomcat地址（主要是为了Copy class等文件）
			String projectTomcat = "D:\\tomcat\\webapps\\con_assets1";
			// 工程地址
			List<TimeVersion> changeList = new ArrayList<TimeVersion>();
			changeList.add(new TimeVersion("X:\\workspace\\con_assets", "2017-12-20 23:59:59"));
			SysLog.log(" 开始支持请等待   ");
			// 根据版本取得差异文件
			IListDiffOper oper = new ListDiffByLastModified(changeList);
			List<String> list = oper.listChangeFile();
			for (String line : list) {
				SysLog.log(line);
			}
			// 打包
			DiffFilePacker p = new DiffFilePacker(projectTomcat);
			p.pack(list);

		} catch (Exception e) {
			SysLog.log("异常", e);
		}
	}

}
