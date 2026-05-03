package com.sieuthi.st_be.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sieuthi.st_be.entity.LoaiHang;
import java.util.List;

@Repository
public interface LoaiHangRepository extends JpaRepository<LoaiHang, String> {
    List<LoaiHang> findByTenLoaiContainingIgnoreCase(String tenLoai);
}
