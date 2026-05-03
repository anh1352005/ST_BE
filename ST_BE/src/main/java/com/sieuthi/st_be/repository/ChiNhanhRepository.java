package com.sieuthi.st_be.repository;

import com.sieuthi.st_be.entity.ChiNhanh;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ChiNhanhRepository extends JpaRepository<ChiNhanh, String> {

    // Tìm theo tên chi nhánh (chứa, không phân biệt hoa thường)
    List<ChiNhanh> findByTenCNContainingIgnoreCase(String tenCN);

    // Tìm theo số điện thoại
    ChiNhanh findBySoDT(String soDT);

    // Tìm theo địa chỉ (chứa)
    List<ChiNhanh> findByDiaChiContainingIgnoreCase(String diaChi);

    // Kiểm tra số điện thoại đã tồn tại chưa (trừ chi nhánh hiện tại)
    @Query("SELECT COUNT(c) > 0 FROM ChiNhanh c WHERE c.soDT = :soDT AND c.maCN != :maCN")
    boolean existsBySoDTAndNotMaCN(@Param("soDT") String soDT, @Param("maCN") String maCN);
}