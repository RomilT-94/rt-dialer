-- phpMyAdmin SQL Dump
-- version 4.3.11
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Jun 10, 2015 at 12:59 PM
-- Server version: 5.6.24
-- PHP Version: 5.6.8

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `rt_rbos_new`
--

-- --------------------------------------------------------

--
-- Table structure for table `dialer_details_pushy`
--

CREATE TABLE IF NOT EXISTS `dialer_details_pushy` (
  `devid` varchar(255) NOT NULL,
  `phno` varchar(15) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `log_details`
--

CREATE TABLE IF NOT EXISTS `log_details` (
  `log_id` int(10) NOT NULL,
  `device_id` varchar(255) NOT NULL,
  `pushy_server` varchar(15) NOT NULL,
  `medium` varchar(15) NOT NULL,
  `sent_time` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  `received_time` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  `mobile` varchar(13) DEFAULT NULL
) ENGINE=InnoDB AUTO_INCREMENT=58 DEFAULT CHARSET=latin1;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `dialer_details_pushy`
--
ALTER TABLE `dialer_details_pushy`
  ADD PRIMARY KEY (`phno`);

--
-- Indexes for table `log_details`
--
ALTER TABLE `log_details`
  ADD PRIMARY KEY (`log_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `log_details`
--
ALTER TABLE `log_details`
  MODIFY `log_id` int(10) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=58;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
