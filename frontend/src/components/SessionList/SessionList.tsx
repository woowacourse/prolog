import ResponsiveButton from '../../components/Button/ResponsiveButton';
import COLOR from '../../constants/color';
import * as Styled from './SessionList.styles';
import { useGetSessions } from '../../hooks/queries/session';
import { useEffect } from 'react';

interface SessionListProps {
  curriculumId: number;
  selectedSessionId: number;
  handleClickSession: (id: number) => void;
}

const SessionList = ({ curriculumId, selectedSessionId, handleClickSession }: SessionListProps) => {
  const { sessions } = useGetSessions(curriculumId);

  useEffect(() => {
    if (sessions?.length) {
      handleClickSession(sessions[0].sessionId);
    } else {
      handleClickSession(-1);
    }
  }, [sessions]);

  return (
    <Styled.Root>
      {sessions?.map(({ sessionId, name }) => {
        return (
          <Styled.SessionButtonWrapper key={sessionId}>
            <ResponsiveButton
              onClick={() => handleClickSession(sessionId)}
              text={name}
              color={selectedSessionId === sessionId ? COLOR.WHITE : COLOR.BLACK_600}
              backgroundColor={
                selectedSessionId === sessionId ? COLOR.LIGHT_BLUE_900 : COLOR.LIGHT_GRAY_400
              }
              height="36px"
            />
          </Styled.SessionButtonWrapper>
        );
      })}
    </Styled.Root>
  );
};

export default SessionList;
