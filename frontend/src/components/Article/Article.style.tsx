import styled from '@emotion/styled';
import { COLOR } from '../../constants';

export const Container = styled.li`
  width: 100%;
  height: 340px;
  padding: 20px;
  border-radius: 15px;
  background-color: #ffffff;
  list-style: none;
`;

export const ThumbnailWrapper = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  width: 100%;
  height: 154px;
  border-radius: 15px;
  margin-bottom: 20px;
`;

export const Thumbnail = styled.img`
  width: 100%;
  height: 154px;
  border-radius: 15px;
  object-fit: cover;
`;

export const ArticleInfoContainer = styled.div`
  display: flex;
  flex-direction: column;
  gap: 10px;
  width: 100%;
  padding: 10px;
`;

export const UserName = styled.span`
  color: ${COLOR.DARK_GRAY_400};
  font-size: 16px;
`;

export const Title = styled.span`
  color: ${COLOR.BLACK_900};
  font-size: 20px;
  font-weight: 700;
`;

export const CreatedAt = styled.span`
  width: 100%;
  margin-top: 16px;
  color: ${COLOR.DARK_GRAY_400};
  text-align: right;
  font-size: 16px;
  font-weight: 700;
`;
