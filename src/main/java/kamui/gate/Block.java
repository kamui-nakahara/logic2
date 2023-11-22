package kamui.gate;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;

public class Block extends Gate{
  public Block(int x,int y,int inputs,int outputs){
    super(x,y,inputs,outputs);
    draw_output_line_flag=true;
  }
  public Gate copy(){
    Gate gate=new Block(x,y,inputs.size(),outputs.size());
    gate.draw_output_line_flag=true;
    return gate;
  }
  public void draw(Graphics g){
    super.draw(g);
    Graphics2D g2=(Graphics2D)g;
    g.setColor(color);
    g.drawRect(x-20,y-20,40,40);
    g2.setStroke(stroke2);
  }
}
