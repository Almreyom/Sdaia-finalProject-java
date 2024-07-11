import { Routes } from '@angular/router';
import { LoginComponent } from './Pages/login/login.component';
import { LayoutComponent } from './Pages/layout/layout.component';
import { DashboardComponent } from './Pages/dashboard/dashboard.component';
import { DoctorComponent } from './Pages/doctor/doctor.component';

export const routes: Routes = [
    {
        path: '',
        redirectTo:'login',
        pathMatch:'full'

    },
    {
        path: 'login',
        component:LoginComponent

    },
    {
        path: '',
        component:LayoutComponent,
        children:[{
            path:'dashboard',
            component: DashboardComponent
        }]
    },
    {
        path: 'doctor',
        component:DoctorComponent,
        children:[{
            path:'',
            component: LayoutComponent
        }]
    },
];
