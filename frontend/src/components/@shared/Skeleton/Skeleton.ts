import styled from '@emotion/styled';
import COLOR from '../../../constants/color';

export type SkeletonProps = {
  /**
   * @default fit-content
   */
  width: string | number;
  /**
   * @default fit-content
   */
  height: string | number;
  /**
   * @default unset
   */
  borderRadius?: string;
};

const Skeleton = styled.div<SkeletonProps>`
  background-color: ${COLOR.LIGHT_GRAY_200};
  width: ${({ width }) => width ?? 'fit-content'};
  height: ${({ height }) => height ?? 'fit-content'};
  border-radius: ${({ borderRadius }) => borderRadius ?? 'unset'}; ;
`;

export default Skeleton;
