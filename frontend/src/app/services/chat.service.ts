
import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ChatService {

  private apiUrl = 'http://localhost:8081/agent';

  constructor(private http: HttpClient) {}

  sendMessage(message: string): Observable<string> {
    const params = new HttpParams().set('message', message);

    return this.http.get(this.apiUrl, {
      params,
      responseType: 'text'
    });
  }
}
