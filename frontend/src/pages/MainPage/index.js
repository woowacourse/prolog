import React from 'react';
import axios from 'axios';
import GitHubLogin from 'react-github-login';

const MainPage = () => {
  const onSuccess = async (res) => {
    const {code} = res;

    const {access_token} = await axios.post(`http://localhost:8080/login/token`, {
      code,
    });

    console.log(access_token);
  };
  const onFailure = (response) => console.error(response);
  return (
    <div>
      <GitHubLogin
        clientId="f91b56445e08d44adb76"
        onSuccess={onSuccess}
        onFailure={onFailure}
        redirectUri={'http://localhost:3000'}
      />
    </div>
  );
};

export default MainPage;
