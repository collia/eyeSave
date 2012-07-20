package windows;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.RoundRectangle2D;

import main.Properties;
import main.Statistic;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;


public class StatisticFrame extends JDialog {

	private static final long serialVersionUID = 8129474148120345736L;

	static JDialog wind;
	FrameDragger fd;
	
	Color col = Color.BLUE;
	//private Properties prop;
	
	final JButton button;
	
	
	public StatisticFrame(Color c, Properties prop, Statistic stat) {
		col=c;
	//	this.prop = prop;
		wind = this;
		this.setUndecorated(true);
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setAlwaysOnTop(true);

		stat.calculate();
		
		button = createButton("17","OK",col);//new JButton("OK");
		JLabel textW = new JLabel("Время работы: ");
		JLabel textWday = new JLabel("    Дней: " + stat.getDaysWork());
		JLabel textWhour = new JLabel("    Часов: " + stat.getHourWork());
		JLabel textWmin = new JLabel("    Минут: " + stat.getMinWork());
		JLabel textWsec = new JLabel("    Секунд: "+stat.getSecWork());
		
		textW.setHorizontalAlignment(JLabel.CENTER);
	
		JLabel textNW = new JLabel("Время отдыха: ");
		
		JLabel textNWday = new JLabel("    Дней: "+stat.getDaysNotWork());
		JLabel textNWhour = new JLabel("    Часов: " + stat.getHourNotWork());
		JLabel textNWmin = new JLabel("    Минут: " + stat.getMinNotWork());
		JLabel textNWsec = new JLabel("    Секунд: "+stat.getSecNotWork());
		
		textNW.setHorizontalAlignment(JLabel.CENTER);	
		
		
		
		button.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
			
				wind.setVisible(false);
			}
		});
		
		JPanel pan1 = new JPanel(new GridLayout(10,1));
		pan1.add(textW);
		pan1.add(textWday);
		pan1.add(textWhour);
		pan1.add(textWmin);
		pan1.add(textWsec);
		
		pan1.add(textNW);
		pan1.add(textNWday);
		pan1.add(textNWhour);
		pan1.add(textNWmin);
		pan1.add(textNWsec);
		
		JPanel all = new JPanel(new BorderLayout());
		
		FrameDragger fd = new FrameDragger(this);
		
		all.addMouseListener(fd);
		all.addMouseMotionListener(fd);
		pan1.addMouseListener(fd);
		pan1.addMouseMotionListener(fd);
		textW.addMouseListener(fd);
		textW.addMouseMotionListener(fd);
		textWday.addMouseListener(fd);
		textWday.addMouseMotionListener(fd);
		textWhour.addMouseListener(fd);
		textWhour.addMouseMotionListener(fd);
		textWmin.addMouseListener(fd);
		textWmin.addMouseMotionListener(fd);
		textWsec.addMouseListener(fd);
		textWsec.addMouseMotionListener(fd);
		textNW.addMouseListener(fd);
		textNW.addMouseMotionListener(fd);
		textNWday.addMouseListener(fd);
		textNWday.addMouseMotionListener(fd);
		textNWhour.addMouseListener(fd);
		textNWhour.addMouseMotionListener(fd);
		textNWmin.addMouseListener(fd);
		textNWmin.addMouseMotionListener(fd);
		textNWsec.addMouseListener(fd);
		textNWsec.addMouseMotionListener(fd);
		
		
		all.add(pan1, BorderLayout.CENTER);
		all.add(button, BorderLayout.SOUTH);
		this.getContentPane().add(all);
		this.pack();
		
	   Rectangle screenRect = this.getGraphicsConfiguration().getBounds();
	   this.setLocation(
	         screenRect.x + screenRect.width/2 - this.getSize().width/2,
			 screenRect.y + screenRect.height/2 - this.getSize().height/2);
		

		Shape shape = new RoundRectangle2D.Float(0, 0, this.getWidth(), this.getHeight(), 20, 20);
		com.sun.awt.AWTUtilities.setWindowShape(this, shape);
	
		this.setVisible(true);
		
	}

	 
  	public ImageIcon createImageIcon(String filename, String description) {
		
	    String path = "/resources/images/" + filename;
	    return new ImageIcon(getClass().getResource(path), description); 
 }

private JButton createButton(String text, String descriptions, Color color){
//	if(useColor)
	UIManager.put("Button.background", color);
//	UIManager.put("Button.select", color);
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
