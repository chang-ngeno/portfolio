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

import { IEducation, defaultValue } from 'app/shared/model/education.model';

export const ACTION_TYPES = {
  FETCH_EDUCATION_LIST: 'education/FETCH_EDUCATION_LIST',
  FETCH_EDUCATION: 'education/FETCH_EDUCATION',
  CREATE_EDUCATION: 'education/CREATE_EDUCATION',
  UPDATE_EDUCATION: 'education/UPDATE_EDUCATION',
  PARTIAL_UPDATE_EDUCATION: 'education/PARTIAL_UPDATE_EDUCATION',
  DELETE_EDUCATION: 'education/DELETE_EDUCATION',
  SET_BLOB: 'education/SET_BLOB',
  RESET: 'education/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IEducation>,
  entity: defaultValue,
  links: { next: 0 },
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type EducationState = Readonly<typeof initialState>;

// Reducer

export default (state: EducationState = initialState, action): EducationState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_EDUCATION_LIST):
    case REQUEST(ACTION_TYPES.FETCH_EDUCATION):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_EDUCATION):
    case REQUEST(ACTION_TYPES.UPDATE_EDUCATION):
    case REQUEST(ACTION_TYPES.DELETE_EDUCATION):
    case REQUEST(ACTION_TYPES.PARTIAL_UPDATE_EDUCATION):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_EDUCATION_LIST):
    case FAILURE(ACTION_TYPES.FETCH_EDUCATION):
    case FAILURE(ACTION_TYPES.CREATE_EDUCATION):
    case FAILURE(ACTION_TYPES.UPDATE_EDUCATION):
    case FAILURE(ACTION_TYPES.PARTIAL_UPDATE_EDUCATION):
    case FAILURE(ACTION_TYPES.DELETE_EDUCATION):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_EDUCATION_LIST): {
      const links = parseHeaderForLinks(action.payload.headers.link);

      return {
        ...state,
        loading: false,
        links,
        entities: loadMoreDataWhenScrolled(state.entities, action.payload.data, links),
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    }
    case SUCCESS(ACTION_TYPES.FETCH_EDUCATION):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_EDUCATION):
    case SUCCESS(ACTION_TYPES.UPDATE_EDUCATION):
    case SUCCESS(ACTION_TYPES.PARTIAL_UPDATE_EDUCATION):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_EDUCATION):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {},
      };
    case ACTION_TYPES.SET_BLOB: {
      const { name, data, contentType } = action.payload;
      return {
        ...state,
        entity: {
          ...state.entity,
          [name]: data,
          [name + 'ContentType']: contentType,
        },
      };
    }
    case ACTION_TYPES.RESET:
      return {
        ...initialState,
      };
    default:
      return state;
  }
};

const apiUrl = 'api/educations';

// Actions

export const getEntities: ICrudGetAllAction<IEducation> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_EDUCATION_LIST,
    payload: axios.get<IEducation>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<IEducation> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_EDUCATION,
    payload: axios.get<IEducation>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IEducation> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_EDUCATION,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const updateEntity: ICrudPutAction<IEducation> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_EDUCATION,
    payload: axios.put(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const partialUpdate: ICrudPutAction<IEducation> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.PARTIAL_UPDATE_EDUCATION,
    payload: axios.patch(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IEducation> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_EDUCATION,
    payload: axios.delete(requestUrl),
  });
  return result;
};

export const setBlob = (name, data, contentType?) => ({
  type: ACTION_TYPES.SET_BLOB,
  payload: {
    name,
    data,
    contentType,
  },
});

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
