📌 Retail Store Management System

📖 Overview
The Retail Store Management System is a full-stack application designed to manage customers, products, and orders efficiently. It simulates a real-world retail environment where billing, inventory, and customer management are handled in an integrated system.
This project demonstrates backend development using Spring Boot along with a simple frontend dashboard for visualization and interaction.

---

✨ Features
👤 Customer Management
Add and view customers

📦 Product Management
Add products with price and quantity
Track inventory in real-time

🧾 Order Management
Place orders with multiple products
Automatic total bill calculation
Dynamic stock reduction after order placement

📊 Dashboard
View total customers, products, and orders
Display product and order details in tables

---

🛠️ Tech Stack
Backend: Java, Spring Boot
Frontend: HTML, CSS, JavaScrip
Database: MySQL
Tools: Postman, Git, GitHub

---

🔗 API Endpoints

Customer APIs
POST /customers/add → Add customer
GET /customers/all → Get all customers

Product APIs
POST /products/add → Add product
GET /products/all → Get all products

Order APIs
POST /orders/add → Place order
GET /orders/all → Get all orders

---

⚙️ How It Works
1. Add customers and products using API or dashboard

2. Place an order by selecting customer and product

3. System will:
Validate product stock
Deduct quantity
Calculate total amount automatically

4. Dashboard updates data in real-time
---

🚀 How to Run the Project

1. Clone the repository
2. Open in Eclipse / IntelliJ
3. Run the Spring Boot application
4. Open browser and go to:

http://localhost:8080/dashboard.html

---

📊 Sample Order Flow

{
  "customer": { "customerId": 1 },
  "orderItems": [
    {
      "product": { "productId": 1 },
      "quantity": 2
    }
  ]
}

---

🎯 Key Highlights

Clean REST API design
Proper entity relationships using JPA
Business logic with stock validation
Real-time dashboard integration
Beginner-friendly full-stack project

---

👩‍💻 About Me

Hi, I’m Hiral Patel, a second-year engineering student specializing in Artificial Intelligence & Data Science.
I enjoy building practical projects that combine backend logic with real-world applications.
