import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { ISocialMedia, defaultValue } from 'app/shared/model/social-media.model';

export const ACTION_TYPES = {
  FETCH_SOCIALMEDIA_LIST: 'socialMedia/FETCH_SOCIALMEDIA_LIST',
  FETCH_SOCIALMEDIA: 'socialMedia/FETCH_SOCIALMEDIA',
  CREATE_SOCIALMEDIA: 'socialMedia/CREATE_SOCIALMEDIA',
  UPDATE_SOCIALMEDIA: 'socialMedia/UPDATE_SOCIALMEDIA',
  PARTIAL_UPDATE_SOCIALMEDIA: 'socialMedia/PARTIAL_UPDATE_SOCIALMEDIA',
  DELETE_SOCIALMEDIA: 'socialMedia/DELETE_SOCIALMEDIA',
  RESET: 'socialMedia/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<ISocialMedia>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false,
};

export type SocialMediaState = Readonly<typeof initialState>;

// Reducer

export default (state: SocialMediaState = initialState, action): SocialMediaState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_SOCIALMEDIA_LIST):
    case REQUEST(ACTION_TYPES.FETCH_SOCIALMEDIA):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_SOCIALMEDIA):
    case REQUEST(ACTION_TYPES.UPDATE_SOCIALMEDIA):
    case REQUEST(ACTION_TYPES.DELETE_SOCIALMEDIA):
    case REQUEST(ACTION_TYPES.PARTIAL_UPDATE_SOCIALMEDIA):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_SOCIALMEDIA_LIST):
    case FAILURE(ACTION_TYPES.FETCH_SOCIALMEDIA):
    case FAILURE(ACTION_TYPES.CREATE_SOCIALMEDIA):
    case FAILURE(ACTION_TYPES.UPDATE_SOCIALMEDIA):
    case FAILURE(ACTION_TYPES.PARTIAL_UPDATE_SOCIALMEDIA):
    case FAILURE(ACTION_TYPES.DELETE_SOCIALMEDIA):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_SOCIALMEDIA_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.FETCH_SOCIALMEDIA):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_SOCIALMEDIA):
    case SUCCESS(ACTION_TYPES.UPDATE_SOCIALMEDIA):
    case SUCCESS(ACTION_TYPES.PARTIAL_UPDATE_SOCIALMEDIA):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_SOCIALMEDIA):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {},
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState,
      };
    default:
      return state;
  }
};

const apiUrl = 'api/social-medias';

// Actions

export const getEntities: ICrudGetAllAction<ISocialMedia> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_SOCIALMEDIA_LIST,
  payload: axios.get<ISocialMedia>(`${apiUrl}?cacheBuster=${new Date().getTime()}`),
});

export const getEntity: ICrudGetAction<ISocialMedia> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_SOCIALMEDIA,
    payload: axios.get<ISocialMedia>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<ISocialMedia> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_SOCIALMEDIA,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<ISocialMedia> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_SOCIALMEDIA,
    payload: axios.put(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const partialUpdate: ICrudPutAction<ISocialMedia> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.PARTIAL_UPDATE_SOCIALMEDIA,
    payload: axios.patch(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<ISocialMedia> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_SOCIALMEDIA,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
