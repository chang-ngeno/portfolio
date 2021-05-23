import React from 'react';
import { Switch } from 'react-router-dom';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import PersonalDetails from './personal-details';
import SocialMedia from './social-media';
import Project from './project';
import Gallery from './gallery';
import WorkExperience from './work-experience';
import Education from './education';
import Hobby from './hobby';
import HireMeSubject from './hire-me-subject';
/* jhipster-needle-add-route-import - JHipster will add routes here */

const Routes = ({ match }) => (
  <div>
    <Switch>
      {/* prettier-ignore */}
      <ErrorBoundaryRoute path={`${match.url}personal-details`} component={PersonalDetails} />
      <ErrorBoundaryRoute path={`${match.url}social-media`} component={SocialMedia} />
      <ErrorBoundaryRoute path={`${match.url}project`} component={Project} />
      <ErrorBoundaryRoute path={`${match.url}gallery`} component={Gallery} />
      <ErrorBoundaryRoute path={`${match.url}work-experience`} component={WorkExperience} />
      <ErrorBoundaryRoute path={`${match.url}education`} component={Education} />
      <ErrorBoundaryRoute path={`${match.url}hobby`} component={Hobby} />
      <ErrorBoundaryRoute path={`${match.url}hire-me-subject`} component={HireMeSubject} />
      {/* jhipster-needle-add-route-path - JHipster will add routes here */}
    </Switch>
  </div>
);

export default Routes;
