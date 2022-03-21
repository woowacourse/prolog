import { MouseEventHandler } from 'react';
import { COLOR } from '../../../constants';
import * as Styled from './AbilityManageButton.styles';

interface Props {
  id: number;
  addEvent?: MouseEventHandler<HTMLButtonElement>;
  updateEvent?: MouseEventHandler<HTMLButtonElement>;
  deleteEvent?: MouseEventHandler<HTMLButtonElement>;
  cancelEvent?: MouseEventHandler<HTMLButtonElement>;
  save?: boolean;
  saveButtonDisabled?: boolean;
}

const AbilityManageButton = ({
  addEvent,
  updateEvent,
  deleteEvent,
  cancelEvent,
  save,
  saveButtonDisabled,
}: Props) => {
  const ButtonList = {
    add: (
      <Styled.Button
        type="button"
        color={COLOR.BLACK_900}
        fontSize="1.2rem"
        borderColor={COLOR.DARK_GRAY_800}
        onClick={addEvent}
      >
        추가
      </Styled.Button>
    ),

    update: (
      <Styled.Button
        type="button"
        color={COLOR.BLACK_900}
        fontSize="1.2rem"
        backgroundColor={COLOR.LIGHT_GRAY_200}
        borderColor={COLOR.LIGHT_GRAY_200}
        onClick={updateEvent}
      >
        수정
      </Styled.Button>
    ),

    delete: (
      <Styled.Button
        type="button"
        color={COLOR.BLACK_900}
        fontSize="1.2rem"
        backgroundColor={COLOR.RED_200}
        borderColor={COLOR.RED_200}
        onClick={deleteEvent}
      >
        삭제
      </Styled.Button>
    ),

    cancel: (
      <Styled.Button
        type="button"
        fontSize="12px"
        backgroundColor={COLOR.WHITE}
        color={COLOR.DARK_GRAY_900}
        borderColor={COLOR.DARK_BLUE_700}
        onClick={cancelEvent}
      >
        취소
      </Styled.Button>
    ),

    save: (
      <Styled.Button
        fontSize="12px"
        backgroundColor={COLOR.DARK_BLUE_700}
        color={COLOR.WHITE}
        disabled={saveButtonDisabled}
      >
        저장
      </Styled.Button>
    ),
  };

  return (
    <Styled.Wrapper>
      {addEvent && ButtonList['add']}
      {updateEvent && ButtonList['update']}
      {deleteEvent && ButtonList['delete']}
      {cancelEvent && ButtonList['cancel']}
      {save && ButtonList['save']}
    </Styled.Wrapper>
  );
};

export default AbilityManageButton;
