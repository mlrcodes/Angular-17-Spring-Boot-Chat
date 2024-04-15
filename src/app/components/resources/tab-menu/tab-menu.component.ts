import { Component } from '@angular/core';
import { MenuItem } from 'primeng/api';
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
      label: 'Home',
      icon: 'pi-icon',
      url: '/home'
    },
    {
      label: 'Contacts',
      icon: 'pi-user',
      url: 'contacts'
    },
    {
      label: 'New Hall',
      icon: 'pi-users',
      command: () => this.displayNewHallPopup()
    },
  ]

  activeItem: MenuItem = this.items[0]

  onActiveItemChange(menuItem: MenuItem) {
    this.activeItem = menuItem;
  }

  displayNewHallPopup() {
     console.log("BIEN")
  }
}
