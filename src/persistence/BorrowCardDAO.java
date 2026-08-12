package persistence;

import entities.BorrowCard;
import utils.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BorrowCardDAO {

    public List<BorrowCard> getAll() {
        List<BorrowCard> list = new ArrayList<>();
        String sql = "SELECT * FROM fn_get_all_borrow_cards()";
        Connection conn = DBConnection.connect();

        if (conn == null) {
            System.err.println("Không thể kết nối đến cơ sở dữ liệu!");
            return list;
        }

        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(mapResultSetToBorrowCard(rs));
            }
        } catch (SQLException e) {
            System.err.println("Lỗi truy vấn danh sách phiếu mượn: " + e.getMessage());
            e.printStackTrace();
        } finally {
            closeConnection(conn);
        }
        return list;
    }

    public boolean add(BorrowCard card) {
        String sql = "CALL sp_add_borrow_card(?, ?, ?, ?, ?, ?)";
        Connection conn = DBConnection.connect();

        if (conn == null) return false;

        try (CallableStatement cs = conn.prepareCall(sql)) {
            cs.setString(1, card.getBookTitle());
            cs.setString(2, card.getBorrowerName());
            cs.setTimestamp(3, Timestamp.valueOf(card.getBorrowDate()));
            cs.setTimestamp(4, Timestamp.valueOf(card.getReturnDeadline()));
            cs.setInt(5, card.getQuantity());
            cs.setString(6, card.getStatus());
            cs.execute();
            return true;
        } catch (SQLException e) {
            System.err.println("Lỗi khi thêm mới phiếu mượn: " + e.getMessage());
            e.printStackTrace();
            return false;
        } finally {
            closeConnection(conn);
        }
    }

    public List<BorrowCard> getByBorrowerName(String borrowerName) {
        List<BorrowCard> list = new ArrayList<>();
        String sql = "SELECT * FROM fn_get_borrow_cards_by_borrower(?)";
        Connection conn = DBConnection.connect();

        if (conn == null) return list;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, borrowerName);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapResultSetToBorrowCard(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Lỗi tìm kiếm theo tên độc giả: " + e.getMessage());
            e.printStackTrace();
        } finally {
            closeConnection(conn);
        }
        return list;
    }

    public boolean update(BorrowCard card) {
        String sql = "CALL sp_update_borrow_card(?, ?, ?, ?, ?, ?, ?)";
        Connection conn = DBConnection.connect();

        if (conn == null) return false;

        try (CallableStatement cs = conn.prepareCall(sql)) {
            cs.setInt(1, card.getCardId());
            cs.setString(2, card.getBookTitle());
            cs.setString(3, card.getBorrowerName());
            cs.setTimestamp(4, Timestamp.valueOf(card.getBorrowDate()));
            cs.setTimestamp(5, Timestamp.valueOf(card.getReturnDeadline()));
            cs.setInt(6, card.getQuantity());
            cs.setString(7, card.getStatus());
            cs.execute();
            return true;
        } catch (SQLException e) {
            System.err.println("Lỗi khi cập nhật phiếu mượn: " + e.getMessage());
            e.printStackTrace();
            return false;
        } finally {
            closeConnection(conn);
        }
    }

    public boolean delete(int cardId) {
        String sql = "CALL sp_delete_borrow_card(?)";
        Connection conn = DBConnection.connect();

        if (conn == null) return false;

        try (CallableStatement cs = conn.prepareCall(sql)) {
            cs.setInt(1, cardId);
            cs.execute();
            return true;
        } catch (SQLException e) {
            System.err.println("Lỗi khi xóa phiếu mượn: " + e.getMessage());
            e.printStackTrace();
            return false;
        } finally {
            closeConnection(conn);
        }
    }

    public List<BorrowCard> searchByBookTitle(String bookTitle) {
        List<BorrowCard> list = new ArrayList<>();
        String sql = "SELECT * FROM fn_search_borrow_cards_by_book_title(?)";
        Connection conn = DBConnection.connect();

        if (conn == null) return list;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, bookTitle);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapResultSetToBorrowCard(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Lỗi tìm kiếm theo tên sách: " + e.getMessage());
            e.printStackTrace();
        } finally {
            closeConnection(conn);
        }
        return list;
    }

    private BorrowCard mapResultSetToBorrowCard(ResultSet rs) throws SQLException {
        return new BorrowCard(
                rs.getInt("card_id"),
                rs.getString("book_title"),
                rs.getString("borrower_name"),
                rs.getTimestamp("borrow_date").toLocalDateTime(),
                rs.getTimestamp("return_deadline").toLocalDateTime(),
                rs.getInt("quantity"),
                rs.getString("status")
        );
    }

    private void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                System.err.println("Lỗi đóng kết nối: " + e.getMessage());
            }
        }
    }
}