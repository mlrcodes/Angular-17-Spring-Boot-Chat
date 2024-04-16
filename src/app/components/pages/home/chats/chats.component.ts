import { Component, EventEmitter, Output } from '@angular/core';
import { MessageService } from 'primeng/api';
import { MessagesModule } from 'primeng/messages';

@Component({
  selector: 'app-chats',
  standalone: true,
  imports: [MessagesModule],
  templateUrl: './chats.component.html',
  styleUrl: './chats.component.scss'
})
export class ChatsComponent {

  constructor(
    private messageService: MessageService,
  ) {}
}
