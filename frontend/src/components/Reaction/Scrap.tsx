import { MouseEventHandler } from 'react';
import { Button, BUTTON_SIZE } from '..';

import scrappedIcon from '../../assets/images/scrap_filled.svg';
import unScrapIcon from '../../assets/images/scrap.svg';
import { ScrapButtonStyle } from './Scrap.styles';

interface Props {
  scrap: boolean;
  scrapedCount: number;
  onClick: MouseEventHandler<HTMLButtonElement>;
}

const Scrap = ({ scrap, scrapedCount = 0, onClick }: Props) => {
  const scrapIcon = scrap ? scrappedIcon : unScrapIcon;
  const scrapIconAlt = scrap ? '스크랩 취소' : '스크랩';

  return (
    <Button
      type="button"
      size={BUTTON_SIZE.X_SMALL}
      icon={scrapIcon}
      alt={scrapIconAlt}
      cssProps={ScrapButtonStyle}
      onClick={onClick}
    >
      {scrapedCount}
    </Button>
  );
};

export default Scrap;
