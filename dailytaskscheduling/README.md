# Daily Task Scheduler - Java Web Application

A complete Daily Task Scheduling web application built with pure Java using the built-in HTTP server.

## 🎯 Features

### Backend (Java)
- **RESTful API** with CRUD operations
- **Built-in HTTP Server** (Java's HttpServer API)
- **In-memory data storage** with filtering capabilities
- **CORS enabled** for cross-origin requests
- **JSON-based communication**
- **Real-time statistics** calculation
- **Task filtering** by status and priority

### Frontend (HTML/CSS/JavaScript)
- **Modern responsive UI** with gradient design
- **Real-time task management** via API calls
- **Filter and search** functionality
- **Statistics dashboard**
- **Edit modal** for updating tasks
- **Task categorization** (Work, Personal, Health, Education, Other)
- **Priority levels** (Low, Medium, High)
- **Mobile-friendly** responsive layout

## 📋 Prerequisites

- Java 11 or higher
- Any modern web browser

## 🚀 How to Compile and Run

### Step 1: Compile the Java Program
Open PowerShell in the `e:\dailytaskscheduling` directory and run:

```powershell
javac TaskSchedulerApp.java
```

### Step 2: Run the Application
```powershell
java TaskSchedulerApp
```

You should see output like:
```
==============================================
Daily Task Scheduler Server Started!
==============================================
🌐 Open your browser and go to: http://localhost:8080
==============================================
```

### Step 3: Open in Browser
Open your web browser and navigate to:
```
http://localhost:8080
```

## 📚 API Endpoints

### GET /api/tasks
Get all tasks or filter by status/priority
- Query parameters:
  - `filter=all` - All tasks
  - `filter=pending` - Pending tasks
  - `filter=completed` - Completed tasks
  - `filter=high` - High priority tasks

**Example:** `http://localhost:8080/api/tasks?filter=pending`

### POST /api/tasks
Add a new task
- Request body:
```json
{
  "title": "Task Title",
  "description": "Task Description",
  "date": "2025-12-07",
  "time": "14:30",
  "priority": "high",
  "category": "work"
}
```

### GET /api/tasks/{id}
Get a specific task by ID

### PUT /api/tasks/{id}
Update a task by ID

### DELETE /api/tasks/{id}
Delete a task by ID

### GET /api/stats
Get task statistics
- Returns:
```json
{
  "total": 5,
  "completed": 2,
  "pending": 3,
  "today": 1
}
```

## 🎨 User Interface

### Left Sidebar
- **Add New Task Form**
  - Task Title (required)
  - Description (optional)
  - Due Date (required)
  - Due Time (optional)
  - Priority Level
  - Category

### Right Section
- **Filter Buttons** (All, Pending, Completed, High Priority)
- **Task Cards** with:
  - Title and Description
  - Due Date and Time
  - Priority Badge
  - Status Badge
  - Category
  - Action Buttons (Complete, Edit, Delete)

### Bottom Section
- **Statistics Dashboard**
  - Total Tasks
  - Completed Tasks
  - Pending Tasks
  - Today's Tasks

## 📝 How to Use

1. **Add a Task**
   - Fill in the form on the left side
   - Click "Add Task"

2. **View Tasks**
   - Tasks appear on the right side
   - Use filter buttons to view different task sets

3. **Complete a Task**
   - Click the "✓ Complete" button on a task card
   - Click "↩️ Undo" to mark as incomplete

4. **Edit a Task**
   - Click the "✏️ Edit" button
   - Modify the details in the modal
   - Click "Save Changes"

5. **Delete a Task**
   - Click the "🗑️ Delete" button
   - Confirm the deletion

6. **Track Progress**
   - View statistics at the bottom of the page
   - Statistics update automatically in real-time

## 🔧 Technical Details

### Architecture
- **Backend:** Java HttpServer (com.sun.net.httpserver)
- **Frontend:** Vanilla HTML, CSS, and JavaScript
- **Communication:** RESTful API with JSON
- **Data Storage:** In-memory List (can be extended to database)

### Key Classes
- `TaskSchedulerApp` - Main server class
- `Task` - Data model for tasks
- `TasksHandler` - Handles GET/POST requests for tasks
- `TaskDetailHandler` - Handles GET/PUT/DELETE for individual tasks
- `StatsHandler` - Handles statistics requests
- `WebHandler` - Serves the HTML frontend

## 🌐 Browser Compatibility

- Chrome/Edge (Latest)
- Firefox (Latest)
- Safari (Latest)
- Opera (Latest)

## 📱 Responsive Design

The application is fully responsive and works on:
- Desktop
- Tablet
- Mobile devices

## 🛠️ Extending the Application

### To Add Database Support
1. Import a JDBC driver (MySQL, PostgreSQL, etc.)
2. Modify the `Task` class to include database operations
3. Update the handlers to use database queries instead of in-memory storage

### To Add Authentication
1. Add a login handler
2. Implement JWT token generation and validation
3. Add user association to tasks

### To Add Notifications
1. Integrate email or push notification service
2. Add reminders for upcoming tasks
3. Send completion confirmations

## 🐛 Troubleshooting

### Port Already in Use
If port 8080 is already in use, modify the `PORT` variable in TaskSchedulerApp.java:
```java
private static final int PORT = 8081; // or any other available port
```

### Connection Refused
- Ensure the server is running
- Check that the URL is correct (http://localhost:8080)
- Check your firewall settings

### Tasks Not Persisting
The current implementation stores tasks in memory only. They will be lost when the server restarts. To persist data, implement database integration.

## 📄 License

This is a demonstration project for educational purposes.

## 👨‍💻 Author

Created as a comprehensive Daily Task Scheduling application example.
