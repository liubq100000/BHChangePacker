package cn.bh.jc.domain;

/**
 * 变化文件
 * 
 * @author liubq
 * @since 2018年1月4日
 */
public class PathVO {
	private String srcPath;
	private String targetPath;

	public PathVO(String srcPath, String targetPath) {
		super();
		this.srcPath = srcPath;
		this.targetPath = targetPath;
	}

	public String getSrcPath() {
		return srcPath;
	}

	public String getTargetPath() {
		return targetPath;
	}

}
