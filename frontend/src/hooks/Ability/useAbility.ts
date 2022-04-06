import { useMutation, useQuery, useQueryClient } from 'react-query';
import AbilityRequest, { ErrorData } from '../../apis/ability';
import { ERROR_MESSAGE } from '../../constants';
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
  const { data: abilities = [], isLoading } = useQuery(
    [`${username}-abilities`],
    () => AbilityRequest.getAbilityList({ url: `/members/${username}/abilities` }),
    {
      onError: (errorData: ErrorData) => {
        const errorCode = errorData?.code;

        alert(ERROR_MESSAGE[errorCode] ?? '역량을 가져오는데 실패하였습니다. 다시 시도해주세요.');
      },
    }
  );

  /** 역량 등록 */
  const onAddAbility = useMutation(
    (ability) => AbilityRequest.addAbility({ url: `/abilities`, data: ability }),
    {
      // TODO: 스크롤을 해당 위치로 보내는 기능 고려하기
      onSuccess: () => {
        setAddFormStatus({ ...DEFAULT_ABILITY_FORM, isOpened: false });
        queryClient.invalidateQueries([`${username}-abilities`]);
      },
      // TODO: 실패했을 때 역량 폼을 유지하는 방법 고려하기 - 지금 오락가락함
      onError: (errorData: ErrorData) => {
        const errorCode = errorData?.code;

        alert(ERROR_MESSAGE[errorCode] ?? '역량 등록에 실패하였습니다.');
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
      onError: (errorData: ErrorData) => {
        const errorCode = errorData?.code;

        alert(ERROR_MESSAGE[errorCode] ?? '역량 삭제에 실패하였습니다. 잠시후 다시 시도해주세요.');
      },
    }
  );

  return { abilities, isLoading, onAddAbility, onDeleteAbility };
};

export default useAbility;
