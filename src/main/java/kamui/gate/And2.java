package kamui.gate;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Arc2D;
import java.awt.Point;
import java.awt.Color;

public class And2 extends Gate{
  public And2(int x,int y){
    super(x,y,2,1);
  }
  public Gate copy(){
    Gate gate=new And2(x,y);
    return gate;
  }
  public void calc(){
    super.calc();
    boolean a=value.get(0) && value.get(1);
    outputs.put(terminal.get(0),a);
  }
  public void draw(Graphics g){
    super.draw(g);
    g.setColor(color);
    Graphics2D g2=(Graphics2D)g;
    g.drawLine(x-20,y-20,x-20,y+20);
    g.drawLine(x-20,y-20,x,y-20);
    g.drawLine(x-20,y+20,x,y+20);
    g2.draw(new Arc2D.Double(x-20,y-20,40,40,90,-180,Arc2D.OPEN));
    g2.setStroke(stroke2);
  }
}
