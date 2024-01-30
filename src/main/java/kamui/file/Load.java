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
  public Load(Main main){
    this.main=main;
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
      main.gate_add=true;
      main.con_blocks=con.blocks;
    }catch(IOException e){
      e.printStackTrace();
    }catch(ClassNotFoundException e){
      e.printStackTrace();
    }
  }
}
