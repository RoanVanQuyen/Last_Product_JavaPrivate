package dataAccess.ImplementDAO;

import JDBC.JDBC;
import dataAccess.IntefaceDAO.BaiVietDAO;
import dataAccess.IntefaceDAO.BinhLuanDAO;
import dataAccess.IntefaceDAO.KhachHangDAO;
import object.BaiViet;
import object.BinhLuan;
import object.KhachHang;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BinhLuanImpl implements BinhLuanDAO {
    @Override
    public List<BinhLuan> findAllByIdBlog(String maBaiViet , int index) {
        List<BinhLuan> binhLuans = new ArrayList<>() ;
        try {
            Connection connection =  JDBC.getConnection() ;
            KhachHangDAO khachHangDAO = new KhachHangImpl() ;
            BaiVietDAO baiVietDAO = new BaiVietImpl() ;
            String sql  = "select * from BinhLuan where maBaiViet = ? Limit ? OFFSET ?" ;
            PreparedStatement preparedStatement = connection.prepareStatement(sql) ;
            preparedStatement.setString(1,maBaiViet);
            preparedStatement.setInt(2,PAGE_SIZE);
            preparedStatement.setInt(3,(index-1) * PAGE_SIZE);
            ResultSet resultSet= preparedStatement.executeQuery() ;
            while (resultSet.next()){
                BaiViet baiViet = baiVietDAO.findById(resultSet.getString("maBaiViet")) ;
                KhachHang khachHang = khachHangDAO.findById(resultSet.getString("tenDangNhap")) ;
                BinhLuan binhLuan = BinhLuan.builder()
                        .maBinhLuan(resultSet.getString("maBinhLuan"))
                        .noiDung(resultSet.getString("noiDung"))
                        .ngayBinhLuan(resultSet.getTimestamp("ngayBinhLuan").toLocalDateTime())
                        .maBaiViet(baiViet)
                        .tenDangNhap(khachHang)
                        .build();
                binhLuans.add(binhLuan) ;
            }
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return  binhLuans ;
    }

    @Override
    public boolean insert(BinhLuan binhLuan) {
        try {
            Connection connection = JDBC.getConnection() ;
            String sql = "INSERT INTO `BinhLuan` (`maBinhLuan`, `maBaiViet`, `tenDangNhap`, `noiDung`, `ngayBinhLuan`) VALUES (?, ? , ?, ?, ?)" ;
            PreparedStatement preparedStatement = connection.prepareStatement(sql) ;
            preparedStatement.setString(1,binhLuan.getMaBinhLuan());
            preparedStatement.setString(2,binhLuan.getMaBaiViet().getMaBaiViet());
            preparedStatement.setString(3,binhLuan.getTenDangNhap().getTenDangNhap());
            preparedStatement.setString(4, binhLuan.getNoiDung());
            preparedStatement.setTimestamp(5, Timestamp.valueOf(binhLuan.getNgayBinhLuan()));
            preparedStatement.executeUpdate() ;
            connection.close();
        } catch (SQLException e) {
            return false ;
        }
        return  true ;
    }

    @Override
    public boolean update(BinhLuan binhLuan) {
        try {
            Connection connection = JDBC.getConnection() ;
            String sql = "UPDATE `BinhLuan` SET  `noiDung` = ? , `ngayBinhLuan` = ?  WHERE `BinhLuan`.`maBinhLuan` = ?" ;
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,binhLuan.getNoiDung());
            preparedStatement.setTimestamp(2, Timestamp.valueOf(binhLuan.getNgayBinhLuan()));
            preparedStatement.setString(3, binhLuan.getMaBinhLuan());
            preparedStatement.executeUpdate() ;
            connection.close();
        } catch (SQLException e) {
            return  false ;
        }
        return  true ;
    }

    @Override
    public boolean delete(BinhLuan binhLuan) {
        try {
            Connection connection = JDBC.getConnection() ;
            String sql = "DELETE FROM BinhLuan WHERE maBinhLuan = ?" ;
            PreparedStatement preparedStatement = connection.prepareStatement(sql) ;
            preparedStatement.setString(1 , binhLuan.getMaBinhLuan());
            preparedStatement.executeUpdate() ;
            connection.close();
        } catch (SQLException e) {
            return false ;
        }
        return  true;
    }

    @Override
    public List<BinhLuan> findAllByUser(String tenDangNhap, int index) {
        List<BinhLuan> binhLuans = new ArrayList<>() ;
        try {
            Connection connection = JDBC.getConnection() ;
            KhachHangDAO khachHangDAO = new KhachHangImpl() ;
            BaiVietDAO baiVietDAO = new BaiVietImpl() ;
            String sql = "SELECT  * FROM BinhLuan WHERE tenDangNhap = ? order by ngayBinhLuan desc  limit ? OFFSET ?" ;
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,tenDangNhap);
            preparedStatement.setInt(2,PAGE_SIZE);
            preparedStatement.setInt(3,(index-1) * PAGE_SIZE);
            ResultSet resultSet = preparedStatement.executeQuery() ;
            while (resultSet.next()){
                BaiViet baiViet = baiVietDAO.findById(resultSet.getString("maBaiViet")) ;
                KhachHang khachHang = khachHangDAO.findById(resultSet.getString("tenDangNhap")) ;
                BinhLuan binhLuan = BinhLuan.builder()
                        .maBinhLuan(resultSet.getString("maBinhLuan"))
                        .noiDung(resultSet.getString("noiDung"))
                        .ngayBinhLuan(resultSet.getTimestamp("ngayBinhLuan").toLocalDateTime())
                        .maBaiViet(baiViet)
                        .tenDangNhap(khachHang)
                        .build();
                binhLuans.add(binhLuan) ;
            }
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return binhLuans ;
    }

    @Override
    public List<BinhLuan> findAllByIdBlog(String maBaiViet) {
        List<BinhLuan> binhLuans = new ArrayList<>() ;
        try {
            Connection connection = JDBC.getConnection() ;
            KhachHangDAO khachHangDAO = new KhachHangImpl() ;
            BaiVietDAO baiVietDAO = new BaiVietImpl() ;
            String sql = "SELECT  * FROM BinhLuan WHERE maBaiViet = ? " ;
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,maBaiViet);
            ResultSet resultSet = preparedStatement.executeQuery() ;
            while (resultSet.next()){
                BaiViet baiViet = baiVietDAO.findById(resultSet.getString("maBaiViet")) ;
                KhachHang khachHang = khachHangDAO.findById(resultSet.getString("tenDangNhap")) ;
                BinhLuan binhLuan = BinhLuan.builder()
                        .maBinhLuan(resultSet.getString("maBinhLuan"))
                        .noiDung(resultSet.getString("noiDung"))
                        .ngayBinhLuan(resultSet.getTimestamp("ngayBinhLuan").toLocalDateTime())
                        .maBaiViet(baiViet)
                        .tenDangNhap(khachHang)
                        .build();
                binhLuans.add(binhLuan) ;
            }
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return binhLuans ;
    }

    @Override
    public BinhLuan findById(String t) {
        return  null ;
    }

    @Override
    public int soTrang(KhachHang khachHang) {
        int ans = 0 ;
        try {
            Connection connection = JDBC.getConnection() ;
            String sql = "SELECT COUNT(*) 'soLuongBinhLuan' FROM BinhLuan where tenDangNhap = ?"  ;
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,khachHang.getTenDangNhap());
            ResultSet resultSet  = preparedStatement.executeQuery() ;
            while(resultSet.next()){
                ans = resultSet.getInt("soLuongBinhLuan") ;
            }
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return ans ;
    }
}
