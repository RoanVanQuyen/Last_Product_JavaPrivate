package service.Itf;

import object.BaiViet;
import object.BaiVietYeuThich;
import object.KhachHang;
import object.Response;

import java.util.List;

public interface BaiVietYeuThichService {
    static int PAGE_SIZE = 5 ;
    public Response layRaDanhSachNguoiYeuThich(BaiViet baiViet) ;
    public Response layRaDanhSachBaiVietYeuThich(KhachHang khachHang) ;
    public Response kiemTraTrangThai(BaiViet baiViet , KhachHang khachHang) ;
    public Response yeuThich(BaiViet baiViet , KhachHang khachHang) ;
    public Response xoaYeuThich(BaiViet baiViet , KhachHang khachHang) ;
    public List<BaiVietYeuThich> layRaDanhSachNguoiYeuThich(BaiViet baiViet , int index) ;
    public List<BaiVietYeuThich> layRaDanhSachBaiVietYeuThich(KhachHang khachHang , int index) ;
    public Response xuLiTrang(int maxPage) ;
    public Response soTrang(BaiViet baiViet) ;
}
