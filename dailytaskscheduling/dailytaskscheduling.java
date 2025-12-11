// Source code is decompiled from a .class file using FernFlower decompiler (from Intellij IDEA).
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class TaskSchedulerApp {
   private static final int PORT = 8080;
   private static List<Task> tasks = new ArrayList();

   public TaskSchedulerApp() {
   }

   public static void main(String[] var0) throws IOException {
      HttpServer var1 = HttpServer.create(new InetSocketAddress(8080), 0);
      var1.createContext("/api/tasks", new TasksHandler());
      var1.createContext("/api/tasks/", new TaskDetailHandler());
      var1.createContext("/api/stats", new StatsHandler());
      var1.createContext("/", new WebHandler());
      var1.setExecutor((Executor)null);
      var1.start();
      System.out.println("==============================================");
      System.out.println("Daily Task Scheduler Server Started!");
      System.out.println("==============================================");
      System.out.println("[WEB] Open your browser: http://localhost:8080");
      System.out.println("==============================================");
   }

   static List<Task> filterTasks(String var0) {
      Object var10000;
      switch (var0) {
         case "completed":
            var10000 = (List)tasks.stream().filter((var0x) -> {
               return var0x.completed;
            }).collect(Collectors.toList());
            break;
         case "pending":
            var10000 = (List)tasks.stream().filter((var0x) -> {
               return !var0x.completed;
            }).collect(Collectors.toList());
            break;
         case "high":
            var10000 = (List)tasks.stream().filter((var0x) -> {
               return var0x.priority.equals("high");
            }).collect(Collectors.toList());
            break;
         default:
            var10000 = new ArrayList(tasks);
      }

      return (List)var10000;
   }

   static String tasksToJson(List<Task> var0) {
      String var1 = (String)var0.stream().map(Task::toJson).collect(Collectors.joining(","));
      return "[" + var1 + "]";
   }

   static Task jsonToTask(String var0) {
      String var1 = extractJsonValue(var0, "title");
      String var2 = extractJsonValue(var0, "description");
      String var3 = extractJsonValue(var0, "date");
      String var4 = extractJsonValue(var0, "time");
      String var5 = extractJsonValue(var0, "priority");
      String var6 = extractJsonValue(var0, "category");
      return new Task(var1, var2, var3, var4, var5, var6);
   }

   static void updateTask(Task var0, String var1) {
      String var2 = extractJsonValue(var1, "title");
      String var3 = extractJsonValue(var1, "description");
      String var4 = extractJsonValue(var1, "date");
      String var5 = extractJsonValue(var1, "time");
      String var6 = extractJsonValue(var1, "priority");
      String var7 = extractJsonValue(var1, "category");
      String var8 = extractJsonValue(var1, "completed");
      if (!var2.isEmpty()) {
         var0.title = var2;
      }

      if (!var3.isEmpty()) {
         var0.description = var3;
      }

      if (!var4.isEmpty()) {
         var0.date = var4;
      }

      if (!var5.isEmpty()) {
         var0.time = var5;
      }

      if (!var6.isEmpty()) {
         var0.priority = var6;
      }

      if (!var7.isEmpty()) {
         var0.category = var7;
      }

      if (!var8.isEmpty()) {
         var0.completed = Boolean.parseBoolean(var8);
      }

   }

   static String extractJsonValue(String var0, String var1) {
      String var2 = "\"" + var1 + "\":\"([^\"]*)\"";
      Pattern var3 = Pattern.compile(var2);
      Matcher var4 = var3.matcher(var0);
      if (var4.find()) {
         return var4.group(1);
      } else {
         var2 = "\"" + var1 + "\":([a-z]+)";
         var3 = Pattern.compile(var2);
         var4 = var3.matcher(var0);
         return var4.find() ? var4.group(1) : "";
      }
   }

   static void sendJsonResponse(HttpExchange var0, String var1, int var2) throws IOException {
      var0.getResponseHeaders().set("Content-Type", "application/json; charset=utf-8");
      byte[] var3 = var1.getBytes(StandardCharsets.UTF_8);
      var0.sendResponseHeaders(var2, (long)var3.length);
      var0.getResponseBody().write(var3);
      var0.close();
   }

   static void enableCors(HttpExchange var0) {
      var0.getResponseHeaders().set("Access-Control-Allow-Origin", "*");
      var0.getResponseHeaders().set("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
      var0.getResponseHeaders().set("Access-Control-Allow-Headers", "Content-Type");
   }

   static class TasksHandler implements HttpHandler {
      TasksHandler() {
      }

      public void handle(HttpExchange var1) throws IOException {
         TaskSchedulerApp.enableCors(var1);
         if (var1.getRequestMethod().equals("OPTIONS")) {
            var1.sendResponseHeaders(200, 0L);
            var1.close();
         } else {
            String var2;
            if (var1.getRequestMethod().equals("GET")) {
               var2 = var1.getRequestURI().getQuery();
               String var3 = "all";
               if (var2 != null && var2.contains("filter=")) {
                  var3 = var2.split("filter=")[1].split("&")[0];
               }

               List var4 = TaskSchedulerApp.filterTasks(var3);
               String var5 = TaskSchedulerApp.tasksToJson(var4);
               TaskSchedulerApp.sendJsonResponse(var1, var5, 200);
            } else if (var1.getRequestMethod().equals("POST")) {
               var2 = new String(var1.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
               Task var6 = TaskSchedulerApp.jsonToTask(var2);
               TaskSchedulerApp.tasks.add(var6);
               TaskSchedulerApp.sendJsonResponse(var1, var6.toJson(), 201);
            } else {
               TaskSchedulerApp.sendJsonResponse(var1, "{\"error\":\"Method not allowed\"}", 405);
            }

         }
      }
   }

   static class TaskDetailHandler implements HttpHandler {
      TaskDetailHandler() {
      }

      public void handle(HttpExchange var1) throws IOException {
         TaskSchedulerApp.enableCors(var1);
         if (var1.getRequestMethod().equals("OPTIONS")) {
            var1.sendResponseHeaders(200, 0L);
            var1.close();
         } else {
            String var2 = var1.getRequestURI().getPath();
            long var3 = Long.parseLong(var2.substring(var2.lastIndexOf(47) + 1));
            if (var1.getRequestMethod().equals("GET")) {
               Task var5 = (Task)TaskSchedulerApp.tasks.stream().filter((var2x) -> {
                  return var2x.id == var3;
               }).findFirst().orElse((Object)null);
               if (var5 != null) {
                  TaskSchedulerApp.sendJsonResponse(var1, var5.toJson(), 200);
               } else {
                  TaskSchedulerApp.sendJsonResponse(var1, "{\"error\":\"Task not found\"}", 404);
               }
            } else if (var1.getRequestMethod().equals("PUT")) {
               String var7 = new String(var1.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
               Task var6 = (Task)TaskSchedulerApp.tasks.stream().filter((var2x) -> {
                  return var2x.id == var3;
               }).findFirst().orElse((Object)null);
               if (var6 != null) {
                  TaskSchedulerApp.updateTask(var6, var7);
                  TaskSchedulerApp.sendJsonResponse(var1, var6.toJson(), 200);
               } else {
                  TaskSchedulerApp.sendJsonResponse(var1, "{\"error\":\"Task not found\"}", 404);
               }
            } else if (var1.getRequestMethod().equals("DELETE")) {
               boolean var8 = TaskSchedulerApp.tasks.removeIf((var2x) -> {
                  return var2x.id == var3;
               });
               if (var8) {
                  TaskSchedulerApp.sendJsonResponse(var1, "{\"message\":\"Task deleted\"}", 200);
               } else {
                  TaskSchedulerApp.sendJsonResponse(var1, "{\"error\":\"Task not found\"}", 404);
               }
            } else {
               TaskSchedulerApp.sendJsonResponse(var1, "{\"error\":\"Method not allowed\"}", 405);
            }

         }
      }
   }

   static class StatsHandler implements HttpHandler {
      StatsHandler() {
      }

      public void handle(HttpExchange var1) throws IOException {
         TaskSchedulerApp.enableCors(var1);
         if (var1.getRequestMethod().equals("OPTIONS")) {
            var1.sendResponseHeaders(200, 0L);
            var1.close();
         } else {
            if (var1.getRequestMethod().equals("GET")) {
               String var2 = LocalDateTime.now().toString().split("T")[0];
               long var3 = (long)TaskSchedulerApp.tasks.size();
               long var5 = TaskSchedulerApp.tasks.stream().filter((var0) -> {
                  return var0.completed;
               }).count();
               long var7 = var3 - var5;
               long var9 = TaskSchedulerApp.tasks.stream().filter((var1x) -> {
                  return var1x.date.equals(var2);
               }).count();
               String var11 = "{\"total\":" + var3 + ",\"completed\":" + var5 + ",\"pending\":" + var7 + ",\"today\":" + var9 + "}";
               TaskSchedulerApp.sendJsonResponse(var1, var11, 200);
            } else {
               TaskSchedulerApp.sendJsonResponse(var1, "{\"error\":\"Method not allowed\"}", 405);
            }

         }
      }
   }

   static class WebHandler implements HttpHandler {
      WebHandler() {
      }

      public void handle(HttpExchange var1) throws IOException {
         if (var1.getRequestURI().getPath().equals("/")) {
            String var2 = HtmlContent.getHtml();
            var1.getResponseHeaders().set("Content-Type", "text/html; charset=utf-8");
            var1.sendResponseHeaders(200, (long)var2.getBytes(StandardCharsets.UTF_8).length);
            var1.getResponseBody().write(var2.getBytes(StandardCharsets.UTF_8));
            var1.close();
         } else {
            TaskSchedulerApp.sendJsonResponse(var1, "{\"error\":\"Not found\"}", 404);
         }

      }
   }

   static class Task {
      public long id = System.currentTimeMillis();
      public String title;
      public String description;
      public String date;
      public String time;
      public String priority;
      public String category;
      public boolean completed;
      public String createdAt;

      public Task(String var1, String var2, String var3, String var4, String var5, String var6) {
         this.title = var1;
         this.description = var2;
         this.date = var3;
         this.time = var4;
         this.priority = var5;
         this.category = var6;
         this.completed = false;
         this.createdAt = LocalDateTime.now().toString();
      }

      public String toJson() {
         long var10000 = this.id;
         return "{\"id\":" + var10000 + ",\"title\":\"" + escapeJson(this.title) + "\",\"description\":\"" + escapeJson(this.description) + "\",\"date\":\"" + this.date + "\",\"time\":\"" + this.time + "\",\"priority\":\"" + this.priority + "\",\"category\":\"" + this.category + "\",\"completed\":" + this.completed + ",\"createdAt\":\"" + this.createdAt + "\"}";
      }

      private static String escapeJson(String var0) {
         return var0 == null ? "" : var0.replace("\\", "\\\\").replace("\"", "\\\"").replace("\n", "\\n").replace("\r", "\\r");
      }
   }
}
