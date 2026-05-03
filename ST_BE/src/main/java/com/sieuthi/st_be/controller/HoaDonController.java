package com.sieuthi.st_be.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.sieuthi.st_be.dto.HoaDonDTO;
import com.sieuthi.st_be.entity.CTHoaDon;
import com.sieuthi.st_be.entity.HoaDon;
import com.sieuthi.st_be.service.HoaDonService;

@RestController
@RequestMapping("/api/HoaDon")
@CrossOrigin(origins = "*")
public class HoaDonController {

    @Autowired
    private HoaDonService hoaDonService;

    // 1. Lấy tất cả: GET /api/HoaDon
    @GetMapping
    public ResponseEntity<List<HoaDonDTO>> getAll() {
        return ResponseEntity.ok(hoaDonService.getAll());
    }

    // 2. Lấy theo mã: GET /api/HoaDon/HD001
    @GetMapping("/{maHD}")
    public ResponseEntity<HoaDonDTO> getById(@PathVariable String maHD) {
        return ResponseEntity.ok(hoaDonService.getById(maHD));
    }

    // 3. Lấy theo chi nhánh: GET /api/HoaDon/chinhanh/CN001
    @GetMapping("/chinhanh/{maCN}")
    public ResponseEntity<List<HoaDonDTO>> getByChiNhanh(@PathVariable String maCN) {
        return ResponseEntity.ok(hoaDonService.getByChiNhanh(maCN));
    }

    // 4. Lấy theo khoảng ngày: GET
    // /api/HoaDon/date?from=2025-04-01&to=2025-04-30
    @GetMapping("/date")
    public ResponseEntity<List<HoaDonDTO>> getByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to) {
        return ResponseEntity.ok(hoaDonService.getByDateRange(from, to));
    }

    // 5. Tạo hóa đơn mới: POST /api/HoaDon
    @PostMapping
    public ResponseEntity<HoaDonDTO> create(@RequestBody Map<String, Object> request) {
        // Parse request body
        HoaDon hoaDon = new HoaDon();
        hoaDon.setMaHD((String) request.get("maHD"));
        hoaDon.setMaCN((String) request.get("maCN"));
        hoaDon.setMaNV((String) request.get("maNV"));

        String ngayLap = (String) request.get("ngayLap");
        if (ngayLap != null) {
            hoaDon.setNgayLap(LocalDate.parse(ngayLap));
        }

        // Parse chi tiết hóa đơn
        @SuppressWarnings("unchecked")
        List<Map<String, Object>> chiTietList = (List<Map<String, Object>>) request.get("chiTiet");
        List<CTHoaDon> chiTiets = chiTietList.stream().map(ct -> {
            CTHoaDon cthd = new CTHoaDon();
            cthd.setId(new com.sieuthi.st_be.entity.CTHoaDonId(hoaDon.getMaHD(), (String) ct.get("maSP")));
            cthd.setSoLuong((Integer) ct.get("soLuong"));
            cthd.setDonGia((Integer) ct.get("donGia"));
            return cthd;
        }).collect(java.util.stream.Collectors.toList());

        return new ResponseEntity<>(hoaDonService.create(hoaDon, chiTiets), HttpStatus.CREATED);
    }

    // 6. Xóa hóa đơn: DELETE /api/HoaDon/HD001
    @DeleteMapping("/{maHD}")
    public ResponseEntity<Void> delete(@PathVariable String maHD) {
        hoaDonService.delete(maHD);
        return ResponseEntity.noContent().build();
    }

    // 7. Thống kê doanh thu theo ngày: GET /api/HoaDon/stat/day
    @GetMapping("/stat/day")
    public ResponseEntity<List<Object[]>> thongKeTheoNgay() {
        return ResponseEntity.ok(hoaDonService.thongKeDoanhThuTheoNgay());
    }

    // 8. Thống kê doanh thu theo chi nhánh: GET /api/HoaDon/stat/chinhanh
    @GetMapping("/stat/chinhanh")
    public ResponseEntity<List<Object[]>> thongKeTheoChiNhanh() {
        return ResponseEntity.ok(hoaDonService.thongKeDoanhThuTheoChiNhanh());
    }

    // 9. Thống kê doanh thu theo tháng: GET /api/HoaDon/stat/month
    @GetMapping("/stat/month")
    public ResponseEntity<List<Object[]>> thongKeTheoThang() {
        return ResponseEntity.ok(hoaDonService.thongKeDoanhThuTheoThang());
    }
}