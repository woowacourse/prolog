import CancelIcon from '../@shared/Icons/CancelIcon';
import { Container } from './Chip.styles';

const Chip = ({ color, backgroundColor, onDelete, children }) => {
  return (
    <Container color={color} backgroundColor={backgroundColor}>
      <span>{children}</span>
      {onDelete && (
        <button type="button" onClick={onDelete}>
          <CancelIcon width="10px" height="10px" strokeWidth="2px" stroke="black" />
        </button>
      )}
    </Container>
  );
};

export default Chip;
