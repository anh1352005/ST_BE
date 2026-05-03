import { useParams } from "react-router-dom";
import { useEffect, useState } from "react";
import GoBack from "../GoBack/index";

function EmployeeDetail() {
  const { id } = useParams();
  const [employee, setEmployee] = useState(null);

  useEffect(() => {
    fetch(`http://localhost:8080/api/NhanVien/${id}`)
      .then(res => res.json())
      .then(data => {
        setEmployee(data);
      });
  }, [id]);

  if (!employee) return <p>Loading...</p>;

  return (
    <div className="employee-detail">
      <GoBack></GoBack>
      <h2 className="emp__title">Chi tiết nhân viên</h2>
      <p className="emp__item"><b>Mã:</b> {employee.maNV}</p>
      <p className="emp__item"><b>Họ tên:</b> {employee.hoTen}</p>
      <p className="emp__item"><b>Ngày sinh:</b> {employee.ngaySinh}</p>
      <p className="emp__item"><b>Giới tính:</b> {employee.gioiTinh}</p>
      <p className="emp__item"><b>SĐT:</b> {employee.soDT}</p>
      <p className="emp__item"><b>Địa chỉ:</b> {employee.diachi}</p>
      <p className="emp__item"><b>Mã CN:</b> {employee.maCN}</p>
    </div>
  );
}

export default EmployeeDetail;