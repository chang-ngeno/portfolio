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

describe('HireMeSubject e2e test', () => {
  let startingEntitiesCount = 0;

  before(() => {
    cy.window().then(win => {
      win.sessionStorage.clear();
    });

    cy.clearCookies();
    cy.intercept('GET', '/api/hire-me-subjects*').as('entitiesRequest');
    cy.visit('');
    cy.login('admin', 'admin');
    cy.clickOnEntityMenuItem('hire-me-subject');
    cy.wait('@entitiesRequest').then(({ request, response }) => (startingEntitiesCount = response.body.length));
    cy.visit('/');
  });

  it('should load HireMeSubjects', () => {
    cy.intercept('GET', '/api/hire-me-subjects*').as('entitiesRequest');
    cy.visit('/');
    cy.clickOnEntityMenuItem('hire-me-subject');
    cy.wait('@entitiesRequest');
    cy.getEntityHeading('HireMeSubject').should('exist');
    if (startingEntitiesCount === 0) {
      cy.get(entityTableSelector).should('not.exist');
    } else {
      cy.get(entityTableSelector).should('have.lengthOf', startingEntitiesCount);
    }
    cy.visit('/');
  });

  it('should load details HireMeSubject page', () => {
    cy.intercept('GET', '/api/hire-me-subjects*').as('entitiesRequest');
    cy.visit('/');
    cy.clickOnEntityMenuItem('hire-me-subject');
    cy.wait('@entitiesRequest');
    if (startingEntitiesCount > 0) {
      cy.get(entityDetailsButtonSelector).first().click({ force: true });
      cy.getEntityDetailsHeading('hireMeSubject');
      cy.get(entityDetailsBackButtonSelector).should('exist');
    }
    cy.visit('/');
  });

  it('should load create HireMeSubject page', () => {
    cy.intercept('GET', '/api/hire-me-subjects*').as('entitiesRequest');
    cy.visit('/');
    cy.clickOnEntityMenuItem('hire-me-subject');
    cy.wait('@entitiesRequest');
    cy.get(entityCreateButtonSelector).click({ force: true });
    cy.getEntityCreateUpdateHeading('HireMeSubject');
    cy.get(entityCreateSaveButtonSelector).should('exist');
    cy.visit('/');
  });

  it('should load edit HireMeSubject page', () => {
    cy.intercept('GET', '/api/hire-me-subjects*').as('entitiesRequest');
    cy.visit('/');
    cy.clickOnEntityMenuItem('hire-me-subject');
    cy.wait('@entitiesRequest');
    if (startingEntitiesCount > 0) {
      cy.get(entityEditButtonSelector).first().click({ force: true });
      cy.getEntityCreateUpdateHeading('HireMeSubject');
      cy.get(entityCreateSaveButtonSelector).should('exist');
    }
    cy.visit('/');
  });

  it('should create an instance of HireMeSubject', () => {
    cy.intercept('GET', '/api/hire-me-subjects*').as('entitiesRequest');
    cy.visit('/');
    cy.clickOnEntityMenuItem('hire-me-subject');
    cy.wait('@entitiesRequest').then(({ request, response }) => (startingEntitiesCount = response.body.length));
    cy.get(entityCreateButtonSelector).click({ force: true });
    cy.getEntityCreateUpdateHeading('HireMeSubject');

    cy.get(`[data-cy="subject"]`)
      .type('Portugal experiences Up-sized', { force: true })
      .invoke('val')
      .should('match', new RegExp('Portugal experiences Up-sized'));

    cy.get(entityCreateSaveButtonSelector).click({ force: true });
    cy.scrollTo('top', { ensureScrollable: false });
    cy.get(entityCreateSaveButtonSelector).should('not.exist');
    cy.intercept('GET', '/api/hire-me-subjects*').as('entitiesRequestAfterCreate');
    cy.visit('/');
    cy.clickOnEntityMenuItem('hire-me-subject');
    cy.wait('@entitiesRequestAfterCreate');
    cy.get(entityTableSelector).should('have.lengthOf', startingEntitiesCount + 1);
    cy.visit('/');
  });

  it('should delete last instance of HireMeSubject', () => {
    cy.intercept('GET', '/api/hire-me-subjects*').as('entitiesRequest');
    cy.intercept('GET', '/api/hire-me-subjects/*').as('dialogDeleteRequest');
    cy.intercept('DELETE', '/api/hire-me-subjects/*').as('deleteEntityRequest');
    cy.visit('/');
    cy.clickOnEntityMenuItem('hire-me-subject');
    cy.wait('@entitiesRequest').then(({ request, response }) => {
      startingEntitiesCount = response.body.length;
      if (startingEntitiesCount > 0) {
        cy.get(entityTableSelector).should('have.lengthOf', startingEntitiesCount);
        cy.get(entityDeleteButtonSelector).last().click({ force: true });
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('hireMeSubject').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click({ force: true });
        cy.wait('@deleteEntityRequest');
        cy.intercept('GET', '/api/hire-me-subjects*').as('entitiesRequestAfterDelete');
        cy.visit('/');
        cy.clickOnEntityMenuItem('hire-me-subject');
        cy.wait('@entitiesRequestAfterDelete');
        cy.get(entityTableSelector).should('have.lengthOf', startingEntitiesCount - 1);
      }
      cy.visit('/');
    });
  });
});
