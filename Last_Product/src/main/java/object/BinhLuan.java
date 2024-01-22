package object;

import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;
@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@ToString @Builder
public class BinhLuan {
    static  int id = 0 ;
    @Builder.Default
    private  String maBinhLuan = "bl"  + id++ ;
    private KhachHang tenDangNhap ;
    private BaiViet maBaiViet ;
    private String noiDung ;
    @Builder.Default
    private LocalDateTime ngayBinhLuan = LocalDateTime.now();
}
