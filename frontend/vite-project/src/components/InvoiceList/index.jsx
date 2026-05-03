import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import hoaDonApi from '../../api/hoaDonApi';
import './InvoiceList.scss';

function InvoiceList() {
    const [invoices, setInvoices] = useState([]);
    const [searchTerm, setSearchTerm] = useState("");
    const [loading, setLoading] = useState(true);
    const navigate = useNavigate();

    useEffect(() => {
        fetchInvoices();
    }, []);

    const fetchInvoices = async () => {
        try {
            const res = await hoaDonApi.getAll();
            const data = res && res.data ? res.data : res;
            setInvoices(Array.isArray(data) ? data : []);
            setLoading(false);
        } catch (error) {
            setInvoices([]);
            setLoading(false);
        }
    };

    const handleDelete = async (maHD) => {
        if (window.confirm(`Bạn có chắc chắn muốn xóa hóa đơn ${maHD}?`)) {
            try {
                await hoaDonApi.delete(maHD);
                fetchInvoices();
            } catch (error) {
            }
        }
    };

    const formatCurrency = (amount) => {
        return new Intl.NumberFormat('vi-VN').format(amount) + " đ";
    };

    const filteredInvoices = Array.isArray(invoices) ? invoices.filter(item => 
        (item.maHD && item.maHD.toLowerCase().includes(searchTerm.toLowerCase())) ||
        (item.tenCN && item.tenCN.toLowerCase().includes(searchTerm.toLowerCase()))
    ) : [];

    return (
        <div className="invoice-list">
            <div className="invoice-list__header">
                <h2 className="invoice-list__title">Danh sách hóa đơn</h2>
                <div className="invoice-list__actions">
                    <div className="search-box">
                        <input 
                            type="text" 
                            placeholder="Tìm mã HD, chi nhánh..." 
                            value={searchTerm}
                            onChange={(e) => setSearchTerm(e.target.value)}
                        />
                        <button className="btn-search">Tìm</button>
                    </div>
                </div>
            </div>

            <div className="table-container">
                <table className="custom-table">
                    <thead>
                        <tr>
                            <th>STT</th>
                            <th>Mã HD</th>
                            <th>Ngày lập</th>
                            <th>Chi nhánh</th>
                            <th>Nhân viên</th>
                            <th>Tổng tiền</th>
                            <th>Tác vụ</th>
                        </tr>
                    </thead>
                    <tbody>
                        {loading ? (
                            <tr><td colSpan="7" className="text-center">Đang tải dữ liệu...</td></tr>
                        ) : filteredInvoices.length > 0 ? (
                            filteredInvoices.map((item, index) => (
                                <tr key={item.maHD}>
                                    <td>{index + 1}</td>
                                    <td><strong>{item.maHD}</strong></td>
                                    <td>{item.ngayLap}</td>
                                    <td>{item.tenCN}</td>
                                    <td>{item.tenNV}</td>
                                    <td className="price-cell">{formatCurrency(item.tongTien || 0)}</td>
                                    <td>
                                        <div className="action-group">
                                            <button 
                                                className="btn-view" 
                                                onClick={() => navigate(`/invoice/list-invoice/${item.maHD}`)}
                                            >
                                                Xem
                                            </button>
                                            <button 
                                                className="btn-delete" 
                                                onClick={() => handleDelete(item.maHD)}
                                            >
                                                Xóa
                                            </button>
                                        </div>
                                    </td>
                                </tr>
                            ))
                        ) : (
                            <tr><td colSpan="7" className="text-center">Không tìm thấy hóa đơn nào</td></tr>
                        )}
                    </tbody>
                </table>
            </div>
        </div>
    );
}

export default InvoiceList;