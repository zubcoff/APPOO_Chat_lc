package com.chat.app.actions;

import java.util.Date;

import javax.swing.JList;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.packet.Message;

import com.chat.app.beans.JEditorPaneX;
import com.chat.app.view.MainFrame;
import com.cmd.CmdMessage;
import com.string.StringUtils;

@SuppressWarnings("rawtypes")
public class MessageAction implements CmdMessage<MainFrame> {

	@Override
	public void executeprocessMessage(MainFrame object, Chat chat, Message message, String command) {
		String fromUser = message.getFrom();
		String body = message.getBody();
		if (body != null) {
			fromUser = StringUtils.getPreparedUser(fromUser);
			boolean isSelectedUser = fromUser.equalsIgnoreCase(object.getSelectedUser());
			try {
				Date date = new Date();
				if (isSelectedUser) {
					JEditorPaneX messagesEditorPaneX = object.getMessagesEditorPaneX();
					messagesEditorPaneX.appendHeMessage(fromUser, body, date, true);
				} else {
					JList friendList = object.getFriendList();
					if (friendList != null) {
						friendList.repaint();
					}
				}
				String user = object.getUser();
				object.getSqLiteService().insertMessage(fromUser, user, body, isSelectedUser, date);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
