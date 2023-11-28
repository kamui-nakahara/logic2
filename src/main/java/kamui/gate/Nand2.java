package kamui.gate;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.geom.Arc2D;
import kamui.system.Settings;

public class Nand2 extends Gate{
  public Nand2(int x,int y){
    super(x,y,2,1);
  }
  public String getName(){
    return "nand2";
  }
  public Gate copy(){
    Gate gate=new Nand2(x,y);
    return gate;
  }
  public void calc(){
    super.calc();
    boolean a=!(value.get(0) && value.get(1));
    outputs.put(terminal.get(0),a);
  }
  public void draw(Graphics g,boolean a){
    super.draw(g,a);
    g.setColor(color);
    Graphics2D g2=(Graphics2D)g;
    g.drawLine(x-20,y-20,x-20,y+20);
    g.drawLine(x-20,y-20,x,y-20);
    g.drawLine(x-20,y+20,x,y+20);
    g2.draw(new Arc2D.Double(x-20,y-20,40,40,90,-180,Arc2D.OPEN));
    g.fillOval(x+20,y-5,10,10);
    g.setColor(Color.WHITE);
    g.fillOval(x+22,y-3,6,6);
    g2.setStroke(Settings.stroke2);
  }
}
