# Text Diff Highlighter Frontend

Angular frontend application for comparing and highlighting differences between texts.

## Prerequisites

- Node.js 18+ 
- npm 9+
- Angular CLI 17+

## Installation

```bash
# Install dependencies
npm install

# Install Angular CLI globally (optional, for ng commands)
npm install -g @angular/cli
```

## Running the Application

```bash
# Start the development server
npm start

# The application will be available at http://localhost:4200
```

## Building for Production

```bash
npm run build

# Build output will be in the dist/ directory
```

## Architecture

### Components

- **AppComponent**: Root component that wraps the application
- **DiffComponent**: Main component handling text input and diff display

### Services

- **DiffService**: Handles API communication with the backend for text comparison

### Key Features

- Two text input areas (side-by-side on desktop, stacked on mobile)
- Real-time comparison via backend API
- Color-coded diff display:
  - Gray: Unchanged text
  - Green: Added text (in Text 2)
  - Red: Removed text (in Text 1)
- Responsive design (desktop, tablet, mobile)
- Error handling for API failures
- Clear and Compare buttons for user actions

## Backend Configuration

The application is pre-configured to communicate with the backend at:

```
http://localhost:8080/api/diff
```

**API Endpoint Details:**
- Method: POST
- URL: `http://localhost:8080/api/diff`
- Request Body:
  ```json
  {
    "text1": "first text",
    "text2": "second text"
  }
  ```
- Response Body:
  ```json
  {
    "diffs": [
      { "type": "equal", "content": "unchanged text" },
      { "type": "delete", "content": "removed text" },
      { "type": "insert", "content": "added text" }
    ],
    "message": "optional message"
  }
  ```

### Changing Backend URL

To change the backend API URL, edit [src/app/services/diff.service.ts](src/app/services/diff.service.ts):

```typescript
private apiUrl = 'http://YOUR_BACKEND_URL:PORT/api/diff';
```

## Styling

The application uses custom CSS with:
- Responsive grid layouts
- Gradient backgrounds
- Smooth transitions and animations
- Mobile-first design approach

### Color Scheme

- Primary: #667eea (blue-purple)
- Secondary: #764ba2 (purple)
- Success/Insert: #c8e6c9 (green)
- Danger/Delete: #ffcdd2 (red)
- Text: #333
- Background: #f5f5f5

## File Structure

```
frontend/
├── src/
│   ├── app/
│   │   ├── components/
│   │   │   └── diff/
│   │   │       ├── diff.component.ts
│   │   │       ├── diff.component.html
│   │   │       └── diff.component.css
│   │   ├── services/
│   │   │   └── diff.service.ts
│   │   ├── app.component.ts
│   │   ├── app.component.html
│   │   ├── app.component.css
│   │   └── app.module.ts
│   ├── index.html
│   ├── main.ts
│   └── styles.css
├── angular.json
├── package.json
├── tsconfig.json
└── README.md
```

## Development

### Running Tests

```bash
npm test
```

### Linting

```bash
npm run lint
```

### Watching for Changes

```bash
npm run watch
```

## Performance

The application is optimized for smooth interactions:
- Lazy loading of modules (if needed)
- Change detection optimization
- Efficient DOM rendering for large diffs

## Browser Support

- Chrome (latest)
- Firefox (latest)
- Safari (latest)
- Edge (latest)
- Mobile browsers

## Troubleshooting

### Backend Connection Issues

If you see "Failed to compare texts. Please ensure the backend is running":
1. Verify the backend is running on http://localhost:8080
2. Check CORS configuration in the backend
3. Verify the API endpoint is `/api/diff`

### Port Already in Use

If port 4200 is already in use:
```bash
ng serve --port 4300
```

## License

MIT

## Contact

For issues or questions, please create an issue in the project repository.
