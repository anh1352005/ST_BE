import { useState } from "react";
import { useNavigate } from "react-router-dom";
import GoBack from "../GoBack";
import "./BranchForm.scss";
import { BRANCH_API_BASE, fetchBranchJson } from "../../utils/branchApi";

const initialBranch = {
  maCN: "",
  tenCN: "",
  diaChi: "",
  soDT: "",
};

function BranchAdd() {
  const navigate = useNavigate();
  const [branch, setBranch] = useState(initialBranch);

  const handleChange = (event) => {
    const { name, value } = event.target;
    setBranch((prev) => ({
      ...prev,
      [name]: value,
    }));
  };

  const handleSubmit = async (event) => {
    event.preventDefault();

    if (!branch.maCN || !branch.tenCN || !branch.diaChi || !branch.soDT) {
      alert("Vui long nhap day du thong tin chi nhanh.");
      return;
    }

    try {
      await fetchBranchJson(BRANCH_API_BASE, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(branch),
      });

      alert("Them chi nhanh thanh cong.");
      setBranch(initialBranch);
      navigate("/branch");
    } catch (err) {
      alert(err.message);
    }
  };

  return (
    <div className="branch-form-page">
      <GoBack />
      <h2 className="branch__title">Them chi nhanh moi</h2>

      <form onSubmit={handleSubmit} className="branch__form">
        <div className="form-group">
          <label>Ma chi nhanh</label>
          <input name="maCN" value={branch.maCN} onChange={handleChange} />
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
          Them chi nhanh
        </button>
      </form>
    </div>
  );
}

export default BranchAdd;
