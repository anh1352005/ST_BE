import {NavLink, Link} from 'react-router-dom';
import "./Header.scss";

function Header() {
  return (
    <>
      <div className="header">
        <div className="header-top">
          <div className="header-top__logo">
            <Link to="/" className={({ isActive }) => isActive ? "active" : ""}>
              <img src="" alt="logo" className='header-top__logo--img'/>
              <p className='header-top__logo--title'>Nhóm 4</p>
            </Link>
          </div>
          <div className="header__btn">
            <Link to="/login">
               <button className='header__btn--item'>Đăng nhập</button>
            </Link>
             <Link to="/register">
               <button className='header__btn--item'>Đăng ký</button>
            </Link>
          </div>
        </div>
        <div className="header-main">
          <NavLink to="/" className={({ isActive }) => isActive ? "active" : ""}>
            <div className="header-main__item">
              Tổng quan
            </div>
          </NavLink>
          <NavLink to="/branch" className={({ isActive }) => isActive ? "active" : ""}>
            <div className="header-main__item">
              Chi nhánh
            </div>
          </NavLink>
          <NavLink to="/employee" className={({ isActive }) => isActive ? "active" : ""}>
            <div className="header-main__item">
              Nhân viên
            </div>
          </NavLink>
          <NavLink to="/category" className={({ isActive }) => isActive ? "active" : ""}>
            <div className="header-main__item">
              Loại hàng
            </div>
          </NavLink>
          <NavLink to="/product" className={({ isActive }) => isActive ? "active" : ""}>
            <div className="header-main__item">
              Sản phẩm
            </div>
          </NavLink>
          <NavLink to="/stock" className={({ isActive }) => isActive ? "active" : ""}>
            <div className="header-main__item">
              Kho
            </div>
          </NavLink>
          <NavLink to="/bill" className={({ isActive }) => isActive ? "active" : ""}>
            <div className="header-main__item">
              Hoá đơn
            </div>
          </NavLink>
          <NavLink to="/statistical" className={({ isActive }) => isActive ? "active" : ""}>
            <div className="header-main__item">
              Thống kê
            </div>
          </NavLink>
        </div>
      </div>
    </>
  );
}

export default Header;