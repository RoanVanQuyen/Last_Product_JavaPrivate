package dataAccess.IntefaceDAO;

import object.BaiViet;
import object.BaoCao;

import java.util.List;

public interface BaoCaoDAO extends DAO<BaoCao> {
    static final int PAGE_SIZE = 5 ;
    List<BaoCao> findAll(int index);
    int soBaoCaoHienCo() ;
    int soBaoCao(BaiViet baiViet) ;

}
