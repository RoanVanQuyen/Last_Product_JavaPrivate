package dataAccess.IntefaceDAO;

import object.KhachHang;

import java.util.List;

public interface KhachHangDAO extends DAO<KhachHang>{
    public static final int PAGE_SIZE = 5 ;
    List<KhachHang> findAll(int index) ;
    List<KhachHang> findByName(String t, int index) ;
    int soKhachHangTimThay(String tenKhachHang) ;
}
