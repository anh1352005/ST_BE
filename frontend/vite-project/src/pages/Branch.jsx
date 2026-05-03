import { NavLink, Outlet } from "react-router-dom";
import Header from "../components/Header";
import "../assets/style/Branch.scss";

function Branch() {
  return (
    <>
      <Header />
      <div className="branch-page">
        <div className="branch-page__sidebar">
          <ul className="menu">
            <NavLink
              to="list-branch"
              className={({ isActive }) => (isActive ? "active" : "")}
            >
              <li className="menu__item">Danh sach chi nhanh</li>
            </NavLink>
            <NavLink
              to="add-branch"
              className={({ isActive }) => (isActive ? "active" : "")}
            >
              <li className="menu__item">Them chi nhanh moi</li>
            </NavLink>
          </ul>
        </div>

        <div className="branch-page__content">
          <Outlet />
        </div>
      </div>
    </>
  );
}

export default Branch;
