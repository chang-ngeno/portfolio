export interface IPersonalDetails {
  id?: number;
  names?: string;
  slug?: string;
  email?: string;
  phoneNumber?: string | null;
}

export const defaultValue: Readonly<IPersonalDetails> = {};
