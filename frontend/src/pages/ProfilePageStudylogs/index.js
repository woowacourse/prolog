import React, { useEffect, useState } from 'react';
import { useHistory, useLocation, useParams } from 'react-router-dom';
import { ALERT_MESSAGE, CONFIRM_MESSAGE, PATH } from '../../constants';
import { Button, BUTTON_SIZE, Card, FilterList, Pagination } from '../../components';
import { requestGetFilters } from '../../service/requests';
import {
  ButtonList,
  CardStyles,
  Container,
  Content,
  DeleteButtonStyle,
  Description,
  EditButtonStyle,
  FilterListWrapper,
  FilterStyles,
  HeaderContainer,
  Mission,
  NoPost,
  PostItem,
  Tags,
  Title,
  Heading,
} from './styles';
import { useSelector } from 'react-redux';
import useStudyLog from '../../hooks/useStudylog';
import useFetch from '../../hooks/useFetch';
import useFilterWithParams from '../../hooks/useFilterWithParams';
import { SelectedFilterList } from '../MainPage/styles';
import Chip from '../../components/Chip/Chip';
import { isEmptyObject } from '../../utils/object';

const ProfilePageStudylogs = () => {
  const {
    postQueryParams,
    selectedFilter,
    setSelectedFilter,
    selectedFilterDetails,
    setSelectedFilterDetails,
    onSetPage,
    onUnsetFilter,
    onFilterChange,
    resetFilter,
    getFullParams,
  } = useFilterWithParams();

  const history = useHistory();
  const accessToken = useSelector((state) => state.user.accessToken.data);
  const myName = useSelector((state) => state.user.profile.data?.username);
  const { username } = useParams();
  const { state } = useLocation();

  const [shouldInitialLoad, setShouldInitialLoad] = useState(!state);
  const [hoveredPostId, setHoveredPostId] = useState(0);

  const [filters] = useFetch({}, requestGetFilters);

  const {
    response: studylogs,
    getAllData: getStudylogs,
    error: studylogError,
    deleteData: deleteStudylog,
  } = useStudyLog([]);

  const goTargetStudylog = (id) => {
    history.push(`${PATH.STUDYLOG}/${id}`);
  };

  const goEditTargetStudylog = (id) => (event) => {
    event.stopPropagation();

    history.push(`${PATH.STUDYLOG}/${id}/edit`);
  };

  const getData = async () => {
    const query = new URLSearchParams(history.location.search) + `&usernames=${username}`;
    await getStudylogs({ type: 'searchParams', data: query });
  };

  const onDeleteStudylog = async (event, id) => {
    event.stopPropagation();

    if (!window.confirm(CONFIRM_MESSAGE.DELETE_POST)) return;

    await deleteStudylog(id, accessToken);

    if (studylogError) {
      alert(ALERT_MESSAGE.FAIL_TO_DELETE_POST);
      return;
    }

    await getData();
  };

  useEffect(() => {
    const params = getFullParams();

    history.push(`${PATH.ROOT}${username}/studylogs${params && '?' + params}`);
  }, [postQueryParams, selectedFilterDetails, username]);

  useEffect(() => {
    if (!shouldInitialLoad) {
      setShouldInitialLoad(true);

      return;
    }

    getData();
  }, [history.location.search]);

  useEffect(() => {
    delete filters.members;

    if (isEmptyObject(filters)) {
      return;
    }

    const selectedFilterDetailsWithName = selectedFilterDetails.map(
      ({ filterType, filterDetailId }) => {
        const name = filters[filterType].find(({ id }) => id === filterDetailId)?.name;
        return { filterType, filterDetailId, name };
      }
    );

    setSelectedFilterDetails(selectedFilterDetailsWithName);
  }, [filters]);

  return (
    <Container>
      <Heading>학습로그</Heading>
      <HeaderContainer>
        <FilterListWrapper>
          <FilterList
            filters={filters}
            selectedFilter={selectedFilter}
            setSelectedFilter={setSelectedFilter}
            selectedFilterDetails={selectedFilterDetails}
            setSelectedFilterDetails={onFilterChange}
            isVisibleResetFilter={!!selectedFilterDetails.length}
            onResetFilter={resetFilter}
            css={FilterStyles}
          />
        </FilterListWrapper>
        <SelectedFilterList>
          <ul>
            {selectedFilterDetails.map(({ filterType, filterDetailId, name }) => (
              <li key={filterType + filterDetailId + name}>
                <Chip onDelete={() => onUnsetFilter({ filterType, filterDetailId })}>
                  {`${filterType}: ${name}`}
                </Chip>
              </li>
            ))}
          </ul>
        </SelectedFilterList>
      </HeaderContainer>
      <Card css={CardStyles}>
        {studylogs?.data?.length ? (
          <>
            {studylogs?.data?.map((post) => {
              const { id, mission, title, tags, content } = post;

              return (
                <PostItem
                  key={id}
                  size="SMALL"
                  onClick={() => goTargetStudylog(id)}
                  onMouseEnter={() => setHoveredPostId(id)}
                  onMouseLeave={() => setHoveredPostId(0)}
                >
                  <Description>
                    <Mission>{mission.name}</Mission>
                    <Title isHovered={id === hoveredPostId}>{title}</Title>
                    <Content>{content}</Content>
                    <Tags>
                      {tags.map(({ id, name }) => (
                        <span key={id}>{`#${name} `}</span>
                      ))}
                    </Tags>
                  </Description>
                  <ButtonList isVisible={hoveredPostId === id && myName === username}>
                    <Button
                      size={BUTTON_SIZE.X_SMALL}
                      type="button"
                      css={EditButtonStyle}
                      alt="수정 버튼"
                      onClick={goEditTargetStudylog(id)}
                    >
                      수정
                    </Button>
                    <Button
                      size={BUTTON_SIZE.X_SMALL}
                      type="button"
                      css={DeleteButtonStyle}
                      alt="삭제 버튼"
                      onClick={(event) => {
                        onDeleteStudylog(event, id);
                      }}
                    >
                      삭제
                    </Button>
                  </ButtonList>
                </PostItem>
              );
            })}
            <Pagination studylogsInfo={studylogs} onSetPage={onSetPage} />
          </>
        ) : (
          <NoPost>작성한 글이 없습니다 🥲</NoPost>
        )}
      </Card>
    </Container>
  );
};

export default ProfilePageStudylogs;
