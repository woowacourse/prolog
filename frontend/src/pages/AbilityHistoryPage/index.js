import { useEffect } from 'react';
import { useSelector } from 'react-redux';
import { useHistory, useParams } from 'react-router-dom';
import useSnackBar from '../../hooks/useSnackBar';
import { requestDeleteAbilityHistory } from '../../service/requests';
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
  const history = useHistory();
  const { username, id: historyId } = useParams();

  const { data: userData } = useSelector((state) => state.user.profile);
  const { data: accessToken } = useSelector((state) => state.user.accessToken);

  const { isSnackBarOpen, SnackBar, openSnackBar } = useSnackBar();

  // 역량 이력 삭제 메서드
  const onDeleteAbilityHistory = async () => {
    const isConfirm = window.confirm(
      '정말로 이력을 삭제하시겠습니까? 삭제한 후에는 복구가 어렵습니다.'
    );

    // if (!isConfirm) return;
    // else {
    //   try {
    //     const response = await requestDeleteAbilityHistory(accessToken, historyId);

    //     if (!response.ok) {
    //       throw new Error(await response.text());
    //     }

    //     history.push('/');
    //   } catch (error) {
    //     const errorResponse = JSON.parse(error.message);

    //     console.error(errorResponse);
    //   }
    // }
  };

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

      {username === userData?.username && (
        <DeleteButton onClick={onDeleteAbilityHistory}>역량 이력 삭제</DeleteButton>
      )}

      {isSnackBarOpen && <SnackBar />}
    </>
  );
};

export default AbilityHistoryPage;
