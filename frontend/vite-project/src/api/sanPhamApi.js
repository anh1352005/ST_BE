import axiosClient from "./axiosClient";

const sanPhamApi = {
  getAll: () => axiosClient.get("/SanPham"),

  getById: (id) => axiosClient.get(`/SanPham/${id}`),

  search: (ten) =>
    axiosClient.get(`/SanPham/search?tenSP=${ten}`),

  getByLoai: (maLoai) =>
    axiosClient.get(`/SanPham/type/${maLoai}`),

  getByPrice: (min, max) =>
    axiosClient.get(`/SanPham/price?min=${min}&max=${max}`),

  create: (data) =>
    axiosClient.post("/SanPham", data),

  update: (id, data) =>
    axiosClient.put(`/SanPham/${id}`, data),

  delete: (id) =>
    axiosClient.delete(`/SanPham/${id}`)
};

export default sanPhamApi;