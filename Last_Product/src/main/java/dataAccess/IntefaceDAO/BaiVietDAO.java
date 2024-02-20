package dataAccess.IntefaceDAO;

import object.BaiViet;
import object.KhachHang;

import java.util.List;

public interface BaiVietDAO extends DAO<BaiViet>{
    public static final int PAGE_SIZE = 3 ;
    List<BaiViet> findAll(int index) ;
    List<BaiViet> findByTitle(String t) ;
    int soBaiViet();

    int soBaiViet(KhachHang khachHang) ;
    int soBaiVietDaXoa(KhachHang khachHang) ;

    List<BaiViet> findAllByUser(KhachHang khachHang , int index);

    List<BaiViet> xemDanhSachBaiVietDaXoa(KhachHang khachHang,int index) ;

    boolean deleteBaiVietForNQT(BaiViet baiViet) ;
}
