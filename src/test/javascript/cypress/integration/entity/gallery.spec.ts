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

describe('Gallery e2e test', () => {
  let startingEntitiesCount = 0;

  before(() => {
    cy.window().then(win => {
      win.sessionStorage.clear();
    });

    cy.clearCookies();
    cy.intercept('GET', '/api/galleries*').as('entitiesRequest');
    cy.visit('');
    cy.login('admin', 'admin');
    cy.clickOnEntityMenuItem('gallery');
    cy.wait('@entitiesRequest').then(({ request, response }) => (startingEntitiesCount = response.body.length));
    cy.visit('/');
  });

  it('should load Galleries', () => {
    cy.intercept('GET', '/api/galleries*').as('entitiesRequest');
    cy.visit('/');
    cy.clickOnEntityMenuItem('gallery');
    cy.wait('@entitiesRequest');
    cy.getEntityHeading('Gallery').should('exist');
    if (startingEntitiesCount === 0) {
      cy.get(entityTableSelector).should('not.exist');
    } else {
      cy.get(entityTableSelector).should('have.lengthOf', startingEntitiesCount);
    }
    cy.visit('/');
  });

  it('should load details Gallery page', () => {
    cy.intercept('GET', '/api/galleries*').as('entitiesRequest');
    cy.visit('/');
    cy.clickOnEntityMenuItem('gallery');
    cy.wait('@entitiesRequest');
    if (startingEntitiesCount > 0) {
      cy.get(entityDetailsButtonSelector).first().click({ force: true });
      cy.getEntityDetailsHeading('gallery');
      cy.get(entityDetailsBackButtonSelector).should('exist');
    }
    cy.visit('/');
  });

  it('should load create Gallery page', () => {
    cy.intercept('GET', '/api/galleries*').as('entitiesRequest');
    cy.visit('/');
    cy.clickOnEntityMenuItem('gallery');
    cy.wait('@entitiesRequest');
    cy.get(entityCreateButtonSelector).click({ force: true });
    cy.getEntityCreateUpdateHeading('Gallery');
    cy.get(entityCreateSaveButtonSelector).should('exist');
    cy.visit('/');
  });

  it('should load edit Gallery page', () => {
    cy.intercept('GET', '/api/galleries*').as('entitiesRequest');
    cy.visit('/');
    cy.clickOnEntityMenuItem('gallery');
    cy.wait('@entitiesRequest');
    if (startingEntitiesCount > 0) {
      cy.get(entityEditButtonSelector).first().click({ force: true });
      cy.getEntityCreateUpdateHeading('Gallery');
      cy.get(entityCreateSaveButtonSelector).should('exist');
    }
    cy.visit('/');
  });

  it('should create an instance of Gallery', () => {
    cy.intercept('GET', '/api/galleries*').as('entitiesRequest');
    cy.visit('/');
    cy.clickOnEntityMenuItem('gallery');
    cy.wait('@entitiesRequest').then(({ request, response }) => (startingEntitiesCount = response.body.length));
    cy.get(entityCreateButtonSelector).click({ force: true });
    cy.getEntityCreateUpdateHeading('Gallery');

    cy.get(`[data-cy="galleryName"]`)
      .type('Senegal invoice Buckinghamshire', { force: true })
      .invoke('val')
      .should('match', new RegExp('Senegal invoice Buckinghamshire'));

    cy.get(`[data-cy="slug"]`)
      .type('../fake-data/blob/hipster.txt', { force: true })
      .invoke('val')
      .should('match', new RegExp('../fake-data/blob/hipster.txt'));

    cy.setFieldImageAsBytesOfEntity('photo', 'integration-test.png', 'image/png');

    cy.setFieldSelectToLastOfEntity('project');

    cy.get(entityCreateSaveButtonSelector).click({ force: true });
    cy.scrollTo('top', { ensureScrollable: false });
    cy.get(entityCreateSaveButtonSelector).should('not.exist');
    cy.intercept('GET', '/api/galleries*').as('entitiesRequestAfterCreate');
    cy.visit('/');
    cy.clickOnEntityMenuItem('gallery');
    cy.wait('@entitiesRequestAfterCreate');
    cy.get(entityTableSelector).should('have.lengthOf', startingEntitiesCount + 1);
    cy.visit('/');
  });

  it('should delete last instance of Gallery', () => {
    cy.intercept('GET', '/api/galleries*').as('entitiesRequest');
    cy.intercept('GET', '/api/galleries/*').as('dialogDeleteRequest');
    cy.intercept('DELETE', '/api/galleries/*').as('deleteEntityRequest');
    cy.visit('/');
    cy.clickOnEntityMenuItem('gallery');
    cy.wait('@entitiesRequest').then(({ request, response }) => {
      startingEntitiesCount = response.body.length;
      if (startingEntitiesCount > 0) {
        cy.get(entityTableSelector).should('have.lengthOf', startingEntitiesCount);
        cy.get(entityDeleteButtonSelector).last().click({ force: true });
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('gallery').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click({ force: true });
        cy.wait('@deleteEntityRequest');
        cy.intercept('GET', '/api/galleries*').as('entitiesRequestAfterDelete');
        cy.visit('/');
        cy.clickOnEntityMenuItem('gallery');
        cy.wait('@entitiesRequestAfterDelete');
        cy.get(entityTableSelector).should('have.lengthOf', startingEntitiesCount - 1);
      }
      cy.visit('/');
    });
  });
});
