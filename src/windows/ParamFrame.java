package windows;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.RoundRectangle2D;
import java.text.MessageFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import main.Properties;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;



public class ParamFrame extends JDialog {

	private static final long serialVersionUID = 8129474148120345736L;

	static JDialog wind;
	FrameDragger fd;
	
	Color col = Color.BLUE;
	//private Properties prop;
	
	final JButton button;
	JTextField timeWork;
	JTextField timeNotWork;
	
	JSlider size;

class DocListener implements DocumentListener{	
    void checkDocument(DocumentEvent e) {
        try {
            String text = e.getDocument().getText(0, e.getDocument().getLength());
       // 	String text1 = ipFieldMain.getText();
       // 	String text2 = ipFieldSource.getText();
       // 	String text3 = ipFieldClient.getText();
        //    button.setEnabled(checkString(text1) && 
         //   				  checkString(text2) &&
          //  				  checkString(text3));
            button.setEnabled(checkString(text)); 
        } catch (BadLocationException ex) {
            //Do something, OK?
        }
    }
    public void insertUpdate(DocumentEvent e) {
        checkDocument(e);
    }
    public void removeUpdate(DocumentEvent e) {
        checkDocument(e);
    }
    public void changedUpdate(DocumentEvent e) {
        checkDocument(e);
    }
}
	
	public ParamFrame(Color c,final Properties prop, final MainFrame parent) {
		col=c;
	//	this.prop = prop;
		wind = this;
		this.setUndecorated(true);
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setAlwaysOnTop(true);

		button = createButton("17","OK",col);//new JButton("OK");
		JButton buttonNo = createButton("18","Cansel",col);//new JButton("Cansel");
		
		JLabel textW = new JLabel("Время работы");
		//textW.setAlignmentX(JLabel.RIGHT_ALIGNMENT);
		textW.setHorizontalAlignment(JLabel.CENTER);
		
		Object[] testArgs = {new Integer(prop.getWorkHour()),new Integer(prop.getWorkMin())}; 
		MessageFormat mf = new MessageFormat("{0,number,00}:{1,number,00}");
		String res = mf.format(testArgs);
		
		timeWork = new JTextField(res);
		timeWork.getDocument().addDocumentListener(new DocListener());
		
		JLabel textNW = new JLabel("Время отдыха");
		//textNW.setAlignmentX(JLabel.RIGHT_ALIGNMENT);
		textNW.setHorizontalAlignment(JLabel.CENTER);
		
		Object[] testArgs2 = {new Integer(prop.getPlayHour()),new Integer(prop.getPlayMin())}; 
		mf = new MessageFormat("{0,number,00}:{1,number,00}");
		res = mf.format(testArgs2);
		
		timeNotWork = new JTextField(res);
		timeNotWork.getDocument().addDocumentListener(new DocListener());
		
		JLabel textS = new JLabel("Размер:");
		//textS.setAlignmentX(JLabel.RIGHT_ALIGNMENT);
		textS.setHorizontalAlignment(JLabel.CENTER);
		
		UIManager.put("Slider.background", col);		
		size =  new JSlider(0,2,prop.getSize()); 
		size.setSnapToTicks(true);
		
		GridLayout gl = new GridLayout(4,2);
		gl.setHgap(5);
		gl.setVgap(5);
		
		JPanel all = new JPanel(gl);
		
		button.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				//StringTokenizer st = new StringTokenizer(":");
				String[] st = timeWork.getText().split(":");
				prop.setWorkHour(Integer.parseInt(st[0]));
				prop.setWorkMin(Integer.parseInt(st[1]));
				
				st = timeNotWork.getText().split(":");
				prop.setPlayHour(Integer.parseInt(st[0]));
				prop.setPlayMin(Integer.parseInt(st[1]));
				
				prop.setSize(size.getValue());
				
				parent.updateWind();
				
				wind.setVisible(false);
			}
		});
		
		buttonNo.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				wind.setVisible(false);
			}
		});
		
		all.add(textW);
		all.add(timeWork);
		
		all.add(textNW);
		all.add(timeNotWork);
		
		all.add(textS);
		all.add(size);
		
		all.add(button);
		all.add(buttonNo);
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

	 
    final Pattern pat = Pattern.compile("\\b([0]?[0-9]?)\\:" +
	            "([0-5][0-9])\\b");
	boolean checkString(String s) {
	        Matcher m = pat.matcher(s);
	        return m.matches();
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
