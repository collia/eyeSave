package windows;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.Arrays;

import timer.Timer;
import main.Properties;
import main.Statistic;
import main.PropFile;


import javax.swing.*;

public class MainFrame extends JFrame {
	private static final long serialVersionUID = 2440851975081118090L;
	
	JButton button;
	final FrameDragger fd;
	final static Color bCol = new Color(00,100,00);//new Color(200,200,200);//Color.getHSBColor(0, 10, 78);
	final static Color textCol = new Color(255,255,255);
	
	JLabel textTime;
	JButton butMenu;
	JButton butStartStop;
	JButton butClose;
	JButton butMin;
	
	JButton butHelp;
	
	MainFrame parent;
	
	
	Timer timer;
	Properties prop;
	Statistic statistic;
		
	Menu menu = null;
	
	boolean showingSplashScreen = false;
	
	public MainFrame(final Timer timer, final Properties prop) throws HeadlessException {
		
		this.timer = timer;
		this.prop = prop;
		
		this.setUndecorated(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setLocation(prop.getPosX(), prop.getPosY());
		
		//this.setSize(350, 40);
		switch(prop.getSize()){
			case(Properties.BIG_SIZE): this.setSize(340, 32); break;
			case(Properties.LARGE_SIZE): this.setSize(275, 23); break;
			case(Properties.SMALL_SIZE): this.setSize(220, 20); break;
		}
		this.setTitle("Eye saver");
		this.setAlwaysOnTop(true);
		
		
		
		Shape shape = new RoundRectangle2D.Float(0, 0, this.getWidth(), this.getHeight(), 30, 30);
		switch(prop.getSize()){
		case(Properties.BIG_SIZE): 
			shape = new RoundRectangle2D.Float(0, 0, this.getWidth(), this.getHeight(), 30, 30); 
			break;
		case(Properties.LARGE_SIZE):
			shape = new RoundRectangle2D.Float(0, 0, this.getWidth(), this.getHeight(), 25, 25);
			break;
		case(Properties.SMALL_SIZE): 
			shape = new RoundRectangle2D.Float(0, 0, this.getWidth(), this.getHeight(), 20, 20); 
			break;
		}
		
		
		com.sun.awt.AWTUtilities.setWindowShape(this, shape);
				
				
		parent = this;
		

		fd = new FrameDragger(this);
	
		UIManager.put("Panel.background", bCol);
		butClose = createButton("1","Close", bCol);
		butMin = createButton("2","Minimize", bCol);
		butHelp = createButton("3","Help", bCol);
		

		UIManager.put("Label.foreground", textCol);
		
		textTime = new JLabel("");
		setTime(0,0,0);
		textTime.setHorizontalAlignment(JLabel.CENTER);
		GraphicsEnvironment ge =
			GraphicsEnvironment.getLocalGraphicsEnvironment();

		java.util.List fonts = Arrays.asList(ge.getAvailableFontFamilyNames());
		if(fonts.contains("Courier New"))
			textTime.setFont(new java.awt.Font("Courier New", Font.BOLD, 16));
		else
			if(fonts.contains("Arial"))
				textTime.setFont(new java.awt.Font("Arial", Font.BOLD, 16));
			else
				textTime.setFont(new java.awt.Font((String)fonts.get(1), Font.BOLD, 16));
		
		butMenu = createButton("4","Begin Work", bCol);
		butStartStop = createButton("6","Start", bCol);
		
		butMenu.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
			if(menu == null)
					menu = new Menu(timer, bCol,prop,parent, statistic);
				else{
					//if(!menu.isVisible()) menu = null;
					menu.setVisible(false);
					menu = null;
				}
			}
		});		
		butStartStop.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				if(!timer.isGo()){
					timer.startTimer();
					butStartStop = updateButton(butStartStop,"5","Stop Work", bCol);
				}else{
					timer.pauseTimer();
					butStartStop = updateButton(butStartStop,"6","Begin Work", bCol);
				}
			}
		});		
		
		butHelp.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				if(splashScreen == null || !splashScreen.isVisible())
					showingSplashScreen = false;
				if(!showingSplashScreen){
					createSplashScreen(); 
					showSplashScreen();
					showingSplashScreen = true;
				}else{
					hideSplash();
					showingSplashScreen = false;
					
				}
				
			}
		});		
		butClose.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				prop.setPosX(parent.getX());
				prop.setPosY(parent.getY());
				try {
					if(!PropFile.writeParemeters(prop)) System.out.println("ERROR");
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				System.exit(0);
			}
		});		
		
		butMin.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				parent.setState(Frame.ICONIFIED);
			}
		});		
		
		GridLayout gl1 = new GridLayout(1,3);
		gl1.setHgap(0);
		gl1.setVgap(0);
		JPanel pan1 = new JPanel(gl1);
		pan1.addMouseListener(fd);
		pan1.addMouseMotionListener(fd);
		
		FlowLayout fl2 = new FlowLayout();
		fl2.setHgap(0);
		fl2.setVgap(0);
		
		JPanel pan2 = new JPanel(fl2);
		pan2.addMouseListener(fd);
		pan2.addMouseMotionListener(fd);
		
		GridLayout gl2 = new GridLayout(1,2);
		gl2.setHgap(0);
		gl2.setVgap(0);
		
		JPanel pan3 = new JPanel(gl2);
		pan3.addMouseListener(fd);
		pan3.addMouseMotionListener(fd);
		
	
		
		pan1.add(butHelp);
		pan1.add(butMenu);
		pan1.add(butStartStop);
		pan2.add(textTime);
		pan3.add(butMin);
		pan3.add(butClose);
		
		FlowLayout fl = new FlowLayout();
		fl.setHgap(5);
		fl.setVgap(1);
		JPanel all = new JPanel(fl);
		all.add(pan1);
		all.add(pan2);
		all.add(pan3);

		getContentPane().add(all);
	}
	
	
	public void setTime(int hour, int min, int sec){
		Object[] testArgs = {new Integer(hour),new Integer(min), new Integer(sec)}; 
		MessageFormat mf = new MessageFormat("{0,number,#0}:{1,number,00}:{2,number,00}");
		final String res = mf.format(testArgs);
		if(textTime != null)
			//textTime.setText(res);//""+hour+":"+min+":"+sec);
			EventQueue.invokeLater(new Runnable(){
				public void run() {
					textTime.setText(res);
				}
				
			});
	}
	
	private JButton createButton(String text, String descriptions, Color color){
		UIManager.put("Button.background", color);
	//	UIManager.put("Button.select", color);
		UIManager.put("Button.select", UIManager.get("Button.background"));
		JButton locBut = null;
		
		String size = "";
		switch(prop.getSize()){
			case(Properties.BIG_SIZE): size =""; break;
			case(Properties.LARGE_SIZE): size ="s1"; break;
			case(Properties.SMALL_SIZE): size ="s2"; break;
		}
		
		locBut = new JButton(createImageIcon("but"+text+size+"/but_1.png", descriptions));
		locBut.setIcon(createImageIcon("but"+text+size+"/but_1.png", descriptions));
		locBut.setPressedIcon(createImageIcon("but"+text+size+"/but_2.png", descriptions));
		locBut.setRolloverIcon(createImageIcon("but"+text+size+"/but_1.png", descriptions));
		locBut.setDisabledIcon(createImageIcon("but"+text+size+"/but_3.png", descriptions));
		locBut.setMargin(new Insets(0,0,0,0));
		
		locBut.addMouseListener(fd);
		locBut.addMouseMotionListener(fd);
		
		locBut.setBorder(null);
		
		
		return locBut;
		
	}

	private JButton updateButton(JButton but, String text, String descriptions, Color color){
		UIManager.put("Button.background", color);
		UIManager.put("Button.select", color);
		
		String size = "";
		switch(prop.getSize()){
			case(Properties.BIG_SIZE): size =""; break;
			case(Properties.LARGE_SIZE): size ="s1"; break;
			case(Properties.SMALL_SIZE): size ="s2"; break;
		}
		
		but.setIcon(createImageIcon("but"+text+size+"/but_1.png", descriptions));
		but.setPressedIcon(createImageIcon("but"+text+size+"/but_2.png", descriptions));
		but.setRolloverIcon(createImageIcon("but"+text+size+"/but_1.png", descriptions));
		but.setDisabledIcon(createImageIcon("but"+text+size+"/but_3.png", descriptions));
		but.setMargin(new Insets(0,0,0,0));
		
				
		but.setBorder(null);
		
		
		return but;
		
	}

	
	public ImageIcon createImageIcon(String filename, String description) {
		
	    String path = "/resources/images/" + filename;
	    return new ImageIcon(getClass().getResource(path), description); 
	
    }
	
	  /**
     * Show the spash screen while the rest of the demo loads
     */
	JLabel splashLabel;
	JWindow splashScreen;
	
    private void createSplashScreen() {

	splashLabel = new JLabel(createImageIcon("Intro1.png", "about"));
	
	splashLabel.addMouseListener(new MouseListener(){

		public void mouseClicked(MouseEvent arg0) {
			hideSplash();
		}

		public void mouseEntered(MouseEvent arg0) {}

		public void mouseExited(MouseEvent arg0) {}

		public void mousePressed(MouseEvent arg0) {}

		public void mouseReleased(MouseEvent arg0) {}
		
	});
	
	    splashScreen = new JWindow(parent);
	   
	    splashScreen.getContentPane().add(splashLabel);
	    splashScreen.pack();
	    Rectangle screenRect = parent.getGraphicsConfiguration().getBounds();
	    splashScreen.setLocation(
         screenRect.x + screenRect.width/2 - splashScreen.getSize().width/2,
		 screenRect.y + screenRect.height/2 - splashScreen.getSize().height/2);
	    
	    if(prop.isNewJVM()){
	    	Shape shape = new RoundRectangle2D.Float(0, 0, splashScreen.getWidth(), splashScreen.getHeight(), 50, 50);
	    	com.sun.awt.AWTUtilities.setWindowShape(splashScreen, shape);
	    	//AWTUtilities.setWindowOpacity(splashScreen, 0.5f);
	    }
	} 
    

    private void showSplashScreen() {

	    splashScreen.setVisible(true);
    }

    /**
     * pop down the spash screen
     */
    private void hideSplash() {

	    splashScreen.setVisible(false);
	    splashScreen = null;
	    splashLabel = null;

    }
    
    public void setButStart(){
    	butStartStop = updateButton(butStartStop,"5","Stop Work", bCol);
    }
    public void setButStop(){
    	butStartStop = updateButton(butStartStop,"6","Begin Work", bCol);
    }
	public Menu getMenu() {
		return menu;
	}

	public void setMenu(Menu menu) {
		this.menu = menu;
	}

	public void updateWind(){
		switch(prop.getSize()){
			case(Properties.BIG_SIZE): this.setSize(340, 32); break;
			case(Properties.LARGE_SIZE): this.setSize(275, 23); break;
			case(Properties.SMALL_SIZE): this.setSize(220, 20); break;
		}
		Shape shape = new RoundRectangle2D.Float(0, 0, this.getWidth(), this.getHeight(), 30, 30);
		switch(prop.getSize()){
		case(Properties.BIG_SIZE): 
			shape = new RoundRectangle2D.Float(0, 0, this.getWidth(), this.getHeight(), 30, 30); 
			break;
		case(Properties.LARGE_SIZE):
			shape = new RoundRectangle2D.Float(0, 0, this.getWidth(), this.getHeight(), 25, 25);
			break;
		case(Properties.SMALL_SIZE): 
			shape = new RoundRectangle2D.Float(0, 0, this.getWidth(), this.getHeight(), 20, 20); 
			break;
		}
		
		
		com.sun.awt.AWTUtilities.setWindowShape(this, shape);
		
		butClose = updateButton(butClose,"1","Close", bCol);
		butMin = updateButton(butMin,"2","Minimize", bCol);
		butHelp = updateButton(butHelp,"3","Help", bCol);
		butMenu = updateButton(butMenu,"4","Begin Work", bCol);
		if(timer.isGo())
			butStartStop = updateButton(butStartStop,"5","Start", bCol);
		else
			butStartStop = updateButton(butStartStop,"6","Start", bCol);
		
		
	}


	public void setStatistic(Statistic statistic) {
		this.statistic = statistic;
	}
	
}
