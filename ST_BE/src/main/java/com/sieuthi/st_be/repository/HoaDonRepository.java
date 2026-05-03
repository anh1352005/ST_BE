package com.sieuthi.st_be.repository;

import com.sieuthi.st_be.entity.HoaDon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface HoaDonRepository extends JpaRepository<HoaDon, String> {

    // Tìm theo chi nhánh
    List<HoaDon> findByMaCN(String maCN);

    // Tìm theo nhân viên
    List<HoaDon> findByMaNV(String maNV);

    // Tìm theo ngày lập
    List<HoaDon> findByNgayLap(LocalDate ngayLap);

    // Tìm theo khoảng ngày
    List<HoaDon> findByNgayLapBetween(LocalDate from, LocalDate to);

    // Tìm theo chi nhánh và khoảng ngày
    List<HoaDon> findByMaCNAndNgayLapBetween(String maCN, LocalDate from, LocalDate to);

    // Lấy kèm thông tin chi nhánh và nhân viên
    @Query("SELECT hd FROM HoaDon hd LEFT JOIN FETCH hd.chiNhanh LEFT JOIN FETCH hd.nhanVien")
    List<HoaDon> findAllWithDetails();

    // Lấy theo chi nhánh kèm details
    @Query("SELECT hd FROM HoaDon hd LEFT JOIN FETCH hd.chiNhanh LEFT JOIN FETCH hd.nhanVien WHERE hd.maCN = :maCN")
    List<HoaDon> findByMaCNWithDetails(@Param("maCN") String maCN);

    // Thống kê doanh thu theo ngày
    @Query("SELECT hd.ngayLap, SUM(ct.soLuong * ct.donGia) FROM HoaDon hd JOIN hd.chiTietHoaDons ct GROUP BY hd.ngayLap ORDER BY hd.ngayLap")
    List<Object[]> thongKeDoanhThuTheoNgay();

    // Thống kê doanh thu theo chi nhánh
    @Query("SELECT hd.maCN, SUM(ct.soLuong * ct.donGia) FROM HoaDon hd JOIN hd.chiTietHoaDons ct GROUP BY hd.maCN")
    List<Object[]> thongKeDoanhThuTheoChiNhanh();

    // Thống kê doanh thu theo tháng
    @Query("SELECT FUNCTION('YEAR', hd.ngayLap), FUNCTION('MONTH', hd.ngayLap), SUM(ct.soLuong * ct.donGia) " +
            "FROM HoaDon hd JOIN hd.chiTietHoaDons ct GROUP BY FUNCTION('YEAR', hd.ngayLap), FUNCTION('MONTH', hd.ngayLap)")
    List<Object[]> thongKeDoanhThuTheoThang();

    // Đếm số hóa đơn theo chi nhánh
    long countByMaCN(String maCN);
}