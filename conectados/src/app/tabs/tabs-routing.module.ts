import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AuthGuard } from '../guards/auth.guard';
import { SelectedRoomGuard } from '../guards/selected-room.guard';
import { TabsPage } from './tabs.page';

const routes: Routes = [
  {
    path: 'room',
    component: TabsPage,
    canActivate: [AuthGuard, SelectedRoomGuard],
    children: [
      {
        path: 'products',
        loadChildren: () =>
          import('../tab1/tab1.module').then((m) => m.Tab1PageModule),
      },
      {
        path: 'notes',
        loadChildren: () =>
          import('../tab2/tab2.module').then((m) => m.Tab2PageModule),
      },
      {
        path: 'purchases',
        loadChildren: () =>
          import('../tab3/tab3.module').then((m) => m.Tab3PageModule),
      },
      {
        path: 'members',
        loadChildren: () =>
          import('../tab4/tab4.module').then((m) => m.Tab4PageModule),
      },
      {
        path: '',
        redirectTo: '/room/notes',
        pathMatch: 'full',
      },
    ],
  },
  {
    path: '',
    redirectTo: '/room/notes',
    pathMatch: 'full',
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
})
export class TabsPageRoutingModule {}
