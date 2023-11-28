package kamui.gate;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.geom.Arc2D;
import kamui.system.Settings;

public class Xnor3 extends Gate{
  public Xnor3(int x,int y){
    super(x,y,3,1);
    draw_input_line_flag=false;
  }
  public String getName(){
    return "xnor3";
  }
  public Gate copy(){
    Gate gate=new Xnor3(x,y);
    gate.draw_input_line_flag=false;
    return gate;
  }
  public void calc(){
    super.calc();
    boolean a=value.get(0);
    boolean b=value.get(1);
    boolean c=value.get(2);
    boolean d=((a || b) && !(a && b));
    boolean e=!((d || c) && !(d && c));
    outputs.put(terminal.get(0),e);
  }
  public void draw(Graphics g,boolean a){
    super.draw(g,a);
    Graphics2D g2=(Graphics2D)g;
    g.drawLine(x-25,y-20,x-10,y-20);
    g.drawLine(x-25,y+20,x-10,y+20);
    g2.draw(new Arc2D.Double(x-99,y-40,80,80,-30,60,Arc2D.OPEN));
    g2.draw(new Arc2D.Double(x-104,y-40,80,80,-30,60,Arc2D.OPEN));
    g2.draw(new Arc2D.Double(x-39,y-20,60,60,90,-70,Arc2D.OPEN));
    g2.draw(new Arc2D.Double(x-39,y-40,60,60,-90,70,Arc2D.OPEN));
    g.fillOval(x+20,y-5,10,10);
    g.setColor(Color.WHITE);
    g.fillOval(x+22,y-3,6,6);
    g2.setStroke(Settings.stroke2);
  }
}
