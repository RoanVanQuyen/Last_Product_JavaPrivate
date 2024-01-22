package controller;

import object.*;
import service.Impl.BaiVietServiceImpl;
import service.Impl.BaiVietYeuThichServiceImpl;
import service.Impl.BinhLuanServiceImpl;
import service.Impl.KhachHangServiceImpl;
import service.Itf.BaiVietService;
import service.Itf.BaiVietYeuThichService;
import service.Itf.BinhLuanService;
import service.Itf.KhachHangService;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class KhachHangController {
    private final Scanner Ip = new Scanner(System.in) ;
    private KhachHangService khachHangService = new KhachHangServiceImpl() ;
    // Dang ki tai khoan
    public void dangKiTaiKhoan(){
        KhachHang khachHang = new KhachHang();
        khachHang.dangKiTaiKhoan();
        Response response = khachHangService.dangKiTaiKhoan(khachHang) ;
        if(!response.getMaLoi().equals("200")){
            System.out.println(response.getNoiDung());
        }
        else{
            System.out.println("Đăng kí tài khoản thành công");
        }
    }
    public KhachHang dangNhap(){
        System.out.println("Bỏ trống để sử dụng mà không cần tài khoản");
        System.out.printf("Nhập tên đăng nhập: ");
        String tenDangNhap = Ip.nextLine().trim() ;
        if(tenDangNhap.equals("")) {
            System.out.println("Để có được trải nghiệp tốt nhất bạn nên đăng nhập, cảm ơn bạn ! ");
            return null ;
        }
        System.out.printf("Nhập mật khẩu: ");
        String matKhau = Ip.nextLine().trim() ;
        Response response= khachHangService.dangNhap(tenDangNhap, matKhau) ;
        if(response.getMaLoi().equals("200")) {
            System.out.println("Đăng nhập thành công");
            return (KhachHang) response.getNoiDung();
        }
        else{
            System.out.println(response.getNoiDung());
        }
        return  null ;
    }

    public KhachHang dangXuat(KhachHang khachHang){
        return  null ;
    }

    public KhachHang suaTaiKhoan(KhachHang khachHang){
        if(khachHang == null){
            System.out.println("Vui lòng đăng nhập");
            return null ;
        }
        System.out.println("Nhập thông tin tài khoản , bỏ trống thông tin không có nhu cầu sửa! ");
        System.out.printf("Nhập tên người dùng: ");
        String tenKhachHang = Ip.nextLine() ;
        tenKhachHang = tenKhachHang.equals("") ? khachHang.getTenKhachHang() : tenKhachHang ;
        System.out.printf("Nhập địa chỉ email: ");
        String diaChiEmail = Ip.nextLine() ;
        diaChiEmail = diaChiEmail.equals("") ? khachHang.getDiaChiEmail() : diaChiEmail ;
        System.out.printf("Nhập vào mật khẩu: ");
        String matKhau = Ip.nextLine() ;
        System.out.printf("Nhập lại mật khẩu: ");
        String nhapLaiMatKhau = Ip.nextLine();
        while(!matKhau.equals(nhapLaiMatKhau)){
            System.out.printf("Mật khẩu nhập lại sai, vui lòng nhập lại mật khẩu: ");
            nhapLaiMatKhau = Ip.nextLine() ;
        }
        matKhau = matKhau.equals("") ? khachHang.getMatKhau() : matKhau ;
        System.out.printf("Nhập vào ngày sinh yy-mm-dd: ");
        String dob = Ip.nextLine() ;
        LocalDate ngaySinh = khachHang.getNgaySinh();
        ngaySinh = dob.equals("") ? khachHang.getNgaySinh() : LocalDate.parse(dob) ;
        khachHang = KhachHang.builder()
                .tenDangNhap(khachHang.getTenDangNhap())
                .tenKhachHang(tenKhachHang)
                .matKhau(matKhau)
                .diaChiEmail(diaChiEmail)
                .ngaySinh(ngaySinh)
                .build();
        Response response = khachHangService.suaTaiKhoan(khachHang) ;
        if(response.getMaLoi().equals("200")){
            System.out.println("Sửa thông tin tài khoản thành công");
            return (KhachHang) response.getNoiDung();
        }
        else {
            System.out.println(response.getNoiDung());
        }
        return khachHang ;
    }
    public void thongTinTaiKhoan(KhachHang khachHang){
        if(khachHang == null){
            System.out.println("Vui lòng đăng nhập");
            return ;
        }
        System.out.printf("Nhập mật khẩu để xem thông tin tài khoản: ");
        String matKhau = Ip.nextLine().trim() ;
        if(khachHang.getMatKhau().equals(matKhau)){
            khachHang.hienThiThongTinTaiKhoan();
        }else{
            System.out.println("Sai mật khẩu");
        }
    }

    public KhachHang xoaTaiKhoan(KhachHang khachHang){
        if(khachHang == null){
            System.out.println("Vui lòng đăng nhập");
            return null;
        }
        Scanner Ip = new Scanner(System.in) ;
        System.out.println("Bạn chắc chắn muốn xoá tài khoản này , điều này sẽ không thể phục hồi");
        System.out.printf("1.Đồng ý \n0.Thoát: ");
        int choose = Ip.nextInt() ;
        if(choose == 1) {
            Response response = khachHangService.xoaTaiKhoan(khachHang);
            System.out.println(response.getNoiDung());
            return null ;
        }
        return khachHang ;
    }

    public void lichSuBaiViet(KhachHang khachHang){ // Lịch sử bài viết
        if(khachHang == null) {
            System.out.println("Bạn cần đăng nhập");
            return ;
        }
        BaiVietService baiVietService = new BaiVietServiceImpl() ;
        int maxPage = (int) baiVietService.soTrang(khachHang).getNoiDung();
        if(maxPage == 0){
            System.out.println("Bạn chưa có lịch sử về bài viết");
            return;
        }
        int pageIndex = 1 ;
        do {
            System.out.println("---------------------Lịch sử bài viết-----------------------");
            Response response = baiVietService.layRaDanhSachBaiVietTheoNguoiDung(khachHang , pageIndex) ;
            List<BaiViet> baiViets = (List<BaiViet>) response.getNoiDung();
            baiVietService.xuLiBaiViet(baiViets,khachHang);
            pageIndex = (int)baiVietService.xuLiTrang(maxPage).getNoiDung();
            if(pageIndex == 0) return ;
        }while (true) ;
    }


    public void lichSuBinhLuan(KhachHang khachHang){ // Lịch sử bình luận
        if(khachHang == null) {
            System.out.println("Bạn cần đăng nhập");
            return ;
        }
        BinhLuanService binhLuanService  = new BinhLuanServiceImpl() ;
        int pageIndex = 1 ;
        do{
            int maxPage =(int)binhLuanService.soTrang(khachHang).getNoiDung();
            if(maxPage == 0){
                System.out.println("Bạn chưa có lịch sử nào về bình luận");
                return;
            }
            Response response = binhLuanService.layRaDanhSachBinhLuan(khachHang,pageIndex) ;
            List<BinhLuan> binhLuans = (List<BinhLuan>) response.getNoiDung();
            System.out.println("---------------------------Lịch sử bình luận--------------------------------");
            for(BinhLuan x:binhLuans){
                binhLuanService.hienThiBinhLuan(x);
            }
            System.out.printf("\n1.Xoá bình luận\n2.Sửa bình luận\n0.Hiển thị thêm hoặc thoát: ");
            int choose = Ip.nextInt() ;
            if(choose == 0) {
                pageIndex = (int) binhLuanService.xuLiTrang(maxPage).getNoiDung();
                if (pageIndex == 0) return;
            }
            if(choose == 2){
                System.out.printf("Lựa chọn bình luận cần xoá (1-" + binhLuans.size() + "): ");
                int viTriBL = Ip.nextInt() ;
                Response responseBinhLuan = binhLuanService.xoaBinhLuan(binhLuans.get(viTriBL-1)) ;
                System.out.println(responseBinhLuan.getNoiDung());
            }
            if(choose == 3){
                System.out.printf("Lựa chọn bình luận cần sửa (1-" + binhLuans.size() + "): ");
                int viTriBL = Ip.nextInt() ;
                Ip.nextLine() ;
                System.out.printf("Nhập nội dung bình luận: ");
                String noiDungBinhLuan = Ip.nextLine() ;
                BinhLuan binhLuan = BinhLuan.builder()
                        .maBinhLuan(binhLuans.get(viTriBL-1).getMaBinhLuan())
                        .tenDangNhap(binhLuans.get(viTriBL-1).getTenDangNhap())
                        .maBaiViet(binhLuans.get(viTriBL-1).getMaBaiViet())
                        .noiDung(noiDungBinhLuan)
                        .build();
                Response responseSuaBinhLuan = binhLuanService.suaBinhLuan(binhLuan) ;
                if(responseSuaBinhLuan.getMaLoi().equals("200")){
                    System.out.println("Sửa thành công bình luận");
                }
                else
                    System.out.println(responseSuaBinhLuan.getNoiDung());
            }
        }while(true) ;
    }
    public void lichSuYeuThich(KhachHang khachHang){
        if(khachHang == null) {
            System.out.println("Bạn cần đăng nhập");
            return ;
        }
        BaiVietYeuThichService baiVietYeuThichService = new BaiVietYeuThichServiceImpl() ;
        Response baiVietYeuThichAll= baiVietYeuThichService.layRaDanhSachBaiVietYeuThich(khachHang) ;
        List<BaiVietYeuThich>  baiVietYeuThiches = (List<BaiVietYeuThich>) baiVietYeuThichAll.getNoiDung();
        int pageSize = baiVietYeuThiches.size() ;
        if(pageSize == 0){
            System.out.println("Bạn chưa có lịch sử nào về việc yêu thích");
            return ;
        }
        int pageIndex = 1 ;
        do{
            System.out.println("-----------------------Lịch sử yêu thích bài viết của bạn----------------------");
            baiVietYeuThiches = baiVietYeuThichService.layRaDanhSachBaiVietYeuThich(khachHang , pageIndex) ;
            int p = 0 ;
            for(BaiVietYeuThich x : baiVietYeuThiches){
                System.out.println("    - " + ++p + ". Tên bài viết: " + x.getMaBaiViet().getTenBaiViet());
            }
            System.out.printf("1.Xoá lượt yêu thích\n0.Hiển thị thêm hoặc thoát: ");
            int choose = Ip.nextInt() ;
            if(choose == 1){
                System.out.print("Nhập vị trí bài viết bạn cần xoá(1-" + baiVietYeuThiches.size() + ",bỏ trống để huỷ): ");
                int viTriYeuThich = Ip.nextInt() ;
                Response responseXoaYeuThich = baiVietYeuThichService.xoaYeuThich(baiVietYeuThiches.get(viTriYeuThich-1).getMaBaiViet(), khachHang) ;
                System.out.println(responseXoaYeuThich.getNoiDung());
            }
            if(choose ==0){
                pageIndex = (int) baiVietYeuThichService.xuLiTrang(pageSize).getNoiDung();
                if(pageIndex < 1) return ;
            }
        }while (true) ;
    }
}
