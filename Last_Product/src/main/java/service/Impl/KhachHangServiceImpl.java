package service.Impl;

import dataAccess.ImplementDAO.KhachHangImpl;
import dataAccess.ImplementDAO.TheoDoiImpl;
import dataAccess.IntefaceDAO.KhachHangDAO;
import dataAccess.IntefaceDAO.TheoDoiDAO;
import object.KhachHang;
import object.Response;
import object.TheoDoi;
import service.Itf.KhachHangService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class KhachHangServiceImpl implements KhachHangService {
    private KhachHangDAO khachHangDAO = new KhachHangImpl() ;
    @Override
    public Response dangKiTaiKhoan(KhachHang khachHang) {
        Optional<KhachHang> t = Optional.ofNullable(khachHangDAO.findById(khachHang.getTenDangNhap()));
        Response response = Response.builder()
                .maLoi("1062")
                .noiDung("Tên đăng nhập đã tồn tại")
                .trangThai("Don't OK")
                .build() ;
        if(t.isEmpty()){
            try {
                khachHangDAO.insert(khachHang);
                response = Response.builder()
                        .maLoi("200")
                        .noiDung("Đăng kí tài khoản thành công")
                        .trangThai("OK")
                        .build();
            }catch (Exception e){
                response = Response.builder()
                        .maLoi("404")
                        .noiDung("Không xác định, xin nhập lại thông tin")
                        .trangThai("Don't OK")
                        .build();
            }
        }
        return response;
    }

    @Override
    public Response timKiemTaiKhoan(String tenDangNhap) {
        Optional<KhachHang> khachHang = Optional.ofNullable(khachHangDAO.findById(tenDangNhap));
        Response response = Response.builder()
                .maLoi("404")
                .noiDung("Không tìm thấy tài khoản")
                .trangThai("Lỗi")
                .build();
        if(khachHang.isPresent()){
            response = Response.builder()
                    .maLoi("200")
                    .noiDung(khachHang.get())
                    .trangThai("Thành công")
                    .build();
        }
        return response;
    }

    @Override
    public Response dangNhap(String tenDangNhap  , String matKhau) {
        Response response = Response.builder()
                .maLoi("404")
                .noiDung("Sai thông tin đăng nhập")
                .trangThai("Lỗi")
                .build();
        Optional<KhachHang> t = Optional.ofNullable(khachHangDAO.findById(tenDangNhap));
        if(t.isPresent()){
            if(t.get().getMatKhau().equals(matKhau)){
                response = Response.builder()
                        .maLoi("200")
                        .noiDung(t.get())
                        .trangThai("Thành công")
                        .build();
            }
        }
        return response;
    }

    @Override
    public Response dangNhapTuCachNguoiQuanTri(String tenDangNhap, String matKhau) {
        Response response = Response.builder()
                .maLoi("404")
                .noiDung("Sai thông tin đăng nhập")
                .trangThai("Lỗi")
                .build();
        Optional<KhachHang> t  = Optional.ofNullable(khachHangDAO.findById(tenDangNhap)) ;
        if(t.isPresent()){
            if(t.get().getMatKhau().equals(matKhau) && t.get().getVaiTro().equals("2")){
                response = Response.builder()
                        .maLoi("200")
                        .noiDung(t.get())
                        .trangThai("Thành công")
                        .build();
            }
        }
        return response;
    }

    @Override
    public Response suaTaiKhoan(KhachHang khachHang) {
        Response response = Response.builder()
                .maLoi("404")
                .noiDung("Không thể sửa thông tin bây giờ")
                .trangThai("Lỗi")
                .build();
        boolean check = khachHangDAO.update(khachHang);
        if(check){
            khachHang = khachHangDAO.findById(khachHang.getTenDangNhap()) ;
            response = Response.builder()
                    .maLoi("200")
                    .noiDung(khachHang)
                    .trangThai("Thành công")
                    .build();
        }
        return response;
    }


    @Override
    public Response xoaTaiKhoan(KhachHang khachHang) {
        Response response = Response.builder()
                .maLoi("404")
                .noiDung("Không thể sửa thông tin bây giờ")
                .trangThai("Lỗi")
                .build();
        boolean check = khachHangDAO.delete(khachHang) ;
        if(check){
            response = Response.builder()
                    .maLoi("200")
                    .noiDung("Xoá tài khoản thành công")
                    .trangThai("Thành công")
                    .build();
        }
        return response;
    }

    @Override
    public Response layDanhSachTaiKhoan(int index) {
        List<KhachHang> khachHangs = khachHangDAO.findAll(index) ;
        Response response = Response.builder()
                .trangThai("200")
                .noiDung(khachHangs)
                .build() ;
        return response;
    }

    @Override
    public Response timKiemTaiKhoan(String tenKhachHang, int index) {
        List<KhachHang> khachHangs = new ArrayList<>() ;
        khachHangs = khachHangDAO.findByName(tenKhachHang,index) ;
        Response response = Response.builder()
                .maLoi("200")
                .noiDung(khachHangs)
                .build() ;
        return response ;
    }

    @Override
    public int soTaiKhoanTimThay(String tenKhachHang) {
        return khachHangDAO.soKhachHangTimThay(tenKhachHang) ;
    }

    @Override
    public Response theoDoi(KhachHang o1, KhachHang o2) {
        TheoDoiDAO theoDoiDAO = new TheoDoiImpl() ;
        TheoDoi theoDoi = TheoDoi.builder()
                .tenDangNhap(o1)
                .tenNguoiTheoDoi(o2)
                .build() ;
        Response response = theoDoiDAO.status(theoDoi) ;
        if(response.getMaLoi().equals("200")){
            Scanner Ip = new Scanner(System.in) ;
            System.out.println("-----Bạn đã theo dõi người dùng này, chọn 1 để huỷ theo dõi , 0 để thoát: ");
            int choose = Ip.nextInt() ;
            if(choose == 1){
                theoDoiDAO.delete(theoDoi) ;
                response = Response.builder()
                        .maLoi("400")
                        .noiDung("-------------------------------Huỷ theo dõi thành công---------------")
                        .build() ;
                return response ;
            }
            if(choose == 0) return  response;
        }
        theoDoiDAO.insert(o1,o2);
        response = Response.builder()
                .maLoi("200")
                .noiDung("-------------------------------Theo dõi thành công-------------------------------")
                .trangThai("")
                .build() ;
        return  response ;
    }
    @Override
    public Response xuLiTrang(int maxPage) {
        Scanner Ip = new Scanner(System.in) ;
        int pageIndex  ;
        System.out.printf("Danh sách các trang bài viết bạn có: <<");
        for (int i = 0; i < (maxPage + PAGESIZE-1) / PAGESIZE; i++) {
            System.out.print(i + 1);
            if (i != (maxPage + PAGESIZE-1) / PAGESIZE -1) System.out.printf(", ");
        }
        System.out.println(">>");
        if((maxPage+PAGESIZE-1) / PAGESIZE <= 1) {
            System.out.println("---------------------------Đây là trang duy nhất---------------------------");
        }
        System.out.printf("Nhập số trang bạn muốn(Nhập 0 để thoát): ");
        pageIndex = Math.min(Ip.nextInt() , (maxPage+PAGESIZE-1)/PAGESIZE) ;
        Response response = Response.builder()
                .maLoi("200")
                .noiDung(pageIndex)
                .trangThai("Thanh cong")
                .build();
        return response;
    }


    @Override
    public Response xemNguoiTheoDoi(KhachHang khachHang) {
        TheoDoiDAO theoDoiDAO = new TheoDoiImpl() ;
        List<TheoDoi> theoDois = new ArrayList<>() ;
        theoDois = theoDoiDAO.layDanhSachNguoiTheoDoi(khachHang)  ;
        Response response = Response.builder()
                .noiDung(theoDois)
                .build()  ;
        return  response ;
    }
}
