import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './hire-me-subject.reducer';
import { IHireMeSubject } from 'app/shared/model/hire-me-subject.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IHireMeSubjectProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const HireMeSubject = (props: IHireMeSubjectProps) => {
  useEffect(() => {
    props.getEntities();
  }, []);

  const handleSyncList = () => {
    props.getEntities();
  };

  const { hireMeSubjectList, match, loading } = props;
  return (
    <div>
      <h2 id="hire-me-subject-heading" data-cy="HireMeSubjectHeading">
        <Translate contentKey="portfolioApp.hireMeSubject.home.title">Hire Me Subjects</Translate>
        <div className="d-flex justify-content-end">
          <Button className="mr-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="portfolioApp.hireMeSubject.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="portfolioApp.hireMeSubject.home.createLabel">Create new Hire Me Subject</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {hireMeSubjectList && hireMeSubjectList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="portfolioApp.hireMeSubject.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="portfolioApp.hireMeSubject.subject">Subject</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {hireMeSubjectList.map((hireMeSubject, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${hireMeSubject.id}`} color="link" size="sm">
                      {hireMeSubject.id}
                    </Button>
                  </td>
                  <td>{hireMeSubject.subject}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${hireMeSubject.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${hireMeSubject.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`${match.url}/${hireMeSubject.id}/delete`}
                        color="danger"
                        size="sm"
                        data-cy="entityDeleteButton"
                      >
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && (
            <div className="alert alert-warning">
              <Translate contentKey="portfolioApp.hireMeSubject.home.notFound">No Hire Me Subjects found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

const mapStateToProps = ({ hireMeSubject }: IRootState) => ({
  hireMeSubjectList: hireMeSubject.entities,
  loading: hireMeSubject.loading,
});

const mapDispatchToProps = {
  getEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(HireMeSubject);
