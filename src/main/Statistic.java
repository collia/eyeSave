package main;

import java.util.*;

public class Statistic {
	class Times{
		Date begin;
		Date end;
		public Times(Date b, Date e){
			begin = b;
			end = e;
		}
		public long getInterval(){
			return end.getTime()-begin.getTime();
		}
		
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + getOuterType().hashCode();
			result = prime * result + ((begin == null) ? 0 : begin.hashCode());
			result = prime * result + ((end == null) ? 0 : end.hashCode());
			return result;
		}
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Times other = (Times) obj;
			if (!getOuterType().equals(other.getOuterType()))
				return false;
			if (begin == null) {
				if (other.begin != null)
					return false;
			} else if (!begin.equals(other.begin))
				return false;
			if (end == null) {
				if (other.end != null)
					return false;
			} else if (!end.equals(other.end))
				return false;
			return true;
		}
		private Statistic getOuterType() {
			return Statistic.this;
		}
		
	}
	
	private ArrayList<Times> workTimes = new ArrayList<Times>();
	private ArrayList<Times> playTimes = new ArrayList<Times>();
	
	private Date beginWork;
	private Date beginPlay;
	
	public void beginWork(){
		//workTimes.add(new Date());
		beginWork = new Date();
	}
	public void endWork(){
		//playTimes.add(new Date());
		workTimes.add(new Times(beginWork, new Date()));
		beginWork = null;
	}

	public void beginPlay(){
		//workTimes.add(new Date());
		beginPlay = new Date();
	}
	public void endPlay(){
		//playTimes.add(new Date());
		playTimes.add(new Times(beginPlay,new Date()));
		beginPlay = null;
	}

	
	
	private long timeWork = 0;
	private long timeNotWork = 0;
	
	private int daysWork = 0;
	private int hourWork = 0;
	private int minWork = 0;
	private int secWork = 0;
	private int daysNotWork = 0;
	private int hourNotWork = 0;
	private int minNotWork = 0;
	private int secNotWork = 0;
	
	
	public void calculate(){
		Iterator<Times> it = workTimes.iterator();
		while(it.hasNext()){
			timeWork += it.next().getInterval();
		}
		
		it = playTimes.iterator();
		while(it.hasNext()){
			timeNotWork += it.next().getInterval();
		}
		if(beginWork != null) 
			timeWork += (new Date().getTime() - beginWork.getTime());
	
		if(beginPlay != null) 
			timeNotWork += (new Date().getTime() - beginPlay.getTime());
		
	
		// seconds
		long seconds = timeWork / 1000; 
		// minuted 
		long minutes = seconds / 60; 
		// hours 
		long hours = minutes / 60; 
		// days 
		long days = hours / 24;
		
		daysWork = (int) days;
		hourWork = (int) (days == 0? hours: hours % (days*24));
		minWork =  (int) (hours == 0? minutes: (minutes %(hours*60)));
		secWork =  (int) (minutes == 0?seconds:(seconds %(minutes*60)));
		
		// seconds
		seconds = timeNotWork / 1000; 
		// minutes 
		minutes = seconds / 60; 
		// hours 
		hours = minutes / 60; 
		// days 
		days = hours / 24;
		
		daysNotWork = (int) days;
		hourNotWork = (int) (days == 0?hours: (hours % (days*24)));
		minNotWork =  (int) (hours == 0? minutes: (minutes %(hours*60)));
		secNotWork =  (int) (minutes == 0?seconds: (seconds %(minutes*60)));
		
	}
	
	public int getHourWork() {
		return hourWork;
	}
	public int getMinWork() {
		return minWork;
	}
	public int getHourNotWork() {
		return hourNotWork;
	}
	public int getMinNotWork() {
		return minNotWork;
	}
	public int getSecWork() {
		return secWork;
	}
	public int getSecNotWork() {
		return secNotWork;
	}
	public int getDaysWork() {
		return daysWork;
	}
	public int getDaysNotWork() {
		return daysNotWork;
	}

	
	
	
}
