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
    if (sessions) {
      handleClickSession(sessions[0].id);
    }
  }, [sessions]);

  return (
    <Styled.Root>
      {sessions?.map(({ id, name }) => (
        <Styled.SessionButtonWrapper key={id}>
          <ResponsiveButton
            onClick={() => handleClickSession(id)}
            text={name}
            color={selectedSessionId === id ? COLOR.WHITE : COLOR.BLACK_600}
            backgroundColor={selectedSessionId === id ? COLOR.LIGHT_BLUE_900 : COLOR.LIGHT_GRAY_400}
            height="36px"
          />
        </Styled.SessionButtonWrapper>
      ))}
    </Styled.Root>
  );
};

export default SessionList;
