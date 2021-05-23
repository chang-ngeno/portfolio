export interface ISocialMedia {
  id?: number;
  username?: string;
  urlLink?: string;
  published?: boolean;
}

export const defaultValue: Readonly<ISocialMedia> = {
  published: false,
};
