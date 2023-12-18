package com.example.quan_ly_phong_thi_nghiem.model.dto;

import com.example.quan_ly_phong_thi_nghiem.model.ReceiptDTO;
import lombok.Data;

import java.sql.Date;

@Data
public class ReceiptDTORequest {
    private ReceiptDTO receiptDTO;
    private String name;
    private String purpose;
    private Date endDate;
}
