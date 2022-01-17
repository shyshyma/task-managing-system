package org.itransition.taskmanager.controllers.rest;

import lombok.Setter;
import org.itransition.taskmanager.dtos.jpa.ConsumerDto;
import org.itransition.taskmanager.exceptions.ResourceNotFoundException;
import org.itransition.taskmanager.mappers.dto.ConsumerDtoMapper;
import org.itransition.taskmanager.mappers.jpa.ConsumerJpaMapper;
import org.itransition.taskmanager.models.jpa.Consumer;
import org.itransition.taskmanager.services.jpa.ConsumerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/consumers", produces = MediaType.APPLICATION_JSON_VALUE)
public class ConsumerController {

    @Setter(onMethod_ = @Autowired)
    private ConsumerService consumerService;

    @Setter(onMethod_ = @Autowired)
    private ConsumerJpaMapper consumerJpaMapper;

    @Setter(onMethod_ = @Autowired)
    private ConsumerDtoMapper consumerDtoMapper;

    @GetMapping("{id}")
    public ResponseEntity<ConsumerDto> getConsumer(@PathVariable("id") final Long id) {
        return ResponseEntity.ok(consumerService.findById(id, consumerDtoMapper::map));
    }

    @GetMapping
    public ResponseEntity<List<ConsumerDto>> getConsumers(@PageableDefault(size = 100) Pageable pageable) {
        List<ConsumerDto> consumerDtos = consumerService.findPage(pageable, consumerDtoMapper::map)
                .stream()
                .collect(Collectors.toList());
        return (consumerDtos.isEmpty())
                ? new ResponseEntity<>(HttpStatus.NOT_FOUND)
                : new ResponseEntity<>(consumerDtos, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ConsumerDto> saveConsumer(@Valid @RequestBody final ConsumerDto consumerDto) {
        consumerService.save(consumerJpaMapper.map(consumerDto));
        return new ResponseEntity<>(consumerDto, HttpStatus.CREATED);
    }

    @PutMapping("{consumerId}")
    public ResponseEntity<ConsumerDto> updateConsumer(final @PathVariable("consumerId") Long consumerId,
                                                      @Valid @RequestBody final ConsumerDto consumerDto) {

        if (!consumerService.existsById(consumerId)) {
            throw new ResourceNotFoundException("No resource was found");
        }

        Consumer consumer = consumerJpaMapper.map(consumerDto, consumerId);
        consumerService.update(consumer);
        return new ResponseEntity<>(consumerDtoMapper.map(consumer), HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<ConsumerDto> deleteConsumer(@PathVariable("id") final Long consumerId) {

        if (!consumerService.existsById(consumerId)) {
            throw new ResourceNotFoundException("Resource wasn't found");
        }

        consumerService.deleteById(consumerId);
        return ResponseEntity.noContent().build();
    }
}
