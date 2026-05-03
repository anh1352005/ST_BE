import { NavLink, Outlet } from "react-router-dom";
import "../assets/style/Employee.scss";
import Header from "../components/Header";

function Employee() {
  return (
    <>
    <Header></Header>
      <div className="employee">
        <div className="esidebar">
          <ul className="menu">
            <NavLink to='list-employee' className={({ isActive }) => isActive ? "active" : ""}>
              <li className="menu__item">Danh sách nhân viên</li>
            </NavLink>
            <NavLink to='deleted-employee' className={({ isActive }) => isActive ? "active" : ""}>
              <li className="menu__item">Nhân viên đã nghỉ</li>
            </NavLink>
            <NavLink to='add-employee' className={({ isActive }) => isActive ? "active" : ""}>
              <li className="menu__item">Thêm nhân viên mới</li>
            </NavLink>
          </ul>
        </div>
        <div className="employee__content">
          <Outlet></Outlet>
        </div>
      </div>
    </>
  );
}

export default Employee;