package kamui.file;

import javax.swing.JFileChooser;
import java.io.File;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import kamui.gate.*;
import kamui.object.Line;
import kamui.Main;

public class Save{
  Main main;
  public Save(Main main){
    this.main=main;
  }
  public void save(){
    JFileChooser filechooser=new JFileChooser();
    int select=filechooser.showSaveDialog(main.screen.display);
    if (select==JFileChooser.APPROVE_OPTION){
      File file=filechooser.getSelectedFile();
      save(file.getPath());
    }
  }
  void save(String filename){
    try(FileOutputStream f=new FileOutputStream(filename+".dat");
	BufferedOutputStream b=new BufferedOutputStream(f);
	ObjectOutputStream out=new ObjectOutputStream(b)){

      Convert con;
      con=new Convert(main.gates,main.lines,main.blocks,main.width,main.height);

      out.writeObject(con);
    }catch(IOException e){
      e.printStackTrace();
    }
  }
}
