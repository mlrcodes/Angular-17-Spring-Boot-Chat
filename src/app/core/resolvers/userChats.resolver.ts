import { Observable } from "rxjs";
import { Chat } from "../models/chat";
import { ActivatedRouteSnapshot, ResolveFn, RouterStateSnapshot } from "@angular/router";
import { inject } from "@angular/core";
import { ChatsService } from "../services/chats/chats.service";

export const UserChatsResolver: ResolveFn<Chat[]> = (
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
): Observable<Chat[]> => {
    return inject(ChatsService).getUserChats()
}
