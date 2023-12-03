package kamui.file;

import javax.swing.JFileChooser;
import java.io.File;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import kamui.gate.*;
import kamui.object.*;
import kamui.Main;

public class Open{
  Main main;
  public Open(Main main){
    this.main=main;
  }
  public void open(){
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
      main.gates=con.gates;
      main.lines=con.lines;
      main.width=con.width;
      main.height=con.height;
      main.setSize(main.width,main.height);
      main.gate_add=true;
      main.con_blocks=con.blocks;
    }catch(IOException e){
      e.printStackTrace();
    }catch(ClassNotFoundException e){
      e.printStackTrace();
    }
  }
}
