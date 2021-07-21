package sanitizerdispenser.model;

public class UpdateLevel {
	
	private int id;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getPercent() {
		return percent;
	}
	public void setPercent(String percent) {
		this.percent = percent;
	}
	private String percent;
}
