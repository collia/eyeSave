package windows;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.RoundRectangle2D;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;

import timer.Timer;

import main.Properties;
import main.Statistic;


public class Menu extends JDialog {
	
	private static final long serialVersionUID = 6230210522884535347L;
	
	JButton buttonWork;
	JButton buttonStat;
	JButton buttonParam;
	static JDialog wind;
	MainFrame parent;
	
	Color col = Color.BLUE;
	private boolean useColor = false;
	
	private Properties prop;
	
	Timer timer;
	
	public Menu(Properties prop, MainFrame par, Timer timer, Statistic stat){
		this(timer, Color.BLUE, prop, par, stat);
		useColor = false;
	}
	
	public Menu(final Timer timer, Color c, Properties proper, final MainFrame par, final Statistic stat){
		
		col = c;
		this.prop = proper;
		
		parent =par;
		
		wind = this;
		this.setUndecorated(true);
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.setLocationRelativeTo(parent);
		this.setAlwaysOnTop(true);
	
		
		this.setLocation(parent.getLocation().x + parent.getHeight(), 
						 parent.getLocation().y + parent.getHeight());
		JPanel pan1 = new JPanel();
		
		if(!timer.isWork()){
			buttonWork = createButton("8","working..",col);
			}
		else {
			buttonWork = createButton("9","working..",col);
		}
		
		buttonWork.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
					wind.setVisible(false);
					parent.setMenu(null);
					boolean b = timer.isPaused();
					if(!timer.isWork()){
						timer.stopTimer();
						timer.startWork();
						timer.startTimer();
						parent.setButStart();
					}else{
						timer.stopTimer();
						timer.stopWork();
						timer.startTimer();
						parent.setButStart();
					}
					timer.setPaused(b);
	
			}
		});
		
		buttonStat = createButton("10","working..",col);
		
		buttonStat.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {

					wind.setVisible(false);
					parent.setMenu(null);
					new StatisticFrame(col,prop, stat);
			}
		});
		
		buttonParam = createButton("11","working..",col);
		
		buttonParam.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
					wind.setVisible(false);
					parent.setMenu(null);
					new ParamFrame(col,prop, par);
			}
		});
		
		pan1.setLayout(new GridLayout(3,1));
		pan1.add(buttonWork);
		pan1.add(buttonStat);
		pan1.add(buttonParam);
		this.getContentPane().add(pan1);
		this.pack();
		
		   if(prop.isNewJVM()){
			   Shape shape = new RoundRectangle2D.Float(0, 0, this.getWidth(), this.getHeight(), 20, 20);
			   com.sun.awt.AWTUtilities.setWindowShape(this, shape);
		   }
		
		
		this.setVisible(true);
	}
	
	public ImageIcon createImageIcon(String filename, String description) {
		
		    String path = "/resources/images/" + filename;
		    return new ImageIcon(getClass().getResource(path), description); 
	 }
	
	private JButton createButton(String text, String descriptions, Color color){
		if(useColor)
			UIManager.put("Button.background", color);
		UIManager.put("Button.select", UIManager.get("Button.background"));
		JButton locBut = null;
		
		String size = "";
		switch(prop.getSize()){
			case(Properties.BIG_SIZE): size =""; break;
			case(Properties.LARGE_SIZE): size ="s1"; break;
			case(Properties.SMALL_SIZE): size ="s2"; break;
		}
		
		locBut = new JButton(  createImageIcon("but"+text+size+"/but_1.png", descriptions));
		locBut.setIcon(		   createImageIcon("but"+text+size+"/but_1.png", descriptions));
		locBut.setPressedIcon( createImageIcon("but"+text+size+"/but_2.png", descriptions));
		locBut.setRolloverIcon(createImageIcon("but"+text+size+"/but_1.png", descriptions));
		locBut.setDisabledIcon(createImageIcon("but"+text+size+"/but_3.png", descriptions));
		locBut.setMargin(new Insets(0,0,0,0));
		
		locBut.setBorder(null);
		
		
		return locBut;
		
	}
}
