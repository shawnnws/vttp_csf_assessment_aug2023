import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Post } from '../model/post';

@Injectable({
  providedIn: 'root'
})
export class PostService {

  constructor() { }

  uploadPost(post: Post, http: HttpClient) {
    
  }
}
