import PropTypes from 'prop-types';
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
  fontSize,
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
