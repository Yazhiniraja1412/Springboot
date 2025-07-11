import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { AuthService } from '../auth.service';

@Component({
  standalone: true,
  selector: 'login',
  templateUrl: './login.html',
  styleUrls: ['./login.css'], // ← Add this line
  imports: [CommonModule, FormsModule]
})
export class Login {
  // your code stays same...
  credentials = {
    username: '',
    password: ''
  };

  error = '';

  constructor(private auth: AuthService, private router: Router) {}

  login() {
    if (!this.credentials.username || !this.credentials.password) {
      this.error = 'Please enter both username and password';
      return;
    }

    this.auth.login(this.credentials).subscribe({
      next: (token: string) => {
        this.auth.saveToken(token);
        this.router.navigate(['/dashboard']);
      },
      error: () => {
        this.error = 'Invalid username or password';
        console.warn('Login failed with:', this.credentials);
      }
    });
  }
}
