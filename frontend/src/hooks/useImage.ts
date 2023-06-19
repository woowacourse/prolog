import { requestImageUpload } from '../service/requests';
import useSnackBar from './useSnackBar';

const useImage = () => {
  const { openSnackBar } = useSnackBar();

  const uploadImage = async (blob, callback) => {
    if (blob.size > 1024 * 1024 * 10) return openSnackBar('10MB 이하의 이미지를 업로드해주세요.');

    try {
      const imageData = new FormData();
      imageData.append('file', blob);

      const response = await requestImageUpload(imageData);

      if (response.status >= 400) throw new Error('이미지 업로드에 실패하였습니다');

      const { imageUrl } = await response.data;

      callback(imageUrl, blob.name);
    } catch (error) {
      console.error(error);
      openSnackBar('해당 이미지를 업로드 할 수 없습니다.');
    }
  };

  return { uploadImage };
};

export default useImage;
