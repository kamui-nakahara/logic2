package kamui.dialog.title;

import javax.swing.JDialog;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Rectangle;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.BorderLayout;
import java.awt.Insets;
import java.awt.Point;
import kamui.Main;
import kamui.Display;
import kamui.gate.Block;
import kamui.gate.Gate;

public class Title extends JDialog{
  Main main;
  JTextField text;
  JButton button;
  public Title(Display display,Rectangle rect,Block block){
    super(display,"ブロックの設定");
    main=display.screen.main;
    setLayout(new BorderLayout());
    setLocationRelativeTo(null);
    text=new JTextField(10);
    button=new JButton("決定");
    button.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent e){
	block.title=text.getText();
	main.wire_flag=false;
	main.gate_put=true;
	main.gate_move=false;
	main.gate_delete=false;
	main.gate_config=false;
	main.gate_select=false;
	main.gate_copy=false;
	main.mode_select=false;
	main.gate_move2=false;
	main.make_block=false;
	main.gate=block;
	Gate b=block.copy();
	b.setPoint(new Point(main.menu.last,120));
	main.menu.gates.add(b);
	main.menu.last+=100;
	main.blocks.add(block.copy());
	dispose();
      }
    });
    add(text,BorderLayout.NORTH);
    add(button,BorderLayout.SOUTH);
    pack();
    setVisible(true);
  }
}
