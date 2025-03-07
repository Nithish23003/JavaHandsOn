package Day5;

import java.io.*;
import java.nio.file.*;
import java.util.*;

// Custom Exception for Book Not Available
class BookNotAvailableException extends Exception {
    public BookNotAvailableException(String message) {
        super(message);
    }
}

// Book Class (OOP Concept)
class Book implements Serializable {
    private String title;
    private String author;
    private boolean isBorrowed;

    public Book(String title, String author) {
        this.title = title;
        this.author = author;
        this.isBorrowed = false;
    }

    public String getTitle() { return title; }
    public boolean isBorrowed() { return isBorrowed; }
    public void borrow() { isBorrowed = true; }
    public void returnBook() { isBorrowed = false; }

    @Override
    public String toString() {
        return title + " by " + author + " | " + (isBorrowed ? "Not Available" : "Available");
    }
}

// Library Class (Manages Book Collection)
class Library {
    private List<Book> books;
    private static final String FILE_PATH = "C:\\Users\\nithish.kumar\\Downloads\\books.txt";

    public Library() {
        this.books = loadBooks(); // Load books from file
    }

    // Add a new book to the library
    public void addBook(String title, String author) {
        books.add(new Book(title, author));
        saveBooks();
    }

    // Display available books using Streams
    public void displayAvailableBooks() {
        System.out.println("\n Available Books:");
        books.stream()
             .filter(book -> !book.isBorrowed())
             .forEach(System.out::println);
    }

    // Borrow a book
    public void borrowBook(String title) throws BookNotAvailableException {
        Optional<Book> book = books.stream()
                                   .filter(b -> b.getTitle().equalsIgnoreCase(title) && !b.isBorrowed())
                                   .findFirst();
        if (book.isPresent()) {
            book.get().borrow();
            System.out.println("\n You borrowed: " + book.get().getTitle());
            saveBooks();
        } else {
            throw new BookNotAvailableException("Book is not available or already borrowed!");
        }
    }

    // Return a book
    public void returnBook(String title) {
        books.stream()
             .filter(book -> book.getTitle().equalsIgnoreCase(title) && book.isBorrowed())
             .findFirst()
             .ifPresent(book -> {
                 book.returnBook();
                 System.out.println("\n You returned: " + book.getTitle());
                 saveBooks();
             });
    }

    // Save books to file
    private void saveBooks() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(FILE_PATH))) {
            out.writeObject(books);
        } catch (IOException e) {
            System.out.println(" Error saving book data!");
        }
    }

    // Load books from file
    private List<Book> loadBooks() {
        if (Files.exists(Paths.get(FILE_PATH))) {
            try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(FILE_PATH))) {
                return (List<Book>) in.readObject();
            } catch (IOException | ClassNotFoundException e) {
                System.out.println("Error loading book data!");
            }
        }
        return new ArrayList<>();
    }
}

// Main Class for User Interaction
public class LibraryManagementSystem {
    public static void main(String[] args) {
        Library library = new Library();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n Library Management System");
            System.out.println("1. Add Book");
            System.out.println("2. Display Available Books");
            System.out.println("3. Borrow Book");
            System.out.println("4. Return Book");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            try {
                switch (choice) {
                    case 1:
                        System.out.print("\nEnter Book Title: ");
                        String title = scanner.nextLine();
                        System.out.print("Enter Author Name: ");
                        String author = scanner.nextLine();
                        library.addBook(title, author);
                        System.out.println("Book Added Successfully!");
                        break;

                    case 2:
                        library.displayAvailableBooks();
                        break;

                    case 3:
                        System.out.print("\nEnter Book Title to Borrow: ");
                        String borrowTitle = scanner.nextLine();
                        library.borrowBook(borrowTitle);
                        break;

                    case 4:
                        System.out.print("\nEnter Book Title to Return: ");
                        String returnTitle = scanner.nextLine();
                        library.returnBook(returnTitle);
                        break;

                    case 5:
                        System.out.println("Thank you for using the Library System!");
                        scanner.close();
                        System.exit(0);

                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } catch (BookNotAvailableException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}

