const params = {
  client_id: `${process.env.REACT_APP_GITHUB_CLIENT_ID}`,
  redirect_uri: `${process.env.REACT_APP_URL}`,
};

const githubOauthURL = 'https://github.com/login/oauth/authorize';
const githubOauthQueries = new URLSearchParams(params).toString();

const GithubLogin = ({ children }) => (
  <a href={`${githubOauthURL}?${githubOauthQueries}`}>{children}</a>
);

export default GithubLogin;
