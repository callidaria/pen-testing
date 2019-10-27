CREATE TABLE `categories` (
`name` TEXT NOT NULL,
`description` text DEFAULT NULL
);

CREATE TABLE `inventory` (
`position` INTEGER NOT NULL,
`product_id` INTEGER NOT NULL,
CONSTRAINT position_unique UNIQUE (position),
FOREIGN KEY(product_id) REFERENCES products(product_id)
);

CREATE TABLE `products` (
`product_id` INTEGER NOT NULL,
`name` TEXT NOT NULL,
`category_id` INTEGER DEFAULT NULL,
`weight` INTEGER NOT NULL,
`prize` INTEGER DEFAULT NULL,
CONSTRAINT product_id_unique UNIQUE (product_id),
FOREIGN KEY(category_id) REFERENCES categories(category_id)
);
