import { useState } from "react";

function KhoForm({ onNhap, onXuat }) {
  const [maSP, setMaSP] = useState("");
  const [maCN, setMaCN] = useState("");
  const [soLuong, setSoLuong] = useState("");

  return (
    <div>
      <h3>Nhập / Xuất kho</h3>

      <input
        placeholder="Mã SP"
        value={maSP}
        onChange={(e) => setMaSP(e.target.value)}
      />

      <input
        placeholder="Mã CN"
        value={maCN}
        onChange={(e) => setMaCN(e.target.value)}
      />

      <input
        placeholder="Số lượng"
        value={soLuong}
        onChange={(e) => setSoLuong(e.target.value)}
      />

      <br />

      <button onClick={() => onNhap(maSP, maCN, soLuong)}>
        Nhập kho
      </button>

      <button onClick={() => onXuat(maSP, maCN, soLuong)}>
        Xuất kho
      </button>
    </div>
  );
}

export default KhoForm;