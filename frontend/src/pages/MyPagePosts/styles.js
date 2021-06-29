import styled from '@emotion/styled';

const PostItem = styled.div`
  height: 18rem;
  padding: 2.4rem 1.6rem;
  cursor: pointer;
  display: flex;
  justify-content: space-between;

  &:not(:last-child) {
    border-bottom: 1px solid #e6e6e6;
  }

  &:hover {
    background-color: #f9f9f9;
  }
`;

const Content = styled.div`
  display: flex;
  height: 100%;
`;

const Description = styled.div`
  display: flex;
  flex-direction: column;
  height: 100%;
`;

const Mission = styled.div`
  font-size: 1.6rem;
  color: #383838;
`;

const Title = styled.h3`
  font-size: 2.8rem;

  color: #383838;
  font-weight: bold;
`;

const Tags = styled.div`
  font-size: 1.2rem;
  color: #848484;
  margin-top: auto;
`;

const ButtonList = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: center;
  gap: 1.6rem;
`;

const NoPost = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  height: 6rem;
  font-size: 2rem;
`;

export { Content, Description, Mission, Title, Tags, PostItem, ButtonList, NoPost };
