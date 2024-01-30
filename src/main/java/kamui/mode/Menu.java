package kamui.mode;

import java.awt.Graphics;
import java.awt.Color;
import java.util.ArrayList;
import kamui.gate.*;
import kamui.object.*;
import kamui.system.Settings;
import kamui.Main;

public class Menu{
  Main main;
  Color menuColor=new Color(128,128,128,200);
  public ArrayList<Gate> gates=new ArrayList<>();
  public int last=850;
  Gate selectedGate;
  boolean selected=false;
  public Menu(Main main){
    this.main=main;
    gates.add(new And2(150,50));
    gates.add(new And3(250,50));
    gates.add(new Or2(350,50));
    gates.add(new Or3(450,50));
    gates.add(new Not(550,50));
    gates.add(new Nand2(650,50));
    gates.add(new Nand3(750,50));
    gates.add(new Nor2(850,50));
    gates.add(new Nor3(950,50));
    gates.add(new Xor2(1050,50));
    gates.add(new Xor3(1150,50));
    gates.add(new Xnor2(1250,50));
    gates.add(new Xnor3(50,120));
    gates.add(new Input(150,120));
    gates.add(new Output(250,120));
    gates.add(new SevenSegment(350,120));
    gates.add(new KeyBoard(450,120));
    gates.add(new LED(550,120));
    gates.add(new Terminal1(650,120));
    gates.add(new Terminal2(750,120));
  }
  public void update(){
    if (main.gate_select){
      if (main.width-100<gates.get(gates.size()-1).x){
	main.width+=Settings.width/2;
      }
      if (main.height-100<gates.get(gates.size()-1).y){
	main.height+=Settings.height/2;
      }
      main.setSize(main.width,main.height);
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
      g.fillRect(0,0,Settings.width,250);
      for (Gate gate:gates){
	gate.draw(g);
      }
      if (selected){
	selectedGate.draw(g);
      }
    }
  }
}
