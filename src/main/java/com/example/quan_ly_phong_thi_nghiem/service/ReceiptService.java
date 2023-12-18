package com.example.quan_ly_phong_thi_nghiem.service;

import com.example.quan_ly_phong_thi_nghiem.model.Receipt;
import com.example.quan_ly_phong_thi_nghiem.repository.IReceiptRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ReceiptService {
    @Autowired
    IReceiptRepo iReceiptRepo;


    public Page<Receipt> findAllByAccountUsername(String username, Pageable pageable){
        return iReceiptRepo.findAllByAccountUsername(username,pageable);
    }

    public Page<Receipt> findAll(Pageable pageable, String status){
        return iReceiptRepo.findAllByStatus(status, pageable);
    }

    public Page<Receipt> findAll(Pageable pageable){
        return iReceiptRepo.findAll(pageable);
    }

    public Receipt save(Receipt receipt){
        return iReceiptRepo.save(receipt);
    }
    public Receipt findById(long id){
        return iReceiptRepo.findById(id).get();
    }
}
