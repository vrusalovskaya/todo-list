import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Task } from "../models/task.model";
import { firstValueFrom } from "rxjs";
import { environment } from "../../environments/environment";

@Injectable({
   providedIn: "root"
})
export class TaskDataService {

   constructor(private readonly http: HttpClient) { }

   async getTasks(): Promise<Task[]> {
      return await firstValueFrom(this.http.get<Task[]>(environment.apiUrl));
   }

   async addTask(content: Task["content"]): Promise<number> {
      return await firstValueFrom(this.http.post<number>(environment.apiUrl, { content }));
   }

   async changeStatus(taskId: Task["id"], isCompleted: Task["completed"]): Promise<void> {
      await firstValueFrom(this.http.patch(this.buildUrl(taskId), { completed: isCompleted }));
   }

   private buildUrl(value: string | number): string {
      return `${environment.apiUrl}/${value}`;
   }
}
