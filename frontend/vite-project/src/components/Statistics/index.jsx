import React, { useEffect, useState } from "react";
import { useOutletContext } from "react-router-dom";
import thongKeApi from "../../api/thongKeApi";

function BranchStats() {
  const [list, setList] = useState([]);
  const { formatVND } = useOutletContext();

  useEffect(() => {
    thongKeApi.getDoanhThuChiNhanh().then(res => setList(res.data || res || []));
  }, []);

  const total = list.reduce((sum, item) => sum + (item.doanhThu || 0), 0);

  return (
    <table className="stats-table">
      <thead>
        <tr>
          <th>Mã CN</th>
          <th>Tên chi nhánh</th>
          <th>Doanh thu</th>
          <th>Tỷ trọng</th>
        </tr>
      </thead>
      <tbody>
        {list.map((item, i) => (
          <tr key={i}>
            <td>{item.maCN}</td>
            <td>{item.tenCN}</td>
            <td className="revenue">{formatVND(item.doanhThu)}</td>
            <td>
              <div className="bar-bg">
                <div className="bar-fill" style={{ width: `${total > 0 ? (item.doanhThu / total) * 100 : 0}%` }}></div>
              </div>
            </td>
          </tr>
        ))}
      </tbody>
    </table>
  );
}

export default BranchStats;