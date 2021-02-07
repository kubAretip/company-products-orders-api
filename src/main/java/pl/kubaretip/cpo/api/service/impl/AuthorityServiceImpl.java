package pl.kubaretip.cpo.api.service.impl;

import org.springframework.stereotype.Service;
import pl.kubaretip.cpo.api.domain.Authority;
import pl.kubaretip.cpo.api.exception.NotFoundException;
import pl.kubaretip.cpo.api.repository.AuthorityRepository;
import pl.kubaretip.cpo.api.service.AuthorityService;
import pl.kubaretip.cpo.api.util.Translator;

@Service
public class AuthorityServiceImpl implements AuthorityService {

    private final AuthorityRepository authorityRepository;
    private final Translator translator;

    public AuthorityServiceImpl(AuthorityRepository authorityRepository,
                                Translator translator) {
        this.authorityRepository = authorityRepository;
        this.translator = translator;
    }

    @Override
    public Authority getAuthority(String role) {
        return authorityRepository.findByNameOrViewNameIgnoreCase(role)
                .orElseThrow(() -> new NotFoundException(translator.translate("exception.common.notFound.title"),
                        translator.translate("exception.authority.notExists.message", new Object[]{role})));
    }

}
