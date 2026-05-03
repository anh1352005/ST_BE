import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import GoBack from "../GoBack";
import "./BranchDetail.scss";
import { BRANCH_API_BASE, fetchBranchJson } from "../../utils/branchApi";

function BranchDetail() {
  const { id } = useParams();
  const [branch, setBranch] = useState(null);
  const [error, setError] = useState("");

  useEffect(() => {
    const loadBranch = async () => {
      try {
        const data = await fetchBranchJson(`${BRANCH_API_BASE}/${id}`);
        setBranch(data);
      } catch (err) {
        setError(err.message);
      }
    };

    loadBranch();
  }, [id]);

  if (error) {
    return (
      <div className="branch-detail-page branch-detail-page--center">
        <GoBack />
        <p>{error}</p>
      </div>
    );
  }

  if (!branch) {
    return (
      <div className="branch-detail-page branch-detail-page--center">
        <GoBack />
        <p>Dang tai chi tiet chi nhanh...</p>
      </div>
    );
  }

  return (
    <div className="branch-detail-page">
      <GoBack />
      <div className="detail-card">
        <h2 className="branch__title">Chi tiet chi nhanh</h2>
        <p className="branch__item">
          <b>Ma chi nhanh:</b>
          <span>{branch.maCN}</span>
        </p>
        <p className="branch__item">
          <b>Ten chi nhanh:</b>
          <span>{branch.tenCN}</span>
        </p>
        <p className="branch__item">
          <b>Dia chi:</b>
          <span>{branch.diaChi}</span>
        </p>
        <p className="branch__item">
          <b>So dien thoai:</b>
          <span>{branch.soDT}</span>
        </p>
        <p className="branch__item">
          <b>So luong nhan vien:</b>
          <span>{branch.soLuongNhanVien ?? 0}</span>
        </p>
        <p className="branch__item">
          <b>So luong san pham trong kho:</b>
          <span>{branch.soLuongSanPham ?? 0}</span>
        </p>
      </div>
    </div>
  );
}

export default BranchDetail;
