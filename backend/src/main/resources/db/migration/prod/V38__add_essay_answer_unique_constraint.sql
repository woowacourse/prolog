ALTER TABLE essay_answer
    ADD CONSTRAINT UK_essay_answer_quiz_id_member_id UNIQUE (quiz_id, member_id);
