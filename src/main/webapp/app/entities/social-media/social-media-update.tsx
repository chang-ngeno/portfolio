import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, reset } from './social-media.reducer';
import { ISocialMedia } from 'app/shared/model/social-media.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface ISocialMediaUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const SocialMediaUpdate = (props: ISocialMediaUpdateProps) => {
  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const { socialMediaEntity, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/social-media');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...socialMediaEntity,
        ...values,
      };

      if (isNew) {
        props.createEntity(entity);
      } else {
        props.updateEntity(entity);
      }
    }
  };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="portfolioApp.socialMedia.home.createOrEditLabel" data-cy="SocialMediaCreateUpdateHeading">
            <Translate contentKey="portfolioApp.socialMedia.home.createOrEditLabel">Create or edit a SocialMedia</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : socialMediaEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="social-media-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="social-media-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="usernameLabel" for="social-media-username">
                  <Translate contentKey="portfolioApp.socialMedia.username">Username</Translate>
                </Label>
                <AvField
                  id="social-media-username"
                  data-cy="username"
                  type="text"
                  name="username"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="urlLinkLabel" for="social-media-urlLink">
                  <Translate contentKey="portfolioApp.socialMedia.urlLink">Url Link</Translate>
                </Label>
                <AvField
                  id="social-media-urlLink"
                  data-cy="urlLink"
                  type="text"
                  name="urlLink"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup check>
                <Label id="publishedLabel">
                  <AvInput id="social-media-published" data-cy="published" type="checkbox" className="form-check-input" name="published" />
                  <Translate contentKey="portfolioApp.socialMedia.published">Published</Translate>
                </Label>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/social-media" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </AvForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

const mapStateToProps = (storeState: IRootState) => ({
  socialMediaEntity: storeState.socialMedia.entity,
  loading: storeState.socialMedia.loading,
  updating: storeState.socialMedia.updating,
  updateSuccess: storeState.socialMedia.updateSuccess,
});

const mapDispatchToProps = {
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(SocialMediaUpdate);
