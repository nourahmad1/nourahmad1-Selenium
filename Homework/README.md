# 🧪 TestNG Automation Framework – Homework 1 & 2

This repository contains two TestNG-based automation projects demonstrating:
- Web testing using Selenium.
- Data-driven testing using Apache POI and Excel files.
- Test execution and reporting with TestNG.

---



## 🧰 Technologies Used

| Tool | Purpose |
|------|----------|
| **Java 8+** | Core programming language |
| **Selenium WebDriver** | Web browser automation |
| **TestNG** | Test framework for structure & reporting |
| **Apache POI** | Reading/writing Excel files |
| **Maven** | Dependency management and build tool |
| **Eclipse / IntelliJ IDEA** | IDE for Java automation |

---

## 🧩 Homework 1 – Ballard Designs Login Automation

### 🔗 URL
[https://www.ballarddesigns.com/](https://www.ballarddesigns.com/)

### 🎯 Objective
Automate login functionality verification for the Ballard Designs website.

### 🧾 Test Steps
1. Navigate to the homepage.
2. Verify the **Ballard Designs logo** is displayed.
3. Click on the **“Sign In/Register”** link.
4. Verify the navigation to the sign-in page by checking for the text:
   > “Welcome back! To access your account, please enter your email address and password and click ‘Sign In.’”
5. Enter a **valid email** and **valid password** and click **Sign In**.
6. Verify that the **“Sign In/Register”** link changes to **“My Account.”**
7. Validate the **welcome message**:  
   > “Welcome, [username]”  
   and ensure it matches the logged-in username.

---

## 📊 Homework 2 – Frontgate Login Testing (Data-Driven with Excel)

### 🔗 URL
[https://www.frontgate.com/ShoppingCartViewp](https://www.frontgate.com/ShoppingCartViewp)

### 🎯 Objective
Automate login test cases using data-driven testing from an Excel file.

### 📘 Excel File: `Book 2.xlsx`

| Email | Password | Expected Result |
|--------|-----------|-----------------|
| invalid email | *(empty)* | Error message |
| valid@email.com | wrongPass | Error message |
| valid@email.com | correctPass | Login success |

### 🧾 Test Steps
1. Navigate to the login page.
2. Read all test data from `Book 2.xlsx` using **Apache POI**.
3. For each row:
   - Enter the provided email and password.
   - Submit the form.
   - Verify the corresponding success or error message.
4. Generate a final **TestNG report** summarizing results for all cases.

---

## 🧠 Framework Concepts Used

- **Assertions** to validate UI messages.
- **TestNG annotations** (`@BeforeTest`, `@Test`, `@AfterTest`) for structure.
- **Maven Surefire Plugin** for automated report generation.

---





