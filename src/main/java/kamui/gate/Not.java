package kamui.gate;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import kamui.system.Settings;

public class Not extends Gate{
  public Not(int x,int y){
    super(x,y,1,1);
  }
  public String getName(){
    return "not";
  }
  public Gate copy(){
    Gate gate=new Not(x,y);
    return gate;
  }
  public void calc(){
    super.calc();
    boolean a=!value.get(0);
    outputs.put(terminal.get(0),a);
  }
  public void draw(Graphics g,boolean a){
    super.draw(g,a);
    Graphics2D g2=(Graphics2D)g;
    g.setColor(color);
    g.drawLine(x-20,y-20,x-20,y+20);
    g.drawLine(x+20,y,x-20,y-20);
    g.drawLine(x+20,y,x-20,y+20);
    g.fillOval(x+20,y-5,10,10);
    g.setColor(Color.WHITE);
    g.fillOval(x+22,y-3,6,6);
    g2.setStroke(Settings.stroke2);
  }
}
