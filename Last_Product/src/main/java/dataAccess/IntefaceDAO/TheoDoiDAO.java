package dataAccess.IntefaceDAO;

import object.KhachHang;
import object.Response;
import object.TheoDoi;

import java.util.List;

public interface TheoDoiDAO extends DAO<object.TheoDoi> {
    Response status(TheoDoi t) ;  // kiem tra trag thai theo doi
    void insert(KhachHang o1 , KhachHang o2)  ;
    TheoDoi findById(TheoDoi t) ;
    List<TheoDoi> layDanhSachNguoiTheoDoi(KhachHang khachHang) ;
}
