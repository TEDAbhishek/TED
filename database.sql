
CREATE DATABASE hospitality_management;

USE hospitality_management;

CREATE TABLE guests (
  id INT PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(50) NOT NULL,
  email VARCHAR(100) UNIQUE,
  phone VARCHAR(20),
  address VARCHAR(100)
);


CREATE TABLE rooms (
  id INT PRIMARY KEY AUTO_INCREMENT,
  room_number VARCHAR(10) NOT NULL,
  room_type VARCHAR(20) NOT NULL,
  capacity INT NOT NULL,
  rate DECIMAL(10, 2) NOT NULL
);


CREATE TABLE bookings (
  id INT PRIMARY KEY AUTO_INCREMENT,
  guest_id INT NOT NULL,
  room_id INT NOT NULL,
  check_in_date DATE NOT NULL,
  check_out_date DATE NOT NULL,
  total_cost DECIMAL(10, 2) NOT NULL,
  FOREIGN KEY (guest_id) REFERENCES guests(id),
  FOREIGN KEY (room_id) REFERENCES rooms(id)
);


CREATE INDEX idx_bookings_guest_id ON bookings(guest_id);
CREATE INDEX idx_bookings_room_id ON bookings(room_id);


INSERT INTO guests (name, email, phone, address) VALUES
  ('John Doe', 'johndoe@example.com', '123-456-7890', '123 Main St'),
  ('Jane Smith', 'janesmith@example.com', '098-765-4321', '456 Elm St'),
  ('Bob Johnson', 'bobjohnson@example.com', '555-123-4567', '789 Oak St');

INSERT INTO rooms (room_number, room_type, capacity, rate) VALUES
  ('101', 'Single', 1, 100.00),
  ('102', 'Double', 2, 150.00),
  ('103', 'Suite', 3, 250.00),
  ('201', 'Single', 1, 100.00),
  ('202', 'Double', 2, 150.00),
  ('203', 'Suite', 3, 250.00);

INSERT INTO bookings (guest_id, room_id, check_in_date, check_out_date, total_cost) VALUES
  (1, 1, '2023-01-01', '2023-01-03', 300.00),
  (2, 2, '2023-01-05', '2023-01-07', 450.00),
  (3, 3, '2023-01-10', '2023-01-12', 600.00);
