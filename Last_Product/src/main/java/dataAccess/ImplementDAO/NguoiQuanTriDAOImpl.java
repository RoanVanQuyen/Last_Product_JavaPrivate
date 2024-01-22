package dataAccess.ImplementDAO;

import JDBC.JDBC;
import dataAccess.IntefaceDAO.KhachHangDAO;
import dataAccess.IntefaceDAO.NguoiQuanTriDAO;
import object.BaiViet;
import object.KhachHang;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class NguoiQuanTriDAOImpl implements NguoiQuanTriDAO {
    @Override
    public List<KhachHang> thongKeKhachHangMoiTheoNgay(LocalDate day1, LocalDate day2 ,int index) {
        List<KhachHang> ans = new ArrayList<>()  ;
        try {
            Connection connection = JDBC.getConnection() ;
            String sql = "SELECT * FROM \n" +
                    "KhachHang \n" +
                    "where ngayTaoTaiKhoan >= ? and ngayTaoTaiKhoan <= ? \n" +
                    "order by ngayTaoTaiKhoan desc \n" +
                    "limit ? , ?" ;
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setDate(1, Date.valueOf(day1));
            preparedStatement.setDate(2, Date.valueOf(day2));
            preparedStatement.setInt(3, (index-1) * PAGE_SIZE );
            preparedStatement.setInt(4,PAGE_SIZE);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                KhachHang khachHangAns = KhachHang.builder()
                        .tenKhachHang(resultSet.getString("tenKhachHang"))
                        .tenDangNhap(resultSet.getString("tenDangNhap"))
                        .matKhau(resultSet.getString("matKhau"))
                        .ngayTaoTaiKhoan(resultSet.getTimestamp("ngayTaoTaiKhoan").toLocalDateTime())
                        .ngaySinh(resultSet.getDate("ngaySinh").toLocalDate())
                        .diaChiEmail(resultSet.getString("diaChiEmail"))
                        .vaiTro(resultSet.getString("vaiTro"))
                        .build();
                ans.add(khachHangAns) ;
            }
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return ans ;
    }

    @Override
    public List<BaiViet> thongKeBaiVietMoiTheoNgay(LocalDate day1, LocalDate day2, int index) {
        List<BaiViet> baiViets = new ArrayList<>( );
        try {
            Connection connection = JDBC.getConnection() ;
            String sql = "SELECT * \n" +
                    "FROM `BaiViet`\n" +
                    "WHERE ngayDang >= ? and ngayDang <= ? \n" +
                    "ORDER BY ngayDang desc \n" +
                    "limit ? , ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setDate(1,Date.valueOf(day1));
            preparedStatement.setDate(2,Date.valueOf(day2));
            preparedStatement.setInt(3, (index-1) * PAGE_SIZE );
            preparedStatement.setInt(4,PAGE_SIZE);
            ResultSet resultSet = preparedStatement.executeQuery();
            KhachHangDAO khachHangDAO = new KhachHangImpl() ;
            while(resultSet.next()){
                KhachHang khachHang = khachHangDAO.findById(resultSet.getString("tenDangNhap")) ;
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
    public int soTrangBaiViet(LocalDate day1, LocalDate day2) {
        int ans = 0 ;
        try {
            Connection connection = JDBC.getConnection();
            String sql = "SELECT COUNT(*)'soBaiViet' \n" +
                    "FROM `BaiViet`\n" +
                    "WHERE ngayDang >= ? and ngayDang <= ?" ;
            PreparedStatement preparedStatement= connection.prepareStatement(sql);
            preparedStatement.setDate(1,Date.valueOf(day1));
            preparedStatement.setDate(2,Date.valueOf(day2));
            ResultSet resultSet = preparedStatement.executeQuery() ;
            while (resultSet.next()){
                ans = resultSet.getInt("soBaiViet") ;
            }
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return ans ;
    }

    @Override
    public int soTrangTaiKhoan(LocalDate day1, LocalDate day2) {
        int ans = 0 ;
        try {
            Connection connection = JDBC.getConnection();
            String sql = "SELECT COUNT(*) 'soTaiKhoan' FROM \n" +
                    "KhachHang \n" +
                    "where ngayTaoTaiKhoan >= ? and ngayTaoTaiKhoan <= ? " ;
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setDate(1, Date.valueOf(day1));
            preparedStatement.setDate(2, Date.valueOf(day2));
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                ans = resultSet.getInt("soTaiKhoan");
            }
            connection.close();
        }catch (Exception e){
            throw new RuntimeException(e);
        }
        return ans ;
    }
}


