package com.sieuthi.st_be.service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sieuthi.st_be.dto.NhanVienDTO;
import com.sieuthi.st_be.entity.NhanVien;
import com.sieuthi.st_be.repository.ChiNhanhRepository;
import com.sieuthi.st_be.repository.NhanVienRepository;

@Service
@Transactional
public class NhanVienService {
    @Autowired
    private NhanVienRepository nhanVienRepository;
    @Autowired
    private ChiNhanhRepository chiNhanhRepository;

    private NhanVienDTO convertToDTO(NhanVien nv) {
        NhanVienDTO dto = new NhanVienDTO();
        dto.setMaNV(nv.getMaNV());
        dto.setHoTen(nv.getHoTen());
        dto.setNgaySinh(nv.getNgaySinh());
        dto.setGioiTinh(nv.getGioiTinh());
        dto.setSoDT(nv.getSoDT());
        dto.setDiaChi(nv.getDiaChi());
        dto.setMaCN(nv.getMaCN());

        if (nv.getNgaySinh() != null) {
            dto.setTuoi(Period.between(nv.getNgaySinh(), LocalDate.now()).getYears());
        }
        if (nv.getChiNhanh() != null) {
            dto.setTenCN(nv.getChiNhanh().getTenCN());
        }
        return dto;
    }

    // Lay tat ca nv
    @Transactional(readOnly = true)
    public List<NhanVienDTO> getAll() {
        List<NhanVien> ls = nhanVienRepository.findAllWithChiNhanh();
        return ls.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    // layTheoma
    @Transactional(readOnly = true)
    public NhanVienDTO getById(String maNV) {
        NhanVien nv = nhanVienRepository.findById(maNV)
                .orElseThrow(() -> new RuntimeException("Khong tim thay nhan vien voi ma : " + maNV));
        return convertToDTO(nv);
    }

    // 3. Lấy nhân viên theo chi nhánh
    @Transactional(readOnly = true)
    public List<NhanVienDTO> getByChiNhanh(String maCN) {
        if (maCN == null || maCN.trim().isEmpty()) {
            throw new RuntimeException("Mã chi nhánh không được để trống!");
        }
        List<NhanVien> nhanViens = nhanVienRepository.findByMaCN(maCN);
        return nhanViens.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // 4. Tìm kiếm theo tên
    @Transactional(readOnly = true)
    public List<NhanVienDTO> searchByName(String hoTen) {
        if (hoTen == null || hoTen.trim().isEmpty()) {
            return getAll();
        }
        List<NhanVien> nhanViens = nhanVienRepository.findByHoTenContainingIgnoreCase(hoTen);
        return nhanViens.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // 5. Tìm theo giới tính
    @Transactional(readOnly = true)
    public List<NhanVienDTO> filterByGioiTinh(String gioiTinh) {
        if (gioiTinh == null || gioiTinh.trim().isEmpty()) {
            return getAll();
        }
        List<NhanVien> nhanViens = nhanVienRepository.findByGioiTinh(gioiTinh);
        return nhanViens.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // 6. Tìm theo khoảng tuổi (sinh từ năm X đến năm Y)
    @Transactional(readOnly = true)
    public List<NhanVienDTO> filterByAgeRange(Integer tuoiMin, Integer tuoiMax) {
        LocalDate now = LocalDate.now();
        LocalDate dateMax = tuoiMin != null ? now.minusYears(tuoiMin) : null;
        LocalDate dateMin = tuoiMax != null ? now.minusYears(tuoiMax + 1) : null;

        List<NhanVien> nhanViens;
        if (dateMin != null && dateMax != null) {
            nhanViens = nhanVienRepository.findByNgaySinhBetween(dateMin, dateMax);
        } else if (dateMax != null) {
            nhanViens = nhanVienRepository.findByNgaySinhBefore(dateMax);
        } else {
            nhanViens = nhanVienRepository.findAll();
        }

        return nhanViens.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // 7. Tạo mới nhân viên
    public NhanVienDTO create(NhanVien nhanVien) {
        // Kiểm tra dữ liệu đầu vào
        if (nhanVien.getMaNV() == null || nhanVien.getMaNV().trim().isEmpty()) {
            throw new RuntimeException("Mã nhân viên không được để trống!");
        }
        if (nhanVien.getHoTen() == null || nhanVien.getHoTen().trim().isEmpty()) {
            throw new RuntimeException("Họ tên không được để trống!");
        }
        if (nhanVien.getNgaySinh() == null) {
            throw new RuntimeException("Ngày sinh không được để trống!");
        }
        if (nhanVien.getGioiTinh() == null || nhanVien.getGioiTinh().trim().isEmpty()) {
            throw new RuntimeException("Giới tính không được để trống!");
        }
        if (nhanVien.getSoDT() == null || nhanVien.getSoDT().trim().isEmpty()) {
            throw new RuntimeException("Số điện thoại không được để trống!");
        }

        // Kiểm tra mã đã tồn tại
        if (nhanVienRepository.existsById(nhanVien.getMaNV())) {
            throw new RuntimeException("Mã nhân viên '" + nhanVien.getMaNV() + "' đã tồn tại!");
        }

        // Kiểm tra số điện thoại đã tồn tại
        NhanVien existingByPhone = nhanVienRepository.findBySoDT(nhanVien.getSoDT());
        if (existingByPhone != null) {
            throw new RuntimeException("Số điện thoại '" + nhanVien.getSoDT() + "' đã được đăng ký!");
        }

        // Kiểm tra chi nhánh tồn tại
        if (nhanVien.getMaCN() == null || nhanVien.getMaCN().trim().isEmpty()) {
            throw new RuntimeException("Mã chi nhánh không được để trống!");
        }
        if (!chiNhanhRepository.existsById(nhanVien.getMaCN())) {
            throw new RuntimeException("Chi nhánh với mã '" + nhanVien.getMaCN() + "' không tồn tại!");
        }
        nhanVien.setChiNhanh(
                chiNhanhRepository.findById(nhanVien.getMaCN())
                        .orElseThrow(() -> new RuntimeException("Không tìm thấy chi nhánh"))
        );

        NhanVien saved = nhanVienRepository.save(nhanVien);
        return convertToDTO(saved);
    }

    // 8. Cập nhật nhân viên
    public NhanVienDTO update(String maNV, NhanVien nhanVienMoi) {
        // Kiểm tra nhân viên tồn tại
        NhanVien nhanVienCu = nhanVienRepository.findById(maNV)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy nhân viên với mã: " + maNV));

        // Cập nhật thông tin
        if (nhanVienMoi.getHoTen() != null && !nhanVienMoi.getHoTen().trim().isEmpty()) {
            nhanVienCu.setHoTen(nhanVienMoi.getHoTen());
        }
        if (nhanVienMoi.getNgaySinh() != null) {
            nhanVienCu.setNgaySinh(nhanVienMoi.getNgaySinh());
        }
        if (nhanVienMoi.getGioiTinh() != null && !nhanVienMoi.getGioiTinh().trim().isEmpty()) {
            nhanVienCu.setGioiTinh(nhanVienMoi.getGioiTinh());
        }
        if (nhanVienMoi.getSoDT() != null && !nhanVienMoi.getSoDT().trim().isEmpty()) {
            // Kiểm tra số điện thoại mới không trùng với người khác
            NhanVien existingByPhone = nhanVienRepository.findBySoDT(nhanVienMoi.getSoDT());
            if (existingByPhone != null && !existingByPhone.getMaNV().equals(maNV)) {
                throw new RuntimeException("Số điện thoại '" + nhanVienMoi.getSoDT() + "' đã được đăng ký!");
            }
            nhanVienCu.setSoDT(nhanVienMoi.getSoDT());
        }
        if (nhanVienMoi.getDiaChi() != null && !nhanVienMoi.getDiaChi().trim().isEmpty()) {
            nhanVienCu.setDiaChi(nhanVienMoi.getDiaChi());
        }
        if (nhanVienMoi.getMaCN() != null && !nhanVienMoi.getMaCN().trim().isEmpty()) {
            if (!chiNhanhRepository.existsById(nhanVienMoi.getMaCN())) {
                throw new RuntimeException("Chi nhánh với mã '" + nhanVienMoi.getMaCN() + "' không tồn tại!");
            }
            nhanVienCu.setMaCN(nhanVienMoi.getMaCN());
        }

        NhanVien updated = nhanVienRepository.save(nhanVienCu);
        return convertToDTO(updated);
    }

    // 9. Xóa nhân viên
    public void delete(String maNV) {
        if (maNV == null || maNV.trim().isEmpty()) {
            throw new RuntimeException("Mã nhân viên không được để trống!");
        }

        NhanVien nhanVien = nhanVienRepository.findById(maNV)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy nhân viên với mã: " + maNV));
        // Nếu có thì không cho xóa
        nhanVienRepository.delete(nhanVien);
    }
}
