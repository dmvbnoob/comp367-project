package com.team5.bms.service.impl;

import com.team5.bms.model.Card;
import com.team5.bms.repository.CardRepository;
import com.team5.bms.service.CardService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.team5.bms.model.Card}.
 * 
 * @author Jasper "Principal Engineer and Software Architect" Belenzo
 */
@Service
@Transactional
public class CardServiceImpl implements CardService {

    private static final Logger LOG = LoggerFactory.getLogger(CardServiceImpl.class);

    private final CardRepository cardRepository;

    public CardServiceImpl(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    @Override
    public Card save(Card card) {
        LOG.debug("Request to save Card : {}", card);
        return cardRepository.save(card);
    }

    @Override
    public Card update(Card card) {
        LOG.debug("Request to update Card : {}", card);
        return cardRepository.save(card);
    }

    @Override
    public Optional<Card> partialUpdate(Card card) {
        LOG.debug("Request to partially update Card : {}", card);

        return cardRepository
            .findById(card.getId())
            .map(existingCard -> {
                if (card.getNumber() != null) {
                    existingCard.setNumber(card.getNumber());
                }
                if (card.getName() != null) {
                    existingCard.setName(card.getName());
                }
                if (card.getExpiry() != null) {
                    existingCard.setExpiry(card.getExpiry());
                }
                if (card.getCvv() != null) {
                    existingCard.setCvv(card.getCvv());
                }

                return existingCard;
            })
            .map(cardRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Card> findAll(Pageable pageable) {
        LOG.debug("Request to get all Cards");
        return cardRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Card> findOne(Long id) {
        LOG.debug("Request to get Card : {}", id);
        return cardRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete Card : {}", id);
        cardRepository.deleteById(id);
    }

}
