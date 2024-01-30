package kamui.object;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Point;
import kamui.gate.Gate;
import kamui.system.Settings;

public class Terminal2 extends Gate{
  public Terminal2(int x,int y){
    super(x,y,1,0);
    draw_input_line_flag=false;
  }
  public String getName(){
    return "Terminal2";
  }
  public Gate copy(){
    Gate gate=new Terminal2(x,y);
    return gate;
  }
  public void draw(Graphics g,boolean a){
    super.draw(g,a);
    Graphics2D g2=(Graphics2D)g;
    g.setColor(color);
    g.drawLine(x,y,x-20,y);
    g.fillOval(x-5,y-5,10,10);
    g.setColor(Color.WHITE);
    g.fillOval(x-3,y-3,6,6);
    g2.setStroke(Settings.stroke2);
  }
}
