import React, { useEffect, useState } from 'react';

import useFetch from '../../hooks/useFetch';
import { requestGetFilters, requestGetMissions, requestGetPosts } from '../../service/requests';
import { Button, Modal, SelectBox } from '../../components';
import { COLOR } from '../../constants';
import { Checkbox } from './style';
import {
  Container,
  TitleContainer,
  SelectBoxContainer,
  StudyLogListContainer,
  StudyLog,
} from './StudyLogModal.styles';

const StudyLogModal = ({ onModalClose, username }) => {
  const [filters] = useFetch([], requestGetFilters);
  const levels = filters.levels;

  const [selectedLevelName, setSelectedLevelName] = useState('');
  const [posts, setPosts] = useState([]);

  // TODO : 부모 컴포넌트로 옮기기
  const [selectedPost, setSelectedPost] = useState([]);

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

  const onToggleStudyLog = (id) => {
    console.log(selectedPost);

    if (selectedPost.includes(id)) {
      const index = selectedPost.indexOf(id);
      setSelectedPost([...selectedPost.slice(0, index), ...selectedPost.slice(index + 1)]);
    } else {
      setSelectedPost((prevSelectedPost) => [...prevSelectedPost, id]);
    }
  };

  return (
    <Modal width="50%" height="80%">
      <Container>
        <TitleContainer>
          <h2 id="dialog1Title">역량별 학습로그 등록하기</h2>
          <button type="button" onClick={onModalClose}>
            닫기
          </button>
        </TitleContainer>

        <SelectBoxContainer>
          <h3>레벨</h3>
          <SelectBox
            options={levels?.map((level) => level.name)}
            selectedOption={selectedLevelName}
            setSelectedOption={setSelectedLevelName}
            title="우아한테크코스 과정 레벨 목록입니다."
            name="level"
          />
        </SelectBoxContainer>

        <StudyLogListContainer>
          <span>
            ✅ {selectedPost.length}개 선택 (총 {posts.length}개)
          </span>
          {posts.length === 0 ? (
            <p>해당 레벨의 학습로그가 없습니다.</p>
          ) : (
            <ul>
              {posts.map((post) => (
                <StudyLog key={post.id} isChecked={selectedPost.includes(post.id)}>
                  <label>
                    <Checkbox type="checkbox" onClick={() => onToggleStudyLog(post.id)} />
                    <div>
                      <p>{post.mission.level.name}</p>
                      <h4>{post.title}</h4>
                    </div>
                  </label>
                </StudyLog>
              ))}
            </ul>
          )}
        </StudyLogListContainer>

        <Button size="X_SMALL" css={{ backgroundColor: `${COLOR.LIGHT_BLUE_500}` }}>
          등록
        </Button>
      </Container>
    </Modal>
  );
};

export default StudyLogModal;
