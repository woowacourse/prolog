import ResponsiveButton from '../Button/ResponsiveButton';
import COLOR from '../../constants/color';
import * as Styled from './CurriculumList.styles';
import { useGetCurriculums } from '../../hooks/queries/curriculum';

interface CurriculumListProps {
  selectedCurriculumId: number;
  handleClickCurriculum: (curriculumId: number) => void;
}

const CurriculumList = ({ selectedCurriculumId, handleClickCurriculum }: CurriculumListProps) => {
  const { isLoading, curriculums } = useGetCurriculums();
  if (isLoading) {
    return null;
  }

  return (
    <Styled.Root>
      {curriculums?.map(({ id, name }) => {
        return (
          <Styled.SessionButtonWrapper key={id}>
            <ResponsiveButton
              onClick={() => handleClickCurriculum(id)}
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
