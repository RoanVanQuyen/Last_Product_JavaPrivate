package service.Impl;

import dataAccess.ImplementDAO.NguoiQuanTriDAOImpl;
import dataAccess.IntefaceDAO.NguoiQuanTriDAO;
import object.BaiViet;
import object.KhachHang;
import object.Response;
import service.Itf.NguoiQuanTriService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class NguoiQuanTriServiceImpl implements NguoiQuanTriService { // Tăng day2 lên 1 ngày để có thể truy vấn đến 00h00 của ngày hôm đó
    private NguoiQuanTriDAO nguoiQuanTriDAO = new NguoiQuanTriDAOImpl() ;
    @Override
    public Response thongKeBaiVietMoiTheoNgay(LocalDate day1, LocalDate day2, int index) {
        day2 = day2.plusDays(1);
        List<BaiViet> baiViets = new ArrayList<>() ;
        baiViets = nguoiQuanTriDAO.thongKeBaiVietMoiTheoNgay(day1, day2 , index) ;
        Response response = Response.builder()
                .maLoi("200")
                .noiDung(baiViets)
                .trangThai("Thành công")
                .build() ;
        return response ;
    }

    @Override
    public Response thongKeTaiKhoanMoiTheoNgay(LocalDate day1, LocalDate day2, int index) {
        day2 = day2.plusDays(1);
        List<KhachHang> khachHangs = new ArrayList<>() ;
        khachHangs = nguoiQuanTriDAO.thongKeKhachHangMoiTheoNgay(day1,day2,index) ;
        Response response= Response.builder()
                .maLoi("200")
                .noiDung(khachHangs)
                .trangThai("Thành công")
                .build() ;
        return  response ;
    }

    @Override
    public int soTrangBaiVietMoiTheoNgay(LocalDate day1, LocalDate day2) {
        day2 = day2.plusDays(1);
        return nguoiQuanTriDAO.soTrangBaiViet(day1,day2);
    }

    @Override
    public int soTrangTaiKhoanMoiTheoNgay(LocalDate day1, LocalDate day2) {
        day2 = day2.plusDays(1);
        return nguoiQuanTriDAO.soTrangTaiKhoan(day1,day2) ;
    }

    @Override
    public Response xuLiTrang(int maxPage) {
        Scanner Ip = new Scanner(System.in) ;
        int pageIndex  ;
        System.out.printf("Danh sách các trang bạn có: <<");
        for (int i = 0; i < (maxPage + PAGE_SIZE-1) / PAGE_SIZE; i++) {
            System.out.print(i + 1);
            if (i != (maxPage + PAGE_SIZE-1) / PAGE_SIZE -1) System.out.printf(", ");
        }
        System.out.println(">>");
        if((maxPage+PAGE_SIZE-1) / PAGE_SIZE <= 1) {
            System.out.println("---------------------------Đây là trang duy nhất---------------------------");
        }
        System.out.printf("Nhập số trang bạn muốn(Nhập 0 để thoát): ");
        pageIndex = Math.min(Ip.nextInt() , (maxPage+PAGE_SIZE-1)/PAGE_SIZE) ;
        Response response = Response.builder()
                .maLoi("200")
                .noiDung(pageIndex)
                .trangThai("Thanh cong")
                .build();
        return response;
    }
}
