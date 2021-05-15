import {useEffect, useState} from "react";
import axios from 'axios';

const App = () => {
  const [studyLogs, setStudyLogs] = useState([]);

  const fetchStudyLogs = async () => {
    try {
      const response = await axios.get('/study-logs');
      setStudyLogs(response.data);
    } catch (error) {
      console.error(error.message);
    }
  }

  useEffect(() => {
    fetchStudyLogs();
  }, []);

  return (
      <div>
        {studyLogs.map((studyLog, index) => {
          return (
              <div key={index}>
                {studyLog.title}
              </div>
          );
        })}
      </div>
  );
};

export default App;
