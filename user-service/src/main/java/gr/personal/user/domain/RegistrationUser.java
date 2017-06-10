package gr.personal.user.domain;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Nick Kanakis on 8/6/2017.
 */
public class RegistrationUser {

    private String username;
    private String password;
    private List<SimpleGrantedAuthority> authorities;

    public RegistrationUser(String username, String password, List<String> roles) {
        this.username = username;
        this.password = password;
        this.authorities = new ArrayList<>();

        //TODO do it with streams
            for (String role:roles) {
            this.authorities.add(new SimpleGrantedAuthority(role));
        }
    }

    public String getUsername() {
        return username;
    }

    public List<SimpleGrantedAuthority> getAuthorities() {
        return authorities;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RegistrationUser user = (RegistrationUser) o;

        if (username != null ? !username.equals(user.username) : user.username != null) return false;
        return password != null ? password.equals(user.password) : user.password == null;

    }

    @Override
    public int hashCode() {
        int result = username != null ? username.hashCode() : 0;
        result = 31 * result + (password != null ? password.hashCode() : 0);
        return result;
    }
}
