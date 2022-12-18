/** @jsxImportSource @emotion/react */

import { useEffect } from 'react';
import ResponsiveButton from '../../components/Button/ResponsiveButton';
import { useGetChildrenKeywordList } from '../../hooks/queries/keywords';
import { KeywordResponse } from '../../models/Keywords';
import * as Styled from './KeywordList.styles';

interface KeywordListProps {
  handleClickKeyword: (keyword: KeywordResponse) => void;
  selectedTopKeyword: KeywordResponse;
  sessionId: number;
}

const KeywordList = ({ handleClickKeyword, selectedTopKeyword, sessionId }: KeywordListProps) => {
  const { childrenKeywordList, refetchChildrenKeywordList } = useGetChildrenKeywordList({
    sessionId,
    keywordId: selectedTopKeyword?.keywordId || 1,
  });

  useEffect(() => {
    refetchChildrenKeywordList();
  }, [selectedTopKeyword?.keywordId]);

  return (
    <Styled.Root>
      <ResponsiveButton
        css={Styled.TopKeywordButton}
        text={selectedTopKeyword?.name}
        onClick={() => handleClickKeyword(selectedTopKeyword)}
      />

      <Styled.ChildrenKeywordListContainer>
        {childrenKeywordList?.map((keyword) => (
          <Styled.ContentsWrapper key={keyword.keywordId}>
            <Styled.SecondButtonWrapper>
              <ResponsiveButton
                css={Styled.SecondKeywordButton}
                text={keyword.name}
                onClick={() => handleClickKeyword(keyword)}
              />
            </Styled.SecondButtonWrapper>

            <Styled.ThirdButtonContainer>
              {keyword.childrenKeywords?.map((keyword) => (
                <ResponsiveButton
                  key={keyword.keywordId}
                  css={Styled.ThirdKeywordButton}
                  text={keyword.name}
                  onClick={() => handleClickKeyword(keyword)}
                />
              ))}
            </Styled.ThirdButtonContainer>
          </Styled.ContentsWrapper>
        ))}
      </Styled.ChildrenKeywordListContainer>
    </Styled.Root>
  );
};

export default KeywordList;
