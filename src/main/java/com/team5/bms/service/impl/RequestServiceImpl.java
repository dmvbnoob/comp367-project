package com.team5.bms.service.impl;

import com.team5.bms.model.Request;
import com.team5.bms.repository.RequestRepository;
import com.team5.bms.service.RequestService;

import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.team5.bms.model.Request}.
 * 
 *  @author Jasper Belenzo
 * 
 */
@Service
@Transactional
public class RequestServiceImpl implements RequestService {

    private static final Logger LOG = LoggerFactory.getLogger(RequestServiceImpl.class);

    private final RequestRepository requestRepository;

    public RequestServiceImpl(RequestRepository requestRepository) {
        this.requestRepository = requestRepository;
    }
    
    @Override
    public List<Request> getRequestsByBuildingAndSuperIntendent(Long buildingId, Long assigneeId) {
        return requestRepository.findByBuildingIdAndAssigneeId(buildingId, assigneeId);
    }

    @Override
    public List<Request> getRequestsByBuildingAndUser(Long buildingId, Long userId) {
        return requestRepository.findByBuildingIdAndUserId(buildingId, userId);
    }

    @Override
    public List<Request> getRequestsByBuilding(Long buildingId) {
        return requestRepository.findByBuildingId(buildingId);
    }

    @Override
    public Request save(Request request) {
        LOG.debug("Request to save Request : {}", request);
        return requestRepository.save(request);
    }

    @Override
    public Request update(Request request) {
        LOG.debug("Request to update Request : {}", request);
        return requestRepository.save(request);
    }

    @Override
    public Optional<Request> partialUpdate(Request request) {
        LOG.debug("Request to partially update Request : {}", request);

        return requestRepository
            .findById(request.getId())
            .map(existingRequest -> {
                if (request.getTitle() != null) {
                    existingRequest.setTitle(request.getTitle());
                }
                if (request.getDescription() != null) {
                    existingRequest.setDescription(request.getDescription());
                }
                if (request.getStatus() != null) {
                    existingRequest.setStatus(request.getStatus());
                }
                if (request.getCreateDate() != null) {
                    existingRequest.setCreateDate(request.getCreateDate());
                }
                if (request.getProgressDate() != null) {
                    existingRequest.setProgressDate(request.getProgressDate());
                }
                if (request.getUpdateDate() != null) {
                    existingRequest.setUpdateDate(request.getUpdateDate());
                }
                if (request.getAssignee() != null) {
                    existingRequest.setAssignee(request.getAssignee());
                }

                return existingRequest;
            })
            .map(requestRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Request> findAll(Pageable pageable) {
        LOG.debug("Request to get all Requests");
        return requestRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Request> findOne(Long id) {
        LOG.debug("Request to get Request : {}", id);
        return requestRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete Request : {}", id);
        requestRepository.deleteById(id);
    }
	
}

