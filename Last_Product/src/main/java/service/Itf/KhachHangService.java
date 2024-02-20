package service.Itf;

import object.KhachHang;
import object.Response;

public interface KhachHangService {
    final  static int PAGESIZE = 5;
    public Response dangKiTaiKhoan(KhachHang khachHang) ;
    public Response dangNhap(String tenDangNhap , String matKhau) ;
    public Response dangNhapTuCachNguoiQuanTri(String tenDangNhap ,String matKhau) ;
    public Response suaTaiKhoan(KhachHang khachHang) ;
    public Response xoaTaiKhoan(KhachHang khachHang) ;
    public Response layDanhSachTaiKhoan(int index) ;
    public Response timKiemTaiKhoan(String tenDangNhap) ;
    public Response timKiemTaiKhoan(String tenKhachHang , int index) ;
    public int soTaiKhoanTimThay(String tenKhachHang) ;
    public Response theoDoi(KhachHang o1 , KhachHang o2) ;
    public Response xuLiTrang(int maxPage) ;
    public Response xemNguoiTheoDoi(KhachHang khachHang) ;
}
