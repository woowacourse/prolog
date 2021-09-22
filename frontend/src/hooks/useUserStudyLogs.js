import { useEffect, useState } from 'react';
import { requestGetPosts } from '../service/requests';

const useUserStudyLog = ({ levels, username }) => {
  const [selectedLevelName, setSelectedLevelName] = useState('');
  const [posts, setPosts] = useState([]);

  useEffect(() => {
    const getPosts = async () => {
      try {
        const selectedLevelId = levels.find((level) => level.name === selectedLevelName)?.id;

        if (selectedLevelId) {
          const query = {
            type: 'searchParams',
            data: `levels=${selectedLevelId}&usernames=${username}`,
          };

          const response = await requestGetPosts(query);

          if (!response.ok) {
            throw new Error(response.status);
          }

          const posts = await response.json();

          setPosts(posts.data);
        }
      } catch (error) {
        console.error(error);
      }
    };

    if (levels) {
      getPosts();
    }
  }, [selectedLevelName, levels, username]);

  return { selectedLevelName, setSelectedLevelName, posts };
};

export default useUserStudyLog;
