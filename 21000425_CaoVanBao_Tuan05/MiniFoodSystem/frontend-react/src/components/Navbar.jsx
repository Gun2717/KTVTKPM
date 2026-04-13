import { Link, NavLink, useNavigate } from "react-router-dom";
import { useAppStore } from "../store";

export default function Navbar() {
  const { currentUser, cart, logout } = useAppStore();
  const navigate = useNavigate();

  const onLogout = () => {
    logout();
    navigate("/login");
  };

  return (
    <header className="topbar">
      <Link to="/" className="brand">
        MiniFood
      </Link>
      <nav className="nav-links">
        {currentUser ? (
          <>
            <NavLink to="/foods">Món ăn</NavLink>
            <NavLink to="/cart">Giỏ hàng ({cart.length})</NavLink>
            <NavLink to="/orders">Đơn hàng</NavLink>
            <NavLink to="/payment">Thanh toán</NavLink>
          </>
        ) : (
          <>
            <NavLink to="/login">Đăng nhập</NavLink>
            <NavLink to="/register">Đăng ký</NavLink>
          </>
        )}
      </nav>
      <div className="user-area">
        {currentUser ? (
          <>
            <span>{currentUser.username}</span>
            <button onClick={onLogout}>Đăng xuất</button>
          </>
        ) : (
          <span>Khách</span>
        )}
      </div>
    </header>
  );
}
