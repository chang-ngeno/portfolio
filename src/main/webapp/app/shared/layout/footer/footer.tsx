import './footer.scss';

import React from 'react';
import { Translate } from 'react-jhipster';
import { Col, Row } from 'reactstrap';

const Footer = props => (
  <div className="footer page-content">
    <Row>
      <Col xs="none" md="12">
        <span>Only Visible on big screens</span>
      </Col>
      <Col md="12">
        <p>Copyright &copy; {new Date().getFullYear()}</p>
      </Col>
    </Row>
  </div>
);

export default Footer;
