-- Create User table
CREATE TABLE User (
    userId CHAR(36) PRIMARY KEY,
    username VARCHAR(50) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    phoneNumber VARCHAR(15) UNIQUE,
    passwordHash VARCHAR(255) NOT NULL,
    role ENUM('User', 'Admin') DEFAULT 'User',
    isVerified BOOLEAN DEFAULT FALSE,
    createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updatedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX (userId),
    INDEX (email),
    INDEX (phoneNumber)
);


-- Create Course table
CREATE TABLE Course (
    courseId CHAR(36) PRIMARY KEY,
    title VARCHAR(100) NOT NULL,
    description TEXT,
    price DECIMAL(10, 2),
    status ENUM('Active', 'Inactive', 'Upcoming') DEFAULT 'Upcoming',
    startDate TIMESTAMP,
    endDate TIMESTAMP,
    createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updatedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX (courseId),
    INDEX (status)
);

-- Create Newsletter table
CREATE TABLE Newsletter (
    newsletterId CHAR(36) PRIMARY KEY,
    courseId CHAR(36),
    releaseDate TIMESTAMP,
    FOREIGN KEY (courseId) REFERENCES Course(courseId)
);

-- Create Payment table
CREATE TABLE Payment (
    paymentId CHAR(36) PRIMARY KEY,
    userId CHAR(36),
    courseId CHAR(36),
    amount DECIMAL(10, 2),
    status ENUM('Pending', 'Completed', 'Failed') DEFAULT 'Pending',
    transactionId VARCHAR(255) UNIQUE,
    createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (userId) REFERENCES User(userId),
    FOREIGN KEY (courseId) REFERENCES Course(courseId)
);

-- Create Subscription table
CREATE TABLE Subscription (
    subscriptionId CHAR(36) PRIMARY KEY,
    userId CHAR(36),
    courseId CHAR(36),
    startDate TIMESTAMP,
    endDate TIMESTAMP,
    isActive BOOLEAN DEFAULT TRUE,
    FOREIGN KEY (userId) REFERENCES User(userId),
    FOREIGN KEY (courseId) REFERENCES Course(courseId),
    INDEX (userId),
    INDEX (courseId)
);

-- Create Video table
CREATE TABLE Video (
    videoId CHAR(36) PRIMARY KEY,
    courseId CHAR(36),
    title VARCHAR(100) NOT NULL,
    duration INT,
    fileUrl VARCHAR(255),
    drmLicenseKey VARCHAR(255),
    createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (courseId) REFERENCES Course(courseId),
    INDEX (videoId),
    INDEX (courseId)
);

-- Create DRM License table
CREATE TABLE DrmLicense (
    licenseId CHAR(36) PRIMARY KEY,
    userId CHAR(36),
    videoId CHAR(36),
    licenseKey VARCHAR(255) UNIQUE,
    expiresAt TIMESTAMP,
    FOREIGN KEY (userId) REFERENCES User(userId),
    FOREIGN KEY (videoId) REFERENCES Video(videoId)
);

-- Create Notification table
CREATE TABLE Notification (
    notificationId CHAR(36) PRIMARY KEY,
    userId CHAR(36),
    type ENUM('Email', 'Push', 'SMS'),
    message TEXT,
    status ENUM('Pending', 'Sent', 'Failed') DEFAULT 'Pending',
    createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    sentAt TIMESTAMP NULL,
    FOREIGN KEY (userId) REFERENCES User(userId),
    INDEX (userId),
    INDEX (status)
);