package advanced.jdbc;

public class Main {
    public static void main(String[] args) {
        BookDAO bookDAO = new BookDAO();
        bookDAO.returnBook(1,101);
    }
}
