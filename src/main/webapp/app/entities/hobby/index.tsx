import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Hobby from './hobby';
import HobbyDetail from './hobby-detail';
import HobbyUpdate from './hobby-update';
import HobbyDeleteDialog from './hobby-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={HobbyUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={HobbyUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={HobbyDetail} />
      <ErrorBoundaryRoute path={match.url} component={Hobby} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={HobbyDeleteDialog} />
  </>
);

export default Routes;
