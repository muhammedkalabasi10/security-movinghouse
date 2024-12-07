package com.movinghouse.security.controller;

import com.movinghouse.security.model.Mover;
import com.movinghouse.security.service.MoverService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/security/movers")
public class MoverController {
    @Autowired
    private MoverService moverService;

    @GetMapping
    public ResponseEntity<List<Mover>> fetchMovers(){
        return ResponseEntity.ok(moverService.fetchMovers());
    }

    @GetMapping("/page")
    public ResponseEntity<List<Mover>> getMoverList(@RequestParam int page_num, @RequestParam int record_num){
        return ResponseEntity.ok(moverService.getMoverList(page_num, record_num));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Mover> getMoverById(@PathVariable Long id){
        Mover mover = moverService.getMoverById(id);
        return ResponseEntity.ok(mover);
    }

    @PutMapping
    public ResponseEntity<Mover> updateMover(@RequestBody @Valid Mover mover){
        return ResponseEntity.ok(moverService.updateMover(mover));
    }
    @PreAuthorize("#id == authentication.principal.id")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteMover(@PathVariable Long id) {
        moverService.deleteMoverById(id);
        return ResponseEntity.ok("Mover successfully deleted.");
    }
}
