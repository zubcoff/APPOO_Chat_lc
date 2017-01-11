package com.chat.app.actions;

import java.awt.Color;
import java.awt.Component;
import java.sql.SQLException;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;

import com.chat.app.view.MainFrame;
import com.cmd.CmdListCell;
import com.string.StringUtils;

@SuppressWarnings({ "rawtypes", "serial" })
public class FriendListColorAction extends DefaultListCellRenderer implements CmdListCell<MainFrame> {

	@Override
	public Component getListCellRendererComponent(MainFrame object, JList list, Object value, int index, boolean isSelected, boolean cellHasFocus,
			String command) {
		Component component = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
		try {
			String fromUser = StringUtils.getPreparedUser(value.toString());
			String toUser = object.getUser();
			int count = object.getSqLiteService().getNumUnreadMsg(fromUser, toUser);
			if (isSelected) {
				component.setBackground(Color.cyan);
			} else if (count > 0) {
				component.setBackground(Color.yellow);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return component;
	}

}
