import './hire-me.css';

import React from 'react';
import { Translate } from 'react-jhipster';
import { connect } from 'react-redux';
import { Row, Col } from 'reactstrap';

export type IHireMeProp = StateProps;

export const HireMe = (props: IHireMeProp) => {
  const { message } = props;

  return (
    <Row>
      <Col md="1" />
      <Col md="10">
        <h6 className="text-right">{message}</h6>
        <h1 className="text-capitalize text-center">
          <Translate contentKey="hireMe.title">Title</Translate>
        </h1>
        {/* <h5 className="text-center">
                  <Translate contentKey="hireMe.subtitle">Subtitle</Translate>
                </h5> */}
        <hr />
        <main className="page hire-me-page">
          <section className="portfolio-block hire-me">
            <div className="container">
              <div className="heading">
                <h2>Hire Me</h2>
              </div>
              <form>
                <div className="form-group">
                  <label htmlFor="subject">Subject</label>
                  <select className="form-control" id="subject">
                    <option value="" selected>
                      Choose Subject
                    </option>
                    <option value="1">Subject 1</option>
                    <option value="2">Subject 2</option>
                    <option value="3">Subject 3</option>
                  </select>
                </div>
                <div className="form-group">
                  <label htmlFor="email">Email</label>
                  <input className="form-control" type="email" id="email" />
                </div>
                <div className="form-group">
                  <label htmlFor="message">Message</label>
                  <textarea className="form-control" id="message"></textarea>
                </div>
                <div className="form-group">
                  <div className="form-row">
                    <div className="col-md-6">
                      <label htmlFor="hire-date">Date</label>
                      <input className="form-control datepicker" type="text" id="hire-date" date-format="MM-DD-YYYY" />
                    </div>
                    <div className="col-md-6 button">
                      <button className="btn btn-primary btn-block" type="button">
                        Hire Me
                      </button>
                    </div>
                  </div>
                </div>
              </form>
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

export default connect(mapStateToProps)(HireMe);
