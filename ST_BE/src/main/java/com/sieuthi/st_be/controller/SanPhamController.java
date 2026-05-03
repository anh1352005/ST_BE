package com.sieuthi.st_be.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sieuthi.st_be.dto.SanPhamDTO;
import com.sieuthi.st_be.entity.SanPham;
import com.sieuthi.st_be.service.SanPhamService;

@RestController
@RequestMapping("/api/SanPham")
@CrossOrigin(origins = "*")
public class SanPhamController {

    @Autowired
    private SanPhamService sanPhamService;

    // get /api/SanPham
    @GetMapping
    public ResponseEntity<List<SanPhamDTO>> getAll() {
        return ResponseEntity.ok(sanPhamService.getAll());
    }

    // get /api/SanPham/SP001
    @GetMapping("/{maSP}")
    public ResponseEntity<SanPhamDTO> getByID(@PathVariable String maSP) {
        return ResponseEntity.ok(sanPhamService.getById(maSP));
    }

    // get /api/SanPham/type/L001
    @GetMapping("/type/{maLoai}")
    public ResponseEntity<List<SanPhamDTO>> searchByLoai(@PathVariable String maLoai) {
        return ResponseEntity.ok(sanPhamService.searchByLoai(maLoai));
    }

    // get /api/SanPham/search hoac search?tenSP={}
    // http://localhost:8080/api/SanPham/search?tenSP=Bánh
    @GetMapping("/search")
    public ResponseEntity<List<SanPhamDTO>> searchByName(@RequestParam(required = false) String tenSP) {
        return ResponseEntity.ok(sanPhamService.searchByName(tenSP));
    }

    // get /api/SanPham/price?min={}&max={}
    // http://localhost:8080/api/SanPham/price?min=10000&max=20000
    @GetMapping("/price")
    public ResponseEntity<List<SanPhamDTO>> searchByPrice(
            @RequestParam(required = false) Integer min,
            @RequestParam(required = false) Integer max) {
        return ResponseEntity.ok(sanPhamService.searchByGia(min, max));
    }

    // post /api/SanPham
    @PostMapping
    public ResponseEntity<SanPhamDTO> create(@RequestBody SanPham sp) {
        return new ResponseEntity<>(sanPhamService.create(sp), HttpStatus.CREATED);
    }

    // put /api/SanPham/SP001 + body json
    @PutMapping("/{maSP}")
    public ResponseEntity<SanPhamDTO> update(@PathVariable String maSP, @RequestBody SanPham sp) {
        return ResponseEntity.ok(sanPhamService.update(maSP, sp));
    }

    // delete /api/SanPham/SP001
    @DeleteMapping("/{maSP}")
    public ResponseEntity<Void> delete(@PathVariable String maSP) {
        sanPhamService.delete(maSP);
        return ResponseEntity.noContent().build();
    }
}