package dataAccess.IntefaceDAO;

import object.BaiViet;
import object.KhachHang;

import java.time.LocalDate;
import java.util.List;

public interface NguoiQuanTriDAO {
    static  final int PAGE_SIZE =3 ;
    public List<KhachHang> thongKeKhachHangMoiTheoNgay(LocalDate day1 , LocalDate day2 , int index) ;
    public List<BaiViet> thongKeBaiVietMoiTheoNgay(LocalDate day1 , LocalDate day2 , int index) ;
    public int soTrangBaiViet(LocalDate day1 , LocalDate day2) ;
    public int soTrangTaiKhoan(LocalDate day1, LocalDate day2) ;
}
