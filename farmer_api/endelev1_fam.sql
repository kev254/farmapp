-- phpMyAdmin SQL Dump
-- version 4.9.7
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Generation Time: Dec 31, 2022 at 11:24 AM
-- Server version: 5.7.36
-- PHP Version: 7.4.33

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `endelev1_fam`
--

-- --------------------------------------------------------

--
-- Table structure for table `fertilizer_stock`
--

CREATE TABLE `fertilizer_stock` (
  `id` int(20) NOT NULL,
  `name` varchar(50) NOT NULL,
  `supplier_id` int(50) NOT NULL,
  `quantity` int(20) NOT NULL,
  `price` int(20) NOT NULL,
  `description` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `fertilizer_usage`
--

CREATE TABLE `fertilizer_usage` (
  `id` int(20) NOT NULL,
  `name` varchar(50) NOT NULL,
  `quantity_used` int(50) NOT NULL,
  `quantity_rem` int(100) NOT NULL,
  `cost` int(50) NOT NULL,
  `farmer_id` int(50) NOT NULL,
  `supplier_id` int(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `fertilizer_usage`
--

INSERT INTO `fertilizer_usage` (`id`, `name`, `quantity_used`, `quantity_rem`, `cost`, `farmer_id`, `supplier_id`) VALUES
(1, 'Potasium Phosphat', 40, 80, 5, 8, 1),
(2, 'Calicium (CA)', 20, 70, 29, 8, 3),
(3, '', 0, 0, 0, 0, 0),
(4, '', 0, 0, 0, 0, 0),
(5, 'hshshs', 66, 5944, 400, 8, 3),
(6, 'hshshs', 66, 5944, 400, 8, 3),
(7, 'hshshs', 66, 5944, 400, 8, 3),
(8, 'hshshs', 66, 5944, 400, 8, 3),
(9, 'hshshs', 66, 5944, 400, 8, 3),
(10, 'hshshs', 66, 5944, 400, 8, 3),
(11, 'hshshs', 66, 5944, 400, 8, 3),
(12, 'hshshs', 66, 5944, 400, 8, 3),
(13, 'hshshs', 66, 5944, 400, 8, 3),
(14, 'hshshs', 66, 5944, 400, 8, 3),
(15, 'hshshs', 66, 5944, 400, 8, 3),
(16, 'Potassium Nitrate ', 0, 20, 400, 8, 3);

-- --------------------------------------------------------

--
-- Table structure for table `sales`
--

CREATE TABLE `sales` (
  `id` int(10) NOT NULL,
  `fertilizer_name` varchar(50) NOT NULL,
  `farmer_id` int(10) NOT NULL,
  `supplier_id` int(10) NOT NULL,
  `quantity` int(50) NOT NULL,
  `price` int(200) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `supplier_shops`
--

CREATE TABLE `supplier_shops` (
  `id` int(20) NOT NULL,
  `name` varchar(50) NOT NULL,
  `location` varchar(50) NOT NULL,
  `supplier_id` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `id` int(10) NOT NULL,
  `phone` varchar(13) NOT NULL,
  `role` varchar(50) NOT NULL DEFAULT 'farmer',
  `password` varchar(50) NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `phone`, `role`, `password`, `created_at`) VALUES
(8, '0701451519', 'farmer', 'e10adc3949ba59abbe56e057f20f883e', '2022-12-31 04:38:15');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `fertilizer_usage`
--
ALTER TABLE `fertilizer_usage`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `sales`
--
ALTER TABLE `sales`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `supplier_shops`
--
ALTER TABLE `supplier_shops`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `fertilizer_usage`
--
ALTER TABLE `fertilizer_usage`
  MODIFY `id` int(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=17;

--
-- AUTO_INCREMENT for table `sales`
--
ALTER TABLE `sales`
  MODIFY `id` int(10) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `supplier_shops`
--
ALTER TABLE `supplier_shops`
  MODIFY `id` int(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
