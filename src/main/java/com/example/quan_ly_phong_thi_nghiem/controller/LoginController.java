package com.example.quan_ly_phong_thi_nghiem.controller;

import com.example.quan_ly_phong_thi_nghiem.model.Account;
import com.example.quan_ly_phong_thi_nghiem.model.dto.AccountToken;
import com.example.quan_ly_phong_thi_nghiem.service.AccountService;
import com.example.quan_ly_phong_thi_nghiem.service.JwtService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.Date;


@RestController
@CrossOrigin("*")
public class LoginController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    AccountService accountService;

    @Autowired
    JwtService jwtService;

    @PostMapping("login")
    public AccountToken login(@RequestBody Account account) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(account.getUsername(), account.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtService.generateTokenLogin(account.getUsername());
        Account account1 = accountService.findByUserName(account.getUsername());
        return new AccountToken(account1.getId(),account1.getUsername(),account1.getAvatar(),token,account1.getRole());
    }



}
