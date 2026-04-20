# Text Diff Highlighter - Backend API

A Spring Boot REST API for comparing and highlighting differences between two text strings.

## Project Structure

```
backend/
├── pom.xml
├── README.md
└── src/
    ├── main/
    │   ├── java/
    │   │   └── com/textdiff/
    │   │       ├── TextDiffApplication.java           # Main Spring Boot application
    │   │       ├── controller/
    │   │       │   └── DiffController.java             # REST API endpoints
    │   │       ├── service/
    │   │       │   └── DiffService.java                # Business logic layer
    │   │       ├── model/
    │   │       │   ├── DiffRequest.java                # Request DTO
    │   │       │   ├── DiffResponse.java               # Response DTO
    │   │       │   └── DiffSegment.java                # Individual diff segment
    │   │       └── util/
    │   │           └── DiffAlgorithm.java              # Diff algorithm implementation
    │   └── resources/
    │       └── application.properties                  # Application configuration
    └── test/
```

## API Endpoints

### 1. POST `/api/diff` - Compute Text Differences

**Request:**
```json
{
  "text1": "Hello world",
  "text2": "Hello new world"
}
```

**Response:**
```json
{
  "differences": [
    {
      "type": "unchanged",
      "text": "Hello "
    },
    {
      "type": "added",
      "text": "new "
    },
    {
      "type": "unchanged",
      "text": "world"
    }
  ]
}
```

**Segment Types:**
- `unchanged`: Text that appears in both text1 and text2
- `added`: Text that appears in text2 but not in text1
- `removed`: Text that appears in text1 but not in text2

### 2. GET `/api/health` - Health Check

**Response:**
```
Text Diff Highlighter API is running
```

## Prerequisites

- Java 17 or higher
- Maven 3.6 or higher
- Git (optional)

## Installation & Setup

### 1. Navigate to the Backend Directory
```bash
cd /home/khoa/Workspace/custom_agent_test/backend
```

### 2. Build the Project
```bash
mvn clean install
```

This will:
- Download all dependencies specified in `pom.xml`
- Compile the Java source code
- Run tests (if any)
- Create a JAR file in the `target/` directory

### 3. Run the Application

**Option A: Using Maven**
```bash
mvn spring-boot:run
```

**Option B: Using the Generated JAR**
```bash
java -jar target/text-diff-highlighter-1.0.0.jar
```

The application will start on `http://localhost:8080`

## Testing the API

### Using cURL

```bash
# Test the diff endpoint
curl -X POST http://localhost:8080/api/diff \
  -H "Content-Type: application/json" \
  -d '{
    "text1": "Hello world",
    "text2": "Hello new world"
  }'

# Test the health endpoint
curl http://localhost:8080/api/health
```

### Using Postman

1. Create a new POST request
2. URL: `http://localhost:8080/api/diff`
3. Headers: `Content-Type: application/json`
4. Body (raw JSON):
```json
{
  "text1": "Hello world",
  "text2": "Hello new world"
}
```
5. Click Send

## Technology Stack

- **Spring Boot 3.2.0**: Web framework and dependency injection
- **Java 17**: Programming language
- **Maven**: Build and dependency management
- **Java Diff Utils 1.3.0**: Utility library for diff operations (available for advanced use)

## Algorithm Details

The diff algorithm uses a **Longest Common Subsequence (LCS)** approach:

1. **Compute LCS Matrix**: Identifies the longest common subsequence between two strings
2. **Backtrack**: Traces through the matrix to build diff segments
3. **Merge**: Combines consecutive segments of the same type for cleaner output

This approach is efficient for most text comparisons and handles:
- Character-level granularity
- Proper identification of additions, removals, and unchanged text
- Minimal diff output by consolidating consecutive same-type segments

## Configuration

Edit `src/main/resources/application.properties` to customize:

- **Server Port**: Change `server.port=8080` to another port
- **Logging Level**: Adjust `logging.level.com.textdiff=DEBUG` for verbosity
- **JSON Formatting**: Modify Jackson settings as needed

## Error Handling

The API implements proper error handling:

- **400 Bad Request**: Invalid or missing `text1`/`text2` fields
- **500 Internal Server Error**: Unexpected server errors
- **Cross-Origin Requests**: CORS is enabled for all origins (configurable in controller)

## Dependencies

### Core Dependencies
- `spring-boot-starter-web`: REST API support
- `spring-boot-starter-validation`: Input validation (JSR-303)

### Optional Dependencies
- `java-diff-utils`: Advanced diff algorithms
- `lombok`: Reduce boilerplate code
- `spring-boot-devtools`: Development tools

## Frontend Integration

The backend provides a simple JSON API that can be consumed by any frontend framework:

### Expected Request Format
```typescript
interface DiffRequest {
  text1: string;
  text2: string;
}
```

### Expected Response Format
```typescript
interface DiffSegment {
  type: "unchanged" | "added" | "removed";
  text: string;
}

interface DiffResponse {
  differences: DiffSegment[];
}
```

### Example Frontend Call (JavaScript)
```javascript
async function computeDiff(text1, text2) {
  const response = await fetch('http://localhost:8080/api/diff', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify({
      text1: text1,
      text2: text2
    })
  });
  
  return await response.json();
}
```

## Development

### Running in Development Mode
```bash
mvn spring-boot:run
```

The application will auto-reload on code changes due to Spring Boot DevTools.

### Building a Production JAR
```bash
mvn clean package
```

The JAR will be available at `target/text-diff-highlighter-1.0.0.jar`

## Troubleshooting

### Port Already in Use
If port 8080 is already in use:
```bash
# On Linux/Mac
lsof -i :8080
kill -9 <PID>

# Or change the port in application.properties
server.port=8081
```

### Maven Build Fails
```bash
# Clear Maven cache
mvn clean
rm -rf ~/.m2/repository

# Rebuild
mvn clean install
```

### Java Version Issues
Verify Java version:
```bash
java -version
```

Must be Java 17 or higher. If not installed, download from [java.com](https://www.java.com)

## License

This project is created for demonstration purposes.

## Support

For issues or questions, check the logs in the console output or enable DEBUG logging.
