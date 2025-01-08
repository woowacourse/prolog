import React, { MutableRefObject, useEffect, useState } from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';
import 'bootstrap/dist/js/bootstrap.bundle.min.js';
import {
  AccordionHeader,
  AccordionButton,
  AnswerTextArea,
  MainContainer,
  NoQuestionMessage,
  AnswerBody,
} from './QuestionAnswerStyles';
import { Answer, Question, QuestionAnswer } from '../../models/Studylogs';

interface EditableProps {
  editable: true;
  questions: Question[];
  editorAnswerRef: MutableRefObject<Answer[]>;
}

interface NonEditableProps {
  editable: false;
  questionAnswers: QuestionAnswer[];
}

type QuestionAnswerProps = EditableProps | NonEditableProps;

const QuestionAnswers: React.FC<QuestionAnswerProps> = (props) => {
  const [answers, setAnswers] = useState<Answer[]>([]);

  useEffect(() => {
    if (props.editable) {
      const initialAnswers = props.questions.map((question) => {
        return (
          props.editorAnswerRef.current.find((answer) => answer.questionId === question.id) || {
            questionId: question.id,
            answerContent: '',
          }
        );
      });
      setAnswers(initialAnswers);
    }
  }, [props]);

  const handleAnswerChange = (questionId: number, answerContent: string) => {
    if (!props.editable) return; // editable이 false면 변경 불가

    const updatedAnswers = answers.map((answer) =>
      answer.questionId === questionId ? { ...answer, answerContent } : answer
    );
    setAnswers(updatedAnswers);

    if (props.editable) {
      const existingAnswerIndex = props.editorAnswerRef.current.findIndex(
        (answer) => answer.questionId === questionId
      );

      if (existingAnswerIndex !== -1) {
        props.editorAnswerRef.current[existingAnswerIndex].answerContent = answerContent;
      } else {
        props.editorAnswerRef.current.push({ questionId, answerContent });
      }
    }
  };

  return (
    <MainContainer>
      <div className="accordion" id="questionAccordion">
        {props.editable ? (
          props.questions.length === 0 ? (
            <NoQuestionMessage>'질문이 없습니다. 질문을 추가해주세요.'</NoQuestionMessage>
          ) : (
            props.questions.map((question) => {
              const answer = answers.find((a) => a.questionId === question.id);
              return (
                <div className="accordion-item" key={question.id}>
                  <AccordionHeader className="accordion-header" id={`heading${question.id}`}>
                    <AccordionButton
                      className="accordion-button collapsed"
                      type="button"
                      data-bs-toggle="collapse"
                      data-bs-target={`#collapse${question.id}`}
                      aria-expanded="false"
                      aria-controls={`collapse${question.id}`}
                    >
                      {question.content}
                    </AccordionButton>
                  </AccordionHeader>
                  <div
                    id={`collapse${question.id}`}
                    className="accordion-collapse collapse"
                    aria-labelledby={`heading${question.id}`}
                  >
                    <AnswerBody className="accordion-body">
                      <AnswerTextArea
                        value={answer?.answerContent || ''}
                        onChange={(e) => handleAnswerChange(question.id, e.target.value)}
                        placeholder="답변을 입력하세요..."
                      />
                    </AnswerBody>
                  </div>
                </div>
              );
            })
          )
        ) : props.questionAnswers.length === 0 ? (
          <NoQuestionMessage>'질문이 없습니다. 질문을 추가해주세요.'</NoQuestionMessage>
        ) : (
          props.questionAnswers.map((qa) => (
            <div className="accordion-item" key={qa.id}>
              <AccordionHeader className="accordion-header" id={`heading${qa.id}`}>
                <AccordionButton
                  className="accordion-button collapsed"
                  type="button"
                  data-bs-toggle="collapse"
                  data-bs-target={`#collapse${qa.id}`}
                  aria-expanded="false"
                  aria-controls={`collapse${qa.id}`}
                >
                  {qa.questionContent}
                </AccordionButton>
              </AccordionHeader>
              <div
                id={`collapse${qa.id}`}
                className="accordion-collapse collapse"
                aria-labelledby={`heading${qa.id}`}
              >
                <AnswerBody className="accordion-body">
                  <div>{qa.answerContent || '답변이 없습니다.'}</div>
                </AnswerBody>
              </div>
            </div>
          ))
        )}
      </div>
    </MainContainer>
  );
};

export default QuestionAnswers;
