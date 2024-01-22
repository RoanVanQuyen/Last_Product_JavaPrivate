package object;

import lombok.*;

import java.time.LocalDateTime;
@AllArgsConstructor @NoArgsConstructor
@Setter @Getter @ToString @Builder
public class BaiVietYeuThich {
    private KhachHang tenDangNhap ;
    private BaiViet maBaiViet ;
    @Builder.Default
    private LocalDateTime ngayThich = LocalDateTime.now();
}
