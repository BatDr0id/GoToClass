-- phpMyAdmin SQL Dump
-- version 4.6.6
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Generation Time: Dec 01, 2017 at 09:55 PM
-- Server version: 10.1.28-MariaDB
-- PHP Version: 7.0.8

Create schema attendanceapp;
use attendanceapp;


SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `id3609176_attendanceapp`
--

-- --------------------------------------------------------

--
-- Table structure for table `attendance`
--

CREATE TABLE `attendance` (
  `id` int(11) NOT NULL,
  `classID` varchar(45) DEFAULT NULL,
  `sectionNumber` int(11) DEFAULT NULL,
  `dateAttended` date DEFAULT NULL,
  `studentID` varchar(45) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `attendance`
--

INSERT INTO `attendance` (`id`, `classID`, `sectionNumber`, `dateAttended`, `studentID`) VALUES
(2, 'COP4656', 1, '2017-12-01', 'student2');

-- --------------------------------------------------------

--
-- Table structure for table `classInfo`
--

CREATE TABLE `classInfo` (
  `id` int(11) NOT NULL,
  `classID` varchar(45) DEFAULT NULL,
  `name` varchar(45) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `classInfo`
--

INSERT INTO `classInfo` (`id`, `classID`, `name`) VALUES
(1, 'COP4656', 'Mobile Device Applications'),
(2, 'COP4934', 'Senior Design'),
(3, 'CNT3004', 'Intro. to Computer Networks'),
(4, 'MAD2104', 'Discrete Mathematics'),
(5, 'EEL4768', 'Computer Architecture');

-- --------------------------------------------------------

--
-- Table structure for table `classSection`
--

CREATE TABLE `classSection` (
  `id` int(11) NOT NULL,
  `classID` varchar(45) DEFAULT NULL,
  `sectionNumber` int(11) DEFAULT NULL,
  `studentID` varchar(45) DEFAULT NULL,
  `professorID` varchar(45) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `classSection`
--

INSERT INTO `classSection` (`id`, `classID`, `sectionNumber`, `studentID`, `professorID`) VALUES
(1, 'CNT3004', 1, 'student1', 'professor1'),
(2, 'COP4656', 1, 'student1', 'professor1'),
(3, 'COP4934', 1, 'student1', 'professor1'),
(4, 'EEL4768', 1, 'student1', 'professor1'),
(5, 'MAD2104', 1, 'student1', 'professor1'),
(6, 'CNT3004', 1, 'student2', 'professor1'),
(7, 'COP4656', 1, 'student2', 'professor1'),
(8, 'COP4934', 1, 'student2', 'professor1'),
(9, 'EEL4768', 1, 'student2', 'professor1'),
(10, 'MAD2104', 1, 'student2', 'professor1');

-- --------------------------------------------------------

--
-- Table structure for table `professorCOD`
--

CREATE TABLE `professorCOD` (
  `id` int(11) NOT NULL,
  `professorID` varchar(45) DEFAULT NULL,
  `code` varchar(45) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `professorCOD`
--

INSERT INTO `professorCOD` (`id`, `professorID`, `code`) VALUES
(1, 'professor1', 'abcde'),
(11, 'professor2', 'afvfgj');

-- --------------------------------------------------------

--
-- Table structure for table `professorInfo`
--

CREATE TABLE `professorInfo` (
  `id` int(11) NOT NULL,
  `professorID` varchar(45) DEFAULT NULL,
  `firstname` varchar(45) DEFAULT NULL,
  `lastname` varchar(45) DEFAULT NULL,
  `email` varchar(45) DEFAULT NULL,
  `image` text
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `professorInfo`
--

INSERT INTO `professorInfo` (`id`, `professorID`, `firstname`, `lastname`, `email`, `image`) VALUES
(1, 'professor1', 'Mike', 'Krenchaw', 'mkrenchaw@some.edu', '/images/glasses.jpg'),
(2, 'professor2', 'Ashley', 'Mavis', 'amavid@some.edu', NULL);

-- --------------------------------------------------------

--
-- Table structure for table `professorLogin`
--

CREATE TABLE `professorLogin` (
  `id` int(11) NOT NULL,
  `professorID` varchar(45) DEFAULT NULL,
  `password` varchar(45) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `professorLogin`
--

INSERT INTO `professorLogin` (`id`, `professorID`, `password`) VALUES
(1, 'professor1', 'guest'),
(2, 'professor2', 'guest');

-- --------------------------------------------------------

--
-- Table structure for table `studentInfo`
--

CREATE TABLE `studentInfo` (
  `id` int(11) NOT NULL,
  `studentID` varchar(45) NOT NULL,
  `firstname` varchar(45) DEFAULT NULL,
  `lastname` varchar(45) DEFAULT NULL,
  `email` varchar(45) DEFAULT NULL,
  `image` text Default null
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `studentInfo`
--

INSERT INTO `studentInfo` (`id`, `studentID`, `firstname`, `lastname`, `email`, `image`) VALUES
(1, 'student1', 'Johnny', 'Appleseed', 'hello@myeggroll.com', '/images/snezana.jpg\r\n'),
(2, 'student2', 'Kailey', 'Ranger', 'kranger@gmail.com', NULL);

-- --------------------------------------------------------

--
-- Table structure for table `studentLogin`
--

CREATE TABLE `studentLogin` (
  `id` int(11) NOT NULL,
  `studentID` varchar(45) DEFAULT NULL,
  `password` varchar(45) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `studentLogin`
--

INSERT INTO `studentLogin` (`id`, `studentID`, `password`) VALUES
(1, 'student1', 'guest'),
(2, 'student2', 'guest'),
(3, 'student3', 'guest');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `attendance`
--
ALTER TABLE `attendance`
  ADD PRIMARY KEY (`id`),
  ADD KEY `classID_idx` (`classID`),
  ADD KEY `sectionNumber_idx` (`sectionNumber`),
  ADD KEY `attendance_ibfk_3` (`studentID`);

--
-- Indexes for table `classInfo`
--
ALTER TABLE `classInfo`
  ADD PRIMARY KEY (`id`),
  ADD KEY `classID_idx` (`classID`);

--
-- Indexes for table `classSection`
--
ALTER TABLE `classSection`
  ADD PRIMARY KEY (`id`),
  ADD KEY `professorID_idx` (`professorID`),
  ADD KEY `classID_idx` (`classID`),
  ADD KEY `studentID_idx` (`studentID`),
  ADD KEY `sectionNumber_idx` (`sectionNumber`);

--
-- Indexes for table `professorCOD`
--
ALTER TABLE `professorCOD`
  ADD PRIMARY KEY (`id`),
  ADD KEY `professorID_idx` (`professorID`);

--
-- Indexes for table `professorInfo`
--
ALTER TABLE `professorInfo`
  ADD PRIMARY KEY (`id`),
  ADD KEY `professorID_idx` (`professorID`);

--
-- Indexes for table `professorLogin`
--
ALTER TABLE `professorLogin`
  ADD PRIMARY KEY (`id`),
  ADD KEY `professorID_idx` (`professorID`);

--
-- Indexes for table `studentInfo`
--
ALTER TABLE `studentInfo`
  ADD PRIMARY KEY (`id`),
  ADD KEY `studentID_idx` (`studentID`);

--
-- Indexes for table `studentLogin`
--
ALTER TABLE `studentLogin`
  ADD PRIMARY KEY (`id`),
  ADD KEY `studentID_idx` (`studentID`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `attendance`
--
ALTER TABLE `attendance`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
--
-- AUTO_INCREMENT for table `classInfo`
--
ALTER TABLE `classInfo`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;
--
-- AUTO_INCREMENT for table `classSection`
--
ALTER TABLE `classSection`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;
--
-- AUTO_INCREMENT for table `professorCOD`
--
ALTER TABLE `professorCOD`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;
--
-- AUTO_INCREMENT for table `professorInfo`
--
ALTER TABLE `professorInfo`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
--
-- AUTO_INCREMENT for table `professorLogin`
--
ALTER TABLE `professorLogin`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
--
-- AUTO_INCREMENT for table `studentInfo`
--
ALTER TABLE `studentInfo`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;
--
-- AUTO_INCREMENT for table `studentLogin`
--
ALTER TABLE `studentLogin`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;
--
-- Constraints for dumped tables
--

--
-- Constraints for table `attendance`
--
ALTER TABLE `attendance`
  ADD CONSTRAINT `attendance_ibfk_1` FOREIGN KEY (`classID`) REFERENCES `classSection` (`classID`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `attendance_ibfk_2` FOREIGN KEY (`sectionNumber`) REFERENCES `classSection` (`sectionNumber`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `attendance_ibfk_3` FOREIGN KEY (`studentID`) REFERENCES `studentLogin` (`studentID`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `classSection`
--
ALTER TABLE `classSection`
  ADD CONSTRAINT `classSection_ibfk_1` FOREIGN KEY (`classID`) REFERENCES `classInfo` (`classID`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `classSection_ibfk_2` FOREIGN KEY (`professorID`) REFERENCES `professorLogin` (`professorID`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `classSection_ibfk_3` FOREIGN KEY (`studentID`) REFERENCES `studentLogin` (`studentID`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `professorInfo`
--
ALTER TABLE `professorInfo`
  ADD CONSTRAINT `professorInfo_ibfk_1` FOREIGN KEY (`professorID`) REFERENCES `professorLogin` (`professorID`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `studentInfo`
--
ALTER TABLE `studentInfo`
  ADD CONSTRAINT `studentInfo_ibfk_1` FOREIGN KEY (`studentID`) REFERENCES `studentLogin` (`studentID`) ON DELETE NO ACTION ON UPDATE NO ACTION;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
