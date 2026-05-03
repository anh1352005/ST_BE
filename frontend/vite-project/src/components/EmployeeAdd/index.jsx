import { useNavigate } from "react-router-dom";
import { useState } from "react";
import GoBack from "../GoBack/index";
import "./EmployeeAdd.scss";

function EmployeeAdd() {
  const [employee, setEmployee] = useState({
    maNV: "",
    hoTen: "",
    ngaySinh: "",
    gioiTinh: "",
    soDT: "",
    diachi: "",
    maCN: ""
  });
  const navigate = useNavigate()


  const handleChange = (e) => {
    const { name, value } = e.target;
    setEmployee(prev => ({
      ...prev,
      [name]: value
    }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    if (!employee.maNV || !employee.hoTen || !employee.ngaySinh || !employee.gioiTinh || !employee.soDT || !employee.diachi || !employee.maCN) {
      alert("Vui lòng nhập đầy đủ thông tin!");
      return;
    }

    try {
      const res = await fetch(`http://localhost:8080/api/NhanVien`, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(employee),
      });

      if (!res.ok) {
        throw new Error("Thêm thất bại");
      }

      alert("Thêm thành công!");

      setEmployee({
        maNV: "",
        hoTen: "",
        ngaySinh: "",
        gioiTinh: "",
        soDT: "",
        diachi: "",
        maCN: ""
      });

      navigate("/employee");

    } catch (err) {
      console.error(err);
      alert("Có lỗi xảy ra!");
    }
  };

  return (
    <div className="employee-detail">
      <GoBack />

      <h2 className="emp__title">Thêm nhân viên mới nhân viên</h2>

      <form onSubmit={handleSubmit} className="emp__form">

        <div className="form-group">
          <label>Mã</label>
          <input name="maNV" value={employee.maNV} onChange={handleChange} />
        </div>

        <div className="form-group">
          <label>Họ tên</label>
          <input name="hoTen" value={employee.hoTen} onChange={handleChange} />
        </div>

        <div className="form-group">
          <label>Ngày sinh</label>
          <input name="ngaySinh" value={employee.ngaySinh} onChange={handleChange} />
        </div>

        <div className="form-group">
          <label>Giới tính</label>
          <input name="gioiTinh" value={employee.gioiTinh} onChange={handleChange} />
        </div>

        <div className="form-group">
          <label>SĐT</label>
          <input name="soDT" value={employee.soDT} onChange={handleChange} />
        </div>

        <div className="form-group">
          <label>Địa chỉ</label>
          <input name="diachi" value={employee.diachi} onChange={handleChange} />
        </div>

        <div className="form-group">
          <label>Mã CN</label>
          <input name="maCN" value={employee.maCN} onChange={handleChange} />
        </div>

        <button type="submit" className="btn-submit">
          Thêm nhân viên mới
        </button>

      </form>
    </div>
  );
}

export default EmployeeAdd;