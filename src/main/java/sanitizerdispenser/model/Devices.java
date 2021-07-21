package sanitizerdispenser.model;
import org.springframework.data.annotation.Id;

import lombok.Data;

public class Devices {
	@Id
	private int id;
	private String address;
	private String city;
	boolean status;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getCity() {
		return city;
	}
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	public void setCity(String city) {
		this.city = city;
	}
}
