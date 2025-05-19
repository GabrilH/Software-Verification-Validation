package services;

public class Country {

	private String code;
	private double latitude, longitude;
	private String name;
	
	public Country(String code, double latitude, double longitude, String name) {
		super();
		this.code = code;
		this.latitude = latitude;
		this.longitude = longitude;
		this.name = name;
	}
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
