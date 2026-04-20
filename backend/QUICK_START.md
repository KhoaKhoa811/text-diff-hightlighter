# Backend - Quick Start Guide

## 🚀 Fast Setup (3 Steps)

### Step 1: Build the Project
```bash
cd /home/khoa/Workspace/custom_agent_test/backend
mvn clean install
```

### Step 2: Run the Application
```bash
mvn spring-boot:run
```
**OR** use the compiled JAR:
```bash
java -jar target/text-diff-highlighter-1.0.0.jar
```

### Step 3: Test the API
```bash
curl -X POST http://localhost:8080/api/diff \
  -H "Content-Type: application/json" \
  -d '{"text1": "Hello world", "text2": "Hello new world"}'
```

## ✅ Expected Response

```json
{
  "differences": [
    {"type": "unchanged", "text": "Hello "},
    {"type": "added", "text": "new "},
    {"type": "unchanged", "text": "world"}
  ]
}
```

## 📋 Project Structure Summary

| File | Purpose |
|------|---------|
| `pom.xml` | Maven configuration & dependencies |
| `TextDiffApplication.java` | Spring Boot entry point |
| `DiffController.java` | REST API endpoints (`POST /api/diff`, `GET /api/health`) |
| `DiffService.java` | Business logic layer |
| `DiffAlgorithm.java` | Character-level diff algorithm (LCS-based) |
| `DiffRequest.java` | Request DTO (text1, text2) |
| `DiffResponse.java` | Response DTO (differences list) |
| `DiffSegment.java` | Individual diff segment (type + text) |
| `application.properties` | Server config (port 8080) |

## 🔌 API Endpoints

### 1. Compute Differences
- **URL:** `POST /api/diff`
- **Body:** `{"text1": "string", "text2": "string"}`
- **Response:** `{"differences": [{"type": "unchanged|added|removed", "text": "string"}]}`

### 2. Health Check
- **URL:** `GET /api/health`
- **Response:** `"Text Diff Highlighter API is running"`

## 🛠 Technology Stack

- **Spring Boot 3.2.0** - REST framework
- **Java 17** - Language
- **Maven** - Build tool
- **Longest Common Subsequence Algorithm** - Diff computation

## 📦 Dependencies Included

- `spring-boot-starter-web` - REST API
- `spring-boot-starter-validation` - Input validation
- `spring-boot-devtools` - Hot reload during development
- `junit 5` - Testing

## ⚙ Configuration

Edit `src/main/resources/application.properties`:

```properties
server.port=8080                    # API port
logging.level.com.textdiff=DEBUG    # Log verbosity
```

## 🔍 Algorithm Explanation

The diff engine uses **Longest Common Subsequence (LCS)**:

1. Builds an LCS matrix comparing character-by-character
2. Backtracks to identify:
   - **Unchanged** - Characters in both texts
   - **Added** - Characters only in text2
   - **Removed** - Characters only in text1
3. Merges consecutive segments of same type

**Time Complexity:** O(m×n) where m, n are string lengths
**Space Complexity:** O(m×n)

## 🚨 Troubleshooting

| Issue | Solution |
|-------|----------|
| Build fails | `mvn clean` and retry, ensure Java 17+ |
| Port 8080 in use | Change port in `application.properties` or kill process: `lsof -i :8080` |
| JAR not found | Run `mvn clean package` first |
| Connection refused | Ensure app is running: check port 8080 is listening |

## 📡 Frontend Integration

The API is ready for integration with any frontend framework:

```javascript
// JavaScript example
const response = await fetch('http://localhost:8080/api/diff', {
  method: 'POST',
  headers: { 'Content-Type': 'application/json' },
  body: JSON.stringify({
    text1: 'Hello world',
    text2: 'Hello new world'
  })
});
const data = await response.json();
console.log(data.differences);
```

CORS is enabled for all origins (configurable in `DiffController.java`).

## 📝 Example Test Cases

### Example 1: Simple Addition
**Input:** `text1: "Hello"`, `text2: "Hello world"`
**Output:**
```json
[
  {"type": "unchanged", "text": "Hello"},
  {"type": "added", "text": " world"}
]
```

### Example 2: Replacement
**Input:** `text1: "cat"`, `text2: "dog"`
**Output:**
```json
[
  {"type": "removed", "text": "c"},
  {"type": "added", "text": "d"},
  {"type": "unchanged", "text": "o"},
  {"type": "removed", "text": "t"},
  {"type": "added", "text": "g"}
]
```

### Example 3: No Changes
**Input:** `text1: "same"`, `text2: "same"`
**Output:**
```json
[
  {"type": "unchanged", "text": "same"}
]
```

## 🔄 Development Workflow

```bash
# Terminal 1: Keep the app running with hot-reload
mvn spring-boot:run

# Terminal 2: Make API calls
curl -X POST http://localhost:8080/api/diff \
  -H "Content-Type: application/json" \
  -d '{"text1": "test", "text2": "testing"}'

# Edit source files → Auto-reloaded by DevTools!
```

## 📦 Production Deployment

```bash
# Build production JAR
mvn clean package

# Run without hot-reload overhead
java -jar target/text-diff-highlighter-1.0.0.jar --server.port=8080
```

## 📚 Full Documentation

See [README.md](./README.md) for complete documentation.
