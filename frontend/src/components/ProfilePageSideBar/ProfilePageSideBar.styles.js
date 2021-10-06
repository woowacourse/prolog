const NicknameInput = styled.input`
  margin: 0.5rem 1.2rem;
  margin-right: 0;
  padding: 0.2rem 0.5rem;
  font-size: 1.6rem;
  outline: none;
  border-radius: 0.5rem;
  border: 1px solid ${COLOR.LIGHT_GRAY_900};
  width: 12rem;
`;

const NicknameWrapper = styled.div`
  display: flex;
  justify-content: space-between;
  word-break: break-all;
  align-items: center;
`;

const EditButtonStyle = css`
  border: 1px solid ${COLOR.LIGHT_GRAY_200};
  background-color: ${COLOR.WHITE};
  margin: 0 0.5rem;
  width: 5rem;
  height: 3rem;
  flex-shrink: 0;

  &:hover {
    background-color: ${COLOR.LIGHT_GRAY_300};
  }
`;

export {
  Profile,
  Image,
  Nickname,
  Role,
  MenuList,
  MenuItem,
  MenuButton,
  Container,
  NicknameInput,
  NicknameWrapper,
  EditButtonStyle,
};