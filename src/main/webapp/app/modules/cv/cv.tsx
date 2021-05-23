import './cv.css';

import React from 'react';
import { Translate } from 'react-jhipster';
import { connect } from 'react-redux';
import { Row, Col } from 'reactstrap';

export type ICvProp = StateProps;

export const Cv = (props: ICvProp) => {
  const { message } = props;

  return (
    <Row>
      <Col md="1" />
      <Col md="10">
        <h6 className="text-right">{message}</h6>
        <h1 className="text-capitalize text-left">
          <Translate contentKey="cv.title">Title</Translate>
        </h1>
        {/* <h5 className="text-left">
                  <Translate contentKey="cv.subtitle">Subtitle</Translate>
                </h5> */}
        <hr />
        <main className="page cv-page text-left">
          <section className="portfolio-block cv">
            <div className="container">
              <div className="work-experience group">
                <div className="heading">
                  <h2 className="text-left">Work Experience</h2>
                </div>
                <div className="item">
                  <div className="row">
                    <div className="col-md-6">
                      <h3>Web Developer</h3>
                      <h4 className="organization">Amazing Co.</h4>
                    </div>
                    <div className="col-md-6">
                      <span className="period">10/2013 - 04/2015</span>
                    </div>
                  </div>
                  <p className="text-muted">
                    Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aenean eget velit ultricies, feugiat est sed, efficitur nunc,
                    vivamus vel accumsan dui.
                  </p>
                </div>
                <div className="item">
                  <div className="row">
                    <div className="col-6">
                      <h3>Front End Developer</h3>
                      <h4 className="organization">Innovative Org.</h4>
                    </div>
                    <div className="col-md-6">
                      <span className="period">05/2015 - 12/2017</span>
                    </div>
                  </div>
                  <p className="text-muted">
                    Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aenean eget velit ultricies, feugiat est sed, efficitur nunc,
                    vivamus vel accumsan dui.
                  </p>
                </div>
                <div className="item">
                  <div className="row">
                    <div className="col-md-6">
                      <h3>Web Developer</h3>
                      <h4 className="organization">Special Inc.</h4>
                    </div>
                    <div className="col-md-6">
                      <span className="period">12/2017 - Present</span>
                    </div>
                  </div>
                  <p className="text-muted">
                    Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aenean eget velit ultricies, feugiat est sed, efficitur nunc,
                    vivamus vel accumsan dui.
                  </p>
                </div>
              </div>
              <div className="education group">
                <div className="heading">
                  <h2 className="text-left">Education</h2>
                </div>
                <div className="item">
                  <div className="row">
                    <div className="col-md-6">
                      <h3>High School</h3>
                      <h4 className="organization">Albert Einstein School</h4>
                    </div>
                    <div className="col-6">
                      <span className="period">09/2005 - 05/2010</span>
                    </div>
                  </div>
                  <p className="text-muted">
                    Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aenean eget velit ultricies, feugiat est sed, efficitur nunc,
                    vivamus vel accumsan dui.
                  </p>
                </div>
                <div className="item">
                  <div className="row">
                    <div className="col-md-6">
                      <h3>Applied Physics</h3>
                      <h4 className="organization">Stephen Hawking College</h4>
                    </div>
                    <div className="col-md-6">
                      <span className="period">09/2010 - 06/2015</span>
                    </div>
                  </div>
                  <p className="text-muted">
                    Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aenean eget velit ultricies, feugiat est sed, efficitur nunc,
                    vivamus vel accumsan dui.
                  </p>
                </div>
              </div>
              <div className="group">
                <div className="row">
                  <div className="col-md-6">
                    <div className="skills portfolio-info-card">
                      <h2>Skills</h2>
                      <h3>Java</h3>
                      <div className="progress">
                        <div className="progress-bar" style={{ width: '100%' }}>
                          <span className="sr-only">100%</span>
                        </div>
                      </div>
                      <h3>PostgreSQL, MySQL</h3>
                      <div className="progress">
                        <div className="progress-bar" style={{ width: '90%' }}>
                          <span className="sr-only">90%</span>
                        </div>
                      </div>
                      <h3>JavaScript</h3>
                      <div className="progress">
                        <div className="progress-bar" style={{ width: '80%' }}>
                          <span className="sr-only">80%</span>
                        </div>
                      </div>
                    </div>
                  </div>
                  <div className="col-md-6">
                    <div className="contact-info portfolio-info-card">
                      <h2>Contact Info</h2>
                      <div className="row">
                        <div className="col-1">
                          <i className="icon ion-social-whatsapp icon" style={{ color: `rgb(96, 239, 102)` }}></i>
                        </div>
                        <div className="col-9">
                          <span>+254 724 880 775</span>
                        </div>
                      </div>
                      <div className="row">
                        <div className="col-1">
                          <i className="icon ion-social-twitter icon" style={{ color: `rgb(29, 161, 242)` }}></i>
                        </div>
                        <div className="col-9">
                          <span>ChangmasaJr</span>
                        </div>
                      </div>
                      <div className="row">
                        <div className="col-1">
                          <i className="icon ion-social-linkedin icon" style={{ color: `rgb(10, 102, 194)` }}></i>
                        </div>
                        <div className="col-9">
                          <span>daniel.changmasa</span>
                        </div>
                      </div>
                      <div className="row">
                        <div className="col-1">
                          <i className="icon ion-social-github icon" style={{ color: `rgb(0, 0, 0)` }}></i>
                        </div>
                        <div className="col-9">
                          <span>chang-ngeno</span>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
              <div className="hobbies group">
                <div className="heading">
                  <h2 className="text-left">Hobbies</h2>
                </div>
                <p className="text-left text-muted">
                  Lorem ipsum dolor sit amet, consectetur adipiscing elit. Cras risus ligula, iaculis ut metus sit amet, luctus pharetra
                  mauris. Aliquam purus felis, pretium vel pretium vitae, dapibus sodales ante. Suspendisse potenti. Duis nunc eros.
                </p>
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

export default connect(mapStateToProps)(Cv);
