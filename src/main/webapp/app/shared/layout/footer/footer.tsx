import './footer.scss';

import React from 'react';
import { Translate } from 'react-jhipster';
import { Col, Row } from 'reactstrap';

const Footer = props => (
  <div className="footer page-content">
    <footer className="page-footer">
      <div className="container">
        <div className="links">
          <a href="#">About me</a> <a href="/hire-me">Contact me</a> <a href="#">Projects</a>
        </div>
        <div className="social-icons">
          <a href="#">
            <i className="icon ion-social-facebook"></i>
          </a>
          <a href="#">
            <i className="icon ion-social-instagram-outline"></i>
          </a>
          <a href="#">
            <i className="icon ion-social-twitter"></i>
          </a>
        </div>
        <p>Copyright &copy; {new Date().getFullYear()}</p>
      </div>
      {/*
            <Row>
              <Col xs="none" md="12">
                <span>Only Visible on big screens</span>
              </Col>
              <Col md="12">
                <p>Copyright &copy; {new Date().getFullYear()}</p>
              </Col>
            </Row> */}
    </footer>
  </div>
);

export default Footer;
