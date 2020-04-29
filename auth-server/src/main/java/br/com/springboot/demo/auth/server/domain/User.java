package br.com.springboot.demo.auth.server.domain;

import java.util.Collection;
import java.util.List;

import javax.validation.constraints.NotEmpty;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.sun.istack.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Document(collection = User.COLLECTION_NAME)
public class User implements UserDetails {

	public static final String COLLECTION_NAME = "users";

	private static final long serialVersionUID = 1L;

	@Id
	private String id;

	@NotEmpty
	@NotNull
	private String name;

	@NotEmpty
	@NotNull
	private String email;

	@NotEmpty
	@NotNull
	private String password;

	@NotNull
	private List<Profile> profiles;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.profiles;
	}

	@Override
	public String getPassword() {
		return this.password;
	}

	@Override
	public String getUsername() {
		return this.email;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
