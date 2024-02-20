package object;

import lombok.*;

@Builder
@AllArgsConstructor @NoArgsConstructor
@Setter @Getter
public class BaoCao {
    static int id = 0;
    @Builder.Default
    private int maBaoCao = id++ ;
    private BaiViet baiViet ;
    private KhachHang khachHang ;
}
