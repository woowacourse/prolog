import styled from '@emotion/styled';
import { COLOR } from '../../constants';

export const Container = styled.li`
  width: 100%;
  height: 340px;
  padding: 20px;
  border-radius: 15px;
  background-color: #ffffff;
  list-style: none;

  &:hover {
    background-color: #f2f2f2;
  }
`;

export const Anchor = styled.a``;

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

export const UserName = styled.p`
  width: 250px;
  margin: 0;
  color: ${COLOR.DARK_GRAY_400};
  font-size: 14px;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
  word-break: break-all;
`;

export const Title = styled.p`
  width: 250px;
  margin: 0;
  color: ${COLOR.BLACK_900};
  font-size: 16px;
  font-weight: 700;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
  word-break: break-all;
`;

export const CreatedAt = styled.span`
  width: 100%;
  margin-top: 16px;
  color: ${COLOR.DARK_GRAY_400};
  text-align: right;
  font-size: 16px;
  font-weight: 700;
`;
