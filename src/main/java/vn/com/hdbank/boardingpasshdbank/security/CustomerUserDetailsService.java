package vn.com.hdbank.boardingpasshdbank.security;


import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import vn.com.hdbank.boardingpasshdbank.repository.CustomerRepository;

@Component
@RequiredArgsConstructor
public class CustomerUserDetailsService implements UserDetailsService {

    private final CustomerRepository customerRepository ;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//        User user = customerRepository.findByEmail(email).orElseThrow(()-> new UsernameNotFoundException("User not found !"));
//        return  user ;
            return null;

    }


}