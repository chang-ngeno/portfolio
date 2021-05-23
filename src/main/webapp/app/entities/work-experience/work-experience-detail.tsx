import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, byteSize, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './work-experience.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IWorkExperienceDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const WorkExperienceDetail = (props: IWorkExperienceDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { workExperienceEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="workExperienceDetailsHeading">
          <Translate contentKey="portfolioApp.workExperience.detail.title">WorkExperience</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{workExperienceEntity.id}</dd>
          <dt>
            <span id="title">
              <Translate contentKey="portfolioApp.workExperience.title">Title</Translate>
            </span>
          </dt>
          <dd>{workExperienceEntity.title}</dd>
          <dt>
            <span id="employer">
              <Translate contentKey="portfolioApp.workExperience.employer">Employer</Translate>
            </span>
          </dt>
          <dd>{workExperienceEntity.employer}</dd>
          <dt>
            <span id="startDate">
              <Translate contentKey="portfolioApp.workExperience.startDate">Start Date</Translate>
            </span>
          </dt>
          <dd>
            {workExperienceEntity.startDate ? (
              <TextFormat value={workExperienceEntity.startDate} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="endDate">
              <Translate contentKey="portfolioApp.workExperience.endDate">End Date</Translate>
            </span>
          </dt>
          <dd>
            {workExperienceEntity.endDate ? <TextFormat value={workExperienceEntity.endDate} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="roles">
              <Translate contentKey="portfolioApp.workExperience.roles">Roles</Translate>
            </span>
          </dt>
          <dd>{workExperienceEntity.roles}</dd>
        </dl>
        <Button tag={Link} to="/work-experience" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/work-experience/${workExperienceEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ workExperience }: IRootState) => ({
  workExperienceEntity: workExperience.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(WorkExperienceDetail);
