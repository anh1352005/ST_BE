package com.sieuthi.st_be.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sieuthi.st_be.entity.Kho;
import com.sieuthi.st_be.entity.KhoID;

@Repository
public interface KhoRepository extends JpaRepository<Kho, KhoID> {
    // theo CN
    List<Kho> findById_MaCN(String maCN);

    // theo sp
    List<Kho> findById_MaSP(String maSP);

    // theo sp va cn
    Kho findById_MaSPAndId_MaCN(String maSP, String maCN);

    // kiem tra kho
    @Query("select k.soLuongTon from Kho k where k.id.maSP = :maSP and k.id.maCN= :maCN")
    Integer getSoLuongTon(@Param("maSP") String maSP, @Param("maCN") String maCN);

    // Cap nhat ton kho (Tang)
    @Modifying
    @Transactional
    @Query("Update Kho k set k.soLuongTon=k.soLuongTon + :soLuong where k.id.maSP = :maSP and k.id.maCN = :maCN")
    int tangTonKho(@Param("maSP") String maSP, @Param("maCN") String maCN, @Param("soLuong") int soLuong);

    // giam
    @Modifying
    @Transactional
    @Query("Update Kho k set k.soLuongTon=k.soLuongTon - :soLuong where k.id.maSP = :maSP and k.id.maCN = :maCN and k.soLuongTon>=:soLuong")
    int giamTonKho(@Param("maSP") String maSP, @Param("maCN") String maCN, @Param("soLuong") int soLuong);

    // lay kem tt SP va CN
    @Query("Select k from Kho k left join fetch k.sanPham left join fetch k.chiNhanh")
    List<Kho> findAllWithDetails();

    // lay kem tt theo CN
    @Query("SELECT k FROM Kho k LEFT JOIN FETCH k.sanPham LEFT JOIN FETCH k.chiNhanh WHERE k.id.maCN = :maCN")
    List<Kho> findByChiNhanhWithDetails(@Param("maCN") String maCN);

    List<Kho> findBySoLuongTonLessThan(Integer threshold);

    // dem so sp co trong kho theo cn
    @Query("SELECT k.id.maCN, COUNT(DISTINCT k.id.maSP) FROM Kho k GROUP BY k.id.maCN")
    List<Object[]> countDistinctSanPhamByMaCN();

    // tong gia tri kho theo cn
    @Query("SELECT k.id.maCN, SUM(k.soLuongTon * sp.giaBan) FROM Kho k JOIN k.sanPham sp GROUP BY k.id.maCN")
    List<Object[]> sumGiaTriTonByMaCN();

    // Xóa tất cả tồn kho của một chi nhánh
    @Modifying
    @Transactional
    void deleteById_MaCN(String maCN);
}
