package com.sieuthi.st_be.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class KhoDTO {
    private String maSP;
    private String maCN;
    private String donViTinh;
    private Integer giaBan;
    private String tenSP;
    private String tenCN;
    private Integer soLuongTon;
    private Long giaTriTon;
}
