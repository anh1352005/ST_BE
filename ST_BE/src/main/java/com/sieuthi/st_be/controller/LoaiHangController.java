package com.sieuthi.st_be.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sieuthi.st_be.entity.LoaiHang;
import com.sieuthi.st_be.service.LoaiHangService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/LoaiHang")
@CrossOrigin(origins = "*")
public class LoaiHangController {
    @Autowired
    private LoaiHangService loaiHangService;

    @GetMapping()
    public ResponseEntity<List<LoaiHang>> getAll() {
        List<LoaiHang> ls = loaiHangService.getAll();
        return ResponseEntity.ok(ls);
    }

    @GetMapping("/{MaLoai}")
    public ResponseEntity<LoaiHang> getById(@PathVariable String MaLoai) {
        LoaiHang lh = loaiHangService.getById(MaLoai);
        return ResponseEntity.ok(lh);
    }

    // search?tenloai={}
    @GetMapping("/search")
    public ResponseEntity<List<LoaiHang>> search(@RequestParam(required = false) String tenloai) {
        List<LoaiHang> ls = loaiHangService.search(tenloai);
        return ResponseEntity.ok(ls);
    }

    @PostMapping
    public ResponseEntity<LoaiHang> create(@RequestBody LoaiHang loaiHang) {
        LoaiHang lh = loaiHangService.create(loaiHang);
        return new ResponseEntity<>(lh, HttpStatus.CREATED);
    }

    @PutMapping("/{MaLoai}")
    public ResponseEntity<LoaiHang> update(
            @PathVariable String MaLoai,
            @RequestBody LoaiHang loaiHang) {
        LoaiHang lh = loaiHangService.update(MaLoai, loaiHang);
        return ResponseEntity.ok(lh);
    }

    @DeleteMapping("/{MaLoai}")
    public ResponseEntity<Void> delete(@PathVariable String MaLoai) {
        loaiHangService.delete(MaLoai);
        return ResponseEntity.noContent().build();
    }
}
