package com.example.quan_ly_phong_thi_nghiem.repository;

import com.example.quan_ly_phong_thi_nghiem.model.Receipt;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface IReceiptRepo extends JpaRepository<Receipt, Long> {
    Page<Receipt> findAllByAccountUsername(String username, Pageable pageable);
    Page<Receipt> findAllByStatus(String status, Pageable pageable);

}
