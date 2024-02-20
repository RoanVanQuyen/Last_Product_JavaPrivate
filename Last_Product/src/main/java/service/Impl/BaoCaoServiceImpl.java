package service.Impl;

import dataAccess.ImplementDAO.BaiVietImpl;
import dataAccess.ImplementDAO.BaoCaoImpl;
import dataAccess.ImplementDAO.KhachHangImpl;
import dataAccess.IntefaceDAO.BaiVietDAO;
import dataAccess.IntefaceDAO.BaoCaoDAO;
import dataAccess.IntefaceDAO.KhachHangDAO;
import object.BaiViet;
import object.BaoCao;
import object.KhachHang;
import object.Response;
import service.Itf.BaoCaoService;

import java.util.List;

public class BaoCaoServiceImpl implements BaoCaoService {
    private BaoCaoDAO baoCaoDAO = new BaoCaoImpl() ;
    private BaiVietDAO baiVietDAO = new BaiVietImpl() ;
    private KhachHangDAO khachHangDAO = new KhachHangImpl() ;
    @Override
    public Response themBaoCao(BaiViet baiViet , KhachHang khachHang) {
        BaoCao baoCao = BaoCao.builder()
                .baiViet(baiViet)
                .khachHang(khachHang)
                .build() ;
        baoCaoDAO.insert(baoCao) ;
        Response response = Response.builder()
                .maLoi("200")
                .noiDung(baoCao)
                .trangThai("Thành công")
                .build() ;
        return response;
    }

    @Override
    public Response hienThiTatCaBaoCao(int index) {
        List<BaoCao> baoCaos = baoCaoDAO.findAll(index)  ;
        return  Response.builder()
                .noiDung(baoCaos)
                .build() ;
    }

    @Override
    public Response soBaoCaoHienCo() {
        int ans = baoCaoDAO.soBaoCaoHienCo();
        return Response.builder()
                .noiDung(ans)
                .build();
    }

    @Override
    public Response soBaoCaoCuaBaiViet(BaiViet baiViet) {
        int soBaoCao = baoCaoDAO.soBaoCao(baiViet);
        return Response.builder()
                .noiDung(soBaoCao)
                .build();
    }
}
