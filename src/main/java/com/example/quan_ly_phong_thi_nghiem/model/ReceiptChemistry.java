package com.example.quan_ly_phong_thi_nghiem.model;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class ReceiptChemistry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String status = "đang mượn";
    private int quantity;
    @ManyToOne
    private Chemistry chemistry;
    @ManyToOne
    private Receipt receipt;
}
