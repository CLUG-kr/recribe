drop table user;
drop table work;
drop table board;
drop table platform;
drop table review;
drop table like_work;
drop table like_review;
drop table groups;
drop table friend;
drop table reply;

select * from user;
select * from work;
select * from board;
select * from platform;
select * from review;
select * from like_work;
select * from like_review;
select * from groups;
select * from friend;
select * from reply;

-- user Table Create SQL
CREATE TABLE user
(
    `user_uid`       INT            NOT NULL    AUTO_INCREMENT COMMENT '사용자 고유번호', 
    `user_name`      VARCHAR(45)    NOT NULL    COMMENT '사용자 이름', 
    `user_nickname`  VARCHAR(45)    NOT NULL    COMMENT '사용자 닉네임', 
    `user_password`  VARCHAR(45)    NOT NULL    COMMENT '사용자 비밀번호', 
    `user_email`     VARCHAR(45)    NOT NULL    COMMENT '사용자 이메일', 
    `user_gender`    INT            NULL        COMMENT '사용자 성별, 여자:0, 남자:1', 
    `user_age`       INT            NULL        COMMENT '사용자 나이', 
    `user_date`      DATETIME       NULL        DEFAULT now()	COMMENT '사용자 가입날짜' , 
    PRIMARY KEY (user_uid)
);

ALTER TABLE user
    ADD CONSTRAINT UC_user_email UNIQUE (user_email);

ALTER TABLE user
    ADD CONSTRAINT UC_user_nickname UNIQUE (user_nickname);

ALTER TABLE user COMMENT '사용자';

-- user Table Create SQL
CREATE TABLE work
(
    `work_uid`        INT            NOT NULL    AUTO_INCREMENT COMMENT '작품 고유번호', 
    `work_name`       VARCHAR(45)    NOT NULL    COMMENT '작품 이름', 
    `work_image`      VARCHAR(45)    NULL        COMMENT '작품 이미지', 
    `work_release`    DATETIME       NULL        COMMENT '작품 출시날짜', 
    `work_genre`      VARCHAR(45)    NULL        COMMENT '작품 장르', 
    `work_detail`     TEXT           NULL        COMMENT '작품 설명', 
    `work_pd`         VARCHAR(45)    NULL        COMMENT '작품 감독', 
    `platform_uid`    INT            NOT NULL        COMMENT '플랫폼 고유번호', 
    `work_rec_score`  FLOAT          NULL        COMMENT '작품 레크라이브 평점', 
    `work_nav_score`  FLOAT          NULL        COMMENT '작품 네이버 평점', 
    PRIMARY KEY (work_uid)
);

ALTER TABLE work COMMENT '작품';

-- user Table Create SQL
CREATE TABLE board
(
    `board_uid`    INT             NOT NULL    AUTO_INCREMENT COMMENT '게시글 고유번호', 
    `user_id`      INT             NOT NULL    COMMENT '사용자 고유번호', 
    `board_title`  VARCHAR(200)    NOT NULL    COMMENT '게시글 제목', 
    `board_text`   TEXT            NOT NULL    COMMENT '게시글 내용', 
    `board_date`   DATETIME        NOT NULL    DEFAULT now()	COMMENT '게시글 작성날짜', 
    `board_views`  INT             NOT NULL    DEFAULT 0	COMMENT '게시글 조회수', 
    PRIMARY KEY (board_uid)
);

ALTER TABLE board COMMENT '게시판';

ALTER TABLE board
    ADD CONSTRAINT FK_board_user_id_user_user_uid FOREIGN KEY (user_id)
        REFERENCES user (user_uid) ON DELETE cascade ON UPDATE RESTRICT;

       

-- user Table Create SQL
CREATE TABLE platform
(
    `platform_uid`   INT            NOT NULL    AUTO_INCREMENT COMMENT '플랫폼 고유번호', 
    `platform_name`  VARCHAR(45)    NOT NULL    COMMENT '플랫폼 이름', 
    `platform_logo`  VARCHAR(45)    NULL        COMMENT '플랫폼 로고', 
    PRIMARY KEY (platform_uid)
);

ALTER TABLE platform COMMENT '플랫폼';

ALTER TABLE work
    ADD CONSTRAINT FK_work_platform_uid_platform_platform_uid FOREIGN KEY (platform_uid)
        REFERENCES platform (platform_uid) ON DELETE cascade ON UPDATE RESTRICT;

-- user Table Create SQL
CREATE TABLE review
(
    `review_uid`    INT         NOT NULL    AUTO_INCREMENT COMMENT '리뷰 고유번호', 
    `review_score`  FLOAT       NOT NULL    COMMENT '리뷰 평점', 
    `user_uid`      INT         NOT NULL    COMMENT '사용자 고유번호', 
    `work_uid`      INT         NOT NULL    COMMENT '작품 고유번호', 
    `review_text`   TEXT        NOT NULL    COMMENT '리뷰 내용', 
    `review_date`   DATETIME    NOT NULL    DEFAULT now()	COMMENT '리뷰 작성날짜', 
    PRIMARY KEY (review_uid)
);

ALTER TABLE review COMMENT '리뷰';

ALTER TABLE review
    ADD CONSTRAINT FK_review_user_uid_user_user_uid FOREIGN KEY (user_uid)
        REFERENCES user (user_uid) ON DELETE cascade ON UPDATE RESTRICT;

ALTER TABLE review
    ADD CONSTRAINT FK_review_work_uid_work_work_uid FOREIGN KEY (work_uid)
        REFERENCES work (work_uid) ON DELETE cascade ON UPDATE RESTRICT;


-- user Table Create SQL
CREATE TABLE reply
(
    `reply_uid`    INT     NOT NULL    AUTO_INCREMENT COMMENT '댓글 고유번호', 
    `reply_text`   TEXT    NOT NULL    COMMENT '댓글 내용', 
    `user_uid`     INT     NOT NULL    COMMENT '사용자 고유번호', 
    `board_uid`    INT     NOT NULL    COMMENT '게시글 고유번호', 
    `reply_depth`  INT     NOT NULL    COMMENT '0:댓글, 1:대댓글', 
    `parent_uid`   INT     NULL        COMMENT '대댓글일 경우 어떤 댓글의 대댓글인지',
    `reply_date`   DATETIME     NOT NULL	DEFAULT now()	COMMENT '댓글/대댓글 작성날짜',
    PRIMARY KEY (reply_uid)
);

ALTER TABLE reply COMMENT '댓글/대댓글';

ALTER TABLE reply
    ADD CONSTRAINT FK_reply_parent_uid_reply_reply_uid FOREIGN KEY (parent_uid)
        REFERENCES reply (reply_uid) ON DELETE cascade ON UPDATE RESTRICT;

ALTER TABLE reply
    ADD CONSTRAINT FK_reply_user_uid_user_user_uid FOREIGN KEY (user_uid)
        REFERENCES user (user_uid) ON DELETE cascade ON UPDATE RESTRICT;

ALTER TABLE reply
    ADD CONSTRAINT FK_reply_board_uid_board_board_uid FOREIGN KEY (board_uid)
        REFERENCES board (board_uid) ON DELETE cascade ON UPDATE RESTRICT;


-- user Table Create SQL
CREATE TABLE friend
(
    `user_uid1`  INT    NOT NULL    COMMENT '사용자 고유번호1', 
    `user_uid2`  INT    NOT NULL    COMMENT '사용자 고유번호2', 
    PRIMARY KEY (user_uid1, user_uid2)
);

ALTER TABLE friend COMMENT '친구';

ALTER TABLE friend
    ADD CONSTRAINT FK_friend_user_uid1_user_user_uid FOREIGN KEY (user_uid1)
        REFERENCES user (user_uid) ON DELETE cascade ON UPDATE RESTRICT;

ALTER TABLE friend
    ADD CONSTRAINT FK_friend_user_uid2_user_user_uid FOREIGN KEY (user_uid2)
        REFERENCES user (user_uid) ON DELETE cascade ON UPDATE RESTRICT;


-- user Table Create SQL
CREATE TABLE like_work
(
    `user_uid`  INT    NOT NULL    COMMENT '사용자 고유번호', 
    `work_uid`  INT    NOT NULL    COMMENT '작품 고유번호', 
    PRIMARY KEY (user_uid, work_uid)
);

ALTER TABLE like_work COMMENT '작품 좋아요';

ALTER TABLE like_work
    ADD CONSTRAINT FK_like_work_user_uid_user_user_uid FOREIGN KEY (user_uid)
        REFERENCES user (user_uid) ON DELETE cascade ON UPDATE RESTRICT;

ALTER TABLE like_work
    ADD CONSTRAINT FK_like_work_work_uid_work_work_uid FOREIGN KEY (work_uid)
        REFERENCES work (work_uid) ON DELETE cascade ON UPDATE RESTRICT;


-- user Table Create SQL
CREATE TABLE like_review
(
    `user_id`     INT    NOT NULL    COMMENT '사용자 고유번호', 
    `review_uid`  INT    NOT NULL    COMMENT '리뷰 고유번호', 
    PRIMARY KEY (user_id, review_uid)
);

ALTER TABLE like_review COMMENT '리뷰 좋아요';


ALTER TABLE like_review
    ADD CONSTRAINT FK_like_review_user_id_user_user_uid FOREIGN KEY (user_id)
        REFERENCES user (user_uid) ON DELETE cascade ON UPDATE RESTRICT;

ALTER TABLE like_review
    ADD CONSTRAINT FK_like_review_review_uid_review_review_uid FOREIGN KEY (review_uid)
        REFERENCES review (review_uid) ON DELETE cascade ON UPDATE RESTRICT;


-- user Table Create SQL
CREATE TABLE groups
(
    `group_uid`      INT             NOT NULL    AUTO_INCREMENT COMMENT '그룹 고유번호', 
    `group_name`     VARCHAR(45)     NOT NULL    COMMENT '그룹 이름', 
    `group_leader`   INT             NOT NULL    COMMENT '그룹 리더', 
    `user_uid1`      INT             NULL        COMMENT '그룹 사용자1', 
    `user_uid2`      INT             NULL        COMMENT '그룹 사용자2', 
    `user_uid3`      INT             NULL        COMMENT '그룹 사용자3', 
    `is_group_full`  INT             NULL	DEFAULT 0        COMMENT '그룹채워짐:1, 그룹빔:0', 
    `group_url`      VARCHAR(225)    NOT NULL    COMMENT '그룹 오픈카톡', 
    `group_url_pw`   VARCHAR(45)     NULL        COMMENT '그룹 오픈카톡 비번', 
    `group_pw`   	 VARCHAR(45)     NULL        COMMENT '그룹 비번', 
    `platform_uid`   INT             NOT NULL        COMMENT '플랫폼 고유번호', 
    PRIMARY KEY (group_uid)
);

ALTER TABLE groups COMMENT '그룹';

ALTER TABLE groups
    ADD CONSTRAINT FK_groups_platform_uid_platform_platform_uid FOREIGN KEY (platform_uid)
        REFERENCES platform (platform_uid) ON DELETE cascade ON UPDATE RESTRICT;

ALTER TABLE groups
    ADD CONSTRAINT FK_groups_user_uid1_user_user_uid FOREIGN KEY (user_uid1)
        REFERENCES user (user_uid) ON DELETE cascade ON UPDATE RESTRICT;

ALTER TABLE groups
    ADD CONSTRAINT FK_groups_group_leader_user_user_uid FOREIGN KEY (group_leader)
        REFERENCES user (user_uid) ON DELETE cascade ON UPDATE RESTRICT;

ALTER TABLE groups
    ADD CONSTRAINT FK_groups_user_uid2_user_user_uid FOREIGN KEY (user_uid2)
        REFERENCES user (user_uid) ON DELETE cascade ON UPDATE RESTRICT;

ALTER TABLE groups
    ADD CONSTRAINT FK_groups_user_uid3_user_user_uid FOREIGN KEY (user_uid3)
        REFERENCES user (user_uid) ON DELETE cascade ON UPDATE RESTRICT;
