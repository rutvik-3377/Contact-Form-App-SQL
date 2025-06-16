# 🚀 Contact Form App with Node.js and MySQL Integration

![Screenshot](CFAS.png)

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
git clone https://github.com/Meet01234/Contact-Form-App-SQL.git
cd Contact-Form-App-SQL
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

#### Allow Jenkins To Run Sudo Without a Password
```bash
sudo visudo
```
#### Add Editor File
```bash
jenkins ALL=(ALL) NOPASSWD:ALL
```

### 🧪 Jenkins Pipeline: Deploy Node.js + MySQL Contact Form App
#### 📋 Jenkinsfile (Declarative Pipeline)

```groovy
pipeline {
    agent any

    environment {
        DB_NAME = "demo_db"
        DB_USER = "nodeuser"
        DB_PASSWORD = "your_password"
        DEBIAN_FRONTEND = "noninteractive"
    }

    stages {
        stage('Fix Broken dpkg') {
            steps {
                sh '''
                sudo dpkg --configure -a
                '''
            }
        }

        stage('Clone Repo') {
            steps {
                sh '''
                rm -rf Contact-Form-App-SQL
                git clone https://github.com/Meet01234/Contact-Form-App-SQL.git
                '''
            }
        }

        stage('Install Packages') {
            steps {
                sh '''
                sudo apt-get update
                sudo apt-get install -y nodejs npm mysql-server
                cd Contact-Form-App-SQL
                npm install express mysql2
                '''
            }
        }

        stage('Start MySQL & Configure Database') {
            steps {
                sh '''
                sudo service mysql start

                sudo mysql -e "CREATE DATABASE IF NOT EXISTS ${DB_NAME};"
                sudo mysql -e "USE ${DB_NAME}; CREATE TABLE IF NOT EXISTS contacts (id INT AUTO_INCREMENT PRIMARY KEY, name VARCHAR(100), email VARCHAR(100));"

                sudo mysql -e "CREATE USER IF NOT EXISTS '${DB_USER}'@'localhost' IDENTIFIED BY '${DB_PASSWORD}';"
                sudo mysql -e "GRANT ALL PRIVILEGES ON ${DB_NAME}.* TO '${DB_USER}'@'localhost';"
                sudo mysql -e "FLUSH PRIVILEGES;"
                '''
            }
        }

        stage('Update DB Credentials in server.js') {
            steps {
                sh '''
                cd Contact-Form-App-SQL
                sed -i "s/user: .*/user: '${DB_USER}',/" server.js
                sed -i "s/password: .*/password: '${DB_PASSWORD}',/" server.js
                sed -i "s/database: .*/database: '${DB_NAME}'/" server.js
                '''
            }
        }

        stage('Run App with PM2') {
            steps {
                sh '''
                sudo npm install -g pm2
                cd Contact-Form-App-SQL
                pm2 start server.js --name contact-form
                pm2 save
                eval "$(sudo pm2 startup | grep sudo)"
                '''
            }
        }
    }

    post {
        success {
            echo "✅ Contact Form App deployed successfully."
        }
        failure {
            echo "❌ Deployment failed. Check logs."
        }
    }
}
```

### 🛠️ Verify MySQL Connection via Node.js

```bash
cd /var/lib/jenkins/workspace/New/Contact-Form-App-SQL
node server.js
```

### 🔄 Run and Manage the Node.js App with PM2

```bash
# Show all running PM2 processes
pm2 list

# Start the Node.js app and name the process
pm2 start server.js --name contact-form

# Save the currently running processes so they restart on boot
pm2 save

# Enable PM2 to start on system boot using systemd
pm2 startup system
```



