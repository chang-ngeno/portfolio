import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IPersonalDetails, defaultValue } from 'app/shared/model/personal-details.model';

export const ACTION_TYPES = {
  FETCH_PERSONALDETAILS_LIST: 'personalDetails/FETCH_PERSONALDETAILS_LIST',
  FETCH_PERSONALDETAILS: 'personalDetails/FETCH_PERSONALDETAILS',
  CREATE_PERSONALDETAILS: 'personalDetails/CREATE_PERSONALDETAILS',
  UPDATE_PERSONALDETAILS: 'personalDetails/UPDATE_PERSONALDETAILS',
  PARTIAL_UPDATE_PERSONALDETAILS: 'personalDetails/PARTIAL_UPDATE_PERSONALDETAILS',
  DELETE_PERSONALDETAILS: 'personalDetails/DELETE_PERSONALDETAILS',
  SET_BLOB: 'personalDetails/SET_BLOB',
  RESET: 'personalDetails/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IPersonalDetails>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false,
};

export type PersonalDetailsState = Readonly<typeof initialState>;

// Reducer

export default (state: PersonalDetailsState = initialState, action): PersonalDetailsState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_PERSONALDETAILS_LIST):
    case REQUEST(ACTION_TYPES.FETCH_PERSONALDETAILS):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_PERSONALDETAILS):
    case REQUEST(ACTION_TYPES.UPDATE_PERSONALDETAILS):
    case REQUEST(ACTION_TYPES.DELETE_PERSONALDETAILS):
    case REQUEST(ACTION_TYPES.PARTIAL_UPDATE_PERSONALDETAILS):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_PERSONALDETAILS_LIST):
    case FAILURE(ACTION_TYPES.FETCH_PERSONALDETAILS):
    case FAILURE(ACTION_TYPES.CREATE_PERSONALDETAILS):
    case FAILURE(ACTION_TYPES.UPDATE_PERSONALDETAILS):
    case FAILURE(ACTION_TYPES.PARTIAL_UPDATE_PERSONALDETAILS):
    case FAILURE(ACTION_TYPES.DELETE_PERSONALDETAILS):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_PERSONALDETAILS_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.FETCH_PERSONALDETAILS):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_PERSONALDETAILS):
    case SUCCESS(ACTION_TYPES.UPDATE_PERSONALDETAILS):
    case SUCCESS(ACTION_TYPES.PARTIAL_UPDATE_PERSONALDETAILS):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_PERSONALDETAILS):
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

const apiUrl = 'api/personal-details';

// Actions

export const getEntities: ICrudGetAllAction<IPersonalDetails> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_PERSONALDETAILS_LIST,
  payload: axios.get<IPersonalDetails>(`${apiUrl}?cacheBuster=${new Date().getTime()}`),
});

export const getEntity: ICrudGetAction<IPersonalDetails> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_PERSONALDETAILS,
    payload: axios.get<IPersonalDetails>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IPersonalDetails> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_PERSONALDETAILS,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IPersonalDetails> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_PERSONALDETAILS,
    payload: axios.put(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const partialUpdate: ICrudPutAction<IPersonalDetails> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.PARTIAL_UPDATE_PERSONALDETAILS,
    payload: axios.patch(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IPersonalDetails> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_PERSONALDETAILS,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
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
