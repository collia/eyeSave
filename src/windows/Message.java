package windows;

import java.awt.Color;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.RoundRectangle2D;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.UIManager;

import main.Properties;

public class Message extends JDialog {
	
	private static final long serialVersionUID = 6230210522884535347L;
	
	JButton button;
	static JDialog wind;
	FrameDragger fd;
	
	Color col = Color.BLUE;
	private boolean useColor = false;
	
	public Message(String title, String img, String descr,Properties prop){
		this(title, img, descr, Color.BLUE, prop);
		useColor = false;
	}
	
	public Message(String title, String img, String descr, Color c, Properties prop){
		
		col = c;
		wind = this;
		this.setUndecorated(true);
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setAlwaysOnTop(true);
		fd = new FrameDragger(this);
		
		button = createButton(img,descr,col);
		
		button.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				if(!fd.isDragged())
					//System.exit(0);
					wind.setVisible(false);
			}
		});
		
		
		this.getContentPane().add(button);
		this.pack();
		
		   Rectangle screenRect = this.getGraphicsConfiguration().getBounds();
		   this.setLocation(
	         screenRect.x + screenRect.width/2 - this.getSize().width/2,
			 screenRect.y + screenRect.height/2 - this.getSize().height/2);
		
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
		locBut = new JButton(createImageIcon("/but"+text+"/but_1.png", descriptions));
		locBut.setIcon(createImageIcon("but"+text+"/but_1.png", descriptions));
		locBut.setPressedIcon(createImageIcon("but"+text+"/but_2.png", descriptions));
		locBut.setRolloverIcon(createImageIcon("but"+text+"/but_1.png", descriptions));
		locBut.setDisabledIcon(createImageIcon("but"+text+"/but_3.png", descriptions));
		locBut.setMargin(new Insets(0,0,0,0));
		
		locBut.addMouseListener(fd);
		locBut.addMouseMotionListener(fd);
		
		locBut.setBorder(null);
		
		
		return locBut;
		
	}
}
