
import {Injectable} from '@angular/core';
import {HttpEvent, HttpHandler, HttpHeaders, HttpInterceptor, HttpRequest} from '@angular/common/http';
import {Observable} from 'rxjs';
import { TokenService } from '../../token/token.service';

@Injectable()
export class HttpTokenInterceptor implements HttpInterceptor {

  constructor(
    private tokenService: TokenService
  ) {}


intercept(req: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>> {
  const token: string = this.tokenService.token;
  if(token){
    const authReq = req.clone( {
      headers: new HttpHeaders({
        Authorization: 'Bearer ' + token
      })
    });
    return next.handle(authReq);
  }
    return next.handle(req);
}
};
