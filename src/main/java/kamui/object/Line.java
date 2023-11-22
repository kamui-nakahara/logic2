package kamui.object;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Point;
import java.awt.BasicStroke;

public class Line{
  BasicStroke stroke1=new BasicStroke(3);
  BasicStroke stroke2=new BasicStroke(1);
  int x1;
  int y1;
  int x2;
  int y2;
  public int begin=0;
  public boolean value=false;
  public Color color=Color.BLACK;
  public Line(int x1,int y1,int x2,int y2){
    this.x1=x1;
    this.y1=y1;
    this.x2=x2;
    this.y2=y2;
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
    g2.setStroke(stroke1);
    g.drawLine(x1,y1,x2,y2);
    g2.setStroke(stroke2);
    g.fillOval(x1-4,y1-4,8,8);
    g.fillOval(x2-4,y2-4,8,8);
  }
}
