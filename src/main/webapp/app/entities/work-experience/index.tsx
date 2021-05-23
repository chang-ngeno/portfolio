import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import WorkExperience from './work-experience';
import WorkExperienceDetail from './work-experience-detail';
import WorkExperienceUpdate from './work-experience-update';
import WorkExperienceDeleteDialog from './work-experience-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={WorkExperienceUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={WorkExperienceUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={WorkExperienceDetail} />
      <ErrorBoundaryRoute path={match.url} component={WorkExperience} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={WorkExperienceDeleteDialog} />
  </>
);

export default Routes;
