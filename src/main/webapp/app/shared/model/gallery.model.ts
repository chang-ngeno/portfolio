import { IProject } from 'app/shared/model/project.model';

export interface IGallery {
  id?: number;
  galleryName?: string;
  slug?: string | null;
  photoContentType?: string | null;
  photo?: string | null;
  project?: IProject | null;
}

export const defaultValue: Readonly<IGallery> = {};
