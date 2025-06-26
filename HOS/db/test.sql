-- phpMyAdmin SQL Dump
-- version 5.2.2
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Generation Time: May 09, 2025 at 10:12 AM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `test`
--

-- --------------------------------------------------------

--
-- Table structure for table `applicants`
--

CREATE TABLE `applicants` (
  `apl_id` int(9) NOT NULL,
  `apl_username` varchar(255) NOT NULL,
  `apl_password` varchar(255) NOT NULL,
  `apl_ISA` varchar(255) NOT NULL CHECK (`apl_ISA` in ('Medical Secretary','Doctor','Support Team Member','Patient'))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `applicants`
--

INSERT INTO `applicants` (`apl_id`, `apl_username`, `apl_password`, `apl_ISA`) VALUES
(1, 'user', '123', 'patient');

-- --------------------------------------------------------

--
-- Table structure for table `appointments`
--

CREATE TABLE `appointments` (
  `id` int(11) NOT NULL,
  `patient_id` int(11) NOT NULL,
  `doctor_id` int(11) DEFAULT NULL,
  `date` date NOT NULL,
  `time` varchar(15) NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `status` varchar(50) NOT NULL DEFAULT 'Pending'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `appointments`
--

INSERT INTO `appointments` (`id`, `patient_id`, `doctor_id`, `date`, `time`, `created_at`, `status`) VALUES
(6, 21, 18, '2025-05-16', '10:00 AM', '2025-05-03 09:23:38', 'Completed'),
(7, 20, 18, '2025-05-16', '10:00 AM', '2025-05-09 08:08:42', 'Completed'),
(8, 20, NULL, '2025-05-16', '10:00 AM', '2025-05-09 08:11:00', 'Cancelled');

-- --------------------------------------------------------

--
-- Table structure for table `available_dates`
--

CREATE TABLE `available_dates` (
  `id` int(11) NOT NULL,
  `date` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `available_dates`
--

INSERT INTO `available_dates` (`id`, `date`) VALUES
(6, '2025-05-16');

-- --------------------------------------------------------

--
-- Table structure for table `available_times`
--

CREATE TABLE `available_times` (
  `id` int(11) NOT NULL,
  `time_slot` varchar(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `available_times`
--

INSERT INTO `available_times` (`id`, `time_slot`) VALUES
(7, '10:00 AM');

-- --------------------------------------------------------

--
-- Table structure for table `doctors`
--

CREATE TABLE `doctors` (
  `d_id` int(11) NOT NULL,
  `u_id` int(11) NOT NULL,
  `d_name` varchar(100) NOT NULL,
  `d_lastname` varchar(100) NOT NULL DEFAULT 'Not Set',
  `d_phone` varchar(20) NOT NULL DEFAULT 'Not Set',
  `d_email` varchar(150) NOT NULL DEFAULT 'Not Set',
  `d_specialization` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `doctors`
--

INSERT INTO `doctors` (`d_id`, `u_id`, `d_name`, `d_lastname`, `d_phone`, `d_email`, `d_specialization`) VALUES
(6, 18, 'tester3', 'test', 'test', 'test', 'General Practitioner'),
(7, 24, 'tester10', 'Not Set', 'Not Set', 'Not Set', 'General Practitioner');

-- --------------------------------------------------------

--
-- Table structure for table `patients`
--

CREATE TABLE `patients` (
  `p_id` int(11) NOT NULL,
  `u_id` int(11) NOT NULL,
  `p_name` varchar(255) NOT NULL,
  `p_lastname` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `phone` varchar(20) DEFAULT NULL,
  `p_symptoms` text DEFAULT NULL,
  `p_treatment` text DEFAULT NULL,
  `dob` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `patients`
--

INSERT INTO `patients` (`p_id`, `u_id`, `p_name`, `p_lastname`, `email`, `phone`, `p_symptoms`, `p_treatment`, `dob`) VALUES
(5, 20, 'testers', 'tester', 'tester@gmail.com', '123457898', 'pain', NULL, '2003-05-23'),
(7, 21, 'tester', 'test', 'teste@gmail.com', '12345678899', 'Pain', NULL, '2006-05-25');

-- --------------------------------------------------------

--
-- Table structure for table `patient_files`
--

CREATE TABLE `patient_files` (
  `file_id` int(11) NOT NULL,
  `file_type` varchar(20) NOT NULL,
  `file_name` varchar(255) NOT NULL,
  `file_path` text NOT NULL,
  `uploaded_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `patient_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `patient_files`
--

INSERT INTO `patient_files` (`file_id`, `file_type`, `file_name`, `file_path`, `uploaded_at`, `patient_id`) VALUES
(11, 'Examination', '20250503121957_Athanasios\'s Resume.pdf', 'C:\\xampp\\htdocs\\HOS\\uploads\\20250503121957_Athanasios\'s Resume.pdf', '2025-05-03 09:19:57', 20);

-- --------------------------------------------------------

--
-- Table structure for table `reports`
--

CREATE TABLE `reports` (
  `r_id` int(11) NOT NULL,
  `support_member_id` int(11) NOT NULL,
  `support_member_name` varchar(255) NOT NULL,
  `support_member_lastname` varchar(255) NOT NULL,
  `problem_name` varchar(255) NOT NULL,
  `report` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `reports`
--

INSERT INTO `reports` (`r_id`, `support_member_id`, `support_member_name`, `support_member_lastname`, `problem_name`, `report`) VALUES
(2, 19, 'tester4', 'test', 'this doesnt work ', 'User of the website said this doesnt work ');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `u_id` int(9) NOT NULL,
  `username` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `ISA` varchar(255) NOT NULL DEFAULT 'Patient'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`u_id`, `username`, `password`, `ISA`) VALUES
(1, 'tester1', '123', 'Medical Secretary'),
(17, 'Tester2', '123', 'Patient'),
(18, 'tester3', '123', 'Doctor'),
(19, 'tester4', '123', 'Support Team Member'),
(20, 'tester', '123', 'Patient'),
(21, 'teste', '123', 'Patient'),
(22, 'tester5', '123', 'Medical Secretary'),
(23, 'tester6', '123', 'Support Team Member'),
(24, 'tester10', '123', 'Doctor');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `applicants`
--
ALTER TABLE `applicants`
  ADD PRIMARY KEY (`apl_id`),
  ADD UNIQUE KEY `apl_username` (`apl_username`);

--
-- Indexes for table `appointments`
--
ALTER TABLE `appointments`
  ADD PRIMARY KEY (`id`),
  ADD KEY `patient_id` (`patient_id`),
  ADD KEY `fk_doctor` (`doctor_id`);

--
-- Indexes for table `available_dates`
--
ALTER TABLE `available_dates`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `available_times`
--
ALTER TABLE `available_times`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `time_slot` (`time_slot`);

--
-- Indexes for table `doctors`
--
ALTER TABLE `doctors`
  ADD PRIMARY KEY (`d_id`),
  ADD KEY `u_id` (`u_id`);

--
-- Indexes for table `patients`
--
ALTER TABLE `patients`
  ADD PRIMARY KEY (`p_id`),
  ADD UNIQUE KEY `email` (`email`),
  ADD KEY `u_id` (`u_id`);

--
-- Indexes for table `patient_files`
--
ALTER TABLE `patient_files`
  ADD PRIMARY KEY (`file_id`),
  ADD KEY `fk_patient_files_user_id` (`patient_id`);

--
-- Indexes for table `reports`
--
ALTER TABLE `reports`
  ADD PRIMARY KEY (`r_id`),
  ADD KEY `support_member_id` (`support_member_id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`u_id`),
  ADD UNIQUE KEY `username` (`username`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `applicants`
--
ALTER TABLE `applicants`
  MODIFY `apl_id` int(9) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- AUTO_INCREMENT for table `appointments`
--
ALTER TABLE `appointments`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT for table `available_dates`
--
ALTER TABLE `available_dates`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `available_times`
--
ALTER TABLE `available_times`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT for table `doctors`
--
ALTER TABLE `doctors`
  MODIFY `d_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT for table `patients`
--
ALTER TABLE `patients`
  MODIFY `p_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT for table `patient_files`
--
ALTER TABLE `patient_files`
  MODIFY `file_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- AUTO_INCREMENT for table `reports`
--
ALTER TABLE `reports`
  MODIFY `r_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `u_id` int(9) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=25;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `appointments`
--
ALTER TABLE `appointments`
  ADD CONSTRAINT `appointments_ibfk_1` FOREIGN KEY (`patient_id`) REFERENCES `patients` (`u_id`) ON DELETE CASCADE,
  ADD CONSTRAINT `appointments_ibfk_2` FOREIGN KEY (`doctor_id`) REFERENCES `doctors` (`u_id`) ON DELETE CASCADE,
  ADD CONSTRAINT `fk_doctor` FOREIGN KEY (`doctor_id`) REFERENCES `doctors` (`u_id`) ON DELETE SET NULL;

--
-- Constraints for table `doctors`
--
ALTER TABLE `doctors`
  ADD CONSTRAINT `doctors_ibfk_1` FOREIGN KEY (`u_id`) REFERENCES `users` (`u_id`) ON DELETE CASCADE;

--
-- Constraints for table `patients`
--
ALTER TABLE `patients`
  ADD CONSTRAINT `patients_ibfk_1` FOREIGN KEY (`u_id`) REFERENCES `users` (`u_id`) ON DELETE CASCADE;

--
-- Constraints for table `patient_files`
--
ALTER TABLE `patient_files`
  ADD CONSTRAINT `fk_patient_files_user_id` FOREIGN KEY (`patient_id`) REFERENCES `patients` (`u_id`) ON DELETE CASCADE;

--
-- Constraints for table `reports`
--
ALTER TABLE `reports`
  ADD CONSTRAINT `reports_ibfk_1` FOREIGN KEY (`support_member_id`) REFERENCES `users` (`u_id`) ON DELETE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
