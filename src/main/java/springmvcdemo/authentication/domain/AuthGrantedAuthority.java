package springmvcdemo.authentication.domain;

import org.springframework.security.core.GrantedAuthority;

public class AuthGrantedAuthority implements GrantedAuthority {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7271361587149021918L;
	
	private String authority;

	public AuthGrantedAuthority(String authority) {
		this.authority = authority;
	}

	public String getAuthority() {
		return authority;
	}

}
