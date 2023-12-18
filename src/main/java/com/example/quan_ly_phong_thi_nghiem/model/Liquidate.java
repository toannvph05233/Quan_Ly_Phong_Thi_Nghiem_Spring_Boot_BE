package com.example.quan_ly_phong_thi_nghiem.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Liquidate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private int quantity;
    @ManyToOne
    private Device device;
}