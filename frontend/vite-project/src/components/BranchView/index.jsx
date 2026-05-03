import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import "./BranchView.scss";
import { BRANCH_API_BASE, fetchBranchJson } from "../../utils/branchApi";

function BranchView() {
  const navigate = useNavigate();
  const [branches, setBranches] = useState([]);
  const [currentPage, setCurrentPage] = useState(1);
  const [keyword, setKeyword] = useState("");
  const [searchType, setSearchType] = useState("ten");
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  const limit = 10;

  const sortedBranches = [...branches].sort((a, b) => {
    if (a.maCN === b.maCN) {
      return a.tenCN.localeCompare(b.tenCN);
    }
    return a.maCN.localeCompare(b.maCN);
  });

  const totalPage = Math.max(1, Math.ceil(sortedBranches.length / limit));
  const startList = (currentPage - 1) * limit;
  const endList = currentPage * limit;

  const loadBranches = async () => {
    setLoading(true);
    setError("");

    try {
      const data = await fetchBranchJson(BRANCH_API_BASE);
      setBranches(Array.isArray(data) ? data : []);
      setCurrentPage(1);
    } catch (err) {
      setBranches([]);
      setError(err.message);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    loadBranches();
  }, []);

  const handleSearch = async () => {
    const trimmedKeyword = keyword.trim();

    if (!trimmedKeyword) {
      loadBranches();
      return;
    }

    setLoading(true);
    setError("");

    try {
      if (searchType === "phone") {
        const data = await fetchBranchJson(
          `${BRANCH_API_BASE}/phone/${encodeURIComponent(trimmedKeyword)}`
        );
        setBranches(data ? [data] : []);
      } else {
        const data = await fetchBranchJson(
          `${BRANCH_API_BASE}/search?ten=${encodeURIComponent(trimmedKeyword)}`
        );
        setBranches(Array.isArray(data) ? data : []);
      }
      setCurrentPage(1);
    } catch (err) {
      setBranches([]);
      setError(err.message);
    } finally {
      setLoading(false);
    }
  };

  const handleClear = () => {
    setKeyword("");
    setSearchType("ten");
    loadBranches();
  };

  const handleDelete = async (maCN, soLuongNhanVien) => {
    if (soLuongNhanVien > 0) {
      alert("Chi nhanh nay dang co nhan vien, khong the xoa.");
      return;
    }

    if (!window.confirm(`Ban co chac chan muon xoa chi nhanh ${maCN}?`)) {
      return;
    }

    try {
      await fetchBranchJson(`${BRANCH_API_BASE}/${maCN}`, {
        method: "DELETE",
      });
      alert("Xoa chi nhanh thanh cong.");
      if (keyword.trim()) {
        handleSearch();
        return;
      }
      loadBranches();
    } catch (err) {
      alert(err.message);
    }
  };

  const handleNext = () => {
    setCurrentPage((prev) => (prev >= totalPage ? 1 : prev + 1));
  };

  const handlePrev = () => {
    setCurrentPage((prev) => (prev <= 1 ? totalPage : prev - 1));
  };

  const visibleBranches = sortedBranches.slice(startList, endList);

  return (
    <div className="BranchView">
      <div className="BranchView__header">
        <div>
          <h2>Danh sach chi nhanh</h2>
          <p>Quan ly chi nhanh, tim kiem, xem thong ke va thao tac nhanh.</p>
        </div>
      </div>

      <div className="filter">
        <select
          value={searchType}
          onChange={(event) => setSearchType(event.target.value)}
        >
          <option value="ten">Tim theo ten</option>
          <option value="phone">Tim theo so dien thoai</option>
        </select>

        <input
          type="text"
          placeholder={
            searchType === "phone"
              ? "Nhap so dien thoai chi nhanh..."
              : "Nhap ten chi nhanh..."
          }
          value={keyword}
          onChange={(event) => setKeyword(event.target.value)}
          onKeyDown={(event) => {
            if (event.key === "Enter") {
              handleSearch();
            }
          }}
        />

        <button type="button" className="btn__search" onClick={handleSearch}>
          Tim
        </button>
        <button type="button" className="btn__clear" onClick={handleClear}>
          Tai lai
        </button>
      </div>

      {error && (
        <p className="BranchView__message BranchView__message--error">{error}</p>
      )}
      {loading && <p className="BranchView__message">Dang tai du lieu...</p>}

      {!loading && !error && (
        <>
          <table className="BranchView__table">
            <thead>
              <tr>
                <th>STT</th>
                <th>Ma CN</th>
                <th>Ten chi nhanh</th>
                <th>Dia chi</th>
                <th>So dien thoai</th>
                <th>So nhan vien</th>
                <th>So san pham kho</th>
                <th>Tac vu</th>
              </tr>
            </thead>

            <tbody>
              {visibleBranches.length > 0 ? (
                visibleBranches.map((item, index) => (
                  <tr key={item.maCN}>
                    <td>{startList + index + 1}</td>
                    <td>{item.maCN}</td>
                    <td>{item.tenCN}</td>
                    <td>{item.diaChi}</td>
                    <td>{item.soDT}</td>
                    <td>{item.soLuongNhanVien ?? 0}</td>
                    <td>{item.soLuongSanPham ?? 0}</td>
                    <td>
                      <div className="operation__btn">
                        <button
                          type="button"
                          onClick={() => navigate(`/branch/list-branch/${item.maCN}`)}
                        >
                          Xem
                        </button>
                        <button
                          type="button"
                          onClick={() => navigate(`/branch/edit-branch/${item.maCN}`)}
                        >
                          Sua
                        </button>
                        <button
                          type="button"
                          onClick={() =>
                            handleDelete(item.maCN, item.soLuongNhanVien ?? 0)
                          }
                          disabled={(item.soLuongNhanVien ?? 0) > 0}
                          title={
                            (item.soLuongNhanVien ?? 0) > 0
                              ? "Chi nhanh con nhan vien nen khong the xoa"
                              : "Xoa chi nhanh"
                          }
                        >
                          Xoa
                        </button>
                      </div>
                    </td>
                  </tr>
                ))
              ) : (
                <tr>
                  <td colSpan="8">Khong tim thay du lieu chi nhanh.</td>
                </tr>
              )}
            </tbody>
          </table>

          <div className="page__btn">
            <button type="button" onClick={handlePrev}>
              Trang truoc
            </button>
            <span>
              {currentPage} / {totalPage}
            </span>
            <button type="button" onClick={handleNext}>
              Trang sau
            </button>
          </div>
        </>
      )}
    </div>
  );
}

export default BranchView;
