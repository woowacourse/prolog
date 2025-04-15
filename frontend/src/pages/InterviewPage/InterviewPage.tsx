/** @jsxImportSource @emotion/react */

import React, { useState } from 'react';
import styled from '@emotion/styled';
import { MainContentStyle } from '../../PageRouter';
import InterviewSetup from './InterviewSetup';
import InterviewSession from './InterviewSession';

const Container = styled.div`
  display: flex;
  flex-direction: column;
  height: calc(100vh - 12rem);
  background: #ffffff;
`;

const ContentContainer = styled.div`
  display: flex;
  flex-direction: column;
  flex: 1;
  min-height: 0;
  gap: 2rem;
  background: linear-gradient(to bottom, #f8f9fa, #ffffff);
`;

const Section = styled.div`
  background: #ffffff;
  border-radius: 16px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.05);
  overflow: hidden;
`;

const SetupSection = styled(Section)`
  height: 300px;
`;

const SessionSection = styled(Section)`
  flex: 1;
  display: flex;
  flex-direction: column;
  min-height: 0;
`;

const SectionHeader = styled.div`
  padding: 1.5rem 2rem;
  border-bottom: 1px solid #f1f3f5;
  background: #ffffff;
`;

const SectionTitle = styled.h2`
  font-size: 1.75rem;
  font-weight: 700;
  color: #343a40;
  margin: 0;
`;

const SectionContent = styled.div`
  flex: 1;
  min-height: 0;
  padding: 2rem;
`;

const InterviewPage = () => {
  const [session, setSession] = useState<any>(null);

  const handleSessionStart = (newSession: any) => {
    setSession(newSession);
  };

  const handleSessionUpdate = (updatedSession: any) => {
    setSession(updatedSession);
  };

  const handleSessionEnd = () => {
    setSession(null);
  };

  return (
    <div css={MainContentStyle}>
      <Container>
        <ContentContainer>
          <SetupSection>
            <SectionHeader>
              <SectionTitle>레벨 인터뷰 설정</SectionTitle>
            </SectionHeader>
            <SectionContent>
              <InterviewSetup 
                onSessionStart={handleSessionStart}
                session={session}
              />
            </SectionContent>
          </SetupSection>
          <SessionSection>
            <SectionHeader>
              <SectionTitle>레벨 인터뷰 진행</SectionTitle>
            </SectionHeader>
            <SectionContent>
              <InterviewSession
                session={session}
                onSessionUpdate={handleSessionUpdate}
                onSessionEnd={handleSessionEnd}
              />
            </SectionContent>
          </SessionSection>
        </ContentContainer>
      </Container>
    </div>
  );
};

export default InterviewPage;
