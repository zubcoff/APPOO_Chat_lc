package com.chat.app.actions;

import java.awt.event.ActionEvent;

import com.chat.app.view.LoginDialog;
import com.chat.app.view.MainFrame;
import com.cmd.CmdAction;

public class CancelAction implements CmdAction<LoginDialog> {

	@Override
	public void execute(LoginDialog object, ActionEvent e, String command) {
		MainFrame mainFrame = object.getMainFrame();
		if (mainFrame != null) {
			mainFrame.getFrame().dispose();
		}
		object.dispose();
		System.exit(0);
	}

}
