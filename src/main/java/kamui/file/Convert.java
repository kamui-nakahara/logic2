package kamui.file;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.awt.Point;
import kamui.gate.Gate;
import kamui.object.Line;

public class Convert implements Serializable{
  ArrayList<Gate> gates;
  ArrayList<Line> lines;
  ArrayList<Gate> blocks;
  int width;
  int height;
  public Convert(ArrayList<Gate> gates,ArrayList<Line> lines,ArrayList<Gate> blocks,int width,int height){
    this.gates=gates;
    this.lines=lines;
    this.blocks=blocks;
    this.width=width;
    this.height=height;
  }
}
