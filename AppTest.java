

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

public class AppTest {

    public static void main(String[] args) {

        String lookAndFeel = "Windows";
        setLookAndFeel(lookAndFeel);
        SwingUtilities.invokeLater(()->{
            initWindow();
        });

    }//end main method 


 private static  void setLookAndFeel(String lookAndFeel){
  //get installed look-and-feel
  LookAndFeelInfo[]  lookAndFeelInfo = 
  UIManager.getInstalledLookAndFeels();
  try {
      for (LookAndFeelInfo info : lookAndFeelInfo) {
          if(lookAndFeel.equalsIgnoreCase(info.getName())){
              UIManager.setLookAndFeel(info.getClassName());
              break;
          }
      }
  } catch (Exception e) {
      e.printStackTrace();
  }
    }

    private static void initWindow() {
        String title = "String Demo -  CodeParl";
        JFrame appFrame = new JFrame(title);

        // make sure our app is closable by clicking the
        // close button
        appFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // use our AppGuI class as the content pane
        // of this window
        appFrame.setContentPane(new AppGUI());

        // set the size and location on screen
       //appFrame.setSize(650, 320);
       appFrame.pack();
       appFrame.setResizable(false);
        // center the window on screen
        appFrame.setLocationRelativeTo(null);

         

        // show the app on screen
        appFrame.setVisible(true);

    }

}
