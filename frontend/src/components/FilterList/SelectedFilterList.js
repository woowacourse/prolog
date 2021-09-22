import React from 'react';
import { CheckIcon, FilterDetail } from './FilterList.styles';
import checkIcon from '../../assets/images/check.png';

const sorting = [true, false];

const SelectedFilterList = ({
  searchKeyword,
  filterList,
  type,
  findFilterItem,
  toggleFilterDetails,
}) => {
  const regExpKeyword = new RegExp(searchKeyword.replace(/\s+/g, ''), 'i');

  return (
    <ul>
      {sorting.map((isChecked) =>
        filterList
          .filter(({ name, username, nickname }) => {
            if (type === 'members') {
              return regExpKeyword.test(username) || regExpKeyword.test(nickname);
            }

            return regExpKeyword.test(name);
          })
          .map(({ id, name, username, nickname, imageSrc }) => {
            if (Boolean(findFilterItem(type, id)) === !isChecked) return null;

            return (
              <li key={id} onClick={() => toggleFilterDetails(type, id, name)}>
                <FilterDetail>
                  {type === 'members' ? (
                    <>
                      <img src={imageSrc} alt="프로필 사진" />
                      <span>{nickname}</span>
                      <span>{username}</span>
                    </>
                  ) : (
                    <span>{name}</span>
                  )}
                  <CheckIcon src={checkIcon} alt="선택된 필터 표시" checked={isChecked} />
                </FilterDetail>
              </li>
            );
          })
      )}
    </ul>
  );
};

export default SelectedFilterList;
