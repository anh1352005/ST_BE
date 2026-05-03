import { useState } from "react";
import { useNavigate } from "react-router-dom";
import "./Login.scss";

function Login() {
  const [form, setForm] = useState({
    username: "",
    password: ""
  });

  const navigate = useNavigate();

  const handleSubmit = async () => {
    try {
      const res = await fetch("http://localhost:8080/api/auth/login", {
        method: "POST",
        headers: {
          "Content-Type": "application/json"
        },
        body: JSON.stringify(form)
      });

      if (!res.ok) {
        throw new Error();
      }

      const data = await res.json();

      // ✅ LƯU USER
      localStorage.setItem("user", JSON.stringify(data));

      alert("Đăng nhập thành công!");

      // ✅ CHUYỂN TRANG
      navigate("/");

    } catch (err) {
      alert("Sai tài khoản hoặc mật khẩu!");
    }
  };

  return (
    <div className="auth">
      <h2>Đăng nhập</h2>

      <input
        placeholder="Username"
        onChange={(e) => setForm({...form, username: e.target.value})}
      />

      <input
        type="password"
        placeholder="Password"
        onChange={(e) => setForm({...form, password: e.target.value})}
      />

      <button onClick={handleSubmit}>Đăng nhập</button>
    </div>
  );
}

export default Login;