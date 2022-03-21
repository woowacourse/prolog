import { useEffect } from 'react';
import { useLocation } from 'react-router-dom';
import ReactGA from 'react-ga4';

const TRACKING_ID = process.env.REACT_APP_GA_TRACKING_ID;

const useGoogleAnalytics = () => {
  const location = useLocation();

  useEffect(() => {
    if (TRACKING_ID && TRACKING_ID.length > 1) {
      ReactGA.initialize(TRACKING_ID);
    }
  }, []);

  useEffect(() => {
    if (TRACKING_ID && TRACKING_ID.length > 1) {
      const currentPath = location.pathname + location.search + location.hash;
      ReactGA.send({ hitType: 'pageview', page: currentPath });
    }
  }, [location]);
};

export { useGoogleAnalytics };
