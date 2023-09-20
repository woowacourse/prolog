import ResponsiveButton from '../Button/ResponsiveButton';
import COLOR from '../../constants/color';
import * as Styled from './CurriculumList.styles';
import { CurriculumResponse } from '../../models/Keywords';

interface CurriculumListProps {
  selectedCurriculumId: number;
  curriculums: CurriculumResponse[];
  onCurriculumClick: (curriculumId: number) => void;
}

const CurriculumList = (props: CurriculumListProps) => {
  const { curriculums, selectedCurriculumId, onCurriculumClick } = props;

  return (
    <Styled.Root>
      {curriculums?.map(({ id, name }) => {
        return (
          <Styled.SessionButtonWrapper key={id}>
            <ResponsiveButton
              onClick={() => onCurriculumClick(id)}
              text={name}
              color={selectedCurriculumId === id ? COLOR.WHITE : COLOR.BLACK_600}
              backgroundColor={
                selectedCurriculumId === id ? COLOR.LIGHT_BLUE_900 : COLOR.LIGHT_GRAY_400
              }
              height="36px"
            />
          </Styled.SessionButtonWrapper>
        );
      })}
    </Styled.Root>
  );
};

export default CurriculumList;
