import dayjs from 'dayjs';

export interface IWorkExperience {
  id?: number;
  title?: string;
  employer?: string;
  startDate?: string;
  endDate?: string | null;
  roles?: string;
}

export const defaultValue: Readonly<IWorkExperience> = {};
