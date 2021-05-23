import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import PersonalDetails from './personal-details';
import PersonalDetailsDetail from './personal-details-detail';
import PersonalDetailsUpdate from './personal-details-update';
import PersonalDetailsDeleteDialog from './personal-details-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={PersonalDetailsUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={PersonalDetailsUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={PersonalDetailsDetail} />
      <ErrorBoundaryRoute path={match.url} component={PersonalDetails} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={PersonalDetailsDeleteDialog} />
  </>
);

export default Routes;
