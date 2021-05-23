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

import { IGallery, defaultValue } from 'app/shared/model/gallery.model';

export const ACTION_TYPES = {
  FETCH_GALLERY_LIST: 'gallery/FETCH_GALLERY_LIST',
  FETCH_GALLERY: 'gallery/FETCH_GALLERY',
  CREATE_GALLERY: 'gallery/CREATE_GALLERY',
  UPDATE_GALLERY: 'gallery/UPDATE_GALLERY',
  PARTIAL_UPDATE_GALLERY: 'gallery/PARTIAL_UPDATE_GALLERY',
  DELETE_GALLERY: 'gallery/DELETE_GALLERY',
  SET_BLOB: 'gallery/SET_BLOB',
  RESET: 'gallery/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IGallery>,
  entity: defaultValue,
  links: { next: 0 },
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type GalleryState = Readonly<typeof initialState>;

// Reducer

export default (state: GalleryState = initialState, action): GalleryState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_GALLERY_LIST):
    case REQUEST(ACTION_TYPES.FETCH_GALLERY):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_GALLERY):
    case REQUEST(ACTION_TYPES.UPDATE_GALLERY):
    case REQUEST(ACTION_TYPES.DELETE_GALLERY):
    case REQUEST(ACTION_TYPES.PARTIAL_UPDATE_GALLERY):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_GALLERY_LIST):
    case FAILURE(ACTION_TYPES.FETCH_GALLERY):
    case FAILURE(ACTION_TYPES.CREATE_GALLERY):
    case FAILURE(ACTION_TYPES.UPDATE_GALLERY):
    case FAILURE(ACTION_TYPES.PARTIAL_UPDATE_GALLERY):
    case FAILURE(ACTION_TYPES.DELETE_GALLERY):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_GALLERY_LIST): {
      const links = parseHeaderForLinks(action.payload.headers.link);

      return {
        ...state,
        loading: false,
        links,
        entities: loadMoreDataWhenScrolled(state.entities, action.payload.data, links),
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    }
    case SUCCESS(ACTION_TYPES.FETCH_GALLERY):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_GALLERY):
    case SUCCESS(ACTION_TYPES.UPDATE_GALLERY):
    case SUCCESS(ACTION_TYPES.PARTIAL_UPDATE_GALLERY):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_GALLERY):
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

const apiUrl = 'api/galleries';

// Actions

export const getEntities: ICrudGetAllAction<IGallery> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_GALLERY_LIST,
    payload: axios.get<IGallery>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<IGallery> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_GALLERY,
    payload: axios.get<IGallery>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IGallery> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_GALLERY,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const updateEntity: ICrudPutAction<IGallery> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_GALLERY,
    payload: axios.put(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const partialUpdate: ICrudPutAction<IGallery> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.PARTIAL_UPDATE_GALLERY,
    payload: axios.patch(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IGallery> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_GALLERY,
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
