import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './hobby.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IHobbyDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const HobbyDetail = (props: IHobbyDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { hobbyEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="hobbyDetailsHeading">
          <Translate contentKey="portfolioApp.hobby.detail.title">Hobby</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{hobbyEntity.id}</dd>
          <dt>
            <span id="slug">
              <Translate contentKey="portfolioApp.hobby.slug">Slug</Translate>
            </span>
          </dt>
          <dd>{hobbyEntity.slug}</dd>
        </dl>
        <Button tag={Link} to="/hobby" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/hobby/${hobbyEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ hobby }: IRootState) => ({
  hobbyEntity: hobby.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(HobbyDetail);
