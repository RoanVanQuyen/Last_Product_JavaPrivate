package service.Impl;

import dataAccess.ImplementDAO.KhachHangImpl;
import dataAccess.IntefaceDAO.KhachHangDAO;
import object.KhachHang;
import object.Response;
import service.Itf.KhachHangService;

import java.util.List;
import java.util.Optional;

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
}
