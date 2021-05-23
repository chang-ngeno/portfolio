import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { setFileData, byteSize, Translate, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, setBlob, reset } from './work-experience.reducer';
import { IWorkExperience } from 'app/shared/model/work-experience.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IWorkExperienceUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const WorkExperienceUpdate = (props: IWorkExperienceUpdateProps) => {
  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const { workExperienceEntity, loading, updating } = props;

  const { roles } = workExperienceEntity;

  const handleClose = () => {
    props.history.push('/work-experience');
  };

  useEffect(() => {
    if (!isNew) {
      props.getEntity(props.match.params.id);
    }
  }, []);

  const onBlobChange = (isAnImage, name) => event => {
    setFileData(event, (contentType, data) => props.setBlob(name, data, contentType), isAnImage);
  };

  const clearBlob = name => () => {
    props.setBlob(name, undefined, undefined);
  };

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    values.startDate = convertDateTimeToServer(values.startDate);
    values.endDate = convertDateTimeToServer(values.endDate);

    if (errors.length === 0) {
      const entity = {
        ...workExperienceEntity,
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
          <h2 id="portfolioApp.workExperience.home.createOrEditLabel" data-cy="WorkExperienceCreateUpdateHeading">
            <Translate contentKey="portfolioApp.workExperience.home.createOrEditLabel">Create or edit a WorkExperience</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : workExperienceEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="work-experience-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="work-experience-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="titleLabel" for="work-experience-title">
                  <Translate contentKey="portfolioApp.workExperience.title">Title</Translate>
                </Label>
                <AvField
                  id="work-experience-title"
                  data-cy="title"
                  type="text"
                  name="title"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="employerLabel" for="work-experience-employer">
                  <Translate contentKey="portfolioApp.workExperience.employer">Employer</Translate>
                </Label>
                <AvField
                  id="work-experience-employer"
                  data-cy="employer"
                  type="text"
                  name="employer"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="startDateLabel" for="work-experience-startDate">
                  <Translate contentKey="portfolioApp.workExperience.startDate">Start Date</Translate>
                </Label>
                <AvInput
                  id="work-experience-startDate"
                  data-cy="startDate"
                  type="datetime-local"
                  className="form-control"
                  name="startDate"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.workExperienceEntity.startDate)}
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="endDateLabel" for="work-experience-endDate">
                  <Translate contentKey="portfolioApp.workExperience.endDate">End Date</Translate>
                </Label>
                <AvInput
                  id="work-experience-endDate"
                  data-cy="endDate"
                  type="datetime-local"
                  className="form-control"
                  name="endDate"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.workExperienceEntity.endDate)}
                />
              </AvGroup>
              <AvGroup>
                <Label id="rolesLabel" for="work-experience-roles">
                  <Translate contentKey="portfolioApp.workExperience.roles">Roles</Translate>
                </Label>
                <AvInput
                  id="work-experience-roles"
                  data-cy="roles"
                  type="textarea"
                  name="roles"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/work-experience" replace color="info">
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
  workExperienceEntity: storeState.workExperience.entity,
  loading: storeState.workExperience.loading,
  updating: storeState.workExperience.updating,
  updateSuccess: storeState.workExperience.updateSuccess,
});

const mapDispatchToProps = {
  getEntity,
  updateEntity,
  setBlob,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(WorkExperienceUpdate);
