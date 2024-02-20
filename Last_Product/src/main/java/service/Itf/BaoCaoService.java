package service.Itf;

import object.BaiViet;
import object.BaoCao;
import object.KhachHang;
import object.Response;

public interface BaoCaoService {
    public Response themBaoCao(BaiViet baiViet , KhachHang khachHang) ;
    public Response hienThiTatCaBaoCao(int index) ;
    public Response soBaoCaoHienCo() ;
    public Response soBaoCaoCuaBaiViet(BaiViet baiViet) ;
}
