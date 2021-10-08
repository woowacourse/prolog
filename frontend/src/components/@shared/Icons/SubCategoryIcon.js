import React from 'react';

const SubCategoryIcon = ({ width = '100%', height = '100%', color = 'black' }) => {
  return (
    <svg viewBox="0 0 32 32" width={width} height={height}>
      <path stroke={color} fill="transparent" strokeWidth="1px" d="M 6 12 L 6 20 L 12 20" />
      <path stroke="transparent" fill={color} d="M 12 22 L 12 18 L 14 20" />
    </svg>
  );
};

export default SubCategoryIcon;
