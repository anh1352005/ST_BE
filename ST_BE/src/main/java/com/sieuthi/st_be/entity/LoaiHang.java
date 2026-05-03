package com.sieuthi.st_be.entity;

import lombok.*;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "LoaiHang")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoaiHang {
    @Id
    @Column(name = "MaLoai", length = 20)
    private String maLoai;

    @Column(name = "TenLoai", length = 100)
    private String tenLoai;
}
