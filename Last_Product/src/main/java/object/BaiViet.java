package object;

import lombok.*;
import service.Itf.BinhLuanService;

import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.Date;
@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@ToString @Builder
public class BaiViet {
    static int id = 0;
    @Builder.Default
    private String maBaiViet  = "bv" + id++;
    private String tenBaiViet ;
    private String noiDung ;
    @Builder.Default
    private LocalDateTime ngayDang = LocalDateTime.now();
    private KhachHang tenDangNhap  ;

}
