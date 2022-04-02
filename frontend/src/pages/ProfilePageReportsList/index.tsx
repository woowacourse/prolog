import { Ability } from '../ProfilePageReports/AbilityGraph';

import * as Styled from './styles';

type Report = {
  id: number;
  title: string;
  description: string;
  abilityGraph: {
    abilities: Ability[];
  };
  studylogs: [];
  represent: boolean;
  createdAt: string;
};

const defaultReports = {
  reports: [],
  currPage: 1,
  totalSize: 0,
  totalPage: 1,
};

const ProfilePageReportsList = () => {
  //   if (!reportList?.length) {
  //     return (
  //       <Container
  //         css={css`
  //           height: 70vh;

  //           display: flex;
  //           flex-direction: column;
  //           justify-content: center;
  //           align-items: center;

  //           p {
  //             margin: 0;
  //             font-size: 2rem;
  //             line-height: 1.5;
  //           }
  //         `}
  //       >
  //         <p>등록된 리포트가 없습니다.</p>
  //         {isOwner && (
  //           <>
  //             <p>리포트를 작성해주세요.</p>
  //             <AddNewReportLink to={`/${username}/reports/write`}>새 리포트 등록</AddNewReportLink>
  //           </>
  //         )}
  //       </Container>
  //     );
  //   }

  // TODO1. 작성된 리포트가 없다면, 리포트 작성하기 페이지를 보여준다.
  return (
    <Styled.Container>
      <h2>리포트페이지</h2>

      <Styled.TimelineWrapper>
        <Styled.AddNewReportLink to={`/devhyun637/reports/write`}>
          <span>+</span>
        </Styled.AddNewReportLink>

        <Styled.Reports>
          <Styled.Report>
            <Styled.ReportDate>2022.03.01 ~ 2022.03.31</Styled.ReportDate>
            <Styled.ReportDesc>
              Lorem ipsum dolor sit amet, consectetur adipisicing elit. Maiores nobis sit minima
              rerum blanditiis sed, fuga soluta ad quisquam rem voluptates delectus eaque aliquid
              ducimus nemo eum. Qui, sapiente voluptatum!
            </Styled.ReportDesc>
            <Styled.TextButton>리포트 보러가기 {'>'} </Styled.TextButton>
          </Styled.Report>

          <Styled.Report>
            <Styled.ReportDate>2022.03.01 ~ 2022.03.31</Styled.ReportDate>
            <Styled.ReportDesc>
              Lorem ipsum dolor sit amet, consectetur adipisicing elit. Maiores nobis sit minima
              rerum blanditiis sed, fuga soluta ad quisquam rem voluptates delectus eaque aliquid
              ducimus nemo eum. Qui, sapiente voluptatum!
            </Styled.ReportDesc>
            <Styled.TextButton>리포트 보러가기 {'>'} </Styled.TextButton>
          </Styled.Report>

          <Styled.Report>
            <Styled.ReportDate>2022.03.01 ~ 2022.03.31</Styled.ReportDate>
            <Styled.ReportDesc>
              Lorem ipsum dolor sit amet, consectetur adipisicing elit. Maiores nobis sit minima
              rerum blanditiis sed, fuga soluta ad quisquam rem voluptates delectus eaque aliquid
              ducimus nemo eum. Qui, sapiente voluptatum!
            </Styled.ReportDesc>
            <Styled.TextButton>리포트 보러가기 {'>'} </Styled.TextButton>
          </Styled.Report>
        </Styled.Reports>
      </Styled.TimelineWrapper>
    </Styled.Container>
  );
};

export default ProfilePageReportsList;
