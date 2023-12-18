package com.example.quan_ly_phong_thi_nghiem.controller;

import com.example.quan_ly_phong_thi_nghiem.model.*;
import com.example.quan_ly_phong_thi_nghiem.model.dto.ReceiptDTORequest;
import com.example.quan_ly_phong_thi_nghiem.repository.IReceiptDTORepo;
import com.example.quan_ly_phong_thi_nghiem.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;

@RestController
@RequestMapping("receipts")
@CrossOrigin("*")
public class ReceiptController {
    @Autowired
    ReceiptService receiptService;

    @Autowired
    AccountService accountService;

    @Autowired
    ReceiptChemistryService receiptChemistryService;

    @Autowired
    ReceiptDeviceService receiptDeviceService;

    @Autowired
    DeviceService deviceService;

    @Autowired
    ChemistryService chemistryService;

    @Autowired
    IReceiptDTORepo iReceiptDTORepo;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("all")
    public ResponseEntity<?> getAll(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "") String status) {
        if (status.equals("")){
            return ResponseEntity.ok(receiptService.findAll(PageRequest.of(page, 10)));
        }
        return ResponseEntity.ok(receiptService.findAll(PageRequest.of(page, 10), status));
    }

    @GetMapping()
    public ResponseEntity<?> getAllUsername(@RequestParam(defaultValue = "0") int page) {
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Account account = accountService.findByUserName(principal.getUsername());
        return ResponseEntity.ok(receiptService.findAllByAccountUsername(account.getUsername(), PageRequest.of(page, 10)));
    }

    @PostMapping("create")
    public ResponseEntity<?> create(@RequestBody ReceiptDTORequest receiptDTORequest) {
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Account account = accountService.findByUserName(principal.getUsername());

        Receipt receipt = new Receipt();
        receipt.setName(receiptDTORequest.getName());
        receipt.setPurpose(receiptDTORequest.getPurpose());
        receipt.setEndDate(receiptDTORequest.getEndDate());
        receipt.setAccount(account);
        receipt.setCreateDate(new Date(System.currentTimeMillis()));
        receipt.setStatus("chờ xác nhận");
        ReceiptType receiptType = new ReceiptType();
        receiptType.setId(1);
        receipt.setReceiptType(receiptType);
        receiptService.save(receipt);

        for (Device d : receiptDTORequest.getReceiptDTO().getDevices()) {
            ReceiptDevice receiptDevice = new ReceiptDevice();
            receiptDevice.setReceipt(receipt);
            receiptDevice.setDevice(d);
            receiptDevice.setQuantity(d.getQuantity());
            receiptDeviceService.save(receiptDevice);
            Device device = deviceService.findById(d.getId());
            device.setQuantity(device.getQuantity() - d.getQuantity());
            deviceService.save(device);
        }

        for (Chemistry c : receiptDTORequest.getReceiptDTO().getChemistry()) {
            ReceiptChemistry chemistry = new ReceiptChemistry();
            chemistry.setReceipt(receipt);
            chemistry.setChemistry(c);
            chemistry.setQuantity(c.getQuantity());
            receiptChemistryService.save(chemistry);
            Chemistry chemistry1 = chemistryService.findById(c.getId());
            chemistry1.setQuantity(chemistry1.getQuantity() - c.getQuantity());
            chemistryService.save(chemistry1);
        }

        ReceiptDTO receiptDTO = receiptDTORequest.getReceiptDTO();
        receiptDTO.setChemistry(null);
        receiptDTO.setDevices(null);
        iReceiptDTORepo.save(receiptDTO);

        return ResponseEntity.ok(null);
    }
    @PostMapping("admin/create")
    public ResponseEntity<?> adminCreate(@RequestBody ReceiptDTORequest receiptDTORequest) {
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Account account = accountService.findByUserName(principal.getUsername());

        Receipt receipt = new Receipt();
        receipt.setName(receiptDTORequest.getName());
        receipt.setPurpose(receiptDTORequest.getPurpose());
        receipt.setEndDate(new Date(System.currentTimeMillis()));
        receipt.setAccount(account);
        receipt.setCreateDate(new Date(System.currentTimeMillis()));
        receipt.setStatus("đã xong");
        ReceiptType receiptType = new ReceiptType();
        receiptType.setId(2);
        receipt.setReceiptType(receiptType);
        receiptService.save(receipt);

        for (Device d : receiptDTORequest.getReceiptDTO().getDevices()) {
            ReceiptDevice receiptDevice = new ReceiptDevice();
            receiptDevice.setReceipt(receipt);
            receiptDevice.setDevice(d);
            receiptDevice.setQuantity(d.getQuantity());
            receiptDeviceService.save(receiptDevice);
            Device device = deviceService.findById(d.getId());
            device.setQuantity(device.getQuantity() + d.getQuantity());
            deviceService.save(device);
        }

        for (Chemistry c : receiptDTORequest.getReceiptDTO().getChemistry()) {
            ReceiptChemistry chemistry = new ReceiptChemistry();
            chemistry.setReceipt(receipt);
            chemistry.setChemistry(c);
            chemistry.setQuantity(c.getQuantity());
            receiptChemistryService.save(chemistry);
            Chemistry chemistry1 = chemistryService.findById(c.getId());
            chemistry1.setQuantity(chemistry1.getQuantity() + c.getQuantity());
            chemistryService.save(chemistry1);
        }

        ReceiptDTO receiptDTO = receiptDTORequest.getReceiptDTO();
        receiptDTO.setChemistry(null);
        receiptDTO.setDevices(null);
        iReceiptDTORepo.save(receiptDTO);

        return ResponseEntity.ok(null);
    }


    @GetMapping("draft")
    public ResponseEntity<?> getDraft() {
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Account account = accountService.findByUserName(principal.getUsername());
        return ResponseEntity.ok(iReceiptDTORepo.findByAccountUsername(account.getUsername()));
    }

    @GetMapping("deleteDDraft/{id}")
    public ResponseEntity<?> deleteDDraft(@PathVariable long id) {
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Account account = accountService.findByUserName(principal.getUsername());
        ReceiptDTO receiptDTO = iReceiptDTORepo.findByAccountUsername(account.getUsername());
        for (int i = 0; i < receiptDTO.getDevices().size(); i++) {
            if (receiptDTO.getDevices().get(i).getId() == id) {
                receiptDTO.getDevices().remove(i);
            }
        }
        iReceiptDTORepo.save(receiptDTO);

        return ResponseEntity.ok(iReceiptDTORepo.findByAccountUsername(account.getUsername()));
    }

    @GetMapping("deleteCDraft/{id}")
    public ResponseEntity<?> deleteCDraft(@PathVariable long id) {
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Account account = accountService.findByUserName(principal.getUsername());
        ReceiptDTO receiptDTO = iReceiptDTORepo.findByAccountUsername(account.getUsername());

        for (int i = 0; i < receiptDTO.getChemistry().size(); i++) {
            if (receiptDTO.getChemistry().get(i).getId() == id) {
                receiptDTO.getChemistry().remove(i);
            }
        }
        iReceiptDTORepo.save(receiptDTO);
        return ResponseEntity.ok(iReceiptDTORepo.findByAccountUsername(account.getUsername()));
    }

    @GetMapping("changeStatus/{id}")
    public ResponseEntity<?> changeStatus(@PathVariable long id) {
        Receipt receipt = receiptService.findById(id);
        if (receipt.getStatus().equals("chờ xác nhận")) {
            receipt.setStatus("đợi trả đồ");
        } else if (receipt.getStatus().equals("đợi trả đồ")) {
            receipt.setStatus("đã xong");
        }
        return ResponseEntity.ok(receiptService.save(receipt));
    }

    @GetMapping("cancel/{id}")
    public ResponseEntity<?> cancel(@PathVariable long id) {
        Receipt receipt = receiptService.findById(id);
        receipt.setStatus("đã hủy");
        return ResponseEntity.ok(receiptService.save(receipt));
    }
}
