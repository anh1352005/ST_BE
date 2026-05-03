package com.sieuthi.st_be.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sieuthi.st_be.dto.SanPhamDTO;
import com.sieuthi.st_be.entity.SanPham;
import com.sieuthi.st_be.repository.LoaiHangRepository;
import com.sieuthi.st_be.repository.SanPhamRepository;

@Service
@Transactional
public class SanPhamService {
    @Autowired
    private SanPhamRepository sanPhamRepository;

    @Autowired
    private LoaiHangRepository loaiHangRepository;

    private SanPhamDTO convertToDTO(SanPham sp) {
        SanPhamDTO dto = new SanPhamDTO();
        dto.setMaSP(sp.getMaSP());
        dto.setTenSP(sp.getTenSP());
        dto.setDonViTinh(sp.getDonViTinh());
        dto.setMaLoai(sp.getMaLoai());
        dto.setGiaBan(sp.getGiaBan());
        if (sp.getLoaiHang() != null) {
            dto.setTenLoai(sp.getLoaiHang().getTenLoai());
        }
        return dto;
    }

    // lay all
    @Transactional(readOnly = true)
    public List<SanPhamDTO> getAll() {
        List<SanPham> sp = sanPhamRepository.findAllWithLoaiHang();
        return sp.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    // lay theo ma
    @Transactional(readOnly = true)
    public SanPhamDTO getById(String maSP) {
        SanPham sp = sanPhamRepository.findById(maSP)
                .orElseThrow(() -> new RuntimeException("Khong tim thay san pham voi ma : " + maSP));
        return convertToDTO(sp);
    }

    public SanPhamDTO create(SanPham sp) {
        if (sp.getMaSP() == null || sp.getMaSP().trim().isEmpty()) {
            throw new RuntimeException("MaSP khong duoc de trong");
        }
        if (sp.getTenSP() == null || sp.getTenSP().trim().isEmpty()) {
            throw new RuntimeException("TenSP khong duoc de trong");
        }
        if (sp.getGiaBan() == null || sp.getGiaBan() < 0) {
            throw new RuntimeException("GiaBan khong hop le");
        }
        if (sanPhamRepository.existsById(sp.getMaSP())) {
            throw new RuntimeException("MaSP da ton tai");
        }
        if (sp.getMaLoai() == null || sp.getMaLoai().trim().isEmpty()) {
            throw new RuntimeException("MaLoai khong duoc de trong");
        } else if (!loaiHangRepository.existsById(sp.getMaLoai())) {
            throw new RuntimeException("LoaiHang khong ton tai");
        }
        // SanPham sp1= sanPhamRepository.save(sp);
        return convertToDTO(sanPhamRepository.save(sp));
    }

    public SanPhamDTO update(String maSp, SanPham spm) {
        SanPham spc = sanPhamRepository.findById(maSp)
                .orElseThrow(() -> new RuntimeException("Khong tim thay MaSp : " + maSp));

        if (spm.getTenSP() != null && !spm.getTenSP().trim().isEmpty()) {
            spc.setTenSP(spm.getTenSP());
        }
        if (spm.getDonViTinh() != null && !spm.getDonViTinh().trim().isEmpty()) {
            spc.setDonViTinh(spm.getDonViTinh());
        }
        if (spm.getGiaBan() != null && spm.getGiaBan() >= 0) {
            spc.setGiaBan(spm.getGiaBan());
        }
        if (spm.getMaLoai() != null && !spm.getMaLoai().trim().isEmpty()) {
            if (!loaiHangRepository.existsById(spm.getMaLoai())) {
                throw new RuntimeException("LoaiHang khong ton tai");
            }
            spc.setMaLoai(spm.getMaLoai());
        }
        return convertToDTO(sanPhamRepository.save(spc));
    }

    public void delete(String maSP) {
        if (maSP == null || maSP.trim().isEmpty()) {
            throw new RuntimeException("MaSP khong duoc de trong");
        }
        SanPham sp = sanPhamRepository.findById(maSP)
                .orElseThrow(() -> new RuntimeException("Khong tim thay san pham"));
        sanPhamRepository.delete(sp);
    }

    // tim theo ten
    @Transactional(readOnly = true)
    public List<SanPhamDTO> searchByName(String tenSP) {
        if (tenSP == null || tenSP.trim().isEmpty()) {
            return getAll();
        }
        List<SanPham> ls = sanPhamRepository.findByTenSPContainingIgnoreCase(tenSP);
        return ls.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    // tim theo loai hang
    @Transactional(readOnly = true)
    public List<SanPhamDTO> searchByLoai(String maLoai) {
        if (maLoai == null || maLoai.trim().isEmpty()) {
            return getAll();
        }
        List<SanPham> ls = sanPhamRepository.findByMaLoai(maLoai);
        return ls.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    // tim theo gia
    @Transactional(readOnly = true)
    public List<SanPhamDTO> searchByGia(Integer min, Integer max) {
        List<SanPham> ls;
        if (min != null && max != null) {
            ls = sanPhamRepository.findByGiaBanBetween(min, max);
        } else if (min != null) {
            ls = sanPhamRepository.findByGiaBanGreaterThanEqual(min);
        } else if (max != null) {
            ls = sanPhamRepository.findByGiaBanLessThanEqual(max);
        } else {
            ls = sanPhamRepository.findAll();
        }
        return ls.stream().map(this::convertToDTO).collect(Collectors.toList());
    }
}