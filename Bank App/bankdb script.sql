DROP DATABASE IF EXISTS bankdb;
CREATE DATABASE bankdb;

USE bankdb;

CREATE TABLE users (
	userId int PRIMARY KEY auto_increment,
	`name` varchar(100) not null,
    email varchar(100) not null,
    `password` varchar(100) not null
);

CREATE TABLE roles (
	roleId int PRIMARY KEY auto_increment,
    `name` varchar(100) not null
);

CREATE TABLE users_roles (
	userId INT NOT NULL,
    roleId INT NOT NULL,
	FOREIGN KEY (userId) references users(userId),
    FOREIGN KEY (roleId) references roles(roleId)
);

CREATE TABLE checkingAccount (
	checkingAccountId int PRIMARY KEY auto_increment,
    checkingBalance decimal,
    userId int,
    FOREIGN KEY (userId) references users(userId)
);

CREATE TABLE savingsAccount (
	savingsAccountId int PRIMARY KEY auto_increment,
    savingsBalance decimal,
    userId int,
    FOREIGN KEY (userId) references users(userId)
);

CREATE TABLE transactionHistory (
	transactionId int PRIMARY KEY auto_increment,
    newCheckingBalance decimal,
    newSavingsBalance decimal,
    transferValue decimal,
    userId int,
    FOREIGN KEY (userId) references users(userId)
);

select * from users;
select * from checkingAccount;
select * from savingsAccount;
select * from transactionHistory;
SELECT * FROM transactionHistory WHERE userId = 1 ORDER BY transactionId DESC LIMIT 5;