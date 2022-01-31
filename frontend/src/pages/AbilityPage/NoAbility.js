import useMutation from '../../hooks/useMutation';
import useSnackBar from '../../hooks/useSnackBar';
import { SUCCESS_MESSAGE, CONFIRM_MESSAGE } from '../../constants/message';
import { requestSetDefaultAbility } from '../../service/requests';
import { BeButton, FeButton, NoAbilityContainer, AnotherWay } from './styles';

const NoAbility = ({ getData, accessToken }) => {
  const { openSnackBar } = useSnackBar();

  const { mutate: addDefaultAbilities } = useMutation(
    (field) => requestSetDefaultAbility(JSON.parse(accessToken), field),
    {
      onSuccess: () => {
        openSnackBar(SUCCESS_MESSAGE.SET_DEFAULT_ABILITIES);
        getData();
      },
      onError: () => {
        openSnackBar(CONFIRM_MESSAGE.SET_DEFAULT_ABILITIES);
      },
    }
  );

  return (
    <NoAbilityContainer>
      <div>
        <h3>존재하는 역량이 없으니, 자네가 원하는 역량을 골라보게</h3>
        <span style={{ fontWeight: 'bold', fontSize: '2.4rem' }}>어느 분야를 고르겠나?</span>
        <AnotherWay>(제 3의 길 ↑)</AnotherWay>
      </div>
      <div
        style={{
          display: 'flex',
          justifyContent: 'center',
          position: 'absolute',
          width: '100%',
          bottom: '10rem',
          left: 0,
        }}
      >
        <FeButton
          type="button"
          onClick={() => {
            if (window.confirm('프론트엔드를 선택하겠나?')) {
              addDefaultAbilities('fe');
            }
          }}
        >
          <span>프론트엔드</span>
        </FeButton>
        <BeButton
          type="button"
          onClick={() => {
            if (window.confirm('백엔드를 선택하겠나?')) {
              addDefaultAbilities('be');
            }
          }}
        >
          <span>백엔드</span>
        </BeButton>
      </div>
    </NoAbilityContainer>
  );
};

export default NoAbility;
