import { NavLink, Outlet } from "react-router-dom";
import "../assets/style/Employee.scss"; // Dùng chung style sidebar để đồng bộ giao diện
import Header from "../components/Header";

function Invoice() {
  return (
    <>
      <Header />
      <div className="employee"> {/* Giữ class 'employee' để nhận CSS sidebar của nhóm */}
        <div className="esidebar">
          <ul className="menu">
            <NavLink to='list-invoice' className={({ isActive }) => isActive ? "active" : ""}>
              <li className="menu__item">Danh sách hóa đơn</li>
            </NavLink>
            <NavLink to='add-invoice' className={({ isActive }) => isActive ? "active" : ""}>
              <li className="menu__item">Tạo hóa đơn mới</li>
            </NavLink>
          </ul>
        </div>
        <div className="employee__content">
          <Outlet /> {/* Đây là nơi nội dung chi tiết sẽ hiển thị */}
        </div>
      </div>
    </>
  );
}

export default Invoice;
