import {
  entityTableSelector,
  entityDetailsButtonSelector,
  entityDetailsBackButtonSelector,
  entityCreateButtonSelector,
  entityCreateSaveButtonSelector,
  entityEditButtonSelector,
  entityDeleteButtonSelector,
  entityConfirmDeleteButtonSelector,
} from '../../support/entity';

describe('WorkExperience e2e test', () => {
  let startingEntitiesCount = 0;

  before(() => {
    cy.window().then(win => {
      win.sessionStorage.clear();
    });

    cy.clearCookies();
    cy.intercept('GET', '/api/work-experiences*').as('entitiesRequest');
    cy.visit('');
    cy.login('admin', 'admin');
    cy.clickOnEntityMenuItem('work-experience');
    cy.wait('@entitiesRequest').then(({ request, response }) => (startingEntitiesCount = response.body.length));
    cy.visit('/');
  });

  it('should load WorkExperiences', () => {
    cy.intercept('GET', '/api/work-experiences*').as('entitiesRequest');
    cy.visit('/');
    cy.clickOnEntityMenuItem('work-experience');
    cy.wait('@entitiesRequest');
    cy.getEntityHeading('WorkExperience').should('exist');
    if (startingEntitiesCount === 0) {
      cy.get(entityTableSelector).should('not.exist');
    } else {
      cy.get(entityTableSelector).should('have.lengthOf', startingEntitiesCount);
    }
    cy.visit('/');
  });

  it('should load details WorkExperience page', () => {
    cy.intercept('GET', '/api/work-experiences*').as('entitiesRequest');
    cy.visit('/');
    cy.clickOnEntityMenuItem('work-experience');
    cy.wait('@entitiesRequest');
    if (startingEntitiesCount > 0) {
      cy.get(entityDetailsButtonSelector).first().click({ force: true });
      cy.getEntityDetailsHeading('workExperience');
      cy.get(entityDetailsBackButtonSelector).should('exist');
    }
    cy.visit('/');
  });

  it('should load create WorkExperience page', () => {
    cy.intercept('GET', '/api/work-experiences*').as('entitiesRequest');
    cy.visit('/');
    cy.clickOnEntityMenuItem('work-experience');
    cy.wait('@entitiesRequest');
    cy.get(entityCreateButtonSelector).click({ force: true });
    cy.getEntityCreateUpdateHeading('WorkExperience');
    cy.get(entityCreateSaveButtonSelector).should('exist');
    cy.visit('/');
  });

  it('should load edit WorkExperience page', () => {
    cy.intercept('GET', '/api/work-experiences*').as('entitiesRequest');
    cy.visit('/');
    cy.clickOnEntityMenuItem('work-experience');
    cy.wait('@entitiesRequest');
    if (startingEntitiesCount > 0) {
      cy.get(entityEditButtonSelector).first().click({ force: true });
      cy.getEntityCreateUpdateHeading('WorkExperience');
      cy.get(entityCreateSaveButtonSelector).should('exist');
    }
    cy.visit('/');
  });

  it('should create an instance of WorkExperience', () => {
    cy.intercept('GET', '/api/work-experiences*').as('entitiesRequest');
    cy.visit('/');
    cy.clickOnEntityMenuItem('work-experience');
    cy.wait('@entitiesRequest').then(({ request, response }) => (startingEntitiesCount = response.body.length));
    cy.get(entityCreateButtonSelector).click({ force: true });
    cy.getEntityCreateUpdateHeading('WorkExperience');

    cy.get(`[data-cy="title"]`)
      .type('enhance solid haptic', { force: true })
      .invoke('val')
      .should('match', new RegExp('enhance solid haptic'));

    cy.get(`[data-cy="employer"]`).type('Movies protocol', { force: true }).invoke('val').should('match', new RegExp('Movies protocol'));

    cy.get(`[data-cy="startDate"]`).type('2021-05-14T12:11').invoke('val').should('equal', '2021-05-14T12:11');

    cy.get(`[data-cy="endDate"]`).type('2021-05-14T19:47').invoke('val').should('equal', '2021-05-14T19:47');

    cy.get(`[data-cy="roles"]`)
      .type('../fake-data/blob/hipster.txt', { force: true })
      .invoke('val')
      .should('match', new RegExp('../fake-data/blob/hipster.txt'));

    cy.get(entityCreateSaveButtonSelector).click({ force: true });
    cy.scrollTo('top', { ensureScrollable: false });
    cy.get(entityCreateSaveButtonSelector).should('not.exist');
    cy.intercept('GET', '/api/work-experiences*').as('entitiesRequestAfterCreate');
    cy.visit('/');
    cy.clickOnEntityMenuItem('work-experience');
    cy.wait('@entitiesRequestAfterCreate');
    cy.get(entityTableSelector).should('have.lengthOf', startingEntitiesCount + 1);
    cy.visit('/');
  });

  it('should delete last instance of WorkExperience', () => {
    cy.intercept('GET', '/api/work-experiences*').as('entitiesRequest');
    cy.intercept('GET', '/api/work-experiences/*').as('dialogDeleteRequest');
    cy.intercept('DELETE', '/api/work-experiences/*').as('deleteEntityRequest');
    cy.visit('/');
    cy.clickOnEntityMenuItem('work-experience');
    cy.wait('@entitiesRequest').then(({ request, response }) => {
      startingEntitiesCount = response.body.length;
      if (startingEntitiesCount > 0) {
        cy.get(entityTableSelector).should('have.lengthOf', startingEntitiesCount);
        cy.get(entityDeleteButtonSelector).last().click({ force: true });
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('workExperience').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click({ force: true });
        cy.wait('@deleteEntityRequest');
        cy.intercept('GET', '/api/work-experiences*').as('entitiesRequestAfterDelete');
        cy.visit('/');
        cy.clickOnEntityMenuItem('work-experience');
        cy.wait('@entitiesRequestAfterDelete');
        cy.get(entityTableSelector).should('have.lengthOf', startingEntitiesCount - 1);
      }
      cy.visit('/');
    });
  });
});
