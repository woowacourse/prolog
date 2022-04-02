import { Chip } from '../../components';
import { COLOR } from '../../constants';
import * as Styled from './ReportStudyLogs.styles';

const ReportStudyLogs = () => {
  /** 선택된 역량을 보여준다.*/
  const selectedAbilities = (abilities) => {
    return abilities?.map((ability) => {
      return (
        <li key={ability.id}>
          <Chip
            backgroundColor={ability.color}
            border={`1px solid ${COLOR.BLACK_OPACITY_300}`}
            fontSize="1.2rem"
            lineHeight="1.5"
          >
            {ability.name}
          </Chip>
        </li>
      );
    });
  };

  return (
    <table>
      <Styled.Thead>
        <Styled.TableRow>
          <th scope="col">제목</th>
          <th scope="col">역량</th>
        </Styled.TableRow>
      </Styled.Thead>

      <Styled.Tbody>
        <Styled.TableRow>
          <Styled.StudyLogTitle>
            <a href={`/studylogs/1`} target="_blank" rel="noopener noreferrer">
              학습로그 제목입니다.
            </a>
          </Styled.StudyLogTitle>

          <Styled.MappedAbility>
            <ul id="mapped-abilities-list">{selectedAbilities([])}</ul>
          </Styled.MappedAbility>
        </Styled.TableRow>
      </Styled.Tbody>

      {/* {currStudyLogs?.length === 0 && (
    <Styled.EmptyTableGuide>등록된 학습로그가 없습니다.</Styled.EmptyTableGuide>
  )} */}
    </table>
  );
};

export default ReportStudyLogs;
