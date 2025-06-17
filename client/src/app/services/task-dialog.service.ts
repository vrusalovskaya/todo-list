import { Injectable } from "@angular/core";
import { MatDialog } from "@angular/material/dialog";
import { AddTaskDialogComponent } from "../components/add-task-dialog/add-task-dialog.component";
import { firstValueFrom } from "rxjs";

@Injectable({
   providedIn: "root"
})
export class TaskDialogService {

   constructor(private readonly dialog: MatDialog) { }

   async openAddTaskDialog(): Promise<number | undefined> {
      const dialog = this.dialog.open<AddTaskDialogComponent, any, number>(AddTaskDialogComponent, {
         width: "100rem",
      });
      return await firstValueFrom(dialog.afterClosed());
   }
}
