import { Component, OnInit } from "@angular/core";
import { TaskDataService } from "./services/task-data.service";
import { Task } from "./models/task.model";
import { TaskListComponent } from "./components/task-list/task-list.component";
import { MatButtonModule } from "@angular/material/button";
import { TaskDialogService } from "./services/task-dialog.service";

@Component({
   selector: "td-root",
   imports: [TaskListComponent, MatButtonModule],
   templateUrl: "./app.html",
   styleUrl: "./app.scss"
})
export class App implements OnInit {
   tasks: Task[] = [];

   constructor(
      private readonly taskDataService: TaskDataService,
      private readonly taskDialogService: TaskDialogService
   ) { }

   async ngOnInit(): Promise<void> {
      this.tasks = await this.taskDataService.getTasks();
   }

   async onTaskStatusChange(task: Task) {
      await this.taskDataService.changeStatus(task.id, !task.completed);
      this.tasks = await this.taskDataService.getTasks();
   }

   async onAddTaskClicked() {
      let dialogResult = await this.taskDialogService.openAddTaskDialog();
      if (dialogResult) {
         this.tasks = await this.taskDataService.getTasks();
      }
   }
}
