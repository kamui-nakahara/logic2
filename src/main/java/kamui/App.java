package kamui;

public class App{
  public static void main(String[] args){
    Display display=new Display("論理回路");
    display.setVisible(true);
    while (true){
      try{
	Thread.sleep(25);
	display.repaint();
      }catch(InterruptedException e){
      }
    }
  }
}
