package presentation;

import business.BorrowCardService;
import entities.BorrowCard;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final BorrowCardService service = new BorrowCardService();
    private static final Scanner scanner = new Scanner(System.in);
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n================ LIBRARY MANAGEMENT ================");
            System.out.println("1. Danh sách tất cả phiếu mượn");
            System.out.println("2. Thêm mới phiếu mượn");
            System.out.println("3. Cập nhật thông tin phiếu mượn");
            System.out.println("4. Xóa phiếu mượn");
            System.out.println("5. Tìm kiếm phiếu mượn theo tên độc giả");
            System.out.println("6. Tìm kiếm phiếu mượn theo tên sách");
            System.out.println("7. Thoát");

            int choice = readInt("Chọn chức năng (1-7): ");
            switch (choice) {
                case 1 -> showAllCards();
                case 2 -> addCard();
                case 3 -> updateCard();
                case 4 -> deleteCard();
                case 5 -> searchByBorrower();
                case 6 -> searchByBookTitle();
                case 7 -> {
                    System.out.println("Cảm ơn bạn đã sử dụng chương trình!");
                    return;
                }
                default -> System.out.println("Lựa chọn không nằm trong phạm vi (1-7), vui lòng chọn lại!");
            }
        }
    }

    private static void printTable(List<BorrowCard> cards) {
        if (cards == null || cards.isEmpty()) {
            System.out.println("Không tìm thấy dữ liệu phiếu mượn.");
            return;
        }
        System.out.println("+-------+---------------------------+----------------------+------------------+------------------+----------+--------------+");
        System.out.println("| ID    | Tên sách                  | Tên độc giả          | Ngày mượn        | Hạn trả          | Số lượng | Trạng thái   |");
        System.out.println("+-------+---------------------------+----------------------+------------------+------------------+----------+--------------+");
        for (BorrowCard card : cards) {
            System.out.println(card);
        }
        System.out.println("+-------+---------------------------+----------------------+------------------+------------------+----------+--------------+");
    }

    private static void showAllCards() {
        System.out.println("\n--- DANH SÁCH TẤT CẢ PHIẾU MƯỢN ---");
        printTable(service.getAllBorrowCards());
    }

    private static void addCard() {
        System.out.println("\n--- THÊM MỚI PHIẾU MƯỢN ---");
        BorrowCard card = inputBorrowCardInfo();
        if (service.addBorrowCard(card)) {
            System.out.println("Thêm mới phiếu mượn thành công!");
        } else {
            System.out.println("Thêm mới thất bại!");
        }
    }

    private static void updateCard() {
        System.out.println("\n--- CẬP NHẬT PHIẾU MƯỢN ---");
        int id = readInt("Nhập ID phiếu mượn cần cập nhật: ");
        BorrowCard card = inputBorrowCardInfo();
        card.setCardId(id);

        if (service.updateBorrowCard(card)) {
            System.out.println("Cập nhật phiếu mượn thành công!");
        } else {
            System.out.println("Cập nhật thất bại!");
        }
    }

    private static void deleteCard() {
        System.out.println("\n--- XÓA PHIẾU MƯỢN ---");
        int id = readInt("Nhập ID phiếu mượn cần xóa: ");

        if (service.deleteBorrowCard(id)) {
            System.out.println("Xóa phiếu mượn thành công!");
        } else {
            System.out.println("Xóa thất bại!");
        }
    }

    private static void searchByBorrower() {
        System.out.println("\n--- TÌM KIẾM THEO TÊN ĐỘC GIẢ ---");
        System.out.print("Nhập tên độc giả: ");
        String name = scanner.nextLine();
        printTable(service.getBorrowCardsByBorrower(name));
    }

    private static void searchByBookTitle() {
        System.out.println("\n--- TÌM KIẾM THEO TÊN SÁCH ---");
        System.out.print("Nhập tên sách: ");
        String title = scanner.nextLine();
        printTable(service.searchBorrowCardsByBookTitle(title));
    }

    private static BorrowCard inputBorrowCardInfo() {
        BorrowCard card = new BorrowCard();

        System.out.print("Nhập tên sách: ");
        card.setBookTitle(scanner.nextLine());

        System.out.print("Nhập tên độc giả: ");
        card.setBorrowerName(scanner.nextLine());

        card.setBorrowDate(readDateTime("Nhập ngày mượn (định dạng yyyy-MM-dd HH:mm): "));
        card.setReturnDeadline(readDateTime("Nhập hạn trả (định dạng yyyy-MM-dd HH:mm): "));
        card.setQuantity(readInt("Nhập số lượng mượn: "));

        System.out.print("Nhập trạng thái (Borrowing/Returned/Overdue): ");
        card.setStatus(scanner.nextLine());

        return card;
    }

    // Hàm tiện ích xử lý ngoại lệ khi nhập số nguyên
    private static int readInt(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.err.println("Lỗi: Dữ liệu nhập vào phải là số nguyên! Vui lòng nhập lại.");
            }
        }
    }

    // Hàm tiện ích xử lý ngoại lệ khi nhập thời gian
    private static LocalDateTime readDateTime(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                String input = scanner.nextLine().trim();
                return LocalDateTime.parse(input, FORMATTER);
            } catch (DateTimeParseException e) {
                System.err.println("Lỗi: Định dạng ngày tháng không hợp lệ (Ví dụ đúng: 2026-08-12 14:30)! Vui lòng nhập lại.");
            }
        }
    }
}