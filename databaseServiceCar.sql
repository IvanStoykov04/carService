CREATE DATABASE CarServiceDB;
USE CarServiceDB;

-- User table
CREATE TABLE user (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    phone VARCHAR(20),  -- Добавено поле за телефон
    address VARCHAR(255),  -- Добавено поле за адрес
    role ENUM('client', 'admin') NOT NULL DEFAULT 'client'
);

-- Car table
CREATE TABLE car (
    car_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    brand VARCHAR(100) NOT NULL,
    model VARCHAR(100) NOT NULL,
    year INT NOT NULL,
    rg_number VARCHAR(50) UNIQUE NOT NULL,
    FOREIGN KEY (user_id) REFERENCES user(user_id) ON DELETE CASCADE
);

-- Request table (modified to keep history)
CREATE TABLE request (
    request_id INT AUTO_INCREMENT PRIMARY KEY,
    car_id INT,  -- Can be NULL to preserve history
    user_id INT NOT NULL,  -- Links request to user
    status ENUM('pending', 'approved', 'completed', 'rejected') NOT NULL,
    createdOn TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    completedOn TIMESTAMP NULL,  -- Track completion date
    modified_request BOOLEAN DEFAULT FALSE,  -- Добавено поле за искане на модификация
    FOREIGN KEY (car_id) REFERENCES car(car_id) ON DELETE SET NULL,  -- Keep request even if car is deleted
    FOREIGN KEY (user_id) REFERENCES user(user_id) ON DELETE CASCADE  -- If user is deleted, requests are also removed
);

-- Services table
CREATE TABLE services (
    services_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    price DECIMAL(10,2) NOT NULL
);

-- Car Service table
CREATE TABLE carService (
    carService_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    location VARCHAR(255) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    phone VARCHAR(20) NOT NULL
);

-- Many-to-Many Relationship: requestServices
CREATE TABLE requestServices (
    request_id INT NOT NULL,
    services_id INT NOT NULL,
    PRIMARY KEY (request_id, services_id),
    FOREIGN KEY (request_id) REFERENCES request(request_id) ON DELETE CASCADE,
    FOREIGN KEY (services_id) REFERENCES services(services_id) ON DELETE CASCADE
);

-- Many-to-Many Relationship: carService_services
CREATE TABLE carService_services (
    carService_id INT NOT NULL,
    services_id INT NOT NULL,
    PRIMARY KEY (carService_id, services_id),
    FOREIGN KEY (carService_id) REFERENCES carService(carService_id) ON DELETE CASCADE,
    FOREIGN KEY (services_id) REFERENCES services(services_id) ON DELETE CASCADE
);



INSERT INTO carService (name, location, email, phone) VALUES 
('Speed Auto Service', 'Sofia, Bulgaria Blvd 12', 'service1@email.com', '+359888123456'),
('Master Car Service', 'Plovdiv, Ivan Vazov St 5', 'service2@email.com', '+359889654321'),
('Auto Express', 'Varna, Levski Blvd 25', 'service3@email.com', '+359887987654'),
('Turbo Service', 'Burgas, Alexandrovka St 45', 'service4@email.com', '+359886345678'),
('Diagnostic Center', 'Ruse, Stroitel St 10', 'service5@email.com', '+359885678910'),
('Premium Car Service', 'Stara Zagora, Tsar Simeon St 22', 'service6@email.com', '+359884123789'),
('Quick Fix Auto', 'Pleven, Vasil Levski Blvd 30', 'service7@email.com', '+359883456987'),
('Elite Auto Repair', 'Dobrich, Hristo Botev St 8', 'service8@email.com', '+359882789654'),
('Ultimate Garage', 'Shumen, Industrial Zone 15', 'service9@email.com', '+359881567432'),
('ProFix Mechanics', 'Blagoevgrad, Pirin St 3', 'service10@email.com', '+359880234567');
