import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import HireMeSubject from './hire-me-subject';
import HireMeSubjectDetail from './hire-me-subject-detail';
import HireMeSubjectUpdate from './hire-me-subject-update';
import HireMeSubjectDeleteDialog from './hire-me-subject-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={HireMeSubjectUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={HireMeSubjectUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={HireMeSubjectDetail} />
      <ErrorBoundaryRoute path={match.url} component={HireMeSubject} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={HireMeSubjectDeleteDialog} />
  </>
);

export default Routes;
