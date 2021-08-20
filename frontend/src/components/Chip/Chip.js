import CancelIcon from '../@shared/Icons/CancelIcon';
import { Container } from './Chip.styles';
import COLOR from '../../constants/color';

const Chip = ({ color, backgroundColor, onDelete, children }) => {
  return (
    <Container color={color} backgroundColor={backgroundColor}>
      <span>{children}</span>
      {onDelete && (
        <button type="button" onClick={onDelete}>
          <CancelIcon width="10px" height="10px" strokeWidth="2px" stroke={COLOR.BLACK_900} />
        </button>
      )}
    </Container>
  );
};

export default Chip;
