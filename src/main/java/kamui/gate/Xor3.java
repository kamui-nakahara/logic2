package kamui.gate;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.geom.Arc2D;

public class Xor3 extends Gate{
  public Xor3(int x,int y){
    super(x,y,3,1);
    draw_input_line_flag=false;
  }
  public Gate copy(){
    Gate gate=new Xor3(x,y);
    gate.draw_input_line_flag=false;
    return gate;
  }
  public void calc(){
    super.calc();
    boolean a=value.get(0);
    boolean b=value.get(1);
    boolean c=value.get(2);
    boolean d=((a || b) && !(a && b));
    boolean e=((c || d) && !(c && d));
    outputs.put(terminal.get(0),e);
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
    g2.setStroke(stroke2);
  }
}
