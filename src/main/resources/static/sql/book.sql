CREATE TABLE IF NOT EXISTS book(
    b_bookId VARCHAR(10) NOT NULL,
    b_name VARCHAR(30),
    b_unitPrice INTEGER,
    b_author VARCHAR(50),
    b_description TEXT,
    b_publisher VARCHAR(20),
    b_category VARCHAR(20),
    b_unitsInStock LONG,
    b_releaseDate VARCHAR(20),
    b_condition VARCHAR(20),
    b_fileName VARCHAR(20),
    PRIMARY KEY (b_bookId)
    )DEFAULT CHARSET=utf8;

INSERT INTO book (
    b_bookId,
    b_name,
    b_unitPrice,
    b_author,
    b_description,
    b_publisher,
    b_category,
    b_unitsInStock,
    b_releaseDate,
    b_condition,
    b_fileName
) VALUES (
             'isbn0001',
             '스프링 부트 완전 정복',
             35000,
             '송미영',
             '스프링 부트는 스프링을 기반으로 쉽고 빠르게 웹 애플리케이션을 개발할 수 있는 도구이다. 이 책에서는 스프링 부트의 기본 개념을 쉽게 이해하고 다양한 실습 예제로 빠르게 익힐 수 있다. 그리고 단계별 실습을 따라 하다 보면 도서 쇼핑몰 구축 프로젝트를 완성할 수 있다. 개념-실습-프로젝트의 3단계 학습으로 스프링 부트를 제대로 익힌다면 개발 시간을 단축하고 생산성을 높일 수 있는 개발자로 성장할 수 있다.',
             '길벗캠퍼스',
             'IT 교재',
             1000,
             '2024.12.31',
             '신규 도서',
             'isbn0001.jpg'
         );

INSERT INTO book (
    b_bookId, b_name, b_unitPrice, b_author, b_description,
    b_publisher, b_category, b_unitsInStock, b_releaseDate,
    b_condition, b_fileName
) VALUES (
             'isbn0002',
             '난생처음 인공지능 입문',
             27000,
             '서지영',
             '이 책은 IT 비전공자뿐만 아니라 중고등학생도 쉽게 배울 수 있는 인공지능 입문서이다. 인공지능의 개념을 실생활에서 접할 수 있는 사례를 중심으로 재미있는 삽화와 생생한 이미지를 통해 친절하게 설명한다. 그리고 인공지능을 구현하기 위해 필요한 기술인 GPU, 5G, 클라우드, 사물인터넷, 빅데이터, 머신러닝, 인공신경망, 딥러닝 등을 자세히 알아봅니다.',
             '한빛아카데미',
             'IT 교재',
             642,
             '2024.10.07',
             '중고 도서',
             'isbn0002.jpg'
         );

INSERT INTO book (
    b_bookId, b_name, b_unitPrice, b_author, b_description,
    b_publisher, b_category, b_unitsInStock, b_releaseDate,
    b_condition, b_fileName
) VALUES (
             'isbn0003',
             'PSIM을 활용한 전력전자공학 실습',
             32000,
             '박영수',
             '『PSIM을 활용한 전력전자공학 실습』은 PSIM 프로그램을 사용하는 방법에 대한 기본 지식을 전달하고, PSIM을 활용한 다양한 전력변환장치 실습 예제들을 소개한다. 여러 시뮬레이션 툴 중에서 PSIM은 전력전자공학 분야에서 가장 활발히 사용되고 있으며, 회로 구성 및 코드 구현이 용이한 장점이 있다.',
             '계명대학교 출판부',
             '전기전자공학',
             705,
             '2024.09.25',
             '신규 도서',
             'isbn0003.jpg'
         );