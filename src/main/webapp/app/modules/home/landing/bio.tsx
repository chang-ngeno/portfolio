import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { RouteComponentProps } from 'react-router-dom';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from '../../../entities/personal-details/personal-details.reducer';
import './landing.scss';

// export interface IBioProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> { }

export const Bio = (/* props: IBioProps */) => {
  // useEffect(() => {
  //     props.getEntity(props.match.params.id);
  // }, []);

  // const { personalDetailsEntity } = props;

  return (
    <section className="portfolio-block block-intro">
      <div className="container">
        <div className="avatar img-fluid image text-center" style={{ marginTop: '2em' }}></div>
        <div className="about-me text-center">
          {/* <p>{personalDetailsEntity.slug}</p> */}
          <p>
            Hello! I work as a software developer. I have passion for well organized, documented and clean code. Profficient in Java, Spring
            , AngularJS, React, Node.js, &amp; Hibernate.
          </p>
          <a className="btn btn-outline-primary" role="button" href="https://twitter.com/changmasajr">
            {' '}
            <FontAwesomeIcon icon="twitter" /> Follow Me
          </a>
        </div>
      </div>
    </section>
  );
};

// const mapStateToProps = ({ personalDetails }: IRootState) => ({
//     personalDetailsEntity: personalDetails.entity

// });

// const mapDispatchToProps = { getEntity };

// type StateProps = ReturnType<typeof mapStateToProps>;
// type DispatchProps = typeof mapDispatchToProps;

// export default connect(mapStateToProps, mapDispatchToProps)(Bio);
export default Bio;
