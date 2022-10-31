import styled from '@emotion/styled';
import { useEffect } from 'react';
import SomeImage from '../../assets/images/background-image.png';
import { COLOR } from '../../enumerations/color';
import { useGetTopKeywordList } from '../../hooks/queries/keywords';
import { KeywordResponse } from '../../models/Keywords';
import LabelledImage from '../LabelledImage/LabelledImage';

interface TopKeywordListProps {
  sessionId: number;
  selectedTopKeyword: KeywordResponse | null;
  handleClickTopKeyword: (keyword: KeywordResponse) => void;
  updateSelectedTopKeyword: (keyword: KeywordResponse) => void;
}

const TopKeywordList = ({
  sessionId,
  selectedTopKeyword,
  handleClickTopKeyword,
  updateSelectedTopKeyword,
}: TopKeywordListProps) => {
  const { topKeywordList } = useGetTopKeywordList(sessionId);

  useEffect(() => {
    if (topKeywordList) {
      updateSelectedTopKeyword(topKeywordList[0]);
    }
  }, [topKeywordList]);

  return (
    <StyledRoot>
      {topKeywordList?.map((keyword, index) => {
        const isNotLast = index + 1 !== topKeywordList?.length;

        return (
          <StyledWrapper>
            <LabelledImage
              src={SomeImage}
              alt=""
              text={keyword.name}
              isSelected={selectedTopKeyword?.keywordId === keyword.keywordId}
              onClick={() => handleClickTopKeyword(keyword)}
            />
            {isNotLast && <StyledArrow />}
          </StyledWrapper>
        );
      })}
    </StyledRoot>
  );
};

export default TopKeywordList;

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
