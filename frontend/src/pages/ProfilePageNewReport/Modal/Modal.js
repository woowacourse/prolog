import React, { useEffect, useRef } from 'react';

import { Button } from '../../../components';
import { COLOR } from '../../../constants';
import { Checkbox } from '../style';
import {
  ModalInner,
  Container,
  TitleContainer,
  SelectBoxContainer,
  StudyLogListContainer,
} from './Modal.styles';

const Modal = ({ onModalClose }) => {
  const modalRef = useRef(null);

  useEffect(() => {
    const closeStudyLogList = (event) => {
      if (!modalRef.current?.contains(event.target)) {
        onModalClose();
      }
    };
    document.addEventListener('click', closeStudyLogList);

    return () => document.removeEventListener('click', closeStudyLogList);
  }, [onModalClose]);

  return (
    <Container>
      <ModalInner ref={modalRef}>
        <TitleContainer>
          <h2>역량별 학습로그 등록하기</h2>
          <button type="button" onClick={onModalClose}>
            닫기
          </button>
        </TitleContainer>

        <SelectBoxContainer>
          <h3>레벨</h3>
          {/* <SelectBox /> */}
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
      </ModalInner>
    </Container>
  );
};

export default Modal;
