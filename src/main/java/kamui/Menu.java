package kamui;

import java.awt.Graphics;
import java.awt.Color;
import java.util.ArrayList;
import kamui.gate.*;
import kamui.object.*;
import kamui.system.Settings;

public class Menu{
  Main main;
  Color menuColor=new Color(0,0,0,200);
  ArrayList<Gate> gates=new ArrayList<>();
  Gate selectedGate;
  boolean selected=false;
  public Menu(Main main){
    this.main=main;
    gates.add(new And2(100,50));
    gates.add(new And3(100,100));
    gates.add(new Or2(100,150));
    gates.add(new Or3(100,200));
    gates.add(new Not(100,250));
    gates.add(new Nand2(100,300));
    gates.add(new Nand3(100,350));
    gates.add(new Nor2(100,400));
    gates.add(new Nor3(100,450));
    gates.add(new Xor2(100,500));
    gates.add(new Xor3(100,550));
    gates.add(new Xnor2(100,600));
    gates.add(new Xnor3(100,650));
    gates.add(new Input(100,700));
    gates.add(new Output(100,750));
  }
  public void update(){
    if (main.gate_select){
      if (main.mousePressed && !main.old_mousePressed){
	for (Gate gate:gates){
	  if (gate.getRect().contains(main.mousePoint)){
	    selectedGate=gate.copy();
	    main.gate=selectedGate;
	    selected=true;
	    break;
	  }
	}
      }
      if (main.mousePressed && main.old_mousePressed && selected){
	selectedGate.setPoint(main.mousePoint);
	main.collision(selectedGate);
      }
      if (!main.mousePressed && main.old_mousePressed && selected){
	main.putGate(selectedGate);
	selected=false;
      }
    }
  }
  public void draw(Graphics g){
    if (main.gate_select){
      g.setColor(menuColor);
      g.fillRect(0,0,200,main.height);
      for (Gate gate:gates){
	gate.draw(g);
      }
      if (selected){
	selectedGate.draw(g);
      }
    }
  }
}
