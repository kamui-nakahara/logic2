package kamui.dialog.title;

import javax.swing.JDialog;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JSpinner;
import javax.swing.JColorChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.BorderLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Dimension;
import kamui.Main;
import kamui.gate.Gate;
import kamui.Display;

public class Title2 extends JDialog{
  Main main;
  JTextField text;
  JButton button;
  JSpinner spinner;
  JColorChooser chooser;
  JPanel panel1;
  JPanel panel2;
  public Title2(Display display,Gate gate){
    super(display,"ブロックの設定");
    main=display.screen.main;
    setLayout(new BorderLayout());
    setLocationRelativeTo(null);
    text=new JTextField(10);
    spinner=new JSpinner();
    chooser=new JColorChooser();
    spinner.setPreferredSize(new Dimension(50,20));
    button=new JButton("決定");
    panel1=new JPanel();
    panel1.setLayout(new BorderLayout());
    panel2=new JPanel();
    panel2.setLayout(new BorderLayout());
    button.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent e){
	gate.color=chooser.getColor();
	gate.originalcolor=gate.color;
	gate.title=text.getText();
	gate.data.set(0,(int)spinner.getValue());
	dispose();
      }
    });
    add(chooser,BorderLayout.NORTH);
    panel1.add(text,BorderLayout.EAST);
    panel1.add(new JLabel("ラベルの文字:"),BorderLayout.WEST);
    panel2.add(new JLabel("文字サイズ:"),BorderLayout.WEST);
    panel2.add(spinner,BorderLayout.EAST);
    add(panel1,BorderLayout.WEST);
    add(panel2,BorderLayout.EAST);
    add(button,BorderLayout.SOUTH);
    pack();
    setVisible(true);
  }
}
