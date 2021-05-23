import { combineReducers } from 'redux';
import { loadingBarReducer as loadingBar } from 'react-redux-loading-bar';

import locale, { LocaleState } from './locale';
import authentication, { AuthenticationState } from './authentication';
import applicationProfile, { ApplicationProfileState } from './application-profile';

import administration, { AdministrationState } from 'app/modules/administration/administration.reducer';
import userManagement, { UserManagementState } from 'app/modules/administration/user-management/user-management.reducer';
import register, { RegisterState } from 'app/modules/account/register/register.reducer';
import activate, { ActivateState } from 'app/modules/account/activate/activate.reducer';
import password, { PasswordState } from 'app/modules/account/password/password.reducer';
import settings, { SettingsState } from 'app/modules/account/settings/settings.reducer';
import passwordReset, { PasswordResetState } from 'app/modules/account/password-reset/password-reset.reducer';
// prettier-ignore
import personalDetails, {
  PersonalDetailsState
} from 'app/entities/personal-details/personal-details.reducer';
// prettier-ignore
import socialMedia, {
  SocialMediaState
} from 'app/entities/social-media/social-media.reducer';
// prettier-ignore
import project, {
  ProjectState
} from 'app/entities/project/project.reducer';
// prettier-ignore
import gallery, {
  GalleryState
} from 'app/entities/gallery/gallery.reducer';
// prettier-ignore
import workExperience, {
  WorkExperienceState
} from 'app/entities/work-experience/work-experience.reducer';
// prettier-ignore
import education, {
  EducationState
} from 'app/entities/education/education.reducer';
// prettier-ignore
import hobby, {
  HobbyState
} from 'app/entities/hobby/hobby.reducer';
// prettier-ignore
import hireMeSubject, {
  HireMeSubjectState
} from 'app/entities/hire-me-subject/hire-me-subject.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

export interface IRootState {
  readonly authentication: AuthenticationState;
  readonly locale: LocaleState;
  readonly applicationProfile: ApplicationProfileState;
  readonly administration: AdministrationState;
  readonly userManagement: UserManagementState;
  readonly register: RegisterState;
  readonly activate: ActivateState;
  readonly passwordReset: PasswordResetState;
  readonly password: PasswordState;
  readonly settings: SettingsState;
  readonly personalDetails: PersonalDetailsState;
  readonly socialMedia: SocialMediaState;
  readonly project: ProjectState;
  readonly gallery: GalleryState;
  readonly workExperience: WorkExperienceState;
  readonly education: EducationState;
  readonly hobby: HobbyState;
  readonly hireMeSubject: HireMeSubjectState;
  /* jhipster-needle-add-reducer-type - JHipster will add reducer type here */
  readonly loadingBar: any;
}

const rootReducer = combineReducers<IRootState>({
  authentication,
  locale,
  applicationProfile,
  administration,
  userManagement,
  register,
  activate,
  passwordReset,
  password,
  settings,
  personalDetails,
  socialMedia,
  project,
  gallery,
  workExperience,
  education,
  hobby,
  hireMeSubject,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
  loadingBar,
});

export default rootReducer;
