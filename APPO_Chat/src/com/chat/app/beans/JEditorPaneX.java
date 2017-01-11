package com.chat.app.beans;

import java.io.IOException;
import java.net.URL;
import java.util.Date;

import javax.swing.JEditorPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;

import com.string.StringUtils;

@SuppressWarnings("serial")
public class JEditorPaneX extends JEditorPane {
	private HTMLEditorKit kit;
	private HTMLDocument doc;
	private String meTemplate;
	private String heTemplate;
	private boolean initialized = false;

	public JEditorPaneX() {
		super();
	}

	public JEditorPaneX(String type, String text) {
		super(type, text);
	}

	public JEditorPaneX(String url) throws IOException {
		super(url);
	}

	public JEditorPaneX(URL initialPage) throws IOException {
		super(initialPage);
	}

	public void init() throws IOException {
		setEditable(false);
		kit = new HTMLEditorKit();
		doc = new HTMLDocument();
		setEditorKit(kit);
		setDocument(doc);
		meTemplate = StringUtils.readFileAsString("templates/message_me_template.html");
		heTemplate = StringUtils.readFileAsString("templates/message_he_template.html");
		setInitialized(true);
	}

	public void appendMeMessage(String user, String message, Date date, boolean setCaret) throws BadLocationException, IOException {
		kit.insertHTML(doc, doc.getLength(),
				meTemplate.replace("{H}", StringUtils.getHMS(date)).replace("{M}", StringUtils.escapeHTML(message)).replace("{U}", StringUtils.getUser(user)), 0, 0,
				null);
		if (setCaret) {
			setCaretPosition(getDocument().getLength());
		}
	}

	public void appendHeMessage(String user, String message, Date date, boolean setCaret) throws BadLocationException, IOException {
		kit.insertHTML(doc, doc.getLength(),
				heTemplate.replace("{H}", StringUtils.getHMS(date)).replace("{M}", StringUtils.escapeHTML(message)).replace("{U}", StringUtils.getUser(user)), 0, 0,
				null);
		if (setCaret) {
			setCaretPosition(getDocument().getLength());
		}
	}

	public void clearAll() {
		setText("");
	}

	public boolean isInitialized() {
		return initialized;
	}

	public void setInitialized(boolean initialized) {
		this.initialized = initialized;
	}

	public HTMLEditorKit getKit() {
		return kit;
	}

	public void setKit(HTMLEditorKit kit) {
		this.kit = kit;
	}

	public HTMLDocument getDoc() {
		return doc;
	}

	public void setDoc(HTMLDocument doc) {
		this.doc = doc;
	}

	public String getMeTemplate() {
		return meTemplate;
	}

	public void setMeTemplate(String meTemplate) {
		this.meTemplate = meTemplate;
	}

	public String getHeTemplate() {
		return heTemplate;
	}

	public void setHeTemplate(String heTemplate) {
		this.heTemplate = heTemplate;
	}
}
