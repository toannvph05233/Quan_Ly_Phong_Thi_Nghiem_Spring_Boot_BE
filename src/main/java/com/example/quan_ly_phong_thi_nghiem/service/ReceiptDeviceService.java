package com.example.quan_ly_phong_thi_nghiem.service;

import com.example.quan_ly_phong_thi_nghiem.model.ReceiptChemistry;
import com.example.quan_ly_phong_thi_nghiem.model.ReceiptDevice;
import com.example.quan_ly_phong_thi_nghiem.repository.IReceiptDeviceRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReceiptDeviceService {
    @Autowired
    IReceiptDeviceRepo iReceiptDeviceRepo;

    public List<ReceiptDevice> findAllByReceiptId(long id){
        return iReceiptDeviceRepo.findAllByReceiptId(id);
    }

    public List<ReceiptDevice> findAllByReceiptIdAndStatus(long id, String status){
        return iReceiptDeviceRepo.findAllByReceiptIdAndStatus(id, status);
    }
    public void save(ReceiptDevice receiptDevice){
         iReceiptDeviceRepo.save(receiptDevice);
    }
    public ReceiptDevice findById(long id){
         return iReceiptDeviceRepo.findById(id).get();
    }

}
