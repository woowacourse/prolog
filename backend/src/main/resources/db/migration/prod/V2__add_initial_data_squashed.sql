-- insert common
INSERT INTO default_ability (name, description, color, template) -- 1
VALUES ('CS', 'CS 입니다.', '#9100ff', 'common');
INSERT INTO default_ability (name, description, color, template, parent_id) -- 2
VALUES ('Database', 'Database 입니다.', '#9100ff', 'common', 1);
INSERT INTO default_ability (name, description, color, template, parent_id) -- 3
VALUES ('네트워크', '네트워크 입니다.', '#9100ff', 'common', 1);
INSERT INTO default_ability (name, description, color, template, parent_id) -- 4
VALUES ('OS', 'OS 입니다.', '#9100ff', 'common', 1);
INSERT INTO default_ability (name, description, color, template, parent_id) -- 5
VALUES ('알고리즘', '알고리즘 입니다.', '#9100ff', 'common', 1);
INSERT INTO default_ability (name, description, color, template, parent_id) -- 6
VALUES ('자료구조', '자료구조 입니다.', '#9100ff', 'common', 1);

-- insert back-end
INSERT INTO default_ability (name, description, color, template) -- 7
VALUES ('Programming', 'Programming 입니다.', '#ff9100', 'be');
INSERT INTO default_ability (name, description, color, template, parent_id) -- 8
VALUES ('Language', 'Language 입니다.', '#ff9100', 'be', 7);
INSERT INTO default_ability (name, description, color, template, parent_id) -- 9
VALUES ('Framework', 'Framework 입니다.', '#ff9100', 'be', 7);
INSERT INTO default_ability (name, description, color, template, parent_id) -- 10
VALUES ('Testing', 'Testing 입니다.', '#ff9100', 'be', 7);
INSERT INTO default_ability (name, description, color, template, parent_id) -- 11
VALUES ('Web Programming', 'Web Programming 입니다.', '#ff9100', 'be', 7);

INSERT INTO default_ability (name, description, color, template) -- 12
VALUES ('Design', 'Design 입니다.', '#00cccc', 'be');
INSERT INTO default_ability (name, description, color, template, parent_id) -- 13
VALUES ('Development Driven', 'Development Driven 입니다.', '#00cccc', 'be', 12);
INSERT INTO default_ability (name, description, color, template, parent_id) -- 14
VALUES ('Programming Principle', 'Programming Principle 입니다.', '#00cccc', 'be', 12);

INSERT INTO default_ability (name, description, color, template) -- 15
VALUES ('Infrastructure', 'Infrastructure 입니다.', '#ccccff', 'be');
INSERT INTO default_ability (name, description, color, template, parent_id) -- 16
VALUES ('Web Architecture Components', 'Web Architecture Components 입니다.', '#ccccff', 'be', 15);
INSERT INTO default_ability (name, description, color, template, parent_id) -- 17
VALUES ('Service & Tools', 'Service & Tools 입니다.', '#ccccff', 'be', 15);

INSERT INTO default_ability (name, description, color, template) -- 18
VALUES ('Software Development Process & Maintenance', 'Software Development Process & Maintenance 입니다.', '#ffcce5', 'be');
INSERT INTO default_ability (name, description, color, template, parent_id) -- 19
VALUES ('Development Process', 'Development Process 입니다.', '#ffcce5', 'be', 18);
INSERT INTO default_ability (name, description, color, template, parent_id) -- 20
VALUES ('Maintenance', 'Maintenance 입니다.', '#ffcce5', 'be', 18);
