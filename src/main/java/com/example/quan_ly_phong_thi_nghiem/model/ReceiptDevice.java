package com.example.quan_ly_phong_thi_nghiem.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class ReceiptDevice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private int quantity;
    private String status = "đang mượn";
    @ManyToOne
    private Device device;
    @ManyToOne
    private Receipt receipt;

}
