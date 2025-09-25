import java.util.ArrayList;
import java.util.Scanner;

class Book {
    String title;
    String author;
    String isbn;
    boolean isBorrowed;

    public Book(String title, String author, String isbn) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.isBorrowed = false;
    }

    // Getters
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public String getIsbn() { return isbn; }
    public boolean isBorrowed() { return isBorrowed; }

    // Setters
    public void setBorrowed(boolean borrowed) { isBorrowed = borrowed; }

    @Override
    public String toString() {
        return "Title: " + title + ", Author: " + author + ", ISBN: " + isbn + ", Borrowed: " + (isBorrowed ? "Yes" : "No");
    }
}

class Member {
    String name;
    int memberId;
    ArrayList<Book> borrowedBooks;

    public Member(String name, int memberId) {
        this.name = name;
        this.memberId = memberId;
        this.borrowedBooks = new ArrayList<>();
    }

    // Getters
    public String getName() { return name; }
    public int getMemberId() { return memberId; }
    public ArrayList<Book> getBorrowedBooks() { return borrowedBooks; }

    @Override
    public String toString() {
        return "Name: " + name + ", Member ID: " + memberId + ", Borrowed Books: " + borrowedBooks.size();
    }
}

public class LibraryManagementSystem {
    private ArrayList<Book> books;
    private ArrayList<Member> members;
    private Scanner scanner;

    public LibraryManagementSystem() {
        books = new ArrayList<>();
        members = new ArrayList<>();
        scanner = new Scanner(System.in);
    }

    public void addBook(String title, String author, String isbn) {
        books.add(new Book(title, author, isbn));
        System.out.println("Book added successfully.");
    }

    public void registerMember(String name, int memberId) {
        members.add(new Member(name, memberId));
        System.out.println("Member registered successfully.");
    }

    public void borrowBook(String isbn, int memberId) {
        Book bookToBorrow = findBookByIsbn(isbn);
        Member borrowingMember = findMemberById(memberId);

        if (bookToBorrow != null && borrowingMember != null) {
            if (!bookToBorrow.isBorrowed()) {
                bookToBorrow.setBorrowed(true);
                borrowingMember.getBorrowedBooks().add(bookToBorrow);
                System.out.println("Book borrowed successfully.");
            } else {
                System.out.println("Book is already borrowed.");
            }
        } else {
            System.out.println("Book or Member not found.");
        }
    }

    public void returnBook(String isbn, int memberId) {
        Book bookToReturn = findBookByIsbn(isbn);
        Member returningMember = findMemberById(memberId);

        if (bookToReturn != null && returningMember != null) {
            if (bookToReturn.isBorrowed() && returningMember.getBorrowedBooks().contains(bookToReturn)) {
                bookToReturn.setBorrowed(false);
                returningMember.getBorrowedBooks().remove(bookToReturn);
                System.out.println("Book returned successfully.");
            } else {
                System.out.println("This member did not borrow this book, or the book was not borrowed.");
            }
        } else {
            System.out.println("Book or Member not found.");
        }
    }

    public void displayAllBooks() {
        if (books.isEmpty()) {
            System.out.println("No books in the library.");
            return;
        }
        System.out.println("\n--- All Books ---");
        for (Book book : books) {
            System.out.println(book);
        }
    }

    public void displayAllMembers() {
        if (members.isEmpty()) {
            System.out.println("No members registered.");
            return;
        }
        System.out.println("\n--- All Members ---");
        for (Member member : members) {
            System.out.println(member);
        }
    }

    private Book findBookByIsbn(String isbn) {
        for (Book book : books) {
            if (book.getIsbn().equals(isbn)) {
                return book;
            }
        }
        return null;
    }

    private Member findMemberById(int memberId) {
        for (Member member : members) {
            if (member.getMemberId() == memberId) {
                return member;
            }
        }
        return null;
    }

    public void run() {
        int choice;
        do {
            System.out.println("\n--- Library Management System Menu ---");
            System.out.println("1. Add Book");
            System.out.println("2. Register Member");
            System.out.println("3. Borrow Book");
            System.out.println("4. Return Book");
            System.out.println("5. Display All Books");
            System.out.println("6. Display All Members");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter book title: ");
                    String title = scanner.nextLine();
                    System.out.print("Enter book author: ");
                    String author = scanner.nextLine();
                    System.out.print("Enter book ISBN: ");
                    String isbn = scanner.nextLine();
                    addBook(title, author, isbn);
                    break;
                case 2:
                    System.out.print("Enter member name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter member ID: ");
                    int memberId = scanner.nextInt();
                    scanner.nextLine(); // Consume newline
                    registerMember(name, memberId);
                    break;
                case 3:
                    System.out.print("Enter ISBN of book to borrow: ");
                    String borrowIsbn = scanner.nextLine();
                    System.out.print("Enter member ID: ");
                    int borrowMemberId = scanner.nextInt();
                    scanner.nextLine(); // Consume newline
                    borrowBook(borrowIsbn, borrowMemberId);
                    break;
                case 4:
                    System.out.print("Enter ISBN of book to return: ");
                    String returnIsbn = scanner.nextLine();
                    System.out.print("Enter member ID: ");
                    int returnMemberId = scanner.nextInt();
                    scanner.nextLine(); // Consume newline
                    returnBook(returnIsbn, returnMemberId);
                    break;
                case 5:
                    displayAllBooks();
                    break;
                case 6:
                    displayAllMembers();
                    break;
                case 0:
                    System.out.println("Exiting Library Management System. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 0);
        scanner.close();
    }

    public static void main(String[] args) {
        LibraryManagementSystem lms = new LibraryManagementSystem();
        lms.run();
    }
}