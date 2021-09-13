import { Button } from '../../components';

const ManageAbilityPage = () => {
  return (
    <div>
      <h2>내 역량 관리</h2>
      <ul style={{ display: 'flex', flexDirection: 'column' }}>
        <li>총 개수</li>
        <li style={{ display: 'flex' }}>
          <div>역량 이름</div>
          <desc>설명</desc>
          <Button size="X_SMALL" type="button">
            수정
          </Button>
          <Button size="X_SMALL" type="button">
            삭제
          </Button>
        </li>
        <li style={{ display: 'flex' }}>
          <div>역량 이름</div>
          <desc>설명</desc>
          <Button size="X_SMALL" type="button">
            수정
          </Button>
          <Button size="X_SMALL" type="button">
            삭제
          </Button>
        </li>
      </ul>
    </div>
  );
};

export default ManageAbilityPage;
