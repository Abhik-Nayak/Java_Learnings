# Ecommerce Backend API

A Spring Boot-based REST API for an ecommerce platform with user authentication and product management.

## Features

- **User Authentication**: Register and login with JWT tokens
- **Role-based Access**: Separate buyer and seller roles
- **Product Management**: CRUD operations for products (sellers only)
- **Stock Management**: Track product availability
- **Secure API**: JWT-based authentication and authorization
- **PostgreSQL Database**: Persistent data storage

## Prerequisites

- Java 21
- Maven 3.6+
- PostgreSQL 12+
- IDE (IntelliJ IDEA, Eclipse, or VS Code)

## Setup Instructions

### 1. Database Setup

1. Install PostgreSQL if not already installed
2. Create a new database:
   ```sql
   CREATE DATABASE ecommerce_db;
   ```
3. Create a user (optional):
   ```sql
   CREATE USER postgres WITH PASSWORD 'password';
   GRANT ALL PRIVILEGES ON DATABASE ecommerce_db TO postgres;
   ```

### 2. Application Configuration

Update `src/main/resources/application.properties` with your database credentials:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/ecommerce_db
spring.datasource.username=your_username
spring.datasource.password=your_password
```

### 3. Build and Run

```bash
# Clean and build the project
mvn clean install

# Run the application
mvn spring-boot:run
```

The application will start on `http://localhost:8080`

## API Endpoints

### Authentication

#### Register User
```
POST /api/auth/register
Content-Type: application/json

{
  "username": "john_doe",
  "email": "john@example.com",
  "password": "password123",
  "fullName": "John Doe",
  "role": "SELLER"  // or "BUYER"
}
```

#### Login
```
POST /api/auth/login
Content-Type: application/json

{
  "username": "john_doe",
  "password": "password123"
}
```

### Product Management (Seller Only)

#### Create Product
```
POST /api/products
Authorization: Bearer <jwt_token>
Content-Type: application/json

{
  "productName": "iPhone 15",
  "productDescription": "Latest iPhone model",
  "category": "Electronics",
  "productPrice": 999.99,
  "productOfferPrice": 899.99,
  "quantity": 10,
  "productImage": "iphone15.jpg"
}
```

#### Get My Products
```
GET /api/products/my-products
Authorization: Bearer <jwt_token>
```

#### Update Product
```
PUT /api/products/{id}
Authorization: Bearer <jwt_token>
Content-Type: application/json

{
  "productName": "iPhone 15 Pro",
  "productDescription": "Updated description",
  "category": "Electronics",
  "productPrice": 1099.99,
  "productOfferPrice": 999.99,
  "quantity": 5,
  "productImage": "iphone15pro.jpg"
}
```

#### Delete Product
```
DELETE /api/products/{id}
Authorization: Bearer <jwt_token>
```

### Public Product Endpoints

#### Get All Products
```
GET /api/products/public/all
```

#### Get Available Products
```
GET /api/products/public/available
```

#### Get Product by ID
```
GET /api/products/public/{id}
```

#### Get Products by Category
```
GET /api/products/public/category/{category}
```

#### Search Products
```
GET /api/products/public/search?name=iphone
```

## Project Structure

```
src/main/java/com/ecommerce/
├── config/                 # Configuration classes
│   ├── SecurityConfig.java
│   └── ApplicationConfig.java
├── controller/             # REST controllers
│   ├── AuthController.java
│   └── ProductController.java
├── dto/                   # Data Transfer Objects
│   ├── UserRegistrationDto.java
│   ├── ProductDto.java
│   └── AuthResponseDto.java
├── entity/                # JPA entities
│   ├── User.java
│   └── Product.java
├── exception/             # Exception handling
│   └── GlobalExceptionHandler.java
├── repository/            # Data access layer
│   ├── UserRepository.java
│   └── ProductRepository.java
├── security/              # Security components
│   ├── JwtService.java
│   └── JwtAuthenticationFilter.java
├── service/               # Business logic
│   ├── AuthService.java
│   └── ProductService.java
└── EcommerceBackendApplication.java
```

## Key Learning Points

### 1. Spring Boot Basics
- **@SpringBootApplication**: Main application class
- **@RestController**: Creates REST endpoints
- **@Service**: Business logic layer
- **@Repository**: Data access layer
- **@Entity**: JPA database entities

### 2. Security Implementation
- **JWT Authentication**: Stateless token-based authentication
- **Role-based Authorization**: Different permissions for buyers and sellers
- **Password Encryption**: BCrypt password hashing
- **Security Filters**: Custom JWT filter for token validation

### 3. Database Integration
- **JPA/Hibernate**: Object-relational mapping
- **Repository Pattern**: Data access abstraction
- **Entity Relationships**: Many-to-one relationship between products and sellers
- **Validation**: Bean validation for data integrity

### 4. API Design
- **RESTful Endpoints**: Standard HTTP methods and status codes
- **DTO Pattern**: Separate objects for API requests/responses
- **Exception Handling**: Global exception handler for consistent error responses
- **CORS Configuration**: Cross-origin resource sharing

## Testing the API

### Using Postman or similar tool:

1. **Register a Seller**:
   - POST `/api/auth/register`
   - Body: Seller registration data
   - Save the JWT token from response

2. **Login**:
   - POST `/api/auth/login`
   - Body: Username and password
   - Save the JWT token

3. **Create Product** (as seller):
   - POST `/api/products`
   - Header: `Authorization: Bearer <jwt_token>`
   - Body: Product data

4. **View Products** (public):
   - GET `/api/products/public/all`
   - No authentication required

## Next Steps

To extend this application, consider adding:

1. **Order Management**: Shopping cart and order processing
2. **Payment Integration**: Payment gateway integration
3. **Image Upload**: File upload for product images
4. **Email Notifications**: Order confirmations and updates
5. **Product Reviews**: Customer reviews and ratings
6. **Inventory Management**: Advanced stock tracking
7. **Admin Panel**: Administrative functions
8. **API Documentation**: Swagger/OpenAPI documentation

## Troubleshooting

### Common Issues:

1. **Database Connection Error**:
   - Verify PostgreSQL is running
   - Check database credentials in `application.properties`
   - Ensure database exists

2. **JWT Token Issues**:
   - Check JWT secret in `application.properties`
   - Verify token format: `Bearer <token>`

3. **Permission Denied**:
   - Ensure user has correct role (SELLER for product management)
   - Verify JWT token is valid and not expired

4. **Validation Errors**:
   - Check request body format
   - Ensure all required fields are provided
   - Verify data types and constraints 