CREATE TABLE User (
    account VARCHAR(50) PRIMARY KEY,
    nickname VARCHAR(50) DEFAULT NULL,
    password VARCHAR(255) NOT NULL,
    address VARCHAR(255) DEFAULT NULL,
    sex VARCHAR(10) DEFAULT NULL,
    merchant VARCHAR(50) DEFAULT NULL,
    type INT DEFAULT NULL,
    avatarpath VARCHAR(255) DEFAULT NULL,
    salt VARCHAR(255) DEFAULT NULL,
    gold DECIMAL(10,2) DEFAULT 999,
    available INT DEFAULT NULL,
) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

CREATE TABLE Role (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    remark VARCHAR(255) DEFAULT NULL,
    available BOOLEAN DEFAULT TRUE
) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE TABLE Permission (
    id INT PRIMARY KEY AUTO_INCREMENT,
    pid INT DEFAULT NULL,
    title VARCHAR(255) DEFAULT NULL,
    percode VARCHAR(50) DEFAULT NULL
)CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

CREATE TABLE role_permission (
    rid INT,
    pid INT,
    PRIMARY KEY (rid, pid),
    FOREIGN KEY (rid) REFERENCES Role(id),
    FOREIGN KEY (pid) REFERENCES Permission(id)
)CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;


CREATE TABLE notice (
    id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    content TEXT NOT NULL,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    publisher VARCHAR(100) NOT NULL,
    duration INT NOT NULL
)CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

CREATE TABLE goods (
    id INT AUTO_INCREMENT PRIMARY KEY,
    price DECIMAL(10, 2),
    image_path VARCHAR(255),
    merchant VARCHAR(50),
    available INT,
    introduction VARCHAR(255),
    label VARCHAR(255)
    FOREIGN KEY (merchant) REFERENCES User(account)
)CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

CREATE TABLE user_goods(
    account VARCHAR(50),
    gid INT,
    num INT,
    finishTime TIMESTAMP DEFAULT NULL,
    status int COMMENT '0未完成,也就是在购物车里. 1完成交易, -1退货',
    FOREIGN KEY(account) REFERENCES User(account),
    FOREIGN KEY(gid) REFERENCES goods(id),
    PRIMARY KEY(account, gid)
)CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

CREATE TABLE comments(
    id int AUTO_INCREMENT PRIMARY KEY,
    account VARCHAR(50),
    gid INT,
    star int,
    content TEXT,
    image_path VARCHAR(255) DEFAULT NULL,
    is_anonym INT,
    FOREIGN KEY(account) REFERENCES User(account),
    FOREIGN KEY(gid) REFERENCES goods(id)
)CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;


INSERT INTO Role VALUES(1, "普通用户", "普通用户,拥有查看,购买,评论等权限", 1);
INSERT INTO Role VALUES(2, "商家", "商家,拥有发布,修改,创建客服账号等权限", 1);
INSERT INTO Role VALUES(3, "客服", "仅仅可以由商家在系统创建", 1);