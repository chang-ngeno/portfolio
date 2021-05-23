import './projects.css';

import React from 'react';
import { Translate } from 'react-jhipster';
import { connect } from 'react-redux';
import { Row, Col } from 'reactstrap';

export type IProjectsProp = StateProps;

export const Projects = (props: IProjectsProp) => {
  const { message } = props;

  return (
    <Row>
      <Col md="1" />
      <Col md="10">
        <h6 className="text-right">{message}</h6>
        <h1 className="text-capitalize text-center">
          <Translate contentKey="projects.title">Title</Translate>
        </h1>
        <h5 className="text-center">
          <Translate contentKey="projects.subtitle">Subtitle</Translate>
        </h5>
        <hr />
        <main className="page projects-page">
          <section className="portfolio-block projects-cards">
            <div className="container">
              <div className="heading">
                <h2>Recent Work</h2>
              </div>
              <div className="row">
                <div className="col-md-6 col-lg-4">
                  <div className="card border-0">
                    <a href="#">
                      <img src="assets/img/nature/image1.jpg" alt="Card Image" className="card-img-top scale-on-hover" />
                    </a>
                    <div className="card-body">
                      <h6>
                        <a href="#">Lorem Ipsum</a>
                      </h6>
                      <p className="text-muted card-text">Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nunc quam urna.</p>
                    </div>
                  </div>
                </div>
                <div className="col-md-6 col-lg-4">
                  <div className="card border-0">
                    <a href="#">
                      <img src="assets/img/nature/image2.jpg" alt="Card Image" className="card-img-top scale-on-hover" />
                    </a>
                    <div className="card-body">
                      <h6>
                        <a href="#">Lorem Ipsum</a>
                      </h6>
                      <p className="text-muted card-text">Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nunc quam urna.</p>
                    </div>
                  </div>
                </div>
                <div className="col-md-6 col-lg-4">
                  <div className="card border-0">
                    <a href="#">
                      <img src="assets/img/nature/image3.jpg" alt="Card Image" className="card-img-top scale-on-hover" />
                    </a>
                    <div className="card-body">
                      <h6>
                        <a href="#">Lorem Ipsum</a>
                      </h6>
                      <p className="text-muted card-text">Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nunc quam urna.</p>
                    </div>
                  </div>
                </div>
                <div className="col-md-6 col-lg-4">
                  <div className="card border-0">
                    <a href="#">
                      <img src="assets/img/nature/image4.jpg" alt="Card Image" className="card-img-top scale-on-hover" />
                    </a>
                    <div className="card-body">
                      <h6>
                        <a href="#">Lorem Ipsum</a>
                      </h6>
                      <p className="text-muted card-text">Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nunc quam urna.</p>
                    </div>
                  </div>
                </div>
                <div className="col-md-6 col-lg-4">
                  <div className="card border-0">
                    <a href="#">
                      <img src="assets/img/nature/image5.jpg" alt="Card Image" className="card-img-top scale-on-hover" />
                    </a>
                    <div className="card-body">
                      <h6>
                        <a href="#">Lorem Ipsum</a>
                      </h6>
                      <p className="text-muted card-text">Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nunc quam urna.</p>
                    </div>
                  </div>
                </div>
                <div className="col-md-6 col-lg-4">
                  <div className="card border-0">
                    <a href="#">
                      <img src="assets/img/nature/image6.jpg" alt="Card Image" className="card-img-top scale-on-hover" />
                    </a>
                    <div className="card-body">
                      <h6>
                        <a href="#">Lorem Ipsum</a>
                      </h6>
                      <p className="text-muted card-text">Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nunc quam urna.</p>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </section>
        </main>
      </Col>
      <Col md="1" />
    </Row>
  );
};

const mapStateToProps = storeState => ({
  message: storeState.message,
});

type StateProps = ReturnType<typeof mapStateToProps>;

export default connect(mapStateToProps)(Projects);
