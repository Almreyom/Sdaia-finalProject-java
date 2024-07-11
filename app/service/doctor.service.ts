import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/internal/Observable';
import { doctor } from '../model/doctor.model'

@Injectable({
  providedIn: 'root'
})
export class DoctorService {
  private apiUrl = 'http://dev-1261.mshome.net:8080/Medical/webapi/doctors';


  constructor(private http: HttpClient) {}

    getAllDoctors(): Observable<doctor[]>{
      return this.http.get<doctor[]>(this.apiUrl + '/doctors');
    
  }
}