/** @jsxImportSource @emotion/react */

import { css } from '@emotion/react';
import { useContext, useEffect, useState } from 'react';
import { useHistory } from 'react-router-dom';
import { Pagination } from '../../components';
import StudylogList from '../../components/Lists/StudylogList';
import { COLOR, PATH } from '../../constants';
import { ERROR_MESSAGE } from '../../constants/message';
import { UserContext } from '../../contexts/UserProvider';
import useFetch from '../../hooks/useFetch';
import useFilterWithParams from '../../hooks/useFilterWithParams';
import useStudylog from '../../hooks/useStudylog';
import { MainContentStyle } from '../../PageRouter';
import { requestGetFilters, requestGetFiltersWithAccessToken } from '../../service/requests';
import { FlexStyle, getFlexStyle } from '../../styles/flex.styles';
import { HeaderContainer, StudylogListContainer } from './styles';
import { Card, SectionName } from '../../components/StudylogEditor/styles';

const StudylogListPage = (): JSX.Element => {
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
  const search = new URLSearchParams(history.location.search).get('keyword');

  const { user } = useContext(UserContext);
  const { isLoggedIn, role, accessToken } = user;

  const authorized = isLoggedIn && role !== 'GUEST';
  const { response: studylogs, getAllData: getStudylogs } = useStudylog([]);

  const [filters] = useFetch(
    [],
    accessToken ? () => requestGetFiltersWithAccessToken(accessToken) : requestGetFilters
  );

  const [showAllMySessions, setShowAllMySessions] = useState(false);
  const [showAllAllSessions, setShowAllAllSessions] = useState(false);

  const goNewStudylog = () => {
    if (!accessToken) {
      alert(ERROR_MESSAGE.LOGIN_DEFAULT);
      window.location.reload();
      return;
    }

    history.push(PATH.NEW_STUDYLOG);
  };

  const findFilterItem = (key, id) =>
    selectedFilterDetails.find(
      (filterItem) => filterItem.filterType === key && filterItem.filterDetailId === id
    );

  const toggleFilterDetails = (filterType, filterDetailId, name) => {
    const targetFilterItem = { filterType, filterDetailId, name };
    const isExistFilterItem = findFilterItem(filterType, filterDetailId);

    if (isExistFilterItem) {
      setSelectedFilterDetails(selectedFilterDetails.filter((item) => item !== isExistFilterItem));
    } else {
      setSelectedFilterDetails([...selectedFilterDetails, targetFilterItem]);
    }
  };

  const checkSelectedSession = (sessionId) => {
    return selectedFilterDetails.some(
      (filterItem) =>
        filterItem.filterType === 'sessions' && filterItem.filterDetailId === sessionId
    );
  };

  useEffect(() => {
    const params = new URLSearchParams(getFullParams());

    if (search) {
      params.set('keyword', search);
    }

    history.push(`${PATH.STUDYLOG}${params && '?' + params.toString()}`);
  }, [getFullParams, postQueryParams, selectedFilterDetails]);

  useEffect(() => {
    const query = new URLSearchParams(history.location.search);

    getStudylogs({ query: { type: 'searchParams', data: query }, accessToken });
  }, [history.location.search, accessToken]);

  useEffect(() => {
    if (filters.length === 0) {
      return;
    }

    const selectedFilterDetailsWithName = selectedFilterDetails.map(
      ({ filterType, filterDetailId }) => {
        const name = filters[filterType].find(({ id }) => id === filterDetailId)?.[
          filterType === 'members' ? 'username' : 'name'
        ];
        return { filterType, filterDetailId, name };
      }
    );

    setSelectedFilterDetails(selectedFilterDetailsWithName);
  }, [filters]);

  const renderSessionList = (sessions, showAll, toggleShowAll) => {
    const sessionsToShow = showAll ? sessions : sessions.slice(0, 5);

    return (
      <div>
        {sessionsToShow.map((session) => (
          <div
            css={css`
              padding: 0.5rem;
              border-radius: 0.5rem;
              font-size: 1.5rem;
              color: ${checkSelectedSession(session.id)
                ? COLOR.DARK_GRAY_700
                : COLOR.LIGHT_GRAY_900};
              margin-bottom: 0.7rem;
              cursor: pointer;
              background-color: ${checkSelectedSession(session.id)
                ? COLOR.LIGHT_GRAY_100
                : 'transparent'};
              border-width: 1px;
              border-style: solid;
              border-color: ${checkSelectedSession(session.id)
                ? COLOR.LIGHT_GRAY_100
                : COLOR.WHITE};
              box-shadow: ${checkSelectedSession(session.id)
                ? '0 1px 2px rgba(0, 0, 0, 0.1)'
                : 'none'};

              &:hover {
                box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
              }
            `}
            key={session.id}
            onClick={() => {
              setSelectedFilter(session.id);
              toggleFilterDetails('sessions', session.id, session.name);
            }}
          >
            # {session.name}
          </div>
        ))}
        {sessions.length > 5 && (
          <button
            css={css`
              margin-top: 1rem;
              padding: 0.5rem 1rem;
              font-size: 1.2rem;
              cursor: pointer;
              background-color: ${COLOR.LIGHT_GRAY_100};
              border: none;
              border-radius: 0.5rem;
            `}
            onClick={toggleShowAll}
          >
            {showAll ? '가리기' : '더보기'}
          </button>
        )}
      </div>
    );
  };

  return (
    <div css={[MainContentStyle, FlexStyle]}>
      <Card
        css={[
          getFlexStyle({ flexDirection: 'column' }),
          css`
            width: 30%;
            overflow-y: auto;
            height: 87vh;
          `,
        ]}
      >
        <SectionName>내 강의 목록</SectionName>
        <HeaderContainer>
          <div
            css={css`
              margin-bottom: 3rem;
            `}
          >
            {filters && filters.mySessions && filters.mySessions.length === 0 && (
              <div
                css={css`
                  font-size: 1.2rem;
                  color: #878787;
                `}
              >
                수강중인 강의가 없습니다.
              </div>
            )}
            {filters &&
              filters.mySessions &&
              renderSessionList(filters.mySessions, showAllMySessions, () =>
                setShowAllMySessions(!showAllMySessions)
              )}
          </div>
        </HeaderContainer>
        <SectionName>전체 강의 목록</SectionName>
        <HeaderContainer>
          <div>
            {filters &&
              filters.sessions &&
              renderSessionList(filters.sessions, showAllAllSessions, () =>
                setShowAllAllSessions(!showAllAllSessions)
              )}
          </div>
        </HeaderContainer>
      </Card>
      <div
        css={[
          css`
            margin-left: 2rem;
            overflow-y: auto;
            height: 87vh;
            width: 70%;
          `,
          getFlexStyle({ flexDirection: 'column' }),
        ]}
      >
        <StudylogListContainer>
          {studylogs?.data?.length === 0 && (
            <Card
              css={css`
                height: 87vh;
                align-content: center;
                text-align: center;
                font-size: 1.5rem;
                color: ${COLOR.LIGHT_GRAY_500};
              `}
            >
              작성된 글이 없습니다.
            </Card>
          )}
          {studylogs && studylogs.data && <StudylogList studylogs={studylogs.data} />}
        </StudylogListContainer>
        <Pagination dataInfo={studylogs} onSetPage={onSetPage}></Pagination>
      </div>
    </div>
  );
};

export default StudylogListPage;
