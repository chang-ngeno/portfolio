package io.changsoft.portfolio.service.impl;

import io.changsoft.portfolio.domain.SocialMedia;
import io.changsoft.portfolio.repository.SocialMediaRepository;
import io.changsoft.portfolio.service.SocialMediaService;
import io.changsoft.portfolio.service.dto.SocialMediaDTO;
import io.changsoft.portfolio.service.mapper.SocialMediaMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link SocialMedia}.
 */
@Service
@Transactional
public class SocialMediaServiceImpl implements SocialMediaService {

    private final Logger log = LoggerFactory.getLogger(SocialMediaServiceImpl.class);

    private final SocialMediaRepository socialMediaRepository;

    private final SocialMediaMapper socialMediaMapper;

    public SocialMediaServiceImpl(SocialMediaRepository socialMediaRepository, SocialMediaMapper socialMediaMapper) {
        this.socialMediaRepository = socialMediaRepository;
        this.socialMediaMapper = socialMediaMapper;
    }

    @Override
    public SocialMediaDTO save(SocialMediaDTO socialMediaDTO) {
        log.debug("Request to save SocialMedia : {}", socialMediaDTO);
        SocialMedia socialMedia = socialMediaMapper.toEntity(socialMediaDTO);
        socialMedia = socialMediaRepository.save(socialMedia);
        return socialMediaMapper.toDto(socialMedia);
    }

    @Override
    public Optional<SocialMediaDTO> partialUpdate(SocialMediaDTO socialMediaDTO) {
        log.debug("Request to partially update SocialMedia : {}", socialMediaDTO);

        return socialMediaRepository
            .findById(socialMediaDTO.getId())
            .map(
                existingSocialMedia -> {
                    socialMediaMapper.partialUpdate(existingSocialMedia, socialMediaDTO);
                    return existingSocialMedia;
                }
            )
            .map(socialMediaRepository::save)
            .map(socialMediaMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SocialMediaDTO> findAll() {
        log.debug("Request to get all SocialMedias");
        return socialMediaRepository.findAll().stream().map(socialMediaMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<SocialMediaDTO> findOne(Long id) {
        log.debug("Request to get SocialMedia : {}", id);
        return socialMediaRepository.findById(id).map(socialMediaMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete SocialMedia : {}", id);
        socialMediaRepository.deleteById(id);
    }
}
