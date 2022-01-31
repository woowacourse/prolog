/** @jsxImportSource @emotion/react */

import { useContext, useEffect, useState } from 'react';
import { useHistory } from 'react-router-dom';
import { css } from '@emotion/react';

import useFetch from '../../hooks/useFetch';
import useFilterWithParams from '../../hooks/useFilterWithParams';
import useStudylog from '../../hooks/useStudylog';
import { requestGetFilters } from '../../service/requests';
import { UserContext } from '../../contexts/UserProvider';

import { Button, FilterList, Pagination } from '../../components';
import FlexBox from '../../components/@shared/FlexBox/FlexBox';
import Chip from '../../components/Chip/Chip';
import StudylogList from '../../components/Lists/StudylogList';
import SearchBar from '../../components/SearchBar/SearchBar';

import { ERROR_MESSAGE } from '../../constants/message';
import { PATH } from '../../constants';

import { MainContentStyle } from '../../PageRouter';
import {
  FilterListWrapper,
  HeaderContainer,
  PostListContainer,
  SelectedFilterList,
} from './styles';
import {
  AlignItemsCenterStyle,
  FlexStyle,
  JustifyContentSpaceBtwStyle,
} from '../../styles/flex.styles';

import PencilIcon from '../../assets/images/pencil_icon.svg';

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
  const { isLoggedIn, accessToken } = user;

  const { response: studylogs, getAllData: getStudylogs } = useStudylog([]);

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

  useEffect(() => {
    const query = new URLSearchParams(history.location.search);

    setSearchKeywords(query.get('keyword') ?? '');
  }, [history.location.search]);

  return (
    <div css={[MainContentStyle]}>
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
              font-size: 2.4rem;
            `}
          >
            📚 학습로그
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
        {studylogs?.data?.length === 0 && '작성된 글이 없습니다.'}
        {studylogs && studylogs.data && <StudylogList studylogs={studylogs.data} />}
      </PostListContainer>
      <Pagination postsInfo={studylogs} onSetPage={onSetPage}></Pagination>
    </div>
  );
};

export default StudylogListPage;
