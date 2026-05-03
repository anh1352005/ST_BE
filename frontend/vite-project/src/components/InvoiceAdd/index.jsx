import React, { useState, useEffect } from 'react';
import axiosClient from '../../api/axiosClient';
import './InvoiceAdd.scss';

function InvoiceAdd() {
  const [products, setProducts] = useState([]);
  const [selectedItems, setSelectedItems] = useState([]);
  const [invoiceInfo, setInvoiceInfo] = useState({
    maHD: '',
    maCN: '',
    maNV: ''
  });

  useEffect(() => {
    fetchProducts();
  }, []);

  const fetchProducts = async () => {
    try {
      const res = await axiosClient.get('/Kho');
      const data = res.data || res;
      setProducts(Array.isArray(data) ? data : []);
    } catch (error) {
      setProducts([]);
    }
  };

  const addToCart = (product) => {
    const exist = selectedItems.find(x => x.maSP === product.maSP && x.maCN === product.maCN);
    if (exist) {
      setSelectedItems(selectedItems.map(x =>
        (x.maSP === product.maSP && x.maCN === product.maCN) ? { ...exist, qty: exist.qty + 1 } : x
      ));
    } else {
      setSelectedItems([...selectedItems, {
        ...product,
        qty: 1,
        gia: Number(product.gia) || 0
      }]);
    }
  };

  const handlePayment = async () => {
    if (!invoiceInfo.maHD || !invoiceInfo.maCN || !invoiceInfo.maNV) {
      alert("Vui lòng nhập đầy đủ Mã HD, Chi nhánh và Mã nhân viên!");
      return;
    }

    const payload = {
      maHD: invoiceInfo.maHD,
      maCN: invoiceInfo.maCN,
      maNV: invoiceInfo.maNV,
      ngayLap: new Date().toISOString().split('T')[0],
      chiTiet: selectedItems.map(item => ({
        maSP: item.maSP,
        soLuong: item.qty,
        donGia: item.gia
      }))
    };

    try {
      await axiosClient.post('/HoaDon', payload);
      alert("Thanh toán thành công! Hóa đơn đã được lưu và trừ tồn kho.");
      setSelectedItems([]);
      setInvoiceInfo({ maHD: '', maCN: '', maNV: '' });
      fetchProducts();
    } catch (error) {
      const msg = error.response?.data?.message || "Lỗi kết nối server hoặc dữ liệu không hợp lệ";
      alert("Thanh toán thất bại: " + msg);
    }
  };

  const formatCurrency = (val) => {
    return new Intl.NumberFormat('vi-VN').format(val || 0) + "đ";
  };

  const totalAmount = selectedItems.reduce((a, c) => a + (c.gia * c.qty), 0);

  return (
    <div className="invoice-add-wrapper">
      <div className="inventory-section">
        <div className="section-header">
          <h3 className="section-title">Danh sách sản phẩm trong kho</h3>
          <button className="btn-refresh" onClick={fetchProducts}>Cập nhật kho</button>
        </div>
        <div className="inventory-grid">
          {products.map((p, index) => (
            <div key={index} className="inventory-card" onClick={() => addToCart(p)}>
              <p className="p-name">{p.tenSP}</p>
              <p className="p-info">Kho: {p.tenCN} | Tồn: {p.soLuongTon}</p>
              <p className="p-price">{formatCurrency(p.gia)}</p>
            </div>
          ))}
        </div>
      </div>

      <div className="billing-section">
        <h3 className="section-title">Thông tin & Đơn hàng</h3>
        <div className="input-group">
          <input
            type="text"
            placeholder="Mã hóa đơn (HD...)"
            value={invoiceInfo.maHD}
            onChange={e => setInvoiceInfo({...invoiceInfo, maHD: e.target.value})}
          />
          <input
            type="text"
            placeholder="Mã chi nhánh (CN...)"
            value={invoiceInfo.maCN}
            onChange={e => setInvoiceInfo({...invoiceInfo, maCN: e.target.value})}
          />
          <input
            type="text"
            placeholder="Mã nhân viên (NV...)"
            value={invoiceInfo.maNV}
            onChange={e => setInvoiceInfo({...invoiceInfo, maNV: e.target.value})}
          />
        </div>

        <div className="cart-container">
          {selectedItems.length === 0 && <p className="empty-cart">Chưa có sản phẩm nào được chọn</p>}
          {selectedItems.map((item, idx) => (
            <div key={idx} className="cart-row">
              <span className="cart-name">{item.tenSP} (x{item.qty})</span>
              <span className="cart-subtotal">{formatCurrency(item.gia * item.qty)}</span>
            </div>
          ))}
        </div>

        <div className="billing-footer">
          <div className="total-row">
            <span>Tổng cộng:</span>
            <strong>{formatCurrency(totalAmount)}</strong>
          </div>
          <button
            className="payment-button"
            onClick={handlePayment}
            disabled={selectedItems.length === 0}
          >
            Thanh toán & Lưu
          </button>
        </div>
      </div>
    </div>
  );
}

export default InvoiceAdd;