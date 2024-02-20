package dataAccess.ImplementDAO;

import JDBC.JDBC;
import dataAccess.IntefaceDAO.BaiVietDAO;
import dataAccess.IntefaceDAO.BaoCaoDAO;
import dataAccess.IntefaceDAO.KhachHangDAO;
import object.BaiViet;
import object.BaoCao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BaoCaoImpl implements BaoCaoDAO {
    @Override
    public boolean insert(BaoCao baoCao) {
        try {
            Connection connection = JDBC.getConnection() ;
            String sql = "INSERT INTO `BaoCao` (`maBaoCao`, `maBaiViet`, `tenDangNhap`) VALUES (?,?,?)" ;
            PreparedStatement preparedStatement = connection.prepareStatement(sql) ;
            preparedStatement.setInt(1,baoCao.getMaBaoCao());
            preparedStatement.setString(2,baoCao.getBaiViet().getMaBaiViet());
            preparedStatement.setString(3,baoCao.getKhachHang().getTenDangNhap());
            preparedStatement.executeUpdate() ;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return true ;
    }

    @Override
    public boolean update(BaoCao baoCao) {
        return false;
    }

    @Override
    public boolean delete(BaoCao baoCao) {
        return false;
    }

    @Override
    public BaoCao findById(String t) {
        return null;
    }

    @Override
    public List<BaoCao> findAll(int index) {
        List<BaoCao> baoCaos = new ArrayList<>() ;
        try {
            Connection connection =JDBC.getConnection() ;
            String sql = "SELECT * \n" +
                    "FROM BaoCao \n" +
                    "where maBaiViet in (SELECT maBaiViet \n" +
                    "                    FROM BaiViet \n" +
                    "                    WHERE tonTai = true)\n" +
                    "GROUP BY maBaiViet \n" +
                    "ORDER BY COUNT(*) \n" +
                    "DESC  limit ? , ?" ;
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,(index-1) * PAGE_SIZE);
            preparedStatement.setInt(2,PAGE_SIZE);
            ResultSet resultSet = preparedStatement.executeQuery() ;
            while (resultSet.next()){
                BaiVietDAO baiVietDAO = new BaiVietImpl() ;
                KhachHangDAO khachHangDAO = new KhachHangImpl() ;
                BaoCao baoCao = BaoCao.builder()
                        .maBaoCao(resultSet.getInt("maBaoCao"))
                        .baiViet(baiVietDAO.findById(resultSet.getString("maBaiViet")))
                        .khachHang(khachHangDAO.findById(resultSet.getString("tenDangNhap")))
                        .build() ;
                baoCaos.add(baoCao)  ;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return baoCaos ;
    }

    @Override
    public int soBaoCaoHienCo() {
        List<BaoCao> baoCaos = new ArrayList<>() ;
        try {
            Connection connection =JDBC.getConnection() ;
            String sql = "SELECT * \n" +
                    "FROM BaoCao \n" +
                    "WHERE maBaiViet in  (SELECT maBaiViet \n" +
                    "                     FROM BaiViet\n" +
                    "                     WHERE tonTai = true)\n" +
                    "GROUP BY maBaiViet" ;
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery() ;
            while (resultSet.next()){
                BaiVietDAO baiVietDAO = new BaiVietImpl() ;
                KhachHangDAO khachHangDAO = new KhachHangImpl() ;
                BaoCao baoCao = BaoCao.builder()
                        .maBaoCao(resultSet.getInt("maBaoCao"))
                        .baiViet(baiVietDAO.findById(resultSet.getString("maBaiViet")))
                        .khachHang(khachHangDAO.findById(resultSet.getString("tenDangNhap")))
                        .build() ;
                baoCaos.add(baoCao)  ;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return baoCaos.size() ;
    }

    @Override
    public int soBaoCao(BaiViet baiViet) {
        int ans = 0 ;
        try {
            Connection connection =JDBC.getConnection() ;
            String sql = "SELECT COUNT(*) 'soBaoCaoCuaBaiViet' \n" +
                    "FROM BaoCao \n" +
                    "WHERE maBaiViet in(SELECT maBaiViet\n" +
                    "                   FROM BaiViet \n" +
                    "                   WHERE maBaiViet = ?\n" +
                    "                  )\n" +
                    "GROUP BY maBaiViet " ;
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, baiViet.getMaBaiViet());
            ResultSet resultSet = preparedStatement.executeQuery() ;
            while (resultSet.next()){
                ans = resultSet.getInt("soBaoCaoCuaBaiViet") ;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return ans ;
    }
}
