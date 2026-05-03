import axiosClient from "./axiosClient";

const loaiHangApi = {
  getAll: () => axiosClient.get("/LoaiHang"),

  getById: (id) => axiosClient.get(`/LoaiHang/${id}`),

  search: (ten) =>
    axiosClient.get(`/LoaiHang/search?tenloai=${ten}`),

  create: (data) =>
    axiosClient.post("/LoaiHang", data),

  update: (id, data) =>
    axiosClient.put(`/LoaiHang/${id}`, data),

  delete: (id) =>
    axiosClient.delete(`/LoaiHang/${id}`)
};

export default loaiHangApi;