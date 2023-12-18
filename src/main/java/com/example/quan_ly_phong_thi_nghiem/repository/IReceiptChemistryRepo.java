package com.example.quan_ly_phong_thi_nghiem.repository;

import com.example.quan_ly_phong_thi_nghiem.model.ReceiptChemistry;
import com.example.quan_ly_phong_thi_nghiem.model.ReceiptDevice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface IReceiptChemistryRepo extends JpaRepository<ReceiptChemistry, Long> {
    List<ReceiptChemistry> findAllByReceiptId(long id);
    List<ReceiptChemistry> findAllByReceiptIdAndStatus(long id, String status);

}
