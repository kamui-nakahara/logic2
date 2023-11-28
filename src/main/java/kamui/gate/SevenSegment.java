package kamui.gate;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Point;
import java.awt.Color;
import kamui.system.Settings;

public class SevenSegment extends Gate{
  public SevenSegment(int x,int y){
    super(x,y,7,0);
  }
  public String getName(){
    return "sevensegment";
  }
  public Gate copy(){
    Gate gate=new SevenSegment(x,y);
    return gate;
  }
  public void draw(Graphics g,boolean a){
    super.draw(g,a);
    g.setColor(color);
    Graphics2D g2=(Graphics2D)g;
    Rectangle[] rects=new Rectangle[]{
      new Rectangle(x,y-30,40,10),
      new Rectangle(x-10,y-20,10,40),
      new Rectangle(x+40,y-20,10,40),
      new Rectangle(x,y+20,40,10),
      new Rectangle(x-10,y+30,10,40),
      new Rectangle(x+40,y+30,10,40),
      new Rectangle(x,y+70,40,10)
    };
    for (int i=0;i<7;i++){
      if (inputs.get(new Point(-40,i*10-30))){
	g2.fill(rects[i]);
      }else{
	g2.draw(rects[i]);
      }
    }
    g.drawRect(x-20,y-40,80,130);
  }
}
