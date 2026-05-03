package com.sieuthi.st_be.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CTHoaDonDTO {
    private String maHD;
    private String maSP;
    private String tenSP;
    private String donViTinh;
    private Integer soLuong;
    private Integer donGia;
    private Long thanhTien; // soLuong * donGia
}