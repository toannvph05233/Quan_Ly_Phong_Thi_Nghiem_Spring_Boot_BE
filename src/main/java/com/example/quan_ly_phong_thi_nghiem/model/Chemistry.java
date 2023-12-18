package com.example.quan_ly_phong_thi_nghiem.model;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Date;

@Entity
@Data
public class Chemistry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    @Column(name = "`describe`")
    private String describe;
    private int quantity;
    private Date createDate;
    private Date endDate;
    @ManyToOne
    private Subject subject;
}

