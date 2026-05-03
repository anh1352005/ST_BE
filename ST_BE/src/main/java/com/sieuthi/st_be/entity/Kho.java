package com.sieuthi.st_be.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Kho")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Kho {

    @EmbeddedId
    private KhoID id;

    @Column(name = "SoLuongTon")
    private Integer soLuongTon;

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("maSP")
    @JoinColumn(name = "MaSP", insertable = false, updatable = false)
    @JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
    SanPham sanPham;

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("maCN")
    @JoinColumn(name = "MaCN", insertable = false, updatable = false)
    @JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
    ChiNhanh chiNhanh;

}
