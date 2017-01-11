package com.chat.app.services;

import java.util.Collection;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ChatManager;
import org.jivesoftware.smack.ChatManagerListener;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.ConnectionConfiguration.SecurityMode;
import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.RosterEntry;
import org.jivesoftware.smack.SmackConfiguration;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.packet.Presence.Type;

import com.chat.app.actions.MessageAction;
import com.chat.app.view.MainFrame;
import com.cmd.CmdMessageListener;

public class XmppManager {

	private static final int packetReplyTimeout = 5000; // millis

	private String server;
	private int port;

	private ConnectionConfiguration config;
	private XMPPConnection connection;

	private ChatManager chatManager;
	private CmdMessageListener<MainFrame> messageListener;
	private MainFrame mainFrame;

	public XmppManager(MainFrame mainFrame, String server, int port) {
		this.server = server;
		this.port = port;
		this.mainFrame = mainFrame;
	}

	public void init() throws XMPPException {

		System.out.println(String.format("Initializing connection to server %1$s port %2$d", server, port));

		SmackConfiguration.setPacketReplyTimeout(packetReplyTimeout);

		config = new ConnectionConfiguration(server, port);
		config.setSASLAuthenticationEnabled(false);
		config.setSecurityMode(SecurityMode.disabled);

		setConnection(new XMPPConnection(config));
		getConnection().connect();

		System.out.println("Connected: " + getConnection().isConnected());

		messageListener = new CmdMessageListener<MainFrame>(mainFrame, new MessageAction(), null);

		chatManager = getConnection().getChatManager();
		chatManager.addChatListener(new ChatManagerListener() {

			@Override
			public void chatCreated(Chat paramChat, boolean paramBoolean) {
				paramChat.addMessageListener(messageListener);
			}
		});

	}

	public void disconnect() {
		if (getConnection() != null && getConnection().isConnected()) {
			getConnection().disconnect();
		}
	}

	public void performLogin(String username, String password) throws XMPPException {
		if (getConnection() != null && getConnection().isConnected()) {
			getConnection().login(username, password);
		}
	}

	public void setStatus(boolean available, String status) {

		Presence.Type type = available ? Type.available : Type.unavailable;
		Presence presence = new Presence(type);

		presence.setStatus(status);
		getConnection().sendPacket(presence);

	}

	public void destroy() {
		if (getConnection() != null && getConnection().isConnected()) {
			getConnection().disconnect();
		}
	}

	public void printRoster() throws Exception {
		Roster roster = getConnection().getRoster();
		Collection<RosterEntry> entries = roster.getEntries();
		for (RosterEntry entry : entries) {
			System.out.println(String.format("Buddy:%1$s - Status:%2$s", entry.getName(), entry.getStatus()));
		}
	}

	public void sendMessage(String message, String buddyJID) throws XMPPException {
		Chat chat = chatManager.createChat(buddyJID, messageListener);
		chat.sendMessage(message);
	}

	public void deleteEntry(String user) throws XMPPException {
		Roster roster = getConnection().getRoster();
		Collection<RosterEntry> entries = roster.getEntries();

		for (RosterEntry entry : entries) {
			if (user.equals((String) entry.getUser())) {
				roster.removeEntry(entry);
			}
		}
	}

	public void createEntry(String user, String name) throws Exception {
		Roster roster = getConnection().getRoster();
		roster.createEntry(user, name, null);
	}

	public XMPPConnection getConnection() {
		return connection;
	}

	public void setConnection(XMPPConnection connection) {
		this.connection = connection;
	}

	// class MyMessageListener implements ChatStateListener {
	//
	// @Override
	// public void stateChanged(Chat arg0, ChatState arg1) {
	// if (ChatState.composing.equals(arg1)) {
	// System.out.println("Chat State" + arg0.getParticipant() + " is typing..");
	// } else if (ChatState.gone.equals(arg1)) {
	// System.out.println("Chat State" + arg0.getParticipant() + " has left the conversation.");
	// } else {
	// System.out.println("Chat State" + arg0.getParticipant() + ": " + arg1.name());
	// }
	// }
	//
	// @Override
	// public void processMessage(Chat paramChat, Message paramMessage) {
	// // TODO Auto-generated method stub
	// }
	//
	// }

}
