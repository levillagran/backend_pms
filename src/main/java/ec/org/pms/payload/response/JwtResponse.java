package ec.org.pms.payload.response;

import java.util.List;

import lombok.Data;

@Data
public class JwtResponse {
	private String token;
	private String type = "Bearer";
	private int id;
	private String username;
	private List<String> roles;
	
	public JwtResponse(String token, int id, String username, List<String> roles) {
		super();
		this.token = token;
		this.id = id;
		this.username = username;
		this.roles = roles;
	}
	
	

}
