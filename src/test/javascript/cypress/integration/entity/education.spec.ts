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

describe('Education e2e test', () => {
  let startingEntitiesCount = 0;

  before(() => {
    cy.window().then(win => {
      win.sessionStorage.clear();
    });

    cy.clearCookies();
    cy.intercept('GET', '/api/educations*').as('entitiesRequest');
    cy.visit('');
    cy.login('admin', 'admin');
    cy.clickOnEntityMenuItem('education');
    cy.wait('@entitiesRequest').then(({ request, response }) => (startingEntitiesCount = response.body.length));
    cy.visit('/');
  });

  it('should load Educations', () => {
    cy.intercept('GET', '/api/educations*').as('entitiesRequest');
    cy.visit('/');
    cy.clickOnEntityMenuItem('education');
    cy.wait('@entitiesRequest');
    cy.getEntityHeading('Education').should('exist');
    if (startingEntitiesCount === 0) {
      cy.get(entityTableSelector).should('not.exist');
    } else {
      cy.get(entityTableSelector).should('have.lengthOf', startingEntitiesCount);
    }
    cy.visit('/');
  });

  it('should load details Education page', () => {
    cy.intercept('GET', '/api/educations*').as('entitiesRequest');
    cy.visit('/');
    cy.clickOnEntityMenuItem('education');
    cy.wait('@entitiesRequest');
    if (startingEntitiesCount > 0) {
      cy.get(entityDetailsButtonSelector).first().click({ force: true });
      cy.getEntityDetailsHeading('education');
      cy.get(entityDetailsBackButtonSelector).should('exist');
    }
    cy.visit('/');
  });

  it('should load create Education page', () => {
    cy.intercept('GET', '/api/educations*').as('entitiesRequest');
    cy.visit('/');
    cy.clickOnEntityMenuItem('education');
    cy.wait('@entitiesRequest');
    cy.get(entityCreateButtonSelector).click({ force: true });
    cy.getEntityCreateUpdateHeading('Education');
    cy.get(entityCreateSaveButtonSelector).should('exist');
    cy.visit('/');
  });

  it('should load edit Education page', () => {
    cy.intercept('GET', '/api/educations*').as('entitiesRequest');
    cy.visit('/');
    cy.clickOnEntityMenuItem('education');
    cy.wait('@entitiesRequest');
    if (startingEntitiesCount > 0) {
      cy.get(entityEditButtonSelector).first().click({ force: true });
      cy.getEntityCreateUpdateHeading('Education');
      cy.get(entityCreateSaveButtonSelector).should('exist');
    }
    cy.visit('/');
  });

  it('should create an instance of Education', () => {
    cy.intercept('GET', '/api/educations*').as('entitiesRequest');
    cy.visit('/');
    cy.clickOnEntityMenuItem('education');
    cy.wait('@entitiesRequest').then(({ request, response }) => (startingEntitiesCount = response.body.length));
    cy.get(entityCreateButtonSelector).click({ force: true });
    cy.getEntityCreateUpdateHeading('Education');

    cy.get(`[data-cy="qualification"]`).type('Handmade', { force: true }).invoke('val').should('match', new RegExp('Handmade'));

    cy.get(`[data-cy="institution"]`)
      .type('Cambridgeshire Cuba Home', { force: true })
      .invoke('val')
      .should('match', new RegExp('Cambridgeshire Cuba Home'));

    cy.get(`[data-cy="startDate"]`).type('2021-05-14T05:30').invoke('val').should('equal', '2021-05-14T05:30');

    cy.get(`[data-cy="endDate"]`).type('2021-05-14T03:54').invoke('val').should('equal', '2021-05-14T03:54');

    cy.get(`[data-cy="slug"]`)
      .type('../fake-data/blob/hipster.txt', { force: true })
      .invoke('val')
      .should('match', new RegExp('../fake-data/blob/hipster.txt'));

    cy.get(entityCreateSaveButtonSelector).click({ force: true });
    cy.scrollTo('top', { ensureScrollable: false });
    cy.get(entityCreateSaveButtonSelector).should('not.exist');
    cy.intercept('GET', '/api/educations*').as('entitiesRequestAfterCreate');
    cy.visit('/');
    cy.clickOnEntityMenuItem('education');
    cy.wait('@entitiesRequestAfterCreate');
    cy.get(entityTableSelector).should('have.lengthOf', startingEntitiesCount + 1);
    cy.visit('/');
  });

  it('should delete last instance of Education', () => {
    cy.intercept('GET', '/api/educations*').as('entitiesRequest');
    cy.intercept('GET', '/api/educations/*').as('dialogDeleteRequest');
    cy.intercept('DELETE', '/api/educations/*').as('deleteEntityRequest');
    cy.visit('/');
    cy.clickOnEntityMenuItem('education');
    cy.wait('@entitiesRequest').then(({ request, response }) => {
      startingEntitiesCount = response.body.length;
      if (startingEntitiesCount > 0) {
        cy.get(entityTableSelector).should('have.lengthOf', startingEntitiesCount);
        cy.get(entityDeleteButtonSelector).last().click({ force: true });
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('education').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click({ force: true });
        cy.wait('@deleteEntityRequest');
        cy.intercept('GET', '/api/educations*').as('entitiesRequestAfterDelete');
        cy.visit('/');
        cy.clickOnEntityMenuItem('education');
        cy.wait('@entitiesRequestAfterDelete');
        cy.get(entityTableSelector).should('have.lengthOf', startingEntitiesCount - 1);
      }
      cy.visit('/');
    });
  });
});
