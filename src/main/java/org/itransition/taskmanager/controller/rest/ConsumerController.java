package org.itransition.taskmanager.controller.rest;

import lombok.RequiredArgsConstructor;
import org.itransition.taskmanager.dto.ConsumerDto;
import org.itransition.taskmanager.service.dto.ConsumerDtoService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/consumers", produces = MediaType.APPLICATION_JSON_VALUE)
public class ConsumerController {

    private final ConsumerDtoService consumerDtoService;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("{id}")
    public ConsumerDto getConsumer(@PathVariable("id") final Long id) {
        return consumerDtoService.findById(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<ConsumerDto> getConsumers(@PageableDefault(size = 100) Pageable pageable) {
        return consumerDtoService.find(pageable);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ConsumerDto saveConsumer(@Valid @RequestBody final ConsumerDto consumerDto) {
        return consumerDtoService.save(consumerDto);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("{consumerId}")
    public ConsumerDto updateConsumer(final @PathVariable("consumerId") Long consumerId,
                                      @Valid @RequestBody final ConsumerDto consumerDto) {

        return consumerDtoService.updateById(consumerId, consumerDto);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("{id}")
    public void deleteConsumer(@PathVariable("id") final Long consumerId) {
        consumerDtoService.deleteById(consumerId);
    }
}
