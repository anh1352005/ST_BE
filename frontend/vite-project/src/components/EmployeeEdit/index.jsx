import { useNavigate, useParams } from "react-router-dom";
import { useEffect, useState } from "react";
import GoBack from "../GoBack/index";
import './EmployeeEdit.scss'

function EmployeeEdit() {
    const { id } = useParams();
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

    useEffect(() => {
        fetch(`http://localhost:8080/api/NhanVien/${id}`)
            .then(res => res.json())
            .then(data => {
                setEmployee(data);
            });
    }, [id]);

    const handleChange = (e) => {
        const { name, value } = e.target;
        setEmployee(prev => ({
            ...prev,
            [name]: value
        }));
    };

    const handleSubmit = async (e) => {
        e.preventDefault();

        try {
            const res = await fetch(`http://localhost:8080/api/NhanVien/${id}`, {
                method: "PUT",
                headers: {
                    "Content-Type": "application/json",
                },
                body: JSON.stringify(employee),
            });

            if (!res.ok) {
                throw new Error("Cập nhật thất bại");
            }

            alert("Cập nhật thành công!");

            navigate("/employee");

        } catch (err) {
            console.error(err);
            alert("Có lỗi xảy ra!");
        }
    };

    return (
        <div className="employee-detail">
            <GoBack />

            <h2 className="emp__title">Cập nhật thông tin nhân viên</h2>

            <form onSubmit={handleSubmit} className="emp__form">

                <div className="form-group">
                    <label>Mã</label>
                    <input name="maNV" value={employee.maNV} disabled />
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
                    Lưu thay đổi
                </button>

            </form>
        </div>
    );
}

export default EmployeeEdit;