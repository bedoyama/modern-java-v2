import java.time.LocalDate;
import java.util.List;

// Compact Record - Java's most concise class style (Java 16+)

public record User(String id, String name, String email, LocalDate createdAt) {
    // Compact constructor for validation
    public User {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name cannot be blank");
        }
        if (email == null || !email.contains("@")) {
            throw new IllegalArgumentException("Invalid email format");
        }
    }

    // Custom method
    public String displayName() {
        return name + " (" + email + ")";
    }
}

// Compact class for a Product
public record Product(String id, String name, double price, int stock) {
    public Product {
        if (price < 0) {
            throw new IllegalArgumentException("Price cannot be negative");
        }
    }

    public boolean isInStock() {
        return stock > 0;
    }
}

// Usage example
void main(String[] args) {
    // Creating compact class instances
    User user1 = new User("U001", "Alice Johnson", "alice@example.com", LocalDate.of(2024, 1, 15));

    Product product1 = new Product("P001", "Laptop", 999.99, 5);

    // Records automatically provide equals(), hashCode(), toString(), and accessors
    System.out.println("User: " + user1);
    System.out.println("Display: " + user1.displayName());
    System.out.println("Product: " + product1);
    System.out.println("In Stock: " + product1.isInStock());

    // Records support pattern matching (Java 21+)
    demonstratePatternMatching(user1);
    demonstratePatternMatching(product1);

    // Working with lists
    List<User> users = List.of(new User("U001", "Alice", "alice@example.com", LocalDate.now()), new User("U002", "Bob", "bob@example.com", LocalDate.now()));

    users.forEach(u -> System.out.println(u.displayName()));
}

// Pattern matching with records (Java 21+)
static void demonstratePatternMatching(Object obj) {
    if (obj instanceof User user) {
        System.out.println("User: " + user.name());
    } else if (obj instanceof Product product) {
        System.out.println("Product: " + product.name());
    }

}
