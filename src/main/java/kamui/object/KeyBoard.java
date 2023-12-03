package kamui.object;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.Point;
import kamui.gate.Gate;
import kamui.Main;
import kamui.dialog.keyset.SetKey;

public class KeyBoard extends Gate{
  public KeyBoard(int x,int y){
    super(x,y,0,1);
    int keycode=KeyEvent.VK_H;
    draw_input_line_flag=false;
    draw_output_line_flag=false;
    data.add(keycode);
    title="H";
  }
  public String getName(){
    return "keyboard";
  }
  public Gate copy(){
    Gate gate=new KeyBoard(x,y);
    gate.data.set(0,this.data.get(0));
    gate.title=title;
    return gate;
  }
  public void update(Main main){
    if (getRect().contains(main.mousePoint) && main.mousePressed && !main.old_mousePressed){
      new SetKey(main.screen.display,this);
    }
    boolean value=false;
    if (main.keys.containsKey(data.get(0))){
      value=main.keys.get(data.get(0));
    }
    outputs.put(new Point(40,0),value);
  }
  public void draw(Graphics g,boolean a){
    super.draw(g,a);
    g.setColor(color);
    g.drawRect(x-20,y-20,40,40);
    if (outputs.get(new Point(40,0))){
      g.drawRect(x-10,y-10,20,20);
    }else{
      g.drawOval(x-15,y-15,30,30);
    }
  }
}
