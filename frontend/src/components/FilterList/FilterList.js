import { css } from '@emotion/react';
import styled from '@emotion/styled';
import React from 'react';
import { DropdownMenu } from '..';

const DropdownToggledStyle = css`
  &:before {
    position: fixed;
    top: 0;
    right: 0;
    bottom: 0;
    left: 0;
    z-index: 80;
    display: block;
    cursor: default;
    content: ' ';
    background: transparent;
  }
`;

const Container = styled.div`
  background-color: #f4f4f4;
  border: 1px solid #707070;
  border-radius: 2rem;
  padding: 1rem 3rem;
  display: flex;
  font-size: 1.4rem;

  ${({ isDropdownToggled }) => isDropdownToggled && DropdownToggledStyle}

  & > div {
    input[type='search'] {
      font-weight: 500;
      padding: 1rem;
      font-size: 1.4rem;
      border: 1px solid #aaa;
      border-radius: 1rem;
      outline: none;

      :focus {
        border-color: #777;
      }
    }

    & > button {
      display: flex;
      align-items: center;
      height: 100%;
      margin-right: 2rem;
      font-size: 2rem;
      text-align: center;
      color: #666;

      ::after {
        content: '';
        width: 0;
        height: 0;
        transform: translateY(40%);
        margin-left: 0.2rem;
        border-top: 0.6rem solid #666;
        border-bottom: 0.6rem solid transparent;
        border-left: 0.6rem solid transparent;
        border-right: 0.6rem solid transparent;
      }
    }

    :hover {
      color: #222;

      ::after {
        border-top-color: #222;
      }
    }
  }
`;

const FilterList = ({ selectedFilter, setSelectedFilter, filters }) => {
  const closeDropdown = (event) => {
    if (event.target === event.currentTarget) {
      setSelectedFilter('');
    }
  };

  return (
    <Container onClick={closeDropdown} isDropdownToggled={selectedFilter}>
      {Object.entries(filters).map(([key, value]) => (
        <div key={key}>
          <button onClick={() => setSelectedFilter(key)}>{key}</button>
          {selectedFilter === key && (
            <DropdownMenu>
              <li>
                <input type="search" placeholder="filter project" />
              </li>
              {value.map(({ id, name }) => (
                <li key={id}>
                  <button>{name}</button>
                </li>
              ))}
            </DropdownMenu>
          )}
        </div>
      ))}
    </Container>
  );
};

export default FilterList;
