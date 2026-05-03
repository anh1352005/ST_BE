package com.sieuthi.st_be.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.sieuthi.st_be.entity.TaiKhoan;
import com.sieuthi.st_be.repository.TaiKhoanRepository;

@Service
public class AuthService {

    @Autowired
    private TaiKhoanRepository taiKhoanRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public TaiKhoan login(String username, String password) {
        TaiKhoan tk = taiKhoanRepository.findByUsername(username)
            .orElseThrow(() -> new RuntimeException("Sai tài khoản"));

        // ✅ so sánh đúng với BCrypt
        if (!passwordEncoder.matches(password, tk.getPassword())) {
            throw new RuntimeException("Sai mật khẩu");
        }

        return tk;
    }

    public TaiKhoan register(TaiKhoan tk) {
        tk.setPassword(passwordEncoder.encode(tk.getPassword()));
        return taiKhoanRepository.save(tk);
    }
}