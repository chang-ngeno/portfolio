import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './social-media.reducer';
import { ISocialMedia } from 'app/shared/model/social-media.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ISocialMediaProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const SocialMedia = (props: ISocialMediaProps) => {
  useEffect(() => {
    props.getEntities();
  }, []);

  const handleSyncList = () => {
    props.getEntities();
  };

  const { socialMediaList, match, loading } = props;
  return (
    <div>
      <h2 id="social-media-heading" data-cy="SocialMediaHeading">
        <Translate contentKey="portfolioApp.socialMedia.home.title">Social Medias</Translate>
        <div className="d-flex justify-content-end">
          <Button className="mr-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="portfolioApp.socialMedia.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="portfolioApp.socialMedia.home.createLabel">Create new Social Media</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {socialMediaList && socialMediaList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="portfolioApp.socialMedia.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="portfolioApp.socialMedia.username">Username</Translate>
                </th>
                <th>
                  <Translate contentKey="portfolioApp.socialMedia.urlLink">Url Link</Translate>
                </th>
                <th>
                  <Translate contentKey="portfolioApp.socialMedia.published">Published</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {socialMediaList.map((socialMedia, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${socialMedia.id}`} color="link" size="sm">
                      {socialMedia.id}
                    </Button>
                  </td>
                  <td>{socialMedia.username}</td>
                  <td>{socialMedia.urlLink}</td>
                  <td>{socialMedia.published ? 'true' : 'false'}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${socialMedia.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${socialMedia.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${socialMedia.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
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
              <Translate contentKey="portfolioApp.socialMedia.home.notFound">No Social Medias found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

const mapStateToProps = ({ socialMedia }: IRootState) => ({
  socialMediaList: socialMedia.entities,
  loading: socialMedia.loading,
});

const mapDispatchToProps = {
  getEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(SocialMedia);
