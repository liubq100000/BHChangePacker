package cn.bh.view.ui;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.filechooser.FileNameExtensionFilter;

import cn.bh.jc.DiffFileLister;
import cn.bh.jc.DiffFilePacker;
import cn.bh.jc.common.ILoglisten;
import cn.bh.jc.common.SysLog;
import cn.bh.jc.domain.ChangeVO;
import cn.bh.jc.version.SVNVersion;

public class PackerEntry extends JFrame implements ILoglisten {

	private static final long serialVersionUID = 1L;

	private JTextField outPosition;
	private JTextField srcPosition;
	private JTextField svnURL1;
	private JTextField svnUserName1;
	private JTextField svnPassword1;
	private JTextField svnStartVersion1;
	private JTextField svnDefaultName1;

	private JTextField svnURL2;
	private JTextField svnUserName2;
	private JTextField svnPassword2;
	private JTextField svnStartVersion2;
	private JTextField svnDefaultName2;
	private JTextArea log;
	private JFrame jf;

	// 定义一个类继承JFrame类
	public void CreateJFrame(String title) {
		SysLog.logListen = this;
		jf = new JFrame(title);
		// 实例化一个JFrame对象
		Container container = jf.getContentPane();
		// 获取一个容器
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(null);
		container.add(mainPanel);
		// 设置容器的背景颜色
		// 使窗体可视
		jf.setVisible(true);
		jf.setSize(UIVO.PAGE_W, UIVO.PAGE_H); // 设置窗体大小
		jf.setLocation(UIVO.LOCATION_X, UIVO.LOCATION_Y);
		// 设置窗体关闭方式
		jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		// 菜单
		int y = UIVO.P_T;

		y = menuDis(mainPanel, y);
		// 位置
		y = outAndsrcAddressDis(mainPanel, y + 10);

		y = svnTitleDis(mainPanel, y + UIVO.R_INTERVAL - 5);

		// svn1
		y = svn1Dis(mainPanel, y + UIVO.R_INTERVAL - 10);

		// svn2
		y = svn2Dis(mainPanel, y + UIVO.R_INTERVAL);

		// 日志
		log = new JTextArea();
		log.setEditable(false);
		JScrollPane jsp = new JScrollPane(log);
		jsp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		jsp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		jsp.setLocation(UIVO.P_L, y + UIVO.R_INTERVAL);
		jsp.setSize(1150, 300);
		mainPanel.add(jsp);

		mainPanel.setSize(UIVO.PAGE_W, UIVO.PAGE_H);
	}

	private int svn2Dis(JPanel mainPanel, int y) {
		JLabel svn2 = new JLabel("2");
		svn2.setLocation(UIVO.P_L - 15, y - 2);
		svn2.setSize(10, UIVO.R_H);
		mainPanel.add(svn2);
		svnURL2 = new JTextField();
		svnURL2.setLocation(UIVO.P_L, y);
		svnURL2.setSize(550, UIVO.R_H);
		mainPanel.add(svnURL2);
		svnUserName2 = new JTextField();
		svnUserName2.setLocation(650, y);
		svnUserName2.setSize(100, UIVO.R_H);
		mainPanel.add(svnUserName2);
		svnPassword2 = new JTextField();
		svnPassword2.setLocation(800, y);
		svnPassword2.setSize(100, UIVO.R_H);
		mainPanel.add(svnPassword2);
		svnStartVersion2 = new JTextField();
		svnStartVersion2.setLocation(950, y);
		svnStartVersion2.setSize(100, UIVO.R_H);
		mainPanel.add(svnStartVersion2);
		svnDefaultName2 = new JTextField();
		svnDefaultName2.setLocation(1100, y);
		svnDefaultName2.setSize(100, UIVO.R_H);
		mainPanel.add(svnDefaultName2);
		return y + UIVO.R_H;
	}

	private int svn1Dis(JPanel mainPanel, int y) {
		JLabel svn1 = new JLabel("1");
		svn1.setLocation(UIVO.P_L - 15, y - 2);
		svn1.setSize(10, UIVO.R_H);
		mainPanel.add(svn1);
		svnURL1 = new JTextField();
		svnURL1.setLocation(UIVO.P_L, y);
		svnURL1.setSize(550, UIVO.R_H);
		mainPanel.add(svnURL1);
		svnUserName1 = new JTextField();
		svnUserName1.setLocation(650, y);
		svnUserName1.setSize(100, UIVO.R_H);
		mainPanel.add(svnUserName1);
		svnPassword1 = new JTextField();
		svnPassword1.setLocation(800, y);
		svnPassword1.setSize(100, UIVO.R_H);
		mainPanel.add(svnPassword1);
		svnStartVersion1 = new JTextField();
		svnStartVersion1.setLocation(950, y);
		svnStartVersion1.setSize(100, UIVO.R_H);
		mainPanel.add(svnStartVersion1);
		svnDefaultName1 = new JTextField();
		svnDefaultName1.setLocation(1100, y);
		svnDefaultName1.setSize(100, UIVO.R_H);
		mainPanel.add(svnDefaultName1);
		return y + UIVO.R_H;
	}

	private int svnTitleDis(JPanel mainPanel, int y) {
		JLabel svnURL0 = new JLabel("SVN地址");
		svnURL0.setLocation(UIVO.P_L, y);
		svnURL0.setSize(550, UIVO.R_H);
		mainPanel.add(svnURL0);
		JLabel svnUserName0 = new JLabel("用户名称");
		svnUserName0.setLocation(650, y);
		svnUserName0.setSize(100, UIVO.R_H);
		mainPanel.add(svnUserName0);
		JLabel svnPassword0 = new JLabel("用户密码");
		svnPassword0.setLocation(800, y);
		svnPassword0.setSize(100, UIVO.R_H);
		mainPanel.add(svnPassword0);
		JLabel svnStartVersion0 = new JLabel("开始版本号");
		svnStartVersion0.setLocation(950, y);
		svnStartVersion0.setSize(100, UIVO.R_H);
		mainPanel.add(svnStartVersion0);
		JLabel svnDefaultName0 = new JLabel("默认名称");
		svnDefaultName0.setLocation(1100, y);
		svnDefaultName0.setSize(100, UIVO.R_H);
		mainPanel.add(svnDefaultName0);
		return y + UIVO.R_H;
	}

	/**
	 * 地址
	 * 
	 * @param mainPanel
	 * @param y
	 * @return
	 */
	private int outAndsrcAddressDis(JPanel mainPanel, int y) {
		// 导出位置
		JLabel outPositionLabel = new JLabel("请选择导出代码保存地址");
		outPositionLabel.setLocation(UIVO.P_L, y);
		outPositionLabel.setSize(200, UIVO.R_H);
		mainPanel.add(outPositionLabel);
		outPosition = new JTextField();
		outPosition.setLocation(UIVO.P_L, y + UIVO.R_H);
		outPosition.setSize(440, UIVO.R_H);
		outPosition.setEnabled(false);
		JButton outPositionBtn = new JButton("选择地址");
		outPositionBtn.setLocation(500, y + UIVO.R_H);
		outPositionBtn.setSize(100, UIVO.R_H);

		outPositionBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser jfc = new JFileChooser();
				jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				jfc.showDialog(new JLabel(), "选择");
				File file = jfc.getSelectedFile();
				if (file != null) {
					outPosition.setText(file.getAbsolutePath());
				}

			}
		});
		mainPanel.add(outPositionBtn);
		mainPanel.add(outPosition);

		// 编译后文件位置
		JLabel srcPositionLabel = new JLabel("请选择编译后的工程地址");
		srcPositionLabel.setLocation(650, y);
		srcPositionLabel.setSize(200, UIVO.R_H);
		mainPanel.add(srcPositionLabel);
		srcPosition = new JTextField();
		srcPosition.setLocation(650, y + UIVO.R_H);
		srcPosition.setSize(440, UIVO.R_H);
		srcPosition.setEnabled(false);
		JButton srcPositionBtn = new JButton("选择目录");
		srcPositionBtn.setLocation(1100, y + UIVO.R_H);
		srcPositionBtn.setSize(100, UIVO.R_H);

		srcPositionBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser jfc = new JFileChooser();
				jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				jfc.showDialog(new JLabel(), "选择");
				File file = jfc.getSelectedFile();
				if (file != null) {
					srcPosition.setText(file.getAbsolutePath());
				}
			}

		});
		mainPanel.add(srcPositionBtn);
		mainPanel.add(srcPosition);

		return y + UIVO.R_H * 2;
	}

	private ConfigInfoVO getInfo() {
		ConfigInfoVO data = new ConfigInfoVO();
		data.setOutPosition(outPosition.getText());
		data.setSrcPosition(srcPosition.getText());
		data.setSvnURL1(svnURL1.getText());
		data.setSvnUserName1(svnUserName1.getText());
		data.setSvnPassword1(svnPassword1.getText());
		data.setSvnStartVersion1(svnStartVersion1.getText());
		data.setSvnDefaultName1(svnDefaultName1.getText());
		data.setSvnURL2(svnURL2.getText());
		data.setSvnUserName2(svnUserName2.getText());
		data.setSvnPassword2(svnPassword2.getText());
		data.setSvnStartVersion2(svnStartVersion2.getText());
		data.setSvnDefaultName2(svnDefaultName2.getText());
		return data;
	}

	private void setInfo(ConfigInfoVO data) {
		if (data == null) {
			return;
		}
		outPosition.setText(data.getOutPosition());
		srcPosition.setText(data.getSrcPosition());
		svnURL1.setText(data.getSvnURL1());
		svnUserName1.setText(data.getSvnUserName1());
		svnPassword1.setText(data.getSvnPassword1());
		svnStartVersion1.setText(data.getSvnStartVersion1());
		svnDefaultName1.setText(data.getSvnDefaultName1());
		svnURL2.setText(data.getSvnURL2());
		svnUserName2.setText(data.getSvnUserName2());
		svnPassword2.setText(data.getSvnPassword2());
		svnStartVersion2.setText(data.getSvnStartVersion2());
		svnDefaultName2.setText(data.getSvnDefaultName2());
	}

	private int menuDis(JPanel mainPanel, int y) {
		JButton openConfigBtn = new JButton("打开配置");
		openConfigBtn.setLocation(890, y);
		openConfigBtn.setSize(90, UIVO.R_H);
		openConfigBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser jfc = new JFileChooser(new File(FileUtil.BASE_DIR));
				jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
				FileNameExtensionFilter filter = new FileNameExtensionFilter("config", "bh");
				jfc.setFileFilter(filter);
				jfc.showDialog(new JLabel(), "选择");
				File file = jfc.getSelectedFile();
				try {
					ConfigInfoVO data = FileUtil.read(file);
					setInfo(data);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}

		});
		mainPanel.add(openConfigBtn);
		// 保存，导出
		JButton saveConfigBtn = new JButton("保存配置");
		saveConfigBtn.setLocation(1000, y);
		saveConfigBtn.setSize(90, UIVO.R_H);
		saveConfigBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String key = JOptionPane.showInputDialog(jf, "请输入保存名称", FileUtil.tempName);
				if (key == null || key.trim().length() == 0) {
					return;
				}
				try {
					String path = FileUtil.save(key, getInfo());
					JOptionPane.showMessageDialog(jf, "保存成功，路径：" + path);
				} catch (Exception e1) {
					e1.printStackTrace();
					JOptionPane.showMessageDialog(jf, "保存失败，异常：" + e1.getMessage());
				}
				return;
			}

		});
		mainPanel.add(saveConfigBtn);

		JButton exportBtn = new JButton("导出代码");
		exportBtn.setLocation(1110, y);
		exportBtn.setSize(90, UIVO.R_H);
		exportBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ConfigInfoVO config = getInfo();
				try {
					String outPos = config.getOutPosition();
					if (outPos == null || outPos.length() == 0) {
						SysLog.log("请选择导出位置");
						return;
					}
					String srcPos = config.getSrcPosition();
					if (srcPos == null || srcPos.length() == 0) {
						SysLog.log("请编译后位置");
						return;
					}
					String s1 = config.getSvnURL1();
					if (s1 == null || s1.length() == 0) {
						SysLog.log("请输入SVN地址");
						return;
					}
					String su1 = config.getSvnUserName1();
					if (su1 == null || su1.length() == 0) {
						SysLog.log("请输入SVN用户名称");
						return;
					}
					String sp1 = config.getSvnPassword1();
					if (sp1 == null || sp1.length() == 0) {
						SysLog.log("请输入SVN用户密码");
						return;
					}
					String sv1 = config.getSvnStartVersion1();
					if (sv1 == null || sv1.length() == 0) {
						SysLog.log("请输入SVN版本（数值）");
						return;
					} else {
						try {
							Long.valueOf(sv1);
						} catch (Exception ex) {
							SysLog.log("请输入正确SVN版本（数值）");
							return;
						}
					}
					String s2 = config.getSvnURL2();
					String su2 = "";
					String sp2 = "";
					String sv2 = "";
					if (s2 != null && s2.length() > 0) {
						su2 = config.getSvnUserName2();
						if (su2 == null || su2.length() == 0) {
							SysLog.log("请输入SVN2用户名称");
							return;
						}
						sp2 = config.getSvnPassword2();
						if (sp2 == null || sp2.length() == 0) {
							SysLog.log("请输入SVN2用户密码");
							return;
						}
						sv2 = config.getSvnStartVersion2();
						if (sv2 == null || sv2.length() == 0) {
							SysLog.log("请输入SVN2版本（数值）");
							return;
						} else {
							try {
								Long.valueOf(sv2);
							} catch (Exception ex) {
								SysLog.log("请输入正确SVN版本（数值）");
								return;
							}
						}
					}

					// 保存地址
					// 工程所用的tomcat地址（主要是为了Copy class等文件）
					List<SVNVersion> chageList = new ArrayList<SVNVersion>();

					chageList.add(new SVNVersion(srcPos, s1, su1, sp1, Long.valueOf(sv1), config.getSvnDefaultName1()));
					if (s2 != null && s2.length() > 0) {
						chageList.add(new SVNVersion(srcPos, s2, su2, sp2, Long.valueOf(sv2), config.getSvnDefaultName2()));
						SysLog.log("包含两个SVN地址 ");
					}

					SysLog.log("开始执行请等待。。。。。。  ");
					// 根据版本取得差异文件
					DiffFileLister<SVNVersion> oper = new DiffFileLister<SVNVersion>(chageList);
					List<ChangeVO> list = oper.listChange();
					// 打包
					DiffFilePacker p = new DiffFilePacker(outPos);
					p.pack(list);

					SysLog.log("\r\n 处理完成 。。。。。。    ");
				} catch (Exception ex) {
					SysLog.log("异常", ex);
				}
			}

		});
		mainPanel.add(exportBtn);
		return y + UIVO.R_H;
	}

	public static void main(String args[]) {
		// 在主方法中调用createJFrame()方法
		new PackerEntry().CreateJFrame("代码打包工具");
	}

	public void log(String value) {
		log.append(value + "\r\n");
	}

}
