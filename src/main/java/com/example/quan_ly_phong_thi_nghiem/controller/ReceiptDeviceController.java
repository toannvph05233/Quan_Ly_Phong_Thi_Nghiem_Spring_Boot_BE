package com.example.quan_ly_phong_thi_nghiem.controller;

import com.example.quan_ly_phong_thi_nghiem.model.Receipt;
import com.example.quan_ly_phong_thi_nghiem.model.ReceiptChemistry;
import com.example.quan_ly_phong_thi_nghiem.model.ReceiptDevice;
import com.example.quan_ly_phong_thi_nghiem.service.ReceiptChemistryService;
import com.example.quan_ly_phong_thi_nghiem.service.ReceiptDeviceService;
import com.example.quan_ly_phong_thi_nghiem.service.ReceiptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("receipt_device")
@CrossOrigin("*")
public class ReceiptDeviceController {
    @Autowired
    ReceiptChemistryService receiptChemistryService;

    @Autowired
    ReceiptDeviceService receiptDeviceService;

    @Autowired
    ReceiptService receiptService;

    @GetMapping("{idReceipt}")
    public ResponseEntity<?> getAll(@PathVariable long idReceipt) {
        return ResponseEntity.ok(receiptDeviceService.findAllByReceiptId(idReceipt));
    }

    @GetMapping("status/{idReceipt}")
    public ResponseEntity<?> getAllStatus(@PathVariable long idReceipt) {
        return ResponseEntity.ok(receiptDeviceService.findAllByReceiptIdAndStatus(idReceipt, "đang mượn"));
    }

    @GetMapping("return/{idReceipt}")
    public ResponseEntity<?> returnChemistry(@PathVariable long idReceipt) {
        ReceiptDevice receiptDevice = receiptDeviceService.findById(idReceipt);
        receiptDevice.setStatus("đã trả");
        receiptDeviceService.save(receiptDevice);

        List<ReceiptChemistry> listC = receiptChemistryService.findAllByReceiptIdAndStatus(receiptDevice.getReceipt().getId(), "đang mượn");
        List<ReceiptDevice> listD = receiptDeviceService.findAllByReceiptIdAndStatus(receiptDevice.getReceipt().getId(), "đang mượn");
        if (listD.size() == 0 && listC.size()==0){
            Receipt receipt = receiptService.findById(receiptDevice.getReceipt().getId());
            receipt.setStatus("đã xong");
            return ResponseEntity.ok(receiptService.save(receipt));
        }
        return ResponseEntity.ok(null);
    }
}
