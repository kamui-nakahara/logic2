package kamui.gate;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Arc2D;
import java.awt.Color;
import kamui.system.Settings;

public class Or2 extends Gate{
  public Or2(int x,int y){
    super(x,y,2,1);
    draw_input_line_flag=false;
  }
  public String getName(){
    return "or2";
  }
  public Gate copy(){
    Gate gate=new Or2(x,y);
    gate.draw_input_line_flag=false;
    return gate;
  }
  public void calc(){
    super.calc();
    boolean a=value.get(0) || value.get(1);
    outputs.put(terminal.get(0),a);
  }
  public void draw(Graphics g,boolean a){
    super.draw(g,a);
    Graphics2D g2=(Graphics2D)g;
    g.drawLine(x-25,y-20,x-10,y-20);
    g.drawLine(x-25,y+20,x-10,y+20);
    g2.draw(new Arc2D.Double(x-99,y-40,80,80,-30,60,Arc2D.OPEN));
    g2.draw(new Arc2D.Double(x-39,y-20,60,60,90,-70,Arc2D.OPEN));
    g2.draw(new Arc2D.Double(x-39,y-40,60,60,-90,70,Arc2D.OPEN));
    g2.setStroke(Settings.stroke2);
  }
}
