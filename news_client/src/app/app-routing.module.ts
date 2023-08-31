import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Routes } from '@angular/router';
import { View2Component } from './components/view2/view2.component';
import { View0Component } from './components/view0/view0.component';
import { View1Component } from './components/view1/view1.component';

const routes: Routes = [
  {path:"", component: View0Component},
  {path:"view0/:selectedTime", component: View0Component},
  {path: "view1/:tag/:selectedTime", component: View1Component},
  {path:"view2", component: View2Component},
  { path: "**", redirectTo: "/", pathMatch: "full" }
]

@NgModule({
  declarations: [],
  imports: [
    CommonModule,
    RouterModule.forRoot(routes),
  ],
  exports: [
    RouterModule,
  ]
})
export class AppRoutingModule { }
