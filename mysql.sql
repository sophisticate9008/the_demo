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
    title VARCHAR(255) DEFAULT NULL,
    percode VARCHAR(50) DEFAULT NULL,
    icon VARCHAR(255) DEFAULT NULL,
    href VARCHAR(255) DEFAULT NULL,
    type VARCHAR(255) DEFAULT NULL
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
    id INT AUTO_INCREMENT PRIMARY KEY,
    account VARCHAR(50),
    gid INT,
    num INT,
    finishTime TIMESTAMP DEFAULT NULL,
    status int COMMENT '0未完成,也就是在购物车里. 1完成交易, -1退货申请, 2退货成功 -2退货失败',
    cost DECIMAL(10, 2),
    FOREIGN KEY(account) REFERENCES User(account),
    FOREIGN KEY(gid) REFERENCES goods(id)
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

CREATE TABLE history(
    account VARCHAR(50),
    gid int,
    view_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY(account) REFERENCES User(account),
    FOREIGN KEY(gid) REFERENCES goods(id),
    PRIMARY KEY(account, gid)
)CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

INSERT INTO Role VALUES(1, "普通用户", "普通用户,拥有查看,购买,评论等权限", 1);
INSERT INTO Role VALUES(2, "商家", "商家,拥有发布,修改,创建客服账号等权限", 1);
INSERT INTO Role VALUES(3, "客服", "仅仅可以由商家在系统创建", 1);
INSERT INTO Role VALUES(4, "管理员", "拥有高级权限", 1);

INSERT INTO Permission VALUES(1,"创建客服权限","merchant:createCustomerService", "", "", "permission");
INSERT INTO permission VALUES(2, "客服管理菜单", "merchant:客服管理:客服管理:1", "", "people", "menu");
INSERT INTO permission VALUES(3, "客服概览", "merchant:客服管理:客服概览:2", "sys/customerServiceManagement", "person", "menu");
INSERT INTO permission VALUES(4, "添加客服", "merchant:客服管理:添加客服:2", "sys/createCustomerService", "person-add", "menu");
INSERT INTO permission VALUES(5, "删除客服权限", "merchant:controlCustomerService", "", "", "permission");
INSERT INTO Permission VALUES(6, "商品管理菜单", "merchant:商品管理:商品管理:1", "", "box2", "menu");
INSERT INTO Permission VALUES(7, "商品概览", "merchant:商品管理:商品概览:2", "/sys/goodsManagement", "bar-chart", "menu");
INSERT INTO Permission VALUES(8, "商品概览", "merchant:商品管理:添加商品:2", "/sys/addGoods", "plus", "menu");
INSERT INTO Permission VALUES(9,"添加商品权限","merchant:addGoods", "", "", "permission");
INSERT INTO Permission VALUES(10,"更新商品权限","merchant:updateGoods", "", "", "permission");
INSERT INTO Permission VALUES(11, "同意退货权限", "customerService:agreeRefound", "", "", "permission");
INSERT INTO Permission VALUES(12, "订单管理菜单", "user:订单管理:订单管理:1", "", "clipboard", "menu");
INSERT INTO Permission VALUES(13, "订单概览", "user:订单管理:订单概览:2", "/sys/orderManagement", "clipboard-data", "menu");
INSERT INTO Permission VALUES(14, "同意退订权限", "customerService:agreeRefound", "", "", "permisssion");
INSERT INTO Permission VALUES(15, "退订管理菜单", "customerService:退订管理:退订管理:1", "", "clipboard", "menu");
INSERT INTO Permission VALUES(16, "退订概览", "customerService:退订管理:退订概览:2", "/sys/refoundManagement", "clipboard-data", "menu");


INSERT INTO role_permission VALUES(2, 1);
INSERT INTO role_permission VALUES(2, 2);
INSERT INTO role_permission VALUES(2, 3);
INSERT INTO role_permission VALUES(2, 4);
INSERT INTO role_permission VALUES(2, 5);
INSERT INTO role_permission VALUES(2, 6);
INSERT INTO role_permission VALUES(2, 7);
INSERT INTO role_permission VALUES(2, 8);
INSERT INTO role_permission VALUES(2, 9);
INSERT INTO role_permission VALUES(2, 10);
INSERT INTO role_permission VALUES(3, 11);
INSERT INTO role_permission VALUES(1, 12);
INSERT INTO role_permission VALUES(1, 13);
INSERT INTO role_permission VALUES(3, 14);
INSERT INTO role_permission VALUES(3, 15);
INSERT INTO role_permission VALUES(3, 16);
