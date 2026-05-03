import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import GoBack from "../GoBack";
import "../BranchAdd/BranchForm.scss";
import { BRANCH_API_BASE, fetchBranchJson } from "../../utils/branchApi";

const initialBranch = {
  maCN: "",
  tenCN: "",
  diaChi: "",
  soDT: "",
};

function BranchEdit() {
  const { id } = useParams();
  const navigate = useNavigate();
  const [branch, setBranch] = useState(initialBranch);

  useEffect(() => {
    const loadBranch = async () => {
      try {
        const data = await fetchBranchJson(`${BRANCH_API_BASE}/${id}`);
        setBranch({
          maCN: data.maCN || "",
          tenCN: data.tenCN || "",
          diaChi: data.diaChi || "",
          soDT: data.soDT || "",
        });
      } catch (err) {
        alert(err.message);
      }
    };

    loadBranch();
  }, [id]);

  const handleChange = (event) => {
    const { name, value } = event.target;
    setBranch((prev) => ({
      ...prev,
      [name]: value,
    }));
  };

  const handleSubmit = async (event) => {
    event.preventDefault();

    if (!branch.tenCN || !branch.diaChi || !branch.soDT) {
      alert("Vui long nhap day du thong tin chi nhanh.");
      return;
    }

    try {
      await fetchBranchJson(`${BRANCH_API_BASE}/${id}`, {
        method: "PUT",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(branch),
      });

      alert("Cap nhat chi nhanh thanh cong.");
      navigate("/branch");
    } catch (err) {
      alert(err.message);
    }
  };

  return (
    <div className="branch-form-page">
      <GoBack />
      <h2 className="branch__title">Cap nhat thong tin chi nhanh</h2>

      <form onSubmit={handleSubmit} className="branch__form">
        <div className="form-group">
          <label>Ma chi nhanh</label>
          <input name="maCN" value={branch.maCN} disabled />
        </div>

        <div className="form-group">
          <label>Ten chi nhanh</label>
          <input name="tenCN" value={branch.tenCN} onChange={handleChange} />
        </div>

        <div className="form-group full">
          <label>Dia chi</label>
          <input name="diaChi" value={branch.diaChi} onChange={handleChange} />
        </div>

        <div className="form-group full">
          <label>So dien thoai</label>
          <input name="soDT" value={branch.soDT} onChange={handleChange} />
        </div>

        <button type="submit" className="btn-submit">
          Luu thay doi
        </button>
      </form>
    </div>
  );
}

export default BranchEdit;
