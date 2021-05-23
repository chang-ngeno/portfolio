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

describe('PersonalDetails e2e test', () => {
  let startingEntitiesCount = 0;

  before(() => {
    cy.window().then(win => {
      win.sessionStorage.clear();
    });

    cy.clearCookies();
    cy.intercept('GET', '/api/personal-details*').as('entitiesRequest');
    cy.visit('');
    cy.login('admin', 'admin');
    cy.clickOnEntityMenuItem('personal-details');
    cy.wait('@entitiesRequest').then(({ request, response }) => (startingEntitiesCount = response.body.length));
    cy.visit('/');
  });

  it('should load PersonalDetails', () => {
    cy.intercept('GET', '/api/personal-details*').as('entitiesRequest');
    cy.visit('/');
    cy.clickOnEntityMenuItem('personal-details');
    cy.wait('@entitiesRequest');
    cy.getEntityHeading('PersonalDetails').should('exist');
    if (startingEntitiesCount === 0) {
      cy.get(entityTableSelector).should('not.exist');
    } else {
      cy.get(entityTableSelector).should('have.lengthOf', startingEntitiesCount);
    }
    cy.visit('/');
  });

  it('should load details PersonalDetails page', () => {
    cy.intercept('GET', '/api/personal-details*').as('entitiesRequest');
    cy.visit('/');
    cy.clickOnEntityMenuItem('personal-details');
    cy.wait('@entitiesRequest');
    if (startingEntitiesCount > 0) {
      cy.get(entityDetailsButtonSelector).first().click({ force: true });
      cy.getEntityDetailsHeading('personalDetails');
      cy.get(entityDetailsBackButtonSelector).should('exist');
    }
    cy.visit('/');
  });

  it('should load create PersonalDetails page', () => {
    cy.intercept('GET', '/api/personal-details*').as('entitiesRequest');
    cy.visit('/');
    cy.clickOnEntityMenuItem('personal-details');
    cy.wait('@entitiesRequest');
    cy.get(entityCreateButtonSelector).click({ force: true });
    cy.getEntityCreateUpdateHeading('PersonalDetails');
    cy.get(entityCreateSaveButtonSelector).should('exist');
    cy.visit('/');
  });

  it('should load edit PersonalDetails page', () => {
    cy.intercept('GET', '/api/personal-details*').as('entitiesRequest');
    cy.visit('/');
    cy.clickOnEntityMenuItem('personal-details');
    cy.wait('@entitiesRequest');
    if (startingEntitiesCount > 0) {
      cy.get(entityEditButtonSelector).first().click({ force: true });
      cy.getEntityCreateUpdateHeading('PersonalDetails');
      cy.get(entityCreateSaveButtonSelector).should('exist');
    }
    cy.visit('/');
  });

  it('should create an instance of PersonalDetails', () => {
    cy.intercept('GET', '/api/personal-details*').as('entitiesRequest');
    cy.visit('/');
    cy.clickOnEntityMenuItem('personal-details');
    cy.wait('@entitiesRequest').then(({ request, response }) => (startingEntitiesCount = response.body.length));
    cy.get(entityCreateButtonSelector).click({ force: true });
    cy.getEntityCreateUpdateHeading('PersonalDetails');

    cy.get(`[data-cy="names"]`).type('migration Bike', { force: true }).invoke('val').should('match', new RegExp('migration Bike'));

    cy.get(`[data-cy="slug"]`)
      .type('../fake-data/blob/hipster.txt', { force: true })
      .invoke('val')
      .should('match', new RegExp('../fake-data/blob/hipster.txt'));

    cy.get(`[data-cy="email"]`)
      .type('Douglas.Padberg68@hotmail.com', { force: true })
      .invoke('val')
      .should('match', new RegExp('Douglas.Padberg68@hotmail.com'));

    cy.get(`[data-cy="phoneNumber"]`).type('Plaza', { force: true }).invoke('val').should('match', new RegExp('Plaza'));

    cy.get(entityCreateSaveButtonSelector).click({ force: true });
    cy.scrollTo('top', { ensureScrollable: false });
    cy.get(entityCreateSaveButtonSelector).should('not.exist');
    cy.intercept('GET', '/api/personal-details*').as('entitiesRequestAfterCreate');
    cy.visit('/');
    cy.clickOnEntityMenuItem('personal-details');
    cy.wait('@entitiesRequestAfterCreate');
    cy.get(entityTableSelector).should('have.lengthOf', startingEntitiesCount + 1);
    cy.visit('/');
  });

  it('should delete last instance of PersonalDetails', () => {
    cy.intercept('GET', '/api/personal-details*').as('entitiesRequest');
    cy.intercept('GET', '/api/personal-details/*').as('dialogDeleteRequest');
    cy.intercept('DELETE', '/api/personal-details/*').as('deleteEntityRequest');
    cy.visit('/');
    cy.clickOnEntityMenuItem('personal-details');
    cy.wait('@entitiesRequest').then(({ request, response }) => {
      startingEntitiesCount = response.body.length;
      if (startingEntitiesCount > 0) {
        cy.get(entityTableSelector).should('have.lengthOf', startingEntitiesCount);
        cy.get(entityDeleteButtonSelector).last().click({ force: true });
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('personalDetails').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click({ force: true });
        cy.wait('@deleteEntityRequest');
        cy.intercept('GET', '/api/personal-details*').as('entitiesRequestAfterDelete');
        cy.visit('/');
        cy.clickOnEntityMenuItem('personal-details');
        cy.wait('@entitiesRequestAfterDelete');
        cy.get(entityTableSelector).should('have.lengthOf', startingEntitiesCount - 1);
      }
      cy.visit('/');
    });
  });
});
