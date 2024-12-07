package com.movinghouse.security.service;

import com.movinghouse.security.model.Mover;
import com.movinghouse.security.repository.MoverRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class MoverService {

    @Autowired
    MoverRepository moverRepository;

    public List<Mover> fetchMovers() {
        return moverRepository.findAll();
    }

    public List<Mover> getMoverList(int page_num, int record_num){
        Pageable pageable = PageRequest.of(page_num, record_num);
        Page<Mover> moverPage = moverRepository.findAll(pageable);
        return moverPage.toList();
    }

    public Mover getMoverById(Long id) {
        return moverRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Mover not found with the given ID."));
    }

    public Mover updateMover(Mover mover) {
        return moverRepository.save(mover);
    }

    @PreAuthorize("#id == authentication.principal.id")
    public void deleteMoverById(Long id) {
        moverRepository.deleteById(id);
    }
}
