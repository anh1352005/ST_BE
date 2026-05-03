import { RiMoneyDollarCircleFill } from "react-icons/ri";
import { BsFillSignTurnLeftFill } from "react-icons/bs";
import { useEffect, useState } from "react";
import '../assets/style/DashBoard.scss';
import Header from "../components/Header";

function DashBoard() {
  const [products, setProduct] = useState([]);
  const [storage, setStorage] = useState([]);
  const [branches, setBranch] = useState([]);
  const [lengthList, setLengthList] = useState(10);

  useEffect(() => {
    fetch(`GET http://localhost:8080/api/HoaDon/top-sanpham`)
      .then(res => res.json())
      .then(data => {
        console.log(data);
        setProduct(data);
      })
  }, []);

  useEffect(() => {
    fetch(`http://localhost:8080/api/Kho`)
      .then(res => res.json())
      .then(data => {
        console.log(data)
        setStorage(data);
      })
  }, [])

  useEffect(() => {
    fetch(`http://localhost:8080/api/HoaDon/stat/chinhanh`)
      .then(res => res.json())
      .then(data => {
        console.log(data)
        setBranch(data)
      })
  }, [])

  const sortedProducts = [...products].sort((a, b) => {
    return b.doanhThu - a.doanhThu;
  });

  const sortedStorage = [...storage].sort((a, b) => {
    if (a.soLuongTon === b.soLuongTon) {
      return b.gia - a.gia;
    }
    return b.soLuongTon - a.soLuongTon;
  })

  const sortedBranches = [...branches].sort((a, b) => {
    return b.doanhThu - a.doanhThu;
  })

  const all = storage.length;

  const handleChange = (value) => {
    setLengthList(Number(value));
  }
  console.log(lengthList);

  return (
    <>
      <Header></Header>
      <div className="dashboard">
        <div className="section">
          <p className="section__title">Kết quả bán hàng hôm nay</p>
          <div className="section__result">
            <div className="section__result--item">
              <div className="detail">
                <div className="detail__icon">
                  <RiMoneyDollarCircleFill />
                </div>
                <div className="detail__item">
                  <p className="detail__item--title">Doanh thu</p>
                  <h3 className="detail__item--total">{ }đ</h3>
                  <p className="detail__item--bill"></p>
                </div>
              </div>
            </div>
            <div className="section__result--item">
              <div className="detail">
                <div className="detail__icon">
                  <BsFillSignTurnLeftFill />
                </div>
                <div className="detail__item">
                  <p className="detail__item--title">Trả hàng</p>
                  <h3 className="detail__item--total">{ }</h3>
                </div>
              </div>
            </div>
          </div>
        </div>
        <div className="section">
          <div className="best-selling">
            <p className="best-selling__title">Top sản phẩm bán chạy</p>
            <table>
              <thead>
                <tr>
                  <th>STT</th>
                  <th>Tên sản phẩm</th>
                  <th>Số lượng</th>
                  <th>Doanh thu</th>
                </tr>
              </thead>
              <tbody>
                {sortedProducts.slice(0, 10).map((item, index) => (
                  <tr key={index}>
                    <td>{index + 1}</td>
                    <td>{item.tenSP}</td>
                    <td>{item.soLuong}</td>
                    <td>{item.doanhThu}</td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
          <div className="branch">
            <p className="branch__title">Top chi nhánh có doanh thu cao nhất</p>
            <table>
              <thead>
                <tr>
                  <th>STT</th>
                  <th>Mã chi nhánh</th>
                  <th>Tên chi nhánh</th>
                  <th>Doanh thu</th>
                </tr>
              </thead>
              <tbody>
                {sortedBranches.slice(0, 10).map((item, index) => (
                  <tr>
                    <td>{index + 1}</td>
                    <td>{item.maCN}</td>
                    <td>{item.tenCN}</td>
                    <td>{item.doanhThu}</td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
          <div className="stock">
            <p className="stock__title">Sản phẩm tồn kho tại các chi nhánh</p>
            <table>
              <thead>
                <tr>
                  <th>STT</th>
                  <th>Mã sản phẩm</th>
                  <th>Tên sản phẩm</th>
                  <th>Mã sản phẩm</th>
                  <th>Mã chi nhánh</th>
                  <th>Tên chi nhánh</th>
                  <th>Số lượng tồn</th>
                  <th>Giá</th>
                </tr>
              </thead>
              <tbody>
                {sortedStorage.slice(0, lengthList).map((item, index) => (
                  <tr key={index}>
                    <td>{index + 1}</td>
                    <td>{item.maSP}</td>
                    <td>{item.tenSP}</td>
                    <td>{item.maCN}</td>
                    <td>{item.tenCN}</td>
                    <td>{item.soLuongTon}</td>
                    <td>{item.gia}</td>
                  </tr>
                ))}
              </tbody>
            </table>
            <select name="" id="" onChange={(e) => handleChange(e.target.value)}>
              <option value={10}>/10</option>
              <option value={20}>/20</option>
              <option value={30}>/30</option>
              <option value={50}>/50</option>
              <option value={all}>Tất cả</option>
            </select>
          </div>
        </div>
      </div>
    </>
  );
}

export default DashBoard;