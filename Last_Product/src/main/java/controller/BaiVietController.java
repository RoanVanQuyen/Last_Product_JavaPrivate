package controller;

import dataAccess.ImplementDAO.BaiVietImpl;
import dataAccess.IntefaceDAO.BaiVietDAO;
import object.*;
import service.Impl.BaiVietServiceImpl;
import service.Impl.BaiVietYeuThichServiceImpl;
import service.Impl.BinhLuanServiceImpl;
import service.Itf.BaiVietService;
import service.Itf.BaiVietYeuThichService;
import service.Itf.BinhLuanService;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BaiVietController {
    private BaiVietService baiVietService = new BaiVietServiceImpl() ;
    public void themBaiViet(KhachHang khachHang){
        Scanner Ip = new Scanner(System.in) ;
        System.out.printf("Nhập tên bài viết: ");
        String tenBaiViet= Ip.nextLine();
        System.out.printf("Nhập nội dung bài viết: ");
        String noiDungBaiViet = Ip.nextLine()  ;
        BaiViet baiViet = BaiViet.builder()
                .tenBaiViet(tenBaiViet)
                .noiDung(noiDungBaiViet)
                .tenDangNhap(khachHang)
                .build();
        Response response = baiVietService.themBaiViet(baiViet) ;
        if(response.getMaLoi().equals("200")){
            System.out.println("Thêm bài viết thành công");
        }
        else{
            System.out.println(response.getNoiDung());
        }
    }

    public void xoaBaiViet(KhachHang khachHang){
        BaiViet baiViet = tienXuLiSuaXoa(khachHang);
        if(baiViet == null) return ;
        Response response = baiVietService.xoaBaiViet(baiViet) ;
        System.out.println(response.getNoiDung());
    }

    public BaiViet tienXuLiSuaXoa(KhachHang khachHang){
        Scanner Ip = new Scanner(System.in) ;
        int pageIndex = 1 ;
        do {
            int maxPage = (int) baiVietService.soTrang(khachHang).getNoiDung();
            List<BaiViet> baiViets = (List<BaiViet>) baiVietService.layRaDanhSachBaiVietTheoNguoiDung(khachHang, pageIndex).getNoiDung();
            int p = 0;
            for (BaiViet x : baiViets) {
                System.out.println("Bài viết số " + ++p);
                baiVietService.hienThiThongTin(x);
            }
            if (baiViets.size() == 0) {
                System.out.println("Bạn chưa có bài viết nào");
                return null;
            }
            System.out.printf("1.Hiển thị thêm bài viết\n2.Xử lí\n0.Thoát: ");
            int choose = Ip.nextInt();
            if (choose == 1) {
                baiVietService.xuLiTrang(maxPage) ;
            }
            if (choose == 2) {
                int viTriBaiViet =  viTriBaiViet(baiViets) ;
                if(viTriBaiViet == -1) return null ;
                else{
                    return  baiViets.get(viTriBaiViet-1) ;
                }
            }
            if(choose ==0) return null ;
        }while(true) ;
    }

    public void suaBaiViet(KhachHang khachHang){
        Scanner Ip = new Scanner(System.in)  ;
        BaiViet baiViet = tienXuLiSuaXoa(khachHang) ;
        if(baiViet == null ) return ;
        System.out.println("Nhập thông tin cần sửa, bỏ trống nếu không cần! ");
        System.out.printf("Nhập tên bài viết: ");
        String tenBaiViet = Ip.nextLine().trim();
        tenBaiViet = tenBaiViet.equals("") ? baiViet.getTenBaiViet() : tenBaiViet;
        System.out.printf("Nhập nội dung: ");
        String noiDung = Ip.nextLine().trim();
        noiDung = noiDung.equals("") ? baiViet.getNoiDung() : noiDung;
        BaiViet baiViet1 = BaiViet.builder()
                .maBaiViet(baiViet.getMaBaiViet())
                .tenDangNhap(baiViet.getTenDangNhap())
                .tenBaiViet(tenBaiViet)
                .noiDung(noiDung)
                .build();
        Response response = baiVietService.suaBaiViet(baiViet1);
        if (response.getMaLoi().equals("200")) {
            System.out.println("Sửa thành công bài viết");
        } else {
            System.out.println(response.getNoiDung());
        }
    }


    public List<BaiViet> layDanhSachBaiViet(int index, KhachHang khachHang){
        Response response = baiVietService.layRaDanhSachBaiViet(index) ;
        List<BaiViet> baiViets = (List<BaiViet>) response.getNoiDung();
        return  baiViets ;
    }

    public void timKiemBaiViet(String name, KhachHang khachHang){
        System.out.println("---------------------Tìm kiếm bài viết----------------------");
        Response response  = baiVietService.timKiemBaiViet(name)  ;
        List<BaiViet> baiViets = (List<BaiViet>) response.getNoiDung();
        System.out.println("Kết quả tìm kiếm cho '" + name +"' là: " + baiViets.size());
        baiVietService.xuLiBaiViet(baiViets, khachHang);
    }


    public void trangChu(KhachHang khachHang){
        Scanner Ip = new Scanner(System.in) ;
        int maxPage = baiVietService.soTrang()  ;
        int pageIndex = 1 ;
        System.out.println("----------------------------------Trang chủ--------------------------");
        List<BaiViet> baiViets = layDanhSachBaiViet(1,khachHang) ;
        do {
            baiVietService.xuLiBaiViet(baiViets,khachHang);
            pageIndex = (int) baiVietService.xuLiTrang(maxPage).getNoiDung();
            if(pageIndex < 1) break;
            baiViets = ((List<BaiViet>) baiVietService.layRaDanhSachBaiViet(pageIndex).getNoiDung());
            System.out.println("----------------------------------Trang chủ--------------------------");
        }while(pageIndex >= 1 || pageIndex <= maxPage);
    }
    public void xemDanhSachBaiViet(KhachHang khachHang){
        Scanner Ip = new Scanner(System.in) ;
        int pageIndex = 1 ;
        int maxPage = (int) baiVietService.soTrang(khachHang).getNoiDung();
        Response response ;
        do {
            System.out.println("--------------------------------Xem danh sách bài viết----------------------------");
            response = baiVietService.layRaDanhSachBaiVietTheoNguoiDung(khachHang, pageIndex);
            List<BaiViet> baiViets = (List<BaiViet>) response.getNoiDung();
            if(baiViets.size() == 0){
                System.out.println("Bạn chưa có bài viết nào");
                return;
            }
            int thuTuBaiViet  = 0 ;
            for (BaiViet x : baiViets) {
                    System.out.println("---Bài viết số " + ++thuTuBaiViet );
                    baiVietService.hienThiThongTin(x);
            }
            System.out.printf("1.Hiển thị thêm\n2.Hiển thị toàn bộ bình luận\n3.Hiển thị danh sách người thích\n0.Thoát: ");
            int choose = Ip.nextInt();

            if (choose == 0) break;

            if (choose == 1) {
                pageIndex = (int) baiVietService.xuLiTrang(maxPage).getNoiDung();
                if (pageIndex < 1) {
                     break ;
                }
            }

            if (choose == 2) {  // Hiển thị danh sách bình luận
                System.out.println("---------------------Hiển thị toàn bộ bình luận------------------");
                BinhLuanService binhLuanService = new BinhLuanServiceImpl();
                int viTriBaiViet = viTriBaiViet(baiViets);
                if (viTriBaiViet > 0) {
                   baiVietService.xemToanBoBinhLuan(baiViets.get(viTriBaiViet-1));
                }
            }

            if (choose == 3) { // hien thi danh sach nguoi thich
                System.out.println("-------------------Hiển thị danh sách người thích---------------------");
                BaiVietYeuThichService baiVietYeuThichService = new BaiVietYeuThichServiceImpl();
                int viTriBaiViet = viTriBaiViet(baiViets);
                if (viTriBaiViet != -1) {
                    baiVietService.xemDanhSachNguoiThich(baiViets.get(viTriBaiViet-1));
                }
            }
        } while (true) ;
    }

    public void xemBaiVietDaXoa(KhachHang khachHang){
        Scanner Ip = new Scanner(System.in) ;
        int maxBaiViets = (int)baiVietService.soTrangDaXoa(khachHang).getNoiDung() ;
        int pageIndex = 1 ;
        do{
            System.out.println("-------------------------Danh sách bài viết bạn đã xoá-------------------------");
            Response response = baiVietService.xemBaiVietDaXoa(khachHang,pageIndex) ;
            List<BaiViet> baiViets = (List<BaiViet>) response.getNoiDung();
            int thuTuBaiViet  = 0 ;
            for (BaiViet x : baiViets) {
                System.out.println("---Bài viết số " + ++thuTuBaiViet );
                baiVietService.hienThiThongTin(x);
            }
            System.out.print("1.Hiển thị thêm bài viết\n2.Khôi phục bài viết\n0.Thoát khỏi xem bài viết đã xoá: ");
            int choose = Ip.nextInt() ;
            if(choose == 1) {
                pageIndex = (int) baiVietService.xuLiTrang(maxBaiViets).getNoiDung();
                if(pageIndex < 1)
                    break;
            }
            if(choose == 2){
                int viTriBaiViet = viTriBaiViet(baiViets) ;
                if(viTriBaiViet <= 0){
                    break ;
                }
                Response khoiPhucBaiVietResponse = baiVietService.khoiPhucBaiVietDaXoa(baiViets.get(viTriBaiViet-1));
                if(khoiPhucBaiVietResponse.getMaLoi().equals("200")){
                    System.out.println("--------------------------Khôi phục bài viết thành công----------------------");
                }
            }

            if(choose == 0){
                break;
            }
        }while(true)  ;


    }
    private int viTriBaiViet(List<BaiViet> baiViets){
        Scanner Ip = new Scanner(System.in)  ;
        System.out.println("Lựa chọn bài viết(1-" + baiViets.size() + "), bỏ trống để huỷ");
        for (BaiViet x : baiViets) {
            System.out.println("    -" + x.getTenBaiViet());
        }
        System.out.printf("Nhập bài viết cần xử lí: ");
        String index = Ip.nextLine();
        if(index.equals("")) return -1 ;
        return Integer.parseInt(index)  ;
    }
}
