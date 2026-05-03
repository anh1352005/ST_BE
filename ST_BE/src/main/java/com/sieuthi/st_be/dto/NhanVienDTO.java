package com.sieuthi.st_be.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NhanVienDTO {
    private String maNV;
    private String hoTen;
    private LocalDate ngaySinh;
    private String gioiTinh;
    private String soDT;
    private String diaChi;
    private String maCN;
    private String tenCN;
    private Integer tuoi;
}
