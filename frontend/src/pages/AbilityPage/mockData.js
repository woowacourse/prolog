import { COLOR } from '../../constants';

const abilityList = [
  {
    id: 0,
    name: '자바스크립트',
    description: '자바스크립트 기초',
    color: COLOR.RED_300,
    isParent: true,
    children: [
      {
        id: 1,
        name: '비동기',
        description: '비동기, 프로미스',
        color: COLOR.RED_300,
        isParent: false,
      },
      {
        id: 2,
        name: '비동기',
        description:
          '비동기, 프로미스비동기, 프로미스비동기, 프로미스비동기, 프로미스비동기, 프로미스비동기, 프로미스비동기, 프로미스비동기, 프로미스비동기, 프로미스비동기, 프로미스',
        color: COLOR.RED_300,
        isParent: false,
      },
    ],
  },
  {
    id: 3,
    name: '자바',
    description: '자바 기초',
    color: COLOR.LIGHT_BLUE_300,
    isParent: true,
    children: [
      {
        id: 4,
        name: '비동기',
        description: '비동기, 프로미스',
        color: COLOR.LIGHT_BLUE_300,
        isParent: false,
      },
    ],
  },
];

export { abilityList };
