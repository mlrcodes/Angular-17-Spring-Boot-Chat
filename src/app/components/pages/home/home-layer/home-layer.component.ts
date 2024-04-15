import { Component, EventEmitter, OnInit, Output } from '@angular/core';

@Component({
  selector: 'app-home-layer',
  standalone: true,
  imports: [],
  templateUrl: './home-layer.component.html',
  styleUrl: './home-layer.component.scss'
})
export class HomeLayerComponent implements OnInit {

  pageTitle: string = "Home";
  @Output() titleEmmiter = new EventEmitter<string>();

  ngOnInit() {
    this.titleEmmiter.emit(this.pageTitle)
  }

}
