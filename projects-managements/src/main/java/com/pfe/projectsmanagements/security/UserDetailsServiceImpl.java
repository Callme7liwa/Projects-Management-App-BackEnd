package com.pfe.projectsmanagements.security;

import com.pfe.projectsmanagements.entities.Journalist;
import com.pfe.projectsmanagements.services.Journalist.JournalistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private JournalistService journalistService ;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("En train de chercher le user par user name");
        Journalist journalist = journalistService.getJournalistByUsername(username);
        System.out.println("User foundded is {} : "+journalist.toString());
        Collection<GrantedAuthority> authorities =  new ArrayList<>();
        journalist
                .getRoles()
                .forEach(role->{
                    authorities
                            .add(new SimpleGrantedAuthority(String.valueOf(role.getRole())));
                });
        return new User(journalist.getUserName(),journalist.getPassword() ,authorities);
    }
}
