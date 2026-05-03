package com.sieuthi.st_be.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sieuthi.st_be.dto.ChiNhanhDTO;
import com.sieuthi.st_be.entity.ChiNhanh;
import com.sieuthi.st_be.repository.ChiNhanhRepository;
import com.sieuthi.st_be.repository.KhoRepository;
import com.sieuthi.st_be.repository.NhanVienRepository;

@Service
@Transactional
public class ChiNhanhService {

    @Autowired
    private ChiNhanhRepository chiNhanhRepository;

    @Autowired
    private NhanVienRepository nhanVienRepository;

    @Autowired
    private KhoRepository khoRepository;

    // Chuyển đổi Entity -> DTO
    private ChiNhanhDTO convertToDTO(ChiNhanh cn) {
        ChiNhanhDTO dto = new ChiNhanhDTO();
        dto.setMaCN(cn.getMaCN());
        dto.setTenCN(cn.getTenCN());
        dto.setDiaChi(cn.getDiaChi());
        dto.setSoDT(cn.getSoDT());

        // Thống kê số lượng nhân viên
        long soLuongNV = nhanVienRepository.countByMaCN(cn.getMaCN());
        dto.setSoLuongNhanVien((int) soLuongNV);

        // Thống kê số lượng sản phẩm trong kho
        List<Object[]> allStats = khoRepository.countDistinctSanPhamByMaCN();
        int soLuongSP = 0;
        for (Object[] stat : allStats) {
            if (stat[0].equals(cn.getMaCN())) {
                Long count = (Long) stat[1];
                soLuongSP = count != null ? count.intValue() : 0;
                break;
            }
        }
        dto.setSoLuongSanPham(soLuongSP);
        return dto;
    }

    private ChiNhanhDTO convertToSimpleDTO(ChiNhanh cn) {
        ChiNhanhDTO dto = new ChiNhanhDTO();
        dto.setMaCN(cn.getMaCN());
        dto.setTenCN(cn.getTenCN());
        dto.setDiaChi(cn.getDiaChi());
        dto.setSoDT(cn.getSoDT());
        return dto;
    }

    // 1. Lấy tất cả chi nhánh (có thống kê)
    @Transactional(readOnly = true)
    public List<ChiNhanhDTO> getAll() {
        List<ChiNhanh> chiNhanhs = chiNhanhRepository.findAll();
        return chiNhanhs.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // 1b. Lấy tất cả chi nhánh (không thống kê - nhanh hơn)
    @Transactional(readOnly = true)
    public List<ChiNhanhDTO> getAllSimple() {
        List<ChiNhanh> chiNhanhs = chiNhanhRepository.findAll();
        return chiNhanhs.stream()
                .map(this::convertToSimpleDTO)
                .collect(Collectors.toList());
    }

    // 2. Lấy chi nhánh theo mã
    @Transactional(readOnly = true)
    public ChiNhanhDTO getById(String maCN) {
        if (maCN == null || maCN.trim().isEmpty()) {
            throw new RuntimeException("Mã chi nhánh không được để trống!");
        }
        ChiNhanh cn = chiNhanhRepository.findById(maCN)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy chi nhánh với mã: " + maCN));
        return convertToDTO(cn);
    }

    // 3. Tìm kiếm theo tên chi nhánh
    @Transactional(readOnly = true)
    public List<ChiNhanhDTO> searchByName(String tenCN) {
        if (tenCN == null || tenCN.trim().isEmpty()) {
            return getAll();
        }
        List<ChiNhanh> chiNhanhs = chiNhanhRepository.findByTenCNContainingIgnoreCase(tenCN);
        return chiNhanhs.stream()
                .map(this::convertToSimpleDTO)
                .collect(Collectors.toList());
    }

    // 4. Tìm theo số điện thoại
    @Transactional(readOnly = true)
    public ChiNhanhDTO getBySoDT(String soDT) {
        if (soDT == null || soDT.trim().isEmpty()) {
            throw new RuntimeException("Số điện thoại không được để trống!");
        }
        ChiNhanh cn = chiNhanhRepository.findBySoDT(soDT);
        if (cn == null) {
            throw new RuntimeException("Không tìm thấy chi nhánh với số điện thoại: " + soDT);
        }
        return convertToSimpleDTO(cn);
    }

    // 5. Tạo mới chi nhánh
    public ChiNhanhDTO create(ChiNhanh chiNhanh) {
        // Kiểm tra dữ liệu đầu vào
        if (chiNhanh.getMaCN() == null || chiNhanh.getMaCN().trim().isEmpty()) {
            throw new RuntimeException("Mã chi nhánh không được để trống!");
        }
        if (chiNhanh.getTenCN() == null || chiNhanh.getTenCN().trim().isEmpty()) {
            throw new RuntimeException("Tên chi nhánh không được để trống!");
        }
        if (chiNhanh.getDiaChi() == null || chiNhanh.getDiaChi().trim().isEmpty()) {
            throw new RuntimeException("Địa chỉ không được để trống!");
        }
        if (chiNhanh.getSoDT() == null || chiNhanh.getSoDT().trim().isEmpty()) {
            throw new RuntimeException("Số điện thoại không được để trống!");
        }

        // Kiểm tra mã đã tồn tại
        if (chiNhanhRepository.existsById(chiNhanh.getMaCN())) {
            throw new RuntimeException("Mã chi nhánh '" + chiNhanh.getMaCN() + "' đã tồn tại!");
        }

        // Kiểm tra số điện thoại đã tồn tại
        ChiNhanh existingByPhone = chiNhanhRepository.findBySoDT(chiNhanh.getSoDT());
        if (existingByPhone != null) {
            throw new RuntimeException("Số điện thoại '" + chiNhanh.getSoDT() + "' đã được đăng ký!");
        }

        ChiNhanh saved = chiNhanhRepository.save(chiNhanh);
        return convertToSimpleDTO(saved);
    }

    // 6. Cập nhật chi nhánh
    public ChiNhanhDTO update(String maCN, ChiNhanh chiNhanhMoi) {
        // Kiểm tra chi nhánh tồn tại
        ChiNhanh chiNhanhCu = chiNhanhRepository.findById(maCN)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy chi nhánh với mã: " + maCN));

        // Cập nhật thông tin
        if (chiNhanhMoi.getTenCN() != null && !chiNhanhMoi.getTenCN().trim().isEmpty()) {
            chiNhanhCu.setTenCN(chiNhanhMoi.getTenCN());
        }
        if (chiNhanhMoi.getDiaChi() != null && !chiNhanhMoi.getDiaChi().trim().isEmpty()) {
            chiNhanhCu.setDiaChi(chiNhanhMoi.getDiaChi());
        }
        if (chiNhanhMoi.getSoDT() != null && !chiNhanhMoi.getSoDT().trim().isEmpty()) {
            // Kiểm tra số điện thoại mới không trùng với chi nhánh khác
            if (chiNhanhRepository.existsBySoDTAndNotMaCN(chiNhanhMoi.getSoDT(), maCN)) {
                throw new RuntimeException("Số điện thoại '" + chiNhanhMoi.getSoDT() + "' đã được đăng ký!");
            }
            chiNhanhCu.setSoDT(chiNhanhMoi.getSoDT());
        }

        ChiNhanh updated = chiNhanhRepository.save(chiNhanhCu);
        return convertToSimpleDTO(updated);
    }

    // 7. Xóa chi nhánh
    public void delete(String maCN) {
        if (maCN == null || maCN.trim().isEmpty()) {
            throw new RuntimeException("Mã chi nhánh không được để trống!");
        }

        ChiNhanh chiNhanh = chiNhanhRepository.findById(maCN)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy chi nhánh với mã: " + maCN));

        // Kiểm tra có nhân viên không
        long soLuongNV = nhanVienRepository.countByMaCN(maCN);
        if (soLuongNV > 0) {
            throw new RuntimeException("Không thể xóa chi nhánh vì đang có " + soLuongNV + " nhân viên!");
        }
        chiNhanhRepository.delete(chiNhanh);
    }

    // 8. Thống kê tất cả chi nhánh (chi tiết)
    @Transactional(readOnly = true)
    public List<ChiNhanhDTO> getStatistics() {
        List<ChiNhanh> chiNhanhs = chiNhanhRepository.findAll();
        return chiNhanhs.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
}