package j3lcardmarket.atelier3.gameserver.controllers;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import j3lcardmarket.atelier3.commons.models.UserInfo;
import j3lcardmarket.atelier3.commons.utils.CardAuth;
import j3lcardmarket.atelier3.gameserver.dto.CreateGameRoomDto;
import j3lcardmarket.atelier3.gameserver.dto.GameRoomDto;
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

    @GetMapping("/{roomId}")
    @ResponseBody
    public GameRoomDto getRoom(@PathVariable int roomId) {
        return gameRoomService.getRoom(roomId);
    }

    @PostMapping
    @ResponseBody
    @SecurityRequirement(name = "cardauth")
    @CardAuth
    public GameRoomDto createRoom(@Valid @RequestBody CreateGameRoomDto gameRoomDto, @RequestAttribute("cardUserInfo") UserInfo cardUserInfo) {
        return gameRoomService.createRoom(cardUserInfo.surname(), gameRoomDto.getName());
    }

    @PostMapping("/{roomId}/join")
    @ResponseBody
    @SecurityRequirement(name = "cardauth")
    @CardAuth
    public GameRoomDto joinRoomAsOpponent(@PathVariable int roomId, @RequestAttribute("cardUserInfo") UserInfo cardUserInfo) {
        return gameRoomService.joinAsOpponent(cardUserInfo.surname(), roomId);
    }

    @PostMapping("/{roomId}/select-card/{userCardId}")
    @ResponseBody
    @SecurityRequirement(name = "cardauth")
    @CardAuth
    public GameRoomDto selectCard(@PathVariable int roomId, @PathVariable int userCardId, @RequestAttribute("cardUserInfo") UserInfo cardUserInfo) {
        return gameRoomService.selectCard(cardUserInfo.surname(), roomId, userCardId);
    }

    @PostMapping("/{roomId}/play")
    @ResponseBody
    public GameRoomDto playGame(@PathVariable int roomId) {
        return gameRoomService.play(roomId);
    }

    @DeleteMapping("/{roomId}")
    @SecurityRequirement(name = "cardauth")
    @CardAuth
    public GameRoomDto cancelRoom(@PathVariable int roomId, @RequestAttribute("cardUserInfo") UserInfo cardUserInfo) {
        return gameRoomService.cancelRoom(cardUserInfo.surname(), roomId);
    }
}
