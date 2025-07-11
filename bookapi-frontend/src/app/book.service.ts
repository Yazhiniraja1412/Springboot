import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface Book {
  isbn: string;
  title: string;
  author: string;
  publicationYear: number; 
}

@Injectable({ providedIn: 'root' })
export class BookService {
  private baseUrl = 'http://localhost:7070/api/books';

  constructor(private http: HttpClient) {}

  getAll(): Observable<Book[]> {
    return this.http.get<Book[]>(this.baseUrl);
  }

  get(isbn: string): Observable<Book> {
    return this.http.get<Book>(`${this.baseUrl}/${isbn}`);
  }

  add(book: Book): Observable<Book> {
    return this.http.post<Book>(this.baseUrl, book);
  }

  update(isbn: string, book: Book): Observable<Book> {
    return this.http.put<Book>(`${this.baseUrl}/${isbn}`, book);
  }

  delete(isbn: string): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${isbn}`);
  }
}
