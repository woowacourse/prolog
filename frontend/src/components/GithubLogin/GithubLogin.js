const params = {
  client_id: 'f91b56445e08d44adb76',
  redirect_uri: 'http://localhost:3000',
};

const githubOauthURL = 'https://github.com/login/oauth/authorize';
const githubOauthQueries = new URLSearchParams(params).toString();

const GithubLogin = ({ children }) => (
  <a href={`${githubOauthURL}?${githubOauthQueries}`}>{children}</a>
);

export default GithubLogin;
