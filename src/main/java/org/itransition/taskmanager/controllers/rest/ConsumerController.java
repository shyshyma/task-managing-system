package org.itransition.taskmanager.controllers.rest;

import lombok.Setter;
import org.itransition.taskmanager.dtos.jpa.ConsumerDto;
import org.itransition.taskmanager.mappers.dto.ConsumerDtoMapper;
import org.itransition.taskmanager.mappers.jpa.ConsumerJpaMapper;
import org.itransition.taskmanager.models.jpa.Consumer;
import org.itransition.taskmanager.services.jpa.ConsumerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("{id}")
    public ConsumerDto getConsumer(@PathVariable("id") final Long id) {
        return consumerService.findById(id, consumerDtoMapper::map);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<ConsumerDto> getConsumers(@PageableDefault(size = 100) Pageable pageable) {
        return consumerService.findPage(pageable, consumerDtoMapper::map)
                .stream()
                .collect(Collectors.toList());
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ConsumerDto saveConsumer(@Valid @RequestBody final ConsumerDto consumerDto) {
        Consumer mappedConsumer = consumerJpaMapper.map(consumerDto);
        return consumerService.save(mappedConsumer, consumerDtoMapper::map);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("{consumerId}")
    public ConsumerDto updateConsumer(final @PathVariable("consumerId") Long consumerId,
                                      @Valid @RequestBody final ConsumerDto consumerDto) {

        Consumer consumer = consumerJpaMapper.mapWithId(consumerDto, consumerId);
        return consumerService.update(consumer, consumerDtoMapper::map);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("{id}")
    public void deleteConsumer(@PathVariable("id") final Long consumerId) {
        consumerService.deleteById(consumerId);
    }
}
