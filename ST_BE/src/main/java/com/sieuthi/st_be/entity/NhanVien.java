package com.sieuthi.st_be.entity;

import java.time.LocalDate;

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
@Table(name = "NhanVien")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class NhanVien {
    @Id
    @Column(name = "MaNv", length = 10)
    private String maNV;
    @Column(name = "HoTen", length = 100)
    private String hoTen;
    @Column(name = "NgaySinh")
    private LocalDate ngaySinh;
    @Column(name = "GioiTinh", length = 10)
    private String gioiTinh;
    @Column(name = "SoDT", length = 15)
    private String soDT;
    @Column(name = "Diachi", length = 100)
    private String diaChi;
    @Column(name = "MaCN", length = 20)
    private String maCN;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "MaCN", insertable = false, updatable = false)
    @JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
    private ChiNhanh chiNhanh;
}
