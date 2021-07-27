import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Highlighter;

public class StringDemo {

    private String candidateText;

    public StringDemo(String text) {
        candidateText = text;
    }

    public StringDemo() {
    }

    public void setCandidateText(String text) {
        candidateText = text.trim();
    } 

    /**
     * 
     * @param value the string value to search from the candidate text
     * @return a list of HighlightPoint objects
     */
    public List<HighlightPoint> findText(String value) {
        List<HighlightPoint> highlightPoints = new ArrayList<>();
        String regex = "(" + value + ")";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(candidateText);
        while (matcher.find()) {
            highlightPoints.add(new HighlightPoint(matcher.start(), matcher.end()));
        }

        return highlightPoints;

    }

    public void highlightAll(List<HighlightPoint> points, JTextPane editor) {
        
        //first remove any Highlighters
        removeHighlight(editor);
        Highlighter highlighter = editor.getHighlighter();

        HighlightPoint.Painter painter = new HighlightPoint.Painter(new Color(160, 210, 254));

        points.stream().forEach((point) -> {
            try {
                highlighter.addHighlight(point.getStart(), point.getEnd(), painter);
            } catch (BadLocationException e) {
                e.printStackTrace();
            }

        });

    }

    public void removeHighlight(JTextPane textPane){
        Highlighter   h  = textPane.getHighlighter();
        Highlighter.Highlight[] highlighters = h.getHighlights();
         for (Highlighter.Highlight highlight : highlighters) 
             if( highlight.getPainter() instanceof HighlightPoint.Painter)
                 h.removeHighlight(highlight);

    }
}
