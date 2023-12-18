package com.example.quan_ly_phong_thi_nghiem.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String username;
    private String password;
    private String phone;
    private String email;
    private String avatar;
    @ManyToOne
    private Role role;
    @ManyToOne
    private Subject subject;
}
