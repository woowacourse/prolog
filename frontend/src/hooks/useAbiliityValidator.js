import useSnackBar from './useSnackBar';
import { isCorrectHexCode } from '../utils/hexCode';
import { ERROR_MESSAGE } from '../constants';

const useAbilityValidator = () => {
  const { openSnackBar } = useSnackBar();

  const abilityValidate = (targetAbilities, { name, color }) => {
    if (!name) {
      openSnackBar(ERROR_MESSAGE.NEED_ABILITY_NAME);
      return false;
    }

    if (targetAbilities.find((ability) => ability.name === name)) {
      openSnackBar('이미 등록된 이름입니다.');
      return false;
    }

    if (!color) {
      openSnackBar(ERROR_MESSAGE.NEED_ABILITY_COLOR);
      return false;
    }

    if (targetAbilities.find((ability) => ability.color === color)) {
      openSnackBar('이미 등록된 색입니다.');
      return false;
    }

    if (!isCorrectHexCode(color)) {
      openSnackBar(ERROR_MESSAGE.INVALID_ABILIT_COLOR);
      return false;
    }

    return true;
  };

  return {
    abilityValidate,
  };
};

export default useAbilityValidator;
