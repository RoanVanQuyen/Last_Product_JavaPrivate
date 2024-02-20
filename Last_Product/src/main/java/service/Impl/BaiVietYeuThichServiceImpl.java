package service.Impl;

import dataAccess.ImplementDAO.BaiVietImpl;
import dataAccess.ImplementDAO.BaiVietYeuThichImpl;
import dataAccess.ImplementDAO.KhachHangImpl;
import dataAccess.IntefaceDAO.BaiVietDAO;
import dataAccess.IntefaceDAO.BaiVietYeuThichDAO;
import dataAccess.IntefaceDAO.KhachHangDAO;
import object.*;
import service.Itf.BaiVietYeuThichService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class BaiVietYeuThichServiceImpl implements BaiVietYeuThichService {
    private BaiVietYeuThichDAO baiVietYeuThichDAO = new BaiVietYeuThichImpl() ;

    @Override
    public Response layRaDanhSachBaiVietYeuThich(KhachHang khachHang) {
        List<BaiVietYeuThich> baiVietYeuThiches = baiVietYeuThichDAO.findAllByUser(khachHang) ;
        List<BaiViet> baiViets = new ArrayList<>() ;
        for(BaiVietYeuThich x : baiVietYeuThiches){
            baiViets.add(x.getMaBaiViet());
        }
        Response response = Response.builder()
                .maLoi("200")
                .noiDung(baiViets)
                .trangThai("Thành công")
                .build();
        return response ;
    }

    @Override
    public Response layRaDanhSachNguoiYeuThich(BaiViet baiViet) {
        Scanner Ip = new Scanner(System.in) ;
        int maxLike = baiVietYeuThichDAO.findAllByBlog(baiViet).size() ;
        if(maxLike == 0){
            System.out.println("-----------------Bài viết chưa có người thích-------------");
            Response response  = Response.builder()
                    .trangThai("400")
                    .noiDung(new ArrayList<BinhLuan>())
                    .trangThai("Thất bại")
                    .build();
            return response ;
        }
        System.out.printf("Số trang người yêu thích hiện có: <<");
        for(int i = 0 ; i < (maxLike + PAGE_SIZE-1) / PAGE_SIZE ; i++ ){
            System.out.print(i + 1);
            if(i+1 != (maxLike + PAGE_SIZE-1) / PAGE_SIZE) System.out.printf(",");
        }
        System.out.println(">>");
        System.out.printf("Nhập số trang bạn muốn hiển thị danh sách người yêu thích(nhập 0 để thoát): ");
        int pageIndex = Math.min(Ip.nextInt(),(maxLike + PAGE_SIZE-1) / PAGE_SIZE );
        if(pageIndex == 0) {
            Response response = Response.builder()
                    .maLoi("400")
                    .noiDung(new ArrayList<>())
                    .build() ;
            return  response ;
        }
        List<BaiVietYeuThich> baiVietYeuThiches = new ArrayList<>() ;
        baiVietYeuThiches = baiVietYeuThichDAO.findById(baiViet,pageIndex) ;
        Response response = Response.builder()
                .maLoi("200")
                .noiDung(baiVietYeuThiches)
                .trangThai("Thành công")
                .build() ;
        return  response ;
    }


    @Override
    public Response kiemTraTrangThai(BaiViet baiViet, KhachHang khachHang) {
        Response response = Response.builder()
                .maLoi("400")
                .trangThai("Thất bại")
                .noiDung("Bài viết chưa được yêu thích")
                .build() ;
        boolean check = baiVietYeuThichDAO.kiemTraTrangThai(baiViet,khachHang) ;
        if(check){
            response = Response.builder()
                    .maLoi("200")
                    .trangThai("Thành công")
                    .noiDung("Bài viết đã được yêu thích")
                    .build() ;
        }
        return response ;
    }

    @Override
    public Response yeuThich(BaiViet baiViet, KhachHang khachHang) {
        Response response = Response.builder()
                .maLoi("404")
                .trangThai("Thất bại")
                .noiDung("Bài viết này đã được yêu thích")
                .build() ;
        boolean check = baiVietYeuThichDAO.kiemTraTrangThai(baiViet , khachHang) ;
        if(!check){
            BaiVietYeuThich baiVietYeuThich = BaiVietYeuThich.builder()
                    .maBaiViet(baiViet)
                    .tenDangNhap(khachHang)
                    .build() ;
            if(baiVietYeuThichDAO.insert(baiVietYeuThich)){
                response = Response.builder()
                        .maLoi("200")
                        .noiDung("------------------------------Thành công-------------------------")
                        .trangThai("Thành công")
                        .build();
            };
        }
        return response ;
    }

    @Override
    public Response xoaYeuThich(BaiViet baiViet, KhachHang khachHang) {
        Response response = Response.builder()
                .maLoi("404")
                .trangThai("Thất bại")
                .noiDung("Chưa được xoá")
                .build() ;
        boolean check = baiVietYeuThichDAO.kiemTraTrangThai(baiViet , khachHang);
        if(check){
            BaiVietYeuThich baiVietYeuThich = BaiVietYeuThich.builder()
                    .maBaiViet(baiViet)
                    .tenDangNhap(khachHang)
                    .build() ;
            if(baiVietYeuThichDAO.delete(baiVietYeuThich)){
                response = Response.builder()
                        .maLoi("200")
                        .noiDung("Xoá thành công")
                        .trangThai("Thành công")
                        .build();
            };
        }
        return response ;
    }

    @Override
    public List<BaiVietYeuThich> layRaDanhSachNguoiYeuThich(BaiViet baiViet, int index) {
        List<BaiVietYeuThich> baiVietYeuThiches = new ArrayList<>() ;
        baiVietYeuThiches =  baiVietYeuThichDAO.findById(baiViet,index);
        return baiVietYeuThiches ;
    }

    @Override
    public List<BaiVietYeuThich> layRaDanhSachBaiVietYeuThich(KhachHang khachHang, int index) {
        List<BaiVietYeuThich> baiVietYeuThiches = new ArrayList<>() ;
        baiVietYeuThiches = baiVietYeuThichDAO.findById(khachHang , index) ;
        return  baiVietYeuThiches ;
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

    @Override
    public Response soTrang(BaiViet baiViet) {
        int ans = baiVietYeuThichDAO.getPageSize(baiViet) ;
        Response response = Response.builder()
                .noiDung(ans)
                .maLoi("200")
                .trangThai("Thành công")
                .build() ;
        return  response ;
    }
}
