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

### 2. Install Required Packages
```bash
sudo apt update
sudo apt install nodejs npm mysql-server -y
npm init -y
npm install express mysql2
```

### 3. MySQL Database Setup
#### Log in to MySQL:
```sql
sudo mysql
```

#### Then execute:
```sql
CREATE DATABASE demo_db;
USE demo_db;

CREATE TABLE contacts (
  id INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(100),
  email VARCHAR(100)
);
```
### 4. Create a New MySQL User (Recommended for Security)
#### Log in to MySQL as root:
```sql
sudo mysql -u root -p
```
#### Then run:
```sql
-- Create user
CREATE USER 'nodeuser'@'localhost' IDENTIFIED BY 'your_password';

-- Grant privileges on your database
GRANT ALL PRIVILEGES ON demo_db.* TO 'nodeuser'@'localhost';

-- Apply changes
FLUSH PRIVILEGES;

EXIT;
```
## Update your server.js file:
```js
const db = mysql.createConnection({
  host: 'localhost',
  user: 'nodeuser',
  password: 'your_password',
  database: 'demo_db'
});
```

### 5. Start the App
```js
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

### 7. View Stored Form Data

#### Use the Database
```sql
USE demo_db;
```

#### List Tables
```sql
SHOW TABLES;
```

#### You should see:
```sql
+---------------------+
| Tables_in_demo_db   |
+---------------------+
| contacts            |
+---------------------+
```
#### View All Stored Data
```sql
SELECT * FROM contacts;
```

#### Sample output:
```sql
+----+------------+---------------------+
| id | name       | email               |
+----+------------+---------------------+
|  1 | Alice Doe  | alice@example.com   |
|  2 | Bob Smith  | bob@example.com     |
+----+------------+---------------------+
```
________________________________________________________________________________________________________

**Steps For Pipeline :**

copy pipeline from @by_jenkins_pipeline file 
```
cd /var/lib/jenkins/workspace/New/database_sql/node server.js
```
mysql connected 
![image](https://github.com/user-attachments/assets/e8e5ff52-cc9d-49be-af04-d75db2ccac73)


after add commands :

``` base
     pm2 list
     pm2 start server.js --name contact-form
     
     pm2 save
     pm2 startup system
 ```
