package QR;

public class EmailModel {
	private String Ref;
	private String Email;
	
	
	public EmailModel(String ref, String email) {
		super();
		Ref = ref;
		Email = email;
	}
	public String getRef() {
		return Ref;
	}
	public void setRef(String ref) {
		Ref = ref;
	}
	public String getEmail() {
		return Email;
	}
	public void setEmail(String email) {
		Email = email;
	}
	
	

}
