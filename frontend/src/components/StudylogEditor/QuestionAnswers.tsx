import React, { MutableRefObject, useEffect, useState } from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';
import 'bootstrap/dist/js/bootstrap.bundle.min.js';
import {
  AccordionButton,
  AccordionHeader,
  AnswerBody,
  AnswerTextArea,
  FeedbackBody,
  FeedbackItemTitle,
  MainContainer,
  NoQuestionMessage,
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
            <NoQuestionMessage>미션에 등록된 질문이 없습니다.</NoQuestionMessage>
          ) : (
            props.questions.map((question) => {
              const answer = answers.find((a) => a.questionId === question.id);
              return (
                <div className="accordion-item" key={question.id}>
                  <AccordionHeader className="accordion-header" id={`heading${question.id}`}>
                    <AccordionButton
                      disabled={true}
                      className="accordion-button"
                      type="button"
                      data-bs-toggle="collapse"
                      data-bs-target={`#collapse${question.id}`}
                      aria-expanded="true"
                      aria-controls={`collapse${question.id}`}
                    >
                      {question.content}
                    </AccordionButton>
                  </AccordionHeader>
                  <div
                    id={`collapse${question.id}`}
                    className="accordion-collapse collapse show"
                    aria-labelledby={`heading${question.id}`}
                  >
                    <AnswerBody className="accordion-body">
                      <AnswerTextArea
                        value={answer?.answerContent || ''}
                        onChange={(e) => {
                          if (e.target.value.length <= 1000) {
                            handleAnswerChange(question.id, e.target.value);
                          }
                        }}
                        placeholder="답변을 입력하세요. (최대 1000자)"
                      />
                    </AnswerBody>
                  </div>
                </div>
              );
            })
          )
        ) : props.questionAnswers.length === 0 ? (
          <NoQuestionMessage>미션에 등록된 질문이 없습니다.</NoQuestionMessage>
        ) : (
          props.questionAnswers.map((qa) => (
            <div className="accordion-item" key={qa.id}>
              <AccordionHeader className="accordion-header" id={`heading${qa.id}`}>
                <AccordionButton
                  className={`accordion-button ${qa.answerContent ? '' : 'collapsed'}`}
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
                className={`accordion-collapse collapse ${qa.answerContent ? 'show' : ''}`}
                aria-labelledby={`heading${qa.id}`}
              >
                <AnswerBody className="accordion-body">
                  <div>{qa.answerContent || '답변이 없습니다.'}</div>
                </AnswerBody>
                <FeedbackBody className="accordion-body">
                  <div>
                    {qa.strengths || qa.improvementPoints || qa.additionalLearning ? (
                      <>
                        <div>
                          <FeedbackItemTitle>✅ 잘한 점: </FeedbackItemTitle> {qa.strengths || ''}
                        </div>
                        <div>
                          <FeedbackItemTitle>⚠️ 개선할 점: </FeedbackItemTitle>{' '}
                          {qa.improvementPoints || ''}
                        </div>
                        <div>
                          <FeedbackItemTitle>📌 추가 학습 방향: </FeedbackItemTitle>{' '}
                          {qa.additionalLearning || ''}
                        </div>
                      </>
                    ) : (
                      '피드백이 없습니다.'
                    )}
                  </div>
                </FeedbackBody>
              </div>
            </div>
          ))
        )}
      </div>
    </MainContainer>
  );
};

export default QuestionAnswers;
