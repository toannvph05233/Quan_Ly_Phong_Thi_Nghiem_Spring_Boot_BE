package com.example.quan_ly_phong_thi_nghiem.model;

import com.example.quan_ly_phong_thi_nghiem.model.Chemistry;
import com.example.quan_ly_phong_thi_nghiem.model.Device;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class ReceiptDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @OneToMany(fetch = FetchType.EAGER)
    List<Chemistry> chemistry;
    @OneToMany(fetch = FetchType.EAGER)
    List<Device> devices;
    @ManyToOne
    Account account;
}
