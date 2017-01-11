package com.chat.app.actions;

import java.awt.event.KeyEvent;

import com.chat.app.view.AddFriendDialog;
import com.cmd.CmdKey;

public class AddCancelAction implements CmdKey<AddFriendDialog> {

	@Override
	public void executeKeyTyped(AddFriendDialog object, KeyEvent e, String command) {
		// TODO Auto-generated method stub

	}

	@Override
	public void executeKeyPressed(AddFriendDialog object, KeyEvent e, String command) {
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			e.consume();
			object.getBtnAdd().doClick();
		} else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			e.consume();
			object.getBtnCancel().doClick();
		}
	}

	@Override
	public void executeKeyReleased(AddFriendDialog object, KeyEvent e, String command) {
		// TODO Auto-generated method stub

	}

}