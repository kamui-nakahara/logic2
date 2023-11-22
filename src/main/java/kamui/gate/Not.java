package kamui.gate;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;

public class Not extends Gate{
  public Not(int x,int y){
    super(x,y,1,1);
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
  public void draw(Graphics g){
    super.draw(g);
    Graphics2D g2=(Graphics2D)g;
    g.setColor(color);
    g.drawLine(x-20,y-20,x-20,y+20);
    g.drawLine(x+20,y,x-20,y-20);
    g.drawLine(x+20,y,x-20,y+20);
    g.fillOval(x+20,y-3,6,6);
    g.setColor(Color.WHITE);
    g.fillOval(x+21,y-2,4,4);
    g2.setStroke(stroke2);
  }
}
