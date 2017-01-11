package com.chat.app.actions.menu;

import java.awt.event.ActionEvent;

import com.chat.app.view.MainFrame;
import com.cmd.CmdAction;

public class JMenuDeleteFriendAction implements CmdAction<MainFrame> {

	@Override
	public void execute(MainFrame object, ActionEvent e, String command) {
		String username = object.getSelectedUser();

		try {
			object.getXmppManager().deleteEntry(username);
			object.setFriendList(object.getXmppManager().getConnection().getRoster());
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

}
