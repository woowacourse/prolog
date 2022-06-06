CREATE TABLE default_ability
(
    id          BIGINT NOT NULL AUTO_INCREMENT,
    name        VARCHAR(255),
    description VARCHAR(255),
    color       VARCHAR(255),
    template    VARCHAR(255),
    parent_id   BIGINT DEFAULT NULL,
    PRIMARY KEY (id)
) ENGINE=InnoDB;

-- insert common
INSERT INTO default_ability (name, description, color, template) -- 1
VALUES ("CS", "CS 입니다.", "#9100ff", "common");
INSERT INTO default_ability (name, description, color, template, parent_id) -- 2
VALUES ("Database", "Database 입니다.", "#9100ff", "common", 1);
INSERT INTO default_ability (name, description, color, template, parent_id) -- 3
VALUES ("네트워크", "네트워크 입니다.", "#9100ff", "common", 1);
INSERT INTO default_ability (name, description, color, template, parent_id) -- 4
VALUES ("OS", "OS 입니다.", "#9100ff", "common", 1);
INSERT INTO default_ability (name, description, color, template, parent_id) -- 5
VALUES ("알고리즘", "알고리즘 입니다.", "#9100ff", "common", 1);
INSERT INTO default_ability (name, description, color, template, parent_id) -- 6
VALUES ("자료구조", "자료구조 입니다.", "#9100ff", "common", 1);

-- insert back-end
INSERT INTO default_ability (name, description, color, template) -- 7
VALUES ("Programming", "Programming 입니다.", "#ff9100", "be");
INSERT INTO default_ability (name, description, color, template, parent_id) -- 8
VALUES ("Language", "Language 입니다.", "#ff9100", "be", 7);
INSERT INTO default_ability (name, description, color, template, parent_id) -- 9
VALUES ("Framework", "Framework 입니다.", "#ff9100", "be", 7);
INSERT INTO default_ability (name, description, color, template, parent_id) -- 10
VALUES ("Testing", "Testing 입니다.", "#ff9100", "be", 7);
INSERT INTO default_ability (name, description, color, template, parent_id) -- 11
VALUES ("Web Programming", "Web Programming 입니다.", "#ff9100", "be", 7);

INSERT INTO default_ability (name, description, color, template) -- 12
VALUES ("Design", "Design 입니다.", "#00cccc", "be");
INSERT INTO default_ability (name, description, color, template, parent_id) -- 13
VALUES ("Development Driven", "Development Driven 입니다.", "#00cccc", "be", 12);
INSERT INTO default_ability (name, description, color, template, parent_id) -- 14
VALUES ("Programming Principle", "Programming Principle 입니다.", "#00cccc", "be", 12);

INSERT INTO default_ability (name, description, color, template) -- 15
VALUES ("Infrastructure", "Infrastructure 입니다.", "#ccccff", "be");
INSERT INTO default_ability (name, description, color, template, parent_id) -- 16
VALUES ("Web Architecture Components", "Web Architecture Components 입니다.", "#ccccff", "be", 15);
INSERT INTO default_ability (name, description, color, template, parent_id) -- 17
VALUES ("Service & Tools", "Service & Tools 입니다.", "#ccccff", "be", 15);

INSERT INTO default_ability (name, description, color, template) -- 18
VALUES ("Software Development Process & Maintenance",
        "Software Development Process & Maintenance 입니다.", "#ffcce5", "be");
INSERT INTO default_ability (name, description, color, template, parent_id) -- 19
VALUES ("Development Process", "Development Process 입니다.", "#ffcce5", "be", 18);
INSERT INTO default_ability (name, description, color, template, parent_id) -- 20
VALUES ("Maintenance", "Maintenance 입니다.", "#ffcce5", "be", 18);

-- insert front-end
INSERT INTO default_ability (name, description, color, template) -- 21
VALUES ("JavaScript & HTML", "JavaScript & HTML 입니다.", "#ff009a", "fe");
INSERT INTO default_ability (name, description, color, template, parent_id) -- 22
VALUES ("기초 언어 시스템", "기초 언어 시스템 입니다.", "#ff009a", "fe", 21);
INSERT INTO default_ability (name, description, color, template, parent_id) -- 23
VALUES ("고급 언어 시스템", "고급 언어 시스템 입니다.", "#ff009a", "fe", 21);
INSERT INTO default_ability (name, description, color, template, parent_id) -- 24
VALUES ("브라우저", "브라우저 입니다.", "#ff009a", "fe", 21);
INSERT INTO default_ability (name, description, color, template, parent_id) -- 25
VALUES ("비동기&데이터 처리", "비동기&데이터 처리 입니다.", "#ff009a", "fe", 21);
INSERT INTO default_ability (name, description, color, template, parent_id) -- 26
VALUES ("성능", "성능 입니다.", "#ff009a", "fe", 21);
INSERT INTO default_ability (name, description, color, template, parent_id) -- 27
VALUES ("HTML", "HTML 입니다.", "#ff009a", "fe", 21);

INSERT INTO default_ability (name, description, color, template) -- 28
VALUES ("Graphics", "Graphics 입니다.", "#2f8aff", "fe");
INSERT INTO default_ability (name, description, color, template, parent_id) -- 29
VALUES ("CSS 스타일링: 레이아웃과 포지셔닝", "CSS 스타일링: 레이아웃과 포지셔닝 입니다.", "#2f8aff", "fe", 28);
INSERT INTO default_ability (name, description, color, template, parent_id) -- 30
VALUES ("트랜지션, 애니메이션", "트랜지션, 애니메이션 입니다.", "#2f8aff", "fe", 28);
INSERT INTO default_ability (name, description, color, template, parent_id) -- 31
VALUES ("2D 그래픽스", "2D 그래픽스 입니다.", "#2f8aff", "fe", 28);
INSERT INTO default_ability (name, description, color, template, parent_id) -- 32
VALUES ("3D 그래픽스", "3D 그래픽스 입니다.", "#2f8aff", "fe", 28);
INSERT INTO default_ability (name, description, color, template, parent_id) -- 33
VALUES ("Vector 그래픽스", "Vector 그래픽스 입니다.", "#2f8aff", "fe", 28);

INSERT INTO default_ability (name, description, color, template) -- 34
VALUES ("Architecture", "Architecture 입니다.", "#e5ffcc", "fe");
INSERT INTO default_ability (name, description, color, template, parent_id) -- 35
VALUES ("인터랙션 디자인 이론", "인터랙션 디자인 이론 입니다.", "#e5ffcc", "fe", 34);
INSERT INTO default_ability (name, description, color, template, parent_id) -- 36
VALUES ("UI/UX 설계", "UI/UX 설계 입니다.", "#e5ffcc", "fe", 34);
INSERT INTO default_ability (name, description, color, template, parent_id) -- 37
VALUES ("UX 일반 이론", "UX 일반 이론 입니다.", "#e5ffcc", "fe", 34);

INSERT INTO default_ability (name, description, color, template) -- 38
VALUES ("UI/UX", "UI/UX 입니다.", "#2fff6e", "fe");
INSERT INTO default_ability (name, description, color, template, parent_id) -- 39
VALUES ("웹애플리케이션 구현 시 고려해야 하는 설계", "웹애플리케이션 구현 시 고려해야 하는 설계 입니다.", "#2fff6e", "fe", 38);
INSERT INTO default_ability (name, description, color, template, parent_id) -- 40
VALUES ("프로젝트 개발 환경 설계", "프로젝트 개발 환경 설계 입니다.", "#2fff6e", "fe", 38);
INSERT INTO default_ability (name, description, color, template, parent_id) -- 41
VALUES ("테스트와 프로젝트 배포", "테스트와 프로젝트 배포 입니다.", "#2fff6e", "fe", 38);
INSERT INTO default_ability (name, description, color, template, parent_id) -- 42
VALUES ("프로그래밍 테크닉, 개발 프랙티스, 개발 방법론", "프로그래밍 테크닉, 개발 프랙티스, 개발 방법론 입니다.", "#2fff6e", "fe", 38);
