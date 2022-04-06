// import { NoAbilityContainer, BeButton, FeButton, AnotherWay } from './EmptyAbility.styles';
import axios from 'axios';
import { useMutation, useQueryClient } from 'react-query';

import { BASE_URL } from '../../../configs/environment';

import * as Styled from './EmptyAbility.styles';

interface Params {
  field: 'fe' | 'be';
}

const EmptyAbility = ({ user }) => {
  const queryClient = useQueryClient();

  const { mutate: getDefaultAbility } = useMutation(
    [`default-abilities`],
    async ({ field }: Params) => {
      const { data } = await axios({
        method: 'post',
        url: `${BASE_URL}/abilities/templates/${field}`,
        headers: {
          Authorization: `Bearer ${user.accessToken}`,
        },
      });

      return data;
    }
  );

  const onSelectFrontEndAbility = () => {
    if (window.confirm('프론트엔드를 선택하겠나?')) {
      getDefaultAbility({ field: 'fe' });
      queryClient.invalidateQueries([`${user.username}-abilities`]);
    }
  };

  const onSelectBackEndAbility = () => {
    if (window.confirm('백엔드를 선택하겠나?')) {
      getDefaultAbility({ field: 'be' });
      queryClient.invalidateQueries([`${user.username}-abilities`]);
    }
  };

  return (
    <Styled.Container>
      <Styled.Header>
        <h3 id="title">존재하는 역량이 없으니, 자네가 원하는 역량을 골라보게</h3>
        <span id="subtitle">어느 분야를 고르겠나?</span>
        <Styled.Guide>(제 3의 길 ↑)</Styled.Guide>
      </Styled.Header>

      <Styled.ButtonWrapper>
        <Styled.FeButton type="button" onClick={onSelectFrontEndAbility}>
          <span>프론트엔드</span>
        </Styled.FeButton>
        <Styled.BeButton type="button" onClick={onSelectBackEndAbility}>
          <span>백엔드</span>
        </Styled.BeButton>
      </Styled.ButtonWrapper>
    </Styled.Container>
  );
};

export default EmptyAbility;
