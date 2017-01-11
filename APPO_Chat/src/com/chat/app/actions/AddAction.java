package com.chat.app.actions;

import java.awt.event.ActionEvent;

import javax.swing.JOptionPane;

import org.jivesoftware.smack.XMPPConnection;

import com.chat.app.services.XmppManager;
import com.chat.app.view.AddFriendDialog;
import com.chat.app.view.MainFrame;
import com.cmd.CmdAction;

public class AddAction implements CmdAction<AddFriendDialog> {

	@Override
	public void execute(AddFriendDialog object, ActionEvent e, String command) {
		String username = object.getUsername();

		MainFrame mainFrame = object.getMainFrame();
		object.setMainFrame(mainFrame);
		try {
			XmppManager xmppManager = mainFrame.getXmppManager();
			XMPPConnection connection = xmppManager.getConnection();
			String serviceName = connection.getServiceName();

			xmppManager.createEntry(username + "@" + serviceName, username + "@" + serviceName);

			mainFrame.setFriendList(connection.getRoster());
		} catch (Exception e1) {
			JOptionPane.showMessageDialog(object, e1.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
			e1.printStackTrace();
		}
	}
}
