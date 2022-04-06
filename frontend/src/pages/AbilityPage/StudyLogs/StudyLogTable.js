import { useContext, useEffect, useRef, useState } from 'react';
import { useMutation } from 'react-query';
import axios from 'axios';

import { BASE_URL } from '../../../configs/environment';
import { UserContext } from '../../../contexts/UserProvider';
import { Button, Chip, Pagination } from '../../../components';
import * as Styled from './StudyLogTable.styles.js';
import { COLOR } from '../../../constants';
import SelectAbilityBox from './SelectAbilityBox';

// TODO. 각 레벨별로 학습로그를 볼 수 있는 기능을 추가한다.
// TODO. 매핑된 역량은 삭제할 수 없다는 예외사항을 추가한다.
// TODO. prefetch 기능을 사용한다. (20개 정도는 미리 가져와도 될듯..?)

const StudyLogTable = ({ studylogs, abilities, setPage, readOnly, totalSize, refetch }) => {
  const { user } = useContext(UserContext);

  /** 역량 선택은 자식 역량만 선택할 수 있다. */
  const wholeAbility = abilities?.map((parentAbility) => [...parentAbility.children]).flat();

  const selectAbilityBoxRef = useRef(null);
  const [selectAbilityBox, setSelectAbilityBox] = useState({
    id: 0,
    isOpen: false,
  });

  /** 역량 목록 열기 */
  const onOpenAbilityBox = (event, id) => {
    event.stopPropagation();

    setSelectAbilityBox({ id, isOpen: true });
  };

  /** 역량 선택 닫기 */
  useEffect(() => {
    const onCloseOptionList = (event) => {
      if (!selectAbilityBox) return;

      if (!selectAbilityBoxRef.current?.contains(event.target)) {
        setSelectAbilityBox({ ...selectAbilityBox, isOpen: false });
      }
    };

    document.addEventListener('click', onCloseOptionList);

    return () => {
      document.removeEventListener('click', onCloseOptionList);
    };
  }, [selectAbilityBox, selectAbilityBoxRef]);

  const mappingAbility = useMutation(
    ({ studylogId, abilities }) => {
      const { data } = axios({
        method: 'put',
        url: `${BASE_URL}/studylogs/${studylogId}/abilities`,
        headers: {
          Authorization: `Bearer ${user.accessToken}`,
        },
        data: {
          abilities,
        },
      });

      return { ...data };
    },
    {
      onSuccess: () => {
        refetch();
      },
    }
  );

  const toggleAbility = ({ studylogId, abilityIds, targetAblityId }) => {
    const targetIndex = abilityIds.findIndex((id) => id === targetAblityId);
    if (targetIndex === -1) {
      alert('삭제할 수 없는 역량입니다.');
    }

    const updatedAbilities = [
      ...abilityIds.slice(0, targetIndex),
      ...abilityIds.slice(targetIndex + 1),
    ];

    mappingAbility.mutate({ studylogId, abilities: updatedAbilities });
  };

  /** 선택된 역량을 보여준다.*/
  const selectedAbilities = (studylogId, abilities) => {
    return abilities?.map((ability) => {
      return (
        <li key={ability.id}>
          {readOnly ? (
            <Chip
              backgroundColor={ability.color}
              border={`1px solid ${COLOR.BLACK_OPACITY_300}`}
              fontSize="1.2rem"
              lineHeight="1.5"
            >
              {ability.name}
            </Chip>
          ) : (
            <Chip
              backgroundColor={ability.color}
              border={`1px solid ${COLOR.BLACK_OPACITY_300}`}
              fontSize="1.2rem"
              lineHeight="1.5"
              onDelete={() => {
                toggleAbility({
                  studylogId,
                  abilityIds: abilities.map((ability) => ability.id),
                  targetAblityId: ability.id,
                });
              }}
            >
              {ability.name}
            </Chip>
          )}
        </li>
      );
    });
  };

  return (
    <>
      <Styled.Section>
        <h3 id="studylog-table-title">📝 학습로그 목록</h3>
        <span id="studylogs-count">(총 {totalSize}개)</span>

        <table>
          <Styled.Thead>
            <Styled.TableRow>
              <th scope="col">제목</th>
              <th scope="col">역량</th>
            </Styled.TableRow>
          </Styled.Thead>

          <Styled.Tbody>
            {studylogs?.data?.map(({ studylog, abilities }) => (
              <Styled.TableRow key={studylog.id}>
                <Styled.StudyLogTitle
                  isSelected={selectAbilityBox.id === studylog.id && selectAbilityBox.isOpen}
                >
                  <a href={`/studylogs/${studylog.id}`} target="_blank" rel="noopener noreferrer">
                    {studylog.title}
                  </a>
                </Styled.StudyLogTitle>

                <Styled.MappedAbility>
                  <ul id="mapped-abilities-list">{selectedAbilities(studylog.id, abilities)}</ul>

                  {!readOnly && (
                    <>
                      <Button
                        id="add-ability-button"
                        size="XX_SMALL"
                        type="button"
                        css={{ backgroundColor: `${COLOR.LIGHT_BLUE_300}` }}
                        onClick={(event) => onOpenAbilityBox(event, studylog.id)}
                      >
                        +
                      </Button>

                      {selectAbilityBox.id === studylog.id && selectAbilityBox.isOpen && (
                        <SelectAbilityBox
                          selectAbilityBoxRef={selectAbilityBoxRef}
                          mappingAbility={mappingAbility}
                          studylogId={studylog.id}
                          abilities={abilities}
                          wholeAbility={wholeAbility}
                          setSelectAbilityBox={setSelectAbilityBox}
                        />
                      )}
                    </>
                  )}
                </Styled.MappedAbility>
              </Styled.TableRow>
            ))}
          </Styled.Tbody>
          {totalSize === 0 && (
            <Styled.EmptyTableGuide>등록된 학습로그가 없습니다.</Styled.EmptyTableGuide>
          )}
        </table>
        <Pagination dataInfo={studylogs} onSetPage={setPage} />
      </Styled.Section>
    </>
  );
};

export default StudyLogTable;
