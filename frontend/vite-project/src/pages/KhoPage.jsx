import { useEffect, useState } from "react";
import khoApi from "../api/khoApi";
import KhoForm from "../components/KhoForm";

function KhoPage() {
  const [list, setList] = useState([]);
  const [threshold, setThreshold] = useState("");
  const [stat, setStat] = useState(null);
  const [count, setCount] = useState(null);

  // LOAD ALL
  const loadData = async () => {
    const res = await khoApi.getAll();
    setList(res.data);
  };

  useEffect(() => {
    loadData();
  }, []);

  // NHẬP KHO
  const handleNhap = async (maSP, maCN, soLuong) => {
    await khoApi.nhapKho(maSP, maCN, soLuong);
    loadData();
  };

  // XUẤT KHO
  const handleXuat = async (maSP, maCN, soLuong) => {
    await khoApi.xuatKho(maSP, maCN, soLuong);
    loadData();
  };

  // LOW STOCK
  const handleLowStock = async () => {
    const res = await khoApi.lowStock(threshold);
    setList(res.data);
  };

  // STAT
  const handleStat = async () => {
    const res = await khoApi.stat();
    setStat(res.data);
  };

  // COUNT
  const handleCount = async () => {
    const res = await khoApi.countProducts();
    setCount(res.data);
  };

  return (
    <div>
      <h1>Quản lý kho</h1>

      {/* FORM NHẬP/XUẤT */}
      <KhoForm
        onNhap={handleNhap}
        onXuat={handleXuat}
      />

      <hr />

      {/* FILTER LOW STOCK */}
      <input
        placeholder="Ngưỡng tồn thấp"
        onChange={(e) => setThreshold(e.target.value)}
      />
      <button onClick={handleLowStock}>
        Lọc tồn thấp
      </button>

      <button onClick={loadData}>Reset</button>

      <hr />

      {/* STAT + COUNT */}
      <button onClick={handleStat}>
        Thống kê kho
      </button>

      <button onClick={handleCount}>
        Đếm sản phẩm
      </button>

      {stat && (
        <div>
          <h3>Thống kê</h3>
          <pre>{JSON.stringify(stat, null, 2)}</pre>
        </div>
      )}

      {count && (
        <div>
          <h3>Tổng số SP: {count}</h3>
        </div>
      )}

      <hr />

      {/* TABLE */}
      <table border="1" cellPadding="10">
        <thead>
          <tr>
            <th>Mã SP</th>
            <th>Mã CN</th>
            <th>Tồn kho</th>
          </tr>
        </thead>

        <tbody>
          {list.map((item) => (
            <tr key={item.maSP + item.maCN}>
              <td>{item.maSP}</td>
              <td>{item.maCN}</td>
              <td>{item.soLuongTon}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}

export default KhoPage;