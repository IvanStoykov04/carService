CREATE DATABASE IF NOT EXISTS CarServiceDB;
USE CarServiceDB;

-- User table
CREATE TABLE user (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    phone VARCHAR(20)NOT NULL,
    address VARCHAR(255) NOT NULL,
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


CREATE TABLE request (
    request_id INT AUTO_INCREMENT PRIMARY KEY,
    car_id INT NOT NULL,
    user_id INT NOT NULL,
    status ENUM('pending', 'approved', 'completed', 'rejected') NOT NULL DEFAULT 'pending',
    createdOn TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    completedOn TIMESTAMP NULL,
    modified_request BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (car_id) REFERENCES car(car_id) ON DELETE SET NULL,
    FOREIGN KEY (user_id) REFERENCES user(user_id) ON DELETE CASCADE
);


CREATE TABLE services (
    services_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    price DECIMAL(10,2) NOT NULL
);


CREATE TABLE carService (
    carService_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    location VARCHAR(255) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    phone VARCHAR(20) NOT NULL
);


CREATE TABLE requestServices (
    request_id INT NOT NULL,
    services_id INT NOT NULL,
    PRIMARY KEY (request_id, services_id),
    FOREIGN KEY (request_id) REFERENCES request(request_id) ON DELETE CASCADE,
    FOREIGN KEY (services_id) REFERENCES services(services_id) ON DELETE CASCADE
);


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




INSERT INTO user (name, email, password, phone, address, role) 
VALUES 
-- Clients
('John Smith', 'john.smith@example.com', 'hashed_password_1', '123-456-7890', '12 Vitosha St, Sofia', 'client'),
('Emma Johnson', 'emma.johnson@example.com', 'hashed_password_2', '234-567-8901', '45 Bulgaria Blvd, Plovdiv', 'client'),
('Liam Brown', 'liam.brown@example.com', 'hashed_password_3', '345-678-9012', '78 Levski St, Varna', 'client'),
('Olivia Wilson', 'olivia.wilson@example.com', 'hashed_password_4', '111-222-3333', '5 Simeon St, Ruse', 'client'),
('Noah Miller', 'noah.miller@example.com', 'hashed_password_5', '456-789-0123', '99 Tsar Osvoboditel St, Varna', 'client'),
('Sophia Davis', 'sophia.davis@example.com', 'hashed_password_6', '567-890-1234', '22 Cherni Vrah St, Sofia', 'client'),
('Mason Garcia', 'mason.garcia@example.com', 'hashed_password_7', '678-901-2345', '10 Alexander Stamboliyski Blvd, Plovdiv', 'client'),
('Isabella Martinez', 'isabella.martinez@example.com', 'hashed_password_8', '222-333-4444', '7 Rakovski St, Burgas', 'client'),
('James Rodriguez', 'james.rodriguez@example.com', 'hashed_password_9', '789-012-3456', '77 Patriarh Evtimiy Blvd, Sofia', 'client'),
('Charlotte Lopez', 'charlotte.lopez@example.com', 'hashed_password_10', '890-123-4567', '88 Hristo Botev Blvd, Plovdiv', 'client'),
('Benjamin Gonzalez', 'benjamin.gonzalez@example.com', 'hashed_password_11', '901-234-5678', '12 Vasil Levski Blvd, Varna', 'client'),
('Amelia Wilson', 'amelia.wilson@example.com', 'hashed_password_12', '012-345-6789', '100 San Stefano St, Sofia', 'client'),
('Lucas Hernandez', 'lucas.hernandez@example.com', 'hashed_password_13', '123-555-6789', '16 Ivan Vazov St, Plovdiv', 'client'),
('Mia Clark', 'mia.clark@example.com', 'hashed_password_14', '234-666-7890', '13 Shipka St, Sofia', 'client'),
('Ethan Lewis', 'ethan.lewis@example.com', 'hashed_password_15', '345-777-8901', '20 St. Naum Blvd, Sofia', 'client'),
('Harper Walker', 'harper.walker@example.com', 'hashed_password_16', '555-888-9999', '33 San Stefano St, Varna', 'client'),
('Alexander Allen', 'alexander.allen@example.com', 'hashed_password_17', '456-888-9012', '5 Tsarigradsko Shose Blvd, Sofia', 'client'),
('Ella Young', 'ella.young@example.com', 'hashed_password_18', '567-999-0123', '8 Vasil Levski Blvd, Plovdiv', 'client'),
('Daniel King', 'daniel.king@example.com', 'hashed_password_19', '678-000-1234', '27 Shipka St, Sofia', 'client'),
('Scarlett Wright', 'scarlett.wright@example.com', 'hashed_password_20', '789-111-2345', '44 Tsar Boris III Blvd, Sofia', 'client'),

-- Admins
('William Scott', 'william.scott@example.com', 'hashed_password_21', '890-222-3456', 'Admin Office, Sofia', 'admin'),
('Ava Hall', 'ava.hall@example.com', 'hashed_password_22', '901-333-4567', 'IT Department, Plovdiv', 'admin');


INSERT INTO car (user_id, brand, model, year, rg_number) 
VALUES 
(1, 'Toyota', 'Corolla', 2018, 'BG1234AB'),
(2, 'Honda', 'Civic', 2020, 'BG5678CD'),
(3, 'Ford', 'Focus', 2017, 'BG9012EF'),
(4, 'BMW', '320i', 2019, 'BG3456GH'),
(5, 'Mercedes-Benz', 'C-Class', 2021, 'BG7890IJ'),
(6, 'Audi', 'A4', 2016, 'BG1122KL'),
(7, 'Volkswagen', 'Golf', 2015, 'BG3344MN'),
(8, 'Nissan', 'Qashqai', 2022, 'BG5566OP'),
(9, 'Hyundai', 'Tucson', 2018, 'BG7788QR'),
(10, 'Kia', 'Sportage', 2019, 'BG9900ST'),
(11, 'Chevrolet', 'Cruze', 2014, 'BG2233UV'),
(12, 'Mazda', 'CX-5', 2020, 'BG4455WX'),
(13, 'Subaru', 'Forester', 2021, 'BG6677YZ'),
(14, 'Renault', 'Clio', 2013, 'BG8899AA'),
(15, 'Peugeot', '208', 2017, 'BG1010BB'),
(16, 'Fiat', '500', 2015, 'BG1212CC'),
(17, 'Opel', 'Astra', 2018, 'BG1414DD'),
(18, 'Volvo', 'XC60', 2021, 'BG1616EE'),
(19, 'Skoda', 'Octavia', 2019, 'BG1818FF'),
(20, 'Tesla', 'Model 3', 2022, 'BG2020GG');

