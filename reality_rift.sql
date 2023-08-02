use servdb;

drop table if exists booking;
drop table if exists saleItem;
drop table if exists sale;
drop table if exists itemimg;
drop table if exists item;
drop table if exists category;
drop table if exists groupinfo;
drop table if exists topicCollection;
drop table if exists topicLike;
drop table if exists topicMessagePhoto;
drop table if exists topicMessageLike;
drop table if exists topicMessage;
drop table if exists topicReport;
drop table if exists topicsPhoto;
drop table if exists topics;
drop table if exists GAMEDATA;
drop table if exists VerificationToken;
drop table if exists PasswordVerificationToken;
drop table if exists GoogleAuthenticatorSecret;
drop table if exists ContectResponse;
drop table if exists ContectUs;
drop table if exists orderdetails;
drop table if exists orders;
drop table if exists member;



--會員(奕辰)
create table member(
memno int identity(1001,1) primary key,
memclass varchar(15) not null,
memstatus varchar(15) not null,
email varchar(60) unique not null,
password varchar(255) not null,
memname nvarchar(15) not null,
memid varchar(10) not null,
birthdate date,
phone varchar(15),
memaddress nvarchar(30),
memimg varchar(300),
registerdate date not null,
isgoogleaccount tinyint not null
);

create table VerificationToken(
memno int primary key,
token varchar(255) not null,
expiryDate datetime not null
);

create table PasswordVerificationToken(
memno int primary key,
token int not null
);

create table GoogleAuthenticatorSecret(
memno int primary key,
[secret] varchar(50) not null,
qrcodeurl varchar(255) not null,
isenabled bit
);

create table ContectUs(
contectid int identity(1001,1) primary key,
name nvarchar(15) not null,
phone varchar(15) not null,
email varchar(60) not null,
topic nvarchar(100) not null,
theme tinyint not null,
content nvarchar(MAX) not null,
[status] tinyint not null,
contecttime datetime2(0) not null
);

insert into ContectUs(name,phone,email,topic,theme,content,[status],contecttime)
values('張麗娟','0987-654321','abc123@gmail.com','我的帳號被鎖了',1,'請幫我解鎖我的帳號',1,'2023-07-10 10:02:30'),
('張麗娟','0987-654321','abc123@gmail.com','我的帳號被鎖了',1,'請幫我解鎖我的帳號',1,'2023-07-10 10:05:30'),
('張麗娟','0987-654321','abc123@gmail.com','我的帳號被鎖了',1,'請幫我解鎖我的帳號',1,'2023-07-11 10:06:30'),
('張麗娟','0987-654321','abc123@gmail.com','我的帳號被鎖了',1,'請幫我解鎖我的帳號',1,'2023-07-11 10:09:30'),
('李建興','0932-111234','def456@gmail.com','請問要如何預約遊戲',3,'可以給我教學嗎',0,'2023-07-12 15:09:30'),
('黃雅婷','0935-123456','mno345@gmail.com','請問線上商城可以刷卡嗎',5,'請問線上商城可以刷卡嗎',0,'2023-07-12 18:09:30')

create table ContectResponse(
contectid int primary key,
responsecontent nvarchar(MAX) not null,
responsetime datetime2(0) not null

);

insert into member(email,memclass,memstatus,password,memname,memid,birthdate,phone,memaddress,memimg,registerdate,isgoogleaccount)
values('administrator@gmail.com','Administrator','active','$2a$10$JurA2oyDbwR66zndum2d4.04EYZNVnTPWXONqA1hjyfXTV235Y.ky','管理員','F123456789','2000-01-01',null,null,'/rr/img/member/bloodtrail.png','2023-06-18',0),
('a123456@gmail.com','Member','active','$2a$10$NB/fu7zGKmi6w3dZVhDUcOb85tAPUOSI.cG./5ak/AEg.LKrC7x9i','蔡明軒','F187654321','1999-12-05',null,null,'/rr/img/member/img25.jpg','2023-06-18',0),
('abc123@gmail.com','Member','active','$2a$10$NB/fu7zGKmi6w3dZVhDUcOb85tAPUOSI.cG./5ak/AEg.LKrC7x9i','張麗娟','A108234187','1985-06-14','0987-654321','臺中市南區建國南路26號','/rr/img/member/img1.jpg','2023-06-18',0),
('def456@gmail.com','Member','active','$2a$10$NB/fu7zGKmi6w3dZVhDUcOb85tAPUOSI.cG./5ak/AEg.LKrC7x9i','李建興','T211780317','2006-11-07','0932-111234','高雄市前鎮區草衙路10號','/rr/img/member/img2.jpg','2023-06-18',0),
('ghi789@gmail.com','Member','active','$2a$10$NB/fu7zGKmi6w3dZVhDUcOb85tAPUOSI.cG./5ak/AEg.LKrC7x9i','陳玉萍','E262176904','1995-02-22','0987-123456','新北市板橋區民生路100號','/rr/img/member/img3.jpg','2023-06-18',0),
('jkl012@gmail.com','Member','active','$2a$10$NB/fu7zGKmi6w3dZVhDUcOb85tAPUOSI.cG./5ak/AEg.LKrC7x9i','林思賢','D156026211','1983-09-03','0912-345678','臺北市中山區南京東路2段30號','/rr/img/member/img4.jpg','2023-06-18',0),
('mno345@gmail.com','Member','active','$2a$10$NB/fu7zGKmi6w3dZVhDUcOb85tAPUOSI.cG./5ak/AEg.LKrC7x9i','黃雅婷','C210660200','1980-07-25','0935-123456','台中市北區健行路99號','/rr/img/member/img5.jpg','2023-06-18',0),
('pqr678@gmail.com','Member','active','$2a$10$NB/fu7zGKmi6w3dZVhDUcOb85tAPUOSI.cG./5ak/AEg.LKrC7x9i','楊淑芬','F126244503','1999-12-31','0978-123456','新北市三峽區中正路40號','/rr/img/member/img6.jpg','2023-06-18',0),
('stu901@gmail.com','Member','active','$2a$10$NB/fu7zGKmi6w3dZVhDUcOb85tAPUOSI.cG./5ak/AEg.LKrC7x9i','郭明達','B154308462','1981-11-11','0918-765432','臺南市安南區海佃路2號','/rr/img/member/img7.jpg','2023-06-18',0),
('vwx234@gmail.com','Member','active','$2a$10$NB/fu7zGKmi6w3dZVhDUcOb85tAPUOSI.cG./5ak/AEg.LKrC7x9i','林昌盛','H123987651','1993-05-18','0987-654321','桃園市中壢區中大路1號','/rr/img/member/img8.jpg','2023-06-18',0),
('yza567@gmail.com','Member','active','$2a$10$NB/fu7zGKmi6w3dZVhDUcOb85tAPUOSI.cG./5ak/AEg.LKrC7x9i','蔡佳慧','F168224555','1987-03-22','0923-456789','新北市三重區重新路1段2號','/rr/img/member/img9.jpg','2023-06-18',0),
('bcd890@gmail.com','Member','active','$2a$10$NB/fu7zGKmi6w3dZVhDUcOb85tAPUOSI.cG./5ak/AEg.LKrC7x9i','張勝雄','A222258129','1979-12-06','0932-112233','彰化縣員林市萬年路50號','/rr/img/member/img10.jpg','2023-06-18',0),
('efg123@gmail.com','Member','active','$2a$10$NB/fu7zGKmi6w3dZVhDUcOb85tAPUOSI.cG./5ak/AEg.LKrC7x9i','陳建宏','H180660896','1997-08-15','0987-123456','屏東縣潮州鎮興峰里1鄰2號','/rr/img/member/img11.jpg','2023-06-18',0),
('hij456@gmail.com','Member','active','$2a$10$NB/fu7zGKmi6w3dZVhDUcOb85tAPUOSI.cG./5ak/AEg.LKrC7x9i','楊宜君','G244571951','1991-04-30','0935-123456','高雄市左營區重和路101號','/rr/img/member/img12.jpg','2023-06-18',0),
('klm789@gmail.com','Member','active','$2a$10$NB/fu7zGKmi6w3dZVhDUcOb85tAPUOSI.cG./5ak/AEg.LKrC7x9i','吳彥霖','H104674900','1998-12-26','0923--456789','新竹市東區光復路2段100號','/rr/img/member/img13.jpg','2023-06-18',0),
('a123458@gmail.com','Member','active', 'google', '張育誠', '無', Null, Null, Null,'/rr/img/member/img26.jpg','2023-07-12',1),
('b123458@gmail.com','Member','active', 'google', '鄭佩珊', '無', Null, Null, Null,'/rr/img/member/img27.png','2023-07-12',1),
('qwer1234@gmail.com','Member','active', '$2a$10$NB/fu7zGKmi6w3dZVhDUcOb85tAPUOSI.cG./5ak/AEg.LKrC7x9i', '張婷婷', 'L259610163', '1995-02-14', '0912-345678', '新北市新店區新北大道1號','/rr/img/member/img14.jpg','2023-07-13',0),
('abcd5678@gmail.com','Member','active', '$2a$10$NB/fu7zGKmi6w3dZVhDUcOb85tAPUOSI.cG./5ak/AEg.LKrC7x9i', '陳志明', 'P110222186', '1985-10-06', '0921-234567', '台北市信義區忠孝東路4段50號','/rr/img/member/img15.jpg','2023-07-13',0),
('efgh9012@gmail.com','Member','active', '$2a$10$NB/fu7zGKmi6w3dZVhDUcOb85tAPUOSI.cG./5ak/AEg.LKrC7x9i', '李俊逸', 'K283790745', '1998-05-20', '0933-456789', '新竹市東區南大路100號','/rr/img/member/img16.jpg','2023-07-14',0),
('ijkl3456@gmail.com','Member','active', '$2a$10$NB/fu7zGKmi6w3dZVhDUcOb85tAPUOSI.cG./5ak/AEg.LKrC7x9i', '林昱名', 'G189064826', '1989-11-25', '0923-567890', '台中市北區育才南路1號','/rr/img/member/img17.jpg','2023-07-14',0),
('mnop7890@gmail.com','Member','active', '$2a$10$NB/fu7zGKmi6w3dZVhDUcOb85tAPUOSI.cG./5ak/AEg.LKrC7x9i', '吳偉宏', 'M217803649', '1992-06-03', '0975-678901', '高雄市苓雅區民族一路100號','/rr/img/member/img18.jpg','2023-07-14',0),
('qrst2345@gmail.com','Member','active', '$2a$10$NB/fu7zGKmi6w3dZVhDUcOb85tAPUOSI.cG./5ak/AEg.LKrC7x9i', '黃思婷', 'A142811380', '1990-03-12', '0987-789012', '台南市中西區中山路20號','/rr/img/member/img19.jpg','2023-07-15',0),
('uvwx6789@gmail.com','Member','active', '$2a$10$NB/fu7zGKmi6w3dZVhDUcOb85tAPUOSI.cG./5ak/AEg.LKrC7x9i', '劉承翰', 'B157657531', '1993-07-26', '0938-901234', '桃園市中壢區中大路300號','/rr/img/member/img20.jpg','2023-07-15',0),
('yzab0123@gmail.com','Member','active', '$2a$10$NB/fu7zGKmi6w3dZVhDUcOb85tAPUOSI.cG./5ak/AEg.LKrC7x9i', '蔡宛蓉', 'F187879603', '1986-12-17', '0966-123456', '新北市板橋區文化路一段120號','/rr/img/member/img21.jpg','2023-07-15',0),
('cdef4567@gmail.com','Member','active', '$2a$10$NB/fu7zGKmi6w3dZVhDUcOb85tAPUOSI.cG./5ak/AEg.LKrC7x9i', '鄭佩珊', 'G289678053', '1995-09-08', '0925-345678', '台中市西區民權路20號','/rr/img/member/img22.jpg','2023-07-16',0),
('ghij8901@gmail.com','Member','active', '$2a$10$NB/fu7zGKmi6w3dZVhDUcOb85tAPUOSI.cG./5ak/AEg.LKrC7x9i', '楊雅琳', 'E128275715', '1991-08-23', '0973-456789', '台南市北區長榮路二段200號','/rr/img/member/img23.jpg','2023-07-16',0),
('klmn2345@gmail.com','Member','active', '$2a$10$NB/fu7zGKmi6w3dZVhDUcOb85tAPUOSI.cG./5ak/AEg.LKrC7x9i', '林冠霖', 'E107305150', '1991-05-06', '0927-567890', '高雄市三民區民族二路100號','/rr/img/member/img24.jpg','2023-07-16',0),
('opqr6789@gmail.com','Member','active', '$2a$10$NB/fu7zGKmi6w3dZVhDUcOb85tAPUOSI.cG./5ak/AEg.LKrC7x9i', '張育誠', 'B282277466', '1993-06-18', '0939-678901', '台北市萬華區西園路二段50號','/rr/img/member/img25.jpg','2023-07-16',0);






--商品(浩輔)


CREATE TABLE category (
  id INT PRIMARY KEY IDENTITY(1,1),
  name NVARCHAR(255) NOT NULL,
  parentid INT,
  FOREIGN KEY (parentid) REFERENCES category (id)
);
INSERT INTO category(name) VALUES ('商品'),('熱門商品'),('促銷商品'),('聯名商品'),('遊戲道具'),('紀念商品'),('史努比聯名'),('寶可夢聯名');
UPDATE category
SET parentid = 1
WHERE name IN ('熱門商品','促銷商品','聯名商品','遊戲道具','紀念商品');

DECLARE @snoopyId INT;
UPDATE category
SET parentid = 4
WHERE name IN ('史努比聯名','寶可夢聯名');


SELECT @snoopyId = id FROM category WHERE name = '史努比聯名';

INSERT INTO category(name, parentid) VALUES ('史努比T恤', @snoopyId);


create table item (
  itemid int IDENTITY(1001,1) NOT NULL,
  itemname nvarchar(50) NULL CONSTRAINT DF_t_item_itemName DEFAULT NULL,
  price DECIMAL(10,0) NULL CONSTRAINT CK_t_item_price CHECK (price >= 0),
  salescount int NULL,
  stock int NULL,
  itemstatus nvarchar(50) NOT NULL ,
  itemdetail nvarchar(1000),
  added  datetime,
  categoryid int,
  CONSTRAINT PK_t_item PRIMARY KEY (itemid),
    FOREIGN KEY (categoryid) REFERENCES category (id)
);
INSERT INTO item (itemname, price, salescount, stock, itemstatus, itemdetail, added, categoryid)
VALUES
  ('Product A', 100, 100, 50, '販售中', 'Item A description', '2020-01-01T12:00:01', 9),
  ('Product B', 200, 50, 20, '販售中', 'Item B description', DATEADD(MONTH, 1, '2020-01-01T12:00:01'), 9),
  ('Product C', 300, 200, 100, '販售中', 'Item C description', DATEADD(MONTH, 2, '2020-01-01T12:00:01'), 9),
  ('Product D', 400, 80, 30, '販售中', 'Item D description', DATEADD(MONTH, 3, '2020-01-01T12:00:01'), 9),
  ('Product E', 500, 150, 70, '販售中', 'Item E description', DATEADD(MONTH, 4, '2020-01-01T12:00:01'), 9),
  ('Product F', 600, 60, 25, '販售中', 'Item F description', DATEADD(MONTH, 5, '2020-01-01T12:00:01'), 9),
  ('Product G', 700, 120, 50, '販售中', 'Item G description', DATEADD(MONTH, 6, '2020-01-01T12:00:01'), 9),
  ('Product H', 800, 90, 35, '販售中', 'Item H description', DATEADD(MONTH, 7, '2020-01-01T12:00:01'), 9),
  ('Product I', 900, 160, 80, '販售中', 'Item I description', DATEADD(MONTH, 8, '2020-01-01T12:00:01'), 9),
  ('Product J', 1000, 70, 30, '販售中', 'Item J description', DATEADD(MONTH, 9, '2020-01-01T12:00:01'), 9),
  ('Product K', 1100, 150, 75, '販售中', 'Item K description', DATEADD(MONTH, 10, '2020-01-01T12:00:01'), 9),
  ('Product L', 1200, 120, 60, '販售中', 'Item L description', DATEADD(MONTH, 11, '2020-01-01T12:00:01'), 9),
  ('Product M', 1300, 80, 40, '販售中', 'Item M description', DATEADD(MONTH, 12, '2020-01-01T12:00:01'), 9),
  ('Product N', 1400, 70, 35, '販售中', 'Item N description', DATEADD(MONTH, 13, '2020-01-01T12:00:01'), 9),
  ('Product O', 1500, 110, 55, '販售中', 'Item O description', DATEADD(MONTH, 14, '2020-01-01T12:00:01'), 9),
  ('Product P', 1600, 160, 80, '販售中', 'Item P description', DATEADD(MONTH, 15, '2020-01-01T12:00:01'), 9),
  ('Product Q', 1700, 90, 45, '販售中', 'Item Q description', DATEADD(MONTH, 16, '2020-01-01T12:00:01'), 9),
  ('Product R', 1800, 70, 35, '販售中', 'Item R description', DATEADD(MONTH, 17, '2020-01-01T12:00:01'), 9),
  ('Product S', 1900, 160, 80, '販售中', 'Item S description', DATEADD(MONTH, 18, '2020-01-01T12:00:01'), 9),
  ('Product T', 2000, 100, 50, '販售中', 'Item T description', DATEADD(MONTH, 19, '2020-01-01T12:00:01'), 9);

CREATE TABLE orders
(
    OrderId bigint IDENTITY(1001,1) PRIMARY KEY,
    OrderDate datetime DEFAULT GETDATE(),
	orderstatus nvarchar(300),
    TotalAmount decimal(19, 0),
    Memno int FOREIGN KEY REFERENCES Member(Memno)
)

CREATE TABLE orderdetails (
    detailId bigint IDENTITY(1001,1) PRIMARY KEY,
    imageName varchar(255),
    itemid bigint,
	itemname nvarchar(500),
    quantity int,
    price decimal(10,0),
    orderId bigint,
    FOREIGN KEY (orderId) REFERENCES orders(orderId)
);
CREATE TABLE Itemimg (
    id INT IDENTITY(1,1) PRIMARY KEY,
    imagename VARCHAR(255),
    image varbinary(max),
    itemid INT,
    FOREIGN KEY (itemid) REFERENCES item (itemid)
);
INSERT INTO Itemimg (imagename, itemid)
VALUES
  ('Image1.jpg', 1001), ('Image2.jpg', 1001),('Image4.jpg', 1002),('Image6.jpg', 1003),('Image8.jpg', 1004),('Image10.jpg', 1005),
  ('Image11.jpg', 1006),('Image13.jpg', 1007),('Image14.jpg', 1007),('Image15.jpg', 1008),('Image17.jpg', 1009),('Image19.jpg', 1010),('Image20.jpg',1010),
  ('Image21.jpg',1011),('Image22.jpg',1011),('image23.jpg',1012),('image24.jpg',1012),('Image25.jpg',1013),('Image26.jpg',1013),('image27.jpg',1014),('image28.jpg',1014),('image29.jpg',1015),('image30.jpg',1015),
  ('image31.jpg',1016),('image32.jpg',1016),('image33.jpg',1017),('image35.jpg',1018),('image36.jpg',1018);

-- Set the years for which to generate orders
DECLARE @years TABLE (year INT);
INSERT INTO @years VALUES (2022), (2023);

-- Set the months for which to generate orders
DECLARE @months TABLE (month INT);
INSERT INTO @months VALUES (1), (2), (3), (4), (5), (6), (7), (8), (9), (10), (11), (12);

-- Set the last day for data generation
DECLARE @lastDay DATE = '2023-07-10';

DECLARE @year INT;
DECLARE @month INT;

DECLARE yearCursor CURSOR FOR SELECT year FROM @years;
OPEN yearCursor;
FETCH NEXT FROM yearCursor INTO @year;

WHILE @@FETCH_STATUS = 0
BEGIN
    DECLARE monthCursor CURSOR FOR SELECT month FROM @months;
    OPEN monthCursor;
    FETCH NEXT FROM monthCursor INTO @month;

    WHILE @@FETCH_STATUS = 0
    BEGIN
        -- Skip months that are after the last day
        IF DATEFROMPARTS(@year, @month, 1) > @lastDay BREAK;

        DECLARE @orderCount INT;
        IF @year = 2022
        BEGIN
            SET @orderCount = ROUND(3 + (12 - ABS(6.5 - @month)) / 6.5 * 5, 0);
        END
        ELSE IF @year = 2023
        BEGIN
            SET @orderCount = ROUND(3 + ABS(6.5 - @month) / 6.5 * 5, 0);
        END
        DECLARE @count INT = 1;
        DECLARE @totalAmount INT = 500;  -- Set total amount constant for simplicity
        WHILE @count <= @orderCount
        BEGIN
            DECLARE @memno INT;
            IF @month <= 6
                SET @memno = 1001; -- Use Memno 1001 for orders in the first half of the year
            ELSE
                SET @memno = 1002; -- Use Memno 1002 for orders in the second half of the year

            DECLARE @orderDate DATE = DATEADD(MONTH, @month - 1, DATEFROMPARTS(@year, 1, 1));
            -- Don't generate orders after the last day
            IF @orderDate > @lastDay BREAK;

            INSERT INTO orders(OrderDate, orderstatus, TotalAmount, Memno)
            VALUES (@orderDate, '完成付款', @totalAmount, @memno);
            SET @count = @count + 1;
        END;
        FETCH NEXT FROM monthCursor INTO @month;
    END;
    CLOSE monthCursor;
    DEALLOCATE monthCursor;
    FETCH NEXT FROM yearCursor INTO @year;
END;


-- 建立 Sales 表
CREATE TABLE Sale
(
    saleId INT IDENTITY(1,1) PRIMARY KEY,
	saleStatus Nvarchar(100),
    saleName NVARCHAR(255),
    saleStart DATETIME,
    saleEnd DATETIME
);

-- 建立 SaleItems 表
CREATE TABLE SaleItem
(
    id INT IDENTITY(1,1) PRIMARY KEY,
    saleId INT,
    itemId INT,
    discount DECIMAL(5, 2),
    FOREIGN KEY (saleId) REFERENCES Sale(saleId),
    FOREIGN KEY (itemId) REFERENCES Item(itemid)
);


--論壇(子傑)

create table topics(
    topicId int identity(3001,1) primary key,
    title varchar(100) not null,
    content varchar(max) not null,
    memno int not null,
	status varchar(10) not null,
	good int not null,
    publishTime datetime2(0) default getdate(),
    editTime datetime2(0),
    foreign key (memno) references member(memno)
);

create table topicsPhoto(
    topicsPhotoId int identity(5001,1) primary key,
	topicId int not null,
	topicsPhotoFile varbinary(max)
);

create table topicMessage(
    messageId int identity(4001,1) primary key,
	topicId int not null,
	content varchar(max) not null,
	memno int not null,
	status varchar(10) not null,
	publishTime datetime2(0) default getdate(),
    editTime datetime2(0),
	good int not null
);

create table topicMessagePhoto(
    messagePhotoId int identity(6001,1) primary key,
	messageId int not null,
	messageIdPhotoFile varbinary(max)
);

create table topicLike(
	topicLikeId int identity(7001,1) primary key,
	memno int references member(memno),
	topicId int references topics(topicId),
	liked int
);

create table topicMessageLike( --目前沒功能
	messageLikeId int identity(8001,1) primary key,
	memno int references member(memno),
	messageId int references topicMessage(messageId),
	liked int
);

create table topicCollection(
	collectionId int identity(8001,1) primary key,
	memno int references member(memno),
	topicId int references topics(topicId),
	collected int
);

create table topicReport(
	topicReportId int identity(9001,1) primary key,
	memno int references member(memno),
	topicId int references topics(topicId),
	reportClassification varchar(max),
	reportContnet varchar(max),
	reportTime datetime2(0) default getdate(),
	reportStatus int not null,
);


--文章----------------------------------------------------------------------------------------------
insert into topics(title,content,memno,status,good)
values('【這個Case有點Big】號稱全台最小的密室(心得)！？直覺性解謎、劇情帶點洋蔥','「這個Case有點Big」，是LoGin工作室暨第一個作品等一個人盜墓後歷時1年半才完成的主題，雖然中間卡到升三級疫情導致進度有點落後，但經過長時間打造的密室果然沒有讓人失望！
雖然場域較小了些(比起盜墓)，但可以看的出來LoGin已經將空間做最大的運用，雖然是屬於小主題，也號稱很小，但其實所包含的寓意劇情並不少，可以說在解謎與劇情有很好的平衡作用，是個謎題與劇情兼具的好密室！

帶點歡樂、又帶點小憂愁；看似很小、其實又很大，就是「這個Case有點Big」的魅力！

如果是第一次接觸LoGin密室的玩家，建議可以先選「這個Case有點Big」，再來挑戰「等一個人盜墓」。',1006,'on',15),


('高雄密室《法老謎城：是誰摸我屁股》＂Mystoto Escape Games 密室逃脫＂','從主題名稱就會知道是歡樂類型

可惜故事的歡樂度與解密連接不多，不然更可以感受到故事的趣味

有幾點大家可能要注意

1.第一關階梯高度非常規高度，會容易跌倒，在下樓梯時剛好又有板塊，我們有團友就翻船了

2.全黑關卡時大家要確認好方位與討論

3.男性玩家或女性玩家不要趁黑卡油，保持紳士。',1016,'on',17),


('【密室逃脫開箱】荒村小學｜嚇到不行校園恐怖主題','看完前導影片後，小天使領著我們戴著眼罩進入遊戲空間，進去前的路超級漫長，可能要小心會撞到門之類的東西，但同時也可以感受到遊玩空間應該滿大的。

「這是你的位子，坐下」果然是教室嗎？！這什麼恐怖的校園故事背景啦？

大家一一坐好之後，遊戲播放語音後就正式開始了。
語音廣播稍加交代故事背景，聲音在教室裡迴盪讓整個遊戲增加一股詭譎氣氛。摘下眼罩環顧四周看到同學們坐在各自位置上面面相覷（有點類似《惠貞女子高校》的教室加上 LARP 《點名》的環境），接下來就是透過各種觀察、利用線索及道具解謎囉！

整體來說謎題非常新手向，難度都不難且僅是單線解謎，謎題比較需要觀察與操作，謎題跟使用道具都是環繞著校園背景不會出戲，也讓謎題多了幾分趣味性。隨著一道一道謎題解開，會開啟更多學校裡的空間，還會開啟新的遊玩機制，慢慢理解故事的全貌。

然後這整段解謎的過程，都、很、恐、怖。

對，我謎題的部分講完了，我要講恐怖的地方，也是我覺得本主題最大的特色與亮點。

從《荒村小學》可以看到很多不落俗套的嚇人方式，不同於常見的恐怖密室主要花心力在場景及音效的使用，並將「解謎時間」與「嚇人時間」區分開來，《荒村小學》是真的放一個 NPC 進主題內，會在你各種想得到或想不到的時候出現，沈浸式還原了你突然被抓進當年那個已經廢棄的小學，感覺到哪都有一雙眼睛在盯著你看你在做什麼。除了場景恐怖到很逼真外、音效跟燈光也會配合真人 NPC 的行動去做改變，反正就是用盡全力要嚇你。

當天遊玩本團精彩成就，歡迎來挑戰：
(1)一有風聲草動全部人跟地震演習一樣馬上躲在桌子下
(2)有人蹲在地上說「我不要前進了讓我待在這裡就好」
(3)有人一路上蹲著走路
(4)有人一有動靜就蹲下捂眼往下看
(5)有人嚇到忘記女朋友
(6)我嚇到叫出來往後跳
(7)對講機叫我們要繼續往前走，我們也知道要往前走，但沒人敢往前走
(8)有人假借因為試玩女朋友會怕死都不分開硬是拉著大家一起走（用奇怪的方式破解）

呃，我也不知道是不是我們沒有坦的關係？反正我們真的是整團嚇爆了。

加上劇情推進完成後，最後開啟的空間多且非常大，有幾個通道長得又很像我當下有點迷路繞來繞去，真的是很緊張。NPC 出現的時機讓我一直很摸不透，到後來也會自己嚇自己，未知感真的是最可怕的東西呀！

最後的最後我們也會釐清為什麼我們會被抓來這邊，跟更多的故事，最後還會有個很棒的小驚喜（真的很驚喜）。
比較可惜是我們試玩場還沒有結尾說明可以看，正式遊玩就有囉！

遊玩之後我敢說是我目前玩過最恐怖跟被嚇最兇的主題，但畢竟《荒村小學》除了很多氛圍嚇人外還有 Jump Scare 跟一些互動型的驚嚇，如果比較難接受這類的玩家可能要有心理準備。真心建議膽小的倉鼠們要帶隻坦，好一點可以保護大家，好笑一點說不定坦也會變倉鼠（我平常不算坦但也不至於到很怕會叫不敢前進，玩完《荒村小學》就發現我錯了）',1005,'on',21),
('【蛋黃哥懶得去旅行】實境解謎心得》要去哪…｜居加謎解','大概是因為密室逃脫玩久了，習慣於那種「限時」、「競爭」的模式，才常常不小心把「戶外實境遊戲」也玩的緊張兮兮。但總覺得哪裡不對～戶外解謎應該要放鬆步調與心情，看看周遭有甚麼、累了就坐、餓了就吃在地美食。與同伴來場開心的輕旅行才是，如此走馬看花就可惜了。',1002,'on',3),


('期末考體驗心得','雖然是2人可包場，新手、親子友善，不過我們2個人去體驗發現完全不是這麼一回事阿XDDD

後面有好幾個地方的玩法都還滿新穎有趣的，只是結論就是我們謎題一直沒GET到所以導致體驗不是很佳，加上謎題數多&有滿多地方需要時間去解決，所以只有2個人其實有點困難QQ 建議說雖然可以2人包場，但還是多一點人去玩應該會比較有趣~

也因為空間是開放的，所以有很多個謎題要一一對應解決，所以人少&新手應該會一直很沒有方向~~如果是像我們2個一樣去玩應該會覺得雖然有趣但是略絕望XD 建議就算是老手最好也4人以上一起去玩，比較可以分工解謎~',1015,'on',18),


('【LoGin工作室推薦密室】等一個人・盜墓無雷心得評價，變身摸金校尉潛入古墓','等一個人・盜墓的場景相當用心且有誠意，顧及到多數密室逃脫工作室所不會注意的地方，連地板都有下功夫！Tessa 認為等一個人・盜墓的燒腦程度中等，適合新手來體驗，就算老手也不用擔心會無聊，有很多精彩的地方等著你去探索。

LoGin 密室逃脫工作室最令 Tessa 驚艷的地方莫過於演技值得稱讚的小隊長了！小隊長（小天使）將會全程陪同參與，用演的方式，讓玩家更能融入故事情境，完全不會有「現在剩下幾分鐘」的提示出戲感，推薦給喜歡沉浸式體驗密室逃脫的玩家。',1014,'on',87),


('鱗屋之中','五人挑戰，個人覺得普普...

幾個比較可惜的點，遊戲強調選擇的重要性，但到結局看完影片之後，都覺得...選擇這件事情好像不太重要，因為選擇這件是並沒有讓我們體會到選錯會帶來什麼樣的事情，只知道如果選了B好像會有不同結局這種感覺。

小天使很認真也很可愛友善，但...給提示方是有點過於直給，有些提示給的時機點感覺可以待玩家詢問之後再給予幫助，不然給了提示，但玩家解開卻提醒先不要執行，這感覺...蠻奇怪的，過程中遇到機關似乎疑似故障...小天使也是在旁邊嘗試修復機關，會讓玩家有些出戲...。
但整體場景佈置的不錯。

已上是五人團的心得(2位中手、3位新手)',1003,'on',78),


('逃出吸血古堡','密室BOSS出現在身邊時真心感到驚嚇，就像勇者要去打魔王繞了世界一圈，最後發現魔王其實住在新手村裡你家隔壁一樣，雖然我也喜歡純解謎的密室，但機關確實滿有趣的，原本沒注意到的地方其實是機關，機關出現後，說不定又能帶給玩家第二、第三重的驚嚇...驚喜，難怪朋友們都希望我開機關滿滿的密室團。

整體滿喜歡的。

只是朋友是老手，解完時間還剩很多，出來後小天使才說以為我們時間夠，可以破隱藏結局，畢竟是玩一次就要幾百塊，2人接近1千的遊戲，很希望小天使中途的提示能明顯一點，雖然知道尺度很難拿捏，但當下會有一種妳就是希望我們花錢二刷的不悅感，那跟喜歡所以想再來的感覺是完全不同的。

>>>>>>>>>>>>>>>>>>>>>>>

上面是一刷心得，隔了一年跑去二刷，一樣沒破隱藏結局，小天使堅持不肯提示隱藏結局的線索，這點其實無妨，就是比較確定Miss GAME的方針是希望玩家有機會二刷，講清楚規則我可以接受，但這次二刷體驗很糟，密室一樣是很好的，但西門町人太多，服務品質下降很嚴重。

我們下午打電話預訂，7點半左右就到現場付款，等到8點要入場時發現得跟人併團，但是從預訂到付款都沒有人跟我說過這件事，以前玩過，這次沒有再查仔細金額跟規則是我的錯，但多提醒客人一句，應該不會很難吧?還是說450*2跟350*4，當然選擇350*4，如果客人沒搞清楚狀況，不知道自己付的金額不是包場價，最好就忽略這件事?

其實平常要併團就算了，但這次我是二刷，很怕破壞人家的遊戲體驗，但小天使們太忙已經延後開場了，即使說明我不高興的原因也無法改變現況，所以沒有多說什麼，只是整張臉很臭，氣氛很僵，然後進場後第一道謎題改版，改得比以前簡單很多，但空間拆開，線索在另一隊那，她們沒發現，卡關了，我們那有提示鈴，拼命狂按都沒有回應，就這樣卡了應該有15分鐘甚至超過，直到彼此都懷疑對方是豬隊友時，小天使才發現異狀，跑進來提示。

第一道題過後，謎題都一樣，原本煩惱我是二刷，不知提示好還是不提示好，這下可好了，因為擔心沒過關的話另一隊的遊戲體驗會更糟，我就快馬加鞭所有線索都秒讀，假裝理解，直接提示大家怎麼解謎怎麼操作機關，好在當打醬油的主揪慣了，習慣分配事情給大家，應該都有碰到機關與謎題，但提示那麼多，另一隊是否有解謎的成就感，我就無暇顧及了。

畢竟我要一邊演戲裝作第一次來玩，只是理解比較快(雖然有時思考或被反駁時會不小心說溜嘴，說我記得是怎樣)，一邊又希望能留些尋找隱藏結局線索的時間，整場內心戲爆炸，焦慮到不行，最後也沒破到隱藏結局，一樣只有普通結局，玩完後感到非常憤怒，大概是繼玩迴異路跟醒之後第二不堪回首的記憶吧。',1017,'on',51),


('實境解謎心得-居加謎解《咕咕呱呱大冒險 可愛的祕寶》','12/11當天天氣冷到不行，但沒有大雨，只有飄毛毛細雨，看網路心得說是新手向實境解謎，謎題簡單，覺得應該玩2~3小時就會回家，硬著頭皮吃個止經痛的藥，就維持原訂計畫出門了。

孰不知玩了4.5hr。

反正都到晚上了，乾脆揪著大家一起去吃2天快閃的花博耶誕市集，雖然天氣冷，很多攤位提早收了，餐點又偏貴，但各種異國料理餐車真的很美味，加點小酒，不小心就吃飽喝足飄飄然地回家，為當天遊戲過程畫下了完美句點。

可惜我因此感冒了，隔天就開始喉嚨痛，感冒一周又接著確診一周，吃藥吃到反胃想吐這樣。


啊，風蕭蕭兮易水寒，梅子一去兮不復還。


這件事告訴我們反正遊戲地點在雙北、人也在雙北，隨時可以改約，真的不要勉強自己在寒流來的天氣進行戶外遊戲這樣。',1018,'on',101),


('逃出補習班 2.0','可能大家都太在狀況內，自己負責的任務結束的時候別人也差不多搞定了，還有時間互相說明一下解題手法。沒有被題海淹沒的感覺，頂多只是不覺得空虛。
空間大小還可以接受，但考量到題目量其實沒有到真的太需要人手，還有能進第二結局的條件人少可能比較容易達成，沒有冗員的話我想大概6人以下就能玩題海版了。
小天使不會全程跟著，也沒有提示的機制，幸好我們沒遇到什麼完全卡死的狀況。解題主要看腦電波，腦電波對到了題目就會解很快，沒對到的話就是多花一點時間試錯，不太會有真的把人困住的狀況。
補習班的場景很有共鳴，有些讓人會心一笑的地方。',1025,'on',17),


('[心得] Vicky宇宙逃脫｜Enterspace智慧獵人 無雷','把台北神不在場的遊戲都玩過後，接著也快要制霸智慧獵人工作室了
Vicky 實際遊戲位置也不是在Enterspace櫃台處，不過一樣要先在Enterspace報到繳費才
會知道具體地方
Vicky第一個場景布置是溫馨風，不過隨著故事的演進，第二個場景之後就很有智慧獵人
的風格，另外有些密室遊戲是單向的，也就是不會回頭用到線索，但是不管是智慧獵人
or 神不在場工作室，皆會通過劇情的串聯把整個密室空間的提示或機關前後串接，所以
只要門沒關、空間依舊互通代表都有可能用到之前的線索
Vicky特色中寫到的外星科技機關、人工智慧AI互動，在第二個場景開始令人驚艷，高科
技的操作雖然稍微複雜但效果十足，且空間安排跟各種團隊合作機關都非常精緻，讓人在
遊戲後的講解時間都想要再玩一次，CONE也是一樣，不過要注意有一個空間需要簡單肢體
活動，建議要裝扮活動方便
劇情的部分跟解謎本身不直接相關，但會有引導作用，另外智慧獵人的艾比時空膠囊、
CONE控制獄、ZONE異界籠跟Vicky劇情都有先後承接的關係，可以算是系列作但沒玩過並
不會影響單獨遊戲的體驗
謎題本身應該不算難，不過需要仔細觀察各種線索，以及較多機關操作來推進遊戲，我們
五個熟手一起玩，68分鐘完成任務，根據小天使的說法我們彼此間合作良好，而且倒數的
謎題通靈似的知道提示在哪裡
總結來說官網提到的機關跟新手向都還挺符合的，雖然解謎本身不難但遊戲的機關、道具
都很有水準，對所有人都蠻適合的一款密室逃脫，基本上沒有驚悚恐怖成分',1020,'on',2),


('泰洛爾號|心得','場景非常用心
意外是裡面有npc可以一直互動
小天使也非常善良常常給予提示
老實說謎題真的有點難
機關部分還有操作時間限制,非常讓人意外
結局也是有多重
不過建議人數需要5人,不然其中的人要多兼差XD',1019,'on',9),


('密室逃脫心得-EnterSpace《CONE 控制獄》','雖然身為密室團主揪，但我一直不太會解謎，對各密室謎題難度評鑑都是跟我家內建小天使討論決定的，連中期才加入團隊的男友解謎都變得比我快，在這場密室中有驚艷的表現，而內建小天使...依然在大家都沒注意時就獨自解了超難的謎題，讓小天使嚇一跳。

雖然自己解謎等級上不去有點遺憾，但我很喜歡在旁邊看大家解謎開心的樣子(快給我解謎，不要每個謎題都看了就走啊 by男友)，所以很累或遇到各種挫折，還是一直當主揪，平常總是擔心大家玩得不滿意，但玩控制獄時覺得很安心，因為很好玩很歡樂，雖不到炸鬥府那種玩瘋的狀態，也不可能符合每個人的期待，但一定是好的體驗，非常推薦。

然後我第一次記得小天使的名字—鹹魚飯，上次玩Vicky也是他帶的，整場都細心陪在旁邊，不會一直滑手機或忙場復，總是適時給提示，也不會給得太多，而且解說時非常熱情，感覺把整間密室謎題與故事都倒背如流了，那樣歡快的情緒也感染到我們，真的很謝謝他。',1007,'on',81),


('密室逃脫心得-掌門人密室逃脫《塭噬》','如果大家場次時間正好接近晚餐時間的話，很推薦去吃附近的「小喬新疆羊肉串」。串類調味較重，個人沒有特別喜歡，但是清燉羊肉湯非常好喝，我只要去板橋有經過的話就一定會買，如果人多又敢吃辣的話還可以叫大盤雞一起分享，美味實惠。

我們當天6個人玩完《塭噬》之後，再加1位因為人數上限先回家待機的團員，7個人一起用餐。
因為Kallen兄弟早餐吃比較飽，沒吃午餐、我家小天使只有在西門喝「世運」的芋頭西米露墊胃、我和老公有吃一些「老天祿」的滷味，但也沒很飽，所以餐點一上來大家整個狼吞虎嚥、用風捲殘雲般的速度肅清食物，Kallen還吃到整張臉很像塞滿食物的倉鼠……整個吃飯過程有種比玩密室還瘋狂的感覺，非常魔幻寫實。
難怪Kallen在玩《夜蒐人靜》之後，要選我很餓的牌子拍照……我終於解開了謎團。(抱歉，沒有放上團照XD)

這告訴我們團員都是男生的話，行程還是不要排太滿，不要擠壓到吃飯時間。
不過雖然有6個很餓的大男生，吃了非常多的食物，但最後結帳平攤起來每人大概付3百出頭，不會太貴，真心推薦大家品嘗看看。
',1023,'on',92),


('穿越者實境密室逃脫《夜蒐樓靜》','《櫻花暖居》與《夜蒐樓靜》相比的話，個人比較喜歡《櫻花暖居》，因為整體場景精緻，遊戲趣味性也較高，不過這款主題故事需要推理與反思的地方較多，餘韻留存較久。

話說7/3當天我原本打算《屍控邊緣》的邀約場結束後，再去預訂付費的場次，一次刷完4條故事線，所以後面不打算排行程。但是具體遊戲時間出來之後，又擔心難得出門一趟，團員會覺得這樣時間太短，於是多排了《夜蒐樓靜》跟《塭噬》，因為人比較多，一開始我是訂了2場《夜蒐樓靜》，想拆成2組人玩。

然後訂好過了幾天，才發現自己眼殘看錯遊戲時間，《夜蒐樓靜》第二場結束後，會來不及移動到板橋，原本想說小密室應該會玩得快一點，或是不要聽解說去趕看看。但是跟R大討論才知道這主題會玩很久，只好試著拜託穿越者如果到了當天，更早的場次沒人預訂，能不能讓我們提早15分鐘進場之類的……(遮臉

不過能否提早進場要等到當天才能確定，穿越者的小編人很好，試著提供了很多建議，看能不能幫我及早處理，但是我場次實在卡太緊，完全沒有調整空間，真的對小編很不好意思(遠目

結果當天因為現場客人很多，雖然花時間排隊了但沒有排到《屍控邊緣》的連刷(建議大家假日要早點去劃位啊！)，打電話跟穿越者確認時，討論到乾脆2組人進同1場，這樣就可以提早很多時間進場，時間上較緩衝，大家因此獲得了短暫的午餐及飲料時間(躲在穿越者樓下的騎樓吃喝XD)，不至於餓到《塭噬》結束才吃，真得是非常感謝穿越者的協助QQ

不過人多進場，出來就是會被團員靠夭人太多……

雖然眼殘排錯是自己的問題，但每次排場都會有嫌太早、嫌太晚、嫌太遠、嫌太少場、嫌太多場、嫌太恐怖、嫌不恐怖、人太多打醬油，人太少不好解等各種聲音，最近真心覺得當主揪很厭世。',1023,'on',15),
('心得-笨蛋工作室《深處》','這次帶場的小天使熱情親切、非常好聊，講解完遊戲後聊到被團員提醒還有下場要趕，我才依依不捨地離開。
我們除了聊一些《深處》的設計之外，也聊到笨蛋台北南京新館、新竹館都要撤場的消息，真是相當遺憾，這2個館的主題我大多非常喜歡，如果有還沒挑戰的玩家，務必要在撤場前去體驗看看！
另一件難過的事，是聽到備受好評、叫好叫座的《浮世百願》，終究有受到疫情影響，收益不如預期……我是因為《革樓》入坑的，早期也是刷笨蛋的密室為主，雖然近期笨蛋減緩了推出新主題的腳步，還是期待能持續穩定經營、翻陳出新、不斷推出給予玩家驚喜的主題與活動。
真心希望疫情能就此平靜下來，不要再受新的變種影響了……',1002,'on',9),
('頭癮創意遊戲《黃道追弒》','雖然當天的遊戲過程不是很順，氣氛有些尷尬，不過回家討論時團員都覺得這是款不錯的遊戲，還滿喜歡黃道追弒的。

一開始是我的問題，我排連刷時，時間抓太緊，前場結束時間跟黃道追弒開始時間很近，前場結束後又跟Roger大大打了一下招呼才走，有遲到一些(不過有讓其他團員先過去，我如果遲到，通常會請團員幫我付錢，讓密室先開場)，小天使人滿好的，說等我到再開。結果我到了，處理完款項之後，我家小天使忽然腸胃不適，窩在廁所一陣子才出來，整體應該延遲20分鐘左右才開場。

考量到前面延遲開場，我們有加快解謎速度，卡關就翻提示筆記，遊戲過程大致順利，但解最後一扇門的鎖時，我跟小天使的溝通一直有點斷層，我們很確定密碼正確，但門就是不開，溝通一陣子後，小天使說可能是我們太小力，要用力一點，然後我很用力的開門，門就壞了，於是我跟站在外面的小天使面面相覷......
再後來溝通一陣子後，才知道小天使認知的門跟我們認知的門不一樣，他一直以為是前一扇門，但前一扇門因為機關故障，一直都是開的，我們根本沒注意到有這扇會影響遊戲流程的門。然後最後一扇門被我用壞了，我們也無從證實密碼真的正確，但感應失靈，還是說有哪個環節出問題這件事，那時候小天使似乎不是很相信我們的說明，我對於用壞門很感愧疚，但又對於機關故障影響遊戲體驗，以及小天使跟我們的溝通斷層感到生氣，就比較兇的在跟小天使溝通，但小天使態度一直滿持平的，現在想起來我有點過兇了，覺得很抱歉。

後來在確認機關、監視器與早前溝通的情況後，才知道監視器角度照不到有問題的地方，他只能從對講機聽我們的情況，而遊戲設計的關係，搞混兩扇門很正常，討論了我用壞的門修理很快，不用賠償之後，兩造都道了歉，便繼續遊戲後解說，結束了這場遊戲。

這次的體驗讓我有種機關也是雙面刃的感覺，都很順的話玩家會很驚喜，但故障的話體驗會大打折扣，反而全鎖頭很安全，又好維護(遠目',1002,'on',26),
('這不是死了一個人的問題','同理心說來簡單，但做來困難，一直在想現代人藉由沉浸式劇場或LARP的角色扮演，除了體驗非日常的情境與滿足演戲心理之外，是否會比看書籍、電影更能對不同人事物多些想法與同理心，這次藉著遊玩這不是死了一個人的問題的機會，除了司法困境之外，還和朋友討論了廢死相關議題，讓我回想起學生時代總會花費很多時間去聊各種事情，並藉此逐步建立起個人或群體價值觀的時光。',1019,'on',26);

--文章圖片----------------------------------------------------------------------------------------------
insert into topicsPhoto(topicId,topicsPhotoFile)
SELECT 3001, BulkColumn FROM OPENROWSET(BULK 'C:\ProjectImages\article\Big1.png', SINGLE_BLOB) AS Image1
UNION ALL SELECT 3001, BulkColumn FROM OPENROWSET(BULK 'C:\ProjectImages\article\Big2.png', SINGLE_BLOB) AS Image2
UNION ALL SELECT 3002, BulkColumn FROM OPENROWSET(BULK 'C:\ProjectImages\article\法老1.png', SINGLE_BLOB) AS Image3
UNION ALL SELECT 3003, BulkColumn FROM OPENROWSET(BULK 'C:\ProjectImages\article\荒村1.png', SINGLE_BLOB) AS Image4
UNION ALL SELECT 3004, BulkColumn FROM OPENROWSET(BULK 'C:\ProjectImages\article\蛋黃哥1.png', SINGLE_BLOB) AS Image5
UNION ALL SELECT 3005, BulkColumn FROM OPENROWSET(BULK 'C:\ProjectImages\article\期末考.png', SINGLE_BLOB) AS Image6
UNION ALL SELECT 3006, BulkColumn FROM OPENROWSET(BULK 'C:\ProjectImages\article\等一個人.png', SINGLE_BLOB) AS Image7
UNION ALL SELECT 3007, BulkColumn FROM OPENROWSET(BULK 'C:\ProjectImages\article\鱗屋之中.png', SINGLE_BLOB) AS Image8
UNION ALL SELECT 3008, BulkColumn FROM OPENROWSET(BULK 'C:\ProjectImages\article\吸血古堡.png', SINGLE_BLOB) AS Image9
UNION ALL SELECT 3009, BulkColumn FROM OPENROWSET(BULK 'C:\ProjectImages\article\咕呱.png', SINGLE_BLOB) AS Image10
UNION ALL SELECT 3010, BulkColumn FROM OPENROWSET(BULK 'C:\ProjectImages\article\逃出補習班.png', SINGLE_BLOB) AS Image11
UNION ALL SELECT 3011, BulkColumn FROM OPENROWSET(BULK 'C:\ProjectImages\article\智慧獵人VICKEY.png', SINGLE_BLOB) AS Image12
UNION ALL SELECT 3012, BulkColumn FROM OPENROWSET(BULK 'C:\ProjectImages\article\泰洛爾號.png', SINGLE_BLOB) AS Image13
UNION ALL SELECT 3013, BulkColumn FROM OPENROWSET(BULK 'C:\ProjectImages\article\控制獄.png', SINGLE_BLOB) AS Image14
UNION ALL SELECT 3014, BulkColumn FROM OPENROWSET(BULK 'C:\ProjectImages\article\塭噬.png', SINGLE_BLOB) AS Image15
UNION ALL SELECT 3015, BulkColumn FROM OPENROWSET(BULK 'C:\ProjectImages\article\夜蒐樓靜.png', SINGLE_BLOB) AS Image16
UNION ALL SELECT 3016, BulkColumn FROM OPENROWSET(BULK 'C:\ProjectImages\article\深處.png', SINGLE_BLOB) AS Image17
UNION ALL SELECT 3017, BulkColumn FROM OPENROWSET(BULK 'C:\ProjectImages\article\黃道.png', SINGLE_BLOB) AS Image18
UNION ALL SELECT 3018, BulkColumn FROM OPENROWSET(BULK 'C:\ProjectImages\article\這不是.png', SINGLE_BLOB) AS Image19
--留言------------------------------------------------------------------------------------------
insert into topicMessage(topicId,content,memno,status,good)
values(3018,'6人團，以戲劇類比像正劇，給大家很多討論空間，但就沒有娛樂性質，所以不是很合自己的偏好，而在市場的部分會有點擠',1003,'on',15),
(3018,'謎題雖簡單,但故事非常的棒,很值得細細回味故事!',1011,'on',16),
(3018,'議題是值得思考的，可是在故事中想給太多，導致沈浸也不足、謎題也不深。覺得就是散了步但跟謎題結合的不是那麼必須。',1009,'on',7),
(3018,'可惜很空虛',1007,'on',8),
(3017,'原本四個人玩 還怕人手不足 但其實四個老手 進度可以非常快',1007,'on',4),
(3017,'遊戲難易度 中間偏易 題目的順序同時可以解開A跟B 再一起解C 讓隊友可以分散解謎 效率更高',1021,'on',9),
(3017,'兇手白天演的很到位，有被嚇到～謎題充足，難度中偏高，適合老手。',1018,'on',21),
(3016,'可愛+血腥的主題（誤）',1012,'on',11),
(3015,'4個人玩差不多，感覺再多一個會很擠了',1015,'on',22),
(3015,'好像有點需要通靈，常常不知道下一步該做什麼',1005,'on',10);
------------------------------------------------------------------------------------------------

--社交(祐陞)
CREATE TABLE groupinfo (
    groupid INT NOT NULL IDENTITY(1001, 1) PRIMARY KEY,
    groupname VARCHAR(255) NOT NULL,
    location VARCHAR(255),
    organizer VARCHAR(255),
    maxi INT,
    registered INT,
    date DATE,
    time TIME,
    fee INT,
    note VARCHAR(255)
);
INSERT INTO groupinfo (groupname, location, organizer, maxi, registered, date, time, fee, note)
VALUES ('Group 1', 'Location 1', 'Organizer 1', 10, 5, '2023-05-01', '12:00:00', 100, 'Note 1');

INSERT INTO groupinfo (groupname, location, organizer, maxi, registered, date, time, fee, note)
VALUES ('Group 2', 'Location 2', 'Organizer 2', 15, 8, '2023-05-02', '14:30:00', 150, 'Note 2');

INSERT INTO groupinfo (groupname, location, organizer, maxi, registered, date, time, fee, note)
VALUES ('Group 3', 'Location 3', 'Organizer 3', 20, 12, '2023-05-03', '16:45:00', 200, 'Note 3');



--遊戲(悅綾)
CREATE TABLE GAMEDATA (
    gameid INT IDENTITY(1001,1) PRIMARY KEY,
    studio VARCHAR(50),
    gname VARCHAR(50),
	[level] VARCHAR(20),
    player VARCHAR(50),
	gamestatus　VARCHAR(20),
    [time] VARCHAR(50),
    [address] VARCHAR(100),
	phone VARCHAR(50),
    priceper VARCHAR(500),
	textdesc VARCHAR(Max),
	gameimg VARCHAR(300)
);





-- 台北-笨蛋工作室
INSERT INTO GAMEDATA (studio, gname, [level], player, gamestatus, [time], [address], phone, priceper, textdesc, gameimg)
VALUES 
('笨蛋工作室密室逃脫','奪命記憶','中度玩家','4-6人','可預約','100分鐘','台北市松山區南京東路五段399號九樓','02-8787-4387','700','"上次的奪命鎖鏈，你斷開了嗎？來試試看笨蛋工作室的全新力作 – 奪命記憶吧！Deja Vu，似曾相識的感覺，好像曾經發生過，但又好像只是錯覺。但這些似曾相識的既視感真的只是錯覺嗎？還是是被遺忘的過去呢？世界頂級的生技公司 Particle S，其生化部門一夕間消失無蹤，正當外界臆測著各種可能性時，一封神秘的信件邀請你來到這間酒吧，而這封信的寄件人，竟然就是你自己？！被隱藏的真實，等你來挖掘！
笨蛋工作室 2014 年度鉅作『DejaVu 奪命記憶』正式重啟！ 奪命記憶開幕 9 個月已超過 5000 人前來挑戰！ 震撼場景！神秘機關！ 彷彿置身在電影情節的體驗，絕對不能錯過！"','img/game/id-1001.jpg'),
('笨蛋工作室密室逃脫','顛倒之室','新手入門','4-6人','已謝幕','100分鐘','台北市松山區南京東路五段399號九樓','02-8787-4387','650','"風靡台灣的實境遊戲，給了人們一種新的體驗活動形式，更造就了許多職業玩家！下班的夜晚或是不想待在家的周末，揪三五好友一同前往未知的世界，來趟神奇的解謎之旅吧！醉心於研發設計不同巧思機關的笨蛋工作室，又再次「顛」覆了人們對實境遊戲的想像，跳脫大型機關，重新探索解謎遊戲精髓，打造低調奢華日式顛倒場景！將場景、解謎、說故事完美結合，以充滿創意的經典口味喚醒您對實境遊戲的熱情初衷！忘卻現實之中的種種限制，在這空間裡，沒有什麼事是不可能的！
顛倒の室 故事情節
相傳二次世界大戰後，廣島市郊有一間戰後仍倖存的詭異房子。這個長年無人居住的小木屋，每年到了 1/4 午夜 00:00 便會傳來微微的音樂聲，且持續一個小時之久，甚至謠傳有人發現房子出現上下顛倒的異象。『顛倒的房子』謎團幾十年來從沒有人解開過，隨著時間的推移，漸漸地成為了都市人們口耳相傳的傳說之謎。
委託人大河：
『幾天前，高齡九十歲的奶奶交給我一把鑰匙，交代我在今天午夜到她廣島的老家來，幫她尋找遺失多年的重要物品，我探聽了一下發現奶奶的老家居然就是廣島都市傳說中的神祕鬼屋，我認為我一個人的力量沒辦法達成目標，希望各位探險隊員能夠協助我解開傳說中的謎團，完成奶奶的心願。』
任務目標：破解都市傳說
探索時間為 70 分鐘，請在時間內盡快破解都市傳說的所有細節，並完成委託人交代的任務！探險隊若已經完成所有任務，可以隨時離開此空屋，屆時將請探險隊員解答關於都市傳說的問題，最後會根據都市傳說破解程度、花費時間、委託人任務是否達成來評比探險隊的成績。"
','img/game/id-1002.jpg'),
('笨蛋工作室密室逃脫','醉後任務','重度解謎','8人以上','可預約','100分鐘','台北市松山區寶清街31號1樓','02-8787-4387','950','"每個對密室感興趣的玩家只要一踏進笨蛋工作室，幾乎都一試成主顧，變成了笨蛋鐵粉！笨蛋工作室每一款遊戲都有不同的特色與定位，這次要帶給大家的，是我們初次嘗試讓玩家扮演 10 個不同角色，並且每個人都有個人任務要偷偷完成的遊戲模式！
角色間的互動與利害關係設計起來非常具有挑戰性，希望藉此讓每一個來玩的人都能有成就感與參與感！ 這款遊戲設計亮點並不是科技機關的展現，但也絕非小品之作，透過個人任務的設計，讓朋友間會在遊戲結束後愉快的討論分享！配合酒精/非酒精飲料的放送，希望能帶給大家一段輕鬆愉快的難忘時光。
THE SCHEME 醉後任務 故事背景
在一個充滿歡笑與希望的城市—巴利市，
看似平靜的夜晚卻暗潮洶湧，幾位素昧平生的人陸續造訪 Deja Vu 酒吧，
一杯充滿惡意的調酒將每個人的意識抽離……
隔天早晨，醒在酒吧樓上的飯店房間裡，
周圍是一群生面孔以及一顆即將摧毀小鎮的炸彈，
昨晚酒酣耳熱之際，被遺忘的派對時光裡究竟發生了什麼事情？
這些人是敵是友？是否能信任？
所有人各懷鬼胎，我得一邊回想自己的目的，一邊拯救小鎮倖免於難。"
','img/game/id-1003.jpg'),
('笨蛋工作室密室逃脫','深處','中度玩家','4-6人','可預約','100分鐘','台北市大安區安和路一段112巷27號B1','02-8787-4387','800','"『場景很豐富，有種暗黑童話的風格！』
『很多細節之處都能感受到設計者滿滿的用心！』
『玩完才知道，原來零文字全機關的設計是有原因的！』
在遊玩之前你要知道
我們是一群孤兒，完全不記得自己親生父母的樣子，或許我們就是沒人要的孩子吧，好險有「新家」接納了我們，孤單的孩子們在這裡得以溫飽，一起玩樂一起長大，這裡，有我們的「爸爸媽媽」。
每隔一段時間，特別懂事的哥哥姊姊們會被選去「樂園」領取獎勵，聽說，那裡有玩不完的遊戲、吃不完的點心，真是羨慕他們！今天不應該是樂園開放的日子，但在深夜「媽媽」點亮了樂園的七彩燈光，好開心啊！樂園的深處到底有什麼在等我們呢！"
','img/game/id-1004.jpg');



-- 台北-放樂工作室		、
INSERT INTO GAMEDATA (studio, gname, [level], player, gamestatus, [time], [address], phone, priceper, textdesc, gameimg)
VALUES 
('FunLock 放樂工作室','永生劫','新手入門','1-4人','可預約','60分鐘','台北市中山區中山北路二段112號7樓之4','0907-101-822','350','"中式宅邸庭院x煉丹秘術x挑戰身手不凡
當今皇上昏庸無能、篤信道術
世間荒淫無道、民不聊生
御醫已煉成長生不老仙丹
五毒教派獻上一石二鳥之計……
你必須潛入秘密宅邸
​劫走丹藥，拯救眾生！！"
','img/game/id-1005.jpg'),
('FunLock 放樂工作室','噬夢','新手入門','1-4人','可預約','60分鐘','台北市中山區中山北路二段112號7樓之4','0907-101-822','600','"傳說中夜魔會吞噬美夢
讓人們沉睡不醒
如今你已經陷入惡夢的輪迴
不理解黑暗的人才會害怕夜晚
面對恐懼才能打破迴圈
沉浸夢中的你能否逃離？"
','img/game/id-1006.jpg'),
('FunLock 放樂工作室','幻境奇航II：最終的航道','中度玩家','6-8人','準備中','90分鐘','台北市中山區中山北路二段112號7樓之4','0907-101-822','700','"NPC角色機關x重金打造多變場景x奇幻空間轉換
傳説中的寶藏藏匿在島嶼之間，冒險者們終於抵達這座小島，
面對謎樣的小鎮，該如何從居民口中得到寶藏的消息？
海盜啊，無畏葬身海底的風險，
出航吧！"
','img/game/id-1007.jpg'),
('FunLock 放樂工作室','鎮魂曲：迴憶宅邸','中度玩家','4-6人','可預約','100分鐘','台北市中山區中山北路二段112號7樓之4','0907-101-822','650','"微恐氛圍Ｘ驅魔行動
精美歐風古宅
黑夜中樹影幢幢的古老宅邸
細聲竊語在耳邊低吟
似乎述說著老屋的過往
陰影中詭異黑影祟動
悲憤與痛苦縈繞在所有暗處
驅魔協會緊急呼叫，急需前來救援！
你們能解開真相嗎？
※場景燈光昏暗。未滿15歲以下孩童、幽閉恐懼症、孕婦及行動不便者不宜參加"
','img/game/id-1008.jpg');


-- 台北-智慧獵人
INSERT INTO GAMEDATA (studio, gname, [level], player, gamestatus, [time], [address], phone, priceper, textdesc, gameimg)
VALUES 
('智慧獵人','洛克飯店：殺手困境','新手入門','1-4人','可預約','100分鐘','台北市中山區明水路581巷15號B1','02-2533-7817','500','"｜只想好好活下來，卻必須拿其他生命來換？｜
受邀到洛克鎮參加音樂嘉年華的你們，
因為飯店的烏龍事件，被帶到一位殺手的房間，
而殺手，就在門外！
飯店手機裡，一通來自未來的電話，
『你們將在60分鐘後遭到門外殺手的攻擊並喪命，
幸運的是，因為不明原因的時空混亂，你們將有機會改變自己的未來』
眼看時間一分一秒過去，你們的生命維繫在一個名叫Tony的小男孩身上？
–
● 多種遊戲結局，超過十種遊戲成就挑戰！
● 極度沉浸式任務遊戲，與NPC互動！這是場由你所主演的實境電影！
● 喜愛小說、劇情推理者推薦！遊戲中的每一個決定，都會改變未來
● 2 – 4 人即可進行遊戲，輕鬆成團"
','img/game/id-1009.jpg'),
('智慧獵人','紅衣小女孩：噩夢再見','新手入門','1-4人','可預約','90分鐘','台北市中山區明水路581巷15號B1','02-2533-7817','350','"｜才以為擺脫了那段夢魘，卻發現其實從沒醒來過…..｜
在孤兒院成長的你和阿旺，有著風雨同舟般的情誼。
2014年的一場山難，使你遺忘了大部分的記憶。
時隔多年，一場登山聚會，阿旺的女友—小惠卻離奇失蹤，
你們進入了一間老舊荒廢的旅社尋找，卻是誤入了魔神的禁地。
身穿紅衣的女孩穿梭其中、嘻笑迴盪，
是想傳達什麼訊息，或是企圖使你再次懷疑自我？
「你，都忘了嗎….？」
● 特色真實鄉野傳說改編，令人發毛的集體記憶
● 場景極度逼真，打造真實詭異旅社
● 5.1環繞音效，全機關體驗，紅衣小女孩如影隨形…"
','img/game/id-1010.jpg'),
('智慧獵人','異界籠','重度解謎','6-8人','可預約','150分鐘','台北市中山區明水路581巷15號B1','02-2533-7817','700','"｜ 人工智慧大屠殺！時間壓迫感重、最刺激驚險的密室逃脫｜
史上首座人工智慧電腦 Zone ，負責管理被放逐至火星上的監獄，但 Zone 卻進行了一場罪犯與管理人員的大屠殺…
「必須快一點找到破壞 Zone 的方法…」
–
● 科幻場景
● 雙結局脫出
● 原創謎題設計
● 獨創大型電動機關"
','img/game/id-1011.jpg'),
('智慧獵人','洛克飯店：維多莉亞的祕密','新手入門','1-4人','準備中','100分鐘','台北市中山區明水路581巷15號B1','02-2533-7817','600','"｜有些祕密，最好不要揭發…｜
紅極一時的女星-維多莉亞，在演藝事業高峰期，
閃電息影並嫁給鬼才導演-李島衍。
原本令人稱羨的影壇佳偶，卻在婚後變了調….
「2/15中午，我會臨時約她在洛克飯店的餐廳吃飯，
請你們趁這段時間，去調查我老婆入住的房間，看看她是不是真的背叛了我。」
–
● 多香豔刺激的徵信委託，一窺女明星的祕密！
● 極度沉浸式任務遊戲，與NPC互動！這是場由你所主演的實境電影！
● 18禁特殊情境遊戲，千萬記得找熟識的朋友參加！"
','img/game/id-1012.jpg');


-- 台北-邏思起子
INSERT INTO GAMEDATA (studio, gname, [level], player, gamestatus, [time], [address], phone, priceper, textdesc, gameimg)
VALUES
('邏思起子實境遊戲工作室','獨眼傑克','新手入門','4-6人','準備中','90分鐘','台北市信義區松德路12-2號','0965-305-690','500','"18 世紀，極其光榮與黑暗的時代。
海上歷史寫下了新頁，同時締造許多傳奇，
獨眼傑克，便是那其中之一。
我們將化身海盜，依循傑克留下的線索，
秘密潛入東印度公司船隻，
劫取商船，搶奪船上所有的財寶💰💰"
','img/game/id-1013.jpg'),
('邏思起子實境遊戲工作室','迷霧島','中度玩家','8人以上','已謝幕','110分鐘','台北市信義區松德路12-2號','0965-305-690','950','"三條獨立故事線❌古老遺跡場景❌空間大型機關
你曾聽過那迪亞嗎？
遙遠的古文明存在著三大種族，只有走進迷霧島，你才能揭開他們最核心的秘密✨
🏺萬物神殿 – 平衡宇宙秩序，躲避空間機關陷阱
🏺元素之門 – 古老礦場冒險，操控元素穿透維度
🏺亞人試煉 – 團結創造生機，手腳並用進化人類
各種族均有專屬領地，以不同種族身分多次登陸，解鎖改變最終結局！
人數：每條路線4-6人 最多同時啟動三條路線"
','img/game/id-1014.jpg'),
('邏思起子實境遊戲工作室','辛亥隧道','中度玩家','4-6人','可預約','100分鐘','台北市信義區松德路12-2號','0965-305-690','350','"逼真場景 ♦ 滿分機關 ♦ 感人劇情
全台唯一，實車機關
荒郊野嶺，拾階而上
扣人心弦，驚心挑戰
六大賣點📣
1.全新沈浸體驗設計，玩家推動劇情發展
2.獨一無二大型機關
3.專業配音 聲臨其境
4.高標準美術團隊打造，超擬真場景
5.絕美動畫視覺特效
6.人互動，挑戰你的腎上腺"
','img/game/id-1015.jpg');


-- 台北-玩笑實驗室
INSERT INTO GAMEDATA (studio, gname, [level], player, gamestatus, [time], [address], phone, priceper, textdesc, gameimg)
VALUES
('玩笑實驗室','迴異路','中度玩家','1-4人','已謝幕','110分鐘','台北市內湖區內湖路一段136號1樓','02-2659-8383','700','"２０２２五月全新翻修！所有謎題道具、場景布置皆為全新，老玩家新玩家都歡迎！
「憶空門關閉，記憶連結中斷。」
博士使用最新儀器進入記憶探索，卻因無法在時間內回來而昏迷。博士出發前留下字條「如果我沒回來，表示實驗失敗，別來救我，可能很危險。」儘管如此，研究人員仍前往調查，卻也發現博士不為人知的秘密…"
','img/game/id-1016.jpg'),
('玩笑實驗室','紅花','重度解謎','4-6人','可預約','110分鐘','台北市內湖區內湖路一段136號1樓','02-2659-8383','650','"「只有你能救你自己，只有我能救我自己，我是誰？為什麼我會出現在這裡…」
這個聲音…是誰在說話？躺在我眼前的這個人是…是我…
你怎麼會…不對，是我怎麼會在這裡？我不記得所有事情了…我到底是誰…
直逼200萬高規格製作！高臨場體驗！
10000字劇本撰寫
超過20多道豐富謎題
腦洞大開全新玩法
4位多年專業配音員錄製全程體驗
221項自動化程序控制音效聲光
燈光導演設計與劇情情緒相符燈光
5大美術場景、擬真虛幻一秒入戲
2首背景音樂製作，高潮迭起臨場十足
5組影片製作導入，視覺衝擊
—
本活動因為劇情關係，18歲以下禁止進入。另外，孕婦、行動不便者，恕無法入場。"
','img/game/id-1017.jpg');


-- 台北-頂級豬排
INSERT INTO GAMEDATA (studio, gname, [level], player, gamestatus, [time], [address], phone, priceper, textdesc, gameimg)
VALUES
('頂級豬排遊戲工作室','時鐘之國','新手入門','1-4人','可預約','40分鐘','台北市萬華區康定路64號2樓/4樓/9樓','0905-259-817','550','"匡噹一聲巨響後，時鐘之國的時間陷入了混亂的迴圈之中，首席鐘錶師Eric不知去向，掌控時間的核心巨鐘深鎖緊閉，無人能開啟。
為解決危難，受委託的你須深入蒸氣龐克的空間內，抽絲剝繭揭開時序之謎。"
','img/game/id-1018.jpg'),
('頂級豬排遊戲工作室','月夜獨嚎','重度解謎','8人以上','準備中','120分鐘','台北市萬華區康定路64號2樓/4樓/9樓','0905-259-817','600','"寧靜僻世的犬神村究每逢月圓之夜，便會遭到不明異獸襲擊。村民高額懸賞獵人，希望能永除後患。言談中你總覺得委託著神祕並語帶保留，似乎還有其他秘密。
每逢月圓便出現的巨獸，欲言又止的村民，遍布四周的鋸齒葉植物月光下散發惡意…
—
此遊戲有雙版本：
歡樂成就 – 一般版（建議人數 6~10 人 ）
身經百戰 – 困難版（建議6人以上愛好解謎的玩家）
版本差異為謎題難度與數量，故事、空間、機關皆相同。
','img/game/id-1019.jpg'),
('頂級豬排遊戲工作室','鬼靈精怪店','新手入門','4-6人','可預約','40分鐘','台北市萬華區康定路64號2樓/4樓/9樓','0905-259-817','650','"死神見習生的年度結算迫在眉睫，平時不努力的小小鬼差們臨時抱鬼腳，想找到傳說中的鬼靈精怪店。
店內商品包羅萬象，只要能找到門路肯定有救。鬼界中的小小奇幻生物會幫你一把嗎？"
','img/game/id-1020.jpg'),
('頂級豬排遊戲工作室','鱗屋之中·惡夢版','重度解謎','6-8人','已謝幕','100分鐘','台北市萬華區康定路64號2樓/4樓/9樓','0905-259-817','700','"崖邊那棟樓房，磚瓦整齊鱗列，
偶爾閃耀波光水紋，
當地人稱這奇異樓房為──鱗屋。
朝鱗屋望去，
似乎有種黑影散發出嘲弄訪客的惡意，
深入探索，
能否在惡夢詛咒中抽絲剝繭找到希望呢?"
','img/game/id-1021.jpg');


-- 台北-覓見
INSERT INTO GAMEDATA (studio, gname, [level], player, gamestatus, [time], [address], phone, priceper, textdesc, gameimg)
VALUES
('覓見實境遊戲','秦關','中度玩家','8人以上','準備中','120分鐘','台北市萬華區長沙街二段50號','0905-517-672','500','"
「繼承人之戰」，是團體遊戲，需要16至20人，將分成4組競賽，故事為秦關架構之延伸，場地用上秦關前半段場地，並再新增額外房間，遊戲內容與秦關完全不同，但因使用秦關場地，若尚未玩過秦關，會預先見到部分場地 (但不會知道在秦關時的功用)。"
','img/game/id-1022.jpg'),
('覓見實境遊戲','總督諜影','重度解謎','6-8人','可預約','240分鐘','台北市萬華區長沙街二段96號3樓之1 ','0905-517-672','500','"1945年，時值二戰時期，東亞戰線戰況慘烈，
日本帝國跟進原子武器的發展競逐，
下令臺灣總督府，秘密打造「原子力發展情報所」，
專門負責收集、破譯各國原子力發展情報，以利政府製造出原子彈。
然而，中美盟軍步步逼近，接二連三對臺灣展開空襲，
為了避免情資被炸毀，須盡早將情報帶回日本，
但情報所長官卻忽然遭人暗殺，機密資料的位置，
已無從交接…
「聽我說，你現在是我們最接近關鍵情報的人，只能靠你了。」
那聲音頓了一下，「你必須找出那把改變世界的鑰匙。」
遊戲特色
睽違六年，覔見實境遊戲推出全新的誠意之作《總督諜影》，
從進場製作開始至今已逾兩年，終於逐步成型🙏🥲。
這是一款跟諜報有關的主題，我們嘗試揉合：
實境、解謎、機關、諜戰劇本及沉浸演出的作品，
期待帶給您獨一無二的全新體驗🔥
#匠心打造之日治時代遊戲場景
#精密設計之百種動態機密機關
#誠意製作各式道具及專門情境音樂音效
#縝密編織遊戲設定及分角劇本
#精心安排，配合專業演員的實境演出
#充裕尺寸之全角色專屬服裝"
','img/game/id-1023.jpg');


-- 台北-Miss GAME
INSERT INTO GAMEDATA (studio, gname, [level], player, gamestatus, [time], [address], phone, priceper, textdesc, gameimg)
VALUES
('Miss GAME','流亡黯道','中度玩家','6-8人','已謝幕','100分鐘','台北市中山區民權東路二段62號2樓','0986-617-820','700','"傳說300年前，永恆帝國首席奇術師馬拉凱發明將古靈寶石植入人體、賦予使用者各種強大能力的方法，而寶石皇后則是他的最高傑作。
根據記載，寶石皇后身上有一顆永生之石，賦予她不朽的能力，但在一場異變之後，永恆帝國就此覆滅，帝國所在的瓦爾克拉斯大陸成為鬼島，被作為犯人的流放地，同時再也沒人看過寶石皇后……
神秘的奇術師派蒂相信永生之石仍然存在，她提出了條件，只要流亡者能夠進入日耀神殿的遺跡，帶回永生之石，她就幫助流亡者逃離鬼島……"
','img/game/id-1024.jpg'),
('Miss GAME','奇術師','中度玩家','6-8人','可預約','100分鐘','台北市中山區民權東路二段62號2樓','0986-617-820','800','"流亡者們為了逃離鬼島，接下奇術師派蒂的委託，潛入日耀神殿遺跡尋找永生寶石。
隨著成功找到永生寶石，流傳三百年的永生之謎也被揭開．瞭解真相的流亡者們意識到必須趕緊離開，否則將會永遠被困在這裡。
但不慎觸發的古代封印，讓整座神殿的結構完全改變，當初進來的路已消失無蹤。流亡者們能在時間內找到另一條生路，逃出神殿嗎？"
','img/game/id-1025.jpg'),
('Miss GAME','即刻越獄','新手入門','1-4人','可預約','30分鐘','台北市萬華區漢中街24號','0986-617-820','700','"一九二三年，民族自決的思潮正影響著尚在日本統治下的台灣
我們是與蔣渭水一同進行「臺灣議會設置請願運動」的成員
準備趁日本裕仁皇太子來台巡視時舉旗請願
但日本警察發現了我們的意圖
因此，對我們做了預防性羈押，關入了拘留所
幸好，日本警察中有支持我們理念的人
暗中留下了一些逃出的線索
現在，必須在二十分鐘內逃出拘留所
才來得及向皇太子請願！"
','img/game/id-1026.jpg');



-- 新北-LoGin
INSERT INTO GAMEDATA (studio, gname, [level], player, gamestatus, [time], [address], phone, priceper, textdesc, gameimg)
VALUES
('LoGin密室逃脫','等一個人•盜墓','中度玩家','6-8人','可預約','110分鐘','新北市中和區連城路52號4樓','0963-606-082','1000','"西域沙漠中
突然出現了一座神秘的古墓
陵墓的主人正是消失已久的樓蘭女王
政府派出一支考古隊前往調查
但他們卻離奇的失去了聯繫
於是政府秘密派遣盜墓賊再次前往
究竟，會在裡面發現什麼……？"
','img/game/id-1027.jpg'),
('LoGin密室逃脫','這個Case有點Big','新手入門','4-6人','準備中','110分鐘','新北市中和區連城路52號4樓','0963-606-082','700','"『一場發生在行李箱中的奇幻冒險，世界上怎麼會有那麼大的行李箱啊？
再不逃出去，就要被永遠困在這裡了！』
LoGin偵探社最近收到了一封奇怪的委託信，
一位著急的媽媽聲稱她失蹤的10歲兒子被邪惡的科學家綁架了。(這年代還有邪惡的科學家嗎…)
但當偵探社聯繫她時，卻又堅決否認寄出過這封信，這個案子也就不了了之…
但這位媽媽突然又聯繫了偵探社表示她已潛入科學家的研究所，
希望我們能派出偵探協助她，但一時之間實在抽不出人手…
你看起來挺聰明的，能幫個忙嗎？"
','img/game/id-1028.jpg'),
('LoGin密室逃脫','利維德酒吧','重度解謎','1-4人','已謝幕','180分鐘','新北市中和區景平路730號1樓','0963-606-082','550','"傳說有一間酒吧，喝了這裡的酒，便能獲得一段意想不到的愛情
它沒有固定地址、沒有營業時間、連酒……也沒有價格
只需用你一個最難忘的故事，就能換一杯「L’amour」……
－
LoGin偵探社再次接到了一件奇特的委託，被診斷出失憶症、離不開醫院的委託人希望我們能替她奪回她的記憶。
今日，身為偵探社菜鳥的你們來到了與前輩約定的地點，只見一家古老的酒吧。
難不成，委託人的「故事」就在這家酒吧裡……？"
','img/game/id-1029.jpg');


-- 新北-穿越者
INSERT INTO GAMEDATA (studio, gname, [level], player, gamestatus, [time], [address], phone, priceper, textdesc, gameimg)
VALUES
('Througher 穿越者','鎮龍脈','新手入門','6-8人','可預約','75分鐘','新北市板橋區館前東路8號B1','02-2956-6636','950','"西元1371年出現了時空裂縫，依附靈氣而長存，異世界生命帶著戰爭而來，國家動盪，人心惶惶。
西元1375年，異族即將攻陷明朝帝都。
劉伯溫奉皇帝密詔封鎮龍脈，斷絕靈氣，但被異族發現，在生命即將消逝時，劉伯溫利用肉體精元擺下時空法陣，呼喚著你我的救贖………"
','img/game/id-1030.jpg'),
('Througher 穿越者','連鎖效應','中度玩家','4-6人','可預約','75分鐘','新北市板橋區館前東路8號B1','02-2956-6636','500','"千年難得一遇的九星連珠，一群星象愛好者，闖入了一處廢墟，竟然遇見了時空裂縫…. 
穿越到了10年前廢墟還完好時，當年究竟發生了甚麼?為什麼會變成廢墟?…"
','img/game/id-1031.jpg');


-- 新北-謎鹿
INSERT INTO GAMEDATA (studio, gname, [level], player, gamestatus, [time], [address], phone, priceper, textdesc, gameimg)
VALUES
('謎鹿實境遊戲工作室','獄言','中度玩家','1-4人','已謝幕','90分鐘','新北市永和區福和路297號B1','0908-818-100','700','"『獄 言』(千辛萬苦嘔心瀝血的誠意之作)
『來自好友的邀約，昏迷後甦醒，卻發現置身於中國古代監獄，究竟發生了甚麼事？穿越數百年的謎團，你能解開嗎？』"
','img/game/id-1032.jpg');


-- 桃園-A5
INSERT INTO GAMEDATA (studio, gname, [level], player, gamestatus, [time], [address], phone, priceper, textdesc, gameimg)
VALUES
('A5 Studio-全員脫逃中','奎蕾精神病院','重度解謎','4-6人','可預約','90分鐘','桃園市中壢區中北路2段434號8F','03-466-8702','300','"封鎖島-全球十大恐怖島嶼，這座島嶼草不生長，鳥不歇腳，
島上甚麼都沒有，只有一間私人精神病院。
封鎖島長年處於封鎖狀態，除了病院的救護船與補給船，沒有任何船隻和
漁民敢接近。日復一日，年復一年，看似平靜的島嶼，卻始終死氣沉沉，
直到一樁逃脫計畫的誕生…
島上的奎蕾精神病院於1984年成立，是個極不人道的非法私人醫院，
院長奎蕾夫人擁有極度古怪的變態人格。
她的至理名言就是：瘋子看別人都是個瘋子，瘋子在我眼裡卻是金子。」
她秘密地利用病人進行人體實驗，將尚未成熟的醫療技術試用在病人身上
，一切手段就為換得大把鈔票。此外，夫人認為精神病患都是惡魔附身的
結果，唯有接受特殊的治療他們才能得到救贖。但詭異的是，明明每日都
有新進病患，院裡的人口總數卻始終維持在一個水平。
種種荒誕的行徑與醫療行為，醞釀出一樁神秘的逃脫計畫…"
','img/game/id-1033.jpg'),
('A5 Studio-全員脫逃中','黑鯨號的詛咒','中度玩家','4-6人','可預約','90分鐘','桃園市中壢區中北路2段434號8F','03-466-8702','500','"加勒比海上有一艘名為黑鯨號的幽靈船，很多海盜都無故消失在這艘船上，
而這也讓大片神秘的大海留下了更多謎團。
據老水手的講法，只要能登船，船上無數的珍寶夠你一輩子不愁吃穿，
其中最珍貴的就是傳說中的龍涎石。
那麼，黑鯨號的船長到底是什麼來頭呢?
據說船上的財寶都是靠著洗劫其他船隻得來的，
而船長為了怕自己死後滿船的寶物落入別人手中，
因此以自己的靈魂跟黑鯨做了條件交換，得以永生永世守護著黑鯨號。
這一日，加勒比海上出現一艘大型貨船，船上有幾個正在瞭望台站崗的
水手們，赫然發現眼前出現了一艘極其詭譎的船，眼前的景象讓他們一致
篤定這就是傳說中的黑鯨號，心中想著:如果古老的傳說是真的，那這下
就發財了。於是他們打定主意，下放接駁小船接近黑鯨號去偷取龍涎石。
但他們所不知道的是，老水手所講的故事，其實還沒完全講完……
“而每當有人看到它，就暗示著即將有新的災難即將發生……”"
','img/game/id-1034.jpg'),
('謎失工作室','藝樣的代價','中度玩家','4-6人','已謝幕','110分鐘','桃園市平鎮區金陵路301號','0965-729-934','1200','身為怪盜集團的我們，在一次完成竊取宅邸秘寶的委託後，大夥在酒館飲酒慶功時，收到了一封來自厄萊爾6世的委託信，他告訴我們有一間私人美術館裡，私藏著著名畫家梵谷的耳朵，他希望我們可以將耳朵帶回來給他。
','img/game/id-1035.jpg');

-- 桃園-純密室
INSERT INTO GAMEDATA (studio, gname, [level], player, gamestatus, [time], [address], phone, priceper, textdesc, gameimg)
VALUES
('純密室','奶酪陷阱','新手入門','1-4人','可預約','40分鐘','桃園市中壢區協同街95號1樓','03-416-2115','500','"場景夢幻，情節可愛～
適合新手挑戰 曖昧脫單
我們將邀請各位鼠鼠朋友
一起來參加夢幻
「麵包包吃飽飽」的快樂之旅～"
','img/game/id-1036.jpg'),
('純密室','人格之戰','新手入門','4-6人','準備中','40分鐘','桃園市中壢區協同街95號1樓','03-416-2115','450','"特殊題材，視角被轉換
多元題型 謎題控的最愛
這次你將為了“自我”而戰
誰都想要擁有這具軀體的掌控權
那麼…..向其他人格證明
你才是最有資格的吧！"
','img/game/id-1037.jpg');


-- 新竹-代達洛斯、格林跳
INSERT INTO GAMEDATA (studio, gname, [level], player, gamestatus, [time], [address], phone, priceper, textdesc, gameimg)
VALUES
('代達洛斯工作室','命運計畫','新手入門','4-6人','可預約','90分鐘','新竹市南大路241號3樓','0985-520-885','300','西元2136年，知名富商貝塔集團的揚科維奇，日前被診斷出阿茲海默症，有嚴重的失智症狀，為了救治揚科維奇先生，貝塔集團與擁有記憶修復裝置的希格瑪實驗室，決定立即執行「命運計畫」。
','img/game/id-1038.jpg');

INSERT INTO GAMEDATA (studio, gname, [level], player, gamestatus, [time], [address], phone, priceper, textdesc, gameimg)
VALUES
('格林跳-真實密室遊戲','野獸之心','中度玩家','4-6人','可預約','120分鐘','新竹市東區民權路62號','0984-259-002','950','"從未離開森林的食人野獸，
深夜突然出現在王國之中，
為了拯救命危王后，
獵人們深入野獸的森林中，
​試圖找尋一段深藏已久的真相"
','img/game/id-1039.jpg');


-- 台中-哇沙謎
INSERT INTO GAMEDATA (studio, gname, [level], player, gamestatus, [time], [address], phone, priceper, textdesc, gameimg)
VALUES
('哇沙謎創意工作室','冒險王','重度解謎','4-6人','可預約','150分鐘','台中市北區永興街370號B1-4','0909-680-838','800','傳說在百慕達三角洲一帶有一座神秘的島嶼，歷史至今為了前往島嶼的海盜、冒險家皆無一倖免，因此有著死亡島之稱。據說這個死亡島藏有許多珍貴的寶藏，還蘊含著不可思議的力量，唯有成功奪取寶藏並活著回來才能成為冒險王！
','img/game/id-1040.jpg'),
('哇沙謎創意工作室','秘令','中度玩家','8人以上','可預約','120分鐘','台中市豐原區中山路83號5樓','0909-680-838','300','"江戶時代，接獲秘令的眾忍們，為了達成任務，潛入幕府，通過重重難關，但是任務並非想像中如此單純，究竟夜幕中傳出來的腳步聲，是盟友還是敵人?
#九十分鐘長時間挑戰 讓你一次玩個夠
#體驗超大實境迷宮 小心別落入敵人的陷阱中
#打造精緻無違和感的場景 謎題與劇情相互呼應
#多變的空間刺激你的視覺 沉浸於故事氛圍"
','img/game/id-1041.jpg'),
('哇沙謎創意工作室','熊熊危機','新手入門','4-6人','可預約','100分鐘','台中市豐原區自強街77號','0909-680-838','750','大雪紛飛的平安夜裡，大家團聚在爐邊聊天，此時媽媽從櫃子拿出一本”熊熊危機”的童話書，她說這是她小時候最喜歡聽奶奶講的故事書，每當想到奶奶就會翻開來讀，媽媽興致勃勃述說著裡面的故事，但講到最精采的地方，我們竟然睡著了….
','img/game/id-1042.jpg');


-- 台中-玩有引力
INSERT INTO GAMEDATA (studio, gname, [level], player, gamestatus, [time], [address], phone, priceper, textdesc, gameimg)
VALUES
('玩有引力創意工作室','重返地平線','重度解謎','6-8人','可預約','100分鐘','台中市北區永興街370號B1-5','04-2234-1590','300','"在未來，第三次世界大戰後，由人類開發出的具備情感與自我意識的人工智能機器人，取得了勝利並掌控了自由與權力，人類在這些終極機器面前， 毫無抵抗之力，人口銳減了八成…
剩餘的部分人們依舊懷著希望與機器人不懈奮鬥、抗爭，這些人被視為危險犯罪者，如被抓住將送往離地球4.2光年的Z3行星-凱勒斯太空站執行冷凍冬眠懲罰。
在那裡，據說人們於冷凍睡眠時，都會做同一夢，一個回到地球的美夢…
而這次，10年才有一次的解凍自由時間，輪到了F區的各位…"
','img/game/id-1043.jpg'),
('玩有引力創意工作室','地心任務','中度玩家','4-6人','可預約','100分鐘','台中市北區永興街370號B1-5','04-2234-1590','500','一場突如其來的全球性危機，各式各樣的異常災害不斷發生，面對此次危機，目前唯一可行的方法竟藏於地球上最神秘的大陸-南極洲，你將在冰天雪地的嚴寒中尋找答案，並深入未知的地心執行這件看似不可能的任務…
','img/game/id-1044.jpg'),
('玩有引力創意工作室','庫克王國','新手入門','1-4人','已謝幕','120分鐘','台中市北區永興街370號B1-5','04-2234-1590','600','在某個大陸上，有一個以食為名的國度-庫克王國， 這裡充滿了各式珍奇、獨特的食材， 不僅是美食聖地，也是廚師們的天堂， 王國內最受歡迎的人既不是國王，更不是貴族或騎士，而是廚師! 其中已故的“洋蔥大廚”因為做出了一道獨一無二 的美味料理-彩虹湯，被整個國家視為偶像崇拜。 如今，為了紀念這位傳奇廚師，國王發布了一個公告， 只要有人能重現這道神秘的湯品， 便授予超級廚師之名及鉅額的報酬! 儘管挑戰者絡繹不絕，截至今日，還是無人能夠成功…
','img/game/id-1045.jpg');


-- 台中-BGLOCK
INSERT INTO GAMEDATA (studio, gname, [level], player, gamestatus, [time], [address], phone, priceper, textdesc, gameimg)
VALUES
('BGLOCK推理解謎館','壹零玖伍：禁錮之宅','重度解謎','6-8人','準備中','150分鐘','台中市東區東光園路602號','04-2213-3896','1000','"你們在劉家村清貧的生活著
一日耳聞後山藏有密寶
為求富貴 相約上山尋寶
卻遭逢惡劣氣候
不慎跌落山崖
醒來後只見眼前聳立一座與世隔絕的奇特大宅…"
','img/game/id-1046.jpg'),
('BGLOCK推理解謎館','暴雨','重度解謎','4-6人','可預約','150分鐘','南投縣鹿谷鄉內湖村興產路2之3號','04-2213-3896','700','幾個月前，你的妻子在家中遇害身亡。 警方經過幾個月的調查，卻遲遲沒有進展…
','img/game/id-1047.jpg');


-- 台中-黑盒子
INSERT INTO GAMEDATA (studio, gname, [level], player, gamestatus, [time], [address], phone, priceper, textdesc, gameimg)
VALUES
('黑盒子工作室','同學會','中度玩家','4-6人','可預約','120分鐘','南投縣鹿谷鄉內湖村興產路2之3號','04-2293-8533','900','"風吹得葉子沙沙作響,我坐在樹下，
是午後刺眼的陽光讓我眼角酸澀的吧！
還記得那年埋下的時光膠囊嗎？
多年來,我從不曾忘懷,那段最愉快的時光！

喜歡小說或電影嗎，
你是否常沉浸於劇情中，
心情跟著角色一同激盪著，
這個故事，適合喜歡體驗劇情的你。"
','img/game/id-1048.jpg');


-- 彰化-野馬、南投-妖怪村
INSERT INTO GAMEDATA (studio, gname, [level], player, gamestatus, [time], [address], phone, priceper, textdesc, gameimg)
VALUES
('野馬密室逃脫','祭女','新手入門','1-4人','準備中','100分鐘','彰化縣員林市 萬年路一段352號','0923-695-536','600','"一家知名醫院流傳著詭異的故事⋯⋯
某位病人極盡瘋狂，不僅設置了祭壇還奉獻自己的生命。
事後每到半夜，病房中總會傳出聲響….
啪塔！啪塔！啪塔！
隱約的露出女生的身影，正在尋找某樣物件
「你，有看到我的護身符嗎？」"
','img/game/id-1049.jpg');

-- 嘉義-許多門
INSERT INTO GAMEDATA (studio, gname, [level], player, gamestatus, [time], [address], phone, priceper, textdesc, gameimg)
VALUES
('許多門實境解謎','嗜血病院','重度解謎','1-4人','可預約','60分鐘','嘉義市民族路246號2樓','05-227-7636','450','"本以為只是一個小手術，卻作了全身麻醉！？
一覺醒來，身體是沒有什麼特別不舒服，
但是四周卻瀰漫的一股詭異的氣氛？！
嗶- 嗶- 嗶-…仔細聽才發現，
除了各種儀器發出的聲音之外，竟然沒有任何其他人的聲音。
『不對！ 一定有事情發生！ 』
用盡了力氣站起來，我延著走道到了護理站，
卻沒有任何人，所有的出口也都上鎖了。
這時，心裡面產生了很不好的預感……"
','img/game/id-1050.jpg'),
('許多門實境解謎','俠盜羅賓漢','新手入門','6-8人','準備中','60分鐘','嘉義市民族路246號2樓','05-227-7636','500','"『如果……領主是公平正義的，也不需要羅賓漢的幫助了』
嘆了一口氣，農夫繼續耕種著，
希望能有好的收穫，但是心裡卻明白，
新領主上任後又不知道要用什麼政策欺壓百姓，
他們似乎只能倚靠一個傳說……『俠盜羅賓漢』傳說中的英雄，
雖然大家常常津津樂道於他劫富濟貧的義行，
但更重要的，他總是與所有的受苦的人民站在一起，
用智慧、計謀與高超的射箭技藝解決許多難題！
當他聽到大家的不安，決定召集了夥伴挺身而出，
希望完成一項偷天換日的艱難任務……
而心中希望的是真正能夠盡一份力，守護他所愛的人民……"
','img/game/id-1051.jpg'),
('許多門實境解謎','絕命地鐵','重度解謎','6-8人','已謝幕','60分鐘','嘉義市民族路246號2樓','05-227-7636','750','"你們是一組負責對付恐怖份子的特警，
平常總是靠著大家的團隊合作解決許多案件！
而就在剛才，你們接到了一個緊急任務：
「有一臺電車被歹徒狹持，
恐怖份子想要讓這部電車撞毀在終點，
現在這個車站是你們上車解除事件的唯一機會。 」
不過不用擔心，
我們的臥底同仁已經在離開電車之前，
留下了許多可以追蹤恐怖份子的線索，
加上我們精良的犯罪檢索系統，
你們一定可以順利把這部電車停下來！
希望你們能夠順利達成任務！！"
','img/game/id-1052.jpg')


-- 台南-神不在場
INSERT INTO GAMEDATA (studio, gname, [level], player, gamestatus, [time], [address], phone, priceper, textdesc, gameimg)
VALUES
('神不在場實境遊戲','X伯爵的委託','新手入門','4-6人','可預約','120分鐘','台南市永康區中華路1-2號3樓','06-312-0792','350','"“來抓我啊！如果你辦得到？”
古堡附近常有年輕貌美的女性失蹤，
而伯爵的古堡裡竟然有兇手留下的挑釁字樣！
最後連伯爵夫人也不見蹤影…
警方前來調查卻一無所獲，
於是伯爵委託神不在場的偵探到古堡調查。
不料來到古堡的偵探，連伯爵都還未見上一面，
就莫名被迷昏關進地牢，
昏迷前聽見：「一個小時後再來收拾你們…」
各位偵探，當你們醒來後的第一要務，
是找到逃出古堡的出口，一定要在兇手回來之前逃出，
才有機會繼續為伯爵調查真相。"
','img/game/id-1053.jpg'),
('神不在場實境遊戲','莎士比亞的邀請','新手入門','1-4人','可預約','90分鐘','台南市永康區中華路1-2號3樓','06-312-0792','600','"“生活裡沒有書籍，就好像沒有陽光”－莎士比亞
神不在場發現一本
突如其來、年代久遠的書，
似乎有了自己的生命，
每60分鐘才會開啟一次。
僅知道它一直在邀請不同的人進入，
期待有人能改變什麼…
這本書我們所知甚少，進去過的人，
對它的說法眾說紛紜，
你願意冒險進到魔書裡，
接受它的邀請嗎？"
','img/game/id-1054.jpg'),
('神不在場實境遊戲','失落的隕石神殿','重度解謎','8人以上','準備中','120分鐘','台南市永康區中華路1-2號3樓','06-312-0792','750','"『人類啊！探索未知吧！你們將得到一切！』
最近在撒哈哈沙漠，發生隕石撞擊事件，
隕石坑洞中竟出現一個古埃及的神殿入口。
此消息一出，立刻引起各界關注！
神不在場也派出探險隊前往調查，就在看似順利的調查中，
神殿突然爆發了某種能量，探險隊員同時也失去聯繫！
之後詭異的能量，每隔１小時就會爆發一次，
凡觸碰者無不被吸乾血肉。
身為神不在場第二批探險隊的各位，
請前往神殿找出失蹤隊員們的下落，
並調查出詭異的能量來源吧！"
','img/game/id-1055.jpg'),
('神不在場實境遊戲','浮士德家的後院','中度玩家','4-6人','可預約','120分鐘','台南市永康區中華路1-2號3樓','06-312-0792','300','"各大報近日不約而同
出現一則神秘的徵人啟示，
內容是一名男子的未婚妻在不明原因下
成了沒有靈魂的軀殼，
不斷地喃喃自語著無人能理解的話語；
男子發出重賞徵求能喚醒未婚妻的人，
各界紛紛組成研究團隊
但至今仍卻無人能成功。
來到這裡的你們是最後的希望，
調查靈魂消失的原因，
找尋喚醒女子的方法吧！"
','img/game/id-1056.jpg');


-- 台南-計中計
INSERT INTO GAMEDATA (studio, gname, [level], player, gamestatus, [time], [address], phone, priceper, textdesc, gameimg)
VALUES
('計中計密室逃脫','跳轉空間－次元世界之謎','重度解謎','4-6人','已謝幕','100分鐘','台南市北區育成路108號2樓','0958-456-123','750','"天才博士「馬維爾」在某次旅行中意外撿到一個神祕的次元容器
然而這個容器需要同時解開世界三大謎團才能打開
傳聞裡面藏有能與國家匹敵的世界寶藏
然而天才博士用盡各種辦法都不能強行破解這個箱子
剛好博士研發的任意門正巧到了最後一步，於是他打算前往去破解世界三大謎團。"
','img/game/id-1057.jpg'),
('計中計密室逃脫','遺產爭奪戰','中度玩家','6-8人','可預約','100分鐘','台南市北區育成路108號2樓','0958-456-123','450','"劉家在當地是非常有權勢地位的名門望族
家族企業與許多政商互相來往，家族的資產相當龐大
但父親身體狀況一直不好，長年臥病在床
這天老爸病情突然急轉直下，命在旦夕，被轉進急救病房
身為膝下兒女的你們，被通知趕來醫院
表面上一片孝心的子女，背地裡卻進行著一場相互廝殺的遺產爭奪
究竟劉家的遺產最終會落入誰的手中呢?"
','img/game/id-1058.jpg');

-- 高雄-丹尼思、1798
INSERT INTO GAMEDATA (studio, gname, [level], player, gamestatus, [time], [address], phone, priceper, textdesc, gameimg)
VALUES
('丹尼思實境密室逃脫','聖誕老人的秘密基地','新手入門','1-4人','可預約','60分鐘','高雄市鳳山區鎮西里中山西路19號','07-719-3144','550','"聖誕節到了，孩子們都在等待著禮物的到來。在這麼重要的日子裡，聖誕老人居然消失了!!
到底發生了什麼事了? 趕快到聖誕老人的秘密基地一探究竟吧!
馬上就要發禮物到世界各地了，聖誕老人卻不在 ! 聽說他家中有條密道可以通往他的秘密基地 ! 你們能找到入口並替他完成使命嗎 ?"
','img/game/id-1059.jpg'),
('1798真人實境遊戲','同學會','重度解謎','4-6人','準備中','90分鐘','高雄市鹽埕區河西路103號B1F','07-532-1798','950','"青春 是陪你渡過一生中最胸無大志 無慾無求的時光
在這時光裡
不免俗的會出現有你最想愛的、最想揍的
也有一些無關痛癢的
他們和你組成了喧鬧，如夢般的場景
明媚、陽光、生機勃勃、充滿希望
有天 你收到了許久不見的同學會邀請函
美好的回憶驟然出現
但 在到了卡片上的地址後發現
事情好像並非如此簡單
眼前的視線逐漸模糊
再次醒來後已被綑綁並且無法動彈⋯"
','img/game/id-1060.jpg'),
('1798真人實境遊戲','講義','中度玩家','6-8人','可預約','90分鐘','宜蘭縣宜蘭市康樂路61號','07-532-1798','450','社會、是由多元不凡的個體組成的多彩世界，但在世界的角落，坐落於黑暗中的影子，也正在積極的用他們的方式生存。午夜時分，於光和酒店的四聲槍響，震驚了該地區的警界高層，同時也讓政府重新燃起打擊犯罪的決心。 各位刑事警察大隊的警員們，根據情報，此處有許多組織犯罪及非法交易，你們必須重回案發現場調查真相，協助我們蒐集證據，並將該組織繩之以法。
','img/game/id-1061.jpg');



-- 宜蘭-梅林
INSERT INTO GAMEDATA (studio, gname, [level], player, gamestatus, [time], [address], phone, priceper, textdesc, gameimg)
VALUES
('夢遊王國','偶像出道','中度玩家','1-4人','已謝幕','100分鐘','高雄市鹽埕區河西路103號B1F','02-2552-9422','500','"身為一群懷抱星夢的練習生，你們的夢想之路可說是倒楣透頂！
在前往益智節目現場的途中，腳下公車卻傳來爆胎的聲音……這是手上最後一個通告機會，距離直播還有 60 分鐘，如果趕不上，別說出道，就連練習生的資格都沒有了……下了公車，望著遙遠的電視台大樓和路邊破爛的腳踏車…..
什麼挑戰都來吧！今天，一定要成為偶像出道！"
','img/game/id-1062.jpg'),
('梅林的鬍子遊戲工作室','聖劍騎士','重度解謎','6-8人','已謝幕','90分鐘','宜蘭縣宜蘭市康樂路61號','03-933-1765','750','"亞瑟王在場世紀婚禮上離奇猝死，而皇后和國王的忠臣被關在卡美洛城堡內至今生死未卜，亞瑟王身前所配戴的王者石中劍也消聲匿跡。
城外騎士團首領蘭斯洛特召開了圓桌會議，計畫突破重圍營救亞瑟王的忠臣並尋找失落的石中劍。
騎士們，你們有勇氣勇闖城堡嗎…"
','img/game/id-1063.jpg');



--預約(融暘)

CREATE TABLE booking (
    bookingid INT IDENTITY(6001, 1) PRIMARY KEY,
    memno INT NOT NULL,
    gameid INT NOT NULL,
    gameDate NVARCHAR(100) NOT NULL,
    gameTime NVARCHAR(50) NOT NULL,
	bookingTime datetime2(0) default getdate()
);

/*ALTER TABLE booking
ADD CONSTRAINT FK_Booking_Game
FOREIGN KEY (GameID) REFERENCES GameData (GameID);
*/



insert into booking (memno, gameid, gameDate, gameTime ) values
(1003, 1011, '2023-07-12', '20：00'),
(1006, 1012, '2023-08-08', '18：30'),
(1009, 1023, '2023-07-25', '17：00'),
(1012, 1045, '2023-08-11', '20：00'),
(1019, 1033, '2023-07-31', '21：30'),
(1019, 1034, '2023-07-14', '18：30'),
(1019, 1035, '2023-08-05', '20：00'),
(1019, 1018, '2023-08-01', '20：00'),
(1002, 1001, '2023-07-15', '21：30'),
(1002, 1027, '2023-07-20', '20：00'),
(1002, 1005, '2023-08-01', '20：00'),
(1003, 1044, '2023-08-06', '20：00'),
(1004, 1061, '2023-07-10', '20：00'),
(1001, 1001, '2023-08-27', '20：00'),
(1001, 1027, '2023-07-29', '17：00');

