package kamui.dialog.keyset;

import javax.swing.JDialog;
import javax.swing.JButton;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import kamui.gate.Gate;
import kamui.Display;

public class SetKey extends JDialog implements KeyListener{
  JButton button;
  Gate gate;
  int keycode;
  String keychar;
  public SetKey(Display display,Gate gate){
    super(display,"反応するキーの設定");
    this.gate=gate;
    setSize(100,100);
    button=new JButton(gate.title);
    keychar=gate.title;
    keycode=gate.data.get(0);
    add(button);
    button.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent e){
	gate.data.set(0,keycode);
	gate.title=keychar;
	dispose();
      }
    });
    button.addKeyListener(this);
    setVisible(true);
  }
  public void keyReleased(KeyEvent e){
    if (e.getKeyCode()==KeyEvent.VK_ESCAPE){
      gate.data.set(0,keycode);
      gate.title=keychar;
      dispose();
    }
    keycode=e.getKeyCode();
    keychar=(""+e.getKeyChar()).toUpperCase();
    switch(keycode){
      case KeyEvent.VK_UP:
	keychar="UP";
	break;
      case KeyEvent.VK_DOWN:
	keychar="DONW";
	break;
      case KeyEvent.VK_LEFT:
	keychar="LEFT";
	break;
      case KeyEvent.VK_RIGHT:
	keychar="RIGHT";
	break;
    }
    button.setText(keychar);
  }
  public void keyPressed(KeyEvent e){}
  public void keyTyped(KeyEvent e){}
}
