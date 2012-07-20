package timer;

import java.util.Date;

import java.util.concurrent.*;

import main.Properties;
import main.Statistic;

import windows.*;

public class Timer {
	
	private boolean isWork = true;
	
	private boolean isGo = false;
	private boolean isPaused = false;
	
	
	 
	 int hours = 0;
	 int minutes = 0;
	 int secounds = 25;
	 
	 MainFrame mainPanel;
	 Statistic statistic;
	 
	 private Properties prop;
	 
	 private final ScheduledExecutorService scheduler = 
	       Executors.newScheduledThreadPool(1);
	 ScheduledFuture<?> beeperHandle;
	 
	 class TimerExec implements Runnable{
		 private Message wind = null;
		public void run() {
		 	if(secounds-- <= 0){
	    		secounds = 59;
	    		if(minutes-- <= 0){
	    		   minutes = 59;
	    		   if(hours-- <=0){
	    			   stopTimer();
	    			   
	    		   }
	    		}
	    	}
		 	if((secounds == 0)&(minutes == 5) & (hours == 0))
		 		if(isWork)
		 			wind = new Message("Only 5 minuts", "13", "Only 5 minuts", prop);
		 	if((secounds == 0)&(minutes == 0) & (hours == 0)){
		 		
		 		stopTimer();
		 		mainPanel.setButStop();
		 		
		 		if(wind != null) {
	 				wind.setVisible(false);
	 				wind = null;
	 			}
		 		
		 		if(isWork){
		 			wind = new Message("Timer Stop", "14", "Timer Stop", prop);
		 			statistic.endWork();
		 		} else {
		 			wind = new Message("Timer Stop", "12", "Timer Stop", prop);
		 			statistic.endPlay();
		 		}
		 		isWork = !isWork;
		 		
		 		if(wind instanceof Message ){
		 			while(wind.isVisible());
		 			startTimer();
		 			mainPanel.setButStart();
		 		}
		 		
		 	}
		 	
		 	
	    	mainPanel.setTime(hours, minutes, secounds);
		}
		 
	 }
	 
	public MainFrame getMainPanel() {
		return mainPanel;
	}

	public void setMainPanel(MainFrame mainPanel) {
		this.mainPanel = mainPanel;
	}

	public Timer(Properties prop){
		this.prop = prop;
		
	}
	
	public void startTimer(){
		if(!isPaused)
		 if(isWork)
		 {
			 hours = prop.getWorkHour();//1;
			 minutes = prop.getWorkMin();//0;
			 secounds = 0;
			 statistic.beginWork();
			 
		 } else
		 {
			 hours = prop.getPlayHour();//0;
			 minutes = prop.getPlayMin();//15;
			 secounds = 0;
			 statistic.beginPlay();
		 }
		 isGo = true;
		 TimerExec task = new TimerExec();
		 beeperHandle = 
	            scheduler.scheduleAtFixedRate(task, 0, 1, TimeUnit.SECONDS);
		  
	}
	public void stopTimer(){
		isGo = false;
		if(beeperHandle != null) beeperHandle.cancel(false);
		isPaused = false;
			
		
	}
	public void pauseTimer(){
		isGo = false;
		isPaused = true;
		if(beeperHandle != null) beeperHandle.cancel(false);
	}
	public void startWork(){
		isWork = true;
	}
	public void stopWork(){
		isWork = false;
	}
	
	public boolean isWork() {
		return isWork;
	}

	public boolean isGo() {
		return isGo;
	}

	public void setWork(boolean isWork) {
		this.isWork = isWork;
	}

	public void setGo(boolean isGo) {
		this.isGo = isGo;
	}

	public boolean isPaused() {
		return isPaused;
	}

	public void setPaused(boolean isPaused) {
		this.isPaused = isPaused;
	}

	public void setStatistic(Statistic statistic) {
		this.statistic = statistic;
	}
}
