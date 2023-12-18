package com.example.quan_ly_phong_thi_nghiem.controller;

import com.example.quan_ly_phong_thi_nghiem.model.Account;
import com.example.quan_ly_phong_thi_nghiem.model.Chemistry;
import com.example.quan_ly_phong_thi_nghiem.model.Device;
import com.example.quan_ly_phong_thi_nghiem.model.ReceiptDTO;
import com.example.quan_ly_phong_thi_nghiem.repository.IReceiptDTORepo;
import com.example.quan_ly_phong_thi_nghiem.service.AccountService;
import com.example.quan_ly_phong_thi_nghiem.service.ChemistryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Pageable;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("chemistry")
@CrossOrigin("*")
public class ChemistryController {
    @Autowired
    ChemistryService chemistryService;

    @Autowired
    AccountService accountService;

    @Autowired
    IReceiptDTORepo iReceiptDTORepo;


    @GetMapping()
    public ResponseEntity<?> getAllSubject(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Account account = accountService.findByUserName(principal.getUsername());
        return ResponseEntity.ok(chemistryService.findAllBySubjectName(account.getSubject().getName(), PageRequest.of(page, size)));
    }
    @PostMapping
    public ResponseEntity<?> createChemistry(@RequestBody Chemistry chemistry) {
        chemistryService.save(chemistry);
        return ResponseEntity.ok().body("Chemistry created successfully");
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("import")
    public ResponseEntity<?> importData(@RequestBody Chemistry[] chemistry) {
        for (Chemistry c : chemistry) {
            chemistryService.save(c);
        }
        return ResponseEntity.ok(null);

    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("admin")
    public ResponseEntity<?> getAllSubjectAdmin(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Account account = accountService.findByUserName(principal.getUsername());
        return ResponseEntity.ok(chemistryService.findAll(PageRequest.of(page, size)));
    }

    @GetMapping("{name}")
    public ResponseEntity<?> getAllByName(@PathVariable String name) {
        return ResponseEntity.ok(chemistryService.findAllByName(name));
    }

    @GetMapping("add/{id}")
    public ResponseEntity<?> addReceipt(@PathVariable long id) {
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Account account = accountService.findByUserName(principal.getUsername());
        ReceiptDTO receiptDTO = iReceiptDTORepo.findByAccountUsername(account.getUsername());
        Chemistry chemistry = chemistryService.findById(id);
        List<Chemistry> chemistryList = new ArrayList<>();
        if (receiptDTO == null) {
            receiptDTO = new ReceiptDTO();
            receiptDTO.setAccount(account);
        } else {
            if (receiptDTO.getChemistry() != null) {
                chemistryList = receiptDTO.getChemistry();
            }
        }
        chemistryList.add(chemistry);
        receiptDTO.setChemistry(chemistryList);
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
        return ResponseEntity.ok(chemistryService.findAllBySubjectNameAndUsername(account.getSubject().getName(), account.getUsername()));
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
        return ResponseEntity.ok(chemistryService.findAllByAndUsername(account.getUsername()));
    }
}
