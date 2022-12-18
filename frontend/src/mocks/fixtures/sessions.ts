export default {
  data: [
    {
      curriculumId: 1,
      sessions: [
        {
          id: 1,
          name: '백엔드 레벨1',
        },
        {
          id: 2,
          name: '백엔드 레벨2',
        },
        {
          id: 3,
          name: '백엔드 레벨3',
        },
      ],
    },
    {
      curriculumId: 2,
      sessions: [
        {
          id: 4,
          name: '프론트엔드 레벨1',
        },
        {
          id: 5,
          name: '프론트엔드 레벨2',
        },
        {
          id: 6,
          name: '프론트엔드 레벨3',
        },
      ],
    },
  ],
  findSessionByCurriculum(curriculumId: string | readonly string[]) {
    const filteredData = this.data.find((item) => item.curriculumId === Number(curriculumId));

    return {
      sessions: filteredData?.sessions,
    };
  },
};
