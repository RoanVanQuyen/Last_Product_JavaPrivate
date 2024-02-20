package controller;

import object.BaiViet;
import object.BaoCao;
import object.KhachHang;
import object.Response;
import service.Impl.BaiVietServiceImpl;
import service.Impl.BaoCaoServiceImpl;
import service.Impl.KhachHangServiceImpl;
import service.Impl.NguoiQuanTriServiceImpl;
import service.Itf.BaiVietService;
import service.Itf.BaoCaoService;
import service.Itf.KhachHangService;
import service.Itf.NguoiQuanTriService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class NguoiQuanTriController {
    private KhachHangService khachHangService = new KhachHangServiceImpl() ;

    private KhachHang checkNQT(KhachHang khachHang){
        if(khachHang == null) {
            khachHang = dangNhapTuCachNguoiQuanTri(khachHang) ;
            return khachHang ;
        }
        else{
            if(!khachHang.getVaiTro().equals("2")){
                khachHang = dangNhapTuCachNguoiQuanTri(khachHang) ;
            }
        }
        return khachHang ;
    }
    public KhachHang dangKiTaiKhoanQuanTri(KhachHang khachHang){
        khachHang = checkNQT(khachHang) ;
        if(khachHang == null) {
            return  null ;
        }
        else{
            if(!khachHang.getVaiTro().equals("2")) return khachHang ;
        }
        KhachHang khachHangDangKi = new KhachHang() ;
        khachHangDangKi.dangKiTaiKhoan();
        khachHangDangKi.setVaiTro("2");
        Response response = khachHangService.dangKiTaiKhoan(khachHangDangKi) ;
        if(response.getMaLoi().equals("200")) System.out.println("Đăng kí thành công tài khoản quản trị");
        else{
            System.out.println(response.getNoiDung());
        }
        return khachHang ;
    }
    public KhachHang dangNhapTuCachNguoiQuanTri(KhachHang khachHang){
        Scanner Ip = new Scanner(System.in) ;
        System.out.println("Bỏ trống để thoát !");
        System.out.printf("Nhập tên đăng nhập: ");
        String tenDangNhap = Ip.nextLine().trim() ;
        if(tenDangNhap.equals("")) {
            System.out.println("Để có được trải nghiệp tốt nhất bạn nên đăng nhập, cảm ơn bạn ! ");
            return khachHang ;
        }
        System.out.printf("Nhập mật khẩu: ");
        String matKhau = Ip.nextLine().trim() ;
        Response response = khachHangService.dangNhapTuCachNguoiQuanTri(tenDangNhap,matKhau) ;
        if(response.getMaLoi().equals("200")) {
            System.out.println("Bạn đang sử dụng với tư cách người quản trị");
            return (KhachHang) response.getNoiDung();
        }
        else System.out.println(response.getNoiDung());
        return khachHang ;
    }

    public KhachHang xoaBaiVietNQT(KhachHang khachHang){
        khachHang = checkNQT(khachHang) ;
        if(khachHang == null) {
            return  null ;
        }
        else{
            if(!khachHang.getVaiTro().equals("2")) return khachHang ;
        }
        Scanner Ip = new Scanner(System.in) ;
        BaiVietService baiVietService = new BaiVietServiceImpl() ;
        int pageIndex = 1  ;
        do{
            int maxPage = baiVietService.soTrang() ;
            System.out.println("--------------Danh sách bài viết tại trang " + pageIndex + "----------");
            Response response = baiVietService.layRaDanhSachBaiViet(pageIndex) ;
            List<BaiViet> baiViets = (List<BaiViet>) response.getNoiDung();
            int pi = 0;
            for(BaiViet x : baiViets) {
                System.out.println("Bài viết số " + ++pi);
                baiVietService.hienThiThongTin(x);
            }
            System.out.printf("1.Lựa chọn bài viết để xoá\n0.Hiển thị thêm hoặc thoát: ");
            int choose = Ip.nextInt() ;
            if(choose == 1) {
                int p = 1 ;
                for(BaiViet x : baiViets) {
                    System.out.println("      - " + p++ + ": " + x.getTenBaiViet());
                }
                System.out.printf("Nhập số thứ tự bài viết bạn cần xoá, nhập 0 để thoát: ");
                int viTriBaiViet = Ip.nextInt() ;
                if(viTriBaiViet > 0){
                    System.out.print("Bạn vừa lựa chọn xoá bài viết có số thứ tự [" + viTriBaiViet + "] điều này sẽ không thể khôi phục\nNếu bạn chắc chắn muốn xoá ấn phím 1 ,0 để huỷ: ");
                    int check = Ip.nextInt();
                    if(check == 0){
                        continue;
                    }
                    Response xoaBaiViet = baiVietService.xoaBaiVietForNQT(baiViets.get(viTriBaiViet-1)) ;
                    System.out.println(xoaBaiViet.getNoiDung());
                }
            }else {
                pageIndex = (int) baiVietService.xuLiTrang(maxPage).getNoiDung();
                if (pageIndex < 1) break;
            }
        }while (true) ;
        return khachHang ;
    }
    public KhachHang thongKeHeThong(KhachHang khachHang){
        khachHang = checkNQT(khachHang) ;
        if(khachHang == null) {
            return  null ;
        }
        else{
            if(!khachHang.getVaiTro().equals("2")) return khachHang ;
        }
        NguoiQuanTriService nguoiQuanTriService = new NguoiQuanTriServiceImpl() ;
        Scanner Ip = new Scanner(System.in) ;
        LocalDate day1 = LocalDate.now().minusDays(1);
        LocalDate day2 = LocalDate.now() ;
        do {
            int maxBaiViet = nguoiQuanTriService.soTrangBaiVietMoiTheoNgay(day1 , day2);
            int maxTaiKhoan = nguoiQuanTriService.soTrangTaiKhoanMoiTheoNgay(day1, day2);
            System.out.println("    - Số bài viết mới kể từ( " + day1  + " đến ngày " + day2 + " là: "  + maxBaiViet);
            System.out.println("    - Số tài khoản mới kể từ( " + day1  + " đến ngày " + day2 + " là: " + maxTaiKhoan);
            System.out.printf("1.Lựa chọn ngày tuỳ ý\n" +
                    "2.Hiển thị chi tiết về bài viết\n" +
                    "3.Hiển thị chi tiết về khách hàng\n" +
                    "0.Thoát: ");
            int choose = Ip.nextInt()  ;
            if(choose == 1){
                Ip.nextLine() ;
                System.out.printf("Nhập vào ngày bắt đầu(yy-mm-dd): ");
                String ngayBatDau = Ip.nextLine() ;
                System.out.printf("Nhập vào ngày kết thúc(yy-mm-dd): ");
                String ngayKetThuc = Ip.nextLine() ;
                try{
                    day1 = LocalDate.parse(ngayBatDau) ;
                    day2 = LocalDate.parse(ngayKetThuc) ;
                }catch (Exception e){
                    System.out.println("Định dạng ngày bạn nhập không đúng");
                }
            }
            if(choose == 2){
                int pageIndex = 1 ;
                BaiVietService baiVietService = new BaiVietServiceImpl() ;
                do{
                    List<BaiViet> baiViets = ((List<BaiViet>) nguoiQuanTriService.thongKeBaiVietMoiTheoNgay(day1, day2, pageIndex).getNoiDung()) ;
                    System.out.println("--------------Danh sách bài viết tại trang " + pageIndex + "----------");
                    for(BaiViet x : baiViets){
                        baiVietService.hienThiThongTin(x);
                    }
                    pageIndex = (int) nguoiQuanTriService.xuLiTrang(maxBaiViet).getNoiDung();
                    if(pageIndex == 0) {
                        break;
                    }
                }while(true) ;
            }
            if(choose == 3){
                int pageIndex = 1 ;
                do{
                    List<KhachHang> khachHangs = ((List<KhachHang>) nguoiQuanTriService.thongKeTaiKhoanMoiTheoNgay(day1, day2, pageIndex).getNoiDung()) ;
                    System.out.println("--------------Danh sách khách hàng tại trang " + pageIndex + "----------");
                    for(KhachHang x : khachHangs){
                        x.hienThiThongTinTaiKhoan();
                    }
                    pageIndex = (int) nguoiQuanTriService.xuLiTrang(maxTaiKhoan).getNoiDung();
                    if(pageIndex == 0) {
                        break;
                    }
                }while (true) ;
            }
            if(choose == 0){
                break;
            }
        }while (true) ;
        return  khachHang ;
    }

    public KhachHang hienThiBaoCaoBaiVietViPhamTieuChuan(KhachHang khachHang){
        khachHang = checkNQT(khachHang) ;
        if(khachHang == null) {
            return  null ;
        }
        else{
            if(!khachHang.getVaiTro().equals("2")) return khachHang ;
        }
        Scanner Ip = new Scanner(System.in) ;
        BaoCaoService baoCaoService = new BaoCaoServiceImpl() ;
        BaiVietService baiVietService = new BaiVietServiceImpl() ; // Mượn xử lsi trang của cái này ;
        int pageIndex = 1 ;
        int pageSize = (int) baoCaoService.soBaoCaoHienCo().getNoiDung();
        do{
            System.out.println("------------------------Danh sách bài viết bị báo cáo tại trang " + pageIndex + "----------------------" );
            Response response = baoCaoService.hienThiTatCaBaoCao(pageIndex) ;
            List<BaoCao> baoCaos = (List<BaoCao>) response.getNoiDung();
            int p = 0 ;
            for(BaoCao x : baoCaos){
                System.out.println("    Bài viết số " + ++p);
                System.out.println("        -Tên bài viết: " + x.getBaiViet().getTenBaiViet());
                System.out.println("        Số lượt báo cáo: " + baoCaoService.soBaoCaoCuaBaiViet(x.getBaiViet()).getNoiDung());
            }

            System.out.print("1.Xoá bài viết\n2.Truy cập bài viết\n0.Hiển thị thêm hoặc thoát: ");
            int choose = Ip.nextInt() ;

            if(choose == 1){
                int p1 = 1;
                for (BaoCao x : baoCaos) {
                    System.out.println("      - " + p1++ + ": " + x.getBaiViet().getTenBaiViet());
                }
                System.out.printf("Nhập số thứ tự bài viết bạn cần xoá, nhập 0 để thoát: ");
                int viTriBaiViet = Ip.nextInt();
                if (viTriBaiViet > 0) {
                    System.out.print("Bạn vừa lựa chọn xoá bài viết có số thứ tự [" + viTriBaiViet + "] điều này sẽ không thể khôi phục\nNếu bạn chắc chắn muốn xoá ấn phím 1 ,0 để huỷ: ");
                    int check = Ip.nextInt();
                    if(check == 0){
                        continue;
                    }
                    Response xoaBaiViet = baiVietService.xoaBaiVietForNQT(baoCaos.get(viTriBaiViet - 1).getBaiViet());
                    System.out.println(xoaBaiViet.getNoiDung());
                }
            }

            if(choose == 2){
                int p1 = 1;
                for (BaoCao x : baoCaos) {
                    System.out.println("      - " + p1++ + ": " + x.getBaiViet().getTenBaiViet());
                }
                System.out.printf("Nhập số thứ tự bài viết xem , nhập 0 để thoát: ");
                int viTriBaiViet = Ip.nextInt();
                if (viTriBaiViet > 0) {
                    System.out.println("------------------------Bài viết bạn vừa lựa chọn------------------------");
                    baiVietService.hienThiThongTin(baoCaos.get(viTriBaiViet-1).getBaiViet());
                }
            }

            if(choose == 0){
                pageIndex = (int) baiVietService.xuLiTrang(pageSize).getNoiDung();
                if(pageIndex < 1){
                    break;
                }
            }


        }while (true) ;

        return khachHang ;
    }

}
