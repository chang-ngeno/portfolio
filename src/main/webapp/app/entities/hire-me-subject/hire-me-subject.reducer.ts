import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IHireMeSubject, defaultValue } from 'app/shared/model/hire-me-subject.model';

export const ACTION_TYPES = {
  FETCH_HIREMESUBJECT_LIST: 'hireMeSubject/FETCH_HIREMESUBJECT_LIST',
  FETCH_HIREMESUBJECT: 'hireMeSubject/FETCH_HIREMESUBJECT',
  CREATE_HIREMESUBJECT: 'hireMeSubject/CREATE_HIREMESUBJECT',
  UPDATE_HIREMESUBJECT: 'hireMeSubject/UPDATE_HIREMESUBJECT',
  PARTIAL_UPDATE_HIREMESUBJECT: 'hireMeSubject/PARTIAL_UPDATE_HIREMESUBJECT',
  DELETE_HIREMESUBJECT: 'hireMeSubject/DELETE_HIREMESUBJECT',
  RESET: 'hireMeSubject/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IHireMeSubject>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false,
};

export type HireMeSubjectState = Readonly<typeof initialState>;

// Reducer

export default (state: HireMeSubjectState = initialState, action): HireMeSubjectState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_HIREMESUBJECT_LIST):
    case REQUEST(ACTION_TYPES.FETCH_HIREMESUBJECT):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_HIREMESUBJECT):
    case REQUEST(ACTION_TYPES.UPDATE_HIREMESUBJECT):
    case REQUEST(ACTION_TYPES.DELETE_HIREMESUBJECT):
    case REQUEST(ACTION_TYPES.PARTIAL_UPDATE_HIREMESUBJECT):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_HIREMESUBJECT_LIST):
    case FAILURE(ACTION_TYPES.FETCH_HIREMESUBJECT):
    case FAILURE(ACTION_TYPES.CREATE_HIREMESUBJECT):
    case FAILURE(ACTION_TYPES.UPDATE_HIREMESUBJECT):
    case FAILURE(ACTION_TYPES.PARTIAL_UPDATE_HIREMESUBJECT):
    case FAILURE(ACTION_TYPES.DELETE_HIREMESUBJECT):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_HIREMESUBJECT_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.FETCH_HIREMESUBJECT):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_HIREMESUBJECT):
    case SUCCESS(ACTION_TYPES.UPDATE_HIREMESUBJECT):
    case SUCCESS(ACTION_TYPES.PARTIAL_UPDATE_HIREMESUBJECT):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_HIREMESUBJECT):
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

const apiUrl = 'api/hire-me-subjects';

// Actions

export const getEntities: ICrudGetAllAction<IHireMeSubject> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_HIREMESUBJECT_LIST,
  payload: axios.get<IHireMeSubject>(`${apiUrl}?cacheBuster=${new Date().getTime()}`),
});

export const getEntity: ICrudGetAction<IHireMeSubject> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_HIREMESUBJECT,
    payload: axios.get<IHireMeSubject>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IHireMeSubject> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_HIREMESUBJECT,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IHireMeSubject> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_HIREMESUBJECT,
    payload: axios.put(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const partialUpdate: ICrudPutAction<IHireMeSubject> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.PARTIAL_UPDATE_HIREMESUBJECT,
    payload: axios.patch(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IHireMeSubject> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_HIREMESUBJECT,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
