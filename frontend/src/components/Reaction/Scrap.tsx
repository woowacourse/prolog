import { MouseEventHandler } from 'react';
import { Button, BUTTON_SIZE } from '..';

import { ReactComponent as ScrapIcon } from '../../assets/images/scrap_filled.svg';
import { ReactComponent as UnScrapIcon } from '../../assets/images/scrap.svg';
import { DefaultScrapButtonStyle } from './Scrap.styles';
import { SerializedStyles } from '@emotion/react';

interface Props {
  scrap: boolean;
  onClick: MouseEventHandler<HTMLButtonElement>;
  cssProps?: SerializedStyles;
}

const Scrap = ({ scrap, onClick, cssProps }: Props) => {
  const scrapIconAlt = scrap ? '스크랩 취소' : '스크랩';

  return (
    <Button
      type="button"
      alt={scrapIconAlt}
      cssProps={cssProps ?? DefaultScrapButtonStyle}
      onClick={onClick}
    >
      {scrap ? (
        <ScrapIcon width="2rem" height="2rem" />
      ) : (
        <UnScrapIcon width="2rem" height="2rem" />
      )}
    </Button>
  );
};

export default Scrap;
