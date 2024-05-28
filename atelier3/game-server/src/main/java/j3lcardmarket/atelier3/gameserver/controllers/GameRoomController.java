package j3lcardmarket.atelier3.gameserver.controllers;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import j3lcardmarket.atelier3.commons.models.UserInfo;
import j3lcardmarket.atelier3.commons.utils.CardAuth;
import j3lcardmarket.atelier3.gameserver.dto.CreateGameRoomDto;
import j3lcardmarket.atelier3.gameserver.dto.RoomSummaryDto;
import j3lcardmarket.atelier3.gameserver.services.GameRoomService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/game-rooms")
public class GameRoomController {

    @Autowired
    GameRoomService gameRoomService;

    @GetMapping
    @ResponseBody
    public List<RoomSummaryDto> getRooms() {
        return gameRoomService.getRooms();
    }

    @PostMapping
    @ResponseBody
    @SecurityRequirement(name = "cardauth")
    @CardAuth
    public RoomSummaryDto createRoom(@Valid @RequestBody CreateGameRoomDto gameRoomDto, @RequestAttribute("cardUserInfo") UserInfo cardUserInfo) {
        return gameRoomService.createRoom(cardUserInfo.surname(), gameRoomDto.getName());
    }

    @PutMapping("/{cardId}/join")
    @ResponseBody
    @SecurityRequirement(name = "cardauth")
    @CardAuth
    public RoomSummaryDto joinRoomAsOpponent(@PathVariable int cardId, @RequestAttribute("cardUserInfo") UserInfo cardUserInfo) {
        return gameRoomService.joinAsOpponent(cardUserInfo.surname(), cardId);
    }
}
