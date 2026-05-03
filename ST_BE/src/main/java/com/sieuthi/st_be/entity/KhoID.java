package com.sieuthi.st_be.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class KhoID implements Serializable {
    @Column(name = "MaSP", length = 20)
    private String maSP;
    @Column(name = "MaCN", length = 20)
    private String maCN;
}
