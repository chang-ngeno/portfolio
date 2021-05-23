import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import SocialMedia from './social-media';
import SocialMediaDetail from './social-media-detail';
import SocialMediaUpdate from './social-media-update';
import SocialMediaDeleteDialog from './social-media-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={SocialMediaUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={SocialMediaUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={SocialMediaDetail} />
      <ErrorBoundaryRoute path={match.url} component={SocialMedia} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={SocialMediaDeleteDialog} />
  </>
);

export default Routes;
