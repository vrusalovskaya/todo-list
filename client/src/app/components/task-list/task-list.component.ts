import { Component, EventEmitter, Input, Output } from "@angular/core";

import { Task } from "../../models/task.model";
import { TaskComponent } from "../task/task.component";

@Component({
   selector: "td-task-list",
   imports: [TaskComponent],
   templateUrl: "./task-list.component.html",
   styleUrl: "./task-list.component.css",
})
export class TaskListComponent {
   @Input({ required: true }) tasks: Task[] = [];
   @Output() taskStatusChange = new EventEmitter<Task>();

   onTaskStatusChange(task: Task): void {
      this.taskStatusChange.emit(task);
   }
}
