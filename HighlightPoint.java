import javax.swing.text.DefaultHighlighter.DefaultHighlightPainter;
import java.awt.Color;

public class HighlightPoint{

  private   int  start;
  private   int end;
            public HighlightPoint(int start, int end) {
                this.start = start;
                this.end = end;
            }
    
            public int getStart() {
                return start;
            }
    
            public int getEnd() {
                return end;
            }
    
            public void setStart(int start) {
                this.start = start;
            }
    
            public void setEnd(int end) {
                this.end = end;
            }
    
            @Override
            public String toString() {

                
                return String.format("%s[%d,%d]", this.getClass(),getStart(), getEnd()); 
            }
    
    
    
            public static class Painter extends DefaultHighlightPainter {
                public Painter(Color color){
                    super(color);
                }
            }
    
    }