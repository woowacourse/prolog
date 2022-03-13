import React from 'react';
import { COLOR } from '../../../constants';

const SubCategoryIcon = () => {
  return (
    <svg width="16px" height="16px" viewBox="0 0 40 54" fill={COLOR.RED_100}>
      <path
        d="M38.7678 36.7678C39.7441 35.7915 39.7441 34.2085 38.7678 33.2322L22.8579 17.3223C21.8816 16.346 20.2986 16.346 19.3223 17.3223C18.346 18.2986 18.346 19.8816 19.3223 20.8579L33.4645 35L19.3223 49.1421C18.346 50.1184 18.346 51.7014 19.3223 52.6777C20.2986 53.654 21.8816 53.654 22.8579 52.6777L38.7678 36.7678ZM0 37.5H37V32.5H0V37.5Z"
        fill={COLOR.DARK_GRAY_800}
      />
      <line
        x1="2.5"
        y1="1.09278e-07"
        x2="2.5"
        y2="35"
        stroke={COLOR.DARK_GRAY_800}
        strokeWidth="5"
      />
    </svg>
  );
};

export default SubCategoryIcon;
