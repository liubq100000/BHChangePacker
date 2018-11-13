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

	public int hashCode() {
		return (srcPath).hashCode();
	}

	public String toString() {
		return srcPath;
	}

	public boolean equals(Object vo) {
		if (vo == null) {
			return false;
		}
		try {
			PathVO newVO = (PathVO) vo;
			return this.getSrcPath().equals(newVO.getSrcPath());
		} catch (Exception e) {
			return false;
		}
	}
}
