package com.sieuthi.st_be.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.sieuthi.st_be.entity.TaiKhoan;
import com.sieuthi.st_be.service.AuthService;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> req) {
        String username = req.get("username");
        String password = req.get("password");

        TaiKhoan tk = authService.login(username, password);

        // không trả password
        Map<String, Object> res = new HashMap<>();
        res.put("username", tk.getUsername());
        res.put("role", tk.getRole());
        res.put("maNV", tk.getMaNV());

        return ResponseEntity.ok(res);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody TaiKhoan tk) {
        TaiKhoan saved = authService.register(tk);
        return ResponseEntity.ok(saved);
    }
}