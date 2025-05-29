# Trip_package_automation

This project is a Test Automation Framework developed for the **Raj Travels Hotel Booking Application**, designed to book hotels in India and internationally. The automation is implemented using **Java**, **Selenium WebDriver**, **TestNG**, and **Apache POI** for data-driven testing.

---

## 📋 Project Overview

Raj Travels is a web-based platform for booking hotels. Users can select location types (India or International), country, city, check-in and check-out dates, number of rooms, and the number of adults and children.

This test suite validates:

- Mandatory field validations
- Conditional UI behavior (radio buttons & dependent list boxes)
- Date constraints (`Check-In` ≤ `Check-Out`)
- Dynamic room and occupancy field generation
- Search functionality and accurate hotel result display

---

## ✅ Test Deliverables

### 1. **Test Scenarios**
- Verify UI field behavior for India and International selections
- Validate date formats and logical consistency between Check-In and Check-Out
- Check dynamic rendering of room rows based on room count
- Ensure proper search results based on selected city

### 2. **Test Cases**
- Functional, boundary value, and negative test cases
- Data-driven tests using Apache POI (Excel files)
- Automated using Selenium WebDriver and TestNG

### 3. **Defect Logging**
- UI defects (e.g., missing Country list on International selection)
- Logical defects (e.g., invalid city result display)
- Data validation issues (e.g., Check-In > Check-Out)

---

## 🔧 Tech Stack

| Tool/Technology | Purpose                      |
|-----------------|------------------------------|
| Java            | Programming Language         |
| Selenium WebDriver | UI Test Automation       |
| TestNG          | Test Framework               |
| Apache POI      | Excel-based Data Handling    |
| Excel           | Test Data Source             |

---
