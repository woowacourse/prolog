import React, { useState, useRef, useEffect } from 'react';
import styled from '@emotion/styled';
import axios from 'axios';

const api = axios.create({
  baseURL: 'http://localhost:8080',
});

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

interface InterviewSessionProps {
  session: {
    id: number;
    memberId: number;
    finished: boolean;
    messages: {
      sender: 'SYSTEM' | 'INTERVIEWER' | 'INTERVIEWEE';
      content: string;
      hint: string;
      createdAt: string;
    }[];
  };
  onSessionUpdate: (session: any) => void;
  onSessionEnd: () => void;
}

const InterviewSession = ({
  session,
  onSessionUpdate,
  onSessionEnd,
}: InterviewSessionProps) => {
  const [answer, setAnswer] = useState('');
  const [isSubmitting, setIsSubmitting] = useState(false);
  const chatContainerRef = useRef<HTMLDivElement>(null);

  useEffect(() => {
    if (chatContainerRef.current) {
      chatContainerRef.current.scrollTop = chatContainerRef.current.scrollHeight;
    }
  }, [session?.messages]);

  const handleSubmit = async () => {
    if (!answer.trim() || isSubmitting || !session) return;

    setIsSubmitting(true);
    try {
      const accessToken = localStorage.getItem('accessToken');
      const response = await api.post(
        `/interviews/${session.id}/answers`,
        { answer: answer.trim() },
        {
          headers: {
            Authorization: `Bearer ${accessToken}`,
          },
        }
      );
      onSessionUpdate(response.data);
      setAnswer('');
    } catch (error) {
      console.error('Failed to submit answer:', error);
    } finally {
      setIsSubmitting(false);
    }
  };

  const handleRestart = async () => {
    if (!session) return;
    
    try {
      const accessToken = localStorage.getItem('accessToken');
      const response = await api.post(
        `/interviews/${session.id}/restart`,
        {},
        {
          headers: {
            Authorization: `Bearer ${accessToken}`,
          },
        }
      );
      onSessionUpdate(response.data);
    } catch (error) {
      console.error('Failed to restart interview:', error);
    }
  };

  const handleQuit = async () => {
    if (!session) return;
    
    try {
      const accessToken = localStorage.getItem('accessToken');
      await api.post(
        `/interviews/${session.id}/quit`,
        {},
        {
          headers: {
            Authorization: `Bearer ${accessToken}`,
          },
        }
      );
      onSessionEnd();
    } catch (error) {
      console.error('Failed to quit interview:', error);
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
        {session.messages.map((message, index) => (
          <Message
            key={index}
            isUser={message.sender === 'INTERVIEWEE'}
          >
            {message.content}
          </Message>
        ))}
      </ChatContainer>
      <InputContainer>
        <Input
          value={answer}
          onChange={(e) => setAnswer(e.target.value)}
          placeholder="답변을 입력하세요..."
          onKeyPress={(e) => e.key === 'Enter' && handleSubmit()}
          disabled={session.finished}
        />
        <ButtonGroup>
          <Button onClick={handleSubmit} disabled={!answer.trim() || isSubmitting || session.finished}>
            답변하기
          </Button>
          <Button onClick={handleQuit} variant="secondary">
            결과 보기
          </Button>
        </ButtonGroup>
      </InputContainer>
    </Container>
  );
};

export default InterviewSession; 