package com.chat.app.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import com.chat.app.actions.AddAction;
import com.chat.app.actions.AddCancelAction;
import com.chat.app.actions.CancelAddAction;
import com.cmd.CmdActionListener;
import com.cmd.CmdKeyListener;

public class AddFriendDialog extends JDialog {
	private static final long serialVersionUID = 1L;

	private JTextField tfUsername;
	private JLabel lbUsername;
	private JButton btnAdd;
	private JButton btnCancel;
	private boolean succeeded;
	private MainFrame mainFrame;

	public AddFriendDialog(MainFrame mainFrame) {
		this.mainFrame = mainFrame;
		setTitle("Add friend");
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

		panel.setBorder(new LineBorder(Color.GRAY));

		setBtnAdd(new JButton("Add"));

		CmdActionListener<AddFriendDialog> addActionListener = new CmdActionListener<AddFriendDialog>(this, new AddAction(), null);
		getBtnAdd().addActionListener(addActionListener);
		setBtnCancel(new JButton("Cancel"));
		CmdActionListener<AddFriendDialog> logInCancelActionListener = new CmdActionListener<AddFriendDialog>(this, new CancelAddAction(), null);
		getBtnCancel().addActionListener(logInCancelActionListener);
		JPanel bp = new JPanel();
		bp.add(getBtnAdd());
		bp.add(getBtnCancel());

		CmdKeyListener<AddFriendDialog> addChancelActionListener = new CmdKeyListener<AddFriendDialog>(this, new AddCancelAction(), null);
		tfUsername.addKeyListener(addChancelActionListener);

		getContentPane().add(panel, BorderLayout.CENTER);
		getContentPane().add(bp, BorderLayout.PAGE_END);

		pack();
		setResizable(false);
		setLocationRelativeTo(mainFrame.getFrame());
	}

	public String getUsername() {
		return tfUsername.getText().trim();
	}

	public JTextField getTfUsername() {
		return tfUsername;
	}

	public void setTfUsername(JTextField tfUsername) {
		this.tfUsername = tfUsername;
	}

	public JLabel getLbUsername() {
		return lbUsername;
	}

	public void setLbUsername(JLabel lbUsername) {
		this.lbUsername = lbUsername;
	}

	public JButton getBtnAdd() {
		return btnAdd;
	}

	public void setBtnAdd(JButton btnAdd) {
		this.btnAdd = btnAdd;
	}

	public JButton getBtnCancel() {
		return btnCancel;
	}

	public void setBtnCancel(JButton btnCancel) {
		this.btnCancel = btnCancel;
	}

	public boolean isSucceeded() {
		return succeeded;
	}

	public void setSucceeded(boolean succeeded) {
		this.succeeded = succeeded;
	}

	public MainFrame getMainFrame() {
		return mainFrame;
	}

	public void setMainFrame(MainFrame mainFrame) {
		this.mainFrame = mainFrame;
	}
}