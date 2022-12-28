import ResponsiveButton from '../Button/ResponsiveButton';
import COLOR from '../../constants/color';
import * as Styled from './CurriculumList.styles';
import { useGetCurriculums } from '../../hooks/queries/curriculum';

interface CurriculumListProps {
  selectedCurriculumId: number;
  handleClickCurriculum: (curriculumId: number) => void;
}

const CurriculumList = ({ selectedCurriculumId, handleClickCurriculum }: CurriculumListProps) => {
  const { curriculums } = useGetCurriculums();

  return (
    <Styled.Root>
      {curriculums?.map(({ curriculumId, name }) => (
        <Styled.SessionButtonWrapper key={curriculumId}>
          <ResponsiveButton
            onClick={() => handleClickCurriculum(curriculumId)}
            text={name}
            color={selectedCurriculumId === curriculumId ? COLOR.WHITE : COLOR.BLACK_600}
            backgroundColor={
              selectedCurriculumId === curriculumId ? COLOR.LIGHT_BLUE_900 : COLOR.LIGHT_GRAY_400
            }
            height="36px"
          />
        </Styled.SessionButtonWrapper>
      ))}
    </Styled.Root>
  );
};

export default CurriculumList;
