import { useEffect, useState } from "react";
import sanPhamApi from "../api/sanPhamApi";
import loaiHangApi from "../api/loaiHangApi";
import SanPhamForm from "../components/SanPhamForm";

function SanPhamPage() {
  const [list, setList] = useState([]);
  const [loaiHangList, setLoaiHangList] = useState([]);
  const [editingItem, setEditingItem] = useState(null);

  const [search, setSearch] = useState("");
  const [minPrice, setMinPrice] = useState("");
  const [maxPrice, setMaxPrice] = useState("");

  const loadData = async () => {
    const res = await sanPhamApi.getAll();
    setList(res.data);
  };

  const loadLoaiHang = async () => {
    const res = await loaiHangApi.getAll();
    setLoaiHangList(res.data);
  };

  useEffect(() => {
    loadData();
    loadLoaiHang();
  }, []);

  const handleSubmit = async (data) => {
    if (editingItem) {
      await sanPhamApi.update(editingItem.maSP, data);
      setEditingItem(null);
    } else {
      await sanPhamApi.create(data);
    }

    loadData();
  };

  const handleDelete = async (id) => {
    await sanPhamApi.delete(id);
    loadData();
  };

  const handleSearch = async () => {
    if (!search) return loadData();

    const res = await sanPhamApi.search(search);
    setList(res.data);
  };

  const handleFilterPrice = async () => {
    if (!minPrice || !maxPrice) return;

    const res = await sanPhamApi.getByPrice(minPrice, maxPrice);
    setList(res.data);
  };

  const handleFilterLoai = async (maLoai) => {
    if (!maLoai) return loadData();

    const res = await sanPhamApi.getByLoai(maLoai);
    setList(res.data);
  };

  return (
    <div>
      <h1>Quản lý sản phẩm</h1>

      <SanPhamForm
        onSubmit={handleSubmit}
        editingItem={editingItem}
        loaiHangList={loaiHangList}
      />

      <hr />

      {/* SEARCH */}
      <input
        placeholder="Tìm theo tên"
        value={search}
        onChange={(e) => setSearch(e.target.value)}
      />
      <button onClick={handleSearch}>Tìm</button>

      {/* FILTER PRICE */}
      <div>
        <input
          placeholder="Giá từ"
          onChange={(e) => setMinPrice(e.target.value)}
        />
        <input
          placeholder="đến"
          onChange={(e) => setMaxPrice(e.target.value)}
        />
        <button onClick={handleFilterPrice}>
          Lọc giá
        </button>
      </div>

      {/* FILTER LOẠI */}
      <select
        onChange={(e) =>
          handleFilterLoai(e.target.value)
        }
      >
        <option value="">-- Lọc theo loại --</option>
        {loaiHangList.map((l) => (
          <option key={l.maLoai} value={l.maLoai}>
            {l.tenLoai}
          </option>
        ))}
      </select>

      <hr />

      <table border="1" cellPadding="10">
        <thead>
          <tr>
            <th>Mã</th>
            <th>Tên</th>
            <th>Giá</th>
            <th>Loại</th>
            <th>Hành động</th>
          </tr>
        </thead>

        <tbody>
          {list.map((sp) => (
            <tr key={sp.maSP}>
              <td>{sp.maSP}</td>
              <td>{sp.tenSP}</td>
              <td>{sp.gia}</td>
              <td>{sp.maLoai}</td>

              <td>
                <button onClick={() => setEditingItem(sp)}>
                  Sửa
                </button>

                <button onClick={() => handleDelete(sp.maSP)}>
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

export default SanPhamPage;