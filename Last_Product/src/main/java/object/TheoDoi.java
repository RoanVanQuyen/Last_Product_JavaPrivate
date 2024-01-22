package object;

import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString @Builder
public class TheoDoi {
    private KhachHang tenDangNhap ;
    private KhachHang tenNguoiTheoDoi ;
    @Builder.Default
    private LocalDateTime ngayTheoDoi  = LocalDateTime.now();
}
