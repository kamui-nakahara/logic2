package kamui.mode;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import kamui.gate.Gate;
import kamui.object.Line;
import kamui.Main;
import kamui.system.Settings;

public class GateCopy{
  Main main;
  ArrayList<Gate> gates=new ArrayList<>();
  ArrayList<Line> lines=new ArrayList<>();
  Rectangle rect;
  Gate selectedGate;
  int x;
  int y;
  boolean selected=false;
  boolean pressed=false;
  public GateCopy(Main main){
    this.main=main;
  }
  public void init(){
    selected=false;
    pressed=false;
    gates=new ArrayList<>();
    lines=new ArrayList<>();
  }
  public HashMap<Point,Boolean> copy(HashMap<Point,Boolean> a){
    HashMap<Point,Boolean> b=new HashMap<>();
    for (Point p:a.keySet()){
      b.put(p,a.get(p));
    }
    return b;
  }
  public void update(){
    if (main.gate_copy || main.gate_move){
      if (selected){
	x=main.mousePoint.x;
	y=main.mousePoint.y;
	if (pressed && !main.mousePressed && main.old_mousePressed){
	  if (gates.size()!=0 || lines.size()!=0){
	    for (Gate gate:gates){
	      Gate gate2=gate.copy();
	      gate2.inputs=copy(gate.inputs);
	      gate2.outputs=copy(gate.outputs);
	      gate2.setPoint(new Point(gate.x+x,gate.y+y));
	      main.gates.add(gate2);
	      if (main.width-100<gate2.x){
		main.width+=Settings.width/2;
	      }
	      if (main.height-100<gate2.y){
		main.height+=Settings.height/2;
	      }
	      main.setSize(main.width,main.height);
	    }
	    for (Line l:lines){
	      Line line=new Line(l.x1+x,l.y1+y,l.x2+x,l.y2+y);
	      line.value=l.value;
	      main.lines.add(line);
	      if (main.width-100<line.x1 || main.width-100<line.x2){
		main.width+=Settings.width/2;
	      }
	      if (main.height-100<line.y1 || main.height-100<line.y2){
		main.height+=Settings.height/2;
	      }
	      main.setSize(main.width,main.height);
	    }
	  }
	  init();
	}
	if (main.mousePressed && !main.old_mousePressed){
	  pressed=true;
	}
      }else{
	if (pressed){
	  for (Gate g:main.gates){
	    Rectangle r=g.getRect();
	    if (r.contains(main.mousePoint) || (pressed && r.intersects(rect))){
	      g.color=Color.BLUE;
	    }else{
	      g.color=Color.BLACK;
	    }
	  }
	}
	for (Line line:main.lines){
	  if (pressed && rect.contains(line.getCenter())){
	    line.color=Color.BLUE;
	  }else{
	    line.color=Color.BLACK;
	  }
	}
	if (main.mousePressed && !main.old_mousePressed){
	  pressed=true;
	  x=main.mousePoint.x;
	  y=main.mousePoint.y;
	  rect=new Rectangle(
	      ((x>main.mousePoint.x) ? main.mousePoint.x : x),
	      ((y>main.mousePoint.y) ? main.mousePoint.y : y),
	      Math.abs(x-main.mousePoint.x),
	      Math.abs(y-main.mousePoint.y));
	}
	if (main.mousePressed && main.old_mousePressed){
	  rect=new Rectangle(
	      ((x>main.mousePoint.x) ? main.mousePoint.x : x),
	      ((y>main.mousePoint.y) ? main.mousePoint.y : y),
	      Math.abs(x-main.mousePoint.x),
	      Math.abs(y-main.mousePoint.y));
	}
	if (!main.mousePressed && main.old_mousePressed){
	  pressed=false;
	  selected=true;
	  x=main.mousePoint.x;
	  y=main.mousePoint.y;
	  for (Gate g:main.gates){
	    if (g.getRect().intersects(rect)){
	      if (main.gate_move){
		main.remove.add(g);
	      }
	      Gate gate=g.copy();
	      gate.setPoint(new Point(g.x-x,g.y-y));
	      gate.inputs=g.inputs;
	      gate.outputs=g.outputs;
	      gates.add(gate);
	      g.color=Color.BLACK;
	    }
	  }
	  ArrayList<Line> remove=new ArrayList<>();
	  for (Line l:main.lines){
	    if (rect.contains(l.getCenter())){
	      if (main.gate_move){
		remove.add(l);
	      }
	      Line line=new Line(l.x1-x,l.y1-y,l.x2-x,l.y2-y);
	      line.value=l.value;
	      lines.add(line);
	      l.color=Color.BLACK;
	    }
	  }
	  main.lines.removeAll(remove);
	  if (gates.size()==0 && lines.size()==0){
	    selected=false;
	  }
	}
      }
    }
  }
  public void draw(Graphics g){
    if (main.gate_copy || main.gate_move){
      if (pressed){
	g.setColor(Color.CYAN);
	g.drawRect(
	    ((x>main.mousePoint.x) ? main.mousePoint.x : x),
	    ((y>main.mousePoint.y) ? main.mousePoint.y : y),
	    Math.abs(x-main.mousePoint.x),
	    Math.abs(y-main.mousePoint.y));
      }
      if (selected){
	if (gates.size()!=0 || lines.size()!=0){
	  for (Gate gate:gates){
	    Gate gate2=gate.copy();
	    gate2.setPoint(new Point(gate.x+x,gate.y+y));
	    gate2.draw(g);
	  }
	  for (Line l:lines){
	    Line line=new Line(l.x1+x,l.y1+y,l.x2+x,l.y2+y);
	    line.draw(g,main.debug);
	  }
	}else{
	  selectedGate.draw(g);
	}
      }
    }
  }
}
