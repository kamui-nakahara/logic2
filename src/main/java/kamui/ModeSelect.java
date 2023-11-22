package kamui;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Font;

public class ModeSelect{
  Main main;
  Color selectedColor=new Color(100,100,100);
  Color selectedTextColor=new Color(255,255,255);
  Color otherColor=new Color(255,255,255);
  Color otherTextColor=new Color(100,100,100);
  Rectangle[] choices=new Rectangle[5];
  String[] strings=new String[]{
    "配線",
    "配置",
    "移動",
    "削除",
    "設定"
  };
  int selected=-1;
  Font font=new Font("",Font.PLAIN,40);
  public ModeSelect(Main main){
    this.main=main;
  }
  public void open(){
    choices[0]=new Rectangle(main.mousePoint.x+10,main.mousePoint.y,200,50);
    choices[1]=new Rectangle(main.mousePoint.x+10,main.mousePoint.y+50,200,50);
    choices[2]=new Rectangle(main.mousePoint.x+10,main.mousePoint.y+100,200,50);
    choices[3]=new Rectangle(main.mousePoint.x+10,main.mousePoint.y+150,200,50);
    choices[4]=new Rectangle(main.mousePoint.x+10,main.mousePoint.y+200,200,50);
  }
  public void update(){
    if (main.mode_select){
      boolean collision=false;
      for (int i=0;i<5;i++){
	if (choices[i].contains(main.mousePoint)){
	  selected=i;
	  collision=true;
	  break;
	}
      }
      if (!main.mousePressed && main.old_mousePressed && collision){
	main.mode_select=false;
	main.wire_flag=false;
	main.gate_put=false;
	main.gate_move=false;
	main.gate_delete=false;
	main.gate_config=false;
	switch(selected){
	  case 0:
	    main.wire_flag=true;
	    break;
	  case 1:
	    main.gate_put=true;
	    break;
	  case 2:
	    main.gate_move=true;
	    break;
	  case 3:
	    main.gate_delete=true;
	    break;
	  case 4:
	    main.gate_config=true;
	    break;
	}
      }
    }
  }
  public void draw(Graphics g){
    Graphics2D g2=(Graphics2D)g;
    if (main.mode_select){
      for (int i=0;i<5;i++){
	if (i==selected){
	  g.setColor(selectedColor);
	}else{
	  g.setColor(otherColor);
	}
	g2.fill(choices[i]);
	if (i==selected){
	  g.setColor(selectedTextColor);
	}else{
	  g.setColor(otherTextColor);
	}
	g.setFont(font);
	g.drawString(strings[i],choices[i].x,choices[i].y+40);
      }
    }
  }
}
