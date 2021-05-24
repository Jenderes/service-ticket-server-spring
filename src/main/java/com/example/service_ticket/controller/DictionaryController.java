package com.example.service_ticket.controller;

import com.example.service_ticket.model.dictionary.StatusTransitionDictionaryEntity;
import com.example.service_ticket.service.dictionary.CategoryDictionaryService;
import com.example.service_ticket.service.dictionary.StatusDictionaryService;
import com.example.service_ticket.service.dictionary.StatusTransitionDictionaryService;
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

    @GetMapping(APIConstant.STATUS)
    public ResponseEntity<?> getAllStatus(){
        return ResponseEntity.ok(statusDictionaryService.getAllStatus());
    }

    @GetMapping(APIConstant.STATUS + "/{name}")
    public ResponseEntity<?> getStatusByName(@PathVariable String name){
        return statusDictionaryService.getStatusName(name)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping(APIConstant.CATEGORY)
    public ResponseEntity<?> getAllCategories(){
        return ResponseEntity.ok(categoryDictionaryService.getAllCategory());
    }

    @GetMapping(APIConstant.CATEGORY + "/{name}")
    public ResponseEntity<?> getCategoryByName(@PathVariable String name){
        return categoryDictionaryService.getCategoryByName(name)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping(APIConstant.STATUS_TRANSITION)
    public ResponseEntity<?> getAllStatusTransition(){
        return ResponseEntity.ok(statusTransitionDictionaryService.getAllStatusTransitions());
    }

    @GetMapping(APIConstant.STATUS_TRANSITION +  APIConstant.FROM_STATUS + "/{fromStatus}" +APIConstant.CATEGORY + "/{category}")
    public ResponseEntity<?> getStatusTransitionByFromStatusAndCategory(@PathVariable String category, @PathVariable String fromStatus){
            List<StatusTransitionDictionaryEntity> statusTransition = statusTransitionDictionaryService.getStatusTransitionByFromStatsAndCategory(fromStatus, category);
            if (statusTransition.isEmpty())
                return ResponseEntity.notFound().build();
            return ResponseEntity.ok(statusTransition);
    }
}
