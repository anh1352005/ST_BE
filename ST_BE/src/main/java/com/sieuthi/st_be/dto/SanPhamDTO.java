package com.sieuthi.st_be.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SanPhamDTO {
    private String maSP;
    private String tenSP;
    private String donViTinh;
    private Integer giaBan;
    private String maLoai;
    private String tenLoai;
}
