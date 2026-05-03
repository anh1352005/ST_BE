package com.sieuthi.st_be.service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sieuthi.st_be.dto.CTHoaDonDTO;
import com.sieuthi.st_be.dto.HoaDonDTO;
import com.sieuthi.st_be.entity.CTHoaDon;
import com.sieuthi.st_be.entity.CTHoaDonId;
import com.sieuthi.st_be.entity.HoaDon;
import com.sieuthi.st_be.entity.SanPham;
import com.sieuthi.st_be.repository.ChiNhanhRepository;
import com.sieuthi.st_be.repository.CTHoaDonRepository;
import com.sieuthi.st_be.repository.HoaDonRepository;
import com.sieuthi.st_be.repository.KhoRepository;
import com.sieuthi.st_be.repository.NhanVienRepository;
import com.sieuthi.st_be.repository.SanPhamRepository;

@Service
@Transactional
public class HoaDonService {

    @Autowired
    private HoaDonRepository hoaDonRepository;

    @Autowired
    private CTHoaDonRepository ctHoaDonRepository;

    @Autowired
    private SanPhamRepository sanPhamRepository;

    @Autowired
    private ChiNhanhRepository chiNhanhRepository;

    @Autowired
    private NhanVienRepository nhanVienRepository;

    @Autowired
    private KhoRepository khoRepository;

    // Chuyển đổi HoaDon -> DTO
    private HoaDonDTO convertToDTO(HoaDon hd) {
        HoaDonDTO dto = new HoaDonDTO();
        dto.setMaHD(hd.getMaHD());
        dto.setMaCN(hd.getMaCN());
        dto.setMaNV(hd.getMaNV());
        dto.setNgayLap(hd.getNgayLap());

        if (hd.getChiNhanh() != null) {
            dto.setTenCN(hd.getChiNhanh().getTenCN());
        }
        if (hd.getNhanVien() != null) {
            dto.setTenNV(hd.getNhanVien().getHoTen());
        }

        // Lấy chi tiết hóa đơn
        List<CTHoaDon> chiTiets = ctHoaDonRepository.findById_MaHDWithDetails(hd.getMaHD());
        if (chiTiets != null && !chiTiets.isEmpty()) {
            List<CTHoaDonDTO> chiTietDTOs = chiTiets.stream()
                    .map(this::convertToCTDTO)
                    .collect(Collectors.toList());
            dto.setChiTiet(chiTietDTOs);

            // Tính tổng tiền
            Long tongTien = chiTiets.stream()
                    .mapToLong(ct -> (long) ct.getSoLuong() * ct.getDonGia())
                    .sum();
            dto.setTongTien(tongTien);

            // Tính số lượng sản phẩm
            int soLuong = chiTiets.stream().mapToInt(CTHoaDon::getSoLuong).sum();
            dto.setSoLuongSanPham(soLuong);
        }

        return dto;
    }

    // Chuyển đổi CTHoaDon -> DTO
    private CTHoaDonDTO convertToCTDTO(CTHoaDon ct) {
        CTHoaDonDTO dto = new CTHoaDonDTO();
        dto.setMaHD(ct.getId().getMaHD());
        dto.setMaSP(ct.getId().getMaSP());
        dto.setSoLuong(ct.getSoLuong());
        dto.setDonGia(ct.getDonGia());
        dto.setThanhTien((long) ct.getSoLuong() * ct.getDonGia());

        if (ct.getSanPham() != null) {
            dto.setTenSP(ct.getSanPham().getTenSP());
            dto.setDonViTinh(ct.getSanPham().getDonViTinh());
        }

        return dto;
    }

    // 1. Lấy tất cả hóa đơn
    @Transactional(readOnly = true)
    public List<HoaDonDTO> getAll() {
        List<HoaDon> hoaDons = hoaDonRepository.findAllWithDetails();
        return hoaDons.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // 2. Lấy hóa đơn theo mã
    @Transactional(readOnly = true)
    public HoaDonDTO getById(String maHD) {
        HoaDon hd = hoaDonRepository.findById(maHD)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy hóa đơn với mã: " + maHD));
        return convertToDTO(hd);
    }

    // 3. Lấy hóa đơn theo chi nhánh
    @Transactional(readOnly = true)
    public List<HoaDonDTO> getByChiNhanh(String maCN) {
        if (maCN == null || maCN.trim().isEmpty()) {
            throw new RuntimeException("Mã chi nhánh không được để trống!");
        }
        List<HoaDon> hoaDons = hoaDonRepository.findByMaCNWithDetails(maCN);
        return hoaDons.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // 4. Lấy hóa đơn theo khoảng ngày
    @Transactional(readOnly = true)
    public List<HoaDonDTO> getByDateRange(LocalDate from, LocalDate to) {
        if (from == null || to == null) {
            throw new RuntimeException("Ngày bắt đầu và kết thúc không được để trống!");
        }
        List<HoaDon> hoaDons = hoaDonRepository.findByNgayLapBetween(from, to);
        return hoaDons.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // 5. Tạo hóa đơn mới (kèm chi tiết)
    @Transactional
    public HoaDonDTO create(HoaDon hoaDon, List<CTHoaDon> chiTiets) {
        // Kiểm tra dữ liệu đầu vào
        if (hoaDon.getMaHD() == null || hoaDon.getMaHD().trim().isEmpty()) {
            throw new RuntimeException("Mã hóa đơn không được để trống!");
        }
        if (hoaDon.getMaCN() == null || hoaDon.getMaCN().trim().isEmpty()) {
            throw new RuntimeException("Mã chi nhánh không được để trống!");
        }
        if (hoaDon.getMaNV() == null || hoaDon.getMaNV().trim().isEmpty()) {
            throw new RuntimeException("Mã nhân viên không được để trống!");
        }

        // Kiểm tra mã hóa đơn đã tồn tại
        if (hoaDonRepository.existsById(hoaDon.getMaHD())) {
            throw new RuntimeException("Mã hóa đơn '" + hoaDon.getMaHD() + "' đã tồn tại!");
        }

        // Kiểm tra chi nhánh tồn tại
        if (!chiNhanhRepository.existsById(hoaDon.getMaCN())) {
            throw new RuntimeException("Chi nhánh không tồn tại!");
        }

        // Kiểm tra nhân viên tồn tại
        if (!nhanVienRepository.existsById(hoaDon.getMaNV())) {
            throw new RuntimeException("Nhân viên không tồn tại!");
        }

        // Set ngày lập nếu chưa có
        if (hoaDon.getNgayLap() == null) {
            hoaDon.setNgayLap(LocalDate.now());
        }

        // Lưu hóa đơn
        @SuppressWarnings("unused")
        HoaDon savedHoaDon = hoaDonRepository.save(hoaDon);

        // Lưu chi tiết hóa đơn và cập nhật tồn kho
        if (chiTiets != null && !chiTiets.isEmpty()) {
            for (CTHoaDon ct : chiTiets) {

                ct.setHoaDon(hoaDon);

                // 🔥 lấy mã sản phẩm trước
                String maSP = ct.getId().getMaSP();

                // 🔥 LOAD entity SanPham
                SanPham sp = sanPhamRepository.findById(maSP)
                        .orElseThrow(() -> new RuntimeException("Sản phẩm không tồn tại: " + maSP));

                // 🔥 SET QUAN HỆ (QUAN TRỌNG)
                ct.setSanPham(sp);

                // 🔥 SET ID
                ct.setId(new CTHoaDonId(
                    hoaDon.getMaHD(),
                    maSP
                ));

                // giảm tồn kho
                int updated = khoRepository.giamTonKho(
                    ct.getId().getMaSP(),
                    hoaDon.getMaCN(),
                    ct.getSoLuong()
                );

                if (updated == 0) {
                    Integer tonKho = khoRepository.getSoLuongTon(
                        ct.getId().getMaSP(),
                        hoaDon.getMaCN()
                    );

                    throw new RuntimeException(
                        "Tồn kho không đủ! (Tồn: " +
                        (tonKho == null ? 0 : tonKho) +
                        ", cần: " + ct.getSoLuong() + ")"
                    );
                }

                ctHoaDonRepository.save(ct);
            }
                    }

        return getById(hoaDon.getMaHD());
    }

    // 6. Xóa hóa đơn
    @Transactional
    public void delete(String maHD) {
        HoaDon hoaDon = hoaDonRepository.findById(maHD)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy hóa đơn: " + maHD));
        // Lấy chi tiết hóa đơn để hoàn lại kho
        List<CTHoaDon> chiTiets = ctHoaDonRepository.findById_MaHD(maHD);
        for (CTHoaDon ct : chiTiets) {
            khoRepository.tangTonKho(ct.getId().getMaSP(), hoaDon.getMaCN(), ct.getSoLuong());
        }

        // Xóa chi tiết trước
        ctHoaDonRepository.deleteById_MaHD(maHD);
        // Xóa hóa đơn
        hoaDonRepository.delete(hoaDon);
    }

    // 7. Thống kê doanh thu theo ngày
    @Transactional(readOnly = true)
    public List<Object[]> thongKeDoanhThuTheoNgay() {
        return hoaDonRepository.thongKeDoanhThuTheoNgay();
    }

    // 8. Thống kê doanh thu theo chi nhánh
    @Transactional(readOnly = true)
    public List<Object[]> thongKeDoanhThuTheoChiNhanh() {
        return hoaDonRepository.thongKeDoanhThuTheoChiNhanh();
    }

    // 9. Thống kê doanh thu theo tháng
    @Transactional(readOnly = true)
    public List<Object[]> thongKeDoanhThuTheoThang() {
        return hoaDonRepository.thongKeDoanhThuTheoThang();
    }
}