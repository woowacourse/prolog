import React from 'react';

const CancelIcon = ({ width, height, stroke, strokeWidth }) => {
  return (
    <svg
      viewBox="0 0 16 16"
      width={width}
      height={height}
      stroke={stroke}
      strokeWidth={strokeWidth}
    >
      <path d="M 0 0 L 16 16 M 16 0 L 0 16" />
    </svg>
  );
};

export default CancelIcon;
