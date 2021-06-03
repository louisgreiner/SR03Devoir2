-- phpMyAdmin SQL Dump
-- version 5.1.0
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1
-- Généré le : mer. 05 mai 2021 à 19:14
-- Version du serveur :  10.4.18-MariaDB
-- Version de PHP : 8.0.3

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `sr03-devoir2`
--

-- --------------------------------------------------------

--
-- Structure de la table `chat`
--

CREATE TABLE `chat` (
  `nom_chat` varchar(150) NOT NULL,
  `proprietaire` int(11) NOT NULL,
  `description` varchar(500) NOT NULL,
  `date_fin` date NOT NULL,
  `duree_validite` time NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `chat`
--

INSERT INTO `chat` (`nom_chat`, `proprietaire`, `description`, `date_fin`, `duree_validite`) VALUES
('Secret meeting', 3, 'Qu\'est-ce que vous ne comprenez pas dans le terme secret??', '2100-01-01', '19:29:00'),
('SR03', 2, 'Groupe de soutien pour Louis Greiner (il est a la ramasse)', '2021-07-01', '20:00:00'),
('UTC =)', 1, 'Groupe Facebook étudiant de l\'UTC', '2021-06-25', '18:00:00');

-- --------------------------------------------------------

--
-- Structure de la table `invitation`
--

CREATE TABLE `invitation` (
  `chat` varchar(150) NOT NULL,
  `user` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `invitation`
--

INSERT INTO `invitation` (`chat`, `user`) VALUES
('Secret meeting', 2),
('Secret meeting', 4),
('Secret meeting', 6),
('SR03', 1),
('SR03', 5),
('UTC =)', 2),
('UTC =)', 3);

-- --------------------------------------------------------

--
-- Structure de la table `userdb`
--

CREATE TABLE `userdb` (
  `id_user` int(11) NOT NULL,
  `nom` varchar(60) NOT NULL,
  `prenom` varchar(60) NOT NULL,
  `email` varchar(120) NOT NULL,
  `pwd` varchar(60) NOT NULL,
  `role` varchar(60) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `userdb`
--

INSERT INTO `userdb` (`id_user`, `nom`, `prenom`, `email`, `pwd`, `role`) VALUES
(1, 'Greiner', 'Louis', 'louis.greiner@etu.utc.fr', '1234', 'admin'),
(2, 'Missaoui', 'Benjamin', 'benjamin.missaoui@etu.utc.fr', '0000', 'admin'),
(3, 'Chen', 'Yuheng', 'yuheng.chen@etu.utc.fr', 'motdepasse', 'admin'),
(4, 'Obama', 'Barack', 'white@house.us', 'abcde', 'other'),
(5, 'Merkel', 'Angela', 'ger@many', '1989', 'other'),
(6, 'Queen', 'Elisabeth', 'united@kingdom', 'uk', 'other');

--
-- Index pour les tables déchargées
--

--
-- Index pour la table `chat`
--
ALTER TABLE `chat`
  ADD PRIMARY KEY (`nom_chat`),
  ADD KEY `proprietaire` (`proprietaire`);

--
-- Index pour la table `invitation`
--
ALTER TABLE `invitation`
  ADD PRIMARY KEY (`chat`,`user`);

--
-- Index pour la table `userdb`
--
ALTER TABLE `userdb`
  ADD PRIMARY KEY (`id_user`);

--
-- AUTO_INCREMENT pour les tables déchargées
--

--
-- AUTO_INCREMENT pour la table `userdb`
--
ALTER TABLE `userdb`
  MODIFY `id_user` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `chat`
--
ALTER TABLE `chat`
  ADD CONSTRAINT `chat_ibfk_1` FOREIGN KEY (`proprietaire`) REFERENCES `userdb` (`id_user`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
