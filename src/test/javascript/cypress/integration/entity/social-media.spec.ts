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

describe('SocialMedia e2e test', () => {
  let startingEntitiesCount = 0;

  before(() => {
    cy.window().then(win => {
      win.sessionStorage.clear();
    });

    cy.clearCookies();
    cy.intercept('GET', '/api/social-medias*').as('entitiesRequest');
    cy.visit('');
    cy.login('admin', 'admin');
    cy.clickOnEntityMenuItem('social-media');
    cy.wait('@entitiesRequest').then(({ request, response }) => (startingEntitiesCount = response.body.length));
    cy.visit('/');
  });

  it('should load SocialMedias', () => {
    cy.intercept('GET', '/api/social-medias*').as('entitiesRequest');
    cy.visit('/');
    cy.clickOnEntityMenuItem('social-media');
    cy.wait('@entitiesRequest');
    cy.getEntityHeading('SocialMedia').should('exist');
    if (startingEntitiesCount === 0) {
      cy.get(entityTableSelector).should('not.exist');
    } else {
      cy.get(entityTableSelector).should('have.lengthOf', startingEntitiesCount);
    }
    cy.visit('/');
  });

  it('should load details SocialMedia page', () => {
    cy.intercept('GET', '/api/social-medias*').as('entitiesRequest');
    cy.visit('/');
    cy.clickOnEntityMenuItem('social-media');
    cy.wait('@entitiesRequest');
    if (startingEntitiesCount > 0) {
      cy.get(entityDetailsButtonSelector).first().click({ force: true });
      cy.getEntityDetailsHeading('socialMedia');
      cy.get(entityDetailsBackButtonSelector).should('exist');
    }
    cy.visit('/');
  });

  it('should load create SocialMedia page', () => {
    cy.intercept('GET', '/api/social-medias*').as('entitiesRequest');
    cy.visit('/');
    cy.clickOnEntityMenuItem('social-media');
    cy.wait('@entitiesRequest');
    cy.get(entityCreateButtonSelector).click({ force: true });
    cy.getEntityCreateUpdateHeading('SocialMedia');
    cy.get(entityCreateSaveButtonSelector).should('exist');
    cy.visit('/');
  });

  it('should load edit SocialMedia page', () => {
    cy.intercept('GET', '/api/social-medias*').as('entitiesRequest');
    cy.visit('/');
    cy.clickOnEntityMenuItem('social-media');
    cy.wait('@entitiesRequest');
    if (startingEntitiesCount > 0) {
      cy.get(entityEditButtonSelector).first().click({ force: true });
      cy.getEntityCreateUpdateHeading('SocialMedia');
      cy.get(entityCreateSaveButtonSelector).should('exist');
    }
    cy.visit('/');
  });

  it('should create an instance of SocialMedia', () => {
    cy.intercept('GET', '/api/social-medias*').as('entitiesRequest');
    cy.visit('/');
    cy.clickOnEntityMenuItem('social-media');
    cy.wait('@entitiesRequest').then(({ request, response }) => (startingEntitiesCount = response.body.length));
    cy.get(entityCreateButtonSelector).click({ force: true });
    cy.getEntityCreateUpdateHeading('SocialMedia');

    cy.get(`[data-cy="username"]`)
      .type('Island Communications', { force: true })
      .invoke('val')
      .should('match', new RegExp('Island Communications'));

    cy.get(`[data-cy="urlLink"]`)
      .type('Principal Practical Public-key', { force: true })
      .invoke('val')
      .should('match', new RegExp('Principal Practical Public-key'));

    cy.get(`[data-cy="published"]`).should('not.be.checked');
    cy.get(`[data-cy="published"]`).click().should('be.checked');
    cy.get(entityCreateSaveButtonSelector).click({ force: true });
    cy.scrollTo('top', { ensureScrollable: false });
    cy.get(entityCreateSaveButtonSelector).should('not.exist');
    cy.intercept('GET', '/api/social-medias*').as('entitiesRequestAfterCreate');
    cy.visit('/');
    cy.clickOnEntityMenuItem('social-media');
    cy.wait('@entitiesRequestAfterCreate');
    cy.get(entityTableSelector).should('have.lengthOf', startingEntitiesCount + 1);
    cy.visit('/');
  });

  it('should delete last instance of SocialMedia', () => {
    cy.intercept('GET', '/api/social-medias*').as('entitiesRequest');
    cy.intercept('GET', '/api/social-medias/*').as('dialogDeleteRequest');
    cy.intercept('DELETE', '/api/social-medias/*').as('deleteEntityRequest');
    cy.visit('/');
    cy.clickOnEntityMenuItem('social-media');
    cy.wait('@entitiesRequest').then(({ request, response }) => {
      startingEntitiesCount = response.body.length;
      if (startingEntitiesCount > 0) {
        cy.get(entityTableSelector).should('have.lengthOf', startingEntitiesCount);
        cy.get(entityDeleteButtonSelector).last().click({ force: true });
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('socialMedia').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click({ force: true });
        cy.wait('@deleteEntityRequest');
        cy.intercept('GET', '/api/social-medias*').as('entitiesRequestAfterDelete');
        cy.visit('/');
        cy.clickOnEntityMenuItem('social-media');
        cy.wait('@entitiesRequestAfterDelete');
        cy.get(entityTableSelector).should('have.lengthOf', startingEntitiesCount - 1);
      }
      cy.visit('/');
    });
  });
});
