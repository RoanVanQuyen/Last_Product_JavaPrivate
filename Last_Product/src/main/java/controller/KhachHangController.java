package controller;

import dataAccess.ImplementDAO.BaiVietImpl;
import dataAccess.IntefaceDAO.BaiVietDAO;
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
    public void dangKiTaiKhoan(){ // Đăng kí tài khoản
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
    public KhachHang dangNhap(){ // Đăng nhập tài khoản
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

    public KhachHang dangXuat(KhachHang khachHang){ // Đăng xuất tài khoản
        return  null ;
    }

    public KhachHang suaTaiKhoan(KhachHang khachHang){ // Sửa thong tin tài khoản
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
    public void thongTinTaiKhoan(KhachHang khachHang){ // Xem thông tin tài khoản
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

    public KhachHang hienThiSoNguoiTheoDoi(KhachHang khachHang){
        if(khachHang == null){
            System.out.println("Vui lòng đăng nhập");
            return null;
        }
        Response response = khachHangService.xemNguoiTheoDoi(khachHang) ;
        List<TheoDoi> theoDois = (List<TheoDoi>) response.getNoiDung();
        int pageIndex = 1 ;
        do{
            System.out.println("---------------Tổng số người theo dõi: " + theoDois.size() );
            System.out.println("----------------Danh sách người theo dõi---------------------");
            for(int i  = (pageIndex - 1) * 5 ; i < Math.min((pageIndex) * 5 , theoDois.size())  ; i++)  {
                System.out.println("Tên người dùng: " + theoDois.get(i).getTenNguoiTheoDoi().getTenKhachHang());
            }
            System.out.print("1.Hiển thị thêm\n0.Thoát khỏi xem danh sách người theo dõi: " );
            int choose = Ip.nextInt();
            if(choose == 0){
                break;
            }
            if(choose == 1){
                System.out.print("Nhập trang bạn muốn 1-" + ((theoDois.size() + 4) /5) + "), nhập 0 để thoát: ");
                pageIndex = Ip.nextInt();
                if(pageIndex == 0){
                    break;
                }
            }
        }while (true) ;
        return khachHang ;
    }

    public KhachHang xoaTaiKhoan(KhachHang khachHang){ // Xoá tài khoản từ phía khách hàng
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
            int p = 0 ;
            for(BinhLuan x:binhLuans){
                System.out.println("Bình luận số: " + ++p);
                binhLuanService.hienThiBinhLuan(x);
            }
            System.out.printf("\n1.Xoá bình luận\n2.Sửa bình luận\n3.Truy nhập bài viết\n0.Hiển thị thêm hoặc thoát: ");
            int choose = Ip.nextInt() ;
            if(choose == 0) {
                pageIndex = (int) binhLuanService.xuLiTrang(maxPage).getNoiDung();
                if (pageIndex == 0) return;
            }
            if(choose == 1){
                System.out.printf("Lựa chọn bình luận cần xoá (1-" + binhLuans.size() + "): ");
                int viTriBL = Ip.nextInt() ;
                Response responseBinhLuan = binhLuanService.xoaBinhLuan(binhLuans.get(viTriBL-1)) ;
                System.out.println(responseBinhLuan.getNoiDung());
            }
            if(choose == 2){
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
            if(choose == 3){
                BaiVietService baiVietService = new BaiVietServiceImpl() ;
                System.out.printf("Lựa chọn bài viết cần xem(1-" + binhLuans.size() + "),0 để thoát: ");
                int viTriBL = Ip.nextInt() ;
                if(viTriBL < 1){
                    continue;
                }
                Ip.nextLine() ;
                BaiViet baiViet = binhLuans.get(viTriBL-1).getMaBaiViet()  ;
                baiVietService.hienThiThongTin(baiViet);
                System.out.printf("1.Hiển thị danh sách người yêu thích\n2.Hiển thị thêm bình luận\n0.Thoát: ");
                int choose3 = Ip.nextInt();
                if(choose3 == 2){
                    baiVietService.xemToanBoBinhLuan(baiViet);
                }
                if(choose3 == 1){
                    baiVietService.xemDanhSachNguoiThich(baiViet);
                }
                if(choose3 == 0){
                    break;
                }

            }
        }while(true) ;
    }
    public void lichSuYeuThich(KhachHang khachHang){ // Xem lịch sử thích bài viết
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
            System.out.printf("1.Xoá lượt yêu thích\n2.Truy nhập bài viết\n0.Hiển thị thêm hoặc thoát: ");
            int choose = Ip.nextInt() ;
            if(choose == 1){
                System.out.print("Nhập vị trí bài viết bạn cần xoá(1-" + baiVietYeuThiches.size() + ",0 để thoát): ");
                int viTriYeuThich = Ip.nextInt() ;
                if(viTriYeuThich == 0){
                    continue;
                }
                Response responseXoaYeuThich = baiVietYeuThichService.xoaYeuThich(baiVietYeuThiches.get(viTriYeuThich-1).getMaBaiViet(), khachHang) ;
                System.out.println(responseXoaYeuThich.getNoiDung());
            }
            if(choose ==0){
                pageIndex = (int) baiVietYeuThichService.xuLiTrang(pageSize).getNoiDung();
                if(pageIndex < 1) return ;
            }
            if(choose == 2) {
                BaiVietService baiVietService = new BaiVietServiceImpl();
                System.out.printf("Lựa chọn bài viết cần xem(1-" + baiVietYeuThiches.size() + "),0 để thoát: ");
                int viTriBL = Ip.nextInt();
                if(viTriBL < 1){
                    continue;
                }
                Ip.nextLine();
                BaiViet baiViet = baiVietYeuThiches.get(viTriBL - 1).getMaBaiViet();
                baiVietService.hienThiThongTin(baiViet);
                System.out.printf("1.Hiển thị danh sách người yêu thích\n2.Hiển thị thêm bình luận\n0.Thoát: ");
                int choose3 = Ip.nextInt();
                if (choose3 == 2) {
                    baiVietService.xemToanBoBinhLuan(baiViet);
                }
                if (choose3 == 1) {
                    baiVietService.xemDanhSachNguoiThich(baiViet);
                }
                if (choose3 == 0) {
                    break;
                }
            }
        }while (true) ;
    }

    public KhachHang timKiemKhachHang(KhachHang khachHang , String tenKhachHang){
        int pageSize = khachHangService.soTaiKhoanTimThay(tenKhachHang) ;
        int pageIndex = 1 ;
        do{
            System.out.println("-------------------------------Danh sách tài khoản tìm thấy------------------------------------");
            Response response = khachHangService.timKiemTaiKhoan(tenKhachHang , pageIndex) ;
            List<KhachHang> khachHangs = (List<KhachHang>) response.getNoiDung();
            if(khachHangs.size() == 0) {
                System.out.println("-------------------------------Không tìm thấy khách hàng nào---------------------------------");
                break ;
            }
            for(KhachHang x : khachHangs){
                System.out.println("---Tên khách hàng: " + x.getTenKhachHang() + "\nĐịa chỉ email: " + x.getDiaChiEmail() + "\nNgày sinh: " + x.getNgaySinh());
            }
            System.out.print("1.Hiển thị thêm khách hàng\n2.Theo dõi khách hàng\n3.Truy cập trang cá nhân khách hàng\n0.Thoát khỏi tìm kiếm khách hàng: ");
            int choose = Ip.nextInt() ;
            if(choose == 1){
                pageIndex = (int) khachHangService.xuLiTrang(pageSize).getNoiDung();
                if(pageIndex < 1){
                    break;
                }
            }
            if(choose == 2){
                if(khachHang == null){
                    System.out.println("Vui lòng đăng nhập");
                    Ip.nextLine() ;
                    khachHang = dangNhap() ;
                    if(khachHang == null) return null ;
                }
                System.out.println("Lựa chọn người dùng(1-" + khachHangs.size()+ ")");
                int index = 0 ;
                for(KhachHang x : khachHangs){
                    System.out.println("       Khách hàng số: " + ++index);
                    System.out.println( "           " + x.getTenKhachHang());
                }
                System.out.printf("Mời bạn nhập lựa chọn , nhập 0 để thoát:  ");
                int viTriKhachHang = Ip.nextInt();
                if(viTriKhachHang < 1) break;
                Response responseTheoDoi = khachHangService.theoDoi(khachHang   , khachHangs.get(viTriKhachHang-1)) ;
                System.out.println(responseTheoDoi.getNoiDung());
            }
            if(choose == 3){
                BaiVietService baiVietService = new BaiVietServiceImpl() ;
                BaiVietDAO baiVietDAO = new BaiVietImpl() ;
                System.out.printf("Lựa chọn người dùng(1-" + khachHangs.size()+ ")");
                int index = 0 ;
                for(KhachHang x : khachHangs){
                    System.out.println("       Khách hàng số: " + ++index);
                    System.out.println( "           " + x.getTenKhachHang());
                }
                System.out.printf("Mời bạn nhập lựa chọn , nhập 0 để thoát:  ");
                int viTriKhachHang = Ip.nextInt();
                if(viTriKhachHang < 1) break;
                int blogIndex = 1 ;
                int maxPage = (int) baiVietService.soTrang(khachHangs.get(viTriKhachHang-1)).getNoiDung();
                do {
                    Response responseBlog = baiVietService.layRaDanhSachBaiVietTheoNguoiDung(khachHangs.get(viTriKhachHang - 1), blogIndex);
                    List<BaiViet> baiViets = (List<BaiViet>) responseBlog.getNoiDung();
                    baiVietService.xuLiBaiViet(baiViets,khachHang);
                    blogIndex = (int) baiVietService.xuLiTrang(maxPage).getNoiDung();
                    if(blogIndex < 1)
                    {
                        break ;
                    }
                }while (true) ;
            }
            if(choose == 0){
                break;
            }
        }while (true) ;
        return  khachHang ;
    }
}
