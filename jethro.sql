-- phpMyAdmin SQL Dump
-- version 3.5.2.2
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: May 08, 2015 at 07:00 PM
-- Server version: 5.5.27
-- PHP Version: 5.4.7

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `jethro`
--

-- --------------------------------------------------------

--
-- Table structure for table `appointments`
--

CREATE TABLE IF NOT EXISTS `appointments` (
  `fullname` varchar(50) NOT NULL,
  `sender` varchar(40) NOT NULL,
  `receiver` varchar(40) NOT NULL,
  `message` varchar(1000) NOT NULL,
  `date` varchar(50) NOT NULL,
  `appointment_date` varchar(40) NOT NULL,
  `status` varchar(20) NOT NULL,
  `gcm` varchar(200) NOT NULL,
  `id` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=10 ;

--
-- Dumping data for table `appointments`
--

INSERT INTO `appointments` (`fullname`, `sender`, `receiver`, `message`, `date`, `appointment_date`, `status`, `gcm`, `id`) VALUES
('asiku ', '0712994996', '0779703391', 'watch out boss stop thingiring my wife', '12/3/15', '', 'seen', '', 1),
('icytrey', '0779703391', '0712994996', 'okay fine', '24.04.2015', '', 'notseen', '', 2),
('icytrey', '0779703391', '0712994996', 'sawa', '24.04.2015', '', 'notseen', '', 3),
('icytrey', '0779703391', '0712994996', 'hey', '24.04.2015', '', 'notseen', '', 4),
('icytrey', '0779703391', '0712994996', 'okay', '24.04.2015', '', 'notseen', '', 5),
('icytrey', '0779703391', '0712994996', 'man whats up', '24.04.2015', '', 'notseen', '', 6),
('icytrey', '0779703391', '0712994996', 'yeay', '24.04.2015', '', 'notseen', '', 7),
('Asiku denis', '0712994996', '0779703391', 'okay', '24.04.2015', '', 'notseen', '', 8),
('Asiku denis', '0712994996', '0779703391', 'i have a leaking roof whats up', '24.04.2015', '', 'notseen', '', 9);

-- --------------------------------------------------------

--
-- Table structure for table `posts`
--

CREATE TABLE IF NOT EXISTS `posts` (
  `post` varchar(1000) NOT NULL,
  `date` varchar(50) NOT NULL,
  `id` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=4 ;

--
-- Dumping data for table `posts`
--

INSERT INTO `posts` (`post`, `date`, `id`) VALUES
('hey there are you okay', 'Friday 24/04/2015', 1),
('hey those who havent payed yet clear', 'Friday 24/04/2015', 2),
('hey those who havent payed yet clear', 'Friday 24/04/2015', 3);

-- --------------------------------------------------------

--
-- Table structure for table `procurements`
--

CREATE TABLE IF NOT EXISTS `procurements` (
  `load_instruction` varchar(25) NOT NULL,
  `delivery_note` varchar(30) NOT NULL,
  `create_appointment` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE IF NOT EXISTS `users` (
  `fullname` varchar(50) NOT NULL,
  `telephone` varchar(50) NOT NULL,
  `pin` varchar(50) NOT NULL,
  `role` varchar(50) NOT NULL,
  `gcm` varchar(200) NOT NULL,
  `email` varchar(50) NOT NULL,
  `id` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`),
  UNIQUE KEY `telephone` (`telephone`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=15 ;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`fullname`, `telephone`, `pin`, `role`, `gcm`, `email`, `id`) VALUES
('karamagi', 'it', '12345', 'it', '', 'i@g.com', 1),
('icytrey', 'ceo', '12345', 'ceo', '', 'i@g.com', 2),
('jethro', 'project', '12345', 'project', '', 'icy@g.com', 6),
('procurement', 'procurement', '12345', 'procurement', '', 'proc@y.com', 9),
('foreman', 'foreman', '12345', 'foreman', '', 'foreman@c.com', 12),
('okiror', '0702134132', '12345', 'foreman', '', 'f@g.com', 14);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
