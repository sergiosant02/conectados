export interface HttpResponse<T> {
  data: T;
  code: number;
  message: string;
}

export interface JwtRequest {
  username: string;
  password: string;
}

export interface JwtResponse {
  jwttoken: string;
  data: any;
  error: string;
}

export interface ResponseEntity<T> {
  jwttoken: string;
  data: T;
  error: any;
}
