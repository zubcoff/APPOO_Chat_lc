package com.chat.app.actions;

import java.awt.event.ActionEvent;

import com.chat.app.view.AddFriendDialog;
import com.cmd.CmdAction;

public class CancelAddAction implements CmdAction<AddFriendDialog> {

	@Override
	public void execute(AddFriendDialog object, ActionEvent e, String command) {
		object.dispose();
	}
}
