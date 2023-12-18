package com.example.quan_ly_phong_thi_nghiem.service;

import com.example.quan_ly_phong_thi_nghiem.model.ReceiptChemistry;
import com.example.quan_ly_phong_thi_nghiem.model.ReceiptDevice;
import com.example.quan_ly_phong_thi_nghiem.repository.IReceiptChemistryRepo;
import com.example.quan_ly_phong_thi_nghiem.repository.IReceiptDeviceRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ReceiptChemistryService {
    @Autowired
    IReceiptChemistryRepo iReceiptChemistryRepo;

    public List<ReceiptChemistry> findAllByReceiptId(long id){
        return iReceiptChemistryRepo.findAllByReceiptId(id);
    }

    public List<ReceiptChemistry> findAllByReceiptIdAndStatus(long id, String status){
        return iReceiptChemistryRepo.findAllByReceiptIdAndStatus(id, status);
    }

    public void save(ReceiptChemistry receiptChemistry){
        iReceiptChemistryRepo.save(receiptChemistry);
    }

    public ReceiptChemistry findById(long id){
        return iReceiptChemistryRepo.findById(id).get();
    }
}

