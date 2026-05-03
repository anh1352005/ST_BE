import axiosClient from "./axiosClient";

const khoApi = {
  getAll: () => axiosClient.get("/Kho"),

  getByChiNhanh: (maCN) =>
    axiosClient.get(`/Kho/chinhanh/${maCN}`),

  getBySanPham: (maSP) =>
    axiosClient.get(`/Kho/sanpham/${maSP}`),

  getBySP_CN: (maSP, maCN) =>
    axiosClient.get(`/Kho/${maSP}&${maCN}`),

  nhapKho: (maSP, maCN, soLuong) =>
    axiosClient.post(
      `/Kho/nhap?maSP=${maSP}&maCN=${maCN}&soLuong=${soLuong}`
    ),

  xuatKho: (maSP, maCN, soLuong) =>
    axiosClient.post(
      `/Kho/xuat?maSP=${maSP}&maCN=${maCN}&soLuong=${soLuong}`
    ),

  update: (maSP, maCN, soLuongTon) =>
    axiosClient.put(
      `/Kho/${maSP}&${maCN}?soLuongTon=${soLuongTon}`
    ),

  delete: (maSP, maCN) =>
    axiosClient.delete(`/Kho/${maSP}&${maCN}`),

  lowStock: (threshold) =>
    axiosClient.get(`/Kho/lowstock?threshold=${threshold}`),

  stat: () => axiosClient.get("/Kho/stat"),

  countProducts: () =>
    axiosClient.get("/Kho/countproducts")
};

export default khoApi;