package kamui.gate;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import kamui.object.Line;
import kamui.gate.Gate;
import kamui.system.Settings;
import kamui.Main;

public class Block extends Gate{
  public Block(int x,int y,ArrayList<Gate> inputs,ArrayList<Gate> outputs,ArrayList<Gate> gates,ArrayList<Line> lines){
    super(x,y,inputs.size(),outputs.size());
    draw_output_line_flag=true;
    this.block_gates=gates;
    this.block_lines=lines;
    this.block_inputs=inputs;
    this.block_outputs=outputs;
    this.data.add(0);
  }
  public String getName(){
    return "block";
  }
  public Gate copy(){
    ArrayList<Gate> g=new ArrayList<>();
    ArrayList<Line> l=new ArrayList<>();
    ArrayList<Gate> i=new ArrayList<>();
    ArrayList<Gate> o=new ArrayList<>();
    for (Gate gate:block_inputs){
      Gate ga=gate.copy();
      for (Point p:gate.inputs.keySet()){
	ga.inputs.put(p,gate.inputs.get(p));
      }
      for (Point p:gate.outputs.keySet()){
	ga.outputs.put(p,gate.outputs.get(p));
      }
      i.add(ga);
      g.add(ga);
    }
    for (Gate gate:block_outputs){
      Gate ga=gate.copy();
      for (Point p:gate.inputs.keySet()){
	ga.inputs.put(p,gate.inputs.get(p));
      }
      for (Point p:gate.outputs.keySet()){
	ga.outputs.put(p,gate.outputs.get(p));
      }
      o.add(ga);
      g.add(ga);
    }
    for (Gate gate:block_gates){
      if (!gate.getName().equals("input") && !gate.getName().equals("output")){
	Gate ga=gate.copy();
	for (Point p:gate.inputs.keySet()){
	  ga.inputs.put(p,gate.inputs.get(p));
	}
	for (Point p:gate.outputs.keySet()){
	  ga.outputs.put(p,gate.outputs.get(p));
	}
	g.add(ga);
      }
    }
    for (Line line:block_lines){
      l.add(line.copy());
    }
    Gate gate=new Block(x,y,i,o,g,l);
    if (gate.data.size()==0){
      gate.data.add(0);
    }
    gate.title=title;
    gate.inputs=new HashMap<>();
    gate.outputs=new HashMap<>();
    for (Point p:inputs.keySet()){
      gate.inputs.put(new Point(p.x,p.y),inputs.get(p));
    }
    for (Point p:outputs.keySet()){
      gate.outputs.put(new Point(p.x,p.y),outputs.get(p));
    }
    gate.draw_output_line_flag=true;
    return gate;
  }
  public void calc(){
    super.calc();
    for (int i=0;i<value.size();i++){
      block_inputs.get(i).outputs.put(new Point(40,0),value.get(i));
    }
    for (Gate g:block_gates){
      g.setPoints_r();
    }
    for (Gate g1:block_gates){
      HashMap<Point,Boolean> o1=g1.getAbsOutputs();
      for (Line l:block_lines){
	boolean coll=false;
	Point po=null;
	for (Point p:o1.keySet()){
	  if (l.getPoint1().equals(p)){
	    coll=true;
	    po=p;
	    l.begin=1;
	    break;
	  }
	  if (l.getPoint2().equals(p)){
	    coll=true;
	    po=p;
	    l.begin=2;
	    break;
	  }
	}
	if (coll){
	  l.value=g1.outputs.get(g1.points_r.get(po));
	}
      }
      for (Gate g2:block_gates){
	if (g1!=g2){
	  boolean coll=false;
	  HashMap<Point,Boolean> i2=g2.getAbsInputs();
	  Point po1=null;
	  Point po2=null;
	  for (Point p1:o1.keySet()){
	    for (Point p2:i2.keySet()){
	      if (p1.equals(p2)){
		coll=true;
		po1=p1;
		po2=p2;
		break;
	      }
	    }
	    if (coll){
	      break;
	    }
	  }
	  if (coll){
	    boolean a=g1.outputs.get(g1.points_r.get(po1));
	    g2.inputs.put(g2.points_r.get(po2),a);
	  }
	}
      }
    }
    for (Line l1:block_lines){
      for (Gate g:block_gates){
	HashMap<Point,Boolean> i=g.getAbsInputs();
	boolean coll=false;
	Point po=null;
	for (Point p:i.keySet()){
	  if (l1.begin==1){
	    if (l1.getPoint2().equals(p)){
	      coll=true;
	      po=p;
	      break;
	    }
	  }else if (l1.begin==2){
	    if (l1.getPoint1().equals(p)){
	      coll=true;
	      po=p;
	      break;
	    }
	  }
	}
	if (coll){
	  g.inputs.put(g.points_r.get(po),l1.value);
	}
      }
      for (Line l2:block_lines){
	if (l1!=l2){
	  if (l1.begin==1){
	    if (l1.getPoint2().equals(l2.getPoint1()) && l2.begin!=2){
	      l2.value=l1.value;
	      l2.begin=1;
	    }
	    if (l1.getPoint2().equals(l2.getPoint2()) && l2.begin!=1){
	      l2.value=l1.value;
	      l2.begin=2;
	    }
	  }else if (l1.begin==2){
	    if (l1.getPoint1().equals(l2.getPoint1()) && l2.begin!=2){
	      l2.value=l1.value;
	      l2.begin=1;
	    }
	    if (l1.getPoint1().equals(l2.getPoint2()) && l2.begin!=1){
	      l2.value=l1.value;
	      l2.begin=2;
	    }
	  }
	}
      }
    }
    for (Gate g:block_gates){
      g.calc();
    }
    for (int i=0;i<terminal.size();i++){
      outputs.put(terminal.get(i),block_outputs.get(i).inputs.get(new Point(-40,0)));
    }
  }
  public void update(Main main){
    for (Gate gate:block_gates){
      if (gate.getName().equals("block")){
	gate.update(main);
      }
    }
    if (getRect().contains(main.mousePoint)){
      data.set(0,1);
    }else{
      data.set(0,0);
    }
  }
  public void draw(Graphics g,boolean a){
    super.draw(g,a);
    Graphics2D g2=(Graphics2D)g;
    g.setColor(color);
    g.drawRect(x-20,y-20,40,40);
    g2.setStroke(Settings.stroke2);
    if (data.get(0)==1){
      int x1=block_gates.get(0).x;
      int y1=block_gates.get(0).y;
      int x2=block_gates.get(0).x;
      int y2=block_gates.get(0).y;
      for (Gate gate:block_gates){
	if (x1>gate.x){
	  x1=gate.x;
	}
	if (y1>gate.y){
	  y1=gate.y;
	}
	if (x2<gate.x){
	  x2=gate.x;
	}
	if (y2<gate.y){
	  y2=gate.y;
	}
      }
      Rectangle rect=new Rectangle(x1,y1,x2-x1,y2-y1);
      g.setColor(new Color(128,128,128,200));
      g.fillRect(x-rect.width/2-100,y-rect.height/2-100,rect.width+200,rect.height+200);
      g.setColor(Color.BLACK);
      g.drawRect(x-rect.width/2-100,y-rect.height/2-100,rect.width+200,rect.height+200);
      for (Gate gate:block_gates){
	Gate ga=gate.copy();
	ga.setPoint(new Point(ga.x-x1+x-rect.width/2,ga.y-y1+y-rect.height/2));
	ga.draw(g,a);
      }
      for (Line line:block_lines){
	Line l=new Line(line.x1-x1+x-rect.width/2,line.y1-y1+y-rect.height/2,line.x2-x1+x-rect.width/2,line.y2-y1+y-rect.height/2);
	l.draw(g,a);
      }
    }
  }
}
