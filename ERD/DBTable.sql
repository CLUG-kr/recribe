
CREATE TABLE member
(
    `mb_uid`      INT             NOT NULL    AUTO_INCREMENT, 
    `mb_id`       VARCHAR(45)     NOT NULL    , 
    `mb_pw`       TEXT            NOT NULL    , 
    `mb_name`     VARCHAR(45)     NOT NULL    , 
    `mb_img_org`  VARCHAR(200)    NULL        , 
    `mb_img_sav`  VARCHAR(200)    NULL        , 
    `mb_tel`      VARCHAR(45)     NOT NULL    , 
    `mb_email`    TEXT            NOT NULL    , 
    `mb_regDate`  DATETIME        NOT NULL    DEFAULT now(), 
    `mb_type`     INT             NOT NULL    , 
    PRIMARY KEY (mb_uid)
);

ALTER TABLE member
    ADD CONSTRAINT UC_mb_id UNIQUE (mb_id);

ALTER TABLE member
    ADD CONSTRAINT UC_mb_email UNIQUE (mb_email);