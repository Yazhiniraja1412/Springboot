import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { BookService, Book } from '../book.service';

@Component({
  standalone: true,
  selector: 'book-list',
  templateUrl: './book-list.html',
  styleUrls: ['./book-list.css'],
  imports: [CommonModule, RouterModule, FormsModule]
})
export class BookList implements OnInit {
  books: Book[] = [];
  searchIsbn: string = '';
  searchedBook: Book | null = null;
  error: string = '';

  constructor(private bookService: BookService, private router: Router) {}

  ngOnInit() {

    
    this.loadAllBooks();
  }
  goToDashboard() {
  this.router.navigate(['/dashboard']);  // or your actual dashboard route
}


  loadAllBooks() {
    this.bookService.getAll().subscribe(data => {
      this.books = data;
    });
  }

  deleteBook(isbn: string) {
    this.bookService.delete(isbn).subscribe(() => {
      this.books = this.books.filter(book => book.isbn !== isbn);
      // Also clear searched result if deleted
      if (this.searchedBook?.isbn === isbn) this.clearSearch();
    });
  }

  searchBookByIsbn() {
    const isbn = this.searchIsbn.trim();
    if (!isbn) return;

    this.bookService.get(isbn).subscribe({
      next: (book) => {
        this.searchedBook = book;
        this.error = '';
      },
      error: () => {
        this.searchedBook = null;
        this.error = '❌ Book not found for given ISBN.';
      }
    });
  }

  clearSearch() {
    this.searchIsbn = '';
    this.searchedBook = null;
    this.error = '';
  }
}
