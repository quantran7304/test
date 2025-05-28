-- Insert sample data into the Role table (if not already present)
INSERT INTO Role (RoleID, RoleName, Description) VALUES
(1, 'Admin', 'Administrator with full system access'),
(2, 'Customer', 'User who rents/Buy properties'),
(3, 'Seller', 'User who lists properties for rent/sale');

INSERT INTO User (UserID, pass_word, 'NO', '', NULL, ''
, first_name, last_name, birthday, phone, email, createDate, ImgPath, RoleID) VALUES
(1, 'securepass123', 'John', 'Doe', '1985-03-15', '123-456-7890', 'john.doe@example.com', '2023-01-01 10:00:00', '/images/john_doe.jpg', 3); -- RoleID 3 for 'Owner'

-- User 2: A tenant
INSERT INTO User (UserID, pass_word, 'varchar(255)', 'NO', '', NULL, ''
, first_name, last_name, birthday, phone, email, createDate, ImgPath, RoleID) VALUES
(2, 'anothersecurepass', 'Jane', 'Smith', '1990-07-22', '098-765-4321', 'jane.smith@example.com', '2023-01-05 11:30:00', '/images/jane_smith.jpg', 2); -- RoleID 2 for 'Tenant'

select *from User
SELECT * FROM User WHERE email = 'john.doe@example.com';

select u1_0.userid,u1_0.birthday,u1_0.created_art,u1_0.email,u1_0.first_name,u1_0.last_name,u1_0.pass_word,u1_0.phone,u1_0.roleid from `user` u1_0 where u1_0.email='john.doe@example.com'
pass_word