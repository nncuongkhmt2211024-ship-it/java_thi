package entities;

import java.time.LocalDateTime;

public class BorrowCard {
    private int cardId;
    private String bookTitle;
    private String borrowerName;
    private LocalDateTime borrowDate;
    private LocalDateTime returnDeadline;
    private int quantity;
    private String status;

    public BorrowCard() {}

    public BorrowCard(int cardId, String bookTitle, String borrowerName, LocalDateTime borrowDate, LocalDateTime returnDeadline, int quantity, String status) {
        this.cardId = cardId;
        this.bookTitle = bookTitle;
        this.borrowerName = borrowerName;
        this.borrowDate = borrowDate;
        this.returnDeadline = returnDeadline;
        this.quantity = quantity;
        this.status = status;
    }

    public int getCardId() { return cardId; }
    public void setCardId(int cardId) { this.cardId = cardId; }

    public String getBookTitle() { return bookTitle; }
    public void setBookTitle(String bookTitle) { this.bookTitle = bookTitle; }

    public String getBorrowerName() { return borrowerName; }
    public void setBorrowerName(String borrowerName) { this.borrowerName = borrowerName; }

    public LocalDateTime getBorrowDate() { return borrowDate; }
    public void setBorrowDate(LocalDateTime borrowDate) { this.borrowDate = borrowDate; }

    public LocalDateTime getReturnDeadline() { return returnDeadline; }
    public void setReturnDeadline(LocalDateTime returnDeadline) { this.returnDeadline = returnDeadline; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    @Override
    public String toString() {
        return String.format("| %-5d | %-25s | %-20s | %-16s | %-16s | %-8d | %-12s |",
                cardId, bookTitle, borrowerName, 
                borrowDate != null ? borrowDate.toString().replace("T", " ") : "", 
                returnDeadline != null ? returnDeadline.toString().replace("T", " ") : "", 
                quantity, status);
    }
}