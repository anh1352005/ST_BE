import { useState, useEffect } from "react";
import "./EmployeeDelete.scss";

function EmployeeView() {
  const [employees, setEmployee] = useState([]);
  const [filteredEmployees, setFilteredEmployee] = useState([]);
  const [currentPage, setCurrentPage] = useState(1);
  const [keyword, setKeyword] = useState("");

  const limit = 10;

  // fetch data
  useEffect(() => {
    fetch("http://localhost:8080/api/NhanVien")
      .then(res => res.json())
      .then(data => {
        setEmployee(data);
      });
  }, []);


  // sort
  const sortedEmployees = [...employees].sort((a, b) => {
    if (a.maNV === b.maNV) {
      return a.maCN.localeCompare(b.maCN);
    }
    return a.maNV.localeCompare(b.maNV);
  });

  // pagination
  const totalPage = Math.ceil(sortedEmployees.length / limit);
  const startList = (currentPage - 1) * limit;
  const endList = currentPage * limit;

  // handle page
  const handleNext = () => {
    setCurrentPage(prev => (prev >= totalPage ? 1 : prev + 1));
  };

  const handlePrev = () => {
    setCurrentPage(prev => (prev <= 1 ? totalPage : prev - 1));
  };

  const handleSearch = () => {
    const newfilteredEmployees = sortedEmployees.filter(e => {
      return (
        (
          e.hoTen.toLowerCase().includes(keyword.toLowerCase()) ||
          e.maNV.toLowerCase().includes(keyword.toLowerCase()) ||
          e.soDT.includes(keyword)
        )
      );
    });
    setFilteredEmployee(newfilteredEmployees);
  }

  return (
    <div className="EmployeeView">
      <h2>Danh sách nhân viên</h2>
      <div className="filter">
        <input
          type="text"
          placeholder="Tìm tên, mã NV, SĐT..."
          value={keyword}
          onChange={(e) => setKeyword(e.target.value)}
        />

        <button type="submit" className="btn__search" onClick={handleSearch}>Tìm</button>
      </div>

      {keyword === "" && (
        <table className="EmployeeView__table">
          <thead>
            <tr>
              <th>STT</th>
              <th>Mã NV</th>
              <th>Họ tên</th>
              <th>Ngày sinh</th>
              <th>Giới tính</th>
              <th>SĐT</th>
              <th>Địa chỉ</th>
              <th>Mã CN</th>
              {/* <th>Tác vụ</th> */}
            </tr>
          </thead>

          <tbody>
            {sortedEmployees.slice(startList, endList).map((item, index) => (
              <tr key={item.maNV}>
                <td>{startList + index + 1}</td>
                <td>{item.maNV}</td>
                <td>{item.hoTen}</td>
                <td>{item.ngaySinh}</td>
                <td>{item.gioiTinh}</td>
                <td>{item.soDT}</td>
                <td>{item.diachi}</td>
                <td>{item.maCN}</td>
                {/* <td>
                  <div className="operation__btn">
                    <button>Xem</button>
                    <button>Sửa</button>
                    <button>Xóa</button>
                  </div>
                </td> */}
              </tr>
            ))}
          </tbody>
        </table>
      )}

      {(keyword != "" && filteredEmployees.length != 0) && (
        <table className="EmployeeView__table">
          <thead>
            <tr>
              <th>STT</th>
              <th>Mã NV</th>
              <th>Họ tên</th>
              <th>Ngày sinh</th>
              <th>Giới tính</th>
              <th>SĐT</th>
              <th>Địa chỉ</th>
              <th>Mã CN</th>
              <th>Tác vụ</th>
            </tr>
          </thead>

          <tbody>
            {sortedEmployees.slice(startList, endList).map((item, index) => (
              <tr key={item.maNV}>
                <td>{startList + index + 1}</td>
                <td>{item.maNV}</td>
                <td>{item.hoTen}</td>
                <td>{item.ngaySinh}</td>
                <td>{item.gioiTinh}</td>
                <td>{item.soDT}</td>
                <td>{item.diachi}</td>
                <td>{item.maCN}</td>
                {/* <td>
                  <div className="operation__btn">
                    <button>Xem</button>
                    <button>Sửa</button>
                    <button>Xóa</button>
                  </div>
                </td> */}
              </tr>
            ))}
          </tbody>
        </table>
      )}

      {(filteredEmployees.length === 0 && keyword != "") && (
        <table className="EmployeeView__table">
          <thead>
            <tr>
              <th>STT</th>
              <th>Mã NV</th>
              <th>Họ tên</th>
              <th>Ngày sinh</th>
              <th>Giới tính</th>
              <th>SĐT</th>
              <th>Địa chỉ</th>
              <th>Mã CN</th>
              {/* <th>Tác vụ</th> */}
            </tr>
          </thead>
          <tbody>
            <tr>
              <td colSpan="8">Không tìm thấy dữ liệu</td>
            </tr>
          </tbody>
          </table>
      )}

          {/* PAGINATION */}
          <div className="page__btn">
            <button onClick={handlePrev}>⬅ Trang trước</button>
            <span>
              {currentPage} / {totalPage || 1}
            </span>
            <button onClick={handleNext}>Trang sau ➡</button>
          </div>
        </div>
      );
}

      export default EmployeeView;