import { Component, EventEmitter, Output } from "@angular/core";
import { FormsModule } from "@angular/forms";
import { MatButton } from "@angular/material/button";
import { MatDialogActions, MatDialogClose, MatDialogContent, MatDialogRef, MatDialogTitle } from "@angular/material/dialog";
import { MatFormFieldModule } from "@angular/material/form-field";
import { MatInputModule } from "@angular/material/input";
import { Task } from "../../models/task.model";
import { TaskDataService } from "../../services/task-data.service";

@Component({
   selector: "td-add-task-dialog",
   imports: [
      MatDialogActions,
      MatDialogClose,
      MatDialogContent,
      MatDialogTitle,
      MatButton,
      FormsModule,
      MatFormFieldModule,
      MatInputModule
   ],
   templateUrl: "./add-task-dialog.component.html",
   styleUrl: "./add-task-dialog.component.css",
})
export class AddTaskDialogComponent {

   constructor(
      private readonly taskDataService: TaskDataService,
      private readonly dialogRef: MatDialogRef<AddTaskDialogComponent>
   ) { };

   async onContentSubmitClick(content: string): Promise<void> {
         const taskId = await this.taskDataService.addTask(content);
         this.dialogRef.close(taskId);
   }
}
