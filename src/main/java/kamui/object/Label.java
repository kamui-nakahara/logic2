package kamui.object;

import java.awt.Graphics;
import java.awt.Font;
import kamui.gate.Gate;

public class Label extends Gate{
  public Label(int x,int y){
    super(x,y,0,0);
    draw_input_line_flag=false;
    data.add(50);
  }
  public String getName(){
    return "label";
  }
  public Gate copy(){
    Gate gate=new Label(x,y);
    gate.data.set(0,data.get(0));
    gate.title=title;
    gate.color=color;
    gate.originalcolor=originalcolor;
    return gate;
  }
  public void draw(Graphics g,boolean a){
    g.setColor(color);
    g.setFont(new Font("",Font.PLAIN,data.get(0)));
    drawstring(g,title,x,y);
  }
}
