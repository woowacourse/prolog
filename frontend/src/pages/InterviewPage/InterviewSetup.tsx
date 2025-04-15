import React, { useEffect, useState } from 'react';
import styled from '@emotion/styled';
import axios from 'axios';

const api = axios.create({
  baseURL: 'http://localhost:8080',
});

const Container = styled.div`
  display: flex;
  flex-direction: column;
  height: 100%;
  gap: 1.5rem;
`;

const SelectionsContainer = styled.div`
  display: flex;
  gap: 2.5rem;
  flex: 1;
  min-height: 0;
  position: relative;
`;

const SelectionGroup = styled.div`
  display: flex;
  flex-direction: column;
  gap: 1rem;
  flex: 1;
  position: relative;
  padding: 1.5rem;
  background: white;
  border-radius: 12px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.05);
  height: 150px;
`;

const ArrowIcon = styled.div`
  position: absolute;
  top: 50%;
  transform: translateY(-50%);
  width: 0;
  height: 0;
  border-top: 10px solid transparent;
  border-bottom: 10px solid transparent;
  border-left: 10px solid #dee2e6;
  z-index: 1;

  &:nth-of-type(1) {
    left: calc(33.33% + -0.9rem);
  }

  &:nth-of-type(2) {
    left: calc(66.66% + 0.1rem);
  }
`;

const SelectionContent = styled.div`
  flex: 1;
  min-height: 0;
  overflow-y: auto;
  
  &::-webkit-scrollbar {
    width: 8px;
  }

  &::-webkit-scrollbar-track {
    background: #f1f3f5;
    border-radius: 4px;
  }

  &::-webkit-scrollbar-thumb {
    background: #ced4da;
    border-radius: 4px;
  }

  &::-webkit-scrollbar-thumb:hover {
    background: #adb5bd;
  }
`;

const Label = styled.label`
  font-weight: 600;
  font-size: 1.25rem;
  color: #495057;
  margin-bottom: 0.5rem;
`;

const Select = styled.select`
  width: 100%;
  padding: 0.875rem 1rem;
  border: 1px solid #dee2e6;
  border-radius: 8px;
  font-size: 1rem;
  background-color: white;
  cursor: pointer;
  
  &:disabled {
    background-color: #f8f9fa;
    cursor: not-allowed;
    border-color: #e9ecef;
  }

  &:focus {
    outline: none;
    border-color: #339af0;
    box-shadow: 0 0 0 3px rgba(51, 154, 240, 0.1);
  }
`;

const List = styled.ul`
  list-style: none;
  padding: 0;
  margin: 0;
`;

const ListItem = styled.li<{ isSelected: boolean }>`
  padding: 1rem 1.25rem;
  margin-bottom: 0.5rem;
  border: 1px solid ${({ isSelected }) => (isSelected ? '#339af0' : '#dee2e6')};
  border-radius: 8px;
  cursor: pointer;
  background-color: ${({ isSelected }) => (isSelected ? '#e7f5ff' : 'white')};
  color: ${({ isSelected }) => (isSelected ? '#339af0' : '#495057')};
  font-size: 1rem;
  transition: all 0.2s;

  &:hover {
    background-color: ${({ isSelected }) => (isSelected ? '#e7f5ff' : '#f8f9fa')};
  }

  &:last-child {
    margin-bottom: 0;
  }
`;

const ButtonContainer = styled.div`
  display: flex;
  justify-content: flex-end;
  padding: 1rem;
  margin-top: auto;
  border-top: 1px solid #f1f3f5;
  background: white;
`;

const Button = styled.button`
  padding: 0.875rem 2rem;
  background-color: #339af0;
  color: white;
  border: none;
  border-radius: 8px;
  font-size: 1rem;
  font-weight: 600;
  cursor: pointer;
  transition: all 0.2s;

  &:hover {
    background-color: #228be6;
  }

  &:disabled {
    background-color: #adb5bd;
    cursor: not-allowed;
  }
`;

interface Session {
  id: number;
  name: string;
}

interface Mission {
  id: number;
  name: string;
}

interface Question {
  id: number;
  content: string;
}

interface InterviewSetupProps {
  onSessionStart: (session: any) => void;
  session: any;
}

const InterviewSetup = ({ onSessionStart, session }: InterviewSetupProps) => {
  const [sessions, setSessions] = useState<Session[]>([]);
  const [missions, setMissions] = useState<Mission[]>([]);
  const [questions, setQuestions] = useState<Question[]>([]);

  const [selectedSession, setSelectedSession] = useState<number | ''>('');
  const [selectedMission, setSelectedMission] = useState<number | ''>('');
  const [selectedQuestion, setSelectedQuestion] = useState<number | ''>('');

  const [isLoading, setIsLoading] = useState(false);

  useEffect(() => {
    const fetchSessions = async () => {
      try {
        const accessToken = localStorage.getItem('accessToken');
        const response = await api.get('/sessions/mine', {
          headers: {
            Authorization: `Bearer ${accessToken}`,
          },
        });
        setSessions(response.data);
      } catch (error) {
        console.error('Failed to fetch sessions:', error);
      }
    };

    fetchSessions();
  }, []);

  useEffect(() => {
    const fetchMissions = async () => {
      if (!selectedSession) {
        setMissions([]);
        return;
      }

      try {
        const accessToken = localStorage.getItem('accessToken');
        const response = await api.get(`/missions/mine`, {
          headers: {
            Authorization: `Bearer ${accessToken}`,
          },
        });
        setMissions(response.data);
      } catch (error) {
        console.error('Failed to fetch missions:', error);
      }
    };

    fetchMissions();
  }, [selectedSession]);

  useEffect(() => {
    const fetchQuestions = async () => {
      if (!selectedMission) {
        setQuestions([]);
        return;
      }

      try {
        const accessToken = localStorage.getItem('accessToken');
        const response = await api.get(`/questions?missionId=${selectedMission}`, {
          headers: {
            Authorization: `Bearer ${accessToken}`,
          },
        });
        console.log('Questions API response:', response.data);
        
        // API 응답 데이터 구조 확인 및 변환
        const questionList = response.data.questions || response.data || [];
        const formattedQuestions = questionList.map((q: any) => ({
          id: q.id,
          content: q.content || q.question || q.text || '',
        }));
        
        setQuestions(formattedQuestions);
      } catch (error) {
        console.error('Failed to fetch questions:', error);
        setQuestions([]);
      }
    };

    fetchQuestions();
  }, [selectedMission]);

  const handleStartInterview = async () => {
    if (!selectedQuestion) return;

    setIsLoading(true);
    try {
      const accessToken = localStorage.getItem('accessToken');
      const response = await api.post('/interviews', {
        questionId: selectedQuestion,
      }, {
        headers: {
          Authorization: `Bearer ${accessToken}`,
        },
      });
      onSessionStart(response.data);
    } catch (error) {
      console.error('Failed to start interview:', error);
    } finally {
      setIsLoading(false);
    }
  };

  const handleSessionChange = (id: number) => {
    setSelectedSession(id);
    setSelectedMission('');
    setSelectedQuestion('');
  };

  const handleMissionChange = (id: number) => {
    setSelectedMission(id);
    setSelectedQuestion('');
  };

  const handleQuestionChange = (id: number) => {
    setSelectedQuestion(id);
  };

  return (
    <Container>
      <SelectionsContainer>
        <ArrowIcon />
        <ArrowIcon />
        <SelectionGroup>
          <Label>레벨 선택</Label>
          <SelectionContent>
            <List>
              {sessions.map((session) => (
                <ListItem
                  key={session.id}
                  isSelected={selectedSession === session.id}
                  onClick={() => handleSessionChange(session.id)}
                >
                  {session.name}
                </ListItem>
              ))}
              {sessions.length === 0 && (
                <ListItem isSelected={false}>
                  레벨이 없습니다
                </ListItem>
              )}
            </List>
          </SelectionContent>
        </SelectionGroup>

        <SelectionGroup>
          <Label>미션 선택</Label>
          <SelectionContent>
            <List>
              {missions.map((mission) => (
                <ListItem
                  key={mission.id}
                  isSelected={selectedMission === mission.id}
                  onClick={() => handleMissionChange(mission.id)}
                >
                  {mission.name}
                </ListItem>
              ))}
              {missions.length === 0 && selectedSession && (
                <ListItem isSelected={false}>
                  미션이 없습니다
                </ListItem>
              )}
              {!selectedSession && (
                <ListItem isSelected={false}>
                  레벨을 먼저 선택해주세요
                </ListItem>
              )}
            </List>
          </SelectionContent>
        </SelectionGroup>

        <SelectionGroup>
          <Label>질문 선택</Label>
          <SelectionContent>
            <List>
              {questions.map((question) => (
                <ListItem
                  key={question.id}
                  isSelected={selectedQuestion === question.id}
                  onClick={() => handleQuestionChange(question.id)}
                >
                  {question.content}
                </ListItem>
              ))}
              {questions.length === 0 && selectedMission && (
                <ListItem isSelected={false}>
                  질문이 없습니다
                </ListItem>
              )}
              {!selectedMission && (
                <ListItem isSelected={false}>
                  미션을 먼저 선택해주세요
                </ListItem>
              )}
            </List>
          </SelectionContent>
        </SelectionGroup>
      </SelectionsContainer>

      <ButtonContainer>
        <Button
          onClick={handleStartInterview}
          disabled={!selectedQuestion || isLoading}
        >
          {isLoading ? '인터뷰 시작 중...' : (session ? '다시 시작' : '인터뷰 시작')}
        </Button>
      </ButtonContainer>
    </Container>
  );
};

export default InterviewSetup; 