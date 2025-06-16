pipeline {
    agent any
 
    environment {
        DB_NAME = "demo_db"
        DB_USER = "nodeuser"
        DB_PASSWORD = "your_password"
        DEBIAN_FRONTEND = "noninteractive" // Prevents MySQL prompts
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
                rm -rf database_sql
                git clone https://github.com/rutvik-3377/Contact-Form-App-SQL.git
                '''
            }
        }
 
        stage('Install Packages') {
            steps {
                sh '''
                sudo apt-get update
                sudo apt-get install -y nodejs npm mysql-server
                cd database_sql
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
                cd database_sql
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
                cd database_sql
                pm2 start server.js --name contact-form
                pm2 save
                sudo pm2 startup systemd -u $USER --hp $HOME
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
