package com.sieuthi.st_be.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.sieuthi.st_be.dto.ChiNhanhDTO;
import com.sieuthi.st_be.entity.ChiNhanh;
import com.sieuthi.st_be.service.ChiNhanhService;

@RestController
@RequestMapping("/api/ChiNhanh")
@CrossOrigin(origins = "*")
public class ChiNhanhController {

    @Autowired
    private ChiNhanhService chiNhanhService;

    // 1. Lấy tất cả chi nhánh: GET /api/ChiNhanh
    @GetMapping
    public ResponseEntity<List<ChiNhanhDTO>> getAll() {
        return ResponseEntity.ok(chiNhanhService.getAll());
    }

    // 1b. Lấy tất cả chi nhánh (đơn giản, không thống kê): GET /api/ChiNhanh/simple
    @GetMapping("/simple")
    public ResponseEntity<List<ChiNhanhDTO>> getAllSimple() {
        return ResponseEntity.ok(chiNhanhService.getAllSimple());
    }

    // 2. Lấy chi nhánh theo mã: GET /api/ChiNhanh/CN001
    @GetMapping("/{maCN}")
    public ResponseEntity<ChiNhanhDTO> getById(@PathVariable String maCN) {
        return ResponseEntity.ok(chiNhanhService.getById(maCN));
    }

    // 3. Tìm kiếm theo tên: GET /api/ChiNhanh/search?ten=Quận
    @GetMapping("/search")
    public ResponseEntity<List<ChiNhanhDTO>> searchByName(@RequestParam(required = false) String ten) {
        return ResponseEntity.ok(chiNhanhService.searchByName(ten));
    }

    // 4. Tìm theo số điện thoại: GET /api/ChiNhanh/phone/0281234567
    @GetMapping("/phone/{soDT}")
    public ResponseEntity<ChiNhanhDTO> getByPhone(@PathVariable String soDT) {
        return ResponseEntity.ok(chiNhanhService.getBySoDT(soDT));
    }

    // Thừa ko dung đến nha dùng getAll()
    // 5. Thống kê chi nhánh: GET /api/ChiNhanh/stat
    @GetMapping("/stat")
    public ResponseEntity<List<ChiNhanhDTO>> getStatistics() {
        return ResponseEntity.ok(chiNhanhService.getStatistics());
    }

    // 6. Tạo mới chi nhánh: POST /api/ChiNhanh
    @PostMapping
    public ResponseEntity<ChiNhanhDTO> create(@RequestBody ChiNhanh chiNhanh) {
        return new ResponseEntity<>(chiNhanhService.create(chiNhanh), HttpStatus.CREATED);
    }

    // 7. Cập nhật chi nhánh: PUT /api/ChiNhanh/CN001
    @PutMapping("/{maCN}")
    public ResponseEntity<ChiNhanhDTO> update(@PathVariable String maCN, @RequestBody ChiNhanh chiNhanh) {
        return ResponseEntity.ok(chiNhanhService.update(maCN, chiNhanh));
    }

    // 8. Xóa chi nhánh: DELETE /api/ChiNhanh/CN001
    @DeleteMapping("/{maCN}")
    public ResponseEntity<Void> delete(@PathVariable String maCN) {
        chiNhanhService.delete(maCN);
        return ResponseEntity.noContent().build();
    }
}