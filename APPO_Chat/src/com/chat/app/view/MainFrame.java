package com.chat.app.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.InputEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Properties;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSplitPane;
import javax.swing.JTextPane;
import javax.swing.KeyStroke;
import javax.swing.text.BadLocationException;
import javax.swing.text.StyledDocument;

import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.RosterEntry;
import org.jivesoftware.smack.RosterListener;
import org.jivesoftware.smack.packet.Presence;

import com.chat.app.actions.Cmds;
import com.chat.app.actions.FriendListAction;
import com.chat.app.actions.FriendListColorAction;
import com.chat.app.actions.SendAction;
import com.chat.app.actions.menu.JMenuAddFriendAction;
import com.chat.app.actions.menu.JMenuDeleteFriendAction;
import com.chat.app.actions.menu.JMenuExitAction;
import com.chat.app.actions.menu.JMenuLogOutAction;
import com.chat.app.beans.JEditorPaneX;
import com.chat.app.services.SQLiteService;
import com.chat.app.services.XmppManager;
import com.cmd.CmdActionListener;
import com.cmd.CmdListCellRenderer;
import com.cmd.CmdMouseListener;
import com.string.StringUtils;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class MainFrame {
	private static final String PRPERTIES_FILE = "config/app.properties";

	private Cmds cmds;
	private JFrame frame;
	private Properties properties;
	private LoginDialog loginDialog;
	private XmppManager xmppManager = null;
	private JMenuItem mntmExit;
	private JMenuItem mntmLogOut;
	private JSplitPane friendsMesagesSplitPane;
	private JScrollPane friendsScrollPane;
	private JButton sendButton;
	private JTextPane messageTextPane;
	private JScrollPane messageScrollPane;
	private JSplitPane messageSendSplitPane;
	// private JList messagesList;
	private JScrollPane messagesScrollPane;
	private JSplitPane messagesMessageSplitPane;
	private JList friendList;
	private DefaultListModel friendListModel;
	private String selectedUser;

	private JEditorPaneX messagesEditorPaneX;

	private SQLiteService sqLiteService;

	private JMenuItem mntmAddFriend;

	private JMenuItem mntmDeleteFriend;

	private String username;

	private String password;

	// private DefaultListModel messagesListModel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame window = new MainFrame();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public MainFrame(LoginDialog loginDialog, String username, String password) {
		this.loginDialog = loginDialog;
		this.username = username;
		this.password = password;
	}

	/**
	 * Create the application.
	 */
	public MainFrame() {
		try {
			initialize();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Initialize the contents of the frame.
	 * 
	 * @throws IOException
	 */
	public void initialize() throws Exception {
		cmds = new Cmds(this);
		loadPropertiesFile(PRPERTIES_FILE);
		loadFrameComponents();
		loadSQLiteService();
		loadXmppManager();
	}

	public void disconnect() throws SQLException {
		getXmppManager().disconnect();
		getSqLiteService().disconnect();
	}

	private void loadSQLiteService() throws ClassNotFoundException, SQLException {
		sqLiteService = new SQLiteService("SQLite/" + StringUtils.getUser(username) + "_messages.db");
		sqLiteService.init();
	}

	private void loadXmppManager() throws Exception {
		String server = properties.getProperty("SERVER_HOST");
		int port = Integer.parseInt(properties.getProperty("SERVER_PORT"));
		xmppManager = new XmppManager(this, server, port);

		xmppManager.init();
		xmppManager.performLogin(username, password);
		xmppManager.setStatus(true, "Hello everyone");
		xmppManager.printRoster();

		Roster roster = xmppManager.getConnection().getRoster();
		roster.addRosterListener(new RosterListener() {

			@Override
			public void presenceChanged(Presence paramPresence) {
				// TODO Auto-generated method stub
				System.out.println(paramPresence.getFrom());
			}

			@Override
			public void entriesUpdated(Collection<String> paramCollection) {
				// TODO Auto-generated method stub
				System.out.println(paramCollection);

			}

			@Override
			public void entriesDeleted(Collection<String> paramCollection) {
				// TODO Auto-generated method stub
				System.out.println(paramCollection);

			}

			@Override
			public void entriesAdded(Collection<String> paramCollection) {
				// TODO Auto-generated method stub
				System.out.println("added: " + paramCollection);
				try {
					setFriendList(xmppManager.getConnection().getRoster());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		setFriendList(roster);
	}

	public void setFriendList(Roster roster) throws Exception {
		Collection<RosterEntry> entries = roster.getEntries();

		friendListModel = new DefaultListModel();
		friendList.setModel(friendListModel);

		for (RosterEntry entry : entries) {
			friendListModel.addElement((String) entry.getUser());
		}
	}

	public String getUser() {
		String fromUser = xmppManager.getConnection().getUser();
		return StringUtils.getPreparedUser(fromUser);
	}

	public Roster getRoster() throws Exception {
		return xmppManager.getConnection().getRoster();
	}

	private void loadFrameComponents() throws IOException {
		frame = new JFrame();
		frame.setBounds(100, 100, 680, 409);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);

		JMenu mnFile = new JMenu("File");
		mnFile.setMnemonic('f');
		menuBar.add(mnFile);

		JMenu mnFriends = new JMenu("Friends");
		mnFriends.setMnemonic('e');
		menuBar.add(mnFriends);

		mntmExit = new JMenuItem("Exit");
		mntmExit.setMnemonic('x');
		mntmExit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4, InputEvent.ALT_MASK));
		CmdActionListener<MainFrame> mntmExitActionListener = new CmdActionListener<MainFrame>(this, new JMenuExitAction(), null);
		mntmExit.addActionListener(mntmExitActionListener);

		mntmLogOut = new JMenuItem("Log Out");
		mntmLogOut.setMnemonic('o');
		mntmLogOut.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_MASK));
		CmdActionListener<MainFrame> mntmLogOutActionListener = new CmdActionListener<MainFrame>(this, new JMenuLogOutAction(), null);
		mntmLogOut.addActionListener(mntmLogOutActionListener);
		mnFile.add(mntmLogOut);

		JSeparator separator = new JSeparator();
		mnFile.add(separator);
		mnFile.add(mntmExit);

		mntmAddFriend = new JMenuItem("Add Friend");
		mntmAddFriend.setMnemonic('A');
		mntmAddFriend.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.CTRL_MASK | InputEvent.SHIFT_MASK));
		CmdActionListener<MainFrame> mntmAddFriendActionListener = new CmdActionListener<MainFrame>(this, new JMenuAddFriendAction(), null);
		mntmAddFriend.addActionListener(mntmAddFriendActionListener);

		mntmDeleteFriend = new JMenuItem("Delete Friend");
		mntmDeleteFriend.setMnemonic('A');
		mntmDeleteFriend.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D, InputEvent.CTRL_MASK | InputEvent.SHIFT_MASK));
		CmdActionListener<MainFrame> mntmDeleteFriendActionListener = new CmdActionListener<MainFrame>(this, new JMenuDeleteFriendAction(), null);
		mntmDeleteFriend.addActionListener(mntmDeleteFriendActionListener);

		mnFriends.add(mntmAddFriend);
		mnFriends.add(mntmDeleteFriend);

		friendsMesagesSplitPane = new JSplitPane();
		frame.getContentPane().add(friendsMesagesSplitPane, BorderLayout.CENTER);

		friendsScrollPane = new JScrollPane();
		friendsScrollPane.setMinimumSize(new Dimension(100, 24));
		friendsMesagesSplitPane.setLeftComponent(friendsScrollPane);

		friendList = new JList<String>();
		CmdMouseListener<MainFrame> friendListMouseListener = new CmdMouseListener<MainFrame>(this, new FriendListAction(), null);
		friendList.addMouseListener(friendListMouseListener);
		friendList.setCellRenderer(new CmdListCellRenderer<MainFrame>(this, new FriendListColorAction(), null));
		friendsScrollPane.setViewportView(friendList);
		// MainFrameService.setFriendList(list);

		messagesMessageSplitPane = new JSplitPane();
		messagesMessageSplitPane.setAutoscrolls(true);
		messagesMessageSplitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		messagesMessageSplitPane.setResizeWeight(1.0); // equal weights to top and bottom
		friendsMesagesSplitPane.setRightComponent(messagesMessageSplitPane);

		messagesScrollPane = new JScrollPane();
		messagesMessageSplitPane.setLeftComponent(messagesScrollPane);

		setMessagesEditorPaneX(new JEditorPaneX());
		getMessagesEditorPaneX().init();

		// messagesList = new JList();
		// messagesList.setAutoscrolls(false);
		// messagesListModel = new DefaultListModel();
		// messagesList.setModel(messagesListModel);
		messagesScrollPane.setViewportView(getMessagesEditorPaneX());

		messageSendSplitPane = new JSplitPane();
		messageSendSplitPane.setResizeWeight(1.0);
		messageSendSplitPane.setAutoscrolls(true);
		messagesMessageSplitPane.setRightComponent(messageSendSplitPane);

		messageScrollPane = new JScrollPane();
		messageSendSplitPane.setLeftComponent(messageScrollPane);

		setMessageTextPane(new JTextPane());
		getMessageTextPane().addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER && ((e.getModifiers() & KeyEvent.CTRL_MASK) != 0)) {
					try {
						StyledDocument document = (StyledDocument) getMessageTextPane().getDocument();
						document.insertString(document.getLength(), "\n", null);
					} catch (BadLocationException e1) {
						e1.printStackTrace();
					}
				} else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					e.consume();
					sendButton.doClick();
				}
			}
		});
		messageScrollPane.setViewportView(getMessageTextPane());

		sendButton = new JButton("Send");
		sendButton.setEnabled(false);
		CmdActionListener<MainFrame> sendButtonActionListener = new CmdActionListener<MainFrame>(this, new SendAction(), null);
		sendButton.addActionListener(sendButtonActionListener);
		messageSendSplitPane.setRightComponent(sendButton);
	}

	public void appendMessage(String user, String message) {
		// messagesListModel.addElement(element);
	}

	public Cmds getCmds() {
		return cmds;
	}

	public void setCmds(Cmds cmds) {
		this.cmds = cmds;
	}

	public JFrame getFrame() {
		return frame;
	}

	public void setFrame(JFrame frame) {
		this.frame = frame;
	}

	public LoginDialog getLoginDialog() {
		return loginDialog;
	}

	public void setLoginDialog(LoginDialog loginDialog) {
		this.loginDialog = loginDialog;
	}

	private void loadPropertiesFile(String selectedFile) throws IOException {
		InputStream input = null;
		try {
			input = new FileInputStream(selectedFile);
			properties = new Properties();
			properties.load(input);
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public JTextPane getMessageTextPane() {
		return messageTextPane;
	}

	public void setMessageTextPane(JTextPane messageTextPane) {
		this.messageTextPane = messageTextPane;
		messageTextPane.setEnabled(false);
		messageTextPane.setEditable(false);
	}

	public Properties getProperties() {
		return properties;
	}

	public void setProperties(Properties properties) {
		this.properties = properties;
	}

	public XmppManager getXmppManager() {
		return xmppManager;
	}

	public void setXmppManager(XmppManager xmppManager) {
		this.xmppManager = xmppManager;
	}

	public JMenuItem getMntmExit() {
		return mntmExit;
	}

	public void setMntmExit(JMenuItem mntmExit) {
		this.mntmExit = mntmExit;
	}

	public JMenuItem getMntmLogOut() {
		return mntmLogOut;
	}

	public void setMntmLogOut(JMenuItem mntmLogOut) {
		this.mntmLogOut = mntmLogOut;
	}

	public JSplitPane getFriendsMesagesSplitPane() {
		return friendsMesagesSplitPane;
	}

	public void setFriendsMesagesSplitPane(JSplitPane friendsMesagesSplitPane) {
		this.friendsMesagesSplitPane = friendsMesagesSplitPane;
	}

	public JScrollPane getFriendsScrollPane() {
		return friendsScrollPane;
	}

	public void setFriendsScrollPane(JScrollPane friendsScrollPane) {
		this.friendsScrollPane = friendsScrollPane;
	}

	public JButton getSendButton() {
		return sendButton;
	}

	public void setSendButton(JButton sendButton) {
		this.sendButton = sendButton;
	}

	public JScrollPane getMessageScrollPane() {
		return messageScrollPane;
	}

	public void setMessageScrollPane(JScrollPane messageScrollPane) {
		this.messageScrollPane = messageScrollPane;
	}

	public JSplitPane getMessageSendSplitPane() {
		return messageSendSplitPane;
	}

	public void setMessageSendSplitPane(JSplitPane messageSendSplitPane) {
		this.messageSendSplitPane = messageSendSplitPane;
	}

	public JScrollPane getMessagesScrollPane() {
		return messagesScrollPane;
	}

	public void setMessagesScrollPane(JScrollPane messagesScrollPane) {
		this.messagesScrollPane = messagesScrollPane;
	}

	public JSplitPane getMessagesMessageSplitPane() {
		return messagesMessageSplitPane;
	}

	public void setMessagesMessageSplitPane(JSplitPane messagesMessageSplitPane) {
		this.messagesMessageSplitPane = messagesMessageSplitPane;
	}

	public JList getFriendList() {
		return friendList;
	}

	public void setFriendList(JList friendList) {
		this.friendList = friendList;
	}

	public DefaultListModel getFriendListModel() {
		return friendListModel;
	}

	public void setFriendListModel(DefaultListModel friendListModel) {
		this.friendListModel = friendListModel;
	}

	public static String getPrpertiesFile() {
		return PRPERTIES_FILE;
	}

	public SQLiteService getSqLiteService() {
		return sqLiteService;
	}

	public void setSqLiteService(SQLiteService sqLiteService) {
		this.sqLiteService = sqLiteService;
	}

	public String getSelectedUser() {
		return selectedUser;
	}

	public void setSelectedUser(String selectedUser) {
		this.selectedUser = selectedUser;
	}

	public JEditorPaneX getMessagesEditorPaneX() {
		return messagesEditorPaneX;
	}

	public void setMessagesEditorPaneX(JEditorPaneX messagesEditorPaneX) {
		this.messagesEditorPaneX = messagesEditorPaneX;
		messagesEditorPaneX.setEnabled(false);
		messagesEditorPaneX.setEditable(false);
	}
}
