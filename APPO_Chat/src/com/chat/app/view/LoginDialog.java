package com.chat.app.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.border.LineBorder;

import com.chat.app.actions.CancelAction;
import com.chat.app.actions.LogInAction;
import com.chat.app.actions.LogInCancelAction;
import com.cmd.CmdActionListener;
import com.cmd.CmdKeyListener;

public class LoginDialog extends JDialog {
	private static final long serialVersionUID = 1L;

	private JTextField tfUsername;
	private JPasswordField pfPassword;
	private JLabel lbUsername;
	private JLabel lbPassword;
	private JButton btnLogin;
	private JButton btnCancel;
	private boolean succeeded;
	private MainFrame mainFrame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginDialog window = new LoginDialog();
					window.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public LoginDialog() {
		setTitle("Login");
		JPanel panel = new JPanel(new GridBagLayout());
		GridBagConstraints cs = new GridBagConstraints();

		cs.fill = GridBagConstraints.HORIZONTAL;

		lbUsername = new JLabel("Username: ");
		cs.gridx = 0;
		cs.gridy = 0;
		cs.gridwidth = 1;
		panel.add(lbUsername, cs);

		tfUsername = new JTextField(20);
		cs.gridx = 1;
		cs.gridy = 0;
		cs.gridwidth = 2;
		panel.add(tfUsername, cs);

		lbPassword = new JLabel("Password: ");
		cs.gridx = 0;
		cs.gridy = 1;
		cs.gridwidth = 1;
		panel.add(lbPassword, cs);

		pfPassword = new JPasswordField(20);
		cs.gridx = 1;
		cs.gridy = 1;
		cs.gridwidth = 2;
		panel.add(pfPassword, cs);
		panel.setBorder(new LineBorder(Color.GRAY));

		setBtnLogin(new JButton("Login"));

		CmdActionListener<LoginDialog> logInActionListener = new CmdActionListener<LoginDialog>(this, new LogInAction(), null);
		getBtnLogin().addActionListener(logInActionListener);
		setBtnCancel(new JButton("Cancel"));
		CmdActionListener<LoginDialog> logInCancelActionListener = new CmdActionListener<LoginDialog>(this, new CancelAction(), null);
		getBtnCancel().addActionListener(logInCancelActionListener);
		JPanel bp = new JPanel();
		bp.add(getBtnLogin());
		bp.add(getBtnCancel());

		CmdKeyListener<LoginDialog> logInChancelActionListener = new CmdKeyListener<LoginDialog>(this, new LogInCancelAction(), null);
		tfUsername.addKeyListener(logInChancelActionListener);
		pfPassword.addKeyListener(logInChancelActionListener);

		getContentPane().add(panel, BorderLayout.CENTER);
		getContentPane().add(bp, BorderLayout.PAGE_END);

		pack();
		setResizable(false);
		setLocationRelativeTo(null);
		tfUsername.setText("test1");
		pfPassword.setText("test1");
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
	}

	public String getUsername() {
		return tfUsername.getText().trim();
	}

	public String getPassword() {
		return new String(pfPassword.getPassword());
	}

	public boolean isSucceeded() {
		return succeeded;
	}

	public MainFrame getMainFrame() {
		return mainFrame;
	}

	public void setMainFrame(MainFrame mainFrame) {
		this.mainFrame = mainFrame;
	}

	public JButton getBtnLogin() {
		return btnLogin;
	}

	public void setBtnLogin(JButton btnLogin) {
		this.btnLogin = btnLogin;
	}

	public JButton getBtnCancel() {
		return btnCancel;
	}

	public void setBtnCancel(JButton btnCancel) {
		this.btnCancel = btnCancel;
	}
}