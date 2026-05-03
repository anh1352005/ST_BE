package com.sieuthi.st_be.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sieuthi.st_be.entity.TaiKhoan;

public interface TaiKhoanRepository extends JpaRepository<TaiKhoan, Integer> {
    Optional<TaiKhoan> findByUsername(String username);
}