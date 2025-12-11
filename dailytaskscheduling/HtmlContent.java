public class HtmlContent {
    public static String getHtml() {
        return "<!DOCTYPE html>\n" +
"<html lang=\"en\">\n" +
"<head>\n" +
"    <meta charset=\"UTF-8\">\n" +
"    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
"    <title>Daily Task Scheduler - Java Web App</title>\n" +
"    <style>\n" +
"        * { margin: 0; padding: 0; box-sizing: border-box; }\n" +
"        body { font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); min-height: 100vh; padding: 20px; }\n" +
"        .container { max-width: 1200px; margin: 0 auto; display: grid; grid-template-columns: 1fr 2fr; gap: 30px; }\n" +
"        @media (max-width: 768px) { .container { grid-template-columns: 1fr; } }\n" +
"        .header { grid-column: 1 / -1; text-align: center; color: white; margin-bottom: 20px; }\n" +
"        .header h1 { font-size: 2.5rem; margin-bottom: 10px; text-shadow: 2px 2px 4px rgba(0, 0, 0, 0.3); }\n" +
"        .header p { font-size: 1.1rem; opacity: 0.9; }\n" +
"        .input-section { background: white; padding: 30px; border-radius: 10px; box-shadow: 0 8px 30px rgba(0, 0, 0, 0.2); height: fit-content; }\n" +
"        .input-section h2 { color: #667eea; margin-bottom: 25px; font-size: 1.5rem; }\n" +
"        .form-group { margin-bottom: 20px; }\n" +
"        .form-group label { display: block; margin-bottom: 8px; color: #333; font-weight: 600; }\n" +
"        .form-group input, .form-group textarea, .form-group select { width: 100%; padding: 12px; border: 2px solid #e0e0e0; border-radius: 6px; font-size: 1rem; transition: border-color 0.3s; font-family: inherit; }\n" +
"        .form-group input:focus, .form-group textarea:focus, .form-group select:focus { outline: none; border-color: #667eea; }\n" +
"        .form-group textarea { resize: vertical; min-height: 80px; }\n" +
"        .button-group { display: grid; grid-template-columns: 1fr 1fr; gap: 10px; }\n" +
"        .btn { padding: 12px 20px; border: none; border-radius: 6px; font-size: 1rem; font-weight: 600; cursor: pointer; transition: all 0.3s; }\n" +
"        .btn-primary { background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); color: white; }\n" +
"        .btn-primary:hover { transform: translateY(-2px); box-shadow: 0 5px 15px rgba(102, 126, 234, 0.4); }\n" +
"        .btn-secondary { background: #f0f0f0; color: #333; }\n" +
"        .btn-secondary:hover { background: #e0e0e0; }\n" +
"        .tasks-section { background: white; padding: 30px; border-radius: 10px; box-shadow: 0 8px 30px rgba(0, 0, 0, 0.2); }\n" +
"        .tasks-header { display: flex; justify-content: space-between; align-items: center; margin-bottom: 25px; border-bottom: 2px solid #f0f0f0; padding-bottom: 15px; }\n" +
"        .tasks-header h2 { color: #667eea; font-size: 1.5rem; }\n" +
"        .filter-buttons { display: flex; gap: 10px; flex-wrap: wrap; }\n" +
"        .filter-btn { padding: 8px 15px; border: 2px solid #e0e0e0; background: white; border-radius: 6px; cursor: pointer; font-weight: 600; transition: all 0.3s; color: #666; }\n" +
"        .filter-btn.active { background: #667eea; color: white; border-color: #667eea; }\n" +
"        .tasks-list { display: grid; gap: 15px; }\n" +
"        .task-card { background: #f9f9f9; padding: 20px; border-radius: 8px; border-left: 5px solid #667eea; transition: all 0.3s; display: flex; justify-content: space-between; align-items: flex-start; gap: 15px; }\n" +
"        .task-card:hover { box-shadow: 0 5px 15px rgba(0, 0, 0, 0.1); transform: translateX(5px); }\n" +
"        .task-card.completed { background: #f0f8f0; border-left-color: #4caf50; }\n" +
"        .task-card.high-priority { border-left-color: #ff6b6b; }\n" +
"        .task-card.medium-priority { border-left-color: #ffa500; }\n" +
"        .task-card.low-priority { border-left-color: #4caf50; }\n" +
"        .task-content { flex: 1; }\n" +
"        .task-title { font-size: 1.1rem; font-weight: 600; color: #333; margin-bottom: 8px; }\n" +
"        .task-card.completed .task-title { text-decoration: line-through; color: #999; }\n" +
"        .task-description { color: #666; margin-bottom: 10px; line-height: 1.5; }\n" +
"        .task-meta { display: flex; gap: 15px; flex-wrap: wrap; font-size: 0.9rem; }\n" +
"        .meta-item { display: flex; align-items: center; gap: 5px; color: #666; }\n" +
"        .priority-badge { padding: 4px 10px; border-radius: 20px; font-size: 0.85rem; font-weight: 600; }\n" +
"        .priority-high { background: #ffebee; color: #c62828; }\n" +
"        .priority-medium { background: #fff3e0; color: #e65100; }\n" +
"        .priority-low { background: #e8f5e9; color: #2e7d32; }\n" +
"        .status-badge { padding: 4px 10px; border-radius: 20px; font-size: 0.85rem; font-weight: 600; }\n" +
"        .status-pending { background: #e3f2fd; color: #1565c0; }\n" +
"        .status-completed { background: #e8f5e9; color: #2e7d32; }\n" +
"        .task-actions { display: flex; gap: 10px; flex-direction: column; min-width: 100px; }\n" +
"        .action-btn { padding: 8px 12px; border: none; border-radius: 6px; cursor: pointer; font-size: 0.9rem; font-weight: 600; transition: all 0.3s; }\n" +
"        .btn-complete { background: #4caf50; color: white; }\n" +
"        .btn-complete:hover { background: #45a049; }\n" +
"        .btn-delete { background: #ff6b6b; color: white; }\n" +
"        .btn-delete:hover { background: #ff5252; }\n" +
"        .btn-edit { background: #2196f3; color: white; }\n" +
"        .btn-edit:hover { background: #1976d2; }\n" +
"        .empty-state { text-align: center; padding: 50px 20px; color: #999; }\n" +
"        .stats-section { grid-column: 1 / -1; display: grid; grid-template-columns: repeat(auto-fit, minmax(200px, 1fr)); gap: 20px; margin-top: 30px; }\n" +
"        .stat-card { background: white; padding: 20px; border-radius: 10px; box-shadow: 0 8px 30px rgba(0, 0, 0, 0.2); text-align: center; }\n" +
"        .stat-number { font-size: 2.5rem; font-weight: bold; color: #667eea; margin-bottom: 10px; }\n" +
"        .stat-label { color: #666; font-weight: 600; }\n" +
"        .modal { display: none; position: fixed; top: 0; left: 0; width: 100%; height: 100%; background: rgba(0, 0, 0, 0.5); z-index: 1000; justify-content: center; align-items: center; }\n" +
"        .modal.active { display: flex; }\n" +
"        .modal-content { background: white; padding: 30px; border-radius: 10px; max-width: 500px; width: 90%; max-height: 90vh; overflow-y: auto; box-shadow: 0 10px 40px rgba(0, 0, 0, 0.3); }\n" +
"        .modal-header { display: flex; justify-content: space-between; align-items: center; margin-bottom: 20px; border-bottom: 2px solid #f0f0f0; padding-bottom: 15px; }\n" +
"        .modal-header h2 { color: #667eea; }\n" +
"        .close-btn { background: none; border: none; font-size: 1.5rem; cursor: pointer; color: #999; }\n" +
"        .notification { position: fixed; top: 20px; right: 20px; background: white; padding: 15px 20px; border-radius: 6px; box-shadow: 0 5px 15px rgba(0, 0, 0, 0.2); z-index: 2000; animation: slideIn 0.3s ease; }\n" +
"        .notification.success { border-left: 5px solid #4caf50; }\n" +
"        .notification.error { border-left: 5px solid #ff6b6b; }\n" +
"        @keyframes slideIn { from { transform: translateX(400px); opacity: 0; } to { transform: translateX(0); opacity: 1; } }\n" +
"        .loading { text-align: center; padding: 20px; color: #999; }\n" +
"        .server-info { grid-column: 1 / -1; background: rgba(255, 255, 255, 0.1); color: white; padding: 15px; border-radius: 8px; text-align: center; margin-bottom: 20px; font-size: 0.9rem; }\n" +
"    </style>\n" +
"</head>\n" +
"<body>\n" +
"    <div class=\"server-info\">[JAVA BACKEND] Server Running | [API] RESTful API | [SYNC] Real-time Data</div>\n" +
"    <div class=\"header\">\n" +
"        <h1>Daily Task Scheduler (Java)</h1>\n" +
"        <p>Java Web Application - Organize, Track, and Complete Your Daily Tasks</p>\n" +
"    </div>\n" +
"    <div class=\"container\">\n" +
"        <div class=\"input-section\">\n" +
"            <h2>Add New Task</h2>\n" +
"            <form id=\"taskForm\">\n" +
"                <div class=\"form-group\"><label for=\"taskTitle\">Task Title *</label><input type=\"text\" id=\"taskTitle\" placeholder=\"Enter task title\" required></div>\n" +
"                <div class=\"form-group\"><label for=\"taskDescription\">Description</label><textarea id=\"taskDescription\" placeholder=\"Enter task description (optional)\"></textarea></div>\n" +
"                <div class=\"form-group\"><label for=\"taskDate\">Due Date *</label><input type=\"date\" id=\"taskDate\" required></div>\n" +
"                <div class=\"form-group\"><label for=\"taskTime\">Due Time</label><input type=\"time\" id=\"taskTime\"></div>\n" +
"                <div class=\"form-group\"><label for=\"taskPriority\">Priority</label><select id=\"taskPriority\"><option value=\"low\">Low Priority</option><option value=\"medium\" selected>Medium Priority</option><option value=\"high\">High Priority</option></select></div>\n" +
"                <div class=\"form-group\"><label for=\"taskCategory\">Category</label><select id=\"taskCategory\"><option value=\"work\">Work</option><option value=\"personal\">Personal</option><option value=\"health\">Health</option><option value=\"education\">Education</option><option value=\"other\">Other</option></select></div>\n" +
"                <div class=\"button-group\"><button type=\"submit\" class=\"btn btn-primary\">Add Task</button><button type=\"reset\" class=\"btn btn-secondary\">Clear</button></div>\n" +
"            </form>\n" +
"        </div>\n" +
"        <div class=\"tasks-section\">\n" +
"            <div class=\"tasks-header\">\n" +
"                <h2>My Tasks</h2>\n" +
"                <div class=\"filter-buttons\">\n" +
"                    <button class=\"filter-btn active\" data-filter=\"all\">All</button>\n" +
"                    <button class=\"filter-btn\" data-filter=\"pending\">Pending</button>\n" +
"                    <button class=\"filter-btn\" data-filter=\"completed\">Completed</button>\n" +
"                    <button class=\"filter-btn\" data-filter=\"high\">High Priority</button>\n" +
"                </div>\n" +
"            </div>\n" +
"            <div class=\"tasks-list\" id=\"tasksList\"><div class=\"loading\">Loading tasks...</div></div>\n" +
"        </div>\n" +
"    </div>\n" +
"    <div class=\"container\">\n" +
"        <div class=\"stats-section\" id=\"statsSection\">\n" +
"            <div class=\"stat-card\"><div class=\"stat-number\" id=\"totalTasks\">0</div><div class=\"stat-label\">Total Tasks</div></div>\n" +
"            <div class=\"stat-card\"><div class=\"stat-number\" id=\"completedTasks\">0</div><div class=\"stat-label\">Completed</div></div>\n" +
"            <div class=\"stat-card\"><div class=\"stat-number\" id=\"pendingTasks\">0</div><div class=\"stat-label\">Pending</div></div>\n" +
"            <div class=\"stat-card\"><div class=\"stat-number\" id=\"todayTasks\">0</div><div class=\"stat-label\">Today's Tasks</div></div>\n" +
"        </div>\n" +
"    </div>\n" +
"    <div class=\"modal\" id=\"editModal\">\n" +
"        <div class=\"modal-content\">\n" +
"            <div class=\"modal-header\"><h2>Edit Task</h2><button class=\"close-btn\" onclick=\"closeEditModal()\">&times;</button></div>\n" +
"            <form id=\"editForm\">\n" +
"                <div class=\"form-group\"><label for=\"editTaskTitle\">Task Title *</label><input type=\"text\" id=\"editTaskTitle\" required></div>\n" +
"                <div class=\"form-group\"><label for=\"editTaskDescription\">Description</label><textarea id=\"editTaskDescription\"></textarea></div>\n" +
"                <div class=\"form-group\"><label for=\"editTaskDate\">Due Date *</label><input type=\"date\" id=\"editTaskDate\" required></div>\n" +
"                <div class=\"form-group\"><label for=\"editTaskTime\">Due Time</label><input type=\"time\" id=\"editTaskTime\"></div>\n" +
"                <div class=\"form-group\"><label for=\"editTaskPriority\">Priority</label><select id=\"editTaskPriority\"><option value=\"low\">Low Priority</option><option value=\"medium\">Medium Priority</option><option value=\"high\">High Priority</option></select></div>\n" +
"                <div class=\"form-group\"><label for=\"editTaskCategory\">Category</label><select id=\"editTaskCategory\"><option value=\"work\">Work</option><option value=\"personal\">Personal</option><option value=\"health\">Health</option><option value=\"education\">Education</option><option value=\"other\">Other</option></select></div>\n" +
"                <div class=\"button-group\"><button type=\"submit\" class=\"btn btn-primary\">Save Changes</button><button type=\"button\" class=\"btn btn-secondary\" onclick=\"closeEditModal()\">Cancel</button></div>\n" +
"            </form>\n" +
"        </div>\n" +
"    </div>\n" +
"    <script>\n" +
"const API_BASE='http://localhost:8080/api';let currentFilter='all';let editingId=null;document.addEventListener('DOMContentLoaded',function(){setDefaultDate();loadTasks();loadStats();setupEventListeners();setInterval(loadStats,5000);});function setDefaultDate(){const today=new Date().toISOString().split('T')[0];document.getElementById('taskDate').value=today;}function setupEventListeners(){document.getElementById('taskForm').addEventListener('submit',handleAddTask);document.getElementById('editForm').addEventListener('submit',handleEditTask);document.querySelectorAll('.filter-btn').forEach(btn=>{btn.addEventListener('click',function(){document.querySelectorAll('.filter-btn').forEach(b=>b.classList.remove('active'));this.classList.add('active');currentFilter=this.dataset.filter;loadTasks();});});document.getElementById('editModal').addEventListener('click',function(e){if(e.target===this)closeEditModal();});}async function loadTasks(){try{const response=await fetch(`${API_BASE}/tasks?filter=${currentFilter}`);const tasks=await response.json();renderTasks(tasks);}catch(error){console.error('Error loading tasks:',error);showNotification('Error loading tasks','error');}}async function loadStats(){try{const response=await fetch(`${API_BASE}/stats`);const stats=await response.json();updateStatistics(stats);}catch(error){console.error('Error loading stats:',error);}}async function handleAddTask(e){e.preventDefault();const task={title:document.getElementById('taskTitle').value,description:document.getElementById('taskDescription').value,date:document.getElementById('taskDate').value,time:document.getElementById('taskTime').value,priority:document.getElementById('taskPriority').value,category:document.getElementById('taskCategory').value};try{const response=await fetch(`${API_BASE}/tasks`,{method:'POST',headers:{'Content-Type':'application/json'},body:JSON.stringify(task)});if(response.ok){showNotification('Task added successfully!','success');document.getElementById('taskForm').reset();setDefaultDate();loadTasks();loadStats();}else{showNotification('Error adding task','error');}}catch(error){console.error('Error:',error);showNotification('Error adding task','error');}}async function handleEditTask(e){e.preventDefault();const updatedTask={title:document.getElementById('editTaskTitle').value,description:document.getElementById('editTaskDescription').value,date:document.getElementById('editTaskDate').value,time:document.getElementById('editTaskTime').value,priority:document.getElementById('editTaskPriority').value,category:document.getElementById('editTaskCategory').value};try{const response=await fetch(`${API_BASE}/tasks/${editingId}`,{method:'PUT',headers:{'Content-Type':'application/json'},body:JSON.stringify(updatedTask)});if(response.ok){showNotification('Task updated successfully!','success');closeEditModal();loadTasks();loadStats();}else{showNotification('Error updating task','error');}}catch(error){console.error('Error:',error);showNotification('Error updating task','error');}}function renderTasks(tasks){const tasksList=document.getElementById('tasksList');if(tasks.length===0){tasksList.innerHTML='<div class=\"empty-state\"><p>No tasks found. Create one to get started!</p></div>';return;}tasks.sort((a,b)=>{const dateCompare=new Date(a.date)-new Date(b.date);if(dateCompare!==0)return dateCompare;const priorityOrder={high:1,medium:2,low:3};return priorityOrder[a.priority]-priorityOrder[b.priority];});tasksList.innerHTML=tasks.map(task=>createTaskCard(task)).join('');tasksList.querySelectorAll('.btn-complete').forEach(btn=>{btn.addEventListener('click',async function(){const taskId=this.dataset.id;const task=tasks.find(t=>t.id==taskId);try{await fetch(`${API_BASE}/tasks/${taskId}`,{method:'PUT',headers:{'Content-Type':'application/json'},body:JSON.stringify({...task,completed:!task.completed})});loadTasks();loadStats();showNotification('Task status updated!','success');}catch(error){showNotification('Error updating task','error');}});});tasksList.querySelectorAll('.btn-delete').forEach(btn=>{btn.addEventListener('click',async function(){if(confirm('Are you sure you want to delete this task?')){const taskId=this.dataset.id;try{await fetch(`${API_BASE}/tasks/${taskId}`,{method:'DELETE'});loadTasks();loadStats();showNotification('Task deleted!','success');}catch(error){showNotification('Error deleting task','error');}}});});tasksList.querySelectorAll('.btn-edit').forEach(btn=>{btn.addEventListener('click',function(){const taskId=this.dataset.id;const task=tasks.find(t=>t.id==taskId);openEditModal(task);});});}function createTaskCard(task){const formattedDate=new Date(task.date).toLocaleDateString('en-US',{weekday:'short',month:'short',day:'numeric'});return `<div class=\"task-card ${task.completed?'completed':''} ${task.priority}-priority\"><div class=\"task-content\"><div class=\"task-title\">${escapeHtml(task.title)}</div>${task.description?`<div class=\"task-description\">${escapeHtml(task.description)}</div>`:''}  <div class=\"task-meta\"><div class=\"meta-item\">[DATE] <span>${formattedDate}</span></div>${task.time?`<div class=\"meta-item\">[TIME] <span>${task.time}</span></div>`:''}<span class=\"priority-badge priority-${task.priority}\">${task.priority.toUpperCase()}</span><span class=\"status-badge status-${task.completed?'completed':'pending'}\">${task.completed?'COMPLETED':'PENDING'}</span><div class=\"meta-item\">[CATEGORY] <span>${task.category}</span></div></div></div><div class=\"task-actions\"><button class=\"action-btn btn-complete\" data-id=\"${task.id}\">${task.completed?'[UNDO]':'[DONE]'}</button><button class=\"action-btn btn-edit\" data-id=\"${task.id}\">[EDIT]</button><button class=\"action-btn btn-delete\" data-id=\"${task.id}\">[DELETE]</button></div></div>`;}function updateStatistics(stats){document.getElementById('totalTasks').textContent=stats.total;document.getElementById('completedTasks').textContent=stats.completed;document.getElementById('pendingTasks').textContent=stats.pending;document.getElementById('todayTasks').textContent=stats.today;}function openEditModal(task){editingId=task.id;document.getElementById('editTaskTitle').value=task.title;document.getElementById('editTaskDescription').value=task.description||'';document.getElementById('editTaskDate').value=task.date;document.getElementById('editTaskTime').value=task.time||'';document.getElementById('editTaskPriority').value=task.priority;document.getElementById('editTaskCategory').value=task.category;document.getElementById('editModal').classList.add('active');}function closeEditModal(){document.getElementById('editModal').classList.remove('active');editingId=null;}function showNotification(message,type){const notification=document.createElement('div');notification.className=`notification ${type}`;notification.textContent=message;document.body.appendChild(notification);setTimeout(()=>notification.remove(),3000);}function escapeHtml(text){const div=document.createElement('div');div.textContent=text;return div.innerHTML;}\n" +
"    </script>\n" +
"</body>\n" +
"</html>";
    }
}
