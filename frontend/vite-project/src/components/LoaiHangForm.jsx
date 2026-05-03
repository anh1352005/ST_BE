import { useState, useEffect } from "react";

function LoaiHangForm({ onSubmit, editingItem }) {
  const [tenLoai, setTenLoai] = useState("");

  useEffect(() => {
    if (editingItem) {
      setTenLoai(editingItem.tenLoai);
    } else {
      setTenLoai("");
    }
  }, [editingItem]);

  const handleSubmit = () => {
    if (!tenLoai.trim()) return;

    onSubmit({
      tenLoai
    });

    if (!editingItem) {
      setTenLoai("");
    }
  };

  return (
    <div>
      <input
        type="text"
        placeholder="Tên loại hàng"
        value={tenLoai}
        onChange={(e) => setTenLoai(e.target.value)}
      />

      <button onClick={handleSubmit}>
        {editingItem ? "Cập nhật" : "Thêm"}
      </button>
    </div>
  );
}

export default LoaiHangForm;