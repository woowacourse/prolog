import { useMutation, useQuery, useQueryClient } from 'react-query';
import AbilityRequest from '../../apis/ability';
import { DEFAULT_ABILITY_FORM } from './useParentAbilityForm';

interface AbilityForm {
  isOpened: boolean;
  name: string;
  description: string;
  color: string;
  isParent: boolean | null;
}

interface Props {
  username: string;
  setAddFormStatus: (data: AbilityForm) => void;
  addFormClose: () => void;
}

const useAbility = ({ username, setAddFormStatus, addFormClose }: Props) => {
  const queryClient = useQueryClient();

  /** 전체 역량 조회 */
  const { data: abilities = [] } = useQuery([`${username}-abilities`], () =>
    AbilityRequest.getAbilityList({ url: `/members/${username}/abilities` })
  );

  /** 역량 등록 */
  const onAddAbility = useMutation(
    (ability) => AbilityRequest.addAbility({ url: `/abilities`, data: ability }),
    {
      onSuccess: () => {
        // TODO: 스크롤을 해당 위치로 보내는 기능 고려하기
        setAddFormStatus({ ...DEFAULT_ABILITY_FORM, isOpened: false });
        queryClient.invalidateQueries([`${username}-abilities`]);
      },
      onError: () => {
        alert('역량 등록에 실패하였습니다.');
      },
    }
  );

  /** 역량 삭제 */
  const onDeleteAbility = useMutation(
    (id) => AbilityRequest.deleteAbility({ url: `/abilities/${id}` }),
    {
      onSuccess: () => {
        addFormClose();
        queryClient.invalidateQueries([`${username}-abilities`]);
      },
      onError: () => {
        alert('역량 삭제에 실패하였습니다. 잠시후 다시 시도해주세요.');
      },
    }
  );

  return { abilities, onAddAbility, onDeleteAbility };
};

export default useAbility;
