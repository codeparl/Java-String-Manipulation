import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.io.BufferedReader;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * This code example demonstrates how to manipulate strings in java programming
 * language.The program uses a textPane to search and highlihgt string portions
 * found by search its content. It also replaces string portions and a applies a
 * highlight color on a selected text and highlights all matches for any
 * selection.
 */

public class AppGUI extends JPanel {

    // declare a variable of JTextPane
    // that represents a text that can be
    // marked with colors and so forth.
    private JTextPane textPane;
    private JTextField searchField;
    private JTextField replaceField;
    private JButton findButton, resetButton;
    private static final int COLUMNS = 20;
    private Font defaultFont;

    private StringDemo stringDemo;

    // constructor
    public AppGUI() {

        super();
        // lay out the components vertically
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        setBorder(BorderFactory.createEmptyBorder(7, 7, 7, 7));
        Box sectionOne = Box.createHorizontalBox();
        Box sectionTwo = Box.createHorizontalBox();
        defaultFont = new Font(Font.SANS_SERIF, Font.PLAIN, 22);

        //add the components onto this panel
        makeSearchField(sectionOne);
        makeReplaceField(sectionOne);
        makeFindButton(sectionOne);
        makeResetButton(sectionOne);
        makeTextPane(sectionTwo);

        //This is the object that does all the work
        //for string manipulation
        stringDemo = new StringDemo();

        add(sectionOne);
        add(sectionTwo);
    }

    private void makeSearchField(Box section) {
        searchField = new JTextField(COLUMNS);
        JLabel label = new JLabel("Search Value: ");
        JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT));
        p.add(label);
        p.add(searchField);
        section.add(p);

        searchField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                find(searchField.getText());
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                find(searchField.getText());
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
            }
        });

    }

    private void makeReplaceField(Box section) {
        replaceField = new JTextField(COLUMNS);
        JLabel label = new JLabel("Replace Value: ");
        JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT));
        p.add(label);
        p.add(replaceField);
        section.add(p);

    }

    private void makeResetButton(Box section) {

        resetButton = new JButton("Reset");
        JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT));
        p.add(resetButton);
        section.add(p);
        resetButton.addActionListener((e) -> {
            stringDemo.removeHighlight(textPane);
        });
    }

    private void makeFindButton(Box section) {
        findButton = new JButton("Find Or Replace");
        JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT));

        findButton.addActionListener((event) -> {

            find(searchField.getText());
            replaceAll(searchField.getText());
        });

        p.add(findButton);
        section.add(p);

    }

    private void makeTextPane(Box section) {
        textPane = new JTextPane();
        textPane.setDocument(new StyledDocDemo());
        textPane.setPreferredSize(new Dimension(650, 350));
        textPane.setFont(defaultFont);
        textPane.setText(getSampleCode());
        section.add(new JScrollPane(textPane));

        textPane.addCaretListener((e) -> {
            String text = textPane.getSelectedText();
            if (text != null)
                find(text);
            else
                stringDemo.removeHighlight(textPane);
        });

    }

    private void find(String searchVal) {
        if (searchVal.trim().length() == 0) {
            stringDemo.removeHighlight(textPane);
            return;
        }

        String text = textPane.getText();
        stringDemo.setCandidateText(text);
        stringDemo.highlightAll(stringDemo.findText(searchVal), textPane);

    }

    private void replaceAll(String searchVal) {
        if (searchVal.trim().length() == 0) {
            stringDemo.removeHighlight(textPane);
            return;
        }

        String text = textPane.getText();
        var rpText = replaceField.getText().trim();
        if (rpText.length() > 0) {

            String rpl = text.replaceAll("(" + searchVal + ")", replaceField.getText());
            textPane.setText(rpl);
            stringDemo.setCandidateText(rpl);
            stringDemo.highlightAll(stringDemo.findText(rpText), textPane);
        }
    }

    /**
     * This method reads the content of this file (AppGUI.java)
     * 
     * @return the content of the file 
     */
    private String getSampleCode() {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader bs = Files.newBufferedReader(Paths.get("AppGUI.java"))) {
            String line = "";
            while ((line = bs.readLine()) != null) {
                sb.append(line).append('\n');
            }
        } catch (Exception e) {
        }
        return sb.toString().trim();
    }
}