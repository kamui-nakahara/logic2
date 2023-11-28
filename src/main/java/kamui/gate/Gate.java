package kamui.gate;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Point;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.FontMetrics;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.io.Serializable;
import kamui.object.Line;
import kamui.Main;
import kamui.system.Settings;

public class Gate implements Serializable{
  static Font font=new Font("",Font.PLAIN,15);
  public int x;//中心座標のx
  public int y;//中心座標のy
  int input_y_max=0;
  int output_y_max=0;
  public String title="";
  public boolean draw_input_line_flag=true;
  boolean draw_output_line_flag=false;
  public HashMap<Point,Boolean> inputs=new HashMap<>();
  public HashMap<Point,Boolean> outputs=new HashMap<>();
  HashMap<Point,ArrayList<Line>> lines=new HashMap<>();
  HashMap<Point,Point> points=new HashMap<>();
  public HashMap<Point,Point> points_r=new HashMap<>();
  HashMap<Point,Boolean> flag=new HashMap<>();
  public ArrayList<Gate> block_gates=new ArrayList<>();
  public ArrayList<Line> block_lines=new ArrayList<>();
  public HashMap<Point,String> input_title=new HashMap<>();
  public HashMap<Point,String> output_title=new HashMap<>();
  ArrayList<Gate> block_inputs;
  ArrayList<Gate> block_outputs;
  ArrayList<Boolean> value;
  ArrayList<Point> terminal;
  public Color color=Color.BLACK;
  public boolean input=false;
  public boolean output=false;
  public Gate(int x,int y,int inputs,int outputs){
    //int inputsは入力端子の数 int outputsは出力端子の数
    this.x=x;
    this.y=y;
    int is=inputs;
    int os=outputs;
    if (inputs%2==1){
      inputs--;
    }
    for (int i=inputs/2-1;i>=0;i--){
      this.inputs.put(new Point(-40,-i*10-10),false);
    }
    if (is%2==1){
      this.inputs.put(new Point(-40,0),false);
    }
    for (int i=0;i<inputs/2;i++){
      this.inputs.put(new Point(-40,i*10+10),false);
    }
    input_y_max=inputs/2*10+10;
    if (outputs%2==1){
      outputs--;
    }
    for (int i=outputs/2-1;i>=0;i--){
      this.outputs.put(new Point(40,-i*10-10),false);
    }
    if (os%2==1){
      this.outputs.put(new Point(40,0),false);
    }
    for (int i=0;i<outputs/2;i++){
      this.outputs.put(new Point(40,i*10+10),false);
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
    ArrayList<Point> ip=new ArrayList<>();
    terminal=new ArrayList<>();
    for (Point p:inputs.keySet()){
      ip.add(p);
    }
    for (Point p:outputs.keySet()){
      terminal.add(p);
    }
    Point[] i=ip.toArray(new Point[ip.size()]);
    Arrays.sort(i,new PointComparator());
    for (int j=0;j<i.length;j++){
      value.add(inputs.get(i[j]));
    }
    Point[] o=terminal.toArray(new Point[terminal.size()]);
    Arrays.sort(o,new PointComparator());
    terminal=new ArrayList<>();
    for (int j=0;j<o.length;j++){
      terminal.add(o[j]);
    }
  }
  public void update(Main main){
  }
  public void setPoint(Point point){
    x=point.x;
    y=point.y;
  }
  public void draw(Graphics g){
    draw(g,false);
  }
  public String getName(){
    return "original";
  }
  void drawStringCenter(Graphics g,String text,int x,int y){
    FontMetrics fm=g.getFontMetrics();
    Rectangle rectText=fm.getStringBounds(text,g).getBounds();
    int X=x-rectText.width/2;
    int Y=y-rectText.height/2+fm.getMaxAscent();
    g.drawString(text,X,Y);
  }
  public void draw(Graphics g,boolean a){
    Graphics2D g2=(Graphics2D)g;
    g.setColor(color);
    g.setFont(font);
    drawStringCenter(g,title,x,y);
    g2.setStroke(Settings.stroke1);
    for (Point point:inputs.keySet()){
      if (inputs.get(point) && a){
	g.setColor(Color.RED);
      }else{
	g.setColor(color);
      }
      g.drawLine(x+point.x,y+point.y,x+point.x+20,y+point.y);
      g.fillOval(x+point.x-4,y+point.y-4,8,8);
    }
    for (Point point:outputs.keySet()){
      if (outputs.get(point) && a){
	g.setColor(Color.RED);
      }else{
	g.setColor(color);
      }
      g.drawLine(x+point.x,y+point.y,x+point.x-20,y+point.y);
      g.fillOval(x+point.x-4,y+point.y-4,8,8);
    }
    g.setColor(color);
    if (draw_input_line_flag){
      g.drawLine(x-20,y+input_y_max,x-20,y-input_y_max);
    }
    if (draw_output_line_flag){
      g.drawLine(x+20,y+output_y_max,x+20,y-output_y_max);
    }
  }
}
class PointComparator implements Comparator<Point>{
  @Override
  public int compare(Point p1,Point p2){
    return p1.y-p2.y;
  }
}
