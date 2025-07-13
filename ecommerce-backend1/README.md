# Ecommerce Backend API

A Spring Boot-based REST API for an ecommerce platform with user management and product management features.

## Features

### User Management
- **User Registration**: Sign up as either a BUYER or SELLER
- **User CRUD Operations**: Create, read, update, and delete users
- **Role-based Access**: Different permissions for buyers and sellers

### Product Management
- **Product Creation**: Sellers can add products with detailed information
- **Product Information**: Name, description, category, price, offer price, quantity, and image
- **Stock Management**: Track product availability (in stock/out of stock)
- **Product Search**: Search products by name, category, or seller
- **Product Updates**: Sellers can update their own products
- **Quantity Management**: Update product stock levels

## Technology Stack

- **Java 21**
- **Spring Boot 3.5.3**
- **Spring Data JPA**
- **Spring Security**
- **PostgreSQL**
- **Lombok**
- **Maven**
- **Swagger/OpenAPI**

## Prerequisites

- Java 21 or higher
- Maven 3.6+
- PostgreSQL 12+

## Setup Instructions

### 1. Database Setup

1. Install PostgreSQL if not already installed
2. Create a new database:
   ```sql
   CREATE DATABASE ecommerce_db;
   ```
3. Update `src/main/resources/application.yml` with your database credentials:
   ```yaml
   spring:
     datasource:
       url: jdbc:postgresql://localhost:5432/ecommerce_db
       username: your_username
       password: your_password
   ```

### 2. Build and Run

1. Clone the repository
2. Navigate to the project directory
3. Build the project:
   ```bash
   mvn clean install
   ```
4. Run the application:
   ```bash
   mvn spring-boot:run
   ```

The application will start on `http://localhost:8080`

### 3. API Documentation

Once the application is running, you can access the Swagger UI at:
`http://localhost:8080/swagger-ui.html`

## API Endpoints

### User Management

| Method | Endpoint | Description | Access |
|--------|----------|-------------|---------|
| POST | `/api/users/signup` | Register a new user (BUYER/SELLER) | Public |
| GET | `/api/users/{userId}` | Get user by ID | Public |
| GET | `/api/users` | Get all users | Public |
| GET | `/api/users/role/{role}` | Get users by role | Public |
| PUT | `/api/users/{userId}` | Update user | Public |
| DELETE | `/api/users/{userId}` | Delete user | Public |

### Product Management

| Method | Endpoint | Description | Access |
|--------|----------|-------------|---------|
| POST | `/api/products?sellerId={id}` | Create a new product | Seller only |
| GET | `/api/products/{productId}` | Get product by ID | Public |
| GET | `/api/products` | Get all products | Public |
| GET | `/api/products/seller/{sellerId}` | Get products by seller | Public |
| GET | `/api/products/category/{category}` | Get products by category | Public |
| GET | `/api/products/search?keyword={keyword}` | Search products by name | Public |
| GET | `/api/products/available` | Get available products (in stock) | Public |
| PUT | `/api/products/{productId}?sellerId={id}` | Update product | Seller (owner) |
| PATCH | `/api/products/{productId}/quantity?newQuantity={qty}&sellerId={id}` | Update product quantity | Seller (owner) |
| DELETE | `/api/products/{productId}?sellerId={id}` | Delete product | Seller (owner) |

## Sample Data

The application automatically creates sample users on startup:

- **Buyer**: `buyer@example.com` / `password123`
- **Seller**: `seller@example.com` / `password123`

## Request/Response Examples

### User Registration
```json
POST /api/users/signup
{
  "name": "John Doe",
  "email": "john@example.com",
  "password": "password123",
  "role": "BUYER"
}
```

### Product Creation
```json
POST /api/products?sellerId=2
{
  "productName": "iPhone 15",
  "productDescription": "Latest iPhone with advanced features",
  "category": "Electronics",
  "productPrice": 999.99,
  "productOfferPrice": 899.99,
  "quantity": 10,
  "productImage": "https://example.com/iphone15.jpg"
}
```

## Security Features

- Password encryption using BCrypt
- Role-based access control
- Input validation and sanitization
- Global exception handling
- CORS configuration for frontend integration

## Database Schema

### Users Table
- `id` (Primary Key)
- `name`
- `email` (Unique)
- `password` (Encrypted)
- `role` (BUYER/SELLER)

### Products Table
- `id` (Primary Key)
- `product_name`
- `product_description`
- `category`
- `product_price`
- `product_offer_price`
- `quantity`
- `product_image`
- `seller_id` (Foreign Key to Users)
- `created_at`
- `updated_at`

## Development

### Project Structure
```
src/main/java/com/ecommerce/
├── config/          # Configuration classes
├── controller/      # REST controllers
├── dto/            # Data Transfer Objects
├── exception/      # Exception handling
├── model/          # Entity classes
├── repository/     # Data access layer
└── service/        # Business logic layer
```

### Adding New Features

1. Create entity in `model/` package
2. Create DTOs in `dto/` package
3. Create repository interface in `repository/` package
4. Create service interface and implementation in `service/` package
5. Create controller in `controller/` package
6. Add validation annotations where needed
7. Update security configuration if required

## Testing

Run tests using:
```bash
mvn test
```

## Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Add tests for new functionality
5. Submit a pull request

## License

This project is licensed under the MIT License. 