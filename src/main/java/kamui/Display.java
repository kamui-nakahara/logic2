package kamui;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import kamui.system.Settings;

public class Display extends JFrame{
  public Screen screen;
  int width;
  int height;
  public Display(String title){
    super(title);
    pack();
    Insets insets=getInsets();
    width=Settings.width;
    height=Settings.height+insets.top;
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setSize(Settings.width,Settings.height+insets.top);
    setResizable(false);

    screen=new Screen(this);

    JMenuBar menubar=new JMenuBar();
    JMenu menu1=new JMenu("ファイル");
    JMenuItem item1_1=new JMenuItem("新規");
    item1_1.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent e){
	screen.main.gates=new ArrayList<>();
	screen.main.lines=new ArrayList<>();
      }
    });
    menu1.add(item1_1);
    JMenuItem item1_2=new JMenuItem("開く");
    item1_2.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent e){
	screen.main.open.open();
      }
    });
    menu1.add(item1_2);
    JMenuItem item1_5=new JMenuItem("読み込み");
    item1_5.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent e){
	screen.main.load.load();
      }
    });
    menu1.add(item1_5);
    JMenuItem item1_3=new JMenuItem("保存");
    item1_3.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent e){
	screen.main.save.save();
      }
    });
    menu1.add(item1_3);
    JMenuItem item1_4=new JMenuItem("終了");
    item1_4.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent e){
	System.exit(0);
      }
    });
    menu1.add(item1_4);
    menubar.add(menu1);
    setJMenuBar(menubar);

    screen.setPreferredSize(new Dimension(Settings.width,Settings.height));
    add(screen);

    JScrollPane scrollpane=new JScrollPane(screen);
    scrollpane.setPreferredSize(new Dimension(width,height+insets.top));
    scrollpane.getVerticalScrollBar().setUnitIncrement(25);

    add(scrollpane);
    
    addKeyListener(screen.main);
  }
}
