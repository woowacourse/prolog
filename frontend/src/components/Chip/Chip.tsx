import { ReactNode } from 'react';
import PropTypes from 'prop-types';

import CancelIcon from '../@shared/Icons/CancelIcon';
import COLOR from '../../constants/color';

import { ChipText, Container } from './Chip.styles';

import type { ContainerProps, ChipTextProps } from './Chip.styles';

type ComponentProps = {
  /**
   * @description 내용이 너비를 벗어나는 경우 hover 시에 전체 내용을 확인하기 위한 title
   * TODO: 추후 css 요소로 변경 / 스크린 리더로 확인 시 두번 읽히는 이슈 있음.
   */
  title: string;
  onClick?: () => void;
  onDelete?: () => void;
  children: ReactNode;
};

type ChipProps = ContainerProps & ChipTextProps & ComponentProps;

const Chip = ({
  title,
  maxWidth,
  textAlign,
  width,
  backgroundColor,
  fontSize,
  onClick,
  onDelete,
  children,
}: ChipProps): JSX.Element => {
  return (
    <Container
      title={title}
      maxWidth={maxWidth}
      width={width}
      backgroundColor={backgroundColor}
      onClick={onClick}
    >
      <ChipText textAlign={textAlign} fontSize={fontSize}>
        {children}
      </ChipText>
      {onDelete && (
        <button type="button" onClick={onDelete}>
          <CancelIcon width="10px" height="10px" strokeWidth="2px" stroke={COLOR.BLACK_900} />
        </button>
      )}
    </Container>
  );
};

Chip.propTypes = {
  title: PropTypes.string,
  maxWidth: PropTypes.string,
  textAlign: PropTypes.string,
  width: PropTypes.string,
  color: PropTypes.string,
  backgroundColor: PropTypes.string,
  fontSize: PropTypes.string,
  onDelete: PropTypes.func,
  children: PropTypes.node,
};

export default Chip;
