import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Router } from '@angular/router';
import { postNews } from 'src/app/service/post.service';

@Component({
  selector: 'app-view2',
  templateUrl: './view2.component.html',
  styleUrls: ['./view2.component.css']
})
export class View2Component implements OnInit {

  myForm!: FormGroup
  tags: string[] = []

  constructor(private fb: FormBuilder, private http: HttpClient, private router: Router) {}

  ngOnInit(): void {
    this.createForm()
  }

  private createForm() {
    this.myForm = this.fb.group({
      title: ['', [Validators.required, Validators.minLength(5)]],
      photo: ['', Validators.required],
      description: ['', [Validators.required, Validators.minLength(5)]],
      tags: [null]
    })
  }

  invalidFormInput(controlName: string): boolean {
    const control = this.myForm.controls[controlName];
    return control.invalid && control.touched;
  }

  onAddTag() {
    const tags = this.myForm.value.tags.trim()
    if (tags) {
      const tagsList = tags.split(' ')
      this.tags = [...this.tags, ...tagsList];
      this.myForm.patchValue({ tags: ''})
    }
  }

  removeTag(tag: string) {
    this.tags = this.tags.filter(t => t !== tag)
  }

  photoUpload(event: any) {
    const file = event.target.files[0];
    this.myForm.patchValue({ photo: file });
    this.myForm.get('photo')?.markAsTouched();
  }

  submitForm() {
    if (this.myForm.valid) {
      const formData = new FormData()
      formData.append('title', this.myForm.value.title)
      formData.append('description', this.myForm.value.description)
      formData.append('image', this.myForm.value.photo)
      formData.append('tags', JSON.stringify(this.tags))

      postNews(this.http, formData).subscribe(
        (response) => {
          console.log('Form submitted successfully', response);
          alert(JSON.stringify(response))
        },
        (error) => {
          console.error('Error submitting form', error);
          alert(JSON.stringify(error))
        }
      );
    } else {
      console.log('Form contains errors');
    }
  }
}
