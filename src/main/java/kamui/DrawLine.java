package kamui;

import java.awt.Graphics;
import java.awt.Color;
import java.util.ArrayList;
import kamui.object.Line;

public class DrawLine{
  Main main;
  int beginx;
  int beginy;
  Line line;
  boolean pressed=false;
  Color color=new Color(0,0,0,128);
  public DrawLine(Main main){
    this.main=main;
  }
  public void update(){
    if (main.wire_flag){
      if (!main.mousePressed && main.old_mousePressed){
	if (pressed){
	  line=new Line(beginx,beginy,main.mousePoint.x,main.mousePoint.y);
	  main.lines.add(line);
	  pressed=false;
	}else{
	  beginx=main.mousePoint.x;
	  beginy=main.mousePoint.y;
	  pressed=true;
	}
      }
      if (pressed){
	line=new Line(beginx,beginy,main.mousePoint.x,main.mousePoint.y);
      }
    }
  }
  public void draw(Graphics g){
    for (Line line:main.lines){
      line.draw(g,main.debug);
    }
    if (pressed){
      line.draw(g,main.debug);
    }else if (main.wire_flag){
      g.setColor(color);
      g.fillOval(main.mousePoint.x-4,main.mousePoint.y-4,8,8);
    }
  }
}
