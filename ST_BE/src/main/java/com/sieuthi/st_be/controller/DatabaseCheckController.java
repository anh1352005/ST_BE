package com.sieuthi.st_be.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;

@RestController
@RequestMapping("/api/db")
public class DatabaseCheckController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping("/test-connection")
    public String testConnection() {
        try {
            // Thử query đơn giản để test connection
            String result = jdbcTemplate.queryForObject(
                    "SELECT 'Kết nối DB thành công!' as message",
                    String.class);
            return result;
        } catch (Exception e) {
            return "Lỗi kết nối DB: " + e.getMessage();
        }
    }

    @GetMapping("/tables")
    public Map<String, Object> listTables() {
        try {
            // Kiểm tra các bảng trong database
            String query = """
                        SELECT TABLE_NAME
                        FROM INFORMATION_SCHEMA.TABLES
                        WHERE TABLE_TYPE = 'BASE TABLE'
                        ORDER BY TABLE_NAME
                    """;
            return Map.of(
                    "status", "success",
                    "tables", jdbcTemplate.queryForList(query));
        } catch (Exception e) {
            return Map.of("status", "error", "message", e.getMessage());
        }
    }
}