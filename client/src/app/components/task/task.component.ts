import { Component, EventEmitter, Input, Output } from "@angular/core";
import { MatCardModule } from "@angular/material/card";
import { MatButtonModule } from "@angular/material/button";

import { Task } from "../../models/task.model";

@Component({
   selector: "td-task",
   imports: [MatCardModule, MatButtonModule],
   templateUrl: "./task.component.html",
   styleUrl: "./task.component.css",
})
export class TaskComponent {

   @Input({ required: true }) task!: Task;
   @Output() statusChange = new EventEmitter();

   onStatusChangeButtonClicked() {
      this.statusChange.emit()
   }
}
