import styled from '@emotion/styled';
import COLOR from '../../constants/color';

const Card = styled.div`
  padding: 2.5rem;
  background-color: ${COLOR.WHITE};
  border-radius: 8px;
  border: 1px solid ${COLOR.LIGHT_GRAY_50};
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
`;

const SectionName = styled.h3`
  margin-top: 1rem;
  margin-bottom: 2rem;
  font-weight: 600;
  font-size: 1.5rem;
  color: ${COLOR.DARK_GRAY_600};
`;

export { Card, SectionName };
