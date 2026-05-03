package com.sieuthi.st_be.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ChiNhanh")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChiNhanh {
    @Id
    @Column(name = "MaCN", length = 20)
    private String maCN;
    @Column(name = "TenCN", length = 100)
    private String tenCN;
    @Column(name = "Diachi", length = 100)
    private String diaChi;
    @Column(name = "SoDT", length = 15)
    private String soDT;
}
