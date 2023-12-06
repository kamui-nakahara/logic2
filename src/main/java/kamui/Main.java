package kamui;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Color;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import kamui.gate.*;
import kamui.object.*;
import kamui.system.Settings;
import kamui.file.*;
import kamui.mode.*;
import kamui.file.Convert;

public class Main implements KeyListener,MouseListener,MouseMotionListener{
  public Screen screen;
  public Menu menu;
  ModeSelect modeSelect;
  DrawLine drawLine;
  GateDelete gateDelete;
  GateMove gateMove;
  MakeBlock makeBlock;
  public ArrayList<Gate> con_blocks;
  public boolean gate_add=false;
  public GateCopy gateCopy;
  Save save;
  Open open;
  Load load;
  public HashMap<Integer,Boolean> keys=new HashMap<>();
  public int width=Settings.width;
  public int height=Settings.height;
  public ArrayList<Gate> blocks=new ArrayList<>();
  public Gate gate=new And2(-10,-10);
  public Point mousePoint=new Point(-10,-10);
  Rectangle menuButton=new Rectangle(10,10,40,40);
  public static boolean wire_flag=false;
  public static boolean gate_put=false;
  public static boolean gate_move=false;
  public static boolean gate_delete=false;
  public static boolean gate_config=false;
  public static boolean gate_select=false;
  public static boolean gate_copy=false;
  public static boolean mode_select=false;
  public static boolean gate_move2=false;
  public static boolean make_block=false;
  public static boolean debug=false;
  boolean reset=false;
  public boolean mousePressed=false;
  public boolean old_mousePressed=false;
  Color backgroundColor=new Color(200,200,200);
  Color lineColor=new Color(0,0,0,50);
  Color menuButtonColor1=new Color(100,100,100);
  Color menuButtonColor2=new Color(150,150,150);
  public static ArrayList<Gate> gates=new ArrayList<>();
  ArrayList<Point> collision_flags=new ArrayList<>();
  public static ArrayList<Line> lines=new ArrayList<>();
  public ArrayList<Gate> remove;
  public Main(Screen screen){
    this.screen=screen;
    this.menu=new Menu(this);
    this.modeSelect=new ModeSelect(this);
    this.drawLine=new DrawLine(this);
    this.gateDelete=new GateDelete(this);
    this.gateCopy=new GateCopy(this);
    this.gateMove=new GateMove(this);
    this.makeBlock=new MakeBlock(this);
    this.save=new Save(this);
    this.open=new Open(this);
    this.load=new Load(this);
  }
  public void setSize(int width,int height){
    screen.setPreferredSize(new Dimension(width,height));
  }
  public void putGate(Gate gate){
    gates.add(gate.copy());
    if (width-100<gate.x){
      width+=Settings.width/2;
    }
    if (height-100<gate.y){
      height+=Settings.height/2;
    }
    setSize(width,height);
  }
  public void collision(Gate gate){
    for (Gate g:gates){
      for (Point point1:g.getAbsInputs().keySet()){
	for (Point point2:gate.getAbsOutputs().keySet()){
	  if (point1.equals(point2)){
	    collision_flags.add(new Point(point1.x,point1.y));
	    break;
	  }
	}
      }
      for (Point point1:g.getAbsOutputs().keySet()){
	for (Point point2:gate.getAbsInputs().keySet()){
	  if (point1.equals(point2)){
	    collision_flags.add(new Point(point1.x,point1.y));
	    break;
	  }
	}
      }
    }
  }
  void update(){
    if (gate_add){
      for (Gate block:con_blocks){
	blocks.add(block.copy());
	Gate bl=block.copy();
	bl.setPoint(new Point(100,menu.last));
	menu.gates.add(bl);
	menu.last+=50;
      }
      gate_add=false;
    }
    collision_flags=new ArrayList<>();
    gate.setPoint(mousePoint);
    if (gate_put){
      if (mousePressed && !old_mousePressed && !menuButton.contains(mousePoint)){
	putGate(gate);
      }else{
	collision(gate);
      }
    }
    if (mousePressed && !(old_mousePressed || wire_flag || gate_put || gate_move || gate_delete || gate_config || gate_select || mode_select || gate_copy || gate_move2 || make_block)){
      modeSelect.open();
      mode_select=true;
    }
    if (menuButton.contains(mousePoint) && mousePressed && !old_mousePressed){
      gate_select=!gate_select;
      if (gate_select){
	wire_flag=false;
	gate_put=false;
	gate_move=false;
	gate_move2=false;
	gate_delete=false;
	gate_copy=false;
	gate_config=false;
	mode_select=false;
	make_block=false;
      }
    }
    remove=new ArrayList<>();
    menu.update();
    drawLine.update();
    gateDelete.update();
    gateCopy.update();
    gateMove.update();
    makeBlock.update();
    for (Gate gate:gates){
      if (gate_config){
	gate.update(this);
      }
      gate.setPoints_r();
    }
    modeSelect.update();
    gates.removeAll(remove);
    if (reset){
      for (Gate gate:gates){
	for (Point p:gate.inputs.keySet()){
	  gate.inputs.put(p,false);
	}
	for (Point p:gate.outputs.keySet()){
	  gate.outputs.put(p,false);
	}
      }
      for (Line l:lines){
	l.value=false;
      }
      reset=false;
    }
    old_mousePressed=mousePressed;
    calc();
  }
  void calc(){
    for (Gate g1:gates){
      HashMap<Point,Boolean> o1=g1.getAbsOutputs();
      for (Line l:lines){
	boolean coll=false;
	Point po=null;
	for (Point p:o1.keySet()){
	  if (l.getPoint1().equals(p)){
	    coll=true;
	    po=p;
	    l.begin=1;
	    break;
	  }
	  if (l.getPoint2().equals(p)){
	    coll=true;
	    po=p;
	    l.begin=2;
	    break;
	  }
	}
	if (coll){
	  l.value=g1.outputs.get(g1.points_r.get(po));
	}
      }
      for (Gate g2:gates){
	if (g1!=g2){
	  boolean coll=false;
	  HashMap<Point,Boolean> i2=g2.getAbsInputs();
	  Point po1=null;
	  Point po2=null;
	  for (Point p1:o1.keySet()){
	    for (Point p2:i2.keySet()){
	      if (p1.equals(p2)){
		coll=true;
		po1=p1;
		po2=p2;
		break;
	      }
	    }
	    if (coll){
	      break;
	    }
	  }
	  if (coll){
	    boolean a=g1.outputs.get(g1.points_r.get(po1));
	    g2.inputs.put(g2.points_r.get(po2),a);
	  }
	}
      }
    }
    for (Line l1:lines){
      for (Gate g:gates){
	HashMap<Point,Boolean> i=g.getAbsInputs();
	boolean coll=false;
	Point po=null;
	for (Point p:i.keySet()){
	  if (l1.begin==1){
	    if (l1.getPoint2().equals(p)){
	      coll=true;
	      po=p;
	      break;
	    }
	  }else if (l1.begin==2){
	    if (l1.getPoint1().equals(p)){
	      coll=true;
	      po=p;
	      break;
	    }
	  }
	}
	if (coll){
	  g.inputs.put(g.points_r.get(po),l1.value);
	}
      }
      for (Line l2:lines){
	if (l1!=l2){
	  if (l1.begin==1){
	    if (l1.getPoint2().equals(l2.getPoint1()) && l2.begin!=2){
	      l2.value=l1.value;
	      l2.begin=1;
	    }
	    if (l1.getPoint2().equals(l2.getPoint2()) && l2.begin!=1){
	      l2.value=l1.value;
	      l2.begin=2;
	    }
	  }else if (l1.begin==2){
	    if (l1.getPoint1().equals(l2.getPoint1()) && l2.begin!=2){
	      l2.value=l1.value;
	      l2.begin=1;
	    }
	    if (l1.getPoint1().equals(l2.getPoint2()) && l2.begin!=1){
	      l2.value=l1.value;
	      l2.begin=2;
	    }
	  }
	}
      }
    }
    for (Gate g:gates){
      g.calc();
    }
  }
  void draw(Graphics g){
    Graphics2D g2=(Graphics2D)g;
    background(g);
    drawLine.draw(g);
    for (Gate gate:gates){
      gate.draw(g,debug);
    }
    if (gate_put){
      gate.draw(g);
    }
    menu.draw(g);
    gateCopy.draw(g);
    gateDelete.draw(g);
    makeBlock.draw(g);
    g.setColor(Color.RED);
    for (Point point:collision_flags){
      g.fillOval(point.x-4,point.y-4,8,8);
    }
    if (menuButton.contains(mousePoint)){
      g.setColor(menuButtonColor1);
    }else{
      g.setColor(menuButtonColor2);
    }
    g2.fill(menuButton);
    g.setColor(Color.BLACK);
    g.fillRect(15,18,30,4);
    g.fillRect(15,28,30,4);
    g.fillRect(15,38,30,4);
    modeSelect.draw(g);
  }
  void background(Graphics g){
    Graphics2D g2=(Graphics2D)g;
    g.setColor(backgroundColor);
    g.fillRect(0,0,width,height);
    g.setColor(lineColor);
    for (int x=0;x<width;x+=10){
      g.drawLine(x,0,x,height);
    }
    for (int y=0;y<height;y+=10){
      g.drawLine(0,y,width,y);
    }
  }
  public void keyPressed(KeyEvent e){
    keys.put(e.getKeyCode(),true);
  }
  public void keyReleased(KeyEvent e){
    switch (e.getKeyCode()){
      case KeyEvent.VK_ESCAPE:
	wire_flag=false;
	gate_put=false;
	gate_move=false;
	gate_move2=false;
	gate_delete=false;
	gate_copy=false;
	gate_config=false;
	gate_select=false;
	mode_select=false;
	make_block=false;
	break;
      case KeyEvent.VK_UP:
	if (0<mousePoint.y-10){
	  mousePoint.y-=10;
	}
	break;
      case KeyEvent.VK_DOWN:
	if (mousePoint.y+10<height){
	  mousePoint.y+=10;
	}
	break;
      case KeyEvent.VK_LEFT:
	if (0<mousePoint.x-10){
	  mousePoint.x-=10;
	}
	break;
      case KeyEvent.VK_RIGHT:
	if (mousePoint.x+10<width){
	  mousePoint.x+=10;
	}
	break;
      case KeyEvent.VK_W:
	wire_flag=true;
	gate_put=false;
	gate_move=false;
	gate_move2=false;
	gate_delete=false;
	gate_copy=false;
	gate_config=false;
	mode_select=false;
	make_block=false;
	break;
      case KeyEvent.VK_A:
	wire_flag=false;
	gate_put=true;
	gate_move=false;
	gate_delete=false;
	gate_config=false;
	gate_move2=false;
	mode_select=false;
	gate_copy=false;
	make_block=false;
	break;
      case KeyEvent.VK_M:
	wire_flag=false;
	gate_put=false;
	gate_move2=true;
	gate_delete=false;
	gate_copy=false;
	gate_move=false;
	gate_config=false;
	mode_select=false;
	make_block=false;
	break;
      case KeyEvent.VK_D:
	wire_flag=false;
	gate_put=false;
	gate_move=false;
	gate_delete=true;
	gate_config=false;
	gate_move2=false;
	gate_copy=false;
	mode_select=false;
	make_block=false;
	break;
      case KeyEvent.VK_C:
	wire_flag=false;
	gate_put=false;
	gate_move=false;
	gate_move2=false;
	gate_delete=false;
	gate_config=true;
	mode_select=false;
	gate_copy=false;
	make_block=false;
	break;
      case KeyEvent.VK_Y:
	wire_flag=false;
	gate_put=false;
	gate_move=false;
	gate_move2=false;
	gate_delete=false;
	gate_config=false;
	gate_copy=true;
	mode_select=false;
	make_block=false;
	gateCopy.init();
	break;
      case KeyEvent.VK_P:
	if (gate_move || gate_move2){
	  wire_flag=false;
	  gate_put=false;
	  gate_move=!gate_move;
	  gate_move2=!gate_move2;
	  gate_delete=false;
	  gate_config=false;
	  gate_copy=false;
	  mode_select=false;
	  make_block=false;
	}
	break;
      case KeyEvent.VK_B:
	wire_flag=false;
	gate_put=false;
	gate_move=false;
	gate_move2=false;
	gate_delete=false;
	gate_config=false;
	gate_copy=false;
	mode_select=false;
	make_block=true;
	break;
      case KeyEvent.VK_SPACE:
	debug=!debug;
	break;
      case KeyEvent.VK_R:
	reset=true;
	break;
    }
    keys.put(e.getKeyCode(),false);
  }
  public void keyTyped(KeyEvent e){}
  public void mousePressed(MouseEvent e){
    mousePressed=true;
  }
  public void mouseReleased(MouseEvent e){
    mousePressed=false;
  }
  public void mouseClicked(MouseEvent e){}
  public void mouseMoved(MouseEvent e){
    mousePoint=new Point((int)(e.getPoint().x/10)*10,(int)(e.getPoint().y/10)*10);
  }
  public void mouseDragged(MouseEvent e){
    mousePoint=new Point((int)(e.getPoint().x/10)*10,(int)(e.getPoint().y/10)*10);
  }
  public void mouseEntered(MouseEvent e){}
  public void mouseExited(MouseEvent e){}
}
