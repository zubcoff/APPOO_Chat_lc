package com.chat.app.actions;

import java.awt.event.MouseEvent;
import java.util.Date;
import java.util.List;

import javax.swing.JList;

import com.chat.app.beans.JEditorPaneX;
import com.chat.app.beans.MessageSQL;
import com.chat.app.services.SQLiteService;
import com.chat.app.view.MainFrame;
import com.cmd.CmdMouse;

@SuppressWarnings("rawtypes")
public class FriendListAction implements CmdMouse<MainFrame> {

	@Override
	public void executeMouseClicked(MainFrame object, MouseEvent e, String command) {
		JList list = (JList) e.getSource();
		if (e.getClickCount() == 1) {
			object.getMessagesEditorPaneX().setEnabled(true);
			object.getMessageTextPane().setEditable(true);
			object.getMessageTextPane().setEnabled(true);
			object.getSendButton().setEnabled(true);
			int index = list.locationToIndex(e.getPoint());
			String toUser = list.getModel().getElementAt(index).toString();

			JEditorPaneX messagesEditorPaneX = object.getMessagesEditorPaneX();
			messagesEditorPaneX.clearAll();
			String fromUser = object.getUser();

			List<MessageSQL> messages;
			try {
				SQLiteService sqLiteService = object.getSqLiteService();
				messages = sqLiteService.getMessages(fromUser, toUser);
				for (MessageSQL message : messages) {
					String user = message.getFromUser();
					String mess = message.getMessage();
					Date date = message.getDate();
					if (fromUser.equals(user)) {
						messagesEditorPaneX.appendMeMessage(user, mess, date, false);
					} else {
						messagesEditorPaneX.appendHeMessage(user, mess, date, false);
					}
				}
				object.setSelectedUser(toUser);
				sqLiteService.makeAsReadUnreadMsg(toUser, fromUser);
				object.getFrame().setTitle("Chat - " + fromUser + " > " + toUser);
				list.repaint();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

	@Override
	public void executeMouseReleased(MainFrame object, MouseEvent e, String command) {
		// TODO Auto-generated method stub

	}

	@Override
	public void executeMousePressed(MainFrame object, MouseEvent e, String command) {
		// TODO Auto-generated method stub

	}

	@Override
	public void executeMouseEntered(MainFrame object, MouseEvent e, String command) {
		// TODO Auto-generated method stub

	}

	@Override
	public void executeMouseExited(MainFrame object, MouseEvent e, String command) {
		// TODO Auto-generated method stub

	}

}
