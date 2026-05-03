package com.sieuthi.st_be.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "SanPham")
@Data
@AllArgsConstructor
@NoArgsConstructor

public class SanPham {
    @Id
    @Column(name = "MaSP", length = 20)
    private String maSP;
    @Column(name = "TenSp", length = 50)
    private String tenSP;
    @Column(name = "Donvitinh", length = 20)
    private String donViTinh;
    @Column(name = "GiaBan")
    private Integer giaBan;
    @Column(name = "MaLoai", length = 20)
    private String maLoai;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "MaLoai", insertable = false, updatable = false)
    @JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
    private LoaiHang loaiHang;

}