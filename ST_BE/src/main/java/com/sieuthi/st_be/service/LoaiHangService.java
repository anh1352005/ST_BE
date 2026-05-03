package com.sieuthi.st_be.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sieuthi.st_be.entity.LoaiHang;
import com.sieuthi.st_be.repository.LoaiHangRepository;

@Service // Đánh dấu đây là tầng Service
@Transactional // Quản lý transaction - tự động commit/rollback khi có lỗi
public class LoaiHangService {

    @Autowired
    private LoaiHangRepository loaiHangRepository;

    // Lay all
    @Transactional(readOnly = true)
    public List<LoaiHang> getAll() {
        return loaiHangRepository.findAll();
    }

    // lay theo ma
    @Transactional(readOnly = true)
    public LoaiHang getById(String maLoai) {
        if (maLoai == null || maLoai.trim().isEmpty()) {
            throw new RuntimeException("MaLoai khong duoc de trong");
        }
        return loaiHangRepository.findById(maLoai)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy MaLoai : " + maLoai));
    }

    // Tao moi
    public LoaiHang create(LoaiHang loaiHang) {
        if (loaiHang.getMaLoai() == null || loaiHang.getMaLoai().trim().isEmpty()) {
            throw new RuntimeException("MaLoai khong duoc de trong");
        }
        if (loaiHang.getTenLoai() == null || loaiHang.getTenLoai().trim().isEmpty()) {
            throw new RuntimeException("TenLoai khong duoc de trong");
        }
        if (loaiHangRepository.existsById(loaiHang.getMaLoai())) {
            throw new RuntimeException("MaLoai : " + loaiHang.getMaLoai() + "da ton tai");
        }

        return loaiHangRepository.save(loaiHang);
    }

    // update
    public LoaiHang update(String maloai, LoaiHang loaiHang) {
        LoaiHang loaiHang2 = getById(maloai);
        loaiHang2.setTenLoai(loaiHang.getTenLoai());
        return loaiHangRepository.save(loaiHang2);

    }

    public void delete(String maloai) {
        if (maloai == null || maloai.trim().isEmpty())
            throw new RuntimeException("MaLoai khong duoc de trong");
        LoaiHang lHang = getById(maloai);
        loaiHangRepository.delete(lHang);
    }

    @Transactional(readOnly = true)
    public List<LoaiHang> search(String tenloai) {
        if (tenloai == null || tenloai.trim().isEmpty()) {
            return getAll();
        }
        return loaiHangRepository.findByTenLoaiContainingIgnoreCase(tenloai);

    }
}
