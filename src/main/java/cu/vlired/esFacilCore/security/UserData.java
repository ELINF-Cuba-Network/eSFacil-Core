package cu.vlired.esFacilCore.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import cu.vlired.esFacilCore.model.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@Setter @Getter
public class UserData implements UserDetails {

    private UUID id;

    private String lastName;

    private String firstName;

    private String username;

    @JsonIgnore
    private String email;

    @JsonIgnore
    private String password;
    
    private Collection<? extends GrantedAuthority> authorities;

    UserData(
        UUID id,
        String firstName,
        String lastName,
        String username,
        String email,
        String password,
        Collection<? extends GrantedAuthority> authorities
    ) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.email = email;
        this.password = password;
        this.authorities = authorities;
    }

     static UserData create(User user) {
        List<GrantedAuthority> authorities = new LinkedList<>();
       
        authorities.add(new SimpleGrantedAuthority(user.getRole().getName()));
         
        return new UserData(
            user.getId(),
            user.getFirstName(),
            user.getLastName(),
            user.getUsername(),
            user.getEmail(),
            user.getPassword(),
            authorities
        );
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserData that = (UserData) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }
}
