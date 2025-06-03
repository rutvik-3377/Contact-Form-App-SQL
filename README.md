# 📬 Contact Form Web App

A simple contact form web application with:

- ✅ Frontend: HTML, CSS, JS  
- ✅ Backend: Node.js (Express) + PHP (optional)  
- ✅ Database: MySQL

Submitted form data is stored securely in a MySQL database.

---

## 🌐 Live Preview

Access the app at:  
**http://<YOUR_INSTANCE_PUBLIC_IP>:4000**

---

## 📁 Project Structure
```bash
contact-form/
├── public/
│ └── index.html # Frontend form
├── server.js # Node.js backend
├── db.js # MySQL connection config (if modularized)
├── package.json # Node dependencies
```

---

## 🛠️ Installation

### 1. Clone the Repo

```bash
git clone https://github.com/DhruvShah0612/database_sql.git
cd database_sql
```

### 3. Install Required Packages
```
sudo apt update
sudo apt install nodejs npm mysql-server -y
npm init -y
npm install express mysql2
```

### 4. MySQL Database Setup
``` bash
Log in to MySQL:

sudo mysql

Then execute:

CREATE DATABASE demo_db;
USE demo_db;

CREATE TABLE contacts (
  id INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(100),
  email VARCHAR(100)
);
```

### 5. Start the App
```bash
node server.js
```

### 6. Run the App with PM2 (Production)
```bash
sudo npm install -g pm2
pm2 start server.js --name contact-form
pm2 save
pm2 startup
```

## 📸 Form Preview

![Form Preview](form.png)
