package vn.com.hdbank.boardingpasshdbank.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import vn.com.hdbank.boardingpasshdbank.security.JwtUtilities;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api")
public class SecurityController {
    private final AuthenticationManager authenticationManager ;
    private final JwtUtilities jwtUtilities ;
    @PostMapping("/get-token")
    public String authenticate(@RequestBody Map<String,String> loginDto) {
        Authentication authentication= authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDto.get("username"),
                        loginDto.get("password")
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        //User user = iUserRepository.findByEmail(authentication.getName()).orElseThrow(() ->
        // new UsernameNotFoundException("User not found"));
        //List<String> rolesNames = new ArrayList<>();
        //user.getRoles().forEach(r-> rolesNames.add(r.getRoleName()));
        //String token = jwtUtilities.generateToken(user.getUsername(),rolesNames);

        //mock data for security
        List<String> rolesNames = new ArrayList<>();
        rolesNames.add("User");

        return jwtUtilities.generateToken("thanhcong",rolesNames);
    }
}
