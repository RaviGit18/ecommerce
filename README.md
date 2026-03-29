# E-Commerce Application

A Spring Boot-based e-commerce application for inventory management with product and supplier tracking.

## 📋 Overview

This application provides a comprehensive e-commerce platform with features for managing inventory, products, and suppliers. It's built using Spring Boot with JPA for data persistence and H2 as the in-memory database.

## 🛠️ Technology Stack

- **Java 21**
- **Spring Boot 4.0.1**
- **Spring Data JPA**
- **Spring Web MVC**
- **H2 Database** (In-memory)
- **Lombok** (For reducing boilerplate code)
- **Maven** (Build tool)
- **Spring Validation** (For input validation)

## 🏗️ Project Structure

```
src/
├── main/
│   ├── java/com/ravi/ecommerce/
│   │   ├── EcommerceApplication.java     # Main application class
│   │   ├── controller/                    # REST controllers
│   │   │   └── EcommerceAppController.java
│   │   ├── entity/                        # JPA entities
│   │   │   ├── Product.java
│   │   │   ├── Inventory.java
│   │   │   └── Supplier.java
│   │   ├── service/                       # Business logic
│   │   ├── repository/                    # JPA repositories
│   │   ├── exception/                     # Custom exceptions
│   │   └── enums/                         # Enumerations
│   └── resources/
│       ├── application.properties         # Application configuration
│       ├── data.sql                      # Sample data initialization
│       ├── static/                       # Static resources
│       └── templates/                    # Template files
└── test/                                 # Test classes
```

## 🚀 Getting Started

### Prerequisites

- Java 21 or higher
- Maven 3.6 or higher

### Installation

1. **Clone the repository**
   ```bash
   git clone https://github.com/RaviGit18/ecommerce.git
   cd ecommerce
   ```

2. **Build the application**
   ```bash
   mvn clean install
   ```

3. **Run the application**
   ```bash
   mvn spring-boot:run
   ```

The application will start on `http://localhost:8080`

## 📊 Database Configuration

The application uses H2 in-memory database with the following configuration:

- **URL**: `jdbc:h2:mem:testdb`
- **Username**: `sa`
- **Password**: (empty)
- **H2 Console**: Available at `http://localhost:8080/h2`

## 🛍️ Features

### Core Entities

1. **Product**: Represents products with ID and name
2. **Inventory**: Manages inventory details including:
   - Brand name
   - Price
   - Color
   - Size
   - SKU ID
   - Quantity
   - Product and Supplier references
3. **Supplier**: Supplier information

### API Endpoints

#### Inventory Management

- **GET** `/brand/{brandName}`
  - Retrieves inventory details by brand name
  - Example: `GET /brand/Nike`

- **POST** `/brand/{brandName}`
  - Alternative endpoint for retrieving inventory by brand
  - Example: `POST /brand/Adidas`

- **GET** `/color/{color}`
  - Retrieves inventory details by color
  - Example: `GET /color/Red`

## 🧪 Testing

Run the test suite using:

```bash
mvn test
```

## 🔧 Configuration

Key application properties (in `application.properties`):

```properties
spring.application.name=ecommerce
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.h2.console.enabled=true
spring.h2.console.path=/h2
spring.jpa.show-sql=true
```

## 📝 Sample Data

The application includes sample data initialization through `data.sql`, which populates the database with initial products, inventory, and supplier records.

## 🐳 Docker Support (Optional)

To run the application using Docker:

1. **Build the Docker image**
   ```bash
   docker build -t ecommerce-app .
   ```

2. **Run the container**
   ```bash
   docker run -p 8080:8080 ecommerce-app
   ```

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 📞 Contact

- **Repository**: https://github.com/RaviGit18/ecommerce
- **LinkedIn**: [ravi-ranjan-tech](https://linkedin.com/in/ravi-ranjan-tech)

---

**Note**: This is a demo project for Spring Boot showcasing e-commerce functionality with inventory management.
