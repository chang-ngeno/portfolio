import './home.scss';

import React, { useEffect } from 'react';
import { Link } from 'react-router-dom';
import { Translate } from 'react-jhipster';
import { connect } from 'react-redux';
import { Row, Col, Alert, NavItem, NavLink } from 'reactstrap';

import { Bio } from './landing/bio';

export type IHomeProp = StateProps;

export const Home = (props: IHomeProp) => {
  const { account } = props;

  return (
    <Row>
      {/* <Col md="3" className="pad">
              <span className="hipster rounded" />
            </Col> */}
      <Col md="12">
        <div className="d-md-none" style={{ marginTop: '3.0em' }}></div>
        <Bio />
        <section className="portfolio-block photography">
          <div className="container text-center">
            <div className="row no-gutters">
              <div className="col-md-6 col-lg-4 item zoom-on-hover">
                <a href="#">
                  <img className="img-fluid image" src="/assets/img/tech/image5.jpg" />
                </a>
              </div>
              <div className="col-md-6 col-lg-4 item zoom-on-hover">
                <a href="#">
                  <img className="img-fluid image" src="/assets/img/tech/image2.jpg" />
                </a>
              </div>
              <div className="col-md-6 col-lg-4 item zoom-on-hover">
                <a href="#">
                  <img className="img-fluid image" src="/assets/img/tech/image4.jpg" />
                </a>
              </div>
            </div>
          </div>
        </section>
        <section className="portfolio-block call-to-action border-bottom">
          <div className="container">
            <div className="text-center justify-content-center align-items-center content">
              <h3>Like what you see?</h3>
              <br />
              <a href="/contact-me" className="btn btn-outline-primary btn-lg" type="button">
                Hire me
              </a>
            </div>
          </div>
        </section>
      </Col>
    </Row>
  );
};

const mapStateToProps = storeState => ({
  account: storeState.authentication.account,
  isAuthenticated: storeState.authentication.isAuthenticated,
});

type StateProps = ReturnType<typeof mapStateToProps>;

export default connect(mapStateToProps)(Home);
