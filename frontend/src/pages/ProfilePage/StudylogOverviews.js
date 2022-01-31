import React, { useEffect, useState, useCallback } from 'react';
import { useHistory, useLocation, useParams } from 'react-router-dom';

import useFetch from '../../hooks/useFetch';
import useStudylog from '../../hooks/useStudylog';
import { requestGetUserTags } from '../../service/requests';

import { Calendar, Card, Pagination, Tag } from '../../components';
import { PATH } from '../../constants';
import {
  CardStyles,
  Description,
  Mission,
  NoPost,
  Overview,
  PostBottomContainer,
  PostItem,
  TagContainer,
  Tags,
  TagTitle,
  Title,
} from './styles';

const initialPostQueryParams = {
  page: 1,
  size: 5,
  direction: 'desc',
};

const StudylogOverview = () => {
  const history = useHistory();
  const { username } = useParams();
  const { state } = useLocation();

  const [selectedTagId, setSelectedTagId] = useState(0);
  const [selectedDay, setSelectedDay] = useState(state ? state.date.day : -1);

  const [filteringOption, setFilteringOption] = useState([
    { filterType: 'tags', filterDetailId: 0 },
  ]);

  const [shouldInitialLoad, setShouldInitialLoad] = useState(!state);
  const [postQueryParams, setPostQueryParams] = useState(initialPostQueryParams);

  const [hoveredPostId, setHoveredPostId] = useState(0);
  const [tags] = useFetch([], () => requestGetUserTags(username));

  const { response: studylogs, getAllData: getStudylogs } = useStudylog([]);

  const getUserPosts = useCallback(async () => {
    const filterQuery = [...filteringOption, { filterType: 'usernames', filterDetailId: username }];

    await getStudylogs({
      query: {
        type: 'filter',
        data: { filterQuery, postQueryParams },
      },
    });
  }, [postQueryParams, filteringOption, username]);

  const setFilteringOptionWithDate = (year, month, day) => {
    const date = `${year}${month < 10 ? '0' + month : month}${day < 10 ? '0' + day : day}`;

    setFilteringOption([
      {
        filterType: 'startDate',
        filterDetailId: date,
      },
      {
        filterType: 'endDate',
        filterDetailId: date,
      },
    ]);
  };

  const setFilteringOptionWithTagId = (id) =>
    setFilteringOption([{ filterType: 'tags', filterDetailId: id }]);

  const goTargetStudylog = (id) => {
    history.push(`${PATH.STUDYLOG}/${id}`);
  };

  const onSetPage = (page) => {
    setPostQueryParams({ ...postQueryParams, page });
  };

  useEffect(() => {
    if (!shouldInitialLoad) {
      setShouldInitialLoad(true);

      return;
    }

    getUserPosts();
  }, [username, getUserPosts, shouldInitialLoad]);

  useEffect(() => {
    if (!state) return;

    setFilteringOptionWithDate(state.date.year, state.date.month, state.date.day);
  }, [state]);

  return (
    <Overview>
      <div>
        <TagTitle>태그</TagTitle>
        <TagContainer>
          {tags?.data?.map(({ id, name, count }) => (
            <Tag
              key={id}
              id={id}
              name={name}
              postCount={count}
              selectedTagId={selectedTagId}
              onClick={() => {
                setSelectedTagId(id);
                setSelectedDay(-1);
                setFilteringOptionWithTagId(id);
              }}
            />
          ))}
        </TagContainer>
      </div>
      <Card title="캘린더" cssProps={CardStyles}>
        <Calendar
          newDate={state?.date}
          onClick={(year, month, day) => {
            setSelectedTagId(null);
            setFilteringOptionWithDate(year, month, day);
          }}
          selectedDay={selectedDay}
          setSelectedDay={setSelectedDay}
        />
      </Card>
      <Card title="학습로그" cssProps={CardStyles}>
        {studylogs?.data?.length ? (
          <>
            {studylogs?.data?.map((studylog) => {
              const { id, mission, title, tags, createdAt } = studylog;

              return (
                <PostItem
                  key={id}
                  size="SMALL"
                  onClick={() => goTargetStudylog(id)}
                  onMouseEnter={() => setHoveredPostId(id)}
                  onMouseLeave={() => setHoveredPostId(0)}
                >
                  <Description>
                    <Title isHovered={id === hoveredPostId}>{title}</Title>
                    <PostBottomContainer>
                      <Mission>{mission.name}</Mission>
                      <div>{new Date(createdAt).toLocaleString('ko-KR')}</div>
                      {!!tags.length && (
                        <Tags>
                          {tags.map(({ id, name }) => (
                            <span key={id}>{`#${name} `}</span>
                          ))}
                        </Tags>
                      )}
                    </PostBottomContainer>
                  </Description>
                </PostItem>
              );
            })}
            <Pagination dataInfo={studylogs} onSetPage={onSetPage} />
          </>
        ) : (
          <NoPost>작성한 글이 없습니다 🥲</NoPost>
        )}
      </Card>
    </Overview>
  );
};

export default StudylogOverview;
