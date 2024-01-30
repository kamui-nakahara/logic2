package kamui.mode;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import kamui.gate.Gate;
import kamui.object.Line;
import kamui.Main;

public class GateDelete{
  Main main;
  Rectangle rect;
  int x;
  int y;
  boolean delete=false;
  public GateDelete(Main main){
    this.main=main;
  }
  public void update(){
    if (main.gate_delete){
      for (Gate g:main.gates){
	Rectangle r=g.getRect();
	if (r.contains(main.mousePoint) || (delete && r.intersects(rect))){
	  g.color=Color.BLUE;
	}else{
	  g.color=g.originalcolor;
	}
      }
      for (Line line:main.lines){
	if (delete && rect.contains(line.getCenter())){
	  line.color=Color.BLUE;
	}else{
	  line.color=Color.BLACK;
	}
      }
      if (main.mousePressed && !main.old_mousePressed){
	delete=true;
	x=main.mousePoint.x;
	y=main.mousePoint.y;
      }
      if (delete){
	rect=new Rectangle(
	    ((x>main.mousePoint.x) ? main.mousePoint.x : x),
	    ((y>main.mousePoint.y) ? main.mousePoint.y : y),
	    Math.abs(x-main.mousePoint.x),
	    Math.abs(y-main.mousePoint.y));
	if (!main.mousePressed && main.old_mousePressed){
	  for (Gate g:main.gates){
	    if (g.getRect().intersects(rect)){
	      main.remove.add(g);
	    }
	  }
	  ArrayList<Line> remove=new ArrayList<>();
	  for (Line line:main.lines){
	    if (rect.contains(line.getCenter())){
	      remove.add(line);
	    }
	  }
	  main.lines.removeAll(remove);
	  delete=false;
	}
      }
    }
  }
  public void draw(Graphics g){
    if (main.gate_delete && delete){
      g.setColor(Color.CYAN);
      Graphics2D g2=(Graphics2D)g;
      g2.draw(rect);
    }
  }
}
