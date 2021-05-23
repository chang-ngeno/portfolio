import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, openFile, byteSize } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './gallery.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IGalleryDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const GalleryDetail = (props: IGalleryDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { galleryEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="galleryDetailsHeading">
          <Translate contentKey="portfolioApp.gallery.detail.title">Gallery</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{galleryEntity.id}</dd>
          <dt>
            <span id="galleryName">
              <Translate contentKey="portfolioApp.gallery.galleryName">Gallery Name</Translate>
            </span>
          </dt>
          <dd>{galleryEntity.galleryName}</dd>
          <dt>
            <span id="slug">
              <Translate contentKey="portfolioApp.gallery.slug">Slug</Translate>
            </span>
          </dt>
          <dd>{galleryEntity.slug}</dd>
          <dt>
            <span id="photo">
              <Translate contentKey="portfolioApp.gallery.photo">Photo</Translate>
            </span>
          </dt>
          <dd>
            {galleryEntity.photo ? (
              <div>
                {galleryEntity.photoContentType ? (
                  <a onClick={openFile(galleryEntity.photoContentType, galleryEntity.photo)}>
                    <img src={`data:${galleryEntity.photoContentType};base64,${galleryEntity.photo}`} style={{ maxHeight: '30px' }} />
                  </a>
                ) : null}
                <span>
                  {galleryEntity.photoContentType}, {byteSize(galleryEntity.photo)}
                </span>
              </div>
            ) : null}
          </dd>
          <dt>
            <Translate contentKey="portfolioApp.gallery.project">Project</Translate>
          </dt>
          <dd>{galleryEntity.project ? galleryEntity.project.title : ''}</dd>
        </dl>
        <Button tag={Link} to="/gallery" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/gallery/${galleryEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ gallery }: IRootState) => ({
  galleryEntity: gallery.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(GalleryDetail);
