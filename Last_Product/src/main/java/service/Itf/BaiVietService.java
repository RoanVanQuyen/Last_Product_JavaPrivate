package service.Itf;

import object.BaiViet;
import object.KhachHang;
import object.Response;

import java.util.List;

public interface BaiVietService {
    final  static int PAGESIZE = 3;
    public Response themBaiViet(BaiViet baiViet)  ;
    public Response xoaBaiViet(BaiViet baiViet) ;
    public Response suaBaiViet(BaiViet baiViet) ;
    public Response timKiemBaiViet(String name) ;
    public Response timKiemBaiVietTheoId(String id) ;
    public Response layRaDanhSachBaiViet(int index) ;
    public Response layRaDanhSachBaiVietTheoNguoiDung(KhachHang khachHang , int index) ;
    public void hienThiThongTin(BaiViet baiViet) ;
    public int soTrang() ;
    public Response soTrang(KhachHang khachHang) ;
    public Response xuLiTrang(int maxPage) ;
    public void xuLiBaiViet(List<BaiViet> baiVietList , KhachHang khachHang) ;
    public void xemDanhSachNguoiThich(BaiViet baiViet) ;
    public void xemToanBoBinhLuan(BaiViet baiViet) ;
}
