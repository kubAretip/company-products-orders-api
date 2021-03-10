package pl.kubaretip.cpo.api.service.impl;

import org.springframework.stereotype.Service;
import pl.kubaretip.cpo.api.domain.Authority;
import pl.kubaretip.cpo.api.exception.NotFoundException;
import pl.kubaretip.cpo.api.repository.AuthorityRepository;
import pl.kubaretip.cpo.api.service.AuthorityService;

@Service
public class AuthorityServiceImpl implements AuthorityService {

    private final AuthorityRepository authorityRepository;

    public AuthorityServiceImpl(AuthorityRepository authorityRepository) {
        this.authorityRepository = authorityRepository;
    }

    @Override
    public Authority getAuthority(String role) {
        return authorityRepository.findByNameOrViewNameIgnoreCase(role)
                .orElseThrow(() -> new NotFoundException("exception.authority.notFound", new Object[]{role}));
    }

}
