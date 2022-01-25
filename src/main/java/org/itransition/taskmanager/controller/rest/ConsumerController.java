package org.itransition.taskmanager.controller.rest;

import lombok.RequiredArgsConstructor;
import org.itransition.taskmanager.dto.ConsumerDto;
import org.itransition.taskmanager.service.dto.ConsumerService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/consumers",
        produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
public class ConsumerController {

    private final ConsumerService consumerService;

    @GetMapping("{id}")
    public ConsumerDto getConsumer(@PathVariable("id") final Long id) {
        return consumerService.findById(id);
    }

    @GetMapping
    public List<ConsumerDto> getConsumers(@PageableDefault(size = 100) final Pageable pageable) {
        return consumerService.find(pageable);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ConsumerDto saveConsumer(@Valid @RequestBody final ConsumerDto consumerDto) {
        return consumerService.save(consumerDto);
    }

    @PutMapping("{consumerId}")
    public ConsumerDto updateConsumer(final @PathVariable("consumerId") Long consumerId,
                                      @Valid @RequestBody final ConsumerDto consumerDto) {

        return consumerService.updateById(consumerId, consumerDto);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("{id}")
    public void deleteConsumer(@PathVariable("id") final Long consumerId) {
        consumerService.deleteById(consumerId);
    }
}
