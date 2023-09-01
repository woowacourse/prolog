import { AxiosPromise, AxiosResponse } from 'axios';
import { client } from '.';
import {
  Mission,
  Session,
  Studylog,
  StudylogForm,
  Tag,
  TempSavedStudyLog,
  TempSavedStudyLogForm,
} from '../models/Studylogs';
import { Nullable } from '../types/utils';
import { createAxiosInstance } from '../utils/axiosInstance';

const instanceWithoutToken = createAxiosInstance();
const instanceWithToken = (accessToken) => createAxiosInstance({ accessToken });

export const requestGetPopularStudylogs = ({ accessToken }: { accessToken?: string }) => {
  if (accessToken) {
    return instanceWithToken(accessToken).get('/studylogs/popular');
  }
  return instanceWithoutToken.get('/studylogs/popular');
};

export type StudylogQuery =
  | { type: 'searchParams'; data: URLSearchParams }
  | {
      type: 'filter';
      data: {
        postQueryParams: { key: string; value: string }[];
        filterQuery: { filterType: string; filterDetailId: string }[];
      };
    };

/**
 * @description 학습로그 조회 API, query type 이 복잡하므로 StudylogQuery type 참고
 * @param query 조회 조건으로 searchParams 혹은 filter type 으로 분기됨.
 * @param accessToken 유저의 accessToken
 * @todo query 간결화
 */
export const requestGetStudylogs = ({
  query,
  accessToken,
}: {
  query?: StudylogQuery;
  accessToken?: string;
}) => {
  const instance = accessToken ? instanceWithToken(accessToken) : instanceWithoutToken;

  if (!query) {
    return instance.get('/studylogs');
  }

  if (query.type === 'searchParams') {
    return instance.get(`/studylogs?${query.data.toString()}`);
  }

  if (query.type === 'filter') {
    const searchParams = Object.entries(query?.data?.postQueryParams).map(
      ([key, value]) => `${key}=${value}`
    );
    const filterQuery = query.data.filterQuery.length
      ? query.data.filterQuery.map(
          ({ filterType, filterDetailId }) => `${filterType}=${filterDetailId}`
        )
      : [];

    return instance.get(`/studylogs?${[...filterQuery, ...searchParams].join('&')}`);
  }
};

export type ResponseError = { code: number; messsage: string };

export const requestGetTags = (): Promise<AxiosResponse<Tag[]>> =>
  instanceWithoutToken.get('/tags');

export const requestGetMissions = ({ accessToken }): Promise<AxiosResponse<Mission[]>> =>
  instanceWithToken(accessToken).get('/missions/mine');

export const requestGetSessions = ({ accessToken }): Promise<AxiosResponse<Session[]>> =>
  instanceWithToken(accessToken).get('/sessions/mine');

export const requestGetStudylog = ({
  id,
  accessToken,
}: {
  id: string;
  accessToken: string;
}): AxiosPromise<AxiosResponse<Studylog>> =>
  instanceWithToken(accessToken).get<AxiosResponse<Studylog>>(`/studylogs/${id}`);

/** 작성 및 수정 **/
export const requestPostStudylog = ({
  accessToken,
  data,
}: {
  accessToken: string;
  data: StudylogForm;
}): AxiosPromise<AxiosResponse<null>> => instanceWithToken(accessToken).post('/studylogs', data);

export const requestEditStudylog = ({
  id,
  data,
  accessToken,
}: {
  id: string;
  accessToken: string;
  data: StudylogForm;
}): AxiosPromise<AxiosResponse<null>> =>
  instanceWithToken(accessToken).put(`/studylogs/${id}`, data);

/** 임시 저장 **/
export const requestGetTempSavedStudylog = async () => {
  const { data } = await client.get<Nullable<TempSavedStudyLog>>('/studylogs/temp');

  return data;
};

export const requestPostTempSavedStudylog = (data: TempSavedStudyLogForm) =>
  client.put('/studylogs/temp', data);
