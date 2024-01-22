package service.Itf;

import object.Response;

import java.time.LocalDate;

public interface NguoiQuanTriService {
    static final int PAGE_SIZE = 3;
    public Response thongKeBaiVietMoiTheoNgay(LocalDate day1 , LocalDate day2 , int index );
    public Response thongKeTaiKhoanMoiTheoNgay(LocalDate day1 , LocalDate day2 , int index ) ;
    public int soTrangBaiVietMoiTheoNgay(LocalDate day1 , LocalDate day2) ;
    public int soTrangTaiKhoanMoiTheoNgay(LocalDate day1 , LocalDate day2);
    public Response xuLiTrang(int maxPage) ;
}
