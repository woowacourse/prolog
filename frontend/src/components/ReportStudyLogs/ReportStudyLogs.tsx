import { Chip } from '../../components';
import { COLOR } from '../../constants';
import * as Styled from './ReportStudyLogs.styles';

const ReportStudyLogs = ({ studylogs }) => {
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
    <>
      <Styled.Title>📝 학습로그</Styled.Title>

      <table>
        <Styled.Thead>
          <Styled.TableRow>
            <th scope="col">제목</th>
            <th scope="col">역량</th>
          </Styled.TableRow>
        </Styled.Thead>

        <Styled.Tbody>
          {studylogs?.map(({ studylog, studylogAbilities }) => (
            <Styled.TableRow key={studylog.id}>
              <Styled.StudyLogTitle>
                <a href={`/studylogs/1`} target="_blank" rel="noopener noreferrer">
                  {studylog.title}
                </a>
              </Styled.StudyLogTitle>

              <Styled.MappedAbility>
                <ul id="mapped-abilities-list">{selectedAbilities(studylogAbilities)}</ul>
              </Styled.MappedAbility>
            </Styled.TableRow>
          ))}
        </Styled.Tbody>
      </table>
    </>
  );
};

export default ReportStudyLogs;
