import React, { useEffect, useState } from 'react';

import useFetch from '../../hooks/useFetch';
import { requestGetFilters, requestGetPosts } from '../../service/requests';
import { Button, Modal, SelectBox } from '../../components';
import { COLOR } from '../../constants';
import { Checkbox } from './style';
import {
  Container,
  TitleContainer,
  SelectBoxContainer,
  StudyLogListContainer,
} from './StudyLogModal.styles';

const StudyLogModal = ({ onModalClose, username }) => {
  const [selectedLevel, setSelectedLevel] = useState('');
  const [filters] = useFetch([], requestGetFilters);

  const levels = filters.levels;

  return (
    <Modal onModalClose={onModalClose} width="50%" height="80%">
      <Container>
        <TitleContainer>
          <h2 id="dialog1Title">역량별 학습로그 등록하기</h2>
          <button type="button" onClick={onModalClose}>
            닫기
          </button>
        </TitleContainer>

        <SelectBoxContainer>
          <h3>레벨</h3>
          <SelectBox
            options={levels?.map((level) => level.name)}
            selectedOption={selectedLevel}
            setSelectedOption={setSelectedLevel}
            title="우아한테크코스 과정 레벨 목록입니다."
            name="level"
          />
        </SelectBoxContainer>

        <StudyLogListContainer>
          <span>총 0개</span>
          <ul>
            <li>
              <label>
                <Checkbox type="checkbox" />
                <div>
                  <p></p>
                  <h4></h4>
                </div>
              </label>
            </li>
          </ul>
        </StudyLogListContainer>

        <Button size="X_SMALL" css={{ backgroundColor: `${COLOR.LIGHT_BLUE_500}` }}>
          등록
        </Button>
      </Container>
    </Modal>
  );
};

export default StudyLogModal;
