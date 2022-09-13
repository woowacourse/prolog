import { Editor as ToastEditor } from '@toast-ui/react-editor';
import { useEffect, RefObject } from 'react';

const useBeforeunload = (editorContentRef: RefObject<ToastEditor>) => {
  useEffect(() => {
    window.addEventListener('beforeunload', (event) => {
      const content = editorContentRef.current?.getInstance().getMarkdown() || '';

      if (content) {
        event.preventDefault();
        event.returnValue = '';
      }
    });
  }, []);
};

export default useBeforeunload;
