package com.sieuthi.st_be.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sieuthi.st_be.dto.KhoDTO;
import com.sieuthi.st_be.entity.Kho;
import com.sieuthi.st_be.entity.KhoID;
import com.sieuthi.st_be.repository.ChiNhanhRepository;
import com.sieuthi.st_be.repository.KhoRepository;
import com.sieuthi.st_be.repository.SanPhamRepository;

@Service
@Transactional
public class KhoService {

    @Autowired
    private KhoRepository khoRepository;

    @Autowired
    private SanPhamRepository sanPhamRepository;

    @Autowired
    private ChiNhanhRepository chiNhanhRepository;

    // Chuyển đổi Entity -> DTO
    private KhoDTO convertToDTO(Kho kho) {
        KhoDTO dto = new KhoDTO();
        dto.setMaSP(kho.getId().getMaSP());
        dto.setMaCN(kho.getId().getMaCN());
        dto.setSoLuongTon(kho.getSoLuongTon());

        // Lấy thông tin sản phẩm
        if (kho.getSanPham() != null) {
            dto.setTenSP(kho.getSanPham().getTenSP());
            dto.setDonViTinh(kho.getSanPham().getDonViTinh());
            dto.setGiaBan(kho.getSanPham().getGiaBan());
            // Tính giá trị tồn kho
            if (kho.getSanPham().getGiaBan() != null && kho.getSoLuongTon() != null) {
                dto.setGiaTriTon((long) kho.getSanPham().getGiaBan() * kho.getSoLuongTon());
            }
        }

        // Lấy thông tin chi nhánh
        if (kho.getChiNhanh() != null) {
            dto.setTenCN(kho.getChiNhanh().getTenCN());
        }

        return dto;
    }

    // 1. Lấy tất cả tồn kho
    @Transactional(readOnly = true)
    public List<KhoDTO> getAll() {
        List<Kho> khoList = khoRepository.findAllWithDetails();
        return khoList.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // 2. Lấy tồn kho theo chi nhánh
    @Transactional(readOnly = true)
    public List<KhoDTO> getByChiNhanh(String maCN) {
        if (maCN == null || maCN.trim().isEmpty()) {
            throw new RuntimeException("Mã chi nhánh không được để trống!");
        }
        List<Kho> khoList = khoRepository.findByChiNhanhWithDetails(maCN);
        return khoList.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // 3. Lấy tồn kho theo sản phẩm
    @Transactional(readOnly = true)
    public List<KhoDTO> getBySanPham(String maSP) {
        if (maSP == null || maSP.trim().isEmpty()) {
            throw new RuntimeException("Mã sản phẩm không được để trống!");
        }
        List<Kho> khoList = khoRepository.findById_MaSP(maSP);
        return khoList.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // 4. Lấy tồn kho của 1 sản phẩm tại 1 chi nhánh
    @Transactional(readOnly = true)
    public KhoDTO getBySanPhamAndChiNhanh(String maSP, String maCN) {
        if (maSP == null || maSP.trim().isEmpty()) {
            throw new RuntimeException("Mã sản phẩm không được để trống!");
        }
        if (maCN == null || maCN.trim().isEmpty()) {
            throw new RuntimeException("Mã chi nhánh không được để trống!");
        }

        Kho kho = khoRepository.findById_MaSPAndId_MaCN(maSP, maCN);
        if (kho == null) {
            throw new RuntimeException(
                    "Không tìm thấy tồn kho của sản phẩm '" + maSP + "' tại chi nhánh '" + maCN + "'");
        }
        return convertToDTO(kho);
    }

    // 5. Nhập kho (tăng số lượng)
    public KhoDTO nhapKho(String maSP, String maCN, int soLuong) {
        // Kiểm tra dữ liệu đầu vào
        if (maSP == null || maSP.trim().isEmpty()) {
            throw new RuntimeException("Mã sản phẩm không được để trống!");
        }
        if (maCN == null || maCN.trim().isEmpty()) {
            throw new RuntimeException("Mã chi nhánh không được để trống!");
        }
        if (soLuong <= 0) {
            throw new RuntimeException("Số lượng nhập phải lớn hơn 0!");
        }

        // Kiểm tra sản phẩm tồn tại
        if (!sanPhamRepository.existsById(maSP)) {
            throw new RuntimeException("Sản phẩm với mã '" + maSP + "' không tồn tại!");
        }

        // Kiểm tra chi nhánh tồn tại
        if (!chiNhanhRepository.existsById(maCN)) {
            throw new RuntimeException("Chi nhánh với mã '" + maCN + "' không tồn tại!");
        }

        KhoID id = new KhoID(maSP, maCN);

        if (khoRepository.existsById(id)) {
            // Nếu đã có, cập nhật số lượng
            khoRepository.tangTonKho(maSP, maCN, soLuong);
        } else {
            // Nếu chưa có, tạo mới
            Kho kho = new Kho();
            kho.setId(id);
            kho.setSoLuongTon(soLuong);
            khoRepository.save(kho);
        }

        return getBySanPhamAndChiNhanh(maSP, maCN);
    }

    // 6. Xuất kho (giảm số lượng)
    public KhoDTO xuatKho(String maSP, String maCN, int soLuong) {
        // Kiểm tra dữ liệu đầu vào
        if (maSP == null || maSP.trim().isEmpty()) {
            throw new RuntimeException("Mã sản phẩm không được để trống!");
        }
        if (maCN == null || maCN.trim().isEmpty()) {
            throw new RuntimeException("Mã chi nhánh không được để trống!");
        }
        if (soLuong <= 0) {
            throw new RuntimeException("Số lượng xuất phải lớn hơn 0!");
        }

        // Kiểm tra tồn kho hiện tại
        Integer tonKho = khoRepository.getSoLuongTon(maSP, maCN);
        if (tonKho == null) {
            throw new RuntimeException("Sản phẩm '" + maSP + "' không có trong kho của chi nhánh '" + maCN + "'!");
        }

        if (tonKho < soLuong) {
            throw new RuntimeException("Tồn kho không đủ! (Tồn: " + tonKho + ", yêu cầu xuất: " + soLuong + ")");
        }

        int updated = khoRepository.giamTonKho(maSP, maCN, soLuong);
        if (updated == 0) {
            throw new RuntimeException("Xuất kho thất bại!");
        }

        return getBySanPhamAndChiNhanh(maSP, maCN);
    }

    // 7. Cập nhật trực tiếp số lượng tồn kho
    public KhoDTO update(String maSP, String maCN, Integer soLuongTon) {
        if (soLuongTon == null || soLuongTon < 0) {
            throw new RuntimeException("Số lượng tồn không hợp lệ!");
        }

        Kho kho = khoRepository.findById_MaSPAndId_MaCN(maSP, maCN);
        if (kho == null) {
            throw new RuntimeException("Không tìm thấy tồn kho!");
        }

        kho.setSoLuongTon(soLuongTon);
        Kho saved = khoRepository.save(kho);
        return convertToDTO(saved);
    }

    // 8. Xóa tồn kho
    public void delete(String maSP, String maCN) {
        Kho kho = khoRepository.findById_MaSPAndId_MaCN(maSP, maCN);
        if (kho == null) {
            throw new RuntimeException("Không tìm thấy tồn kho!");
        }
        khoRepository.delete(kho);
    }

    // 9. Cảnh báo tồn kho thấp
    @Transactional(readOnly = true)
    public List<KhoDTO> getLowStock(Integer threshold) {
        if (threshold == null) {
            threshold = 10; // Ngưỡng mặc định là 10
        }
        List<Kho> lowStockList = khoRepository.findBySoLuongTonLessThan(threshold);
        return lowStockList.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // 10. Thống kê giá trị tồn kho theo chi nhánh
    @Transactional(readOnly = true)
    public List<Object[]> getTonKhoStatistics() {
        return khoRepository.sumGiaTriTonByMaCN();
    }

    // 11. Đếm số sản phẩm theo chi nhánh
    @Transactional(readOnly = true)
    public List<Object[]> countSanPhamByChiNhanh() {
        return khoRepository.countDistinctSanPhamByMaCN();
    }
}