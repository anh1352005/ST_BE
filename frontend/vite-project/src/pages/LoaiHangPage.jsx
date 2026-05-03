import { useEffect, useState } from "react";
import loaiHangApi from "../api/loaiHangApi";
import LoaiHangForm from "../components/LoaiHangForm";

function LoaiHangPage() {
  const [list, setList] = useState([]);
  const [searchText, setSearchText] = useState("");
  const [editingItem, setEditingItem] = useState(null);

  const loadData = async () => {
    const res = await loaiHangApi.getAll();
    setList(res.data);
  };

  useEffect(() => {
    loadData();
  }, []);

  const handleSubmit = async (data) => {
    if (editingItem) {
      await loaiHangApi.update(editingItem.maLoai, data);
      setEditingItem(null);
    } else {
      await loaiHangApi.create(data);
    }

    loadData();
  };

  const handleDelete = async (id) => {
    await loaiHangApi.delete(id);
    loadData();
  };

  const handleEdit = (item) => {
    setEditingItem(item);
  };

  const handleSearch = async () => {
    if (!searchText.trim()) {
      loadData();
      return;
    }

    const res = await loaiHangApi.search(searchText);
    setList(res.data);
  };

  return (
    <div>
      <h1>Quản lý loại hàng</h1>

      <LoaiHangForm
        onSubmit={handleSubmit}
        editingItem={editingItem}
      />

      <br />

      <input
        type="text"
        placeholder="Tìm theo tên"
        value={searchText}
        onChange={(e) => setSearchText(e.target.value)}
      />

      <button onClick={handleSearch}>Tìm</button>

      <hr />

      <table border="1" cellPadding="10">
        <thead>
          <tr>
            <th>Mã loại</th>
            <th>Tên loại</th>
            <th>Hành động</th>
          </tr>
        </thead>

        <tbody>
          {list.map((item) => (
            <tr key={item.maLoai}>
              <td>{item.maLoai}</td>
              <td>{item.tenLoai}</td>
              <td>
                <button onClick={() => handleEdit(item)}>
                  Sửa
                </button>

                <button
                  onClick={() => handleDelete(item.maLoai)}
                >
                  Xóa
                </button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}

export default LoaiHangPage;