export enum Gender {
  Male = 'MALE',
  Female = 'FEMALE',
  Other = 'OTHER',
}
export interface Token {
  token: string;
}

export interface User {
  id: number;
  email: string;
  gender: Gender;
  name: string;
  lastname: string;
  role: string;
  enabled: boolean;
}
