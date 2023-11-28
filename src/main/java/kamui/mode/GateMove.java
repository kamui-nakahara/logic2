package kamui.mode;

import java.awt.Color;
import java.awt.Point;
import java.util.HashMap;
import java.util.ArrayList;
import kamui.gate.Gate;
import kamui.object.Line;
import kamui.system.Settings;
import kamui.Main;

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
    if (main.gate_move2){
      if (selected){
	gate.setPoint(main.mousePoint,true);
	if (!main.mousePressed && main.old_mousePressed){
	  if (main.width-100<gate.x){
	    main.width+=Settings.width/2;
	  }
	  if (main.height-100<gate.y){
	    main.height+=Settings.height/2;
	  }
	  main.setSize(main.width,main.height);
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
