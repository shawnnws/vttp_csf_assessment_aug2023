import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { TagCount } from 'src/app/model/tag-count';
import { retrieveTopTags } from 'src/app/service/post.service';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-view0',
  templateUrl: './view0.component.html',
  styleUrls: ['./view0.component.css']
})
export class View0Component implements OnInit {

  selectedTime: number = 5
  topTags: TagCount[] = []

  constructor(private http: HttpClient, private activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.selectedTime = this.activatedRoute.snapshot.params['selectedTime'] || 5;
    this.getTopTags
  }

  onSelectChange() {
    this.getTopTags
  }

  getTopTags() {
    retrieveTopTags(this.http, this.selectedTime).subscribe(
      (response) => {
        this.topTags = response;
      },
      (error) => {
        console.error('Error fetching top tags', error);
      }
    );
  }
} 
