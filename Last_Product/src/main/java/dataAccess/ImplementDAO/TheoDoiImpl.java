package dataAccess.ImplementDAO;

import JDBC.JDBC;
import dataAccess.IntefaceDAO.KhachHangDAO;
import dataAccess.IntefaceDAO.TheoDoiDAO;
import object.KhachHang;
import object.Response;
import object.TheoDoi;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.Optional;

public class TheoDoiImpl implements TheoDoiDAO {
    @Override
    public void insert(KhachHang o1, KhachHang o2) {
        try {
            Connection connection = JDBC.getConnection() ;
            String sql = "INSERT INTO `TheoDoi` (`tenDangNhap`, `tenNguoiTheoDoi`, `ngayTheoDoi`) VALUES (?,?,?)" ;
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1 , o1.getTenDangNhap());
            preparedStatement.setString(2 , o2.getTenDangNhap());
            preparedStatement.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
            preparedStatement.executeUpdate() ;
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean insert(TheoDoi theoDoi) {
        return  false ;
    }

    @Override
    public boolean update(TheoDoi theoDoi) {
        return  false ;
    }

    @Override
    public boolean delete(TheoDoi theoDoi) {
        try {
            Connection connection = JDBC.getConnection()  ;
            String sql = "DELETE FROM TheoDoi where tenDangNhap = ? and tenNguoiTheoDoi = ?" ;
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1 , theoDoi.getTenDangNhap().getTenDangNhap());
            preparedStatement.setString(2,theoDoi.getTenNguoiTheoDoi().getTenDangNhap());
            preparedStatement.executeQuery() ;
            connection.close();
        } catch (SQLException e) {
            return  false ;
        }
        return true ;
    }

    @Override
    public TheoDoi findById(String t) {
        return null;
    }

    @Override
    public TheoDoi findById(TheoDoi t) {
        TheoDoi theoDoi = null ;
        try {
            Connection connection = JDBC.getConnection() ;
            String sql = "SELECT * FROM TheoDoi where tenDangNhap = ? and tenNguoiTheoDoi = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql) ;
            preparedStatement.setString(1,t.getTenDangNhap().getTenDangNhap());
            preparedStatement.setString(2,t.getTenNguoiTheoDoi().getTenDangNhap());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                KhachHangDAO khachHangDAO = new KhachHangImpl()  ;
                KhachHang tenDangNhap = khachHangDAO.findById(resultSet.getString("tenDangNhap")) ;
                KhachHang tenNguoiTheoDoi = khachHangDAO.findById(resultSet.getString("tenNguoiTheoDoi")) ;
                theoDoi = TheoDoi.builder()
                        .tenDangNhap(tenDangNhap)
                        .tenNguoiTheoDoi(tenNguoiTheoDoi)
                        .ngayTheoDoi(resultSet.getTimestamp("ngayTheoDoi").toLocalDateTime())
                        .build();
            }
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return theoDoi ;
    }

    @Override
    public Response status(TheoDoi t) {
        Optional<TheoDoi> theoDoi = Optional.ofNullable(findById(t));
        Response response = new Response("400" , "NOT FOUND" , "OK");
        if(theoDoi.isPresent()){
            response = Response.builder()
                    .maLoi("200")
                    .noiDung("Theo doi ton tai")
                    .trangThai("OK")
                    .build();
        }
        return response;
    }
}
