import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './social-media.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ISocialMediaDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const SocialMediaDetail = (props: ISocialMediaDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { socialMediaEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="socialMediaDetailsHeading">
          <Translate contentKey="portfolioApp.socialMedia.detail.title">SocialMedia</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{socialMediaEntity.id}</dd>
          <dt>
            <span id="username">
              <Translate contentKey="portfolioApp.socialMedia.username">Username</Translate>
            </span>
          </dt>
          <dd>{socialMediaEntity.username}</dd>
          <dt>
            <span id="urlLink">
              <Translate contentKey="portfolioApp.socialMedia.urlLink">Url Link</Translate>
            </span>
          </dt>
          <dd>{socialMediaEntity.urlLink}</dd>
          <dt>
            <span id="published">
              <Translate contentKey="portfolioApp.socialMedia.published">Published</Translate>
            </span>
          </dt>
          <dd>{socialMediaEntity.published ? 'true' : 'false'}</dd>
        </dl>
        <Button tag={Link} to="/social-media" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/social-media/${socialMediaEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ socialMedia }: IRootState) => ({
  socialMediaEntity: socialMedia.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(SocialMediaDetail);
