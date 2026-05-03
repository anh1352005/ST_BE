package com.sieuthi.st_be.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.time.LocalDate;
import java.util.List;
import com.sieuthi.st_be.entity.CTHoaDonId;

@Entity
@Table(name = "HoaDon")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HoaDon {

    @Id
    @Column(name = "MaHD", length = 50)
    private String maHD;

    @Column(name = "MaCN", length = 20)
    private String maCN;

    @Column(name = "MaNV", length = 10)
    private String maNV;

    @Column(name = "NgayLap")
    private LocalDate ngayLap;

    // Relationship với ChiNhanh
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "MaCN", insertable = false, updatable = false)
    @JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
    private ChiNhanh chiNhanh;

    // Relationship với NhanVien
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "MaNV", insertable = false, updatable = false)
    @JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
    private NhanVien nhanVien;

    // Relationship với CT_HoaDon (1-n)
    @OneToMany(mappedBy = "hoaDon", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<CTHoaDon> chiTietHoaDons;
}