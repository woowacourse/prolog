import React, { useState, useRef, useEffect } from 'react';
import styled from '@emotion/styled';
import { client } from '../../apis';

const Container = styled.div`
  display: flex;
  flex-direction: column;
  gap: 1rem;
  height: 100%;
`;

const ChatContainer = styled.div`
  flex: 1;
  overflow-y: auto;
  padding: 1rem;
  background-color: #f8f9fa;
  border-radius: 12px;
  display: flex;
  flex-direction: column;
  gap: 1rem;
`;

const Message = styled.div<{ isUser: boolean }>`
  max-width: 80%;
  padding: 1rem 1.25rem;
  border-radius: 16px;
  background-color: ${({ isUser }) => (isUser ? '#33c859' : 'white')};
  color: ${({ isUser }) => (isUser ? 'white' : '#495057')};
  align-self: ${({ isUser }) => (isUser ? 'flex-end' : 'flex-start')};
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.05);
  font-size: 1.125rem;
  line-height: 1.5;
  white-space: pre-wrap;
`;

const EmphasizedFeedbackMessage = styled(Message)`
  border-left: 5px solid #0D6EFD !important;
  background-color: #f0f0f0 !important;
  color: #000000 !important;

  display: block !important;
  width: auto !important;
  min-width: 200px !important;
  word-break: normal !important;

  padding: 1rem !important;
  border-top: none !important;
  border-right: none !important;
  border-bottom: none !important;
  box-shadow: none !important;
  border-radius: 4px !important;
`;

const InputContainer = styled.div`
  display: flex;
  gap: 1rem;
  margin-top: 1rem;
  background: #f8f9fa;
  padding: 1rem;
  border-radius: 12px;
`;

const Input = styled.textarea`
  flex: 1;
  padding: 1rem 1.25rem;
  border: 1px solid #e9ecef;
  border-radius: 12px;
  font-size: 1.125rem;
  resize: none;
  height: 100px;
  background: white;
  transition: all 0.2s;

  &:focus {
    outline: none;
    border-color: #339af0;
    box-shadow: 0 0 0 3px rgba(51, 154, 240, 0.1);
  }

  &:disabled {
    background-color: #f1f3f5;
    cursor: not-allowed;
  }
`;

const ButtonGroup = styled.div`
  display: flex;
  flex-direction: column;
  gap: 0.5rem;
  justify-content: center;
`;

const Button = styled.button<{ variant?: 'primary' | 'secondary' }>`
  padding: 0.875rem 1.5rem;
  background-color: ${({ variant }) => variant === 'secondary' ? '#868e96' : '#339af0'};
  color: white;
  border: none;
  border-radius: 8px;
  font-size: 1.125rem;
  font-weight: 600;
  cursor: pointer;
  transition: all 0.2s;

  &:hover {
    background-color: ${({ variant }) => variant === 'secondary' ? '#495057' : '#228be6'};
  }

  &:disabled {
    background-color: #adb5bd;
    cursor: not-allowed;
  }
`;

const EmptyState = styled.div`
  display: flex;
  align-items: center;
  justify-content: center;
  height: 100%;
  color: #868e96;
  font-size: 1.125rem;
  text-align: center;
  padding: 2rem;
  background: #f8f9fa;
  border-radius: 12px;
`;

const HintButton = styled.div`
  width: 18px;
  height: 18px;
  border-radius: 50%;
  border: 1px solid #bbb;
  background: transparent;
  display: flex;
  align-items: center;
  justify-content: center;
  cursor: pointer;
  font-weight: 500;
  font-size: 13px;
  color: #888;
  position: relative;
`;

const HintTooltip = styled.div`
  position: absolute;
  top: 120%;
  left: 50%;
  transform: translateX(-50%);
  background: #fff;
  border: 1px solid #ddd;
  border-radius: 8px;
  box-shadow: 0 2px 8px #0001;
  padding: 10px 14px;
  font-size: 1rem;
  color: #333;
  z-index: 10;
  min-width: 140px;
  max-width: 320px;
  white-space: pre-wrap;
  font-weight: 400;
  line-height: 1.5;
  font-family: inherit;
`;

interface InterviewSessionData {
  id: number;
  memberId: number;
  finished: boolean;
  messages: {
    sender: 'SYSTEM' | 'INTERVIEWER' | 'INTERVIEWEE';
    content: string;
    hint: string;
    createdAt: string;
  }[];
  currentRound: number;
  remainRound: number;
}

interface InterviewSessionProps {
  session: InterviewSessionData | null;
  onSessionUpdate: (session: InterviewSessionData) => void;
  onSessionEnd: () => void;
}

const InterviewSession = ({
  session,
  onSessionUpdate,
  onSessionEnd,
}: InterviewSessionProps) => {
  const [answer, setAnswer] = useState('');
  const [isSubmitting, setIsSubmitting] = useState(false);
  const [localMessages, setLocalMessages] = useState(session?.messages ?? []);
  const [isTyping, setIsTyping] = useState(false);
  const chatContainerRef = useRef<HTMLDivElement>(null);
  const [hoveredHintIdx, setHoveredHintIdx] = useState<number | null>(null);
  const inputRef = useRef<HTMLTextAreaElement>(null);

  useEffect(() => {
    setLocalMessages(session?.messages ?? []);
  }, [session?.messages]);

  useEffect(() => {
    if (chatContainerRef.current) {
      chatContainerRef.current.scrollTop = chatContainerRef.current.scrollHeight;
    }
  }, [localMessages]);

  const handleSubmit = async () => {
    if (!answer.trim() || isSubmitting || !session) return;

    setIsSubmitting(true);
    const newMessage = {
      sender: 'INTERVIEWEE',
      content: answer.trim(),
      hint: '',
      createdAt: new Date().toISOString(),
    } as const;
    setLocalMessages((prev) => [...prev, newMessage]);
    setAnswer('');
    setIsTyping(true);

    try {
      const accessToken = localStorage.getItem('accessToken');
      const response = await client.post(
        `/interviews/${session.id}/answers`,
        { answer: newMessage.content },
        {
          headers: {
            Authorization: `Bearer ${accessToken}`,
          },
        }
      );
      setLocalMessages(response.data.messages);
      onSessionUpdate(response.data as InterviewSessionData);
    } catch (error) {
      console.error('Failed to submit answer:', error);
    } finally {
      setIsSubmitting(false);
      setIsTyping(false);
      if (session && !(session.finished || session.remainRound <= 0)) {
        inputRef.current?.focus();
      }
    }
  };

  if (!session) {
    return (
      <Container>
        <EmptyState>
          인터뷰를 시작하기 위해 왼쪽에서 레벨과 미션, 질문을 선택해주세요.
        </EmptyState>
      </Container>
    );
  }

  return (
    <Container>
      <ChatContainer ref={chatContainerRef}>
        {localMessages.map((message, idx) => {
          const isLastMessage = idx === localMessages.length - 1;
          const isInterviewFinished = (session?.finished || session?.remainRound <= 0);

          if (message.sender === 'INTERVIEWER') {
            const CurrentMessageComponent = (isInterviewFinished && isLastMessage)
                                              ? EmphasizedFeedbackMessage
                                              : Message;
            return (
              <div key={idx} style={{ display: 'flex', alignItems: 'center', gap: 8, position: 'relative', alignSelf: 'flex-start' }}>
                <CurrentMessageComponent isUser={false}>
                  {message.content}
                </CurrentMessageComponent>
                {message.hint && (
                  <HintButton
                    onMouseEnter={() => setHoveredHintIdx(idx)}
                    onMouseLeave={() => setHoveredHintIdx(null)}
                    aria-label="힌트"
                  >
                    ?
                    {hoveredHintIdx === idx && (
                      <HintTooltip>
                        {message.hint || '힌트가 없습니다.'}
                      </HintTooltip>
                    )}
                  </HintButton>
                )}
              </div>
            );
          }
          // INTERVIEWEE 메시지의 경우 (또는 시스템 메시지 등) - 여기서는 강조 없음
          return (
            <Message key={idx} isUser={message.sender === 'INTERVIEWEE'}>
              {message.content}
            </Message>
          );
        })}
        {isTyping && (
          <Message isUser={false} style={{ fontStyle: 'italic', opacity: 0.7 }}>
            인터뷰어가 답변을 작성중입니다...
          </Message>
        )}
      </ChatContainer>

      {/* 인터뷰가 진행 중일 때만 답변 입력창 표시 */}
      {session && !(session.finished || session.remainRound <= 0) && (
        <InputContainer>
          <Input
            ref={inputRef}
            value={answer}
            onChange={(e) => setAnswer(e.target.value)}
            placeholder="답변을 입력하세요..."
            onKeyPress={(e) => {
              if (e.key === 'Enter' && !e.shiftKey && !isSubmitting) {
                e.preventDefault();
                handleSubmit();
              }
            }}
            disabled={isSubmitting || !session }
          />
          <ButtonGroup>
            <Button
              onClick={handleSubmit}
              disabled={!answer.trim() || isSubmitting || !session }
            >
              {isSubmitting ? '전송 중...' : '답변 전송'}
            </Button>
          </ButtonGroup>
        </InputContainer>
      )}
    </Container>
  );
};

export default InterviewSession;
