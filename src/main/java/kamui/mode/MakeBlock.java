package kamui.mode;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import kamui.Main;
import kamui.dialog.title.Title;
import kamui.gate.Gate;
import kamui.gate.Block;
import kamui.object.Line;

public class MakeBlock{
  Main main;
  Rectangle rect=new Rectangle(-10,-10,0,0);
  int x;
  int y;
  boolean pressed=false;
  Menu menu;
  public MakeBlock(Main main){
    this.main=main;
    this.menu=main.menu;
  }
  public void update(){
    if (main.make_block){
      if (main.mousePressed && !main.old_mousePressed){
	pressed=true;
	x=main.mousePoint.x;
	y=main.mousePoint.y;
	rect=new Rectangle(x,y,0,0);
      }
      if (main.mousePressed && main.old_mousePressed){
	rect=new Rectangle(
	    (x>main.mousePoint.x) ? main.mousePoint.x : x,
	    (y>main.mousePoint.y) ? main.mousePoint.y : y,
	    Math.abs(main.mousePoint.x-x),
	    Math.abs(main.mousePoint.y-y)
	    );
	for (Gate gate:main.gates){
	  if (gate.getRect().intersects(rect)){
	    gate.color=Color.BLUE;
	  }else{
	    gate.color=Color.BLACK;
	  }
	}
	for (Line line:main.lines){
	  if (rect.contains(line.getCenter())){
	    line.color=Color.BLUE;
	  }else{
	    line.color=Color.BLACK;
	  }
	}
      }
      if (!main.mousePressed && main.old_mousePressed){
	pressed=false;
	ArrayList<Gate> gates=new ArrayList<>();
	ArrayList<Line> lines=new ArrayList<>();
	ArrayList<Gate> inputs=new ArrayList<>();
	ArrayList<Gate> outputs=new ArrayList<>();
	ArrayList<Gate> removeGates=new ArrayList<>();
	ArrayList<Line> removeLines=new ArrayList<>();
	for (Gate gate:main.gates){
	  if (gate.getRect().intersects(rect)){
	    Gate g=gate.copy();
	    for (Point p:gate.inputs.keySet()){
	      g.inputs.put(p,gate.inputs.get(p));
	    }
	    for (Point p:gate.outputs.keySet()){
	      g.outputs.put(p,gate.outputs.get(p));
	    }
	    gates.add(g);
	    removeGates.add(gate);
	    switch (gate.getName()){
	      case "input":
		inputs.add(g);
		break;
	      case "output":
		outputs.add(g);
		break;
	    }
	  }
	}
	for (Line line:main.lines){
	  if (rect.contains(line.getCenter())){
	    lines.add(line.copy());
	    removeLines.add(line);
	  }
	}
	Gate[] i=inputs.toArray(new Gate[inputs.size()]);
	Arrays.sort(i,new GateComparator());
	inputs=new ArrayList<>();
	for (int j=0;j<i.length;j++){
	  inputs.add(i[j]);
	}
	Gate[] o=outputs.toArray(new Gate[outputs.size()]);
	Arrays.sort(o,new GateComparator());
	outputs=new ArrayList<>();
	for (int j=0;j<o.length;j++){
	  outputs.add(o[j]);
	}
	if (gates.size()!=0 || lines.size()!=0){
	  main.gates.removeAll(removeGates);
	  main.lines.removeAll(removeLines);
	  Block block=new Block(-10,-10,inputs,outputs,gates,lines);
	  Title title=new Title(main.screen.display,rect,block);
	}
      }
    }
  }
  public void draw(Graphics g){
    if (main.make_block){
      Graphics2D g2=(Graphics2D)g;
      if (pressed){
	g.setColor(Color.CYAN);
	g2.draw(rect);
      }
    }
  }
}
class GateComparator implements Comparator<Gate>{
  @Override
  public int compare(Gate g1,Gate g2){
    return g1.y-g2.y;
  }
}
