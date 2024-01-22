package dataAccess.ImplementDAO;

import JDBC.JDBC;
import dataAccess.IntefaceDAO.BaiVietDAO;
import dataAccess.IntefaceDAO.BaiVietYeuThichDAO;
import dataAccess.IntefaceDAO.KhachHangDAO;
import object.BaiViet;
import object.BaiVietYeuThich;
import object.KhachHang;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BaiVietYeuThichImpl implements BaiVietYeuThichDAO {
    @Override
    public List<BaiVietYeuThich> findAllByUser(KhachHang khachHang) {
        List<BaiVietYeuThich> baiVietYeuThichList = new ArrayList<>() ;
        try {
            Connection connection = JDBC.getConnection() ;
            BaiVietDAO baiVietDAO = new BaiVietImpl() ;
            KhachHangDAO khachHangDAO = new KhachHangImpl() ;
            String sql =  "Select * FROM BaiVietYeuThich where tenDangNhap = ?" ;
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, khachHang.getTenDangNhap());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                BaiViet baiViet = baiVietDAO.findById(resultSet.getString("maBaiViet")) ;
                KhachHang t = khachHangDAO.findById(resultSet.getString("tenDangNhap"))  ;
                BaiVietYeuThich baiVietYeuThich = BaiVietYeuThich.builder()
                        .maBaiViet(baiViet)
                        .tenDangNhap(t)
                        .build() ;
                baiVietYeuThichList.add(baiVietYeuThich)  ;
            }
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return  baiVietYeuThichList ;
    }

    @Override
    public List<BaiVietYeuThich> findById(BaiViet baiViet , int index) {
        List<BaiVietYeuThich> baiVietYeuThichList = new ArrayList<>() ;
        try {
            Connection connection = JDBC.getConnection() ;
            BaiVietDAO baiVietDAO = new BaiVietImpl() ;
            KhachHangDAO khachHangDAO = new KhachHangImpl() ;
            String sql =  "Select * FROM BaiVietYeuThich where maBaiViet = ? limit  ? , ? " ;
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, baiViet.getMaBaiViet());
            preparedStatement.setInt(2,(index-1) * PAGE_SIZE ) ;
            preparedStatement.setInt(3,PAGE_SIZE);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                BaiViet baiViet01 = baiVietDAO.findById(resultSet.getString("maBaiViet")) ;
                KhachHang t = khachHangDAO.findById(resultSet.getString("tenDangNhap"))  ;
                BaiVietYeuThich baiVietYeuThich = BaiVietYeuThich.builder()
                        .maBaiViet(baiViet01)
                        .tenDangNhap(t)
                        .build() ;
                baiVietYeuThichList.add(baiVietYeuThich)  ;
            }
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return  baiVietYeuThichList ;
    }

    @Override
    public boolean insert(BaiVietYeuThich baiVietYeuThich) {
        try {
            Connection connection = JDBC.getConnection() ;
            String sql ="INSERT INTO `BaiVietYeuThich` (`tenDangNhap`, `maBaiViet`, `ngayThich`) VALUES (?, ? , ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,baiVietYeuThich.getTenDangNhap().getTenDangNhap());
            preparedStatement.setString(2,baiVietYeuThich.getMaBaiViet().getMaBaiViet());
            preparedStatement.setTimestamp(3, Timestamp.valueOf(baiVietYeuThich.getNgayThich()));
            preparedStatement.executeUpdate() ;
            connection.close();
        } catch (SQLException e) {
            return  false ;
        }
        return  true ;
    }

    @Override
    public boolean update(BaiVietYeuThich baiVietYeuThich) {

        return  false ;
    }

    @Override
    public boolean delete(BaiVietYeuThich baiVietYeuThich) {
        try {
            Connection connection = JDBC.getConnection() ;
            String sql = "DELETE FROM BaiVietYeuThich where tenDangNhap = ? and maBaiViet = ?" ;
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,baiVietYeuThich.getTenDangNhap().getTenDangNhap());
            preparedStatement.setString(2,baiVietYeuThich.getMaBaiViet().getMaBaiViet());
            preparedStatement.executeUpdate() ;
            connection.close();
        } catch (SQLException e) {
            return false ;
        }
        return true ;
    }

    @Override
    public BaiVietYeuThich findById(String t) {
        return null;
    }

    @Override
    public boolean kiemTraTrangThai(BaiViet baiViet, KhachHang khachHang) {
        try {
            Connection connection = JDBC.getConnection() ;
            String sql = "SELECT * FROM BaiVietYeuThich where maBaiViet = ? and tenDangNhap = ?" ;
            PreparedStatement preparedStatement = connection.prepareStatement(sql) ;
            preparedStatement.setString(1, baiViet.getMaBaiViet());
            preparedStatement.setString(2, khachHang.getTenDangNhap());
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                connection.close();
                return true ;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false ;
    }

    @Override
    public List<BaiVietYeuThich> findAllByBlog(BaiViet baiViet) {
        List<BaiVietYeuThich> baiVietYeuThichList = new ArrayList<>() ;
        try {
            Connection connection = JDBC.getConnection() ;
            BaiVietDAO baiVietDAO = new BaiVietImpl() ;
            KhachHangDAO khachHangDAO = new KhachHangImpl() ;
            String sql =  "Select * FROM BaiVietYeuThich where maBaiViet = ? " ;
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, baiViet.getMaBaiViet());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                BaiViet baiViet01 = baiVietDAO.findById(resultSet.getString("maBaiViet")) ;
                KhachHang t = khachHangDAO.findById(resultSet.getString("tenDangNhap"))  ;
                BaiVietYeuThich baiVietYeuThich = BaiVietYeuThich.builder()
                        .maBaiViet(baiViet01)
                        .tenDangNhap(t)
                        .build() ;
                baiVietYeuThichList.add(baiVietYeuThich)  ;
            }
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return  baiVietYeuThichList ;
    }

    @Override
    public List<BaiVietYeuThich> findById(KhachHang khachHang, int index) {
        List<BaiVietYeuThich> baiVietYeuThichList = new ArrayList<>() ;
        try {
            Connection connection = JDBC.getConnection() ;
            BaiVietDAO baiVietDAO = new BaiVietImpl() ;
            KhachHangDAO khachHangDAO = new KhachHangImpl() ;
            String sql =  "Select * FROM BaiVietYeuThich where tenDangNhap = ? limit  ? , ? " ;
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, khachHang.getTenDangNhap());
            preparedStatement.setInt(2,(index-1) * PAGE_SIZE ) ;
            preparedStatement.setInt(3,PAGE_SIZE);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                BaiViet baiViet01 = baiVietDAO.findById(resultSet.getString("maBaiViet")) ;
                KhachHang t = khachHangDAO.findById(resultSet.getString("tenDangNhap"))  ;
                BaiVietYeuThich baiVietYeuThich = BaiVietYeuThich.builder()
                        .maBaiViet(baiViet01)
                        .tenDangNhap(t)
                        .build() ;
                baiVietYeuThichList.add(baiVietYeuThich)  ;
            }
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return  baiVietYeuThichList ;
    }

    @Override
    public int getPageSize(BaiViet baiViet) {
        int ans = 0 ;
        try {
            Connection connection = JDBC.getConnection() ;
            String sql = "select count(*) 'yeuThich' from BaiVietYeuThich where maBaiViet = ?" ;
            PreparedStatement preparedStatement = connection.prepareStatement(sql) ;
            preparedStatement.setString(1, baiViet.getMaBaiViet());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                ans = resultSet.getInt("yeuThich") ;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return ans ;
    }
}
