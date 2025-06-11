-- Insert sample data into the Role table (if not already present)
INSERT INTO Role (RoleID, RoleName, Description) VALUES
(1, 'Admin', 'Administrator with full system access'),
(2, 'Customer', 'User who rents/Buy properties'),
(3, 'Seller', 'User who lists properties for rent/sale');

INSERT INTO User (UserID, pass_word
, first_name, last_name, birthday, phone, email, createDate, ImgPath, RoleID) VALUES
(1, 'securepass123', 'John', 'Doe', '1985-03-15', '123-456-7890', 'john.doe@example.com', '2023-01-01 10:00:00', '/images/john_doe.jpg', 3); -- RoleID 3 for 'Owner'

-- User 2: A tenant
INSERT INTO User (UserID, pass_word
, first_name, last_name, birthday, phone, email, createDate, ImgPath, RoleID) VALUES
(2, 'anothersecurepass', 'Jane', 'Smith', '1990-07-22', '098-765-4321', 'jane.smith@example.com', '2023-01-05 11:30:00', '/images/jane_smith.jpg', 2); -- RoleID 2 for 'Tenant'

select *from User
SELECT * FROM User WHERE email = 'john.doe@example.com';

ALTER TABLE Payment CHANGE PaymentDate payment_date DATETIME;
ALTER TABLE Payment CHANGE ReceivedBy received_by VARCHAR(255);
ALTER TABLE Payment CHANGE RentalContractID rental_contractid INT;
ALTER TABLE Payment CHANGE TransactionCode transaction_code VARCHAR(255);
ALTER TABLE Role CHANGE RoleName role_name VARCHAR(255);
ALTER TABLE User CHANGE createDate create_date DATETIME;

INSERT INTO Role (RoleID, role_name, Description)
VALUES
    (1, 'USER', 'Regular User'),
    (2, 'ADMIN', 'Administrator'),
    (3, 'SELLER', 'Seller');
INSERT INTO User (UserID, first_name, last_name, email, create_date, RoleID)
VALUES
    (1, 'Nguyen', 'Van A', 'nguyenvana@gmail.com', '2025-06-01 09:00:00', 1),
    (2, 'Tran', 'Thi B', 'tranthib@gmail.com', '2025-06-02 10:00:00', 2),
    (3, 'Le', 'Van C', 'levanc@gmail.com', '2025-06-03 11:00:00', 3);
INSERT INTO Membership (MembershipID, Type, Price, Description)
VALUES
    (1, 'BASIC', 100000, 'Basic Package'),
    (2, 'PREMIUM', 200000, 'Premium Package'),
    (3, 'GOLD', 300000, 'Gold Package');
INSERT INTO RentalContract (ContractID, TenantID, OwnerID, StartDate, EndDate, MonthlyRent, Deposit, SignedDate)
VALUES
    (1, 1, 1, '2025-06-01', '2025-12-01', 5000000, 1000000, '2025-06-01 09:00:00'),
    (2, 2, 2, '2025-06-02', '2025-12-02', 6000000, 1200000, '2025-06-02 10:00:00'),
    (3, 3, 3, '2025-06-03', '2025-12-03', 7000000, 1400000, '2025-06-03 11:00:00');
INSERT INTO Payment (PaymentID, UserID, MembershipID, rental_contractid, received_by, Amount, Status, payment_date, transaction_code, Note)
VALUES
    (1, 1, 1, 1, 'Admin', 1000000, 'SUCCESS', '2025-06-01 10:00:00', 'TX123', 'House Rent Payment'),
    (2, 1, 1, 1, 'Admin', 500000, 'PENDING', '2025-06-01 12:00:00', 'TX124', 'Deposit'),
    (3, 2, 2, 2, 'Admin', 1200000, 'SUCCESS', '2025-06-02 10:00:00', 'TX125', 'House Rent Payment'),
    (4, 2, 2, 2, 'Admin', 600000, 'PENDING', '2025-06-02 12:00:00', 'TX126', 'Deposit'),
    (5, 3, 3, 3, 'Admin', 1400000, 'SUCCESS', '2025-06-03 10:00:00', 'TX127', 'House Rent Payment'),
    (6, 3, 3, 3, 'Admin', 700000, 'PENDING', '2025-06-03 12:00:00', 'TX128', 'Deposit');

INSERT INTO Property (
    Address_Line1, Address_Line2, Region, City, Area, Interior,
    PropertyType, Num_Bedroom, Num_Compares, Num_Bathroom, Floor,
    PrivatePool, LandType, LegalStatus, ImgURL
)
VALUES
    ('Số 58 Bạch Đằng', 'Phường Hải Châu 1', 'Hải Châu', 'Đà Nẵng', 55, 55, 'Căn hộ', 1, 0, 1, 30, FALSE, 'Chung cư', 'Đã có sổ hồng', '/image1.jpg'),
    ('Vương Thừa Vũ', 'Phước Mỹ', 'Sơn Trà', 'Đà Nẵng', 35, 35, 'Căn hộ studio', 1, 0, 1, 12, FALSE, 'Chung cư', 'Sổ đỏ/ sổ hồng', '/image2.jpg'),
    ('Nguyễn Thị Minh Khai', 'Phường Thạch Thang', 'Hải Châu', 'Đà Nẵng', 68, 68, 'Condotel', 2, 0, 2, 15, FALSE, 'Chung cư', 'Sổ đỏ/ sổ hồng', '/image3.jpg'),
    ('Đường Man Thiện', 'Phường Hòa Thuận Tây', 'Hải Châu', 'Đà Nẵng', 97, 97, 'Nhà mặt phố', 3, 1, 3, 3, FALSE, 'Đất thổ cư', 'Sổ đỏ/ sổ hồng', '/image4.jpg'),
    ('Đường Lê Văn Thứ', 'Phường Mân Thái', 'Sơn Trà', 'Đà Nẵng', 103.6, 103.6, 'Tòa căn hộ', 12, 1, 6, 6, FALSE, 'Đất thổ cư', 'Sổ đỏ/ sổ hồng', '/image5.jpg'),
    ('Đường Lê Lợi', 'Phường Thạch Thang', 'Hải Châu', 'Đà Nẵng', 85.1, 85.1, 'Nhà mặt phố', 3, 1, 3, 3, FALSE, 'Đất thổ cư', 'Có sổ đỏ', '/image6.jpg'); bỏ dô bảng chjay ni trc ne