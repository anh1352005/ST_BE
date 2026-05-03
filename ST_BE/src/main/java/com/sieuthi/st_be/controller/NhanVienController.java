package com.sieuthi.st_be.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.sieuthi.st_be.dto.NhanVienDTO;
import com.sieuthi.st_be.entity.NhanVien;
import com.sieuthi.st_be.service.NhanVienService;

@RestController
@RequestMapping("/api/NhanVien")
@CrossOrigin(origins = "*")
public class NhanVienController {

    @Autowired
    private NhanVienService nhanVienService;

    // 1. Lấy tất cả: GET /api/NhanVien
    @GetMapping
    public ResponseEntity<List<NhanVienDTO>> getAll() {
        return ResponseEntity.ok(nhanVienService.getAll());
    }

    // 2. Lấy theo mã: GET /api/NhanVien/NV001
    @GetMapping("/{maNV}")
    public ResponseEntity<NhanVienDTO> getById(@PathVariable String maNV) {
        return ResponseEntity.ok(nhanVienService.getById(maNV));
    }

    // 3. Lấy theo chi nhánh: GET /api/NhanVien/chinhanh/CN001
    @GetMapping("/chinhanh/{maCN}")
    public ResponseEntity<List<NhanVienDTO>> getByChiNhanh(@PathVariable String maCN) {
        return ResponseEntity.ok(nhanVienService.getByChiNhanh(maCN));
    }

    // 4. Tìm kiếm theo tên: GET /api/NhanVien/search?ten=Nguyễn
    @GetMapping("/search")
    public ResponseEntity<List<NhanVienDTO>> searchByName(@RequestParam(required = false) String ten) {
        return ResponseEntity.ok(nhanVienService.searchByName(ten));
    }

    // 5. Lọc theo giới tính: GET /api/NhanVien/gioitinh/Nam
    @GetMapping("/gioitinh/{gioiTinh}")
    public ResponseEntity<List<NhanVienDTO>> filterByGioiTinh(@PathVariable String gioiTinh) {
        return ResponseEntity.ok(nhanVienService.filterByGioiTinh(gioiTinh));
    }

    // 6. Lọc theo độ tuổi: GET /api/NhanVien/age?min=20&max=30
    @GetMapping("/age")
    public ResponseEntity<List<NhanVienDTO>> filterByAge(
            @RequestParam(required = false) Integer min,
            @RequestParam(required = false) Integer max) {
        return ResponseEntity.ok(nhanVienService.filterByAgeRange(min, max));
    }

    // 7. Tạo mới: POST /api/NhanVien
    @PostMapping
    public ResponseEntity<NhanVienDTO> create(@RequestBody NhanVien nhanVien) {
        return new ResponseEntity<>(nhanVienService.create(nhanVien), HttpStatus.CREATED);
    }

    // 8. Cập nhật: PUT /api/NhanVien/NV001
    @PutMapping("/{maNV}")
    public ResponseEntity<NhanVienDTO> update(@PathVariable String maNV, @RequestBody NhanVien nhanVien) {
        return ResponseEntity.ok(nhanVienService.update(maNV, nhanVien));
    }

    // 9. Xóa: DELETE /api/NhanVien/NV001
    @DeleteMapping("/{maNV}")
    public ResponseEntity<Void> delete(@PathVariable String maNV) {
        nhanVienService.delete(maNV);
        return ResponseEntity.noContent().build();
    }
}