import { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { userApi } from "../api";
import { useAppStore } from "../store";

export default function LoginPage() {
  const navigate = useNavigate();
  const { login } = useAppStore();
  const [form, setForm] = useState({ username: "", password: "" });
  const [message, setMessage] = useState("");

  const onSubmit = async (event) => {
    event.preventDefault();
    try {
      const { data } = await userApi.login(form);
      login(data);
      navigate("/foods");
    } catch (error) {
      setMessage(error.response?.data?.message || "Đăng nhập thất bại");
    }
  };

  return (
    <section className="card auth-card">
      <h2>Đăng nhập</h2>
      <form onSubmit={onSubmit} className="form">
        <input
          placeholder="Tên đăng nhập"
          value={form.username}
          onChange={(e) => setForm({ ...form, username: e.target.value })}
        />
        <input
          type="password"
          placeholder="Mật khẩu"
          value={form.password}
          onChange={(e) => setForm({ ...form, password: e.target.value })}
        />
        <button type="submit">Đăng nhập</button>
      </form>
      <p>
        Chưa có tài khoản? <Link to="/register">Đăng ký ngay</Link>
      </p>
      {message && <p className="message">{message}</p>}
    </section>
  );
}
