package com.example.service_ticket.controller;

import com.example.service_ticket.model.dictionary.StatusTransitionDictionaryEntity;
import com.example.service_ticket.service.dictionary.CategoryDictionaryService;
import com.example.service_ticket.service.dictionary.StatusDictionaryService;
import com.example.service_ticket.service.dictionary.StatusTransitionDictionaryService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@CrossOrigin("*")
@RestController
@RequestMapping(APIConstant.API + APIConstant.DICTIONARY)
@RequiredArgsConstructor
public class DictionaryController {
    private final CategoryDictionaryService categoryDictionaryService;
    private final StatusDictionaryService statusDictionaryService;
    private final StatusTransitionDictionaryService statusTransitionDictionaryService;

    @ApiOperation(value = "Получение всех статусов")
    @GetMapping(APIConstant.STATUS)
    public ResponseEntity<?> getAllStatus(){
        return ResponseEntity.ok(statusDictionaryService.getAllStatus());
    }

    @ApiOperation(value = "Получение статуса по имени")
    @GetMapping(APIConstant.STATUS + "/{name}")
    public ResponseEntity<?> getStatusByName(@PathVariable String name){
        return statusDictionaryService.getStatusName(name)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @ApiOperation(value = "Получение всех категорий")
    @GetMapping(APIConstant.CATEGORY)
    public ResponseEntity<?> getAllCategories(){
        return ResponseEntity.ok(categoryDictionaryService.getAllCategory());
    }

    @ApiOperation(value = "Получение категории по имени")
    @GetMapping(APIConstant.CATEGORY + "/{name}")
    public ResponseEntity<?> getCategoryByName(@PathVariable String name){
        return categoryDictionaryService.getCategoryByName(name)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @ApiOperation(value = "Получение всех транзитных статусов")
    @GetMapping(APIConstant.STATUS_TRANSITION)
    public ResponseEntity<?> getAllStatusTransition(){
        return ResponseEntity.ok(statusTransitionDictionaryService.getAllStatusTransitions());
    }

    @ApiOperation(value = "Получение массива транзитных статусов по статусу и категории")
    @GetMapping(APIConstant.STATUS_TRANSITION +  APIConstant.FROM_STATUS + "/{fromStatus}" +APIConstant.CATEGORY + "/{category}")
    public ResponseEntity<?> getStatusTransitionByFromStatusAndCategory(@PathVariable String category, @PathVariable String fromStatus){
            List<StatusTransitionDictionaryEntity> statusTransition = statusTransitionDictionaryService.getStatusTransitionByFromStatsAndCategory(fromStatus, category);
            if (statusTransition.isEmpty())
                return ResponseEntity.notFound().build();
            return ResponseEntity.ok(statusTransition);
    }
}
