package dataAccess.ImplementDAO;

import JDBC.JDBC;
import dataAccess.IntefaceDAO.KhachHangDAO;
import object.KhachHang;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class KhachHangImpl implements KhachHangDAO{
    @Override
    public boolean insert(KhachHang khachHang) {
        Connection connection = null;
        try {
            connection = JDBC.getConnection();
            String sql = "Insert into KhachHang values(?,?,?,?,?,?,?)" ;
            PreparedStatement preparedStatement = connection.prepareStatement(sql) ;
            preparedStatement.setString(1,khachHang.getTenDangNhap());
            preparedStatement.setString(2, khachHang.getMatKhau());
            preparedStatement.setString(3, khachHang.getTenKhachHang());
            preparedStatement.setString(4, khachHang.getDiaChiEmail());
            preparedStatement.setDate(5, Date.valueOf(khachHang.getNgaySinh()));
            preparedStatement.setTimestamp(6, Timestamp.valueOf(khachHang.getNgayTaoTaiKhoan()));
            preparedStatement.setString(7, khachHang.getVaiTro());
            preparedStatement.executeUpdate() ;
            connection.close();
        } catch (SQLException e) {
            return  false ;
        }
        return  true ;
    }


    @Override
    public boolean update(KhachHang khachHang) {
        try {
            Connection connection = JDBC.getConnection() ;
            String sql="UPDATE `KhachHang` SET `tenDangNhap` = ?, `matKhau` = ?, `tenKhachHang` = ? , diaChiEmail = ? , ngaySinh = ? ,vaiTro = ? WHERE `KhachHang`.`tenDangNhap` = ?" ;
            PreparedStatement preparedStatement = connection.prepareStatement(sql) ;
            preparedStatement.setString(1,khachHang.getTenDangNhap());
            preparedStatement.setString(2, khachHang.getMatKhau());
            preparedStatement.setString(3, khachHang.getTenKhachHang());
            preparedStatement.setString(4, khachHang.getDiaChiEmail());
            preparedStatement.setDate(5, Date.valueOf(khachHang.getNgaySinh()));
            preparedStatement.setString(7,khachHang.getTenDangNhap());
            preparedStatement.setString(6 , khachHang.getVaiTro());
            preparedStatement.executeUpdate() ;
            connection.close();
        } catch (SQLException e) {
            return false ;
        }
        return  true ;
    }

    @Override
    public boolean delete(KhachHang khachHang) {
        try {
            Connection connection = JDBC.getConnection();
            String sql = "Delete from KhachHang where tenDangNhap = ?" ;
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, khachHang.getTenDangNhap());
            preparedStatement.executeUpdate() ;
            connection.close();
        } catch (SQLException e) {
            return false ;
        }
        return  true ;
    }


    @Override
    public KhachHang findById(String t) {
        KhachHang khachHangAns = null;
        try {
            Connection connection  = JDBC.getConnection() ;
            String sql = "select * from KhachHang where tenDangNhap =  ?" ;
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,t);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                khachHangAns = KhachHang.builder()
                        .tenKhachHang(resultSet.getString("tenKhachHang"))
                        .tenDangNhap(resultSet.getString("tenDangNhap"))
                        .matKhau(resultSet.getString("matKhau"))
                        .ngayTaoTaiKhoan(resultSet.getTimestamp("ngayTaoTaiKhoan").toLocalDateTime())
                        .ngaySinh(resultSet.getDate("ngaySinh").toLocalDate())
                        .diaChiEmail(resultSet.getString("diaChiEmail"))
                        .vaiTro(resultSet.getString("vaiTro"))
                        .build();
            }
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return khachHangAns ;
    }

    @Override
    public List<KhachHang> findAll(int index) {
        List<KhachHang> khachHangs = new ArrayList<>() ;
        try {
            Connection connection = JDBC.getConnection() ;
            String sql = "select * from KhachHang LIMIT ? , ?" ;
            PreparedStatement preparedStatement = connection.prepareStatement(sql) ;
            preparedStatement.setInt(1,(index - 1) * PAGE_SIZE);
            preparedStatement.setInt(2, PAGE_SIZE);
            ResultSet resultSet = preparedStatement.executeQuery() ;
            while (resultSet.next()){
                KhachHang one = KhachHang.builder()
                        .tenKhachHang(resultSet.getString("tenKhachHang"))
                        .tenDangNhap(resultSet.getString("tenDangNhap"))
                        .matKhau(resultSet.getString("matKhau"))
                        .ngayTaoTaiKhoan(resultSet.getTimestamp("ngayTaoTaiKhoan").toLocalDateTime())
                        .ngaySinh(resultSet.getDate("ngaySinh").toLocalDate())
                        .diaChiEmail(resultSet.getString("diaChiEmail"))
                        .vaiTro(resultSet.getString("vaiTro"))
                        .build();
                khachHangs.add(one) ;
            }
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return khachHangs ;
    }

    @Override
    public List<KhachHang> findByName(String t) {
        List<KhachHang> khachHangs = new ArrayList<>() ;
        try {
            Connection connection = JDBC.getConnection() ;
            String sql = "select * from KhachHang where tenKhachHang like CONCAT('%',?,'%') " ;
            PreparedStatement preparedStatement = connection.prepareStatement(sql) ;
            preparedStatement.setString(1, t);
            ResultSet resultSet = preparedStatement.executeQuery() ;
            while (resultSet.next()){
                KhachHang one = KhachHang.builder()
                        .tenKhachHang(resultSet.getString("tenKhachHang"))
                        .tenDangNhap(resultSet.getString("tenDangNhap"))
                        .matKhau(resultSet.getString("matKhau"))
                        .ngayTaoTaiKhoan(resultSet.getTimestamp("ngayTaoTaiKhoan").toLocalDateTime())
                        .ngaySinh(resultSet.getDate("ngaySinh").toLocalDate())
                        .diaChiEmail(resultSet.getString("diaChiEmail"))
                        .vaiTro(resultSet.getString("vaiTro"))
                        .build();
                khachHangs.add(one) ;
            }
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return khachHangs ;
    }
}
