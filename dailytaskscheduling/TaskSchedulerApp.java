import com.sun.net.httpserver.*;
import java.io.*;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class TaskSchedulerApp {
    private static final int PORT = 8080;
    private static List<Task> tasks = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(PORT), 0);
        
        server.createContext("/api/tasks", new TasksHandler());
        server.createContext("/api/tasks/", new TaskDetailHandler());
        server.createContext("/api/stats", new StatsHandler());
        server.createContext("/", new WebHandler());
        
        server.setExecutor(null);
        server.start();
        
        System.out.println("==============================================");
        System.out.println("Daily Task Scheduler Server Started!");
        System.out.println("==============================================");
        System.out.println("[WEB] Open your browser: http://localhost:" + PORT);
        System.out.println("==============================================");
    }

    static class Task {
        public long id;
        public String title;
        public String description;
        public String date;
        public String time;
        public String priority;
        public String category;
        public boolean completed;
        public String createdAt;

        public Task(String title, String description, String date, String time, 
                   String priority, String category) {
            this.id = System.currentTimeMillis();
            this.title = title;
            this.description = description;
            this.date = date;
            this.time = time;
            this.priority = priority;
            this.category = category;
            this.completed = false;
            this.createdAt = LocalDateTime.now().toString();
        }

        public String toJson() {
            return "{" +
                    "\"id\":" + id + "," +
                    "\"title\":\"" + escapeJson(title) + "\"," +
                    "\"description\":\"" + escapeJson(description) + "\"," +
                    "\"date\":\"" + date + "\"," +
                    "\"time\":\"" + time + "\"," +
                    "\"priority\":\"" + priority + "\"," +
                    "\"category\":\"" + category + "\"," +
                    "\"completed\":" + completed + "," +
                    "\"createdAt\":\"" + createdAt + "\"" +
                    "}";
        }

        private static String escapeJson(String str) {
            if (str == null) return "";
            return str.replace("\\", "\\\\")
                     .replace("\"", "\\\"")
                     .replace("\n", "\\n")
                     .replace("\r", "\\r");
        }
    }

    static class TasksHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            enableCors(exchange);
            
            if (exchange.getRequestMethod().equals("OPTIONS")) {
                exchange.sendResponseHeaders(200, 0);
                exchange.close();
                return;
            }

            if (exchange.getRequestMethod().equals("GET")) {
                String query = exchange.getRequestURI().getQuery();
                String filter = "all";
                
                if (query != null && query.contains("filter=")) {
                    filter = query.split("filter=")[1].split("&")[0];
                }

                List<Task> filtered = filterTasks(filter);
                String json = tasksToJson(filtered);
                sendJsonResponse(exchange, json, 200);
            } 
            else if (exchange.getRequestMethod().equals("POST")) {
                String body = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
                Task task = jsonToTask(body);
                tasks.add(task);
                sendJsonResponse(exchange, task.toJson(), 201);
            } 
            else {
                sendJsonResponse(exchange, "{\"error\":\"Method not allowed\"}", 405);
            }
        }
    }

    static class TaskDetailHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            enableCors(exchange);
            
            if (exchange.getRequestMethod().equals("OPTIONS")) {
                exchange.sendResponseHeaders(200, 0);
                exchange.close();
                return;
            }

            String path = exchange.getRequestURI().getPath();
            long id = Long.parseLong(path.substring(path.lastIndexOf('/') + 1));
            
            if (exchange.getRequestMethod().equals("GET")) {
                Task task = tasks.stream()
                    .filter(t -> t.id == id)
                    .findFirst()
                    .orElse(null);
                
                if (task != null) {
                    sendJsonResponse(exchange, task.toJson(), 200);
                } else {
                    sendJsonResponse(exchange, "{\"error\":\"Task not found\"}", 404);
                }
            }
            else if (exchange.getRequestMethod().equals("PUT")) {
                String body = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
                Task task = tasks.stream()
                    .filter(t -> t.id == id)
                    .findFirst()
                    .orElse(null);
                
                if (task != null) {
                    updateTask(task, body);
                    sendJsonResponse(exchange, task.toJson(), 200);
                } else {
                    sendJsonResponse(exchange, "{\"error\":\"Task not found\"}", 404);
                }
            }
            else if (exchange.getRequestMethod().equals("DELETE")) {
                boolean removed = tasks.removeIf(t -> t.id == id);
                if (removed) {
                    sendJsonResponse(exchange, "{\"message\":\"Task deleted\"}", 200);
                } else {
                    sendJsonResponse(exchange, "{\"error\":\"Task not found\"}", 404);
                }
            }
            else {
                sendJsonResponse(exchange, "{\"error\":\"Method not allowed\"}", 405);
            }
        }
    }

    static class StatsHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            enableCors(exchange);
            
            if (exchange.getRequestMethod().equals("OPTIONS")) {
                exchange.sendResponseHeaders(200, 0);
                exchange.close();
                return;
            }

            if (exchange.getRequestMethod().equals("GET")) {
                String today = LocalDateTime.now().toString().split("T")[0];
                long totalTasks = tasks.size();
                long completedTasks = tasks.stream().filter(t -> t.completed).count();
                long pendingTasks = totalTasks - completedTasks;
                long todayTasks = tasks.stream().filter(t -> t.date.equals(today)).count();

                String json = "{" +
                        "\"total\":" + totalTasks + "," +
                        "\"completed\":" + completedTasks + "," +
                        "\"pending\":" + pendingTasks + "," +
                        "\"today\":" + todayTasks +
                        "}";
                
                sendJsonResponse(exchange, json, 200);
            } else {
                sendJsonResponse(exchange, "{\"error\":\"Method not allowed\"}", 405);
            }
        }
    }

    static class WebHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            if (exchange.getRequestURI().getPath().equals("/")) {
                String html = HtmlContent.getHtml();
                exchange.getResponseHeaders().set("Content-Type", "text/html; charset=utf-8");
                exchange.sendResponseHeaders(200, html.getBytes(StandardCharsets.UTF_8).length);
                exchange.getResponseBody().write(html.getBytes(StandardCharsets.UTF_8));
                exchange.close();
            } else {
                sendJsonResponse(exchange, "{\"error\":\"Not found\"}", 404);
            }
        }
    }

    static List<Task> filterTasks(String filter) {
        return switch (filter) {
            case "completed" -> tasks.stream().filter(t -> t.completed).collect(Collectors.toList());
            case "pending" -> tasks.stream().filter(t -> !t.completed).collect(Collectors.toList());
            case "high" -> tasks.stream().filter(t -> t.priority.equals("high")).collect(Collectors.toList());
            default -> new ArrayList<>(tasks);
        };
    }

    static String tasksToJson(List<Task> taskList) {
        String content = taskList.stream().map(Task::toJson).collect(Collectors.joining(","));
        return "[" + content + "]";
    }

    static Task jsonToTask(String json) {
        String title = extractJsonValue(json, "title");
        String description = extractJsonValue(json, "description");
        String date = extractJsonValue(json, "date");
        String time = extractJsonValue(json, "time");
        String priority = extractJsonValue(json, "priority");
        String category = extractJsonValue(json, "category");
        
        return new Task(title, description, date, time, priority, category);
    }

    static void updateTask(Task task, String json) {
        String title = extractJsonValue(json, "title");
        String description = extractJsonValue(json, "description");
        String date = extractJsonValue(json, "date");
        String time = extractJsonValue(json, "time");
        String priority = extractJsonValue(json, "priority");
        String category = extractJsonValue(json, "category");
        String completed = extractJsonValue(json, "completed");

        if (!title.isEmpty()) task.title = title;
        if (!description.isEmpty()) task.description = description;
        if (!date.isEmpty()) task.date = date;
        if (!time.isEmpty()) task.time = time;
        if (!priority.isEmpty()) task.priority = priority;
        if (!category.isEmpty()) task.category = category;
        if (!completed.isEmpty()) task.completed = Boolean.parseBoolean(completed);
    }

    static String extractJsonValue(String json, String key) {
        String pattern = "\"" + key + "\":\"([^\"]*)\"";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(pattern);
        java.util.regex.Matcher m = p.matcher(json);
        if (m.find()) {
            return m.group(1);
        }
        
        pattern = "\"" + key + "\":([a-z]+)";
        p = java.util.regex.Pattern.compile(pattern);
        m = p.matcher(json);
        if (m.find()) {
            return m.group(1);
        }
        
        return "";
    }

    static void sendJsonResponse(HttpExchange exchange, String json, int statusCode) throws IOException {
        exchange.getResponseHeaders().set("Content-Type", "application/json; charset=utf-8");
        byte[] response = json.getBytes(StandardCharsets.UTF_8);
        exchange.sendResponseHeaders(statusCode, response.length);
        exchange.getResponseBody().write(response);
        exchange.close();
    }

    static void enableCors(HttpExchange exchange) {
        exchange.getResponseHeaders().set("Access-Control-Allow-Origin", "*");
        exchange.getResponseHeaders().set("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        exchange.getResponseHeaders().set("Access-Control-Allow-Headers", "Content-Type");
    }
}
