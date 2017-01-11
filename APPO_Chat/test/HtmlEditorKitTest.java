import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.StyleSheet;

public class HtmlEditorKitTest {
	public static void main(String[] args) {
		new HtmlEditorKitTest();
	}

	public HtmlEditorKitTest() {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				// create jeditorpane
				JEditorPane jEditorPane = new JEditorPane();
				JScrollPane scrollPane = new JScrollPane(jEditorPane);
				jEditorPane.setEditable(false);

				HTMLEditorKit kit = new HTMLEditorKit();
				HTMLDocument doc = new HTMLDocument();
				jEditorPane.setEditorKit(kit);
				jEditorPane.setDocument(doc);

				StyleSheet styleSheet = kit.getStyleSheet();
				styleSheet.addRule("table {}");

				String meTemplate = null;
				String heTemplate = null;
				try {
					meTemplate = readFileAsString("templates/message_me_template.html");
					heTemplate = readFileAsString("templates/message_he_template.html");

					kit.insertHTML(doc, doc.getLength(), meTemplate.replace("{H}", "20.20").replace("{M}", "mesajul meumesajul meumesajul meu").replace("{U}", "me"), 0,
							0, null);

					kit.insertHTML(doc, doc.getLength(), meTemplate.replace("{H}", "20.20").replace("{M}", "mesajul meu").replace("{U}", "me"), 0, 0, null);
					kit.insertHTML(doc, doc.getLength(), meTemplate.replace("{H}", "20.20").replace("{M}", "mesajul meu").replace("{U}", "me"), 0, 0, null);

					kit.insertHTML(doc, doc.getLength(), heTemplate.replace("{H}", "20.21").replace("{M}", "mesajul lui").replace("{U}", "user"), 0, 0, null);

					kit.insertHTML(doc, doc.getLength(),
							meTemplate.replace("{H}", "20.20").replace("{M}", "mesajul meumesajul meumesajul meumesajul meu").replace("{U}", "me"), 0, 0, null);

					kit.insertHTML(doc, doc.getLength(), heTemplate.replace("{H}", "20.21").replace("{M}", "mesajul lui").replace("{U}", "user"), 0, 0, null);
					kit.insertHTML(doc, doc.getLength(), heTemplate.replace("{H}", "20.21").replace("{M}", "mesajul lui").replace("{U}", "user"), 0, 0, null);

					// htmlResult = mainTemplate.replace("{A}", htmlResult);
				} catch (Exception e) {
					e.printStackTrace();
				}

				// jEditorPane.setText(htmlResult);

				// now add it all to a frame
				JFrame j = new JFrame("HtmlEditorKit Test");
				j.getContentPane().add(scrollPane, BorderLayout.CENTER);

				// make it easy to close the application
				j.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

				// display the frame
				j.setSize(new Dimension(300, 200));

				// pack it, if you prefer
				// j.pack();

				// center the jframe, then make it visible
				j.setLocationRelativeTo(null);
				j.setVisible(true);
			}
		});
	}

	private static String readFileAsString(String filePath) throws IOException {
		StringBuffer fileData = new StringBuffer();
		BufferedReader reader = new BufferedReader(new FileReader(filePath));
		char[] buf = new char[1024];
		int numRead = 0;
		while ((numRead = reader.read(buf)) != -1) {
			String readData = String.valueOf(buf, 0, numRead);
			fileData.append(readData);
		}
		reader.close();
		return fileData.toString();
	}
}