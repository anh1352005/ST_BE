package com.sieuthi.st_be.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.sieuthi.st_be.entity.SanPham;

@Repository
public interface SanPhamRepository extends JpaRepository<SanPham, String> {
    List<SanPham> findByTenSPContainingIgnoreCase(String tenSP);

    List<SanPham> findByMaLoai(String maLoai);

    List<SanPham> findByGiaBanGreaterThanEqual(Integer giaBan);

    List<SanPham> findByGiaBanLessThanEqual(Integer giaBan);

    List<SanPham> findByGiaBanBetween(Integer min, Integer max);

    @Query("Select sp from SanPham sp Left join fetch sp.loaiHang")
    List<SanPham> findAllWithLoaiHang();

    long countByMaLoai(String maLoai);

}