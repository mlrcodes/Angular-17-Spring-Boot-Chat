import { Component } from '@angular/core';
import { MenuItem, PrimeIcons } from 'primeng/api';
import { TabMenuModule } from 'primeng/tabmenu';

@Component({
  selector: 'app-tab-menu',
  standalone: true,
  imports: [TabMenuModule],
  templateUrl: './tab-menu.component.html',
  styleUrl: './tab-menu.component.scss'
})
export class TabMenuComponent {

  items: MenuItem[] = [
    {
      label: 'Chats',
      icon: PrimeIcons.HOME,
      routerLink: '/home/chats'
    },
    {
      label: 'Halls',
      icon: PrimeIcons.USER,
      routerLink: '/home/halls'
    },
    {
      label: 'Contacts',
      icon: PrimeIcons.USERS,
      routerLink: '/home/contacts'
    }
  ]

  activeItem: MenuItem = this.items[0]

  onActiveItemChange(menuItem: MenuItem) {
    this.activeItem = menuItem;
  }
}
