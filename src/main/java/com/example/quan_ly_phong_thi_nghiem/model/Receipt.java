package com.example.quan_ly_phong_thi_nghiem.model;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Date;

@Entity
@Data
public class Receipt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String purpose;
    private String status;
    private Date createDate;
    private Date endDate;
    @ManyToOne
    private Account account;
    @ManyToOne
    private ReceiptType receiptType;
}
