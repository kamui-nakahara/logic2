package kamui;

import java.awt.Dimension;
import java.awt.Graphics;

public class Main{
  Screen screen;
  public Main(Screen screen){
    this.screen=screen;
  }
  void setSize(int width,int height){
    screen.setPreferredSize(new Dimension(width,height));
  }
  void update(){
  }
  void draw(Graphics g){
  }
}
