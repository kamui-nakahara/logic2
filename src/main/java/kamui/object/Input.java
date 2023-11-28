package kamui.object;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Point;
import java.awt.geom.Arc2D;
import kamui.gate.Gate;
import kamui.Main;
import kamui.system.Settings;

public class Input extends Gate{
  public Input(int x,int y){
    super(x,y,0,1);
    draw_input_line_flag=false;
    input=true;
  }
  public String getName(){
    return "input";
  }
  public Gate copy(){
    Gate gate=new Input(x,y);
    return gate;
  }
  public void update(Main main){
    if (getRect().contains(main.mousePoint) && main.mousePressed && !main.old_mousePressed){
      Point p=new Point(40,0);
      outputs.put(p,!outputs.get(p));
    }
  }
  public void draw(Graphics g,boolean a){
    super.draw(g,a);
    g.setColor(color);
    Graphics2D g2=(Graphics2D)g;
    g.drawOval(x-40,y-20,60,40);
    if (outputs.get(new Point(40,0))){
      g.drawLine(x-10,y-10,x-10,y+10);
    }else{
      g.drawOval(x-20,y-10,20,20);
    }
    g2.setStroke(Settings.stroke2);
  }
}
