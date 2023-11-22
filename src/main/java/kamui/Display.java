package kamui;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import java.awt.Dimension;
import java.awt.Insets;
import kamui.system.Settings;

public class Display extends JFrame{
  Screen screen;
  int width;
  int height;
  public Display(String title){
    super(title);
    pack();
    Insets insets=getInsets();
    width=Settings.width;
    height=Settings.height+insets.top;
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setSize(Settings.width,Settings.height+insets.top);
    setResizable(false);

    screen=new Screen(this);
    screen.setPreferredSize(new Dimension(Settings.width,Settings.height));
    add(screen);

    JScrollPane scrollpane=new JScrollPane(screen);
    scrollpane.setPreferredSize(new Dimension(width,height+insets.top));
    scrollpane.getVerticalScrollBar().setUnitIncrement(25);

    add(scrollpane);
    
    addKeyListener(screen.main);
  }
}
