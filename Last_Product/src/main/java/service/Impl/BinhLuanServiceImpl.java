package service.Impl;

import dataAccess.ImplementDAO.BinhLuanImpl;
import dataAccess.IntefaceDAO.BinhLuanDAO;
import object.BaiViet;
import object.BinhLuan;
import object.KhachHang;
import object.Response;
import service.Itf.BaiVietService;
import service.Itf.BinhLuanService;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BinhLuanServiceImpl implements BinhLuanService {
    private BinhLuanDAO binhLuanDAO = new BinhLuanImpl() ;
    private BaiVietService baiVietService = new BaiVietServiceImpl() ;
    @Override
    public Response themBinhLuan(BinhLuan binhLuan) {
        Response response = Response.builder()
                .maLoi("400")
                .noiDung("Không thể thêm bình luận")
                .trangThai("Thất bại")
                .build();
        boolean check = binhLuanDAO.insert(binhLuan);
        if(check){
            response = Response.builder()
                    .maLoi("200")
                    .noiDung(binhLuan)
                    .trangThai("Thành công")
                    .build() ;
        }
        return response;
    }

    @Override
    public Response xoaBinhLuan(BinhLuan binhLuan) {
        Response response = Response.builder()
                .maLoi("400")
                .noiDung("Không thể xoá bình luận")
                .trangThai("Thất bại")
                .build();
        boolean check = binhLuanDAO.delete(binhLuan) ;
        if(check){
            response = Response.builder()
                    .maLoi("200")
                    .noiDung("Xoá thành công")
                    .trangThai("Thành công")
                    .build() ;
        }
        return response;
    }

    @Override
    public Response suaBinhLuan(BinhLuan binhLuan) {
        Response response = Response.builder()
                .maLoi("400")
                .noiDung("Không thể sửa bình luận")
                .trangThai("Thất bại")
                .build();
        boolean check = binhLuanDAO.update(binhLuan) ;
        if(check){
            response = Response.builder()
                    .maLoi("200")
                    .noiDung(binhLuan)
                    .trangThai("Thành công")
                    .build() ;
        }
        return response;
    }

    @Override
    public Response layRaDanhSachBinhLuan(String maBaiViet , int index) {
        List<BinhLuan> binhLuans = new ArrayList<>() ;
        binhLuans = binhLuanDAO.findAllByIdBlog(maBaiViet , index) ;
        Response response  = Response.builder()
                .noiDung(binhLuans)
                .build() ;
        return response;
    }

    @Override
    public Response hienThiSoLuongBinhLuan(BaiViet baiViet) {
        List<BinhLuan> binhLuans = binhLuanDAO.findAllByIdBlog(baiViet.getMaBaiViet()) ;
        Response response = Response.builder()
                .maLoi("200")
                .noiDung(binhLuans.size())
                .trangThai("Thành công")
                .build() ;
        return response ;
    }

    @Override
    public Response layRaDanhSachBinhLuan(BaiViet baiViet, int index) {
        return layRaDanhSachBinhLuan(baiViet.getMaBaiViet() , index) ;
    }


    @Override
    public void hienThiBinhLuan(BinhLuan binhLuan) {
            System.out.printf("      ");
            System.out.println("-Người bình luận: " + binhLuan.getTenDangNhap().getTenKhachHang() + "\n        Tên bài viết: " + binhLuan.getMaBaiViet().getTenBaiViet() +  "\n        Nội dung: " + binhLuan.getNoiDung() +  "\n        Ngày bình luận: " + binhLuan.getNgayBinhLuan());
    }

    @Override
    public Response vietBinhLuan(BaiViet baiViet, KhachHang khachHang) {
        Scanner Ip = new Scanner(System.in)  ;
        System.out.printf("Nội dung bình luận: ");
        String noiDung = Ip.nextLine() ;
        BinhLuan binhLuan = BinhLuan.builder()
                .noiDung(noiDung)
                .maBaiViet(baiViet)
                .tenDangNhap(khachHang)
                .build();
        Response response = themBinhLuan(binhLuan) ;
        return  response  ;
    }

    public Response layRaDanhSachBinhLuan(BaiViet baiViet){
        Scanner Ip = new Scanner(System.in) ;
        int maxComments = binhLuanDAO.findAllByIdBlog(baiViet.getMaBaiViet()).size() ;
        if(maxComments == 0) {
            System.out.println("-------------------------Bài viết chưa có bình luận nào---------------------");
            Response response  = Response.builder()
                    .maLoi("400")
                    .noiDung(new ArrayList<BinhLuan>())
                    .trangThai("Thất bại")
                    .build();
            return response ;
        }
        int pageIndex ;
        System.out.printf("Số trang bình luận hiện có: <<");
        for(int i = 0 ; i < (maxComments+PAGE_SIZE-1)/PAGE_SIZE ; i++){
            System.out.print(i + 1);
            if(i + 1 != (maxComments+PAGE_SIZE-1)/PAGE_SIZE) System.out.printf(",");
        }
        System.out.println(">>");
        System.out.printf("Nhập số trang bạn muốn hiển thị(nhập 0 để thoát): ");
        pageIndex = Ip.nextInt() ;
        if(pageIndex == 0){
            Response response = Response.builder()
                    .maLoi("400")
                    .build() ;
            return response ;
        }
        List<BinhLuan> binhLuans = binhLuanDAO.findAllByIdBlog(baiViet.getMaBaiViet() , pageIndex) ;
        Response response = Response.builder()
                .maLoi("200")
                .noiDung(binhLuans)
                .trangThai(pageIndex + "")
                .build() ;
        return response ;
    }

    @Override
    public Response layRaDanhSachBinhLuan(KhachHang khachHang, int index) {
        List<BinhLuan> binhLuans = binhLuanDAO.findAllByUser(khachHang.getTenDangNhap(), index) ;
        Response response = Response.builder()
                .maLoi("200")
                .noiDung(binhLuans)
                .trangThai("Thành công")
                .build() ;
        return response ;
    }

    @Override
    public Response soTrang(KhachHang khachHang) {
        Response response = Response.builder()
                .maLoi("200")
                .noiDung(binhLuanDAO.soTrang(khachHang))
                .trangThai("Thanh Cong")
                .build() ;
        return  response ;
    }

    @Override
    public Response xuLiTrang(int maxPage) {
        Scanner Ip = new Scanner(System.in) ;
        int pageIndex  ;
        System.out.printf("Danh sách các trang bình luận bạn có: <<");
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
