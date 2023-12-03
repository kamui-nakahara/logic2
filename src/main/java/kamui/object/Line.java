package kamui.object;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Point;
import java.awt.BasicStroke;
import java.util.List;
import java.io.Serializable;
import kamui.system.Settings;
import kamui.Main;
import kamui.gate.Gate;

public class Line implements Serializable{
  public int x1;
  public int y1;
  public int x2;
  public int y2;
  public int begin=0;
  public boolean value=false;
  public Color color=Color.BLACK;
  public Line(int x1,int y1,int x2,int y2){
    this.x1=x1;
    this.y1=y1;
    this.x2=x2;
    this.y2=y2;
  }
  public Line copy(){
    Line line=new Line(x1,y1,x2,y2);
    line.begin=this.begin;
    line.value=this.value;
    return line;
  }
  public Point getCenter(){
    return new Point((x1+x2)/2,(y1+y2)/2);
  }
  public Point getPoint1(){
    return new Point(x1,y1);
  }
  public Point getPoint2(){
    return new Point(x2,y2);
  }
  public void setPoint1(Point p){
    x1=p.x;
    y1=p.y;
  }
  public void setPoint2(Point p){
    x2=p.x;
    y2=p.y;
  }
  public void update(){
  }
  public void draw(Graphics g,boolean a){
    Graphics2D g2=(Graphics2D)g;
    if (value && a){
      g.setColor(Color.RED);
    }else{
      g.setColor(color);
    }
    g2.setStroke(Settings.stroke1);
    g.drawLine(x1,y1,x2,y2);
    g2.setStroke(Settings.stroke2);
    int terminals=0;
    for (Gate gate:Main.gates){
      for (Point p:gate.getAbsInputs().keySet()){
	if (p.equals(getPoint1())){
	  terminals++;
	}
      }
      for (Point p:gate.getAbsOutputs().keySet()){
	if (p.equals(getPoint1())){
	  terminals++;
	}
      }
    }
    for (Line line:Main.lines){
      if (line.getPoint1().equals(getPoint1())){
	terminals++;
      }
      if (line.getPoint1().equals(getPoint2())){
	terminals++;
      }
    }
    if (Main.wire_flag || Main.gate_put || Main.gate_delete || Main.gate_copy || Main.gate_move2 || Main.make_block || terminals>2){
      g.fillOval(x1-4,y1-4,8,8);
    }
    terminals=0;
    for (Gate gate:Main.gates){
      for (Point p:gate.getAbsInputs().keySet()){
	if (p.equals(getPoint2())){
	  terminals++;
	}
      }
      for (Point p:gate.getAbsOutputs().keySet()){
	if (p.equals(getPoint2())){
	  terminals++;
	}
      }
    }
    for (Line line:Main.lines){
      if (line.getPoint1().equals(getPoint2())){
	terminals++;
      }
      if (line.getPoint2().equals(getPoint2())){
	terminals++;
      }
    }
    if (Main.wire_flag || Main.gate_put || Main.gate_delete || Main.gate_copy || Main.gate_move2 || Main.make_block || terminals>2){
      g.fillOval(x2-4,y2-4,8,8);
    }
  }
}
