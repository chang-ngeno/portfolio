import axios from 'axios';
import {
  parseHeaderForLinks,
  loadMoreDataWhenScrolled,
  ICrudGetAction,
  ICrudGetAllAction,
  ICrudPutAction,
  ICrudDeleteAction,
} from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IHobby, defaultValue } from 'app/shared/model/hobby.model';

export const ACTION_TYPES = {
  FETCH_HOBBY_LIST: 'hobby/FETCH_HOBBY_LIST',
  FETCH_HOBBY: 'hobby/FETCH_HOBBY',
  CREATE_HOBBY: 'hobby/CREATE_HOBBY',
  UPDATE_HOBBY: 'hobby/UPDATE_HOBBY',
  PARTIAL_UPDATE_HOBBY: 'hobby/PARTIAL_UPDATE_HOBBY',
  DELETE_HOBBY: 'hobby/DELETE_HOBBY',
  RESET: 'hobby/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IHobby>,
  entity: defaultValue,
  links: { next: 0 },
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type HobbyState = Readonly<typeof initialState>;

// Reducer

export default (state: HobbyState = initialState, action): HobbyState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_HOBBY_LIST):
    case REQUEST(ACTION_TYPES.FETCH_HOBBY):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_HOBBY):
    case REQUEST(ACTION_TYPES.UPDATE_HOBBY):
    case REQUEST(ACTION_TYPES.DELETE_HOBBY):
    case REQUEST(ACTION_TYPES.PARTIAL_UPDATE_HOBBY):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_HOBBY_LIST):
    case FAILURE(ACTION_TYPES.FETCH_HOBBY):
    case FAILURE(ACTION_TYPES.CREATE_HOBBY):
    case FAILURE(ACTION_TYPES.UPDATE_HOBBY):
    case FAILURE(ACTION_TYPES.PARTIAL_UPDATE_HOBBY):
    case FAILURE(ACTION_TYPES.DELETE_HOBBY):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_HOBBY_LIST): {
      const links = parseHeaderForLinks(action.payload.headers.link);

      return {
        ...state,
        loading: false,
        links,
        entities: loadMoreDataWhenScrolled(state.entities, action.payload.data, links),
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    }
    case SUCCESS(ACTION_TYPES.FETCH_HOBBY):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_HOBBY):
    case SUCCESS(ACTION_TYPES.UPDATE_HOBBY):
    case SUCCESS(ACTION_TYPES.PARTIAL_UPDATE_HOBBY):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_HOBBY):
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

const apiUrl = 'api/hobbies';

// Actions

export const getEntities: ICrudGetAllAction<IHobby> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_HOBBY_LIST,
    payload: axios.get<IHobby>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<IHobby> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_HOBBY,
    payload: axios.get<IHobby>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IHobby> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_HOBBY,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const updateEntity: ICrudPutAction<IHobby> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_HOBBY,
    payload: axios.put(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const partialUpdate: ICrudPutAction<IHobby> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.PARTIAL_UPDATE_HOBBY,
    payload: axios.patch(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IHobby> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_HOBBY,
    payload: axios.delete(requestUrl),
  });
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
