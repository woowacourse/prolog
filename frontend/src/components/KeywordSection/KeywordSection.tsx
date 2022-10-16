import styled from '@emotion/styled';
import React, { useState } from 'react';
import SomeImage from '../../assets/images/background-image.png';
import { COLOR } from '../../enumerations/color';
import LabelledImage from '../LabelledImage/LabelledImage';

interface Keyword {
  src: string;
  alt: string;
  text: string;
}

const keywords: Keyword[] = [
  { src: SomeImage, alt: 'JavaScript', text: 'JavaScript' },
  { src: SomeImage, alt: 'JavaScript', text: 'JavaScript' },
  { src: SomeImage, alt: 'JavaScript', text: 'JavaScript' },
  { src: SomeImage, alt: 'JavaScript', text: 'JavaScript' },
];

const KeywordSection = () => {
  const [keywordOrder, setKeywordOrder] = useState(0);

  const handleClickKeyword = (order: number) => {
    setKeywordOrder(order);
  };
  return (
    <StyledRoot>
      {keywords.map((keyword, index) => (
        <StyledWrapper>
          <LabelledImage
            {...keyword}
            isSelected={keywordOrder === index}
            onClick={() => setKeywordOrder(index)}
          />
          {index + 1 !== keywords.length && <StyledArrow />}
        </StyledWrapper>
      ))}
    </StyledRoot>
  );
};

export default KeywordSection;

const StyledRoot = styled.div`
  display: flex;
`;

const StyledArrow = styled.div`
  width: 0;
  height: 0;
  border: 9px solid transparent;
  border-top: 0;
  border-bottom: 15px solid ${COLOR.LIGHT_BLUE_700};
  transform: rotate(90deg);
  margin: 0 12px;
`;

const StyledWrapper = styled.div`
  display: flex;
  align-items: center;
`;
