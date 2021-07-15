import React from 'react';
import { PaginationContainer, PageButtonStyle, PageSkipButtonStyle } from './Pagination.styles';
import { Button, BUTTON_SIZE, PageButton } from '../index';

const Pagination = ({ postsInfo, onSetPage }) => {
  const basePage = Math.floor((postsInfo.currPage - 1) / 5) * 5 + 1;
  const paginationArray = Array.from({
    length: postsInfo?.totalPage - basePage + 1 > 5 ? 5 : postsInfo?.totalPage - basePage + 1,
  }).map((_, index) => index);
  const isEnableMovePrevSection = basePage - 5 > 0;
  const isEnableMoveNextSection = basePage + 5 <= postsInfo.totalPage;

  const movePrevSection = () => {
    if (isEnableMovePrevSection) {
      onSetPage(basePage - 5);
    }
  };

  const moveNextSection = () => {
    if (isEnableMoveNextSection) {
      onSetPage(basePage + 5);
    }
  };

  const movePage = (page) => () => {
    onSetPage(page);
  };

  return (
    <PaginationContainer>
      <Button
        size={BUTTON_SIZE.XX_SMALL}
        css={PageSkipButtonStyle}
        onClick={movePrevSection}
        disabled={!isEnableMovePrevSection}
      >
        {'<'}
      </Button>
      {paginationArray.map((index) => {
        return (
          <PageButton
            size={BUTTON_SIZE.XX_SMALL}
            css={PageButtonStyle}
            selected={basePage + index === postsInfo.currPage}
            onClick={movePage(basePage + index)}
          >
            {basePage + index}
          </PageButton>
        );
      })}
      <Button
        size={BUTTON_SIZE.XX_SMALL}
        css={PageSkipButtonStyle}
        onClick={moveNextSection}
        disabled={!isEnableMoveNextSection}
      >
        {'>'}
      </Button>
    </PaginationContainer>
  );
};

export default Pagination;
