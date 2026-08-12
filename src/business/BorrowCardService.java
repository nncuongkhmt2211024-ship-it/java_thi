package business;

import entities.BorrowCard;
import persistence.BorrowCardDAO;

import java.util.List;

public class BorrowCardService {
    private final BorrowCardDAO borrowCardDAO = new BorrowCardDAO();

    public List<BorrowCard> getAllBorrowCards() {
        return borrowCardDAO.getAll();
    }

    public boolean addBorrowCard(BorrowCard card) {
        if (card == null || card.getQuantity() <= 0) {
            System.err.println("Lỗi nghiệp vụ: Thông tin phiếu mượn hoặc số lượng không hợp lệ!");
            return false;
        }
        return borrowCardDAO.add(card);
    }

    public List<BorrowCard> getBorrowCardsByBorrower(String borrowerName) {
        if (borrowerName == null || borrowerName.trim().isEmpty()) {
            System.err.println("Tên độc giả tìm kiếm không được để trống!");
            return List.of();
        }
        return borrowCardDAO.getByBorrowerName(borrowerName.trim());
    }

    public boolean updateBorrowCard(BorrowCard card) {
        if (card == null || card.getCardId() <= 0) {
            System.err.println("Lỗi nghiệp vụ: Mã phiếu mượn không hợp lệ!");
            return false;
        }
        return borrowCardDAO.update(card);
    }

    public boolean deleteBorrowCard(int cardId) {
        if (cardId <= 0) {
            System.err.println("Lỗi nghiệp vụ: Mã phiếu mượn phải lớn hơn 0!");
            return false;
        }
        return borrowCardDAO.delete(cardId);
    }

    public List<BorrowCard> searchBorrowCardsByBookTitle(String bookTitle) {
        if (bookTitle == null || bookTitle.trim().isEmpty()) {
            System.err.println("Tên sách tìm kiếm không được để trống!");
            return List.of();
        }
        return borrowCardDAO.searchByBookTitle(bookTitle.trim());
    }
}