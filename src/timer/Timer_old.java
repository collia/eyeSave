package timer;

import java.util.Date;
import java.util.TimerTask;

import main.Properties;
import main.Statistic;

import windows.*;

public class Timer_old {
	
	private boolean isWork = true;
	
	private boolean isGo = false;
	private boolean isPaused = false;
	
	
	 TimerTask task;
	 java.util.Timer timer2;
	 
	 int hours = 0;
	 int minutes = 0;
	 int secounds = 25;
	 
	 MainFrame mainPanel;
	 Statistic statistic;
	 
	 private Properties prop;
	 
	 class TimerExec extends TimerTask{
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
		 		new Message("Only 5 minuts", "12", "Only 5 minuts", prop);
		 	if((secounds == 0)&(minutes == 0) & (hours == 0)){
		 		
		 		stopTimer();
		 		mainPanel.setButStop();
		 		
		 		if(isWork){
		 			wind = new Message("Timer Stop", "13", "Timer Stop", prop);
		 		//	mainPanel.setButNotWork();
		 			statistic.endWork();
		 		} else {
		 			wind = new Message("Timer Stop", "14", "Timer Stop", prop);
		 		//	mainPanel.setButWork();
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

	public Timer_old(Properties prop){
		this.prop = prop;
		 timer2 = new java.util.Timer();
	/*	 task = new TimerTask() {
		    public void run()
		    {
		    //Do work!
		    //	  System.out.println( "Запуск задачи" );
		    //	  System.out.println( "Время: " + new Date().getTime());
		    	if(secounds-- <= 0){
		    		secounds = 60;
		    		if(minutes-- <= 0){
		    		   minutes = 60;
		    		   if((hours-- <=0)&(minutes-- <=0)&(minutes-- <=0)){
		    			   stopTimer();
		    			   
		    		   }
		    		}
		    	}
		    	mainPanel.setTime(hours, minutes, secounds);
		    	
		    }
		  };*/
		  
		
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
		 task = new TimerExec();
		 long curDate = new Date().getTime();
			// long begDate = curDate & (~((long)0xFFFF));
		 long begDate = (curDate/1000) * 1000;
		 Date date = new Date(begDate+1000);
			  //timer2.schedule( task, 100 );
		 timer2.schedule( task, date, 1000 ); //date - java.util.Date
		
		 timer2.schedule( task, 0L, 1000 ); //date - java.util.Date
	}
	public void stopTimer(){
		isGo = false;
		if(task != null) task.cancel();
		timer2.purge();
		isPaused = false;
			
		
	}
	/*public void resumeTimer(){
		isGo = true;
		 task = new TimerExec();
		 long curDate = new Date().getTime();
			// long begDate = curDate & (~((long)0xFFFF));
		 long begDate = (curDate/1000) * 1000;
		 Date date = new Date(begDate+1000);
			  //timer2.schedule( task, 100 );
		 timer2.schedule( task, date, 1000 );
		 
		
	}*/
	public void pauseTimer(){
		isGo = false;
		isPaused = true;
		task.cancel();
		timer2.purge();
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
