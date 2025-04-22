SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `mysecureapplication`
--

DROP DATABASE IF EXISTS `bank_system`;
CREATE DATABASE /*!32312 IF NOT EXISTS*/ `bank_system` /*!40100 DEFAULT CHARACTER SET latin1 */;

USE `bank_system`;

-- --------------------------------------------------------
-- Table structure for table `customers`
--

CREATE TABLE `customers` (
  `accountNo` INT NOT NULL AUTO_INCREMENT,
  `fullName` VARCHAR(100) NOT NULL,
  `phoneNumber` VARCHAR(15) NOT NULL UNIQUE,
  `emailAddress` VARCHAR(100) NOT NULL UNIQUE,
  `password` VARCHAR(255) NOT NULL,
  `balance` FLOAT NOT NULL,
  PRIMARY KEY (`accountNo`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `customers`
--

INSERT INTO `customers` (`fullName`, `phoneNumber`, `emailAddress`, `password`, `balance`) VALUES
('Leonardo DiCaprio', '1234567890', 'leo.dicaprio@example.com', 'test', 100.23),
('Scarlett Johansson', '9876543210', 'scarlett.johansson@example.com', 'password1', 100.23),
('Tom Cruise', '4561237890', 'tom.cruise@example.com', 'password2', 2000.36),
('Jennifer Lawrence', '3217896540', 'jennifer.lawrence@example.com', 'password3', 500.99),
('Brad Pitt', '7418529630', 'brad.pitt@example.com', 'password4', 123.65),
('Angelina Jolie', '8529637410', 'angelina.jolie@example.com', 'password5', 6587.21),
('Robert Downey Jr.', '3692581470', 'robert.downey@example.com', 'password6', 5000.00),
('Emma Watson', '1593574860', 'emma.watson@example.com', 'password7', 86.35);

--
-- Indexes for table `customers`
--
ALTER TABLE `customers`
  ADD UNIQUE KEY `emailAddress` (`emailAddress`),
  ADD UNIQUE KEY `phoneNumber` (`phoneNumber`);

COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
