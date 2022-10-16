import styled from '@emotion/styled';
import React from 'react';
import SomeImage from '../../assets/images/background-image.png';
import { COLOR } from '../../enumerations/color';
import { useGetTopKeywordList } from '../../hooks/queries/keywords';
import { KeywordResponse } from '../../models/Keywords';
import LabelledImage from '../LabelledImage/LabelledImage';

interface KeywordSectionProps {
  sessionId: number;
  selectedTopKeyword: KeywordResponse | null;
  handleClickTopKeyword: (keyword: KeywordResponse) => void;
  updateSelectedTopKeyword: (keyword: KeywordResponse) => void;
}

const KeywordSection = ({
  sessionId,
  selectedTopKeyword,
  handleClickTopKeyword,
  updateSelectedTopKeyword,
}: KeywordSectionProps) => {
  const { topKeywordList } = useGetTopKeywordList(sessionId, {
    onSuccessCallback(data) {
      updateSelectedTopKeyword(data.data[0]); // 초기값 설정
    },
  });

  return (
    <StyledRoot>
      {topKeywordList?.map((keyword, index) => (
        <StyledWrapper>
          <LabelledImage
            src={SomeImage}
            alt=""
            text={keyword.name}
            isSelected={selectedTopKeyword?.keywordId === keyword.keywordId}
            onClick={() => handleClickTopKeyword(keyword)}
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
