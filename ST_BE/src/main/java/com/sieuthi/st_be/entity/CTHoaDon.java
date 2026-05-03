package com.sieuthi.st_be.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "CT_HoaDon")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CTHoaDon {

    @EmbeddedId
    private CTHoaDonId id;

    @Column(name = "SoLuong")
    private Integer soLuong;

    @Column(name = "DonGia")
    private Integer donGia;

    // Relationship với HoaDon
    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("maHD")
    @JoinColumn(name = "MaHD", insertable = false, updatable = false)
    @JsonIgnoreProperties({ "hibernateLazyInitializer", "handler", "chiTietHoaDons" })
    private HoaDon hoaDon;

    // Relationship với SanPham
    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("maSP")
    @JoinColumn(name = "MaSP", insertable = false, updatable = false)
    @JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
    private SanPham sanPham;
}