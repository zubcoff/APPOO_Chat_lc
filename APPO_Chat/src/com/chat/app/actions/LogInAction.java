package com.chat.app.actions;

import java.awt.event.ActionEvent;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.chat.app.view.LoginDialog;
import com.chat.app.view.MainFrame;
import com.cmd.CmdAction;

public class LogInAction implements CmdAction<LoginDialog> {

	@Override
	public void execute(LoginDialog object, ActionEvent e, String command) {
		String username = object.getUsername();
		String password = object.getPassword();

		MainFrame mainFrame = new MainFrame(object, username, password);
		object.setMainFrame(mainFrame);
		try {
			mainFrame.initialize();
			object.setVisible(false);
			JFrame frame = mainFrame.getFrame();
			frame.setTitle("Chat - " + username);
			frame.setVisible(true);
		} catch (Exception e1) {
			JOptionPane.showMessageDialog(object, e1.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
			e1.printStackTrace();
		}
	}
}
