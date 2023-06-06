package vn.com.hdbank.boardingpasshdbank.entity;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import vn.com.hdbank.boardingpasshdbank.common.anotation.MyColumn;

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
    @MyColumn (name = "id")
    private String id;

    @MyColumn (name = "username")
    private String username;

    @MyColumn (name = "password")
    private String password;

    @MyColumn (name = "account_number")
    private String accountNumber;

    @MyColumn (name = "balance")
    private BigDecimal balance;

    @MyColumn (name = "first_name")
    private String firstName;
    @MyColumn (name = "last_name")
    private String lastName;

    @MyColumn (name = "email")
    private String email;

    @MyColumn (name = "phone_number")
    private String phoneNumber;

    @MyColumn (name = "birth_date")
    private String birthDate;

    @MyColumn (name = "address")
    private String address;

    @MyColumn (name = "customer_type")
    private String customerType;

    @MyColumn (name = "created_at")
    private LocalDateTime createdAt;

    @MyColumn (name = "role_id")
    private String roleId;

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
