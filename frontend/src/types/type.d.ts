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
    content: string;
    mission: Mission;
    title: string;
    tags: Tag[];
    createdAt: Date;
    updatedAt?: Date;
    isRead: boolean;
    isScrapped: boolean;
  }
}
