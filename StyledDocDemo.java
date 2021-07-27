import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;

import java.awt.Color;
import java.util.regex.Matcher;
import java.util.regex.Pattern;;

public class StyledDocDemo extends DefaultStyledDocument {

    private AttributeSet color_attr;
    private AttributeSet comments_attr;
    private AttributeSet default_attr;

    private static final Color FOREGROUND = new Color(39, 56, 165);
    private StyleContext styleContext;

    public final static String JAVA_KEYWORDS = "(public|private|protected|int|long|short|float|double|byte|"
            + "for|while|do|switch|case|break|default|continue|class|abstract|implements|"
            + "extends|native|interface|char|catch|try|const|goto|void|return|transiet|super|"
            + "synchronized|package|import|static|final|finally|if|else|instaceof|new)";

    private String keywordsRegex = "(\\W)*(" + JAVA_KEYWORDS + ")";

    public StyledDocDemo() {

        styleContext = StyleContext.getDefaultStyleContext();
        AttributeSet emptySet = styleContext.getEmptySet();
        color_attr = styleContext.addAttribute(emptySet, StyleConstants.Foreground, FOREGROUND);
        default_attr = styleContext.addAttribute(emptySet, StyleConstants.Foreground, Color.BLACK);

        comments_attr = styleContext.addAttribute(emptySet, StyleConstants.Foreground, Color.GRAY);

    }

    @Override
    public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
        super.insertString(offs, str, a);

        String text = getText(0, getLength());

        // find the last typed char before typing the next
        int before = findLastNonWordChar(text, offs);

        if (before < 0)
            before = 0;

        // then, find the last typed char, could be space char
        int after = findFirstNonWordChar(text, offs + str.length());
        
        int wordL = before;
        int wordR = before;
        while (wordR <= after) {
            if (wordR == after || String.valueOf(text.charAt(wordR)).matches("\\W")) {
        
               if (text.substring(wordL, wordR).matches(keywordsRegex))
                    setCharacterAttributes(wordL , wordR - wordL, color_attr, false); 
               else
               setCharacterAttributes(wordL , wordR - wordL, default_attr, false);    
               
                    wordL = wordR;
            }  
            wordR++;
        }
     

    }




    private int findLastNonWordChar(String text, int index) {
        while (--index >= 0) {
            if (String.valueOf(text.charAt(index)).matches("\\W")) 
                break;
        }
        return index;
    }

    private int findFirstNonWordChar(String text, int index) {
        while (index < text.length()) {
            if (String.valueOf(text.charAt(index)).matches("\\W")) 
                break;
            index++;
        }
        return index;
    }

    @Override
    public void remove(int offs, int len) throws BadLocationException {
        super.remove(offs, len);
        String text = getText(0, getLength());
        int before = findLastNonWordChar(text, offs);
        if (before < 0)
            before = 0;
        int after = findFirstNonWordChar(text, offs);

        if (text.substring(before, after).matches("(\\W)*(" + JAVA_KEYWORDS + ")")) {
            setCharacterAttributes(before + 1, after - before, color_attr, false);
        }else 
        setCharacterAttributes(before, after - before, default_attr, false);
    
    }


    /**
     * This method can be used to highlight comments 
     * @throws BadLocationException
     */
    public  void findComments() throws BadLocationException{
        String content  =  getText(0, getLength());
        Pattern singleLinecommentsPattern = Pattern.compile("\\/\\/.*");
        Matcher matcher = singleLinecommentsPattern.matcher(content);
        
        while (matcher.find()) {
            setCharacterAttributes(matcher.start(), 
              matcher.end() - matcher.start(), comments_attr, false);
        }
        
        Pattern multipleLinecommentsPattern = Pattern.compile("\\/\\*.*?\\*\\/",
                                Pattern.DOTALL);
        matcher = multipleLinecommentsPattern.matcher(content);
        
        while (matcher.find()) {
           setCharacterAttributes(matcher.start(), 
              matcher.end() - matcher.start(), comments_attr, false);
        }

      

    }

}// end class
