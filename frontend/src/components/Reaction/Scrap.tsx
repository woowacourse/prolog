import { MouseEventHandler } from 'react';
import { Button, BUTTON_SIZE } from '..';

import scrappedIcon from '../../assets/images/scrap_filled.svg';
import unScrapIcon from '../../assets/images/scrap.svg';
import { DefaultScrapButtonStyle } from './Scrap.styles';
import { SerializedStyles } from '@emotion/react';

interface Props {
  scrap: boolean;
  onClick: MouseEventHandler<HTMLButtonElement>;
  cssProps?: SerializedStyles;
}

const Scrap = ({ scrap, onClick, cssProps }: Props) => {
  const scrapIcon = scrap ? scrappedIcon : unScrapIcon;
  const scrapIconAlt = scrap ? '스크랩 취소' : '스크랩';

  return (
    <Button
      type="button"
      size={BUTTON_SIZE.X_SMALL}
      icon={scrapIcon}
      alt={scrapIconAlt}
      cssProps={cssProps ?? DefaultScrapButtonStyle}
      onClick={onClick}
    />
  );
};

export default Scrap;
