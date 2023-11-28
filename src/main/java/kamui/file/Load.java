package kamui.file;

import javax.swing.JFileChooser;
import java.io.File;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.awt.Point;
import java.util.HashMap;
import java.util.ArrayList;
import kamui.gate.*;
import kamui.object.*;
import kamui.Main;

public class Load{
  Main main;
  HashMap<String,Gate> conv=new HashMap<>();
  public Load(Main main){
    this.main=main;
    conv.put("and2",new And2(-10,-10));
    conv.put("and3",new And3(-10,-10));
    conv.put("or2",new Or2(-10,-10));
    conv.put("or3",new Or3(-10,-10));
    conv.put("nand2",new Nand2(-10,-10));
    conv.put("nand3",new Nand3(-10,-10));
    conv.put("nor2",new Nor2(-10,-10));
    conv.put("nor3",new Nor3(-10,-10));
    conv.put("xor2",new Xor2(-10,-10));
    conv.put("xor3",new Xor3(-10,-10));
    conv.put("xnor2",new Xnor2(-10,-10));
    conv.put("xnor3",new Xnor3(-10,-10));
    conv.put("not",new Not(-10,-10));
    conv.put("input",new Input(-10,-10));
    conv.put("output",new Output(-10,-10));
    conv.put("sevensegment",new SevenSegment(-10,-10));
  }
  public void load(){
    JFileChooser filechooser=new JFileChooser();
    int select=filechooser.showOpenDialog(main.screen.display);
    if (select==JFileChooser.APPROVE_OPTION){
      File file=filechooser.getSelectedFile();
      open(file.getPath());
    }
  }
  void open(String filename){
    try(FileInputStream f=new FileInputStream(filename);
	BufferedInputStream b=new BufferedInputStream(f);
	ObjectInputStream in=new ObjectInputStream(b)){

      Convert con=(Convert)in.readObject();
      main.blocks.addAll(con.blocks);
      for (Gate block:con.blocks){
	main.blocks.add(block.copy());
	Gate bl=block.copy();
	bl.setPoint(new Point(100,main.menu.last));
	main.menu.gates.add(bl);
	main.menu.last+=50;
      }
    }catch(IOException e){
      e.printStackTrace();
    }catch(ClassNotFoundException e){
      e.printStackTrace();
    }
  }
}
