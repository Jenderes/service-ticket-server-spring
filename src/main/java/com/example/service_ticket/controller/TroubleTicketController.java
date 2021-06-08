package com.example.service_ticket.controller;

import com.example.service_ticket.exception.SearchFieldNameNotFoundException;
import com.example.service_ticket.exception.TicketNotFoundException;
import com.example.service_ticket.model.ticket.TroubleTicket;
import com.example.service_ticket.service.ticket.TicketService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@CrossOrigin("*")
@RestController
@RequestMapping(APIConstant.API + APIConstant.TICKET)
@RequiredArgsConstructor
public class TroubleTicketController {

    private final TicketService ticketService;

    @ApiOperation(value = "Создание тикета",
            notes = "Может выполнить только авторизированный пользователь.")
    @PostMapping
    public ResponseEntity<?> CreateTroubleTicket(@RequestBody TroubleTicket troubleTicket){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(TroubleTicket.convertToDto(ticketService.creatTicket(TroubleTicket.convertToEntity(troubleTicket))));
    }

    @ApiOperation(value = "Найти тикет по id",
            notes = "Может выполнить только авторизированный пользователь.")
    @GetMapping("/{id}")
    public ResponseEntity<?> GetTroubleTicketById(@ApiParam(value = "id тикета", required = true) @PathVariable Long id){
        return ticketService.getTicketById(id)
                .map(TroubleTicket::convertToDto)
                .map(ResponseEntity::ok)
                .orElseGet(() ->ResponseEntity.notFound().build());
    }

    @ApiOperation(value = "Изменить тикет по id",
            notes = "Может выполнить только авторизированный пользователь.")
    @PatchMapping("/{id}")
    public ResponseEntity<?> PatchTroubleTicketById(@RequestBody TroubleTicket troubleTicket,
                                                    @ApiParam(value = "id тикета", required = true)@PathVariable Long id){
        try {
             troubleTicket.setTicketId(id);
             TroubleTicket trouble = TroubleTicket.convertToDto(ticketService.updateTicket(TroubleTicket.convertToEntity(troubleTicket)));
            return ResponseEntity.status(HttpStatus.OK)
                    .body(trouble);
        } catch (TicketNotFoundException e) {
            log.info(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("не нвйде тикет по id: " + id);
        }
    }

    @ApiOperation(value = "Удалить тикет по id",
            notes = "Может выполнить только авторизированный пользователь.")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> DeleteTroubleTicketById(@ApiParam(value = "id тикета", required = true)
                                                         @PathVariable Long id) throws SearchFieldNameNotFoundException {
        try {
            ticketService.deleteTicketById(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT)
                    .body("Удален тикет с id: " + id);
        } catch (TicketNotFoundException e) {
            log.info(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "category", value = "категория тикета", required = false, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "status", value = "статус тикета", required = false, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "userAssigneeId", value = "assignee ID", required = false, dataType = "string", paramType = "query")
    })
    @ApiOperation(value = "Поиск по параметрам",
            notes = "Может выполнить только авторизированный пользователь.")
    @GetMapping
    public ResponseEntity<?> getSpecificTroubleTicket(@RequestParam @ApiParam(value = "параметры поиска", hidden = true) Map<String, String> params ){
        try {
            return ResponseEntity.ok(ticketService.searchTicket(params)
                    .stream()
                    .map(TroubleTicket::convertToDto)
                    .collect(Collectors.toList()));
        } catch (SearchFieldNameNotFoundException e) {
            log.info(e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
