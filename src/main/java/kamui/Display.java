package kamui;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JCheckBoxMenuItem;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentListener;
import java.awt.event.ComponentEvent;
import java.awt.Dimension;
import java.util.ArrayList;
import kamui.system.Settings;

public class Display extends JFrame implements ComponentListener{
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
    setResizable(true);

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
    JMenuItem item1_4=new JMenuItem("画像に保存");
    item1_4.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent e){
	screen.main.screenshot();
      }
    });
    menu1.add(item1_4);
    JMenuItem item1_6=new JMenuItem("終了");
    item1_6.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent e){
	System.exit(0);
      }
    });
    menu1.add(item1_6);
    menubar.add(menu1);
    JMenu menu2=new JMenu("表示");
    JCheckBoxMenuItem item2_1=new JCheckBoxMenuItem("グリッドの表示"){
      {
	setSelected(true);
      }
    };
    item2_1.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent e){
	screen.main.show_grid=item2_1.isSelected();
      }
    });
    menu2.add(item2_1);
    menubar.add(menu2);
    setJMenuBar(menubar);

    screen.setPreferredSize(new Dimension(Settings.width,Settings.height));
    add(screen);

    JScrollPane scrollpane=new JScrollPane(screen);
    scrollpane.setPreferredSize(new Dimension(width,height+insets.top));
    scrollpane.getVerticalScrollBar().setUnitIncrement(25);

    add(scrollpane);
    
    addKeyListener(screen.main);
    addComponentListener(this);
  }
  public void componentHidden(ComponentEvent e) {
  }
  public void componentMoved(ComponentEvent e) {
  }
  public void componentResized(ComponentEvent e) {
    Dimension d=getSize();
    Settings.width=d.width;
    Settings.height=d.height;
    if (Settings.width>screen.main.width){
      screen.main.width=d.width;
    }
    if (Settings.height>screen.main.height){
      screen.main.height=d.height;
    }
  }
  public void componentShown(ComponentEvent e) {
  }
}
