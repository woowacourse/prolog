import { useContext, useEffect, useState } from 'react';
import { useHistory, useParams } from 'react-router-dom';

import useRequest from '../../hooks/useRequest';
import useMutation from '../../hooks/useMutation';
import { requestDeleteScrap, requestGetMyScrap } from '../../service/requests';
import { UserContext } from '../../contexts/UserProvider';

import { Button, BUTTON_SIZE, Card, Pagination } from '../../components';

import { CONFIRM_MESSAGE, PATH } from '../../constants';

import {
  ButtonList,
  CardStyles,
  Container,
  Content,
  DeleteButtonStyle,
  Description,
  Mission,
  NoPost,
  PostItem,
  Tags,
  Title,
  Heading,
} from './styles';
import { useDeleteScrapMutation, useFetchMyScrap } from '../../hooks/queries/profile';

const initialPostQueryParams = {
  page: 1,
  size: 10,
  direction: 'desc',
};

const ProfilePageScraps = () => {
  const history = useHistory();

  const { user } = useContext(UserContext);
  const { accessToken } = user;

  const { username } = useParams();

  const [postQueryParams, setPostQueryParams] = useState(initialPostQueryParams);

  const goTargetStudylog = (id) => {
    history.push(`${PATH.STUDYLOG}/${id}`);
  };

  const { data: studylogs } = useFetchMyScrap({ username, accessToken, postQueryParams });

  const onSetPage = (page) => {
    setPostQueryParams({ ...postQueryParams, page });
  };

  const deleteScrapMutation = useDeleteScrapMutation();

  const onDeleteScrap = async (event, id) => {
    event.stopPropagation();

    if (!window.confirm(CONFIRM_MESSAGE.DELETE_SCRAP)) {
      return;
    }

    deleteScrapMutation.mutate({ username, accessToken, id });
  };

  return (
    <Container>
      <Heading>스크랩</Heading>
      <Card css={CardStyles}>
        {studylogs?.data?.length ? (
          <>
            {studylogs?.data?.map((studylog) => {
              const { id, mission, title, tags, content } = studylog;

              return (
                <PostItem key={id} size="SMALL" onClick={() => goTargetStudylog(id)}>
                  <Description>
                    <Mission>{mission?.name}</Mission>
                    <Title>{title}</Title>
                    <Content>{content.replace(/[#*>\n]/g, '')}</Content>
                    <Tags>
                      {tags.map(({ id, name }) => (
                        <span key={id}>{`#${name} `}</span>
                      ))}
                    </Tags>
                  </Description>
                  <ButtonList>
                    <Button
                      size={BUTTON_SIZE.X_SMALL}
                      type="button"
                      css={DeleteButtonStyle}
                      alt="삭제 버튼"
                      onClick={(event) => {
                        onDeleteScrap(event, id);
                      }}
                    >
                      스크랩 취소
                    </Button>
                  </ButtonList>
                </PostItem>
              );
            })}
            <Pagination dataInfo={studylogs} onSetPage={onSetPage} />
          </>
        ) : (
          <NoPost>스크랩한 글이 없습니다 🥲</NoPost>
        )}
      </Card>
    </Container>
  );
};

export default ProfilePageScraps;
