package org.example;

import controller.BaiVietController;
import controller.BinhLuanController;
import controller.KhachHangController;
import controller.NguoiQuanTriController;
import dataAccess.ImplementDAO.BaiVietImpl;
import dataAccess.ImplementDAO.KhachHangImpl;
import dataAccess.IntefaceDAO.BaiVietDAO;
import dataAccess.IntefaceDAO.KhachHangDAO;
import object.BaiViet;
import object.BinhLuan;
import object.KhachHang;
import object.Response;
import service.Impl.BaiVietServiceImpl;
import service.Impl.BinhLuanServiceImpl;
import service.Impl.KhachHangServiceImpl;
import service.Itf.BaiVietService;
import service.Itf.BinhLuanService;
import service.Itf.KhachHangService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    static Scanner Ip = new Scanner(System.in)  ;
    static KhachHangController khachHangController = new KhachHangController() ;
    static BaiVietController baiVietController = new BaiVietController() ;
    static void tinhNang(KhachHang khachHang) throws  Exception{
        int choose;
        do {
            System.out.print("----------------------------------Menu--------------------------\n1.Trang chủ\n2.Tìm kiếm bài viết\n3.Quản lí bài viết\n4.Lịch sử hoạt động\n5.Tài khoản\n6.Sử dụng với tư cách người quản trị\n0.Dừng chương trình: ");
            choose = Ip.nextInt();
            if (choose == 1) trangChu(khachHang);
            if (choose == 2) timKiemBaiViet(khachHang);
            if (choose == 3) khachHang = quanLiBaiViet(khachHang);
            if (choose == 4) khachHang = lichSuHoatDong(khachHang);
            if (choose == 5) khachHang = taiKhoan(khachHang);
            if (choose == 6) khachHang = nguoiQuanTri(khachHang) ;
        } while (choose > 0);
    }
    static void trangChu(KhachHang khachHang){
        BaiVietController baiVietController = new BaiVietController();
        baiVietController.trangChu(khachHang);
    }
    static void timKiemBaiViet(KhachHang khachHang){
        Ip.nextLine() ;
        System.out.printf("Tên bài viết muốn tìm kiếm(bỏ trống để huỷ): ");
        String name = Ip.nextLine().trim() ;
        if(name.equals("")) return ;
        baiVietController.timKiemBaiViet(name, khachHang);
    }

    static KhachHang quanLiBaiViet(KhachHang khachHang){
        if(khachHang ==null){
            System.out.println("Vui lòng đăng nhập");
            khachHang = khachHangController.dangNhap() ;
            if(khachHang == null) return null ;
        }
        int choose ;
        do{
            System.out.println("----------------------------------Quản lí bài viết--------------------------");
            System.out.printf("1.Thêm bài viết \n2.Xoá bài viết\n3.Sửa bài viết\n4.Xem danh sách bài viết\n0.Thoát: ");
            choose=Ip.nextInt() ;
            if(choose == 1) baiVietController.themBaiViet(khachHang);
            if(choose == 2) baiVietController.xoaBaiViet(khachHang);
            if(choose == 3) baiVietController.suaBaiViet(khachHang);
            if(choose == 4) baiVietController.xemDanhSachBaiViet(khachHang);
            if(choose == 0) return khachHang ;
        }while(true) ;
    }

    static KhachHang lichSuHoatDong(KhachHang khachHang){
        if(khachHang == null){
            System.out.println("Vui lòng đăng nhập");
            khachHang = khachHangController.dangNhap() ;
            if(khachHang == null) return null ;
        }
        do{
            System.out.printf("1.Lịch sử bài viết\n2.Lịch sử bình luận\n3.Lịch sử yêu thích\n0.Thoát: ");
            int choose = Ip.nextInt() ;
            if(choose == 1) khachHangController.lichSuBaiViet(khachHang);
            if(choose == 2) khachHangController.lichSuBinhLuan(khachHang);
            if(choose == 3) khachHangController.lichSuYeuThich(khachHang);
            if(choose == 0) break;
        }while(true) ;
        return  khachHang ;
    }

    static KhachHang taiKhoan(KhachHang khachHang){
        KhachHang ans = khachHang ;
        int choose;
        do{
            khachHang = ans ;
            System.out.println("-----------------------------------Tài khoản--------------------------");
            if(khachHang == null)
                System.out.printf("1.Đăng nhập\n2.Đăng kí\n3.Hiển thị thông tin tài khoản\n4.Cập nhập tài khoản\n5.Xoá tài khoản\n0.Thoát: ");
            else{
                System.out.printf("1.Đăng xuất\n2.Đăng kí\n3.Hiển thị thông tin tài khoản\n4.Cập nhập tài khoản\n5.Xoá tài khoản\n0.Thoát: ");
            }
            choose = Ip.nextInt() ;
            Ip.nextLine() ;
            if(choose == 1){
                if(khachHang == null)
                    ans = khachHangController.dangNhap() ;
                else{
                    ans = khachHangController.dangXuat(khachHang)   ;
                    System.out.println("Đăng xuất thành công");
                }
            }
            if(choose == 2) khachHangController.dangKiTaiKhoan();
            if(choose == 3) khachHangController.thongTinTaiKhoan(khachHang);
            if(choose == 4) ans = khachHangController.suaTaiKhoan(khachHang) ;
            if(choose == 5) ans = khachHangController.xoaTaiKhoan(khachHang) ;
            if(choose == 0) {
                return ans ;
            }
        }while(true) ;
    }

    public static KhachHang nguoiQuanTri(KhachHang khachHang){
        NguoiQuanTriController nguoiQuanTriController = new NguoiQuanTriController() ;
        Optional<KhachHang> t = Optional.ofNullable(khachHang);
        if(t.isEmpty() || !t.get().getVaiTro().equals("2")){
            khachHang = nguoiQuanTriController.dangNhapTuCachNguoiQuanTri(khachHang) ;
        }
        if(khachHang == null){
            return khachHang ;
        }
        else{
            if(!khachHang.getVaiTro().equals("2")) return  khachHang ;
        }
        do {
            System.out.printf("1.Đăng kí tài khoản quản trị\n" +
                    "2.Thống kê hệ thống\n" +
                    "3.Xoá bài viết\n" +
                    "0.Thoát: ");
            int choose = Ip.nextInt();
            if (choose == 1) nguoiQuanTriController.dangKiTaiKhoanQuanTri(khachHang);
            if (choose == 2) khachHang = nguoiQuanTriController.thongKeHeThong(khachHang) ;
            if (choose == 3) khachHang = nguoiQuanTriController.xoaBaiVietNQT(khachHang) ;
            if (choose == 0) return khachHang;
        }while (true) ;
    }

    public static void main(String[] args) throws Exception {
        KhachHang khachHang = null ;
        tinhNang(khachHang);
    }
}