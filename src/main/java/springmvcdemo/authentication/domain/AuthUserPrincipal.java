package springmvcdemo.authentication.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import springmvcdemo.authentication.model.User;
import springmvcdemo.authentication.service.UserService;

public class AuthUserPrincipal implements UserDetails {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1026882722639048927L;
	private User user;
	
	public AuthUserPrincipal(User user) {
		this.user = user;
	}

	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<AuthGrantedAuthority> list=new ArrayList<AuthGrantedAuthority>();
		String authorities = this.user.getAuthorities();
		if (authorities != null && authorities.isEmpty() == false) {
			String[] auths = authorities.split(",");
			for(int i=0;i<auths.length;i++){
				list.add(new AuthGrantedAuthority(auths[i]));
			}
		}
		return list;
	}

	public String getPassword() {
		return this.user.getPassword();
	}

	public String getUsername() {
		return this.user.getUsername();
	}

	public boolean isEnabled() {
		return this.user.getEnabled();
	}

	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

}
