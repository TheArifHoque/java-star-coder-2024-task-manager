
# Task Manager App
***
This is a task manager app where you can manage your task. You can create new task, update your task and also delete your task.

To use this application you have to register yourself and then login for further procedure. 

### Registration Process 
***
The parameters to register user are FirstName, LastName, Email, Username, Password.
![](/images/register.png)

This will store in configured database. Password given here will be encoded by BCryptEncoder.
![](/images/databse.png)

After successful registration a success message will be shown.
![](/images/register-successful.png)

If different user tries to register with same email or username it'll not let the user register
![](/images/register-unsuccessful.png)

### Login Process
***
To login user needs their username and password. On successful login it'll create a token which will let the user access tasks.
![](/images/login-successful.png)

If any unauthorized user tries to log-in it'll give a warning that they have incorrect credentials
![](/images/login-unsuccessful.png)

### Add Task
***
After login with the help of generated token user can add task. After adding task it'll show a message saying "New task added"
![](/images/add-task)

### Get All Task
***
Authorized user can see their task as we give their token to authenticate. 
![](/images/get-task.png)

### Update Task
***
We can update task. We have to send taskId as parameter to edit the task. 
![](/images/update-task.png)

If we try to see all task again we can see the task being updated
![](/images/all-updated-task.png)

### Delete Task
***
To delete task we have to hit the api with taskId parameter. Upon successful deletion it'll send a message saying "task deleted".
![](/images/delete-task.png)

If we try to delete a task which is not available it'll show error.
![](/images/delete-unsuccessful.png)