package dataAccess.IntefaceDAO;

import object.BinhLuan;
import object.KhachHang;

import java.util.List;

public interface BinhLuanDAO extends DAO<BinhLuan> {
    public static final int PAGE_SIZE = 3 ;
    List<BinhLuan> findAllByIdBlog(String maBaiViet , int index) ;
    List<BinhLuan> findAllByUser(String tenDangNhap , int index) ;
    List<BinhLuan> findAllByIdBlog(String maBaiViet) ;
    int soTrang(KhachHang khachHang) ;
}
