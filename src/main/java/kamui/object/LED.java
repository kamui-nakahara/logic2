package kamui.object;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Color;
import java.awt.Rectangle;
import java.util.ArrayList;
import kamui.gate.Gate;
import kamui.Main;

public class LED extends Gate{
  public LED(int x,int y){
    super(x,y,1,0);
    data.add(x);
    data.add(y);
    data.add(0);
    data.add(0);
  }
  public Gate copy(){
    Gate gate=new LED(x,y);
    ArrayList<Integer> d=new ArrayList<>();
    for (int i:data){
      d.add(i);
    }
    gate.data=d;
    return gate;
  }
  public void update(Main main){
    if ((new Rectangle(data.get(0)-10,data.get(1)-10,20,20)).contains(main.mousePoint)){
      data.set(2,1);
      if (main.mousePressed && !main.old_mousePressed){
	data.set(3,1);
      }
    }else if (getRect().contains(main.mousePoint)){
      data.set(2,1);
    }else{
      data.set(2,0);
    }
    if (data.get(3)==1){
      data.set(0,main.mousePoint.x);
      data.set(1,main.mousePoint.y);
    }
    if (!main.mousePressed && main.old_mousePressed){
      data.set(3,0);
    }
  }
  public void setPoint(Point p){
    int x1=x;
    int y1=y;
    int x2=p.x;
    int y2=p.y;
    data.set(0,data.get(0)+(x2-x1));
    data.set(1,data.get(1)+(y2-y1));
    x=p.x;
    y=p.y;
  }
  public void setPoint(Point p,boolean f){
    setPoint(p);
    updateLines();
  }
  public void draw(Graphics g,boolean a){
    super.draw(g,a);
    g.setColor((data.get(2)==0) ? color : Color.BLUE);
    g.drawRect(x-20,y-20,40,40);
    g.drawRect(data.get(0)-10,data.get(1)-10,20,20);
    if (inputs.get(new Point(-40,0))){
      g.fillRect(data.get(0)-10,data.get(1)-10,20,20);
    }
  }
}
