package com.example.quan_ly_phong_thi_nghiem.repository;

import com.example.quan_ly_phong_thi_nghiem.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface IAccountRepo extends JpaRepository<Account, Long> {
    Account findByUsername(String username);
}
