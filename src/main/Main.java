package main;

import java.io.IOException;
import javax.swing.JOptionPane;
import org.jdom.JDOMException;

import windows.*;
import timer.Timer;
import timer.Timer;

public class Main {

	
	public static void main(String[] args) {
		//java.runtime.version=1.6.0_13-b03
		
		Properties prop = Properties.createPProperties();
		
		
		int diferese = System.getProperty("java.runtime.version").compareTo("1.6.0_10");
		if(diferese < 0) {
			int answ = JOptionPane.showConfirmDialog(null, 
					"Your java version is: " + System.getProperty("java.runtime.version") + 
					" but need 1.6.0_10 \n" +
					"Do you want contunie (Will be not work some extensions)?", 
					"Error",
					JOptionPane.YES_NO_OPTION, 
					JOptionPane.INFORMATION_MESSAGE);
		 if(answ == JOptionPane.NO_OPTION){
			 System.exit(1);
		 } else{
			 prop.setNewJVM(false);
		 }
		 
		} else {
			prop.setNewJVM(true);
		}
		
		try {
			if(!PropFile.readParemeters(prop));
		} catch (JDOMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		Statistic statistic = new Statistic();
		
		 Timer timer = new Timer(prop);
		 
		 timer.setStatistic(statistic);
		 
	
		 MainFrame frame = new MainFrame(timer, prop);
		 timer.setMainPanel(frame);
		 frame.setStatistic(statistic);
		 frame.setVisible(true);
	}

}
