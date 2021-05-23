import React from 'react';
import { Translate } from 'react-jhipster';

import { NavItem, NavLink, NavbarBrand } from 'reactstrap';
import { NavLink as Link } from 'react-router-dom';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import appConfig from 'app/config/constants';

export const BrandIcon = props => (
  <div {...props} className="brand-icon">
    <img src="content/images/logo-jhipster.png" alt="Logo" />
  </div>
);

export const Brand = props => (
  <NavbarBrand tag={Link} to="/" className="brand-logo">
    {/* <BrandIcon /> */}
    <span className="brand-title">
      <Translate contentKey="global.title">Changmasa D.K.</Translate>
    </span>
    <span className="navbar-version">{appConfig.VERSION}</span>
  </NavbarBrand>
);

export const Home = props => (
  <NavItem>
    <NavLink tag={Link} to="/" className="d-flex align-items-center">
      <FontAwesomeIcon icon="home" />
      <span>
        &nbsp;
        <Translate contentKey="global.menu.home">Home</Translate>
      </span>
    </NavLink>
  </NavItem>
);

export const Projects = props => (
  <NavItem>
    <NavLink tag={Link} to="/projects" className="d-flex align-items-center">
      <FontAwesomeIcon icon="code" />
      <span>
        &nbsp;
        <Translate contentKey="global.menu.projects">Projects</Translate>
      </span>
    </NavLink>
  </NavItem>
);
export const HireMe = props => (
  <NavItem>
    <NavLink tag={Link} to="/hire-me" className="d-flex align-items-center">
      <FontAwesomeIcon icon={['fas', 'envelope-open']} />
      <span>
        &nbsp;
        <Translate contentKey="global.menu.hireMe">Hire Me</Translate>
      </span>
    </NavLink>
  </NavItem>
);
export const Cv = props => (
  <NavItem>
    <NavLink tag={Link} to="/cv" className="d-flex align-items-center">
      <FontAwesomeIcon icon={['fas', 'file-certificate']} />
      <span>
        &nbsp;
        <Translate contentKey="global.menu.cv">CV</Translate>
      </span>
    </NavLink>
  </NavItem>
);
export const ContactMe = props => (
  <NavItem>
    <NavLink tag={Link} to="/contact-me" className="d-flex align-items-center">
      <FontAwesomeIcon icon={['fab', 'twitter']} />
      <span>
        &nbsp;
        <Translate contentKey="global.menu.contact-me">Contact me</Translate>
      </span>
    </NavLink>
  </NavItem>
);
