package com.chat.app.actions.menu;

import java.awt.event.ActionEvent;

import com.chat.app.view.AddFriendDialog;
import com.chat.app.view.MainFrame;
import com.cmd.CmdAction;

public class JMenuAddFriendAction implements CmdAction<MainFrame> {

	@Override
	public void execute(MainFrame object, ActionEvent e, String command) {
		new AddFriendDialog(object).setVisible(true);
	}

}
