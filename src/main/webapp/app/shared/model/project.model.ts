import { IGallery } from 'app/shared/model/gallery.model';

export interface IProject {
  id?: number;
  title?: string;
  slug?: string;
  galleries?: IGallery[] | null;
}

export const defaultValue: Readonly<IProject> = {};
