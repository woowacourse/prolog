import { useEffect } from 'react';
import useSnackBar from '../../hooks/useSnackBar';
import AbilityListItem from '../AbilityPage/AbilityListItem';
import { AbilityList, ListHeader } from '../AbilityPage/styles';
import { DeleteButton, Title } from './styles';

const mockAbility = [
  {
    id: 1,
    name: '상위 역량 이름1',
    description: '상위 역량 설명1',
    color: '#001122',
    isParent: true,
    children: [
      {
        id: 3,
        name: '하위 역량 이름1',
        description: '하위 역량 설명1',
        color: '#001122',
        isParent: false,
      },
    ],
  },
  {
    id: 2,
    name: '상위 역량 이름2',
    description: '상위 역량 설명2',
    color: '#ffffff',
    isParent: true,
    children: [
      {
        id: 4,
        name: '하위 역량 이름2',
        description: '하위 역량 설명2',
        color: '#ffffff',
        isParent: false,
      },
    ],
  },
  {
    id: 3,
    name: '하위 역량 이름1',
    description: '하위 역량 설명1',
    color: '#001122',
    isParent: false,
    children: [],
  },
  {
    id: 4,
    name: '하위 역량 이름2',
    description: '하위 역량 설명2',
    color: '#ffffff',
    isParent: false,
    children: [],
  },
];

const AbilityHistoryPage = () => {
  const { isSnackBarOpen, SnackBar, openSnackBar } = useSnackBar();

  return (
    <>
      <Title>2022.01.06 역량</Title>

      <AbilityList>
        <ListHeader>
          <div>
            역량<span>{`(총 ${mockAbility?.length ?? 0}개)`}</span>
          </div>
        </ListHeader>

        {mockAbility
          ?.filter(({ isParent }) => isParent)
          .map((ability) => (
            <AbilityListItem key={ability.id} ability={ability} readOnly={true} />
          ))}
      </AbilityList>

      <DeleteButton>역량 이력 삭제</DeleteButton>

      {isSnackBarOpen && <SnackBar />}
    </>
  );
};

export default AbilityHistoryPage;
