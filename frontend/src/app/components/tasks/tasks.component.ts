import { Inject } from '@angular/core';
import { OnInit } from '@angular/core';
import { AfterViewInit, Component, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { TaskService } from 'src/app/services/task.service';
import { MySnackBar } from 'src/app/util/mysnackbar';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA} from '@angular/material/dialog';
import { formatDate } from '@angular/common';
import { Sort } from '@angular/material/sort';

export interface TaskElement {
  id: number;
  description: string;
  modifiedDate: string;
  done: boolean;
}

@Component({
  selector: 'tasks.component',
  styleUrls: ['tasks.component.css'],
  templateUrl: 'tasks.component.html',
})
export class TasksComponent implements OnInit, AfterViewInit {
  displayedColumns: string[] = ['todo', 'date', 'modify'];
  dataSource = new MatTableDataSource<TaskElement>();
  allTasks = new MatTableDataSource<TaskElement>();
  doneTasks = new Set<TaskElement>();
  hide_done = false;
  errorMessage = '';
  searchValue = '';
  isLoading = true;
  newTask: TaskElement = { id: 0, description: "", modifiedDate: "", done: false };

  @ViewChild(MatPaginator)
  paginator!: MatPaginator;

  constructor(
    private taskService: TaskService,
    private mySnackBar: MySnackBar,
    public dialog: MatDialog
  ) { }

  ngOnInit(): void {
    this.hide_done = localStorage.getItem('hide') === 'true' ? true : false;
  }
  
  ngAfterViewInit() {
    this.getAllTasks();
  }
  
  sortData(sort: Sort) {
    if (!sort.active || sort.direction === '') this.getAllTasks();

    this.dataSource = new MatTableDataSource<TaskElement>(this.dataSource.data.sort((a, b) => {
      const isAsc = sort.direction === 'asc';
      switch (sort.active) {
        case 'todo': return compare(a.description, b.description, isAsc);
        case 'date': return compare(a.modifiedDate, b.modifiedDate, isAsc);
        default: return 0;
      }
    }));
  }
  
  getAllTasks() {
    this.taskService.getTasks().subscribe(
      (data: TaskElement[]) => {
        if (data && data.length > 0) {
          this.allTasks = new MatTableDataSource<TaskElement>(data.sort((a, b) => b.id - a.id));
          this.dataSource.data = this.allTasks.data;
          if (this.hide_done) this.dataSource = new MatTableDataSource<TaskElement>(data.filter(t => !t.done).sort((a, b) => b.id - a.id));
          this.doneTasks = new Set<TaskElement>(data.filter(t => t.done === true));
          this.dataSource.paginator = this.paginator;
        } else {
          this.dataSource = new MatTableDataSource<TaskElement>();
        }
        this.isLoading = false;
        this.updateSearch();
      },
      (err) => {
        this.errorMessage = err.error.message;
        this.mySnackBar.openSnackBar("Something went wrong", "red");
      }
    );
  }

  updateSearch() {
    this.dataSource.filter = this.searchValue;
  }

  toggleDone(task: TaskElement) {
    if (task.done) {
      this.doneTasks.delete(task);
      task.done = false;
    } else {
      this.doneTasks.add(task);
      task.done = true;
    }
    this.taskService.updateTask(task).subscribe(
      (data) => {
        if (task.done) this.mySnackBar.openSnackBar("Marked the task as done", "green");
        else this.mySnackBar.openSnackBar("Marked the task as not done", "yellow")
        this.getAllTasks();
      },
      (err) => {
        this.errorMessage = err.error.message;
        this.mySnackBar.openSnackBar("Something went wrong", "red");
      }
    );
  }

  hideDone() {
    this.hide_done = !this.hide_done;
    localStorage.setItem('hide', String(this.hide_done));
    this.getAllTasks();
  }

  openCreateTaskDialog(): void {
    const dialogRef = this.dialog.open(EditTaskDialog, {
      width: '420px',
      data: { opName: "Add Task", taskDesc: "" }
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.newTask.description = result;
        this.newTask.modifiedDate = formatDate(new Date(), 'dd/MM/yyyy HH:mm', 'en');
        this.taskService.createTask(this.newTask).subscribe(
          (data) => {
            this.mySnackBar.openSnackBar("Added new task", "green");
            this.getAllTasks();
          },
          (err) => {
            this.errorMessage = err.error.message;
            this.mySnackBar.openSnackBar("Something went wrong", "red");
          }
        );
      }
    });
    this.newTask = { id: 0, description: "", modifiedDate: "", done: false };
  }

  openEditTaskDialog(editTask: TaskElement): void {
    const dialogRef = this.dialog.open(EditTaskDialog, {
      width: '420px',
      data: { opName: "Edit Task", taskDesc: editTask.description }
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.newTask.id = editTask.id;
        this.newTask.description = result;
        this.newTask.modifiedDate = formatDate(new Date(), 'dd/MM/yyyy HH:mm', 'en');
        this.taskService.updateTask(this.newTask).subscribe(
          (data) => {
            this.mySnackBar.openSnackBar("Updated the task", "green");
            this.getAllTasks();
          },
          (err) => {
            this.errorMessage = err.error.message;
            this.mySnackBar.openSnackBar("Something went wrong", "red");
          }
        );
      }
    });
    this.newTask = { id: 0, description: "", modifiedDate: "", done: false };
  }

  clickDeleteTask(deleteTask: TaskElement): void {
    this.taskService.deleteTask(deleteTask).subscribe(
      (data) => {
        this.mySnackBar.openSnackBar("Deleted the task", "red");
        this.getAllTasks();
      },
      (err) => {
        this.errorMessage = err.error.message;
        this.mySnackBar.openSnackBar("Something went wrong", "red");
      }
    );
  }
}

function compare(a: number | string, b: number | string, isAsc: boolean) {
  return (a < b ? -1 : 1) * (isAsc ? 1 : -1);
}


export interface DialogData {
  opName: String
  taskDesc: string;
}

@Component({
  selector: 'edit-task-dialog',
  templateUrl: 'edit.task.dialog.html',
})
export class EditTaskDialog {

  constructor(
    public dialogRef: MatDialogRef<EditTaskDialog>,
    @Inject(MAT_DIALOG_DATA) public data: DialogData) {}

  onNoClick(): void {
    this.dialogRef.close();
  }
}