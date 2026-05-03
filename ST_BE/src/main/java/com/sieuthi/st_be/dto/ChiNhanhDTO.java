package com.sieuthi.st_be.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChiNhanhDTO {
    private String maCN;
    private String tenCN;
    private String diaChi;
    private String soDT;
    private Integer soLuongNhanVien;
    private Integer soLuongSanPham;
}