import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { TagNews } from 'src/app/model/tag-news';
import { getTagNewsWithinTime } from 'src/app/service/post.service';

@Component({
  selector: 'app-view1',
  templateUrl: './view1.component.html',
  styleUrls: ['./view1.component.css']
})
export class View1Component {

  tag?: string
  selectedTime?: number = 5;
  tagNewsList: TagNews[] = [];

  constructor(private activatedRoute: ActivatedRoute, private http: HttpClient, private router: Router) { }

  ngOnInit(): void {
    this.activatedRoute.params.subscribe(params => {
      let tag = params['tag'];
      this.tag = tag;
      let selectedTime = params['selectedTime'];
      this.selectedTime = selectedTime;
      getTagNewsWithinTime(this.http, selectedTime, tag).subscribe(
        (response) => {
          this.tagNewsList = response;
        },
        (error) => {
          console.error('Error fetching top tags', error);
        }
      )
    })
  }

  navigateToView0() {
    this.router.navigate(['/view0', this.selectedTime]);
  }

  navigateToView2() {
    this.router.navigate(['/view2']);
  }
}
