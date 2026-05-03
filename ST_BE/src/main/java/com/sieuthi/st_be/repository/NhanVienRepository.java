package com.sieuthi.st_be.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.sieuthi.st_be.entity.NhanVien;
import java.time.LocalDate;;;

@Repository
public interface NhanVienRepository extends JpaRepository<NhanVien, String> {

    List<NhanVien> findByHoTenContainingIgnoreCase(String hoTen);

    List<NhanVien> findByMaCN(String maCN);

    List<NhanVien> findByGioiTinh(String gioiTinh);

    NhanVien findBySoDT(String soDT);

    @Query("SELECT nv FROM NhanVien nv LEFT JOIN FETCH nv.chiNhanh")
    List<NhanVien> findAllWithChiNhanh();

    long countByMaCN(String maCN);

    List<NhanVien> findByNgaySinhBetween(LocalDate a, LocalDate b);

    List<NhanVien> findByNgaySinhBefore(LocalDate ngaySinh);

    List<NhanVien> findByMaCNAndGioiTinh(String maCN, String gioiTinh);

}
