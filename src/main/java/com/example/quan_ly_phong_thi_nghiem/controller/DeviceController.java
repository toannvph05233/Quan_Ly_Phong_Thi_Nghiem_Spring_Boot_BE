package com.example.quan_ly_phong_thi_nghiem.controller;

import com.example.quan_ly_phong_thi_nghiem.model.Account;
import com.example.quan_ly_phong_thi_nghiem.model.Chemistry;
import com.example.quan_ly_phong_thi_nghiem.model.Device;
import com.example.quan_ly_phong_thi_nghiem.model.ReceiptDTO;
import com.example.quan_ly_phong_thi_nghiem.repository.IReceiptDTORepo;
import com.example.quan_ly_phong_thi_nghiem.service.AccountService;
import com.example.quan_ly_phong_thi_nghiem.service.ChemistryService;
import com.example.quan_ly_phong_thi_nghiem.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("devices")
@CrossOrigin("*")
public class DeviceController {
    @Autowired
    DeviceService deviceService;

    @Autowired
    AccountService accountService;
    @Autowired
    IReceiptDTORepo iReceiptDTORepo;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("admin")
    public ResponseEntity<?> getAllSubjectAdmin(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Account account = accountService.findByUserName(principal.getUsername());
        return ResponseEntity.ok(deviceService.findAll(PageRequest.of(page, size)));
    }
    @PostMapping
    public ResponseEntity<?> createChemistry(@RequestBody Device device) {
        deviceService.save(device);
        return ResponseEntity.ok().body("Device created successfully");
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("import")
    public ResponseEntity<?> importData(@RequestBody Device[] devices) {
        for (Device d : devices) {
            deviceService.save(d);
        }
        return ResponseEntity.ok(null);

    }

    @GetMapping()
    public ResponseEntity<?> getAllSubject(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Account account = accountService.findByUserName(principal.getUsername());
        return ResponseEntity.ok(deviceService.findAllBySubjectName(account.getSubject().getName(), PageRequest.of(page, size)));
    }

    @GetMapping("{name}")
    public ResponseEntity<?> getAllByName(@PathVariable String name) {
        return ResponseEntity.ok(deviceService.findAllByName(name));
    }

    @GetMapping("add/{id}")
    public ResponseEntity<?> addReceipt(@PathVariable long id) {
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Account account = accountService.findByUserName(principal.getUsername());
        ReceiptDTO receiptDTO = iReceiptDTORepo.findByAccountUsername(account.getUsername());
        Device device = deviceService.findById(id);
        List<Device> devices = new ArrayList<>();
        if (receiptDTO == null) {
            receiptDTO = new ReceiptDTO();
            receiptDTO.setAccount(account);
        } else {
            if (receiptDTO.getDevices() != null) {
                devices = receiptDTO.getDevices();
            }
        }
        devices.add(device);
        receiptDTO.setDevices(devices);
        return ResponseEntity.ok(iReceiptDTORepo.save(receiptDTO));
    }

    @GetMapping("notDraft")
    public ResponseEntity<?> getAllSubjectNotReceipt() {
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Account account = accountService.findByUserName(principal.getUsername());
        ReceiptDTO receiptDTO = iReceiptDTORepo.findByAccountUsername(account.getUsername());
        if (receiptDTO == null) {
            receiptDTO = new ReceiptDTO();
            receiptDTO.setAccount(account);
            iReceiptDTORepo.save(receiptDTO);
        }
        return ResponseEntity.ok(deviceService.findAllBySubjectNameAndUsername(account.getSubject().getName(), account.getUsername()));
    }

    @GetMapping("admin/notDraft")
    public ResponseEntity<?> getAllSubjectNotReceiptAdmin() {
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Account account = accountService.findByUserName(principal.getUsername());
        ReceiptDTO receiptDTO = iReceiptDTORepo.findByAccountUsername(account.getUsername());
        if (receiptDTO == null) {
            receiptDTO = new ReceiptDTO();
            receiptDTO.setAccount(account);
            iReceiptDTORepo.save(receiptDTO);
        }
        return ResponseEntity.ok(deviceService.findAllByAndUsername(account.getUsername()));
    }
}
