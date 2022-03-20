import axios, { AxiosError, Method } from 'axios';

import { BASE_URL } from '../configs/environment';
import LOCAL_STORAGE_KEY from '../constants/localStorage';
import { getLocalStorageItem } from '../utils/localStorage';

export interface ErrorData {
  code: number;
  message: string;
}
interface Config {
  method?: Method;
  url: string;
  data?: any;
}

export class API {
  public get({ url }) {
    return this.request({ method: 'get', url });
  }

  public post({ url, data }) {
    return this.request({ method: 'post', url, data });
  }

  public put({ url, data }) {
    return this.request({ method: 'put', url, data });
  }

  public delete({ url }) {
    return this.request({ method: 'delete', url });
  }

  private async request({ method, url, data }: Config) {
    try {
      const response = await axios({
        method,
        url: `${this.baseUrl}${url}`,
        headers: {
          Authorization: `Bearer ${this.accessToken}`,
        },
        data: { ...data },
      });

      return response.data;
    } catch (error) {
      throw (error as AxiosError).response?.data;
    }
  }

  constructor({ baseUrl, accessToken }) {
    this.baseUrl = baseUrl;
    this.accessToken = accessToken;
  }

  public baseUrl: string;
  public accessToken: string;
}

const AbilityAPI = new API({
  accessToken: getLocalStorageItem(LOCAL_STORAGE_KEY.ACCESS_TOKEN),
  baseUrl: BASE_URL,
});

const AbilityRequest = {
  getAbilityList: ({ url }: Config) => AbilityAPI.get({ url }),
  addAbility: ({ url, data }: Config) => AbilityAPI.post({ url, data }),
  updateAbility: ({ url, data }: Config) => AbilityAPI.put({ url, data }),
  deleteAbility: ({ url }: Config) => AbilityAPI.delete({ url }),
};

export default AbilityRequest;
