package sanitizerdispenser.model;

public class SanitizerLevel {
	private int id;
	private boolean status;
	private int levelPercent;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	public int getLevelPercent() {
		return levelPercent;
	}
	public void setLevelPercent(int levelPercent) {
		this.levelPercent = levelPercent;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	private String time;
}
