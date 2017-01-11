package com.chat.app.actions;

import java.awt.event.KeyEvent;

import com.chat.app.view.LoginDialog;
import com.cmd.CmdKey;

public class LogInCancelAction implements CmdKey<LoginDialog> {

	@Override
	public void executeKeyTyped(LoginDialog object, KeyEvent e, String command) {
		// TODO Auto-generated method stub

	}

	@Override
	public void executeKeyPressed(LoginDialog object, KeyEvent e, String command) {
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			e.consume();
			object.getBtnLogin().doClick();
		} else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			e.consume();
			object.getBtnCancel().doClick();
		}
	}

	@Override
	public void executeKeyReleased(LoginDialog object, KeyEvent e, String command) {
		// TODO Auto-generated method stub

	}

}
