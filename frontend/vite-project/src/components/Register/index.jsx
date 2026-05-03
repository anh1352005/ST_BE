import { useState } from "react";
import "./Register.scss";

function Register() {
  const [form, setForm] = useState({
    username: "",
    password: "",
    role: "STAFF",
    maNV: "",
    trangThai: 1
  });

  const handleSubmit = async () => {
    try {
      const res = await fetch("http://localhost:8080/api/auth/register", {
        method: "POST",
        headers: {
          "Content-Type": "application/json"
        },
        body: JSON.stringify(form)
      });

      const data = await res.json();
      console.log(data);

      if (res.ok) {
        alert("Đăng ký thành công!");
      } else {
        alert(data.message || "Đăng ký thất bại!");
      }
    } catch (err) {
      console.error(err);
      alert("Lỗi kết nối server!");
    }
  };

  return (
    <div className="auth">
      <h2>Đăng ký</h2>

      <input
        placeholder="Username"
        onChange={(e) =>
          setForm({ ...form, username: e.target.value })
        }
      />

      <input
        type="password"
        placeholder="Password"
        onChange={(e) =>
          setForm({ ...form, password: e.target.value })
        }
      />

      <input
        placeholder="Mã nhân viên"
        onChange={(e) =>
          setForm({ ...form, maNV: e.target.value })
        }
      />

      {/* ROLE */}
      <select
        onChange={(e) =>
          setForm({ ...form, role: e.target.value })
        }
      >
        <option value="STAFF">Nhân viên</option>
        <option value="ADMIN">Admin</option>
      </select>

      <button onClick={handleSubmit}>Đăng ký</button>
    </div>
  );
}

export default Register;