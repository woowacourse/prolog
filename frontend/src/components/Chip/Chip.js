import CancelIcon from '../@shared/Icons/CancelIcon';
import { ChipText, Container } from './Chip.styles';
import COLOR from '../../constants/color';

const Chip = ({
  title,
  maxWidth,
  textAlign,
  width,
  color,
  backgroundColor,
  onDelete,
  children,
}) => {
  return (
    <Container
      title={title}
      maxWidth={maxWidth}
      width={width}
      color={color}
      backgroundColor={backgroundColor}
    >
      <ChipText textAlign={textAlign}>{children}</ChipText>
      {onDelete && (
        <button type="button" onClick={onDelete}>
          <CancelIcon width="10px" height="10px" strokeWidth="2px" stroke={COLOR.BLACK_900} />
        </button>
      )}
    </Container>
  );
};

export default Chip;
