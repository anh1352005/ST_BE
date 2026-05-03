package com.sieuthi.st_be.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HoaDonDTO {
    private String maHD;
    private String maCN;
    private String tenCN;
    private String maNV;
    private String tenNV;
    private LocalDate ngayLap;
    private Long tongTien;
    private Integer soLuongSanPham;
    private List<CTHoaDonDTO> chiTiet;
}