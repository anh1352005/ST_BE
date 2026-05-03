package com.sieuthi.st_be.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CTHoaDonId implements Serializable {

    @Column(name = "MaHD", length = 50)
    private String maHD;

    @Column(name = "MaSP", length = 20)
    private String maSP;
}