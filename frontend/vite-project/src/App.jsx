import './App.css'
import { Routes, Route, Link, Navigate } from 'react-router-dom'

// Pages
import DashBoard from './pages/DashBoard'
import Employee from './pages/Employee'
import Branch from './pages/Branch'
import LoaiHangPage from "./pages/LoaiHangPage"
import SanPhamPage from "./pages/SanPhamPage"
import KhoPage from "./pages/KhoPage"

// Auth
import Login from './components/Login'
import Register from './components/Register'

// Employee components
import EmployeeView from './components/EmployeeView'
import EmployeeDetail from './components/EmployeeDetail'
import EmployeeDelete from './components/EmployeeDelete'
import EmployeeAdd from './components/EmployeeAdd'
import EmployeeEdit from './components/EmployeeEdit'

// Branch components
import BranchView from './components/BranchView'
import BranchDetail from './components/BranchDetail'
import BranchAdd from './components/BranchAdd'
import BranchEdit from './components/BranchEdit'

// ✅ CHECK LOGIN
const isLoggedIn = () => {
  return localStorage.getItem("user") !== null;
};

// ✅ ROUTE BẢO VỆ
const PrivateRoute = ({ children }) => {
  return isLoggedIn() ? children : <Navigate to="/login" />;
};

function App() {
  return (
    <>
      {/* ✅ CHỈ HIỆN MENU KHI ĐÃ LOGIN */}
      {isLoggedIn() && (
        <nav>
          <Link to="/">Dashboard</Link> | 
          <Link to="/loaihang">Loại hàng</Link> | 
          <Link to="/sanpham">Sản phẩm</Link> | 
          <Link to="/kho">Kho</Link>
        </nav>
      )}

      <Routes>

        {/* LOGIN */}
        <Route path="/login" element={<Login />} />
        <Route path="/register" element={<Register />} />

        {/* DASHBOARD */}
        <Route path="/" element={
          <PrivateRoute>
            <DashBoard />
          </PrivateRoute>
        } />

        {/* Branch */}
        <Route path='/branch' element={
          <PrivateRoute>
            <Branch />
          </PrivateRoute>
        }>
          <Route index element={<BranchView />} />
          <Route path='list-branch' element={<BranchView />} />
          <Route path='list-branch/:id' element={<BranchDetail />} />
          <Route path='add-branch' element={<BranchAdd />} />
          <Route path='edit-branch/:id' element={<BranchEdit />} />
        </Route>

        {/* Employee */}
        <Route path='/employee' element={
          <PrivateRoute>
            <Employee />
          </PrivateRoute>
        }>
          <Route index element={<EmployeeView />} />
          <Route path='list-employee' element={<EmployeeView />} />
          <Route path='list-employee/:id' element={<EmployeeDetail />} />
          <Route path='deleted-employee' element={<EmployeeDelete />} />
          <Route path='add-employee' element={<EmployeeAdd />} />
          <Route path='edit-employee/:id' element={<EmployeeEdit />} />
        </Route>

        {/* Other pages */}
        <Route path="/loaihang" element={
          <PrivateRoute><LoaiHangPage /></PrivateRoute>
        } />
        <Route path="/sanpham" element={
          <PrivateRoute><SanPhamPage /></PrivateRoute>
        } />
        <Route path="/kho" element={
          <PrivateRoute><KhoPage /></PrivateRoute>
        } />

      </Routes>
    </>
  )
}

export default App