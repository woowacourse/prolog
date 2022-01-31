import { PaginationContainer, PageButtonStyle, PageSkipButtonStyle } from './Pagination.styles';
import { Button, BUTTON_SIZE, PageButton } from '../index';

const Pagination = ({ dataInfo, onSetPage }) => {
  const basePage = Math.floor((dataInfo.currPage - 1) / 5) * 5 + 1;
  const paginationArray = Array.from({
    length: dataInfo?.totalPage - basePage + 1 > 5 ? 5 : dataInfo?.totalPage - basePage + 1,
  }).map((_, index) => index);
  const isEnableMovePrevSection = basePage - 5 > 0;
  const isEnableMoveNextSection = basePage + 5 <= dataInfo.totalPage;

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
        type="button"
      >
        {'<'}
      </Button>
      {paginationArray.map((index) => {
        return (
          <PageButton
            key={index}
            size={BUTTON_SIZE.XX_SMALL}
            css={PageButtonStyle}
            selected={basePage + index === dataInfo.currPage}
            onClick={movePage(basePage + index)}
            type="button"
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
        type="button"
      >
        {'>'}
      </Button>
    </PaginationContainer>
  );
};

export default Pagination;
