package com.example.quan_ly_phong_thi_nghiem.model.dto;

import com.example.quan_ly_phong_thi_nghiem.model.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.List;

@Data
@AllArgsConstructor
public class AccountToken {
    private long id;
    private String username;
    private String avatar;
    private String token;
    private Role role;
}