import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Post } from '../model/post';
import { Observable } from 'rxjs';
import { BACKEND_URL } from 'environment_variables';
import { TagCount } from '../model/tag-count';
import { TagNews } from '../model/tag-news';


export function postNews(httpCient: HttpClient, formData: FormData): Observable<any> {
  const headers = new HttpHeaders()
      .append('Accept', 'application/json');
  const url = `${BACKEND_URL}/news`;

  return httpCient.post(url, formData, { headers })
}

export function retrieveTopTags(http: HttpClient, selectedTime: number): Observable<TagCount[]> {
  const headers = new HttpHeaders()
    .append('Accept', 'application/json')
  const url = `${BACKEND_URL}/top-tags/${selectedTime}`;
  
  return http.get<TagCount[]>(url, { headers })
}

export function getTagNewsWithinTime(httpClient: HttpClient, selectedTime: number, tag: string): Observable<TagNews[]> {
  const headers = new HttpHeaders()
  .append('Accept', 'application/json');
  const url = `${BACKEND_URL}/tag-news?selectedTime=${selectedTime}&tag=${tag}`;
  return httpClient.get<TagNews[]>(url, { headers })
}