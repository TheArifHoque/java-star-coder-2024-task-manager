
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
![](/home/arif/Pictures/register-successful.png)
If different user tries to register with same email or username it'll not let the user register
![](/home/arif/Pictures/register-unsuccessful.png)