import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';

import { App } from './app';
import { AppRoutingModule } from './app-routing-module';

// Standalone components (just imported in imports array)
import { Login } from './login/login';
import { Dashboard } from './dashboard/dashboard';
import { BookList } from './book-list/book-list';
import { BookForm } from './book-form/book-form';
import { AuthInterceptor } from './auth.interceptor';
import { Home } from './home/home';

@NgModule({
  declarations: [App], // ✅ Correct
  imports: [
    BrowserModule,
    FormsModule,
    HttpClientModule,
    AppRoutingModule,
    Login,
    Dashboard,
    BookList,
    BookForm,
    Home
  ],
  providers: [
    { provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true }
  ],
  bootstrap: [App] // ✅ Bootstrapping non-standalone App
})
export class AppModule {}
