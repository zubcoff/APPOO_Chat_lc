import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.text.Document;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.StyleSheet;

/**
 * A complete Java class that demonstrates how to create an HTML viewer with styles, using the JEditorPane, HTMLEditorKit, StyleSheet, and JFrame.
 * 
 * @author alvin alexander, devdaily.com.
 *
 */
public class HtmlEditorKitTest2 {
	public static void main(String[] args) {
		new HtmlEditorKitTest2();
	}

	public HtmlEditorKitTest2() {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				// create jeditorpane
				JEditorPane jEditorPane = new JEditorPane();

				// make it read-only
				jEditorPane.setEditable(false);

				// create a scrollpane; modify its attributes as desired
				final JScrollPane scrollPane = new JScrollPane(jEditorPane);

				// add an html editor kit
				HTMLEditorKit kit = new HTMLEditorKit();
				jEditorPane.setEditorKit(kit);

				// add some styles to the html
				StyleSheet styleSheet = kit.getStyleSheet();
				styleSheet.addRule("body {"
						+ "font-family: Segoe UI;"
						+ "font-size: 10px;"
						+ "font-style: normal;"
						+ "font-variant: normal;"
						+ "font-weight: 400;"
						+ "line-height: 15.4px;"
						+ "}");

				// create some simple html as a string
				String mainTemplate = null;
				String meTemplate = null;
				String heTemplate = null;
				String htmlResult = "";
				try {
					mainTemplate = readFileAsString("templates/template.html");
					meTemplate = readFileAsString("templates/message_me_template.html");
					heTemplate = readFileAsString("templates/message_he_template.html");

					htmlResult += meTemplate.replace("{H}", "20.20").replace("{M}", "mesajul meu").replace("{U}", "me");
					htmlResult += meTemplate.replace("{H}", "20.20").replace("{M}", "mesajul meu").replace("{U}", "me");

					htmlResult += heTemplate.replace("{H}", "20.21").replace("{M}", "mesajul lui").replace("{U}", "user");

					htmlResult += meTemplate.replace("{H}", "20.20").replace("{M}", "mesajul meumesajul meumesajul meumesajul meu").replace("{U}", "me");

					htmlResult += heTemplate.replace("{H}", "20.21").replace("{M}", "mesajul lui").replace("{U}", "user");
					htmlResult += heTemplate.replace("{H}", "20.21").replace("{M}", "mesajul lui").replace("{U}", "user");

//					htmlResult = mainTemplate.replace("{A}", htmlResult);
				} catch (IOException e) {
					e.printStackTrace();
				}

				// create a document, set it on the jeditorpane, then add the html
				Document doc = kit.createDefaultDocument();
				jEditorPane.setDocument(doc);
				
				
				
				jEditorPane.setText(htmlResult);

				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						JScrollBar verticalScrollBar = scrollPane.getVerticalScrollBar();
						verticalScrollBar.setValue(verticalScrollBar.getMaximum());
					}
				});

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