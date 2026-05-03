import { useEffect, useState } from "react";

function SanPhamForm({ onSubmit, editingItem, loaiHangList }) {
  const [tenSP, setTenSP] = useState("");
  const [gia, setGia] = useState("");
  const [maLoai, setMaLoai] = useState("");

  useEffect(() => {
    if (editingItem) {
      setTenSP(editingItem.tenSP);
      setGia(editingItem.gia);
      setMaLoai(editingItem.maLoai);
    } else {
      setTenSP("");
      setGia("");
      setMaLoai("");
    }
  }, [editingItem]);

  const handleSubmit = () => {
    if (!tenSP || !gia || !maLoai) return;

    onSubmit({
      tenSP,
      gia,
      maLoai
    });
  };

  return (
    <div>
      <input
        placeholder="Tên sản phẩm"
        value={tenSP}
        onChange={(e) => setTenSP(e.target.value)}
      />

      <input
        placeholder="Giá"
        value={gia}
        onChange={(e) => setGia(e.target.value)}
      />

      <select
        value={maLoai}
        onChange={(e) => setMaLoai(e.target.value)}
      >
        <option value="">-- Chọn loại hàng --</option>
        {loaiHangList.map((l) => (
          <option key={l.maLoai} value={l.maLoai}>
            {l.tenLoai}
          </option>
        ))}
      </select>

      <button onClick={handleSubmit}>
        {editingItem ? "Cập nhật" : "Thêm"}
      </button>
    </div>
  );
}

export default SanPhamForm;