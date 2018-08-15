package cn.bh.view.ui;

public class ConfigInfoVO {
	private String outPosition;
	private String srcPosition;

	private String svnURL1;
	private String svnUserName1;
	private String svnPassword1;
	private String svnStartVersion1;
	private String svnDefaultName1;

	private String svnURL2;
	private String svnUserName2;
	private String svnPassword2;
	private String svnStartVersion2;
	private String svnDefaultName2;

	public String getOutPosition() {
		return nvl(outPosition);
	}

	public void setOutPosition(String outPosition) {
		this.outPosition = outPosition;
	}

	public String getSrcPosition() {
		return nvl(srcPosition);
	}

	public void setSrcPosition(String srcPosition) {
		this.srcPosition = srcPosition;
	}

	public String getSvnURL1() {
		return nvl(svnURL1);
	}

	public void setSvnURL1(String svnURL1) {
		this.svnURL1 = svnURL1;
	}

	public String getSvnUserName1() {
		return nvl(svnUserName1);
	}

	public void setSvnUserName1(String svnUserName1) {
		this.svnUserName1 = svnUserName1;
	}

	public String getSvnPassword1() {
		return nvl(svnPassword1);
	}

	public void setSvnPassword1(String svnPassword1) {
		this.svnPassword1 = svnPassword1;
	}

	public String getSvnStartVersion1() {
		return nvl(svnStartVersion1);
	}

	public void setSvnStartVersion1(String svnStartVersion1) {
		this.svnStartVersion1 = svnStartVersion1;
	}

	public String getSvnDefaultName1() {
		return nvl(svnDefaultName1);
	}

	public void setSvnDefaultName1(String svnDefaultName1) {
		this.svnDefaultName1 = svnDefaultName1;
	}

	public String getSvnURL2() {
		return nvl(svnURL2);
	}

	public void setSvnURL2(String svnURL2) {
		this.svnURL2 = svnURL2;
	}

	public String getSvnUserName2() {
		return nvl(svnUserName2);
	}

	public void setSvnUserName2(String svnUserName2) {
		this.svnUserName2 = svnUserName2;
	}

	public String getSvnPassword2() {
		return nvl(svnPassword2);
	}

	public void setSvnPassword2(String svnPassword2) {
		this.svnPassword2 = svnPassword2;
	}

	public String getSvnStartVersion2() {
		return nvl(svnStartVersion2);
	}

	public void setSvnStartVersion2(String svnStartVersion2) {
		this.svnStartVersion2 = svnStartVersion2;
	}

	public String getSvnDefaultName2() {
		return nvl(svnDefaultName2);
	}

	public void setSvnDefaultName2(String svnDefaultName2) {
		this.svnDefaultName2 = svnDefaultName2;
	}

	private String nvl(String key) {
		if (key == null) {
			return "";
		}
		return key;
	}
}
