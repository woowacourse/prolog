declare namespace Prolog {
  type Author = {
    username: string;
    nickname: string;
    imageUrl: string;
  };

  type Mission = { id: number; name: string };
  type Tag = { id: number; name: string };

  interface StudyLog {
    id: number;
    author: Author;
    mission: Mission;
    title: string;
    tags: Tag[];
  }
}
