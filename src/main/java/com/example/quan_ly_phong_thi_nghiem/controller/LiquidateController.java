package com.example.quan_ly_phong_thi_nghiem.controller;


import com.example.quan_ly_phong_thi_nghiem.model.*;
import com.example.quan_ly_phong_thi_nghiem.repository.ILiquidateRepo;
import com.example.quan_ly_phong_thi_nghiem.service.ReceiptDeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("liquidates")
@CrossOrigin("*")
public class LiquidateController {
    @Autowired
    ILiquidateRepo iLiquidateRepo;

    @Autowired
    ReceiptDeviceService receiptDeviceService;
    @GetMapping("{idReceiptDevice}")
    public ResponseEntity<?> returnChemistry(@PathVariable long idReceiptDevice) {
        ReceiptDevice receiptDevice = receiptDeviceService.findById(idReceiptDevice);
        Liquidate liquidate = new Liquidate();
        liquidate.setQuantity(receiptDevice.getQuantity());
        liquidate.setDevice(receiptDevice.getDevice());
        iLiquidateRepo.save(liquidate);

        receiptDevice.setStatus("thanh lý");
        receiptDeviceService.save(receiptDevice);
        return ResponseEntity.ok(null);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<?> getAllSubjectAdmin(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        Page<Liquidate> liquidatePage = iLiquidateRepo.findAll(PageRequest.of(page, size));
        return ResponseEntity.ok(liquidatePage);
    }
}
