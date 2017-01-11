package com.chat.app.actions;

import java.awt.event.ActionEvent;
import java.util.Date;

import javax.swing.JTextPane;

import org.jivesoftware.smack.packet.Message;

import com.chat.app.beans.JEditorPaneX;
import com.chat.app.services.SQLiteService;
import com.chat.app.services.XmppManager;
import com.chat.app.view.MainFrame;
import com.cmd.CmdAction;

public class SendAction implements CmdAction<MainFrame> {

	@Override
	public void execute(MainFrame object, ActionEvent e, String command) {
		JTextPane messageTextPane = object.getMessageTextPane();
		String text = messageTextPane.getText();
		messageTextPane.setText("");
		String toUser = object.getSelectedUser();
		String fromUser = object.getUser();
		Message message = new Message(toUser);
		message.setType(Message.Type.chat);
		message.setBody(text);

		try {
			Date date = new Date();
			JEditorPaneX messagesEditorPaneX = object.getMessagesEditorPaneX();
			String body = message.getBody();
			messagesEditorPaneX.appendMeMessage(fromUser, body, date, true);
			XmppManager xmppManager = object.getXmppManager();
			xmppManager.sendMessage(text, toUser);
			SQLiteService sqLiteService = object.getSqLiteService();
			sqLiteService.insertMessage(fromUser, toUser, body, true, date);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
}
