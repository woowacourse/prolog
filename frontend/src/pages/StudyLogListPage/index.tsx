/** @jsxImportSource @emotion/react */

import { useEffect, useState } from 'react';
import { Button, FilterList, Pagination } from '../../components';
import { useHistory } from 'react-router-dom';
import { PATH } from '../../constants';
import PencilIcon from '../../assets/images/pencil_icon.svg';
import useFetch from '../../hooks/useFetch';
import { requestGetFilters } from '../../service/requests';
import { useSelector } from 'react-redux';
import {
  FilterListWrapper,
  HeaderContainer,
  PostListContainer,
  SelectedFilterList,
} from './styles';
import { ERROR_MESSAGE } from '../../constants/message';
import Chip from '../../components/Chip/Chip';
import FlexBox from '../../components/@shared/FlexBox/FlexBox';
import useFilterWithParams from '../../hooks/useFilterWithParams';
import StudyLogList from '../../components/Lists/StudyLogList';
import useStudyLog from '../../hooks/useStudyLog';
import { css } from '@emotion/react';
import SearchBar from '../../components/SearchBar/SearchBar';
import {
  AlignItemsCenterStyle,
  FlexStyle,
  JustifyContentSpaceBtwStyle,
} from '../../styles/flex.styles';

type User = {
  profile: { loading: boolean; data: string; error: null | string };
  accessToken: { loading: boolean; data: string; error: null | string };
};

const StudyLogListPage = (): JSX.Element => {
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

  const user = useSelector((state: { user: User }) => state.user.profile);
  const isLoggedIn = !!user.data;

  const accessToken = useSelector((state: { user: User }) => state.user.accessToken.data);

  const { response: posts, getAllData: getStudyLogs } = useStudyLog([]);

  const [filters] = useFetch([], requestGetFilters);

  const [searchKeywords, setSearchKeywords] = useState('');

  const goNewPost = () => {
    if (!accessToken) {
      alert(ERROR_MESSAGE.LOGIN_DEFAULT);
      window.location.reload();
      return;
    }

    history.push(PATH.NEW_POST);
  };

  const onDeleteSearchKeyword = () => {
    const params = new URLSearchParams(history.location.search);
    params.delete('keyword');

    history.push(`${PATH.STUDYLOG}?${params.toString()}`);
  };

  const onSearchKeywordsChange = (event) => {
    setSearchKeywords(event.target.value);
  };

  const onSearch = async (event) => {
    event.preventDefault();

    const query = new URLSearchParams(history.location.search);
    query.set('page', '1');

    if (searchKeywords) {
      query.set('keyword', searchKeywords);
    } else {
      query.delete('keyword');
    }

    history.push(`${PATH.STUDYLOG}?${query.toString()}`);
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

    getStudyLogs({ type: 'searchParams', data: query }, accessToken);
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

  useEffect(() => {
    const query = new URLSearchParams(history.location.search);

    setSearchKeywords(query.get('keyword') ?? '');
  }, [history.location.search]);

  return (
    <>
      <HeaderContainer>
        <div
          css={[
            FlexStyle,
            JustifyContentSpaceBtwStyle,
            AlignItemsCenterStyle,
            css`
              margin-bottom: 1rem;
            `,
          ]}
        >
          <h1
            css={css`
              font-size: 2rem;
            `}
          >
            학습로그
          </h1>
          <SearchBar
            onSubmit={onSearch}
            onChange={onSearchKeywordsChange}
            value={searchKeywords}
            css={css``}
          />
        </div>
        <FlexBox>
          <FilterListWrapper>
            <FilterList
              filters={filters}
              selectedFilter={selectedFilter}
              setSelectedFilter={setSelectedFilter}
              selectedFilterDetails={selectedFilterDetails}
              setSelectedFilterDetails={onFilterChange}
              isVisibleResetFilter={!!selectedFilterDetails.length}
              onResetFilter={resetFilter}
            />
          </FilterListWrapper>
          {isLoggedIn && (
            <Button
              type="button"
              size="SMALL"
              icon={PencilIcon}
              alt="글쓰기 아이콘"
              onClick={goNewPost}
              cssProps={css`
                margin-left: 1rem;
              `}
            >
              글쓰기
            </Button>
          )}
        </FlexBox>

        <SelectedFilterList>
          <ul>
            {!!search && (
              <li>
                <Chip onDelete={onDeleteSearchKeyword}>{`검색어 : ${search}`}</Chip>
              </li>
            )}
            {selectedFilterDetails.map(({ filterType, filterDetailId, name }) => (
              <li key={filterType + filterDetailId + name}>
                <Chip
                  onDelete={() => onUnsetFilter({ filterType, filterDetailId })}
                >{`${filterType}: ${name}`}</Chip>
              </li>
            ))}
          </ul>
        </SelectedFilterList>
      </HeaderContainer>

      <PostListContainer>
        {posts?.data?.length === 0 && '작성된 글이 없습니다.'}
        {posts && posts.data && <StudyLogList studylogs={posts.data} />}
      </PostListContainer>
      <Pagination postsInfo={posts} onSetPage={onSetPage}></Pagination>
    </>
  );
};

export default StudyLogListPage;
