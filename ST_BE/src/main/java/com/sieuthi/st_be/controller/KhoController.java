package com.sieuthi.st_be.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.sieuthi.st_be.dto.KhoDTO;
import com.sieuthi.st_be.service.KhoService;

@RestController
@RequestMapping("/api/Kho")
@CrossOrigin(origins = "*")
public class KhoController {

    @Autowired
    private KhoService khoService;

    // 1. Lấy tất cả tồn kho: GET /api/Kho
    @GetMapping
    public ResponseEntity<List<KhoDTO>> getAll() {
        return ResponseEntity.ok(khoService.getAll());
    }

    // 2. Lấy theo chi nhánh: GET /api/Kho/chinhanh/CN001
    @GetMapping("/chinhanh/{maCN}")
    public ResponseEntity<List<KhoDTO>> getByChiNhanh(@PathVariable String maCN) {
        return ResponseEntity.ok(khoService.getByChiNhanh(maCN));
    }

    // 3. Lấy theo sản phẩm: GET /api/Kho/sanpham/SP001
    @GetMapping("/sanpham/{maSP}")
    public ResponseEntity<List<KhoDTO>> getBySanPham(@PathVariable String maSP) {
        return ResponseEntity.ok(khoService.getBySanPham(maSP));
    }

    // 4. Lấy theo sản phẩm và chi nhánh: GET /api/Kho/SP001&CN001
    @GetMapping("/{maSP}&{maCN}")
    public ResponseEntity<KhoDTO> getBySanPhamAndChiNhanh(
            @PathVariable String maSP,
            @PathVariable String maCN) {
        return ResponseEntity.ok(khoService.getBySanPhamAndChiNhanh(maSP, maCN));
    }

    // 5. Nhập kho: POST /api/Kho/nhap?maSP=SP001&maCN=CN001&soLuong=10
    @PostMapping("/nhap")
    public ResponseEntity<KhoDTO> nhapKho(
            @RequestParam String maSP,
            @RequestParam String maCN,
            @RequestParam int soLuong) {
        return ResponseEntity.ok(khoService.nhapKho(maSP, maCN, soLuong));
    }

    // 6. Xuất kho: POST /api/Kho/xuat?maSP=SP001&maCN=CN001&soLuong=5
    @PostMapping("/xuat")
    public ResponseEntity<KhoDTO> xuatKho(
            @RequestParam String maSP,
            @RequestParam String maCN,
            @RequestParam int soLuong) {
        return ResponseEntity.ok(khoService.xuatKho(maSP, maCN, soLuong));
    }

    // 7. Cảnh báo tồn kho thấp: GET /api/Kho/lowstock?threshold=10
    @GetMapping("/lowstock")
    public ResponseEntity<List<KhoDTO>> getLowStock(@RequestParam(required = false) Integer threshold) {
        return ResponseEntity.ok(khoService.getLowStock(threshold));
    }

    // 8. Thống kê giá trị tồn kho: GET /api/Kho/stat
    @GetMapping("/stat")
    public ResponseEntity<List<Object[]>> getStatistics() {
        return ResponseEntity.ok(khoService.getTonKhoStatistics());
    }

    // 9. Thống kê số lượng sản phẩm theo chi nhánh: GET /api/Kho/countproducts
    @GetMapping("/countproducts")
    public ResponseEntity<List<Object[]>> countSanPhamByChiNhanh() {
        return ResponseEntity.ok(khoService.countSanPhamByChiNhanh());
    }

    // 10. Cập nhật tồn kho: PUT /api/Kho/SP001&CN001?soLuongTon=100
    @PutMapping("/{maSP}&{maCN}")
    public ResponseEntity<KhoDTO> update(
            @PathVariable String maSP,
            @PathVariable String maCN,
            @RequestParam Integer soLuongTon) {
        return ResponseEntity.ok(khoService.update(maSP, maCN, soLuongTon));
    }

    // 11. Xóa tồn kho: DELETE /api/Kho/SP001&CN001
    @DeleteMapping("/{maSP}&{maCN}")
    public ResponseEntity<Void> delete(@PathVariable String maSP, @PathVariable String maCN) {
        khoService.delete(maSP, maCN);
        return ResponseEntity.noContent().build();
    }
}