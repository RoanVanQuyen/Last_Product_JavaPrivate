package dataAccess.IntefaceDAO;

import object.BaiViet;
import object.BaiVietYeuThich;
import object.KhachHang;

import java.util.List;

public interface BaiVietYeuThichDAO extends DAO<BaiVietYeuThich> {
    static int PAGE_SIZE = 5 ;
    public List<BaiVietYeuThich> findById(KhachHang khachHang,int index) ;

    public List<BaiVietYeuThich> findById(BaiViet baiViet,int index) ;
    public boolean kiemTraTrangThai(BaiViet baiViet, KhachHang khachHang) ;
    public List<BaiVietYeuThich> findAllByBlog(BaiViet baiViet) ;
    public List<BaiVietYeuThich> findAllByUser(KhachHang khachHang) ;
    public int getPageSize(BaiViet baiViet) ;
}
