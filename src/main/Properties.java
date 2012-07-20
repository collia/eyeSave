package main;

public class Properties {
	private boolean isNewJVM = false;

//	private static boolean isExist = false;
	
	private int workHour = 0;
	private int workMin = 1;
	private int playHour = 0;
	private int playMin = 1;
	
	
	private int size = 2;
	
	public static final int SMALL_SIZE = 0;
	public static final int LARGE_SIZE = 1;
	public static final int BIG_SIZE = 2;
	
	private int posX = 0;
	private int posY = 0;
	
	private Properties(){
		
	}
	static public Properties createPProperties(){
			return new Properties();
	
	}
	
	/**
	 * @param isNewJVM the isNewJVM to set
	 */
	public void setNewJVM(boolean isNewJVM) {
		
		this.isNewJVM = isNewJVM;
	}

	/**
	 * @return the isNewJVM
	 */
	public boolean isNewJVM() {
		return isNewJVM;
	}
	
	public int getWorkHour() {
		return workHour;
	}
	public int getWorkMin() {
		return workMin;
	}
	public int getPlayHour() {
		return playHour;
	}
	public int getPlayMin() {
		return playMin;
	}
	public void setWorkHour(int workHour) {
		this.workHour = workHour;
	}
	public void setWorkMin(int workMin) {
		this.workMin = workMin;
	}
	public void setPlayHour(int playHour) {
		this.playHour = playHour;
	}
	public void setPlayMin(int playMin) {
		this.playMin = playMin;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public int getSize() {
		return size;
	}
	public int getPosX() {
		return posX;
	}
	public void setPosX(int posX) {
		this.posX = posX;
	}
	public int getPosY() {
		return posY;
	}
	public void setPosY(int posY) {
		this.posY = posY;
	}
	
	
	
}
