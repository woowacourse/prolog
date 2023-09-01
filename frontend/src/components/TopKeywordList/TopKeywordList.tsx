import styled from '@emotion/styled';
import { useEffect } from 'react';
import { COLOR } from '../../enumerations/color';
import { useGetTopKeywordList } from '../../hooks/queries/keywords';
import { KeywordResponse } from '../../models/Keywords';
import ResponsiveButton from '../Button/ResponsiveButton';

interface TopKeywordListProps {
  sessionId: number;
  selectedTopKeyword: KeywordResponse | null;
  handleClickTopKeyword: (keyword: KeywordResponse) => void;
}

const compareFn = (a: KeywordResponse, b: KeywordResponse) => a.order - b.order;

const TopKeywordList = ({
  sessionId,
  selectedTopKeyword,
  handleClickTopKeyword,
}: TopKeywordListProps) => {
  const { topKeywordList } = useGetTopKeywordList(sessionId);

  useEffect(() => {
    if (topKeywordList) {
      handleClickTopKeyword(topKeywordList[0]);
    }
  }, [topKeywordList]);

  return (
    <StyledRoot>
      {topKeywordList?.sort(compareFn).map((keyword, index) => {
        const isNotFirst = index > 0;
        const isSelected = selectedTopKeyword?.keywordId === keyword.keywordId;

        return (
          <StyledWrapper key={keyword.keywordId}>
            {isNotFirst && <StyledArrow />}
            <ResponsiveButton
              text={keyword.name}
              backgroundColor={isSelected ? COLOR.LIGHT_BLUE_900 : COLOR.LIGHT_GRAY_400}
              onClick={() => handleClickTopKeyword(keyword)}
            />
          </StyledWrapper>
        );
      })}
    </StyledRoot>
  );
};

export default TopKeywordList;

const StyledRoot = styled.div`
  display: flex;
  flex-wrap: wrap;
  row-gap: 12px;
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
