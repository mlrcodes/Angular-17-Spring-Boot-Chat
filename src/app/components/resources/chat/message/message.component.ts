import { CommonModule } from '@angular/common';
import { Component, Input, OnInit } from '@angular/core';
import { Message } from 'primeng/api';

@Component({
  selector: 'app-message',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './message.component.html',
  styleUrl: './message.component.scss'
})
export class MessageComponent implements OnInit {


  @Input() message!: Message;
  @Input() sentByOwner!: boolean;

  ngOnInit() {
    
  }

}
