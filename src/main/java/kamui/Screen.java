package kamui;

import javax.swing.JPanel;
import java.awt.Graphics;
import kamui.system.Settings;

public class Screen extends JPanel{
  Main main;
  public Screen(){
    super();
    main=new Main(this);
  }
  public void paintComponent(Graphics g){
    super.paintComponent(g);
    main.update();
    main.draw(g);
  }
}
