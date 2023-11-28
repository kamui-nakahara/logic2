package kamui.object;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Point;
import kamui.gate.Gate;
import kamui.system.Settings;

public class Output extends Gate{
  public Output(int x,int y){
    super(x,y,1,0);
    output=true;
  }
  public String getName(){
    return "output";
  }
  public Gate copy(){
    Gate gate=new Output(x,y);
    return gate;
  }
  public void draw(Graphics g,boolean a){
    super.draw(g,a);
    Graphics2D g2=(Graphics2D)g;
    g.setColor(color);
    g.drawRect(x-20,y-20,60,40);
    if (inputs.get(new Point(-40,0))){
      g.drawLine(x+10,y-10,x+10,y+10);
    }else{
      g.drawOval(x,y-10,20,20);
    }
    g2.setStroke(Settings.stroke2);
  }
}
