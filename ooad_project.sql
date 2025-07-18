CREATE DATABASE food_ordering1;
USE food_ordering1;

CREATE TABLE users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    phone VARCHAR(15),
    user_type ENUM('customer', 'delivery') NOT NULL
);

CREATE TABLE restaurants (
    restaurant_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    location VARCHAR(100)
);

CREATE TABLE menu_items (
    item_id INT AUTO_INCREMENT PRIMARY KEY,
    restaurant_id INT NOT NULL,
    name VARCHAR(100) NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (restaurant_id) REFERENCES restaurants(restaurant_id)
        ON DELETE CASCADE
);
CREATE TABLE orders (
    order_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    restaurant_id INT NOT NULL,
    order_type ENUM('regular', 'rush', 'express') NOT NULL,
    placed_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(user_id)
        ON DELETE CASCADE,
    FOREIGN KEY (restaurant_id) REFERENCES restaurants(restaurant_id)
        ON DELETE CASCADE
);
CREATE TABLE order_items (
    order_id INT,
    item_id INT,
    quantity INT NOT NULL,
    PRIMARY KEY (order_id, item_id),
    FOREIGN KEY (order_id) REFERENCES orders(order_id)
        ON DELETE CASCADE,
    FOREIGN KEY (item_id) REFERENCES menu_items(item_id)
        ON DELETE CASCADE
);

CREATE TABLE order_status (
    status_id INT AUTO_INCREMENT PRIMARY KEY,
    order_id INT NOT NULL,
    status ENUM('Placed', 'Preparing', 'Out for Delivery', 'Delivered') NOT NULL,
    timestamp DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (order_id) REFERENCES orders(order_id)
        ON DELETE CASCADE
);
CREATE TABLE delivery_commands (
    command_id INT AUTO_INCREMENT PRIMARY KEY,
    order_id INT NOT NULL,
    action VARCHAR(100) NOT NULL,
    executed BOOLEAN DEFAULT FALSE,
    execution_time DATETIME,
    FOREIGN KEY (order_id) REFERENCES orders(order_id)
        ON DELETE CASCADE
);
CREATE TABLE reviews (
    review_id INT AUTO_INCREMENT PRIMARY KEY,
    order_id INT NOT NULL,
    user_id INT NOT NULL,
    timestamp DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (order_id) REFERENCES orders(order_id)
        ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES users(user_id)
        ON DELETE CASCADE
);
CREATE TABLE food_reviews (
    review_id INT PRIMARY KEY,
    rating INT CHECK (rating BETWEEN 1 AND 5),
    comment TEXT,
    FOREIGN KEY (review_id) REFERENCES reviews(review_id)
        ON DELETE CASCADE
);
CREATE TABLE service_reviews (
    review_id INT PRIMARY KEY,
    rating INT CHECK (rating BETWEEN 1 AND 5),
    comment TEXT,
    FOREIGN KEY (review_id) REFERENCES reviews(review_id)
        ON DELETE CASCADE
);
CREATE TABLE delivery_reviews (
    review_id INT PRIMARY KEY,
    rating INT CHECK (rating BETWEEN 1 AND 5),
    comment TEXT,
    FOREIGN KEY (review_id) REFERENCES reviews(review_id)
        ON DELETE CASCADE
);

INSERT INTO users (user_id, name, email, phone, user_type)
VALUES (123, 'John Doe', 'john@example.com', '1234567890', 'customer');

INSERT INTO restaurants (name, location)
VALUES ('Spice Kitchen', 'MG Road, Bangalore');

DROP TABLE order_status;
CREATE TABLE order_status (
    status_id INT AUTO_INCREMENT PRIMARY KEY,
    order_id INT NOT NULL,
    status ENUM('Placed', 'Preparing', 'Out for Delivery', 'Delivered') DEFAULT 'Placed',
    timestamp DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (order_id) REFERENCES orders(order_id)
        ON DELETE CASCADE
);
DROP TABLE order_status;
CREATE TABLE order_status (
    status_id INT AUTO_INCREMENT PRIMARY KEY,
    order_id INT NOT NULL,
    status ENUM('Placed', 'Preparing', 'Out for Delivery', 'Delivered') DEFAULT 'Placed',
    FOREIGN KEY (order_id) REFERENCES orders(order_id)
        ON DELETE CASCADE
);

CREATE TABLE delivery_status (
    order_id INT PRIMARY KEY,
    status ENUM('delivered on time', 'delivered early', 'delivered late'),
    house_number INT
);
INSERT INTO users (user_id, name, email, phone, user_type)
VALUES (456, 'Anika Yadav', 'anika@example.com', '987654321', 'customer');
INSERT INTO restaurants (name, location)
VALUES ('Burger King', 'Newtwon Road, Bangalore');
INSERT INTO users (user_id, name, email, phone, user_type)
VALUES (789, 'Anika Pesu', 'anikapesu@example.com', '987654321', 'customer');
