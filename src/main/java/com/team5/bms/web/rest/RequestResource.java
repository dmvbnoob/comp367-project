package com.team5.bms.web.rest;

import com.team5.bms.model.Request;
import com.team5.bms.repository.RequestRepository;
import com.team5.bms.service.RequestService;
import com.team5.bms.web.rest.errors.BadRequestAlertException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import com.team5.bms.web.util.HeaderUtil;
import com.team5.bms.web.util.PaginationUtil;
import com.team5.bms.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.team5.bms.model.Request}.
 *
 * @author
 */
@RestController
@RequestMapping("/api/requests")
public class RequestResource {

    private static final Logger LOG = LoggerFactory.getLogger(RequestResource.class);
	
	private String applicationName = "bmsApp";	

    private static final String ENTITY_NAME = "request";

    private final RequestService requestService;

    private final RequestRepository requestRepository;

    public RequestResource(RequestService requestService, RequestRepository requestRepository) {
        this.requestService = requestService;
        this.requestRepository = requestRepository;
    }

    /**
     * {@code POST  /requests} : Create a new request.
     *
     * @param request the request to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new request, or with status {@code 400 (Bad Request)} if the request has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Request> createRequest(@Valid @RequestBody Request request) throws URISyntaxException {
		
        LOG.debug("REST request to save Request : {}", request);
        if (request.getId() != null) {
            throw new BadRequestAlertException("A new request cannot already have an ID", ENTITY_NAME, "idexists");
        }
        request = requestService.save(request);
        return ResponseEntity.created(new URI("/api/requests/" + request.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, request.getId().toString()))
            .body(request);
			
    }

    /**
     * {@code PUT  /requests/:id} : Updates an existing request.
     *
     * @param id the id of the request to save.
     * @param request the request to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated request,
     * or with status {@code 400 (Bad Request)} if the request is not valid,
     * or with status {@code 500 (Internal Server Error)} if the request couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Request> updateRequest(@PathVariable(value = "id", required = false) final Long id, @Valid @RequestBody Request request) throws URISyntaxException {
        
		LOG.debug("REST request to update Request : {}, {}", id, request);
        if (request.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, request.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!requestRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        request = requestService.update(request);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, request.getId().toString()))
            .body(request);
			
    }

    /**
     * {@code PATCH  /requests/:id} : Partial updates given fields of an existing request, field will ignore if it is null
     *
     * @param id the id of the request to save.
     * @param request the request to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated request,
     * or with status {@code 400 (Bad Request)} if the request is not valid,
     * or with status {@code 404 (Not Found)} if the request is not found,
     * or with status {@code 500 (Internal Server Error)} if the request couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Request> partialUpdateRequest(@PathVariable(value = "id", required = false) final Long id, @NotNull @RequestBody Request request) throws URISyntaxException {
        
		LOG.debug("REST request to partial update Request partially : {}, {}", id, request);
        if (request.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, request.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!requestRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Request> result = requestService.partialUpdate(request);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, request.getId().toString())
        );
		
    }

    /**
     * {@code GET  /requests} : get all the requests.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of requests in body.
     */
    @GetMapping("")
    public ResponseEntity<List<Request>> getAllRequests(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
		
        LOG.debug("REST request to get a page of Requests");
        Page<Request> page = requestService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
		
    }

    @GetMapping("/building/{buildingId}/user/{userId}")
    public ResponseEntity<List<Request>> getRequestsByBuildingAndUser(@PathVariable("buildingId") Long buildingId, @PathVariable("userId") Long userId) {
        
        List<Request> requests = requestService.getRequestsByBuildingAndUser(buildingId, userId);
        return ResponseEntity.ok().body(requests);

    }

    @GetMapping("/building/{buildingId}")
    public ResponseEntity<List<Request>> getRequestsByBuilding(@PathVariable("buildingId") Long buildingId) {
        
        List<Request> requests = requestService.getRequestsByBuilding(buildingId);
        return ResponseEntity.ok().body(requests);

    }

    /**
     * {@code GET  /requests/:id} : get the "id" request.
     *
     * @param id the id of the request to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the request, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Request> getRequest(@PathVariable("id") Long id) {
		
        LOG.debug("REST request to get Request : {}", id);
        Optional<Request> request = requestService.findOne(id);
        return ResponseUtil.wrapOrNotFound(request);
		
    }

    /**
     * {@code DELETE  /requests/:id} : delete the "id" request.
     *
     * @param id the id of the request to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRequest(@PathVariable("id") Long id) {
		
        LOG.debug("REST request to delete Request : {}", id);
        requestService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
			
    }
	
}
