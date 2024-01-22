package controller;

import object.BaiViet;
import object.BinhLuan;
import object.KhachHang;
import object.Response;
import service.Impl.BinhLuanServiceImpl;
import service.Itf.BinhLuanService;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BinhLuanController {
    private BinhLuanService binhLuanService = new BinhLuanServiceImpl();
    public void themBinhLuan(KhachHang khachHang , BaiViet baiViet){
        Scanner Ip = new Scanner(System.in)  ;
        System.out.printf("Nhập nội dung: ");
        String noiDung = Ip.nextLine() ;
        BinhLuan binhLuan = BinhLuan.builder()
                .maBaiViet(baiViet)
                .tenDangNhap(khachHang)
                .noiDung(noiDung)
                .build() ;
        Response response  = binhLuanService.themBinhLuan(binhLuan) ;
        if(response.equals("200")){
            System.out.println("Thêm bình luận thành công");
        }else{
            System.out.println(response.getNoiDung());
        }
    }

    public void hienThiBinhLuan(BaiViet baiViet, int index){
        Response response = binhLuanService.layRaDanhSachBinhLuan(baiViet , 1) ;
        List<BinhLuan> binhLuans = (List<BinhLuan>) response.getNoiDung();
        for(BinhLuan x : binhLuans){
            binhLuanService.hienThiBinhLuan(x);
        }
    }
    public void hienThiBinhLuan(BaiViet baiViet){
        Response response = binhLuanService.layRaDanhSachBinhLuan(baiViet) ;
        List<BinhLuan> binhLuans = (List<BinhLuan>) response.getNoiDung();
        for(BinhLuan x : binhLuans){
            binhLuanService.hienThiBinhLuan(x);
        }
    }

    public void xoaBinhLuan(BinhLuan binhLuan){
        Response response = binhLuanService.xoaBinhLuan(binhLuan)  ;
        if(response.getMaLoi().equals("200")){
            System.out.println("Bình luận đã được xoá");
        }else {
            System.out.println(response.getNoiDung());
        }
    }

    public void suaBinhLuan(BinhLuan binhLuan){
        Scanner Ip = new Scanner(System.in);
        System.out.printf("Nhập nội dung cần sửa: ");
        String noiDung  = Ip.nextLine()  ;
        BinhLuan newBinhLuan = BinhLuan.builder()
                .tenDangNhap(binhLuan.getTenDangNhap())
                .maBinhLuan(binhLuan.getMaBinhLuan())
                .maBaiViet(binhLuan.getMaBaiViet())
                .noiDung(noiDung)
                .build() ;
        Response response = binhLuanService.suaBinhLuan(binhLuan) ;
        if(response.getMaLoi().equals("200")){
            System.out.println("Bình luận đã được sửa đổi");
        }else{
            System.out.println(response.getNoiDung());
        }
    }
    public void hienThiSoLuongBinhLuan(BaiViet baiViet){
        Response response = binhLuanService.hienThiSoLuongBinhLuan(baiViet) ;
        System.out.println(response.getNoiDung());
    }
}
