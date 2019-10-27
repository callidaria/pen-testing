INSERT INTO `categories` (`name`, `description`) VALUES
('pens', 'good old pens');

INSERT INTO `inventory` (`position`, `product_id`) VALUES
(1, 1);

INSERT INTO `products` (`product_id`, `name`, `category_id`, `weight`, `prize`) VALUES
(1, 'PenXL', 1, 10, 100);