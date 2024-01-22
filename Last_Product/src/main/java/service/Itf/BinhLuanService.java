package service.Itf;

import object.BaiViet;
import object.BinhLuan;
import object.KhachHang;
import object.Response;

public interface BinhLuanService {
    public static final int PAGE_SIZE = 3 ;
    public Response themBinhLuan(BinhLuan binhLuan) ;
    public Response xoaBinhLuan(BinhLuan binhLuan)  ;
    public Response suaBinhLuan(BinhLuan binhLuan) ;
    public Response layRaDanhSachBinhLuan(String maBaiViet , int index) ;
    public Response layRaDanhSachBinhLuan(BaiViet baiViet, int index) ;
    public void hienThiBinhLuan(BinhLuan binhLuan) ;
    public Response hienThiSoLuongBinhLuan(BaiViet baiViet) ;
    public Response vietBinhLuan(BaiViet baiViet, KhachHang khachHang) ;
    public Response layRaDanhSachBinhLuan(BaiViet baiViet)  ;
    public Response layRaDanhSachBinhLuan(KhachHang khachHang , int index) ;
    public Response soTrang(KhachHang khachHang) ;
    public Response xuLiTrang(int pageSize) ;
}
