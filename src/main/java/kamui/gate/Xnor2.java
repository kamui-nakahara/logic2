package kamui.gate;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.geom.Arc2D;

public class Xnor2 extends Gate{
  public Xnor2(int x,int y){
    super(x,y,2,1);
    draw_input_line_flag=false;
  }
  public Gate copy(){
    Gate gate=new Xnor2(x,y);
    gate.draw_input_line_flag=false;
    return gate;
  }
  public void calc(){
    super.calc();
    boolean a=!((value.get(0) || value.get(1)) && !(value.get(0) && value.get(1)));
    outputs.put(terminal.get(0),a);
  }
  public void draw(Graphics g){
    super.draw(g);
    Graphics2D g2=(Graphics2D)g;
    g.drawLine(x-25,y-20,x-10,y-20);
    g.drawLine(x-25,y+20,x-10,y+20);
    g2.draw(new Arc2D.Double(x-99,y-40,80,80,-30,60,Arc2D.OPEN));
    g2.draw(new Arc2D.Double(x-104,y-40,80,80,-30,60,Arc2D.OPEN));
    g2.draw(new Arc2D.Double(x-39,y-20,60,60,90,-70,Arc2D.OPEN));
    g2.draw(new Arc2D.Double(x-39,y-40,60,60,-90,70,Arc2D.OPEN));
    g.fillOval(x+20,y-3,6,6);
    g.setColor(Color.WHITE);
    g.fillOval(x+21,y-2,4,4);
    g2.setStroke(stroke2);
  }
}
