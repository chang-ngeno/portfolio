import dayjs from 'dayjs';

export interface IEducation {
  id?: number;
  qualification?: string;
  institution?: string;
  startDate?: string;
  endDate?: string;
  slug?: string | null;
}

export const defaultValue: Readonly<IEducation> = {};
