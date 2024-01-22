package service.Itf;

import object.KhachHang;
import object.Response;

public interface KhachHangService {
    public Response dangKiTaiKhoan(KhachHang khachHang) ;
    public Response timKiemTaiKhoan(String tenDangNhap) ;
    public Response dangNhap(String tenDangNhap , String matKhau) ;
    public Response dangNhapTuCachNguoiQuanTri(String tenDangNhap ,String matKhau) ;
    public Response suaTaiKhoan(KhachHang khachHang) ;
    public Response xoaTaiKhoan(KhachHang khachHang) ;
    public Response layDanhSachTaiKhoan(int index) ;
}
