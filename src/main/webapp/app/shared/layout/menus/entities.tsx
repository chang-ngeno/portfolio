import React from 'react';
import MenuItem from 'app/shared/layout/menus/menu-item';
import { Translate, translate } from 'react-jhipster';
import { NavDropdown } from './menu-components';

export const EntitiesMenu = props => (
  <NavDropdown
    icon="th-list"
    name={translate('global.menu.entities.main')}
    id="entity-menu"
    data-cy="entity"
    style={{ maxHeight: '80vh', overflow: 'auto' }}
  >
    <MenuItem icon="asterisk" to="/personal-details">
      <Translate contentKey="global.menu.entities.personalDetails" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/social-media">
      <Translate contentKey="global.menu.entities.socialMedia" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/project">
      <Translate contentKey="global.menu.entities.project" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/gallery">
      <Translate contentKey="global.menu.entities.gallery" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/work-experience">
      <Translate contentKey="global.menu.entities.workExperience" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/education">
      <Translate contentKey="global.menu.entities.education" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/hobby">
      <Translate contentKey="global.menu.entities.hobby" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/hire-me-subject">
      <Translate contentKey="global.menu.entities.hireMeSubject" />
    </MenuItem>
    {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
  </NavDropdown>
);
