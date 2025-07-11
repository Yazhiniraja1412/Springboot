import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';

import { Book, BookService } from '../book.service';

@Component({
  standalone: true,
  selector: 'app-book-form',
  templateUrl: './book-form.html',
  styleUrls: ['./book-form.css'],
  imports: [CommonModule, FormsModule]
})
export class BookForm implements OnInit {
  book: Book = { isbn: '', title: '', author: '', publicationYear: 0 };
  isEdit = false;

  constructor(
    private service: BookService,
    private route: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit() {
    const isbn = this.route.snapshot.paramMap.get('isbn');
    if (isbn) {
      this.isEdit = true;
      this.service.get(isbn).subscribe(book => this.book = book);
    }
  }

  clearForm() {
    this.book = { isbn: '', title: '', author: '', publicationYear: 0 };
    this.isEdit = false;
  }

  goToDashboard() {
    this.router.navigate(['/dashboard']);  // Adjust route if needed
  }

  submit() {
    if (this.isEdit) {
      this.service.update(this.book.isbn, this.book)
        .subscribe(() => this.router.navigate(['/books']));
    } else {
      this.service.add(this.book)
        .subscribe(() => this.router.navigate(['/books']));
    }
  }
}
