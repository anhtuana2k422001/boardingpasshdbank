package vn.com.hdbank.boardingpasshdbank.entity;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.io.Serializable;
import java.util.ArrayList;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Customer implements Serializable, UserDetails {
    private int id;
    private String firstName;
    private String lastName;
    private String birthDate;
    private String customerType;
    private LocalDateTime createdAt;
    //mock data
    List <String> roles = new ArrayList<>() ;
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        //mock data
       // roles.add("Admin");
        roles.add("User");
        List<GrantedAuthority> authorities = new ArrayList<>();
        this.roles.forEach(role -> authorities.add(new SimpleGrantedAuthority(role)));
        return authorities;
    }

    @Override
    public String getPassword() {
        //"thanhcong" format Bcrypt
        return "$2a$12$v6itJZtxbG5c4egalbWycOEz8cZ26BrsMGSfq3ZofvVsJLVLKHJA6";
    }

    @Override
    public String getUsername() {
        return "thanhcong";
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
