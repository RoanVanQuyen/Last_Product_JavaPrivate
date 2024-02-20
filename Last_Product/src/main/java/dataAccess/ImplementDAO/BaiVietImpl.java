package dataAccess.ImplementDAO;

import JDBC.JDBC;
import dataAccess.IntefaceDAO.BaiVietDAO;
import dataAccess.IntefaceDAO.KhachHangDAO;
import object.BaiViet;
import object.KhachHang;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BaiVietImpl implements BaiVietDAO {
    static int id  = 0 ;
    @Override
    public List<BaiViet> findAll(int index) {
        List<BaiViet> baiViets = new ArrayList<>();
        try {
            Connection connection = JDBC.getConnection() ;
            String sql = "select * from BaiViet where tonTai = true order by ngayDang desc limit ? , ? ";
            PreparedStatement preparedStatement = connection.prepareStatement(sql) ;
            preparedStatement.setInt(1,((index-1) * PAGE_SIZE) );
            preparedStatement.setInt(2,PAGE_SIZE);
            ResultSet resultSet = preparedStatement.executeQuery() ;
            KhachHangDAO khachHangDAO = new KhachHangImpl() ;
            while (resultSet.next()){
                KhachHang khachHang = khachHangDAO.findById(resultSet.getString("tenDangNhap"));
                BaiViet baiViet = BaiViet.builder()
                        .maBaiViet(resultSet.getString("maBaiViet"))
                        .tenBaiViet(resultSet.getString("tenBaiViet"))
                        .noiDung(resultSet.getString("noiDung"))
                        .tenDangNhap(khachHang)
                        .ngayDang(resultSet.getTimestamp("ngayDang").toLocalDateTime())
                        .build();
                baiViets.add(baiViet);
            }
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return  baiViets ;
    }

    @Override
    public List<BaiViet> findByTitle(String t) {
        List<BaiViet> baiViets = new ArrayList<>();
        try {
            Connection connection = JDBC.getConnection() ;
            String sql = "select * from BaiViet where tenBaiViet like CONCAT('%',?,'%') and tonTai = TRUE" ;
            PreparedStatement preparedStatement = connection.prepareStatement(sql) ;
            preparedStatement.setString(1,t);
            ResultSet resultSet = preparedStatement.executeQuery() ;
            KhachHangDAO khachHangDAO = new KhachHangImpl();
            while (resultSet.next()){
                KhachHang khachHang = khachHangDAO.findById(resultSet.getString("tenDangNhap"));
                BaiViet baiViet = BaiViet.builder()
                        .maBaiViet(resultSet.getString("maBaiViet"))
                        .tenBaiViet(resultSet.getString("tenBaiViet"))
                        .noiDung(resultSet.getString("noiDung"))
                        .tenDangNhap(khachHang)
                        .ngayDang(resultSet.getTimestamp("ngayDang").toLocalDateTime())
                        .build();
                baiViets.add(baiViet);
            }
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return  baiViets ;
    }

    @Override
    public boolean insert(BaiViet baiViet) {
        try {
            Connection connection = JDBC.getConnection() ;
            String sql = "INSERT INTO `BaiViet` VALUES (?,?,?,?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql) ;
            preparedStatement.setString(1 , baiViet.getMaBaiViet() );
            preparedStatement.setString(2, baiViet.getTenBaiViet());
            preparedStatement.setString(3, baiViet.getNoiDung());
            preparedStatement.setTimestamp(4, Timestamp.valueOf(baiViet.getNgayDang()));
            preparedStatement.setString(5,baiViet.getTenDangNhap().getTenDangNhap());
            preparedStatement.setBoolean(6,baiViet.isTonTai());
            preparedStatement.executeUpdate() ;
            connection.close();
        } catch (SQLException e) {
            return  false ;
        }
        return true ;
    }

    @Override
    public boolean update(BaiViet baiViet) {
        try {
            Connection connection = JDBC.getConnection() ;
            String sql="UPDATE `BaiViet` SET `tenBaiViet` = ?, `noiDung` = ?, `ngayDang` = ? , tonTai = ? WHERE `BaiViet`.`maBaiViet` = ? " ;
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, baiViet.getTenBaiViet());
            preparedStatement.setString(2, baiViet.getNoiDung());
            preparedStatement.setTimestamp(3, Timestamp.valueOf(baiViet.getNgayDang()));
            preparedStatement.setBoolean(4 , baiViet.isTonTai());
            preparedStatement.setString(5, baiViet.getMaBaiViet());
            preparedStatement.executeUpdate() ;
            connection.close();
        } catch (SQLException e) {
            return  false ;
        }
        return  true ;
    }

    @Override
    public boolean delete(BaiViet baiViet) {
        try {
            Connection connection = JDBC.getConnection() ;
            String sql = "UPDATE BaiViet SET tonTai = false WHERE maBaiViet = ?"  ;
            PreparedStatement preparedStatement = connection.prepareStatement(sql) ;
            preparedStatement.setString(1, baiViet.getMaBaiViet());
            preparedStatement.executeUpdate() ;
            connection.close();
        } catch (SQLException e) {
            return  false ;
        }
        return  true ;
    }

    @Override
    public boolean deleteBaiVietForNQT(BaiViet baiViet) {
        try {
            Connection connection = JDBC.getConnection() ;
            String sql = "DELETE FROM BaiViet " +
                    "WHERE maBaiViet = ?" ;
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, baiViet.getMaBaiViet());
            return preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public BaiViet findById(String t) {
        BaiViet baiViet  = null;
        try {
            Connection connection = JDBC.getConnection() ;
            String sql = "select * from BaiViet where maBaiViet = ?" ;
            PreparedStatement preparedStatement = connection.prepareStatement(sql) ;
            preparedStatement.setString(1,t);
            ResultSet resultSet = preparedStatement.executeQuery() ;
            KhachHangDAO khachHangDAO = new KhachHangImpl();
            while (resultSet.next()){
                KhachHang khachHang = khachHangDAO.findById(resultSet.getString("tenDangNhap"));
                baiViet = BaiViet.builder()
                        .maBaiViet(resultSet.getString("maBaiViet"))
                        .tenBaiViet(resultSet.getString("tenBaiViet"))
                        .noiDung(resultSet.getString("noiDung"))
                        .tenDangNhap(khachHang)
                        .ngayDang(resultSet.getTimestamp("ngayDang").toLocalDateTime())
                        .build();
            }
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return  baiViet ;
    }




    @Override
    public int soBaiViet() {
        try {
            Connection connection = JDBC.getConnection();
            String sql = "select count(*)'soBaiViet' from BaiViet where tonTai = TRUE" ;
            PreparedStatement preparedStatement = connection.prepareStatement(sql) ;
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                return resultSet.getInt("soBaiViet") ;
            }
            connection.close();
        } catch (SQLException e) {
            return  -1 ;
        }
        return -1 ;
    }

    @Override
    public int soBaiViet(KhachHang khachHang) {
        try {
            Connection connection = JDBC.getConnection();
            String sql = "select count(*)'soBaiViet' from BaiViet where tenDangNhap = ? and tonTai = TRUE" ;
            PreparedStatement preparedStatement = connection.prepareStatement(sql) ;
            preparedStatement.setString(1, khachHang.getTenDangNhap());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                return resultSet.getInt("soBaiViet") ;
            }
            connection.close();
        } catch (SQLException e) {
            return  -1 ;
        }
        return -1 ;
    }

    @Override
    public List<BaiViet> findAllByUser(KhachHang khachHang, int index) {
        List<BaiViet> baiViets = new ArrayList<>() ;
        try {
            Connection connection = JDBC.getConnection() ;
            String sql = "select * from BaiViet where tenDangNhap = ? and tonTai = TRUE order by ngayDang desc limit ? , ? " ;
            PreparedStatement preparedStatement = connection.prepareStatement(sql) ;
            preparedStatement.setString(1, khachHang.getTenDangNhap());
            preparedStatement.setInt(2,(index-1) * PAGE_SIZE );
            preparedStatement.setInt(3, PAGE_SIZE);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                BaiViet baiViet = BaiViet.builder()
                        .maBaiViet(resultSet.getString("maBaiViet"))
                        .tenBaiViet(resultSet.getString("tenBaiViet"))
                        .noiDung(resultSet.getString("noiDung"))
                        .tenDangNhap(khachHang)
                        .ngayDang(resultSet.getTimestamp("ngayDang").toLocalDateTime())
                        .build();
                baiViets.add(baiViet) ;
            }
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return baiViets ;
    }

    @Override
    public List<BaiViet> xemDanhSachBaiVietDaXoa(KhachHang khachHang ,int index) {
        List<BaiViet> baiViets = new ArrayList<>() ;
        try {
            Connection connection = JDBC.getConnection()   ;
            String sql = "SELECT * " +
                    "FROM BaiViet " +
                    "WHERE tonTai = false and tenDangNhap = ?" +
                    "ORDER By ngayDang " +
                    "LIMIT ? , ?" ;
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, khachHang.getTenDangNhap());
            preparedStatement.setInt(2,(index-1) * PAGE_SIZE);
            preparedStatement.setInt(3,PAGE_SIZE);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                BaiViet baiViet = BaiViet.builder()
                        .maBaiViet(resultSet.getString("maBaiViet"))
                        .tenBaiViet(resultSet.getString("tenBaiViet"))
                        .noiDung(resultSet.getString("noiDung"))
                        .tenDangNhap(khachHang)
                        .ngayDang(resultSet.getTimestamp("ngayDang").toLocalDateTime())
                        .build();
                baiViets.add(baiViet) ;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return baiViets ;
    }

    @Override
    public int soBaiVietDaXoa(KhachHang khachHang) {
        int ans = 0 ;
        try {
            Connection connection = JDBC.getConnection() ;
            String sql = "SELECT count(*) 'soBaiVietDaXoa'" +
                    "FROM BaiViet " +
                    "WHERE tonTai = false and tenDangNhap = ?" ;
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, khachHang.getTenDangNhap());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                ans = resultSet.getInt("soBaiVietDaXoa") ;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return ans ;
    }
}
