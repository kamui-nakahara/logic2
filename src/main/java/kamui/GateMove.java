package kamui;

import java.awt.Color;
import java.awt.Point;
import java.util.HashMap;
import java.util.ArrayList;
import kamui.gate.Gate;
import kamui.object.Line;

public class GateMove{
  Main main;
  Gate gate;
  boolean selected=false;
  HashMap<Point,ArrayList<Line>> lines;
  public GateMove(Main main){
    this.main=main;
  }
  Point add(Point p,int x,int y){
    return new Point(p.x+x,p.y+y);
  }
  public void update(){
    if (main.gate_move){
      if (selected){
	gate.setPoint(main.mousePoint,true);
	if (!main.mousePressed && main.old_mousePressed){
	  selected=false;
	}
      }else{
	for (Gate g:main.gates){
	  if (g.getRect().contains(main.mousePoint)){
	    g.color=Color.BLUE;
	    if (main.mousePressed && !main.old_mousePressed){
	      gate=g;
	      gate.scanLines(main.lines);
	      selected=true;
	    }
	  }else{
	    g.color=Color.BLACK;
	  }
	}
      }
    }
  }
}
