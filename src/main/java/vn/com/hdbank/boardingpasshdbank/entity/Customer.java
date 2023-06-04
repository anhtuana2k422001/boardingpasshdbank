package vn.com.hdbank.boardingpasshdbank.entity;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.math.BigDecimal;
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
    private String accountNumber;
    private String username;
    private String password;
    private String email;
    private String phoneNumber;
    private String address;
    private BigDecimal balance;

    /* Mock data */
    private List <String> listRole = new ArrayList<>() ;
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        listRole.add("User");
        List<GrantedAuthority> authorities = new ArrayList<>();
        this.listRole.forEach(role -> authorities.add(new SimpleGrantedAuthority(role)));
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
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
