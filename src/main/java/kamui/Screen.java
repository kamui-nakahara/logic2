package kamui;

import javax.swing.JPanel;
import java.awt.Graphics;
import kamui.system.Settings;

public class Screen extends JPanel{
  public Main main;
  public Display display;
  public Screen(Display display){
    super();
    this.display=display;
    main=new Main(this);
    addMouseListener(main);
    addMouseMotionListener(main);
  }
  public void paintComponent(Graphics g){
    super.paintComponent(g);
    main.update();
    main.draw(g);
  }
}
