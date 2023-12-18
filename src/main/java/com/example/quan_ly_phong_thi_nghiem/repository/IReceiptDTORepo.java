package com.example.quan_ly_phong_thi_nghiem.repository;

import com.example.quan_ly_phong_thi_nghiem.model.ReceiptDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface IReceiptDTORepo extends JpaRepository<ReceiptDTO, Long> {
    ReceiptDTO findByAccountUsername(String username);
}
