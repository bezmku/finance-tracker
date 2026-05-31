USE finance_tracker;
CREATE TABLE IF NOT EXISTS transactions (
    id INT AUTO_INCREMENT PRIMARY KEY,
    amount DECIMAL(10, 2) NOT NULL,
    type ENUM('INCOME', 'EXPENSE') NOT NULL,
    category ENUM('FOOD', 'TRANSPORT', 'UTILITIES', 'ENTERTAINMENT', 'SHOPPING', 'RENT','SALARY', 'OTHER') NOT NULL,
    description VARCHAR(255) NOT NULL,
    date DATE  DEFAULT (CURRENT_DATE)
);


-- Insert temporary testing data
INSERT INTO transactions (amount, type, category, description, date) VALUES 
(2500.00, 'INCOME', 'SALARY', 'Monthly Salary', '2023-10-01'),
(50.00, 'EXPENSE', 'FOOD', 'Groceries at Walmart', '2023-10-02'),
(15.50, 'EXPENSE', 'FOOD', 'Starbucks Coffee', '2023-10-03'),
(120.00, 'EXPENSE', 'UTILITIES', 'Electric Bill', '2023-10-05'),
(800.00, 'EXPENSE', 'RENT', 'Monthly Rent', '2023-10-05'),
(200.00, 'INCOME', 'OTHER', 'Sold old bicycle', '2023-10-10'),
(60.00, 'EXPENSE', 'ENTERTAINMENT', 'Movie Tickets', '2023-10-12'),
(45.00, 'EXPENSE', 'TRANSPORT', 'Gas Station', '2023-10-15'),
(150.00, 'EXPENSE', 'SHOPPING', 'New Shoes', '2023-10-20'),
(30.00, 'EXPENSE', 'FOOD', 'Pizza Delivery', '2023-10-22');

