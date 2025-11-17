# Ballard Designs Checkout Automation

Automated testing project for the **Ballard Designs** website using **Java**, **Selenium**, and **TestNG**.  
The project focuses on automating the **checkout process**, including product selection, personalization, delivery options, and PayPal payment.

---

## Features

### Product Management

- **Search & Add Products**
  - Supports single and multiple product codes.
- **Personalization**
  - Image selection, text customization, and monogram input.
- **Cart Verification**
  - Ensures all products are added correctly.

### Checkout Process

- Handles **guest** and **logged-in** users.
- Automatically fills checkout forms for guest users.
- Navigates through shipping, delivery, and payment steps.

### Shipping Method Verification

- **Step Title Verification**
  - Checks if the title is `"Delivery Method"` or `"Delivery Method & Gift"` for gift products.
- **Shipping Help**
  - Verifies that shipping help buttons are displayed correctly.
- **Standard Delivery**
  - Opens dropdowns for all standard delivery options and selects them to ensure functionality.
- **Truck Delivery**
  - Verifies truck delivery options, including gift and radio buttons.
- **Contact Information**
  - Updates phone numbers for in-home delivery requirements.
- **Gift Options**
  - Adds gift messages for each product when applicable.

### Payment

- Supports **PayPal** checkout automation:
  - Select PayPal option.
  - Handle PayPal popup window.
  - Login and complete payment.
  - Return to main checkout page.

---

---

## Test Data

**Product Examples:**

- Single: `WN268`, `AW164`
- Multiple: `FF016KRH`, `FT237`, `#FM139`

**Guest Checkout Example:**

- Email: `nourbzour65@gmail.com`  
- First Name: `firstvisa`  
- Last Name: `lastvisa`  
- Address: `Lexington Avenue`  
- ZIP: `10001`  
- Phone: `2125557890`

**Login User Example:**

- Email: `nourbzour65@gmail.com`  
- Password: `Noor@123456`

---

## How to Run

1. Clone the repository:

```bash
https://github.com/nourahmad1/nourahmad1-Selenium.git


