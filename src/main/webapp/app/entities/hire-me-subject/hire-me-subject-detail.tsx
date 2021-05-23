import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './hire-me-subject.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IHireMeSubjectDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const HireMeSubjectDetail = (props: IHireMeSubjectDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { hireMeSubjectEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="hireMeSubjectDetailsHeading">
          <Translate contentKey="portfolioApp.hireMeSubject.detail.title">HireMeSubject</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{hireMeSubjectEntity.id}</dd>
          <dt>
            <span id="subject">
              <Translate contentKey="portfolioApp.hireMeSubject.subject">Subject</Translate>
            </span>
          </dt>
          <dd>{hireMeSubjectEntity.subject}</dd>
        </dl>
        <Button tag={Link} to="/hire-me-subject" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/hire-me-subject/${hireMeSubjectEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ hireMeSubject }: IRootState) => ({
  hireMeSubjectEntity: hireMeSubject.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(HireMeSubjectDetail);
