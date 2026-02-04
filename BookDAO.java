package advanced.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class BookDAO {
    public void addBook(String title, String author) {
        String sql = "INSERT INTO books (title, author) VALUES (?,?)";
        try {
            Connection connect = DbTest.getConnection();
            PreparedStatement ps = connect.prepareStatement(sql); {
                ps.setString(1, title);
                ps.setString(2, author);

                ps.executeUpdate();
                System.out.println("Book added successfully!");
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void viewAllBooks() {
        String sql = "SELECT * FROM books";

        try {
            Connection connection = DbTest.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery(); {
                System.out.println("BOOK LIST");
                while (rs.next()) {
                    Book book = new Book(
                            rs.getInt("book_id"),
                            rs.getString("title"),
                            rs.getString("author"),
                            rs.getBoolean("available")
                    );
                    System.out.println(book);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void borrowBook(int bookId, int userId) {
        String checkBook = "SELECT available FROM books WHERE book_id = ?";
        String updateBook = "UPDATE books SET available = false WHERE book_id = ? ";
        String insertTxn = "INSERT INTO transaction(book_id, user_id) VALUES (?, ?)";

        try {
            Connection connection = DbTest.getConnection();
            connection.setAutoCommit(false);

            PreparedStatement ps = connection.prepareStatement(checkBook);
            ps.setInt(1,bookId);
            ResultSet rs = ps.executeQuery();

            if(!rs.next() || rs.getBoolean("available")) {
                System.out.println("Book not available");
                connection.rollback();
                return;
            }

            PreparedStatement psUpdate = connection.prepareStatement(updateBook);
            psUpdate.setInt(1,bookId);
            psUpdate.executeUpdate();

            PreparedStatement psT = connection.prepareStatement(insertTxn);
            psT.setInt(1,bookId);
            psT.setInt(2,userId);
            psT.executeUpdate();


            connection.commit();
            System.out.println("Book borrowed successfully!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void returnBook(int bookId, int userId) {
        String findTxn = """
                SELECT transaction_id FROM transactions
                WHERE book_id = ? AND user_id = ?
                AND return_date IS NULL
                ORDER BY borrow_date DESC
                LIMIT 1
                """;

        String updateTxn = "UPDATE transactions SET return_date = CURRENT_TIMESTAMP WHERE transaction_id = ?";
        String updateBook = "UPDATE books SET available = true WHERE book_id = ?";

        try (Connection connection = DbTest.getConnection()) {
            connection.setAutoCommit(false);

            PreparedStatement psF = connection.prepareStatement(findTxn);
            psF.setInt(1,bookId);
            psF.setInt(2,userId);
            ResultSet rs = psF.executeQuery();

            if(!rs.next()) {
                System.out.println("No active borrow record found");
                connection.rollback();
                return;
            }
            int txnId = rs.getInt("transaction_id");

            PreparedStatement psUT = connection.prepareStatement(updateTxn);
            psUT.setInt(1,txnId);
            psUT.executeUpdate();

            PreparedStatement psB = connection.prepareStatement(updateBook);
            psB.setInt(1,bookId);
            psB.executeUpdate();

            connection.commit();
            System.out.println("Book returned successfully");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
