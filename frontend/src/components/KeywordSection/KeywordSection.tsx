import styled from '@emotion/styled';
import React, { useState } from 'react';
import SomeImage from '../../assets/images/background-image.png';
import { COLOR } from '../../enumerations/color';
import { useGetTopKeywordList } from '../../hooks/queries/keywords';
import LabelledImage from '../LabelledImage/LabelledImage';

interface KeywordSectionProps {
  sessionId: number;
}

const KeywordSection = ({ sessionId }: KeywordSectionProps) => {
  const [selectedKeywordId, setSelectedKeywordId] = useState(1);
  const { topKeywordList } = useGetTopKeywordList(sessionId);

  const handleClickKeyword = (keywordId: number) => {
    setSelectedKeywordId(keywordId);
  };

  return (
    <StyledRoot>
      {topKeywordList?.map(({ name, keywordId }, index) => (
        <StyledWrapper>
          <LabelledImage
            src={SomeImage}
            alt=""
            text={name}
            isSelected={selectedKeywordId === keywordId}
            onClick={() => handleClickKeyword(keywordId)}
          />
          {index + 1 !== topKeywordList?.length && <StyledArrow />}
        </StyledWrapper>
      ))}
    </StyledRoot>
  );
};

export default KeywordSection;

const StyledRoot = styled.div`
  display: flex;
`;

const StyledArrow = styled.div`
  width: 0;
  height: 0;
  border: 9px solid transparent;
  border-top: 0;
  border-bottom: 15px solid ${COLOR.LIGHT_BLUE_700};
  transform: rotate(90deg);
  margin: 0 12px;
`;

const StyledWrapper = styled.div`
  display: flex;
  align-items: center;
`;
