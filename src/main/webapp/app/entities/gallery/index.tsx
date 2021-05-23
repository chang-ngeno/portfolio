import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Gallery from './gallery';
import GalleryDetail from './gallery-detail';
import GalleryUpdate from './gallery-update';
import GalleryDeleteDialog from './gallery-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={GalleryUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={GalleryUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={GalleryDetail} />
      <ErrorBoundaryRoute path={match.url} component={Gallery} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={GalleryDeleteDialog} />
  </>
);

export default Routes;
