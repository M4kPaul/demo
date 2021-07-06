import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { AbstractControl } from '@angular/forms';
import { Observable } from 'rxjs';
import { AppConstants } from '../common/app.constants';
import { TaskElement } from '../components/tasks/tasks.component';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' }),
};

@Injectable({
  providedIn: 'root',
})
export class TaskService {
  constructor(private http: HttpClient) {}

  getTasks(): Observable<any> {
    return this.http.get(AppConstants.API_URL + 'tasks');
  }

  createTask(task: TaskElement): Observable<any> {
    return this.http.post(AppConstants.API_URL + 'tasks',
      {
        id: task.id,
        description: task.description,
        done: task.done,
        modifiedDate: task.modifiedDate
      },
      httpOptions
    );
  }

  updateTask(task: TaskElement): Observable<any> {
    return this.http.put(AppConstants.API_URL + `task/${task.id}`,
      {
        id: task.id,
        description: task.description,
        done: task.done,
        modifiedDate: task.modifiedDate
      },
      httpOptions
    );
  }

  deleteTask(task: TaskElement): Observable<any> {
    return this.http.delete(AppConstants.API_URL + `task/${task.id}`,
      httpOptions
    );
  }
}
