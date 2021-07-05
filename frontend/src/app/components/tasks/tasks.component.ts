import { AfterViewInit, Component, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';

/**
 * @title Table with pagination
 */
@Component({
  selector: 'tasks.component',
  styleUrls: ['tasks.component.css'],
  templateUrl: 'tasks.component.html',
})
export class TasksComponent implements AfterViewInit {
  displayedColumns: string[] = ['position', 'todo', 'date'];
  dataSource = new MatTableDataSource<PeriodicElement>(ELEMENT_DATA);
  clickedRows = new Set<PeriodicElement>();

  @ViewChild(MatPaginator)
  paginator!: MatPaginator;

  ngAfterViewInit() {
    this.dataSource.paginator = this.paginator;
  }
}

export interface PeriodicElement {
  position: number;
  todo: String;
  date: number;
}

const ELEMENT_DATA: PeriodicElement[] = [
  { position: 1, todo: 'Hydrogen', date: 1.0079 },
  { position: 2, todo: 'Helium', date: 4.0026 },
  { position: 3, todo: 'Lithium', date: 6.941 },
  { position: 4, todo: 'Beryllium', date: 9.0122 },
  { position: 5, todo: 'Boron', date: 10.811 },
  { position: 6, todo: 'Carbon', date: 12.0107 },
  { position: 7, todo: 'Nitrogen', date: 14.0067 },
  { position: 8, todo: 'Oxygen', date: 15.9994 },
  { position: 9, todo: 'Fluorine', date: 18.9984 },
  { position: 10, todo: 'Neon', date: 20.1797 },
  { position: 11, todo: 'Sodium', date: 22.9897 },
  { position: 12, todo: 'Magnesium', date: 24.305 },
  { position: 13, todo: 'Aluminum', date: 26.9815 },
  { position: 14, todo: 'Silicon', date: 28.0855 },
  { position: 15, todo: 'Phosphorus', date: 30.9738 },
  { position: 16, todo: 'Sulfur', date: 32.065 },
  { position: 17, todo: 'Chlorine', date: 35.453 },
  { position: 18, todo: 'Argon', date: 39.948 },
  { position: 19, todo: 'Potassium', date: 39.098 },
  { position: 20, todo: 'Calcium', date: 40.078 },
];
