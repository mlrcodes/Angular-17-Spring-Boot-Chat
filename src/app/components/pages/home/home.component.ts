import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { TabMenuComponent } from './../../resources/tab-menu/tab-menu.component';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [RouterOutlet, TabMenuComponent],
  templateUrl: './home.component.html',
  styleUrl: './home.component.scss'
})
export class HomeComponent {

  pageTitle!: string;

  setPageTitle(pageTitle: any) {
    this.pageTitle = pageTitle;
  }
}
