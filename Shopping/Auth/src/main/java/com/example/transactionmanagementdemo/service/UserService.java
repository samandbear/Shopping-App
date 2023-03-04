package com.example.transactionmanagementdemo.service;

import com.example.transactionmanagementdemo.dao.UserDao;
import com.example.transactionmanagementdemo.domain.entity.User;
import com.example.transactionmanagementdemo.domain.request.RegRequest;
import com.example.transactionmanagementdemo.security.AuthUserDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class UserService implements UserDetailsService {
    private final UserDao userDao;

    @Autowired
    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    @Transactional
    public void createUser(RegRequest user) throws Exception {
        List<User> sameUsername = userDao.getUserByUsername(user.getUsername());
        if (sameUsername != null && sameUsername.size() > 0) {
            throw new Exception("Username existed");
        }
        List<User> sameUserEmail = userDao.getUserByEmail(user.getEmail());
        if (sameUserEmail != null && sameUserEmail.size() > 0) {
            throw new Exception("Email existed");
        }

        userDao.createUser(user.getUsername(), user.getPassword(), user.getEmail());
    }

    @Transactional
    public User getUserByUsername(String username) throws Exception {
        List<User> users = userDao.getUserByUsername(username);
        if (users.size() == 1) {
            return users.get(0);
        } else {
            throw new Exception("We should only have 1 user");
        }
    }

    @Transactional
    public User getUserByUserId(Integer user_id) throws Exception {
        List<User> users = userDao.getUserByUserId(user_id);
        if (users.size() == 1) {
            return users.get(0);
        } else {
            throw new Exception("We should only have 1 user");
        }
    }

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userOptional = userDao.loadUserByUsername(username);

        if (!userOptional.isPresent()){
            throw new UsernameNotFoundException("Username does not exist");
        }

        User user = userOptional.get(); // database user

        return AuthUserDetail.builder() // spring security's userDetail
                .username(user.getUsername())
                .password(new BCryptPasswordEncoder().encode(user.getPassword()))
                .authorities(getAuthoritiesFromUser(user))
                .accountNonExpired(true)
                .accountNonLocked(true)
                .credentialsNonExpired(true)
                .enabled(true)
                .build();
    }


    private List<GrantedAuthority> getAuthoritiesFromUser(User user){
        List<GrantedAuthority> userAuthorities = new ArrayList<>();

        if (user.is_seller()) {
            userAuthorities.add(new SimpleGrantedAuthority("true"));
        }

        return userAuthorities;
    }
}
