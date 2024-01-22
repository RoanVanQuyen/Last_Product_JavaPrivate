package object;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Scanner;


@AllArgsConstructor @NoArgsConstructor
@Getter @Setter
@ToString @Builder
public class KhachHang {
    private String tenDangNhap ;
    private String matKhau ;
    private String tenKhachHang ;
    private String diaChiEmail  ;
    private LocalDate ngaySinh  ;
    @Builder.Default
    private LocalDateTime ngayTaoTaiKhoan = LocalDateTime.now();
    @Builder.Default
    private String vaiTro = "1";
    public void dangKiTaiKhoan(){
        Scanner Ip = new Scanner(System.in) ;
        System.out.print("Nhập tên đăng nhập: ");
        tenDangNhap = Ip.nextLine().trim() ;
        System.out.print("Nhập mật khẩu: ");
        matKhau = Ip.nextLine().trim() ;
        System.out.printf("Nhập lại mật khẩu: ");
        String nhapLaiMatKhau = Ip.nextLine().trim() ;
        while(!matKhau.equals(nhapLaiMatKhau)){
            System.out.printf("Mật khẩu nhập lại không khớp, xin vui lòng nhập lại: ");
            nhapLaiMatKhau = Ip.nextLine() ;
        }
        System.out.print("Nhập tên người dùng: ");
        tenKhachHang = Ip.nextLine().trim() ;
        System.out.printf("Nhập địa chỉ email: ");
        diaChiEmail = Ip.nextLine().trim() ;
        System.out.printf("Nhập ngày tháng năm sinh yy-mm-dd: ");
        String dob = Ip.nextLine().trim() ;
        ngaySinh =  dob.equals("") ? LocalDate.now() : LocalDate.parse(dob);
    }

    public String xacThucVaiTro(){
        String s = vaiTro.equals("1") ? "Người dùng" : "Quản trị";
        return s;
    }
    public void hienThiThongTinTaiKhoan(){
        System.out.println("    - Tên đăng nhập: " + tenDangNhap + "\nMật khẩu: " + matKhau + "\nTên khách hàng: " + tenKhachHang
        +"\nĐịa chỉ email: " + diaChiEmail + "\nNgày sinh: " + ngaySinh + "\nVai trò: " + xacThucVaiTro()
        );
    }
}
