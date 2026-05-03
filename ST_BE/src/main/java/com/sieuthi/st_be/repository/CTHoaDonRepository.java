package com.sieuthi.st_be.repository;

import com.sieuthi.st_be.entity.CTHoaDon;
import com.sieuthi.st_be.entity.CTHoaDonId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CTHoaDonRepository extends JpaRepository<CTHoaDon, CTHoaDonId> {

    // Tìm chi tiết theo mã hóa đơn
    List<CTHoaDon> findById_MaHD(String maHD);

    // Tìm chi tiết theo mã sản phẩm
    List<CTHoaDon> findById_MaSP(String maSP);

    // Lấy kèm thông tin sản phẩm
    @Query("SELECT ct FROM CTHoaDon ct LEFT JOIN FETCH ct.sanPham WHERE ct.id.maHD = :maHD")
    List<CTHoaDon> findById_MaHDWithDetails(@Param("maHD") String maHD);

    // Tính tổng tiền của hóa đơn
    @Query("SELECT SUM(ct.soLuong * ct.donGia) FROM CTHoaDon ct WHERE ct.id.maHD = :maHD")
    Long tongTienHoaDon(@Param("maHD") String maHD);

    // Xóa tất cả chi tiết của hóa đơn
    void deleteById_MaHD(String maHD);
}