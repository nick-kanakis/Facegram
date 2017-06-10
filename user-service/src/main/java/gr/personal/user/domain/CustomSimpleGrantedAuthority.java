package gr.personal.user.domain;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.util.Assert;

/**
 * Created by Nick Kanakis on 10/6/2017.
 *
 * Implemented a custom version of SimpleGrantedAuthority in order to enable instance construction @Controller.
 *
 */
public class CustomSimpleGrantedAuthority implements GrantedAuthority {
    private static final long serialVersionUID = 420L;
    private String role;

    public CustomSimpleGrantedAuthority(String role) {
        Assert.hasText(role, "A granted authority textual representation is required");
        this.role = role;
    }
    public CustomSimpleGrantedAuthority() {
    }

    public String getAuthority() {
        return this.role;
    }

    public boolean equals(Object obj) {
        return this == obj?true:(obj instanceof CustomSimpleGrantedAuthority?this.role.equals(((CustomSimpleGrantedAuthority)obj).role):false);
    }

    public int hashCode() {
        return this.role.hashCode();
    }

    public String toString() {
        return this.role;
    }
}
