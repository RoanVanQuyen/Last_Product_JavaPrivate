package service.Impl;

import dataAccess.ImplementDAO.BaiVietImpl;
import dataAccess.ImplementDAO.BaiVietYeuThichImpl;
import dataAccess.IntefaceDAO.BaiVietDAO;
import dataAccess.IntefaceDAO.BaiVietYeuThichDAO;
import object.*;
import service.Itf.BaiVietService;
import service.Itf.BaiVietYeuThichService;
import service.Itf.BinhLuanService;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class BaiVietServiceImpl implements BaiVietService {
    private BaiVietDAO baiVietDAO = new BaiVietImpl() ;
    private BaiVietYeuThichDAO baiVietYeuThichDAO = new BaiVietYeuThichImpl() ;
    @Override
    public Response themBaiViet(BaiViet baiViet) {
        Response response = Response.builder()
                .maLoi("400")
                .noiDung("Không thể thêm bài viết")
                .trangThai("Lỗi")
                .build() ;
        Boolean check = baiVietDAO.insert(baiViet) ;
        if(check){
            response = Response.builder()
                    .maLoi("200")
                    .noiDung(baiViet)
                    .trangThai("Thành công")
                    .build() ;
        }
        return response;
    }


    @Override
    public Response suaBaiViet(BaiViet baiViet) {
        Response response = Response.builder()
                .maLoi("400")
                .noiDung("Không thể sửa bài viết")
                .trangThai("Lỗi")
                .build() ;
        Boolean check = baiVietDAO.update(baiViet) ;
        if(check){
            response = Response.builder()
                    .maLoi("200")
                    .noiDung(baiViet)
                    .trangThai("Thành công")
                    .build() ;
        }
        return response;
    }

    @Override
    public Response timKiemBaiViet(String name) {
        List<BaiViet> baiViets = baiVietDAO.findByTitle(name) ;
        Response response = Response.builder()
                .maLoi("200")
                .noiDung(baiViets)
                .trangThai("Thành công")
                .build() ;
        return response;
    }

    @Override
    public Response layRaDanhSachBaiViet(int index) {
        List<BaiViet> baiViets = baiVietDAO.findAll(index) ;
        Response response = Response.builder()
                .maLoi("200")
                .noiDung(baiViets)
                .trangThai("Thành công")
                .build() ;
        return response;
    }

    @Override
    public Response timKiemBaiVietTheoId(String id) { // Người quản trị
        Response response = Response.builder()
                .maLoi("404")
                .noiDung("Không có bài viét nào")
                .trangThai("Thất bại")
                .build() ;
        Optional<BaiViet> baiViet = Optional.ofNullable(baiVietDAO.findById(id));
        if(baiViet.isPresent()){
            response = Response.builder()
                    .maLoi("200")
                    .noiDung(baiViet.get())
                    .trangThai("Thành công")
                    .build() ;
        }
        return response;
    }

    @Override
    public void xuLiBaiViet(List<BaiViet> baiVietList, KhachHang khachHang) {
        BinhLuanService binhLuanService = new BinhLuanServiceImpl() ;
        if(baiVietList.size() ==0 ) return ;
        Scanner Ip = new Scanner(System.in) ;
        int choose = 0 ;
        do {
            int thuTuBaiViet  = 0 ;
            for(BaiViet x :baiVietList){
                System.out.println("---Bài viết số " + ++thuTuBaiViet );
                hienThiThongTin(x);
            }
            System.out.printf("1.Thích\n2.Bình luận bài viết\n3.Hiển thị toàn bộ bình luận\n4.Hiển thị danh sách người thích bài viết \n0.Hiển thị thêm hoặc thoát: ");
            choose = Ip.nextInt();
            if (choose == 1) { // Yêu thích bài viết
                if (khachHang == null) {
                    System.out.println("Vui lòng đăng nhập để có thể yêu thích bài viết");
                    return;
                }
                System.out.println("Lựa chọn bài viết");
                for (BaiViet x : baiVietList) {
                    System.out.println("    -" + x.getTenBaiViet());
                }
                System.out.printf("Nhập bài viết cần thích(1-" + baiVietList.size() + ",nhập 0 để thoát): ");
                int viTriBaiViet = Ip.nextInt();
                if(viTriBaiViet >  0) {
                    yeuThichBaiViet(khachHang , baiVietList.get(viTriBaiViet-1));
                }
            }
            if (choose == 2) { // Bình luận bài viết
                if (khachHang == null) {
                    System.out.println("Vui lòng đăng nhập để có thể bình luận bài viết");
                    return;
                }
                System.out.println("Lựa chọn bài viết");
                for (BaiViet x : baiVietList) {
                    System.out.println("    -" + x.getTenBaiViet());
                }
                System.out.printf("Nhập bài viết cần bình luận(1-" + baiVietList.size() + ",nhập 0 để thoát): ");
                int viTriBaiViet = Ip.nextInt();
                if(viTriBaiViet > 0) {
                    Ip.nextLine();
                    BinhLuan binhLuan = BinhLuan.builder().build();
                    Response response1 = binhLuanService.vietBinhLuan(baiVietList.get(viTriBaiViet - 1), khachHang);
                    if (response1.getMaLoi().equals("200")) System.out.println("Thêm thành công bình luận");
                    else System.out.println(response1.getNoiDung());
                }
            }
            if (choose == 3) { // xem toàn bộ bình luận bài viết
                System.out.println("Lựa chọn bài viết");
                for (BaiViet x : baiVietList) {
                    System.out.println("    -" + x.getTenBaiViet());
                }
                System.out.printf("Nhập bài viết cần xem(1-" + baiVietList.size() + ",nhập 0 để thoát): ");
                int viTriBaiViet = Ip.nextInt();
                if(viTriBaiViet > 0) {
                    xemToanBoBinhLuan(baiVietList.get(viTriBaiViet-1));
                }
            }
            if(choose == 4){ // xem danh sách người yêu thích
                BaiVietYeuThichService baiVietYeuThichService = new BaiVietYeuThichServiceImpl() ;
                System.out.println("Lựa chọn bài viết");
                for (BaiViet x : baiVietList) {
                    System.out.println("    -" + x.getTenBaiViet());
                }
                System.out.printf("Nhập bài viết cần xem danh sách người yêu thích(1-" + baiVietList.size() + ",nhập 0 để thoát): ");
                int viTriBaiViet = Ip.nextInt();
                if(viTriBaiViet > 0) {
                    xemDanhSachNguoiThich(baiVietList.get(viTriBaiViet-1));
                }
            }
            if (choose == 0) return;
        }while(true) ;
    }

    @Override
    public void xemToanBoBinhLuan(BaiViet baiViet){
        BinhLuanService binhLuanService = new BinhLuanServiceImpl() ;
        List<BinhLuan> binhLuans = (List<BinhLuan>) binhLuanService.layRaDanhSachBinhLuan(baiViet, 1).getNoiDung();
        System.out.println("-------------------------Danh sách bình luận tại trang 1--------------------");
        for (BinhLuan x : binhLuans) {
            binhLuanService.hienThiBinhLuan(x);
        }
        do {
            Response response = binhLuanService.layRaDanhSachBinhLuan(baiViet);
            if (response.getMaLoi().equals("400")) break;
            else {
                System.out.println("-------------------------Danh sách bình luận tại trang " + response.getTrangThai() +"--------------------");
                binhLuans = (List<BinhLuan>) response.getNoiDung();
                for (BinhLuan x : binhLuans) {
                    binhLuanService.hienThiBinhLuan(x);
                }
            }
        } while (true);
    }

    @Override
    public void xemDanhSachNguoiThich(BaiViet baiViet){
        BaiVietYeuThichService baiVietYeuThichService = new BaiVietYeuThichServiceImpl() ;
        int pageSize = (int) baiVietYeuThichService.soTrang(baiViet).getNoiDung();
        int pageIndex = 1 ;
        do {
            System.out.println("---------------------------Danh sách người yêu thich bài viết tại trang " + pageIndex + "---------------------------" );
            List<BaiVietYeuThich> baiVietYeuThiches = baiVietYeuThichService.layRaDanhSachNguoiYeuThich(baiViet,pageIndex) ;
            for (BaiVietYeuThich x : baiVietYeuThiches) {
                System.out.println("    - Người thích: " + x.getTenDangNhap().getTenKhachHang() + "\n           Ngày thích: " + x.getNgayThich());
            }
            pageIndex = (int) baiVietYeuThichService.xuLiTrang(pageSize).getNoiDung();
            if(pageIndex < 1) return ;
        } while (true);
    }

    public void yeuThichBaiViet(KhachHang khachHang , BaiViet baiViet){
        Scanner Ip = new Scanner(System.in) ;
        BaiVietYeuThichService baiVietYeuThichService = new BaiVietYeuThichServiceImpl();
        Response response = baiVietYeuThichService.kiemTraTrangThai(baiViet, khachHang);
        if (response.getMaLoi().equals("200")) {
            System.out.printf("Bài viết đã được yêu thích tử trước \n1.Để huỷ yêu thích bài viết này\n0.Thoát: ");
            int p = Ip.nextInt();
            if (p == 0) {
                System.out.println();
            } else {
                Response responseXoaYeuThich = baiVietYeuThichService.xoaYeuThich(baiViet, khachHang);
                System.out.println(responseXoaYeuThich.getNoiDung());
            }
        } else {
            Response responseThemYeuThich = baiVietYeuThichService.yeuThich(baiViet, khachHang);
            System.out.println(responseThemYeuThich.getNoiDung());
        }
    }

    public void hienThiThongTin(BaiViet baiViet){
        System.out.println("   - Tên bài viết: "+ baiViet.getTenBaiViet());
        String[] text =baiViet.getNoiDung().split("\s") ;
        System.out.printf("Nội dung: ");
        for(int i = 0; i < text.length  ; i++){
            if(i % 28 ==0 && i !=0) System.out.println();
            System.out.print(text[i] + " ");
        }
        System.out.println();
        System.out.println("Ngày đăng: " + baiViet.getNgayDang());
        BinhLuanService binhLuanService = new BinhLuanServiceImpl()  ;
        Response response1 = binhLuanService.hienThiSoLuongBinhLuan(baiViet) ;
        List<BaiVietYeuThich> baiVietYeuThiches = baiVietYeuThichDAO.findAllByBlog(baiViet) ;
        System.out.println("Người yêu thích: " + baiVietYeuThiches.size() );
        System.out.println("Tổng số lượng bình luận: " + response1.getNoiDung());
        Response response2 = binhLuanService.layRaDanhSachBinhLuan(baiViet.getMaBaiViet() ,1) ;
        List<BinhLuan> binhLuans = (List<BinhLuan>) response2.getNoiDung();
        for(BinhLuan x : binhLuans){
            binhLuanService.hienThiBinhLuan(x);
        }
    }

    @Override
    public int soTrang() { // trả về số trang của bài viết trong database
        int pageSize = baiVietDAO.soBaiViet() ;
        return  pageSize ;
    }

    @Override
    public Response xoaBaiViet(BaiViet baiViet) {
        boolean check = baiVietDAO.delete(baiViet) ;
        Response response = Response.builder()
                .maLoi("404")
                .noiDung("Bài viết chưa được xoá")
                .trangThai("Thất bại")
                .build();
        if(check) {
            response = Response.builder()
                    .maLoi("200")
                    .noiDung("Xoá thành công")
                    .trangThai("Thành công")
                    .build() ;
        }
        return response ;
    }

    @Override
    public Response layRaDanhSachBaiVietTheoNguoiDung(KhachHang khachHang , int index) { // trả về danh sách bài viết của người dùng
        List<BaiViet>baiViets = baiVietDAO.findAllByUser(khachHang , index) ;
        Response response = Response.builder()
                .maLoi("200")
                .noiDung(baiViets)
                .trangThai("Thành công")
                .build();
        return response ;
    }

    @Override
    public Response soTrang(KhachHang khachHang) { // Lấy ra tổng sổ trang hiện tại của khách hàng đó
        int pageSize = baiVietDAO.soBaiViet(khachHang) ;
        Response response = Response.builder()
                .maLoi("200")
                .noiDung(pageSize)
                .trangThai("Thành công")
                .build() ;
        return response ;
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
}
