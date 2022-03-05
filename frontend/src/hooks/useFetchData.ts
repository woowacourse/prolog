import { useState, useEffect } from 'react';
import { getResponseData } from '../utils/response';

type ErrorType = { message: string; code: number };

const isError = (arg: any): arg is ErrorType =>
  arg && arg.message === 'string' && arg.code === 'number';

interface FetchOptions<T> {
  /**
   * @description 요청 성공 시 실행할 함수
   */
  onSuccess?: (data: T) => void;
  /**
   * @description 요청 실패 시 실행할 함수
   */
  onError?: (data: unknown) => void;
  /**
   * @description 요청 완료 시 실행할 함수
   */
  onFinish?: () => void;
  /**
   * @description 첫 fetch 여부
   * @default true;
   */
  initialFetch?: boolean;
}

/**
 * @description 데이터를 fetch 하는 커스텀 훅, 응답값과, 에러, 로딩 상태 및 refetch 수단을 반환합니다.
 * @param defaultValue
 * @param callback 데이터 fetch 함수 (ex, fetch, axios)
 * @param options {onSuccess: 성공 시 실행할 콜백, onError: 에러 시 실행할 콜백, onFinish: 요청 종료시 실행할 콜백}
 * @returns { response, error, fetchData }
 */

const useFetchData = <T = unknown>(
  defaultValue: T,
  callback: () => Promise<Response>,
  options?: FetchOptions<T>
) => {
  const { onSuccess, onError, onFinish, initialFetch = true } = options ?? {};

  const [response, setResponse] = useState(defaultValue);
  const [error, setError] = useState('');

  const [isLoading, setIsLoading] = useState(initialFetch);

  const fetchData = async () => {
    try {
      const response = await callback();

      if (!response) {
        return;
      }

      if (!response.ok) {
        throw new Error(await response.text());
      }

      const responseData = await getResponseData(response);

      setResponse(responseData);
      onSuccess?.(responseData);
    } catch (error) {
      if (isError(error)) {
        const errorResponse = JSON.parse(error.message);

        console.error(errorResponse);
        setError(errorResponse.code);
        onError?.({ code: errorResponse.code, message: errorResponse.message });
      }
    } finally {
      onFinish?.();
      setIsLoading(false);
    }
  };

  useEffect(() => {
    if (isLoading) {
      fetchData();
    }
  }, [isLoading]);

  return { response, error, refetch: () => setIsLoading(true), isLoading };
};

export default useFetchData;
