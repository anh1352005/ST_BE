import { useNavigate } from "react-router-dom";
import './GoBack.scss';

function GoBack() {
  const navigate = useNavigate();

  return (
    <button className="go-back" onClick={() => navigate(-1)}>
      ← Quay lại
    </button>
  );
}

export default GoBack;