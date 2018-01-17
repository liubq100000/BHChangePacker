package cn.bh.jc.vo;

/**
 * 变化文件
 * 
 * @author liubq
 * @since 2018年1月4日
 */
public class VSVO {
	private String srcPath;
	private String targetPath;

	public VSVO(String srcPath, String targetPath) {
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
