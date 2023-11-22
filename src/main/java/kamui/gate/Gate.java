package kamui.gate;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Point;
import java.awt.BasicStroke;
import java.awt.Rectangle;
import java.util.HashMap;
import java.util.ArrayList;
import kamui.object.Line;
import kamui.Main;

public class Gate{
  public int x;//中心座標のx
  public int y;//中心座標のy
  int input_y_max=0;
  int output_y_max=0;
  public boolean draw_input_line_flag=true;
  boolean draw_output_line_flag=false;
  public HashMap<Point,Boolean> inputs=new HashMap<>();
  public HashMap<Point,Boolean> outputs=new HashMap<>();
  HashMap<Point,ArrayList<Line>> lines=new HashMap<>();
  HashMap<Point,Point> points=new HashMap<>();
  public HashMap<Point,Point> points_r=new HashMap<>();
  HashMap<Point,Boolean> flag=new HashMap<>();
  ArrayList<Boolean> value;
  ArrayList<Point> terminal;
  static BasicStroke stroke1=new BasicStroke(3);
  public static BasicStroke stroke2=new BasicStroke(1);
  public Color color=Color.BLACK;
  public boolean input=false;
  public boolean output=false;
  public Gate(int x,int y,int inputs,int outputs){
    //int inputsは入力端子の数 int outputsは出力端子の数
    this.x=x;
    this.y=y;
    if (inputs%2==1){
      this.inputs.put(new Point(-40,0),false);
      inputs--;
    }
    for (int i=0;i<inputs/2;i++){
      this.inputs.put(new Point(-40,i*10+10),false);
      this.inputs.put(new Point(-40,-i*10-10),false);
    }
    input_y_max=inputs/2*10+10;
    if (outputs%2==1){
      this.outputs.put(new Point(40,0),false);
      outputs--;
    }
    for (int i=0;i<outputs/2;i++){
      this.outputs.put(new Point(40,i*10+10),false);
      this.outputs.put(new Point(40,-i*10-10),false);
    }
    output_y_max=outputs/2*10+10;
  }
  public Rectangle getRect(){
    return new Rectangle(x-20,y-20,40,40);
  }
  public HashMap<Point,Boolean> getAbsInputs(){
    HashMap<Point,Boolean> absInputs=new HashMap<>();
    for (Point point:inputs.keySet()){
      absInputs.put(new Point(point.x+x,point.y+y),inputs.get(point));
    }
    return absInputs;
  }
  public HashMap<Point,Boolean> getAbsOutputs(){
    HashMap<Point,Boolean> absOutputs=new HashMap<>();
    for (Point point:outputs.keySet()){
      absOutputs.put(new Point(point.x+x,point.y+y),outputs.get(point));
    }
    return absOutputs;
  }
  public void setPoint(Point p,boolean f){
    x=p.x;
    y=p.y;
    updateLines();
  }
  public void updateLines(){
    for (Point p:lines.keySet()){
      for (Line line:lines.get(p)){
	Point pp=new Point(p.x+x,p.y+y);
	if (points.get(p).equals(line.getPoint1()) && flag.get(p)){
	  line.setPoint1(pp);
	}
	if (points.get(p).equals(line.getPoint2()) && !flag.get(p)){
	  line.setPoint2(pp);
	}
	points.put(p,pp);
      }
    }
  }
  public void setPoints_r(){
    points_r=new HashMap<>();
    for (Point p:inputs.keySet()){
      points_r.put(new Point(p.x+x,p.y+y),new Point(p.x,p.y));
    }
    for (Point p:outputs.keySet()){
      points_r.put(new Point(p.x+x,p.y+y),new Point(p.x,p.y));
    }
  }
  public void scanLines(ArrayList<Line> lines){
    this.lines=new HashMap<>();
    points=new HashMap<>();
    flag=new HashMap<>();
    for (Point point:inputs.keySet()){
      ArrayList<Line> l=new ArrayList<>();
      Point p=new Point(point.x+x,point.y+y);
      for (Line line:lines){
	if (line.getPoint1().equals(p)){
	  l.add(line);
	  points.put(point,p);
	  flag.put(point,true);
	}
	if (line.getPoint2().equals(p)){
	  l.add(line);
	  points.put(point,p);
	  flag.put(point,false);
	}
      }
      this.lines.put(point,l);
    }
    for (Point point:outputs.keySet()){
      ArrayList<Line> l=new ArrayList<>();
      Point p=new Point(point.x+x,point.y+y);
      for (Line line:lines){
	if (line.getPoint1().equals(p)){
	  l.add(line);
	  points.put(point,p);
	  flag.put(point,true);
	}
	if (line.getPoint2().equals(p)){
	  l.add(line);
	  points.put(point,p);
	  flag.put(point,false);
	}
      }
      this.lines.put(point,l);
    }
  }
  public Gate copy(){
    return this;
  }
  public void calc(){
    value=new ArrayList<>();
    terminal=new ArrayList<>();
    for (Point p:inputs.keySet()){
      value.add(inputs.get(p));
    }
    for (Point p:outputs.keySet()){
      terminal.add(p);
    }
  }
  public void update(Main main){
  }
  public void setPoint(Point point){
    x=point.x;
    y=point.y;
  }
  public void draw(Graphics g){
    g.setColor(color);
    Graphics2D g2=(Graphics2D)g;
    g2.setStroke(stroke1);
    for (Point point:inputs.keySet()){
      g.drawLine(x+point.x,y+point.y,x+point.x+20,y+point.y);
      g.fillOval(x+point.x-4,y+point.y-4,8,8);
    }
    for (Point point:outputs.keySet()){
      g.drawLine(x+point.x,y+point.y,x+point.x-20,y+point.y);
      g.fillOval(x+point.x-4,y+point.y-4,8,8);
    }
    if (draw_input_line_flag){
      g.drawLine(x-20,y+input_y_max,x-20,y-input_y_max);
    }
    if (draw_output_line_flag){
      g.drawLine(x+20,y+output_y_max,x+20,y-output_y_max);
    }
  }
}
