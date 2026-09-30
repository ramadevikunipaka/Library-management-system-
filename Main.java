package com.library;

import com.library.model.Book;
import com.library.service.BookService;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final BookService bookService = new BookService();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== Library Management System ===");

        while (true) {
            System.out.println("\n1. Add Book");
            System.out.println("2. View Books");
            System.out.println("3. Update Book");
            System.out.println("4. Delete Book");
            System.out.println("5. Exit");
            System.out.print("Choose: ");

            String choice = scanner.nextLine();

            try {
                switch (choice) {
                    case "1" -> addBook(scanner);
                    case "2" -> viewBooks();
                    case "3" -> updateBook(scanner);
                    case "4" -> deleteBook(scanner);
                    case "5" -> {
                        System.out.println("Goodbye!");
                        return;
                    }
                    default -> System.out.println("Invalid choice.");
                }
            } catch (SQLException | IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private static void addBook(Scanner scanner) throws SQLException {
        System.out.print("Title: ");
        String title = scanner.nextLine();
        System.out.print("Author: ");
        String author = scanner.nextLine();
        System.out.print("ISBN: ");
        String isbn = scanner.nextLine();
        System.out.print("Category: ");
        String category = scanner.nextLine();
        System.out.print("Quantity: ");
        int quantity = Integer.parseInt(scanner.nextLine());

        bookService.addBook(new Book(title, author, isbn, category, quantity, quantity));
        System.out.println("Book added.");
    }

    private static void viewBooks() throws SQLException {
        List<Book> books = bookService.getAllBooks();
        if (books.isEmpty()) {
            System.out.println("No books found.");
            return;
        }

        for (Book b : books) {
            System.out.printf("ID=%d | %s | %s | ISBN=%s | Available=%d/%d%n",
                    b.getId(), b.getTitle(), b.getAuthor(), b.getIsbn(),
                    b.getAvailableQuantity(), b.getQuantity());
        }
    }

    private static void updateBook(Scanner scanner) throws SQLException {
        System.out.print("Book ID: ");
        int id = Integer.parseInt(scanner.nextLine());
        System.out.print("New title: ");
        String title = scanner.nextLine();
        System.out.print("New author: ");
        String author = scanner.nextLine();
        System.out.print("New ISBN: ");
        String isbn = scanner.nextLine();
        System.out.print("New category: ");
        String category = scanner.nextLine();
        System.out.print("New quantity: ");
        int quantity = Integer.parseInt(scanner.nextLine());

        Book book = new Book(title, author, isbn, category, quantity, quantity);
        book.setId(id);
        bookService.updateBook(book);
        System.out.println("Book updated.");
    }

    private static void deleteBook(Scanner scanner) throws SQLException {
        System.out.print("Book ID: ");
        int id = Integer.parseInt(scanner.nextLine());
        bookService.deleteBook(id);
        System.out.println("Book deleted.");
    }
}
