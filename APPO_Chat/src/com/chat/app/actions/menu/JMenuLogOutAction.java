package com.chat.app.actions.menu;

import java.awt.event.ActionEvent;
import java.sql.SQLException;

import javax.swing.JFrame;

import com.chat.app.view.LoginDialog;
import com.chat.app.view.MainFrame;
import com.cmd.CmdAction;

public class JMenuLogOutAction implements CmdAction<MainFrame> {

	@Override
	public void execute(MainFrame object, ActionEvent e, String command) {
		try {
			LoginDialog loginDialog = object.getLoginDialog();
			object.disconnect();
			JFrame frame = object.getFrame();
			frame.dispose();
			loginDialog.setVisible(true);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}

}
