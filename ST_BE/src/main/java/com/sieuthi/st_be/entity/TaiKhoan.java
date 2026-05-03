package com.sieuthi.st_be.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "TaiKhoan")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaiKhoan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Long id;

    @Column(name = "Username", unique = true, nullable = false, length = 50)
    private String username;

    @Column(name = "Password", nullable = false, length = 255)
    private String password;

    @Column(name = "Role", length = 20)
    private String role; // ADMIN / STAFF

    @Column(name = "MaNV", length = 10)
    private String maNV;

    // 👉 NÊN CÓ (để khóa tài khoản nếu cần)
    @Column(name = "trangThai")
    private Integer status; // 1 = active, 0 = khóa
}