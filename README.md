# Java-kanban

Project realizing the backend for Task Manager

# Description
Stack: JDK 11, gson, jupiter, HTTP

Project has 3 main entity:
1. Task - our main entity 
2. Subtask - small task included into Epic 
3. Epic - a big task containing one or several subtasks

Any task has name, description, status, start time and duration. Tasks can't cross to each other in same time.

In project realized 3 Task Managers: 
1. InMemoryTaskManger - Storage tasks in ORM of PC
2. FileBackedTaskManager - Storage tasks in the file on PC
3. HttpTaskManger - Storage tasks in the file on server

Also realized the storage the history of handling tasks.

# Deploying

For checking project need at least JDK 11 with external libraries gson and jupiter (for tests).