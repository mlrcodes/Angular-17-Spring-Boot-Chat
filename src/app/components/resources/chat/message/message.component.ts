import { CommonModule } from '@angular/common';
import { Component, Input, OnInit } from '@angular/core';
import { Message } from '../../../../core/models/message';

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
    console.log(this.message)
    console.log(this.sentByOwner)
  }
}
