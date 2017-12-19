package QR;

public class WarehouseModel {
	private String Ref;
	private String Description;
	private String Number;
	private String City;
	

	public WarehouseModel(String ref, String description, String number, String city) {
		super();
		Ref = ref;
		Description = description;
		Number = number;
		City = city;
	}
	public String getNumber() {
		return Number;
	}
	public void setNumber(String number) {
		Number = number;
	}
	public String getCity() {
		return City;
	}
	public void setCity(String city) {
		City = city;
	}
	public String getRef() {
		return Ref;
	}
	public void setRef(String ref) {
		Ref = ref;
	}
	public String getDescription() {
		return Description;
	}
	public void setDescription(String Description) {
		this.Description = Description;
	}
}
