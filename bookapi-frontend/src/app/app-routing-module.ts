// src/app/app-routing-module.ts
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { Login } from './login/login';
import { Dashboard } from './dashboard/dashboard';
import { BookList } from './book-list/book-list';
import { BookForm } from './book-form/book-form';
import { Home } from './home/home'; // ✅ NEW

const routes: Routes = [
  { path: 'home', component: Home },
  { path: 'login', component: Login },
  { path: 'dashboard', component: Dashboard },
  { path: 'books', component: BookList },
  { path: 'books/new', component: BookForm },
  { path: 'books/edit/:isbn', component: BookForm },
  { path: '', redirectTo: 'home', pathMatch: 'full' } // ✅ default to /home
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {}
