# Swagger/OpenAPI Documentation Guide

## Overview

The e-commerce application includes comprehensive API documentation using Swagger/OpenAPI 3.0, providing interactive API testing and detailed endpoint documentation.

## Access Points

### Swagger UI
- **URL**: `http://localhost:8080/swagger-ui.html`
- **Description**: Interactive API documentation interface
- **Features**: Try out endpoints, view schemas, explore parameters

### OpenAPI JSON
- **URL**: `http://localhost:8080/v3/api-docs`
- **Description**: Raw OpenAPI specification in JSON format
- **Usage**: For integration with other API documentation tools

## Documentation Features

### Interactive API Testing
- **Try it out**: Test endpoints directly from your browser
- **Parameter validation**: Automatic input validation
- **Response examples**: Sample responses for all endpoints
- **Error handling**: View error responses and status codes

### Detailed Descriptions
- **Endpoint summaries**: Clear, concise descriptions
- **Parameter documentation**: Detailed parameter explanations
- **Response schemas**: Automatic model documentation
- **Example values**: Sample data for all parameters

### API Organization
The documentation is organized into logical groups:

#### Inventory Management
- Core CRUD operations
- Create, read, update, delete inventory items
- Business logic validation

#### Search Operations
- Filter by brand, color, size
- Seller-specific queries
- Advanced search capabilities

#### Projection Endpoints
- Optimized data views
- Basic, price, and summary projections
- Performance-optimized responses

#### Pagination
- Paginated data retrieval
- Sorting options
- Efficient large dataset handling

## Using Swagger UI

### Step-by-Step Guide

1. **Start the Application**
   ```bash
   ./gradlew.bat bootRun
   ```

2. **Access Swagger UI**
   - Open your browser
   - Navigate to `http://localhost:8080/swagger-ui.html`

3. **Explore Endpoints**
   - Browse the API tags on the left
   - Click on any endpoint to expand details
   - View parameters, schemas, and responses

4. **Test Endpoints**
   - Click "Try it out" button
   - Fill in required parameters
   - Click "Execute" to test the endpoint
   - View response in real-time

### Features Available

#### Request Testing
- **Path parameters**: Dynamic values in URL paths
- **Query parameters**: Optional filtering and sorting
- **Request body**: JSON payloads for POST/PUT operations
- **Headers**: Custom headers (if required)

#### Response Analysis
- **Status codes**: HTTP response status explanations
- **Response body**: Actual API responses
- **Headers**: Response headers information
- **Duration**: Request processing time

#### Schema Documentation
- **Models**: Data structure definitions
- **Properties**: Field descriptions and types
- **Examples**: Sample data for models
- **Validation**: Input constraints and rules

## API Endpoints Documentation

### Core Endpoints

#### Inventory CRUD
- `POST /api/v1/inventory` - Create inventory item
- `GET /api/v1/inventory/{id}` - Get inventory by ID
- `PUT /api/v1/inventory/{id}` - Update inventory item
- `DELETE /api/v1/inventory/{id}` - Delete inventory item

#### Search Operations
- `GET /api/v1/inventory/brand/{brandName}` - Search by brand
- `GET /api/v1/inventory/color/{color}` - Search by color
- `GET /api/v1/inventory/size/{size}` - Search by size
- `GET /api/v1/inventory/seller/{sellerId}` - Get seller product count

#### Projection Endpoints
- `GET /api/v1/inventory/projections/basic` - Basic projections
- `GET /api/v1/inventory/projections/price` - Price projections
- `GET /api/v1/inventory/projections/summary` - Summary projections

#### Pagination
- `GET /api/v1/inventory/paginated` - Paginated inventory list
- `GET /api/v1/inventory/paginated/brand/{brandName}` - Paginated by brand
- Additional pagination endpoints for color, size, supplier

## Configuration

### Swagger Configuration
The API documentation is configured in `SwaggerConfig.java`:

```java
@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("E-commerce API")
                        .description("A comprehensive REST API for e-commerce inventory management")
                        .version("1.0.0")
                        // ... additional configuration
                );
    }
}
```

### Controller Annotations
Each controller method includes comprehensive annotations:

```java
@Operation(summary = "Get inventory by brand", description = "Retrieve all inventory items for a specific brand")
@ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Successfully retrieved inventory items"),
    @ApiResponse(responseCode = "204", description = "No inventory items found for the brand"),
    @ApiResponse(responseCode = "400", description = "Invalid brand name provided")
})
public ResponseEntity<List<InventoryDto>> getInventoryDetailsByBrand(
    @Parameter(description = "Brand name to search for", required = true, example = "Nike")
    @PathVariable String brandName) {
    // implementation
}
```

## Integration with Other Tools

### OpenAPI Generator
Use the OpenAPI specification to generate:
- Client SDKs
- Server stubs
- API documentation in various formats

### API Testing Tools
Import the OpenAPI spec into:
- Postman
- Insomnia
- Other API testing tools

### Continuous Integration
Automate API documentation testing:
- Validate OpenAPI specification
- Test endpoint availability
- Monitor API changes

## Best Practices

### Documentation Maintenance
- Keep descriptions accurate and up-to-date
- Update examples when API changes
- Review parameter documentation regularly
- Test all documented endpoints

### Performance Considerations
- Use projections for large datasets
- Implement proper pagination
- Optimize database queries
- Cache frequently accessed data

### Security Notes
- Document authentication requirements
- Explain authorization scopes
- Provide security examples
- Include rate limiting information

## Troubleshooting

### Common Issues

#### Swagger UI Not Accessible
- Ensure application is running
- Check port configuration (default: 8080)
- Verify no firewall blocking
- Check application logs for errors

#### Missing Endpoints
- Verify controller annotations
- Check package scanning configuration
- Ensure endpoints are properly mapped
- Review Spring Boot configuration

#### Schema Issues
- Verify DTO class annotations
- Check Jackson serialization
- Review model relationships
- Validate JSON structure

### Debug Tips
- Enable debug logging
- Check Spring Boot startup logs
- Verify bean configuration
- Test endpoints manually

## Conclusion

The Swagger/OpenAPI documentation provides comprehensive, interactive API documentation that enhances developer experience and facilitates API integration. Regular maintenance and updates ensure the documentation remains accurate and useful.
